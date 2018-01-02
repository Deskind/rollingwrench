package com.deskind.rollingwrench.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FuelUp;

import java.util.ArrayList;

@Dao
public interface  CarsDAO {
    @Insert
    public void insertNewCar(Car car);

    @Insert
    public void insertFuelUp(FuelUp fuelUp);

    @Query("SELECT * FROM 'Car' where car_brand = :car_brand")
    public Car getCar(String car_brand);

    @Query("SELECT * FROM 'FuelUp'")
    public Cursor getFuelUps();

    @Query("SELECT car_brand FROM car")
    public String[] getAllCarBrands();


}
