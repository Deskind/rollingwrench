package com.deskind.rollingwrench.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.deskind.rollingwrench.dao.CarsDAO;
import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FuelUp;


@Database(version = 4, entities = {Car.class, FuelUp.class})
public abstract class AppDatabase extends RoomDatabase {
abstract public CarsDAO getCarsDao();
}
