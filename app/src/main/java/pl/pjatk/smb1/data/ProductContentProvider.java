package pl.pjatk.smb1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ProductContentProvider extends ContentProvider {

    private DatabaseHandler dbAdapter;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int PRODUCT = 100;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProductsContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, ProductsContract.ProductEntry.TABLE_PRODUCTS, PRODUCT);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbAdapter = new DatabaseHandler(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT: {
                return ProductsContract.ProductEntry.CONTENT_DIR_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbAdapter.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case PRODUCT: {
                long id = db.insert(ProductsContract.ProductEntry.TABLE_PRODUCTS, null, values);
                returnUri = ProductsContract.ProductEntry.buildProductsUri(id);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case PRODUCT: {
                retCursor = dbAdapter.getReadableDatabase().query(
                        ProductsContract.ProductEntry.TABLE_PRODUCTS, projection, selection,
                        selectionArgs, null, null, sortOrder);
                return retCursor;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbAdapter.getWritableDatabase();
        int numUpdated = 0;


        switch (sUriMatcher.match(uri)) {
            case PRODUCT: {
                numUpdated = db.update(ProductsContract.ProductEntry.TABLE_PRODUCTS,
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
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbAdapter.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted = 0;
        switch (match) {
            case PRODUCT:
                numDeleted = db.delete(ProductsContract.ProductEntry.TABLE_PRODUCTS, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numDeleted;
    }

}
