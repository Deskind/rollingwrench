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
import com.deskind.rollingwrench.entities.FluidService;
import com.deskind.rollingwrench.entities.Repair;
import com.rollingwrench.deskind.rollingwrench.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Variables
    public static ArrayAdapter<String> adapter;

    //Views
    public static Spinner spinner = null;
    public static Context context;
    public TextView fuelSpendings, repairSpendings, fluidsSpendings;

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

        fuelSpendings = (TextView)findViewById(R.id.fuelSpengings);
        repairSpendings = (TextView)findViewById(R.id.repairSpendings);
        fluidsSpendings = (TextView)findViewById(R.id.fluidsSpendings);


        //Set up categories spendings

        //Prepare spinner
        spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(getApplicationContext())));
        spinner.setOnItemSelectedListener(new SpinnerItem());
    }

    @Override
    protected void onResume(){
        super.onResume();

        String carBrand = (String)spinner.getSelectedItem();

        float forFuel = calcFuelSpendings(carBrand);
        fuelSpendings.setText(String.format("%.1f", forFuel));

        float forRepairs = calcRepairsSpendings(carBrand);
        repairSpendings.setText(String.format("%.1f", forRepairs));

        int fluidsTotalCost = calcFluidsSpendings(carBrand);
        fluidsSpendings.setText(String.valueOf(fluidsTotalCost));
    }

    public void fuelCategoryClicked(View v){
        Intent intent = new Intent(this, FuelChooseActivity.class);
        startActivity(intent);
    }

    public void fluidsCategoryClicked(View v){
        Intent intent = new Intent(this, FluidsActivity.class);
        startActivity(intent);
    }

    public void repairsCategotyClicked(View v){
        Intent intent = new Intent(this, RepairsCategoryActivity.class);
        startActivity(intent);
    }

    public void addNewCarClicked(View v){
        Intent intent = new Intent(this, NewCarActivity.class);
        startActivity(intent);
    }

    private ArrayAdapter prepareSpinner(AppDatabase database){
        String [] arr = database.getCarsDao().getAllCarBrands();
        Log.i("DB", "CARS IN DATABASE " + arr.length);
        adapter =  new ArrayAdapter<String>(context, R.layout.spinner_item, arr);
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

    public static float calcRepairsSpendings(String carBrand){
        float repairsSpendings = 0;
        Repair[] repairs = DBUtility.getAppDatabase(context).getCarsDao().getAllRapairsForBrand(carBrand);
        ArrayList<Repair> repairs1 = new ArrayList<>(Arrays.asList(repairs));
        for(Repair r : repairs1){
            repairsSpendings+=r.getPartPrice();
        }
        return repairsSpendings;
    }

    public static int calcFluidsSpendings(String carBrand){
        int total = 0;
        int [] arr = DBUtility.getAppDatabase(context).getCarsDao().getFluidServicesTotalCost(carBrand);
        for(int i = 0 ; i < arr.length; i++){
            total+=arr[i];
        }
        return total;
    }


    class SpinnerItem implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String carBrand = (String)spinner.getSelectedItem();
            float forFuel = calcFuelSpendings(carBrand);
            fuelSpendings.setText(String.format("%.1f", forFuel));

            float forRepairs = calcRepairsSpendings(carBrand);
            repairSpendings.setText(String.format("%.1f", forRepairs));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
