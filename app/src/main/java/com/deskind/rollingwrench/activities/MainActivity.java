package com.deskind.rollingwrench.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.deskind.rollingwrench.database.AppDatabase;
import com.deskind.rollingwrench.database.DBUtility;
import com.rollingwrench.deskind.rollingwrench.R;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    //Variables
    //Views
    public static Spinner spinner = null;
    public static Context context;
    public TextView fuelSpendings;

    @Override
    protected void onStart(){
        super.onStart();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        spinner = (Spinner)findViewById(R.id.car_spinner);


        //Prepare spinner
        spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(getApplicationContext())));
        spinner.setOnItemSelectedListener(new SpinnerItem());
        fuelSpendings = (TextView)findViewById(R.id.fuelSpengings);

        //Set up categories spendings

    }

    @Override
    protected void onResume(){
        super.onResume();

        String carBrand = (String)spinner.getSelectedItem();
        float spendings = calcFuelSpendings(carBrand);
        fuelSpendings.setText(String.format("%.1f", spendings));

    }

    public void fuelCategoryClicked(View v){
        Intent intent = new Intent(this, FuelChooseActivity.class);
        startActivity(intent);
    }

    public void addNewCarClicked(View v){
        Intent intent = new Intent(this, NewCarActivity.class);
        startActivity(intent);
    }

    private ArrayAdapter prepareSpinner(AppDatabase database){
        String [] arr = database.getCarsDao().getAllCarBrands();
        Log.i("DB", "CARS IN DATABASE " + arr.length);
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        return adapter;
    }

    public static float calcFuelSpendings(String carBrand){
        float fuelSpendings = 0;
        Cursor c = DBUtility.getFuelUpsList(context);
        String [] cNames = c.getColumnNames();
        int index = 0;
        String s = "";
        while (c.moveToNext()){
            index = c.getColumnIndexOrThrow(cNames[1]);
            s = c.getString(index);
            if(s.equals(carBrand)){
                Log.i("EQ", "STRINGS ARE EQUALS");
                index = c.getColumnIndexOrThrow(cNames[4]);
                fuelSpendings += c.getFloat(index);
            }
        }


        return fuelSpendings;
    }


    class SpinnerItem implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String carBrand = (String)spinner.getSelectedItem();
            float spendings = calcFuelSpendings(carBrand);
            fuelSpendings.setText(String.format("%.1f", spendings));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
