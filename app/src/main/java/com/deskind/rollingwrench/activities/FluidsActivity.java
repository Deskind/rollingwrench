package com.deskind.rollingwrench.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.adapters.FluidsServicesListAdapter;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.FluidService;
import com.deskind.rollingwrench.fragments.ConfirmDeleteFluidServiceFragment;
import com.deskind.rollingwrench.managers.MileageManager;
import com.deskind.rollingwrench.managers.Toster;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidsActivity extends AppCompatActivity {

    public static FluidsServicesListAdapter adapter;
    public static FluidService fluidService;

    public static final String TAG = "LIQUIDS ACTIVITY";
    private static final int DELETE_COMMAND = 1;

    final String ATTRIBUTE_DATE = "date";
    final String ATTRIBUTE_MILEAGE = "mileage";
    final String ATTRIBUTE_BRAND = "brand";
    final String ATTRIBUTE_PRICE = "price";
    final String ATTRIBUTE_DESCRIPTION = "description";
    final String ATTRIBUTE_AFTER_SERVICE = "afterService";

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

        List<FluidService> services = DBUtility.getAppDatabase(this).getCarsDao().getAllFluidServices((String)MainActivity.spinner.getSelectedItem());

        adapter = new FluidsServicesListAdapter(this, R.layout.fluids_item, services);

        lv.setAdapter(adapter);
    }

    public void     deleteFluidService(View v){
        fluidService = (FluidService)v.getTag();
//        adapter.remove(fluidService);
        ConfirmDeleteFluidServiceFragment fragment = new ConfirmDeleteFluidServiceFragment();
        fragment.show(getFragmentManager(), "DeleteFluidService");
    }

    public static void deleteServiceFromDB(Context context){
        //remove item from list view
        adapter.remove(fluidService);
        //remove entry from database
        DBUtility.getAppDatabase(context).getCarsDao().deleteFluidService(fluidService.getServiceId());
        Toster.showToast(context, "Удалено!", 1);
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
