package pl.pjatk.smb1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import pl.pjatk.smb1.data.DatabaseHandler;
import pl.pjatk.smb1.data.ProductsContract;

public class ProductEditActivity extends AppCompatActivity {

    private EditText productName;
    private DatabaseHandler databaseHandler;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        databaseHandler = new DatabaseHandler(this);
        productName = (EditText)findViewById(R.id.name);
        Intent i = getIntent();
        productId = i.getIntExtra("product", 0);
        Cursor c = databaseHandler.getById(productId);
        c.moveToFirst();

       productName.setText(c.getString(c.getColumnIndex(ProductsContract.ProductEntry.KEY_NAME)));

    }

    public void update(View v) {
        ContentValues values = new ContentValues();
        values.put(ProductsContract.ProductEntry.KEY_NAME, productName.getText().toString());
        databaseHandler.UpdateById(values, productId);
        Intent i = new Intent(this, ProductListActivity.class);
        startActivity(i);
    }
}
