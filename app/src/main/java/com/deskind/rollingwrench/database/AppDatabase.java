package com.deskind.rollingwrench.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.deskind.rollingwrench.dao.CarsDAO;
import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FluidService;
import com.deskind.rollingwrench.entities.FuelUp;
import com.deskind.rollingwrench.entities.Repair;

import static com.deskind.rollingwrench.activities.MainActivity.context;


@Database(version = 9, entities = {Car.class, FuelUp.class, Repair.class, FluidService.class})
public abstract class AppDatabase extends RoomDatabase {
abstract public CarsDAO getCarsDao();



}
