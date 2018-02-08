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
import com.deskind.rollingwrench.managers.MileageManager;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.List;


public class FluidsServicesListAdapter extends ArrayAdapter<FluidService> {

    private List<FluidService> items;
    private int layoutId;
    private Context context;

    public FluidsServicesListAdapter(Context context, int layoutId, List<FluidService> items){
        super(context, layoutId, items);
        this.items = items;
        this.layoutId = layoutId;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ServiceHolder holder = null;

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(layoutId, parent, false);

        holder = new ServiceHolder();

        holder.fluidService = items.get(position);

        holder.button = (ImageButton)row.findViewById(R.id.ib_delete_fluid_service);
        holder.button.setTag(holder.fluidService);

        holder.date = (TextView)row.findViewById(R.id.tvDate);
        holder.mileage = (TextView)row.findViewById(R.id.tvMileage);
        holder.brand = (TextView)row.findViewById(R.id.tvBrand);
        holder.price = (TextView)row.findViewById(R.id.tvPrice);
        holder.description = (TextView)row.findViewById(R.id.tvDescription);
        holder.afterService = (TextView)row.findViewById(R.id.tv_fluiditem_after_service);

        row.setTag(holder);

        setUpItem(holder, items.get(position));

        return row;
    }

    private void setUpItem(ServiceHolder holder, FluidService service){
        holder.date.setText(service.getDate());
        holder.mileage.setText(String.valueOf(service.getMileage()));
        holder.brand.setText(service.getCarBrand());
        holder.price.setText(String.valueOf(service.getPrice()));
        holder.description.setText(service.getDescription());
        if(MileageManager.getCurrentMileage() != 0){
            holder.afterService.setText(String.valueOf(MileageManager.getCurrentMileage()-service.getMileage()));
        }else {
            holder.afterService.setText("");
        }
    }

    public static class ServiceHolder{
        FluidService fluidService;
        TextView date, mileage, brand, price, description, afterService;
        ImageButton button;
    }
}
