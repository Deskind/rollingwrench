package com.deskind.rollingwrench.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.dao.CarsDAO;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.Repair;
import com.deskind.rollingwrench.managers.MileageManager;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class RepairsCategoryActivity extends AppCompatActivity {

    public static long currentMileage;

    ImageButton previousRepair, nextRepair, saveRepair;
    TextView repairDate, tvAfterRepair1, tvAfterRepair2;
    EditText repairMileage, repairPartManufacturer, repairPartNumber, repairPartDescription, repairCost;
    Button deleteRepairButton;

    CarsDAO dao;
    Repair [] repairs = null;
    ArrayList<Repair> repairs1 = null;
    int repairsSize;
    int repairsPointer;

    String currentBrand;

    /** This is the "new entry" flag
        The variable is indicator for showing user fields for adding new repair
        Value becomes true when clicking on the "next repair" button after showing last entry in repairs
        Value becones false when clicking on the "previous repair" button
     */
     boolean nextIsNew = false;

     //The reference to current repair object
     Repair r = null;



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
        deleteRepairButton = (Button)findViewById(R.id.delete_repair_button);


        repairDate = (TextView)findViewById(R.id.repairDate);
        tvAfterRepair1 = (TextView)findViewById(R.id.tvAfterRepair1);
        tvAfterRepair2 = (TextView)findViewById(R.id.tvAfterRepair2);

        repairMileage = (EditText)findViewById(R.id.repairMileage);
        repairPartManufacturer = (EditText)findViewById(R.id.repairPartManufacturer);
        repairPartNumber = (EditText)findViewById(R.id.repairPartNumber);
        repairPartDescription = (EditText)findViewById(R.id.repairPartDescription);
        repairCost = (EditText)findViewById(R.id.repairCost);

        //Hiding "next repair" button
        nextRepair.setVisibility(View.INVISIBLE);
        //Hiding "delete repair" button
        deleteRepairButton.setVisibility(View.INVISIBLE);
        //Hiding "after repair" text views
        showAfterRepair(false);


        //Set listeners
        repairDate.setOnClickListener(new repairDateListener());

        currentMileage = MileageManager.getCurrentMileage();

    }

    @Override
    protected void onResume(){
        super.onResume();

        currentBrand = (String)MainActivity.spinner.getSelectedItem();
    }

    public void showPreviousRepair(View v){

        String brandInSpinner = (String)MainActivity.spinner.getSelectedItem();

        //Reset "new entry" flag
        nextIsNew = false;

        //Show "next repair" button
        nextRepair.setVisibility(View.VISIBLE);
        deleteRepairButton.setVisibility(View.VISIBLE);

        if(repairs == null || !brandInSpinner.equals(currentBrand)){
            repairs = dao.getAllRapairsForBrand(brandInSpinner);
            repairs1 = new ArrayList<>(Arrays.asList(repairs));
            repairsSize = repairs1.size();
            repairsPointer = repairsSize - 1;
        }

        if(repairs1.isEmpty()){
            showToast("Нет записей");
            deleteRepairButton.setVisibility(View.INVISIBLE);
            showAfterRepair(false);
        }else{
            if(repairsPointer >= 0){
                r = repairs1.get(repairsPointer);

                repairDate.setText(r.getDate());
                repairPartManufacturer.setText(r.getManufacturer());
                repairPartNumber.setText(r.getPartNumber());
                repairPartDescription.setText(r.getDescription());
                repairMileage.setText(String.valueOf(r.getMileage()));
                repairCost.setText(String.valueOf(r.getPartPrice()));

                //SetUp after repair text view
                long mileage = r.getMileage();
                if(currentMileage > 0){
                    tvAfterRepair2.setText(String.valueOf(currentMileage-mileage));
                }

                saveRepair.setVisibility(View.GONE);
                showAfterRepair(true);

                if(repairsPointer != 0){
                    repairsPointer-=1;
                }else{
                    showToast("Последнияя запись");
                }
            }
        }

    }

    public void showNextRepair(View v){
        String brandInSpinner = (String)MainActivity.spinner.getSelectedItem();


        if(repairs == null || !brandInSpinner.equals(currentBrand)){
            repairs = dao.getAllRapairsForBrand(brandInSpinner);
            repairs1 = new ArrayList<>(Arrays.asList(repairs));
            repairsSize = repairs1.size();
            repairsPointer = repairsSize - 1;
        }

        if(nextIsNew){
            Toast.makeText(this, "Заполните все поля, что бы добавить новую запись о ремонте и нажмите сохранить", Toast.LENGTH_LONG).show();
            saveRepair.setVisibility(View.VISIBLE);
            deleteRepairButton.setVisibility(View.INVISIBLE);
            showAfterRepair(false);
            repairDate.setText("");
            repairDate.setHint("Дата");
            repairPartManufacturer.setText("");
            repairPartNumber.setText("");
            repairPartDescription.setText("");
            repairMileage.setText("");
            repairCost.setText("");


        }else {
            if (repairs1.isEmpty()) {
                showToast("Нет записей");
            } else {
                if (repairsPointer < repairs1.size()) {
                    r = repairs1.get(repairsPointer);

                    repairDate.setText(r.getDate());
                    repairPartManufacturer.setText(r.getManufacturer());
                    repairPartNumber.setText(r.getPartNumber());
                    repairPartDescription.setText(r.getDescription());
                    repairMileage.setText(String.valueOf(r.getMileage()));
                    repairCost.setText(String.valueOf(r.getPartPrice()));

                    long mileage = r.getMileage();
                    if(currentMileage > 0){
                        tvAfterRepair2.setText(String.valueOf(currentMileage-mileage));
                    }

                    if (repairsPointer != repairs1.size() - 1) {
                        repairsPointer += 1;
                    } else {
                        showToast("Последнияя запись");
                        nextIsNew = true;
                    }
                }
            }
        }
    }

    public void onDeleteRepair(View v){
        DBUtility.getAppDatabase(this).getCarsDao().deleteRepair(r.getRepairId());
        Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        this.finish();
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

    private void showAfterRepair(Boolean toShow){
        if(toShow == false){
            tvAfterRepair1.setVisibility(View.INVISIBLE);
            tvAfterRepair2.setVisibility(View.INVISIBLE);
        }else{
            tvAfterRepair1.setVisibility(View.VISIBLE);
            tvAfterRepair2.setVisibility(View.VISIBLE);
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
            String date = year +"-"+(month+1)+"-"+day;
            repairDate.setText(date);
        }
    }

}
