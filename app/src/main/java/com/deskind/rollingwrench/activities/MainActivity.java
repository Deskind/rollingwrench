package com.deskind.rollingwrench.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.database.AppDatabase;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.Repair;
import com.deskind.rollingwrench.managers.MileageManager;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //Variables
    AlertDialog dialog = null;
    public static ArrayAdapter<String> adapter;
    public static String newCarBrand = null;

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
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        context = getApplicationContext();
        spinner = (Spinner)findViewById(R.id.car_spinner);

        fuelSpendings = (TextView)findViewById(R.id.fuelSpengings);
        repairSpendings = (TextView)findViewById(R.id.repairSpendings);
        fluidsSpendings = (TextView)findViewById(R.id.fluidsSpendings);

        //Prepare spinner
        spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(getApplicationContext())));
        spinner.setOnItemSelectedListener(new SpinnerItem());

        if(spinner.getCount() == 0){
            Toast.makeText(this, "Введите имя для вашего авто", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, NewCarActivity.class));
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        //Check on new car in the app
        if(NewCarActivity.isThereNewCar){
            spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(this)));
            NewCarActivity.isThereNewCar = false;
        }

        String carBrand = (String)spinner.getSelectedItem();

        float forFuel = calcFuelSpendings(carBrand);
        fuelSpendings.setText(String.format("%.1f", forFuel));

        float forRepairs = calcRepairsSpendings(carBrand);
        repairSpendings.setText(String.format("%.1f", forRepairs));

        int fluidsTotalCost = calcFluidsSpendings(carBrand);
        fluidsSpendings.setText(String.valueOf(fluidsTotalCost));
    }

    public void fuelCategoryClicked(View v){
        if(MainActivity.spinner.getCount() != 0) {
            Intent intent = new Intent(this, FuelChooseActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Необходимо добавить авто", Toast.LENGTH_SHORT).show();
        }

    }

    public void fluidsCategoryClicked(View v){
        if(MainActivity.spinner.getCount() != 0) {
            Intent intent = new Intent(this, FluidsActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Необходимо добавить авто", Toast.LENGTH_SHORT).show();
        }
    }

    public void repairsCategotyClicked(View v){
        if(MainActivity.spinner.getCount() != 0) {
            Intent intent = new Intent(this, RepairsCategoryActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Необходимо добавить авто", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_car_menu_item:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.dialog_delete_car, null);

                builder.setView(view);
                dialog = builder.create();
                dialog.show();

                final EditText etCarBrand = (EditText)view.findViewById(R.id.etDeleteCar);
                Button button = (Button)view.findViewById(R.id.car_delete_button);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String carToDelete = etCarBrand.getText().toString();
                        Log.i("CARBRAND" , carToDelete);
                        if(carToDelete.isEmpty()){

                            Toast.makeText(getApplicationContext(), "Введите название авто", Toast.LENGTH_LONG).show();
                        }else{
                            String brand = (String)MainActivity.spinner.getSelectedItem();
                            Car car = DBUtility.getAppDatabase(getApplicationContext()).getCarsDao().getCar(carToDelete);
                            if(car==null){
                                Toast.makeText(getApplicationContext(), "Авто с таким названием не найдено", Toast.LENGTH_LONG).show();
                            }else {
                                DBUtility.getAppDatabase(getApplicationContext()).getCarsDao().deleteCar(carToDelete);
                                Toast.makeText(getApplicationContext(), "Авто удалено", Toast.LENGTH_LONG).show();

                                //Refresh values on the activity
                                spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(getApplicationContext())));
                                float forFuel = calcFuelSpendings((String)MainActivity.spinner.getSelectedItem());
                                fuelSpendings.setText(String.format("%.1f", forFuel));
                                float forRepairs = calcRepairsSpendings((String)MainActivity.spinner.getSelectedItem());
                                repairSpendings.setText(String.format("%.1f", forRepairs));
                                int fluidsTotalCost = calcFluidsSpendings((String)MainActivity.spinner.getSelectedItem());
                                fluidsSpendings.setText(String.valueOf(fluidsTotalCost));

                                dialog.cancel();
                            }
                        }

                    }
                });
                return true;

            case R.id.set_current_mileage:
            MileageManager.showCurrentMileageDialog(this, getLayoutInflater());
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }





    class SpinnerItem implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String carBrand = (String)spinner.getSelectedItem();

            //reset current mileage
            MileageManager.setCurrentMileage(0);


            float forFuel = calcFuelSpendings(carBrand);
            fuelSpendings.setText(String.format("%.1f", forFuel));

            float forRepairs = calcRepairsSpendings(carBrand);
            repairSpendings.setText(String.format("%.1f", forRepairs));

            int forFluids = calcFluidsSpendings(carBrand);
            fluidsSpendings.setText(String.valueOf(forFluids));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
