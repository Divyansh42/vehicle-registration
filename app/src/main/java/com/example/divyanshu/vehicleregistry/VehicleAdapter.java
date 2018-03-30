package com.example.divyanshu.vehicleregistry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by divyanshu on 21/3/18.
 */

public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    public VehicleAdapter(Context context, int resource, List<Vehicle> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemListView = convertView;
        if (itemListView == null) {
            itemListView = LayoutInflater.from(getContext()).inflate(R.layout.activity_vehicle_adapter, parent, false);
        }

        Vehicle currentVehicle = getItem(position);
        String imageSource = currentVehicle.getManufacturer();


        ImageView companyImage = itemListView.findViewById(R.id.company_logo);
        Context context = companyImage.getContext();
        int id = context.getResources().getIdentifier(imageSource, "drawable", context.getPackageName());
        companyImage.setImageResource(id);

        TextView vehicleNamePlateNoView = itemListView.findViewById(R.id.name_plate_no);
        vehicleNamePlateNoView.setText(currentVehicle.getVin());

        TextView vehicleModelView = itemListView.findViewById(R.id.model_no_in_list);
        vehicleModelView.setText(currentVehicle.getVehicleDetails().getModelType());

        return itemListView;
    }
}
