package com.deskind.rollingwrench.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.dao.CarsDAO;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.Repair;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class RepairsCategoryActivity extends AppCompatActivity {

    ImageButton previousRepair, nextRepair, saveRepair;
    TextView repairDate;
    EditText repairMileage, repairPartManufacturer, repairPartNumber, repairPartDescription, repairCost;

    CarsDAO dao;
    Repair [] repairs = null;
    ArrayList<Repair> repairs1 = null;
    int repairsSize;
    int repairsPointer;
    String currentBrand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairs_category);

        dao = DBUtility.getAppDatabase(getApplicationContext()).getCarsDao();


        //Set title
        String carBrand = (String)MainActivity.spinner.getSelectedItem();
        setTitle(carBrand + "   (Ремонты)");

        //Find views
        previousRepair = (ImageButton)findViewById(R.id.nextRepair);
        nextRepair = (ImageButton)findViewById(R.id.nextRepair);
        saveRepair = (ImageButton)findViewById(R.id.saveRepair);

        repairDate = (TextView)findViewById(R.id.repairDate);

        repairMileage = (EditText)findViewById(R.id.repairMileage);
        repairPartManufacturer = (EditText)findViewById(R.id.repairPartManufacturer);
        repairPartNumber = (EditText)findViewById(R.id.repairPartNumber);
        repairPartDescription = (EditText)findViewById(R.id.repairPartDescription);
        repairCost = (EditText)findViewById(R.id.repairCost);


        //Set listeners
        repairDate.setOnClickListener(new repairDateListener());
    }

    @Override
    protected void onResume(){
        super.onResume();

        currentBrand = (String)MainActivity.spinner.getSelectedItem();
    }

    public void showPreviousRepair(View v){
        Repair r = null;
        String brandInSpinner = (String)MainActivity.spinner.getSelectedItem();

        if(repairs == null || !brandInSpinner.equals(currentBrand)){
            repairs = dao.getAllRapairsForBrand(brandInSpinner);
            repairs1 = new ArrayList<>(Arrays.asList(repairs));
            repairsSize = repairs1.size();
            repairsPointer = repairsSize - 1;
        }

        if(repairs1.isEmpty()){
            showToast("Нет записей");
        }else{
            if(repairsPointer >= 0){
                r = repairs1.get(repairsPointer);

                Log.i("R", "date value is " + r.getDate());
                Log.i("R", "manufacturer value is " + r.getManufacturer());
                Log.i("R", "partNumber value is " +  r.getPartNumber());
                Log.i("R", "description value is " + r.getDescription());
                Log.i("R", "mileage value is " + r.getMileage());
                Log.i("R", "cost value is " + r.getPartPrice());

                repairDate.setText(r.getDate());
                repairPartManufacturer.setText(r.getManufacturer());
                repairPartNumber.setText(r.getPartNumber());
                repairPartDescription.setText(r.getDescription());
                repairMileage.setText(String.valueOf(r.getMileage()));
                repairCost.setText(String.valueOf(r.getPartPrice()));

                if(repairsPointer != 0){
                    repairsPointer-=1;
                }else{
                    showToast("Последнияя запись");
                }
            }
        }

    }

    public void showNextRepair(View v){
        Repair r = null;
        String brandInSpinner = (String)MainActivity.spinner.getSelectedItem();

        if(repairs == null || !brandInSpinner.equals(currentBrand)){
            repairs = dao.getAllRapairsForBrand(brandInSpinner);
            repairs1 = new ArrayList<>(Arrays.asList(repairs));
            repairsSize = repairs1.size();
            repairsPointer = repairsSize - 1;
        }

        if(repairs1.isEmpty()){
            showToast("Нет записей. Пустой массив");
        }else{
            if(repairsPointer < repairs1.size()){
                r = repairs1.get(repairsPointer);

                Log.i("R", "date value is " + r.getDate());
                Log.i("R", "manufacturer value is " + r.getManufacturer());
                Log.i("R", "partNumber value is " +  r.getPartNumber());
                Log.i("R", "description value is " + r.getDescription());
                Log.i("R", "mileage value is " + r.getMileage());
                Log.i("R", "cost value is " + r.getPartPrice());

                repairDate.setText(r.getDate());
                repairPartManufacturer.setText(r.getManufacturer());
                repairPartNumber.setText(r.getPartNumber());
                repairPartDescription.setText(r.getDescription());
                repairMileage.setText(String.valueOf(r.getMileage()));
                repairCost.setText(String.valueOf(r.getPartPrice()));

                if(repairsPointer != repairs1.size()-1){
                    repairsPointer+=1;
                }else{
                    showToast("Последнияя запись");
                }
            }
        }
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void saveRepair(View v){
        long mileage1;
        int cost1;

        //Getting data from views
        String date = repairDate.getText().toString();
        String manufacturer = repairPartManufacturer.getText().toString();
        String partNumber = repairPartNumber.getText().toString();
        String description = repairPartDescription.getText().toString();
        String mileage = repairMileage.getText().toString();
        String cost = repairCost.getText().toString();



        Log.i("R", "date value is " + date);
        Log.i("R", "manufacturer value is " + manufacturer);
        Log.i("R", "partNumber value is " +  partNumber);
        Log.i("R", "description value is " + description);
        Log.i("R", "mileage value is " + mileage);
        Log.i("R", "cost value is " + cost);

        if(date == "Дата" || manufacturer.isEmpty() || partNumber.isEmpty() || description.isEmpty() || mileage.isEmpty() || cost.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_LONG);
            toast.show();
        }else{
            mileage1 = Long.valueOf(mileage);
            cost1 = Integer.valueOf(cost);
            String carBrand = (String)MainActivity.spinner.getSelectedItem();
            Repair r = new Repair(carBrand, date, mileage1, manufacturer, partNumber, description, cost1);
            DBUtility.getAppDatabase(getApplicationContext()).getCarsDao().insertRepair(r);
            Log.i("R", "REPAIR ADDED TO DATABASE!!");
            Toast toast = Toast.makeText(getApplicationContext(), "Добавлено", Toast.LENGTH_LONG);
            toast.show();
            this.finish();
        }
    }


    class repairDateListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(RepairsCategoryActivity.this,R.style.Theme_AppCompat_Light_Dialog, new DatePickedListener(), year, month, day);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    class DatePickedListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String date = year +"/"+(month+1)+"/"+day;
            repairDate.setText(date);
        }
    }

}
