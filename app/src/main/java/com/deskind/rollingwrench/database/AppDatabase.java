package com.deskind.rollingwrench.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.deskind.rollingwrench.dao.CarsDAO;
import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FuelUp;
import com.deskind.rollingwrench.entities.Repair;


@Database(version = 7, entities = {Car.class, FuelUp.class, Repair.class})
public abstract class AppDatabase extends RoomDatabase {
abstract public CarsDAO getCarsDao();
}
