package pl.pjatk.smb1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import pl.pjatk.smb1.data.DatabaseHandler;
import pl.pjatk.smb1.data.DatabaseContract;
import pl.pjatk.smb1.enums.ColorTxt;

import static java.security.AccessController.getContext;
import static pl.pjatk.smb1.data.DatabaseContract.ProductEntry.*;

/**
 * Created by user on 14.10.2016.
 */
public class ProductsAdapter extends CursorAdapter {
    private Context context;
    private DatabaseHandler db;

    public ProductsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
        db = new DatabaseHandler(this.context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        manipulationOnProductText(view, context, cursor);
        int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        configCheckBox(view, cursor, id);
        configRemoveButton(view, id);
        configEditButton(view, id);
    }

    private void configEditButton(View view, final int id) {
        Button editbtn = (Button) view.findViewById(R.id.edit);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductEditActivity.class);
                intent.putExtra("product", id);
                context.startActivity(intent);
            }
        });
    }

    private void configRemoveButton(final View view, final int id) {
        final Button btn = (Button) view.findViewById(R.id.remove);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getContentResolver().delete(CONTENT_URI, COLUMN_ID +"="+id, null);
                context.getContentResolver().notifyChange(CONTENT_URI, null);
                swapCursor(db.getAll());
            }
        });
    }

    private void configCheckBox(View view, Cursor cursor, final int id) {
        final CheckBox cb= (CheckBox) view.findViewById(R.id.checkBox);
        cb.setChecked(cursor.getInt(cursor.getColumnIndex(COLUMN_DONE)) == 1);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_DONE, isChecked);
                db.UpdateById(values, id);
            }
        });

    }

    private void manipulationOnProductText(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        float fontSize = sharedPref.getFloat("fontSize", 6);
        name.setTextSize(fontSize);
        String color = sharedPref.getString("fontColor", ColorTxt.BLACK.getDisplayName());
        ColorTxt colorTxt = ColorTxt.getValue(color);
        name.setTextColor(colorTxt.getColor());
    }
}