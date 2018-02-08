package com.deskind.rollingwrench.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.deskind.rollingwrench.activities.MainActivity;

@Entity (foreignKeys = @ForeignKey(entity = Car.class,
                                    parentColumns = "car_brand",
                                    childColumns = "car_brand",
                                    onDelete = ForeignKey.CASCADE))
public class FuelUp {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fuelup_id")
    public int fuelUpId;

    @ColumnInfo(name = "car_brand")
    public String carBrand;

    //YYYY-MM-DD HH:MM:SS.SSS
    public String date;

    public int liters;

    public  float cost;

    public FuelUp(String carBrand, String date, int liters, float cost) {
        this.carBrand = carBrand;
        this.date = date;
        this.liters = liters;
        this.cost = cost;
    }
}
