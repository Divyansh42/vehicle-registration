package com.example.divyanshu.vehicleregistry;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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
            itemListView = LayoutInflater.from(getContext()).inflate(R.layout.activity_car_adapter, parent, false);
        }

        Vehicle currentVehicle = getItem(position);

        TextView vehicleModelView = itemListView.findViewById(R.id.vin);
        vehicleModelView.setText(currentVehicle.getVin());

        /*TextView vehicleNoView = itemListView.findViewById(R.id.vehicle_no);
        vehicleNoView.setText(currentVehicle.getRegistered_no());*/

        return itemListView;
    }
}
