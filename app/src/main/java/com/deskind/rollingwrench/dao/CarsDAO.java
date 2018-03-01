package com.deskind.rollingwrench.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FluidService;
import com.deskind.rollingwrench.entities.FuelUp;
import com.deskind.rollingwrench.entities.Repair;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface  CarsDAO {
    @Insert
    public void insertNewCar(Car car);

    @Insert
    public void insertFuelUp(FuelUp fuelUp);

    @Insert
    public void insertRepair(Repair repair);

    @Insert
    public void insertFluidService(FluidService fluidService);

    @Query("SELECT * FROM 'Car' where car_brand = :car_brand")
    public Car getCar(String car_brand);

    @Query("SELECT * FROM 'FuelUp'")
    public List<FuelUp> getFuelUps();

    @Query("DELETE FROM FuelUp where fuelup_id = :id")
    public void deleteFuelUp(int id);

    @Query("SELECT car_brand FROM car")
    public String[] getAllCarBrands();

    @Query("SELECT * FROM FluidService where car_brand = :carBrand ORDER BY \"Date\" DESC")
    public List<FluidService> getAllFluidServices(String carBrand);

    @Query("SELECT * FROM Repair where CarBrand = :carBrand ORDER BY \"Пробег\" ASC")
    public Repair[] getAllRapairsForBrand(String carBrand);

    @Query("SELECT price FROM FluidService where car_brand = :carBrand")
    public int[] getFluidServicesTotalCost(String carBrand);

    @Query("DELETE from Car where car_brand = :carBrand")
    public void deleteCar(String carBrand);

    @Query("DELETE from Repair where ID = :repairId")
    public void deleteRepair (int repairId);

    @Query("DELETE from FluidService where service_id = :serviceId")
    public void deleteFluidService(int serviceId);
}
