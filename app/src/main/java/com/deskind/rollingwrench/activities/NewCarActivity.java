package com.deskind.rollingwrench.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.deskind.rollingwrench.database.AppDatabase;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.Car;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewCarActivity extends Activity {

    private EditText brandName;
    private Button saveCarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);

        brandName = (EditText) findViewById(R.id.NewCarName);
    }

    public void AddNewCar(View v){
        Car car = new Car();
        car.setCarBrand(brandName.getText().toString());
        AppDatabase db = DBUtility.getAppDatabase(MainActivity.context);
        String [] arr = db.getCarsDao().getAllCarBrands();
        //If cars table is empty no need to check
        if (arr.length == 0){
            db.getCarsDao().insertNewCar(car);
            Log.i("DB", "NEW CAR SUCCESSFULLY ADDED IN THE DATABSE");
        //Checking on existense
        }else{
            List<String> list = Arrays.asList(arr);
            boolean isContains = list.contains(car.getCarBrand());
            if(isContains){
                Log.i("DB", "SORRY BUT CAR WITH SUCH NAME ALREADY EXISTS IN THE DATABASE");
            }else{
                db.getCarsDao().insertNewCar(car);
                Log.i("DB", "NEW CAR SUCCESSFULLY ADDED IN THE DATABSE");
            }
        }
    }
}
