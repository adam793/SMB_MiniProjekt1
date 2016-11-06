package pl.pjatk.smb1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import static pl.pjatk.smb1.data.DatabaseContract.DATABASE_NAME;
import static pl.pjatk.smb1.data.DatabaseContract.DATABASE_VERSION;

public class ProductContentProvider extends ContentProvider {

    private SQLiteDatabase db;
    private DatabaseHandler dbHelper;
    private static final UriMatcher uriMatcher;
    private static final int uriCode = 1;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.ProductEntry.TABLE_PRODUCTS, uriCode);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode: {
                return DatabaseContract.ProductEntry.CONTENT_DIR_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri;
        switch (uriMatcher.match(uri)) {
            case uriCode: {
                long id = db.insert(DatabaseContract.ProductEntry.TABLE_PRODUCTS, null, values);
                returnUri = DatabaseContract.ProductEntry.buildProductsUri(id);
                break;
            }
            default: {
                throw new SQLException("Failed to add a record into " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(DatabaseContract.ProductEntry.TABLE_PRODUCTS, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int numUpdated;
        switch (uriMatcher.match(uri)) {
            case uriCode: {
                numUpdated = db.update(DatabaseContract.ProductEntry.TABLE_PRODUCTS,
                        contentValues, selection, selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            case uriCode: {
                retCursor = dbHelper.getReadableDatabase().query(
                        DatabaseContract.ProductEntry.TABLE_PRODUCTS, projection, selection,
                        selectionArgs, null, null, sortOrder);
                return retCursor;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


}