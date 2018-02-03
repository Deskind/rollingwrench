package com.deskind.rollingwrench.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.FluidService;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.Calendar;

public class NewFluidServiceActivity extends AppCompatActivity {

    public static TextView tvDate;
    public static EditText etMileage, etBrand, etPrice, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fluid_service);

        tvDate = (TextView) findViewById(R.id.tvDate);
        etMileage = (EditText) findViewById(R.id.etMileage);
        etBrand = (EditText) findViewById(R.id.etBrand);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDescription = (EditText) findViewById(R.id.etDescription);



    }

    public void dateClicked(View v){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    public void newFluidServiceDoneClicked(View v){
        FluidService fluidService = null;
        String date, mileage, brand, price, description;

        date = tvDate.getText().toString();
        mileage = etMileage.getText().toString();
        brand = etBrand.getText().toString();
        price = etPrice.getText().toString();
        description = etDescription.getText().toString();

        if(date.isEmpty()||mileage.isEmpty()||brand.isEmpty()||price.isEmpty()||description.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            fluidService = new FluidService();
            fluidService.setCarBrand((String)MainActivity.spinner.getSelectedItem());
            fluidService.setDate(date);
            fluidService.setMileage(Integer.valueOf(mileage));
            fluidService.setFluidBrand(brand);
            fluidService.setPrice(Integer.valueOf(price));
            fluidService.setDescription(description);

            DBUtility.getAppDatabase(this).getCarsDao().insertFluidService(fluidService);
            Toast toast = Toast.makeText(getApplicationContext(), "Готово!", Toast.LENGTH_SHORT);
            toast.show();
            this.finish();
        }


    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = year +"/"+(month+1)+"/"+day;
            tvDate.setText(date);
        }
    }

}
