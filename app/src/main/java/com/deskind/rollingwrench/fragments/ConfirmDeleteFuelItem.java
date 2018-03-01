package com.deskind.rollingwrench.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.deskind.rollingwrench.activities.FluidsActivity;
import com.deskind.rollingwrench.activities.FuelsListActivity;
import com.rollingwrench.deskind.rollingwrench.R;

public class ConfirmDeleteFuelItem extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.confirmDelete)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FuelsListActivity.deleteFuelUpFromDB(getActivity());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
