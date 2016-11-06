package pl.pjatk.smb1;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.pjatk.smb1.data.DatabaseContract;

public class AddProductActivity extends AppCompatActivity {

    private AddProductActivity addProductActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        this.bindSave();
        addProductActivity = this;
    }


    private void insertData() {
        ContentValues[] valuesAttr = new ContentValues[1];
        valuesAttr[0] = new ContentValues();
        EditText name  = (EditText)findViewById(R.id.name);
        valuesAttr[0].put(DatabaseContract.ProductEntry.COLUMN_NAME, name.getText().toString());
        getContentResolver().bulkInsert(DatabaseContract.ProductEntry.CONTENT_URI, valuesAttr);
    }

    private void bindSave() {
        final Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertData();
                Intent i = new Intent(AddProductActivity.this, ProductListActivity.class);
                startActivity(i);
            }
        });
    }
}
