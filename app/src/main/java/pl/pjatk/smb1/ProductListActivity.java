package pl.pjatk.smb1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import pl.pjatk.smb1.data.DatabaseHandler;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        this.bindList();
    }

    public void navigateToAdd(View v) {
        Intent i = new Intent(this, AddProductActivity.class);
        startActivity(i);
    }

    private void bindList() {
        ListView yourListView = (ListView) findViewById(R.id.listView);
        yourListView.setAdapter(new ProductsAdapter(this, new DatabaseHandler(this).getAll()));
    }
}
