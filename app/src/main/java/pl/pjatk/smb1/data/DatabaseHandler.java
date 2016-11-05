package pl.pjatk.smb1.data;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by user on 14.10.2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "productsManager";
    private Context ctx;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public Cursor getAll() {
        return ctx.getContentResolver().query(ProductsContract.ProductEntry.CONTENT_URI, null, null, null, ProductsContract.ProductEntry.KEY_ID + " DESC");
    }

    public Cursor getById(int id) {
        return ctx.getContentResolver().query(
                ProductsContract.ProductEntry.CONTENT_URI, null, ProductsContract.ProductEntry.KEY_ID + "=" + id, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + ProductsContract.ProductEntry.TABLE_PRODUCTS + "("
                + ProductsContract.ProductEntry.KEY_ID + " INTEGER PRIMARY KEY," + ProductsContract.ProductEntry.KEY_NAME + " TEXT, " + ProductsContract.ProductEntry.KEY_ACTIVE + " INTEGER)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductsContract.ProductEntry.TABLE_PRODUCTS);
        onCreate(db);
    }

    public void UpdateById(ContentValues values, int pId) {
        ctx.getContentResolver().update(ProductsContract.ProductEntry.CONTENT_URI, values, ProductsContract.ProductEntry.KEY_ID + "=" + pId, null);

    }
}
