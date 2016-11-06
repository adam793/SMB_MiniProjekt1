package pl.pjatk.smb1.data;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;

import static pl.pjatk.smb1.data.DatabaseContract.DATABASE_NAME;
import static pl.pjatk.smb1.data.DatabaseContract.DATABASE_VERSION;


public class DatabaseHandler extends SQLiteOpenHelper {


    private Context ctx;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public Cursor getAll() {
        return ctx.getContentResolver().query(DatabaseContract.ProductEntry.CONTENT_URI, null, null, null, DatabaseContract.ProductEntry.COLUMN_ID + " ASC");
    }

    public Cursor getById(int id) {
        return ctx.getContentResolver().query(
                DatabaseContract.ProductEntry.CONTENT_URI, null, DatabaseContract.ProductEntry.COLUMN_ID + "=" + id, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DatabaseContract.ProductEntry.TABLE_PRODUCTS + "("
                + DatabaseContract.ProductEntry.COLUMN_ID + " INTEGER PRIMARY KEY," + DatabaseContract.ProductEntry.COLUMN_NAME + " TEXT, " + DatabaseContract.ProductEntry.COLUMN_DONE + " INTEGER)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ProductEntry.TABLE_PRODUCTS);
        onCreate(db);
    }

    public void UpdateById(ContentValues values, int pId) {
        ctx.getContentResolver().update(DatabaseContract.ProductEntry.CONTENT_URI, values, DatabaseContract.ProductEntry.COLUMN_ID + "=" + pId, null);

    }
}
