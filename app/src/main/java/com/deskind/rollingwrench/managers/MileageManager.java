package com.deskind.rollingwrench.managers;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deskind.rollingwrench.activities.MainActivity;
import com.rollingwrench.deskind.rollingwrench.R;

public class MileageManager {

    private static long currentMileage;

    private static AlertDialog.Builder builder;
    private static AlertDialog dialog;

    private static SharedPreferences preferences;


    public static void showCurrentMileageDialog(final Context context, LayoutInflater inflater){
        if(builder==null) {
            builder = new AlertDialog.Builder(context);
        }
        View view = (View)inflater.inflate(R.layout.dialog_current_mileage, null);

        final EditText etCurrentMileage = (EditText)view.findViewById(R.id.et_current_mileage);
        Button btnCurrentMileageDone = (Button)view.findViewById(R.id.btn_current_mileage_done);

        //Done button listener
        btnCurrentMileageDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brand = (String)MainActivity.spinner.getSelectedItem();
                long currentMileage = Long.valueOf(etCurrentMileage.getText().toString());
                MileageManager.setCurrentMileage(currentMileage);
                Toster.showToast(context, context.getString(R.string.done), Toast.LENGTH_SHORT);
                dialog.cancel();
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    public static void setCurrentMileage(long currentMileage) {
        MileageManager.currentMileage = currentMileage;
    }

    public static long getCurrentMileage() {
        return currentMileage;
    }
}
