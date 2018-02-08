package com.deskind.rollingwrench.managers;


import android.content.Context;
import android.widget.Toast;

public class Toster {
    public static void showToast(Context context, String message, int time){
        Toast.makeText(context, message, time).show();
    }
}
