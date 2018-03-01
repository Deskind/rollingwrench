package com.deskind.rollingwrench.activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.deskind.rollingwrench.adapters.FuelsListAdapter;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.FuelUp;
import com.deskind.rollingwrench.fragments.ConfirmDeleteFuelItem;
import com.deskind.rollingwrench.managers.Toster;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.List;

public class FuelsListActivity extends Activity {

    public static FuelsListAdapter adapter;
    public static FuelUp fuelUp;
    public ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuels_list);

        lv = findViewById(R.id.lv_fuelItems);

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<FuelUp> fuelUps = DBUtility.getAppDatabase(this).getCarsDao().getFuelUps();

        adapter = new FuelsListAdapter(this, R.layout.fuel_item, fuelUps);

        Log.i("FUELUPS IN ADAPTER", String.valueOf(adapter.getCount()));

        lv.setAdapter(adapter);

    }

    public void deleteFuelUpItem(View v){
        fuelUp = (FuelUp)v.getTag();
        ConfirmDeleteFuelItem fragment = new ConfirmDeleteFuelItem();
        fragment.show(getFragmentManager(), "DeleteFuelUpItemFragment");
    }

    public static void deleteFuelUpFromDB(Context context){
        adapter.remove(fuelUp);
        DBUtility.getAppDatabase(context).getCarsDao().deleteFuelUp(fuelUp.getFuelUpId());
        Toster.showToast(context, "Удалено!", 1);
    }
}
