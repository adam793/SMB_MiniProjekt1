package pl.pjatk.smb1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import pl.pjatk.smb1.enums.ColorTxt;

public class OptionsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private NumberPicker numberPicker;
    private String[] arraySpinner;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        configNumerPicker();
        configColorSpinner();
    }

    private void configColorSpinner() {
        this.arraySpinner = new String[]{
               ColorTxt.BLACK.getDisplayName(),  ColorTxt.RED.getDisplayName(), ColorTxt.BLUE.getDisplayName()};
        spinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);
        spinner.setScrollBarSize(28);
        String color = sharedPreferences.getString("fontColor", ColorTxt.BLACK.getDisplayName());
        ColorTxt colorTxt = ColorTxt.getValue(color);
        spinner.setSelection(colorTxt.getPosition());


    }

    private void configNumerPicker() {
        float fontSize = sharedPreferences.getFloat("fontSize", 22);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(4);
        numberPicker.setMaxValue(40);
        numberPicker.setValue((int) fontSize);
    }

    public void save(View v) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("fontSize", numberPicker.getValue());
        editor.putString("fontColor", spinner.getSelectedItem().toString());
        editor.commit();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
