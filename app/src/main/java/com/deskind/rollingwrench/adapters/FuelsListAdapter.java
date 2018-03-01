package com.deskind.rollingwrench.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.deskind.rollingwrench.entities.FluidService;
import com.deskind.rollingwrench.entities.FuelUp;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.List;

public class FuelsListAdapter extends ArrayAdapter<FuelUp> {
    private List<FuelUp> items;
    private int layoutId;
    private Context context;

    public FuelsListAdapter(Context context, int layoutId, List<FuelUp> items){
        super(context, layoutId, items);
        this.items = items;
        this.layoutId = layoutId;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        FuelUpHolder holder = null;

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(layoutId, parent, false);

        holder = new FuelUpHolder();

        holder.fuelUp = items.get(position);

        holder.delButton = row.findViewById(R.id.btn_fuel_item_del);
        holder.delButton.setTag(holder.fuelUp);

        holder.itemBrand = (TextView)row.findViewById(R.id.tv_fuel_item_car_brand);
        holder.itemResultPrice = (TextView)row.findViewById(R.id.tv_fuelUp_result_price);
        holder.itemDate = (TextView)row.findViewById(R.id.tv_fuel_item_date);

        row.setTag(holder);

        setUpItem(holder, items.get(position));

        return row;
    }

    public void setUpItem(FuelUpHolder holder, FuelUp fuelUp){
        holder.itemBrand.setText(fuelUp.getCarBrand());
        holder.itemResultPrice.setText(String.format("%.2f", fuelUp.getCost()));
        holder.itemDate.setText(fuelUp.getDate());
    }

    public static class FuelUpHolder{
        FuelUp fuelUp;
        TextView itemBrand, itemResultPrice, itemDate;
        Button delButton;
    }
}
