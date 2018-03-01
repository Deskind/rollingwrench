package com.deskind.rollingwrench.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.rollingwrench.deskind.rollingwrench.R;

public class Tokenizer {
    private static SharedPreferences pref;
    private static String currencyToken;

    public Tokenizer (){}

    public static String getToken(Activity activity){
        if(pref == null){
            pref = activity.getSharedPreferences(activity.getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
            currencyToken = pref.getString("currency_token", "");
            return currencyToken;
        }else {
            return currencyToken;
        }

    }

    public static void setTokenizer(String s){
        currencyToken = s;
    }
}
