package com.deskind.rollingwrench.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rollingwrench.deskind.rollingwrench.R;

public class FuelChooseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_choose);
    }

    public void newFuelUpClicked(View v){
        Intent intent = new Intent(this, FuelUpActicity.class);
        startActivity(intent);
    }

    public void showFueslTable(View v){
        Intent intent = new Intent(this, FuelsListActivity.class);
        startActivity(intent);
    }
}
