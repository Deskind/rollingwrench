package com.deskind.rollingwrench.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.deskind.rollingwrench.database.AppDatabase;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FuelUp;
import com.rollingwrench.deskind.rollingwrench.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FuelUpActicity extends Activity {

    private NumberPicker litersPicker;
    private EditText fuelCost;
    private TextView fuelUpResult;
    private Button fuelDone;

    private float fuelUpCost;
    private String brand;
    private int liters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_up_acticity);

        setTitle("Заправка " + (String)MainActivity.spinner.getSelectedItem());

        //Find elements
        litersPicker = (NumberPicker)findViewById(R.id.litersPicker);
        fuelCost = (EditText)findViewById(R.id.fuelCost);
        fuelUpResult = (TextView)findViewById(R.id.fuelUpResult);
        fuelDone = (Button)findViewById(R.id.fuelDone);

        /**Set up liter picker*/
        litersPicker.setMaxValue(500);
        litersPicker.setMinValue(0);
        litersPicker.setWrapSelectorWheel(false);

        //set listener
        litersPicker.setOnScrollListener(new LitersScrollListener());

    }

    public void FuelUpDone(View v){
        AppDatabase appDatabase = DBUtility.getAppDatabase(MainActivity.context);
        Car car = appDatabase.getCarsDao().getCar(brand);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        FuelUp fuelUp = new FuelUp(brand, formattedDate, liters, fuelUpCost);
        appDatabase.getCarsDao().insertFuelUp(fuelUp);

        Log.i("FuelUP", "SUCCESSFULL FUELUP FOR " + brand);
        this.finish();

    }

    /**Object listen liters scrolling*/
    class LitersScrollListener implements NumberPicker.OnScrollListener{
        @Override
        public void onScrollStateChange(NumberPicker numberPicker, int i) {
            brand = MainActivity.spinner.getSelectedItem().toString();
            liters = numberPicker.getValue();
            fuelCost = (EditText)findViewById(R.id.fuelCost);
            String fuelCost1 = fuelCost.getText().toString();
            float fuelCost2 = Float.valueOf(fuelCost1);
            float fuelCost3 = fuelCost2 * liters;
            fuelUpCost = fuelCost3;
            fuelUpResult.setText(String.format("%.2f", fuelCost3));

        }
    }

}
