package pl.pjatk.smb1.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 14.10.2016.
 */
public class DatabaseContract {
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "productsManager";
    public static final String CONTENT_AUTHORITY = "pl.pjatk.smb1.product";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ProductEntry implements BaseColumns {

        public static final String TABLE_PRODUCTS = "PRODUCTS";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DONE = "done";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_PRODUCTS).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_PRODUCTS;

        public static Uri buildProductsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
