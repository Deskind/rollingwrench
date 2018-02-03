package com.deskind.rollingwrench.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.FluidService;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FluidsActivity extends AppCompatActivity {

    public static final String TAG = "LIQUIDS ACTIVITY";

    final String ATTRIBUTE_DATE = "date";
    final String ATTRIBUTE_MILEAGE = "mileage";
    final String ATTRIBUTE_BRAND = "brand";
    final String ATTRIBUTE_PRICE = "price";
    final String ATTRIBUTE_DESCRIPTION = "description";

    public ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluids);

        setTitle((String)MainActivity.spinner.getSelectedItem()+" (Замена жидкостей)");

        lv = (ListView)findViewById(R.id.lvFluidsServices);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Map<String, Object> service;

        FluidService[] services = DBUtility.getAppDatabase(this).getCarsDao().getAllFluidServices((String)MainActivity.spinner.getSelectedItem());
        Log.i("SERVICES", String.valueOf(services.length));

        ArrayList<Map<String, Object>> servicesMap = new ArrayList<>(services.length);

        for(int i = 0; i < services.length; i++){
            service = new HashMap<>();
            service.put(ATTRIBUTE_DATE, services[i].getDate());
            service.put(ATTRIBUTE_MILEAGE, services[i].getMileage());
            service.put(ATTRIBUTE_BRAND, services[i].getFluidBrand());
            service.put(ATTRIBUTE_PRICE, services[i].getPrice());
            service.put(ATTRIBUTE_DESCRIPTION, services[i].getDescription());

            servicesMap.add(service);

            String [] from = {ATTRIBUTE_DATE, ATTRIBUTE_MILEAGE, ATTRIBUTE_BRAND, ATTRIBUTE_PRICE, ATTRIBUTE_DESCRIPTION};
            int [] to = {R.id.tvDate, R.id.tvMileage, R.id.tvBrand, R.id.tvPrice, R.id.tvDescription};

            SimpleAdapter adapter = new SimpleAdapter(this, servicesMap, R.layout.fluids_item, from, to);

            lv.setAdapter(adapter);
        }
    }


    public void newFluidServiceClicked(View v){
        Intent intent = new Intent(this, NewFluidServiceActivity.class);
        startActivity(intent);
    }

    public void addFluidServiceClicked(View v){
        DBUtility.getAppDatabase(this).getCarsDao().insertFluidService(
                new FluidService("Audi a4 b7","2018/1/24", 222222, "Febi", 20, "Coolant"));
        Toast toast = Toast.makeText(getApplicationContext(), "Готово!", Toast.LENGTH_SHORT);
        toast.show();
    }

}
