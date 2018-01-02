package com.deskind.rollingwrench.database;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.Cursor;

import com.deskind.rollingwrench.entities.FuelUp;

import java.util.List;

public class DBUtility {
    //DB reference
    private static AppDatabase appDatabase = null;


    public static AppDatabase getAppDatabase(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "cars").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            return  appDatabase;
        }else {
            return appDatabase;
        }
    }

    //Getting list of fuel ups
    public static Cursor getFuelUpsList (Context context){

            return getAppDatabase(context).getCarsDao().getFuelUps();

    }

}
