package com.example.divyanshu.vehicleregistry;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    ImageView rcValidityIcon;
    TextView rcValidity;
    ImageView insuranceValidityIcon;
    TextView insuranceValidity;
    ImageView pollutionValidityIcon;
    TextView pollutionValidity;
    TextView owner;
    TextView registeredNo;
    TextView modelNo;
    TextView chassisNo;
    TextView engineNo;
    TextView dateOfRegistration;
    TextView fuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView list = (ListView) findViewById(R.id.list);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("vehicleVin")) {
                int aadharNo = intentThatStartedThisActivity.getIntExtra("aadharNo", 0);
                String vin = intentThatStartedThisActivity.getStringExtra("vehicleVin");
               /* GifView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);*/
                VehicleInfoAsyncTask vehicleInfoAsyncTask = new VehicleInfoAsyncTask();
                vehicleInfoAsyncTask.execute(Utils.createUrl(Constants.APP_URL + "/rest/user/" + aadharNo + "/vehicle/" +vin));

            }
        }


        rcValidityIcon = (ImageView)findViewById(R.id.rc_validity_icon);
        rcValidity = (TextView)findViewById(R.id.rc_validity);

        insuranceValidityIcon = (ImageView) findViewById(R.id.insurance_validity_icon);
        insuranceValidity = (TextView) findViewById(R.id.insurance_validity);

        pollutionValidityIcon = (ImageView) findViewById(R.id.pollution_validity_icon);
        pollutionValidity = (TextView) findViewById(R.id.pollution_validity);

        owner = (TextView) findViewById(R.id.owner_name);
        registeredNo = (TextView) findViewById(R.id.registered_no);
        modelNo = (TextView) findViewById(R.id.model_no);
        chassisNo = (TextView) findViewById(R.id.chassis_no);
        engineNo = (TextView) findViewById(R.id.engine_no);
        dateOfRegistration = (TextView) findViewById(R.id.date_of_registration);
        fuelType = (TextView) findViewById(R.id.fuel_type);


    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void updateUi(String result){
        System.out.println(result);
        Vehicle vehicle = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            vehicle = (objectMapper.readValue(result, Vehicle.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView registeredNo = (TextView) findViewById(R.id.registered_no);
        registeredNo.setText(vehicle.getVin());

        switch (Integer.parseInt(vehicle.getVehicleDetails().getRcValidity())){

            case Constants.ACTIVE:

                rcValidityIcon.setImageResource(R.drawable.green_tick);
                rcValidity.setText(R.string.rc_valid);
                break;

            case Constants.EXPIRING_SOON:

                rcValidityIcon.setImageResource((R.drawable.orange_tick));
                rcValidity.setText(R.string.rc_expiring_soon);
                break;

            case Constants.EXPIRED:

                rcValidityIcon.setImageResource(R.drawable.red_icon);
                rcValidity.setText(R.string.rc_expired);
                break;

        }

        switch (Integer.parseInt(vehicle.getVehicleDetails().getInsuranceValidity())){

            case Constants.ACTIVE:

                insuranceValidityIcon.setImageResource(R.drawable.green_tick);
                insuranceValidity.setText(R.string.insurance_valid);
                break;

            case Constants.EXPIRING_SOON:

                insuranceValidityIcon.setImageResource((R.drawable.orange_tick));
                insuranceValidity.setText(R.string.insurance_expiring_soon);
                break;

            case Constants.EXPIRED:

                insuranceValidityIcon.setImageResource(R.drawable.red_icon);
                insuranceValidity.setText(R.string.insurance_expired);
                break;

        }

        switch (Integer.parseInt(vehicle.getVehicleDetails().getPollutionValidity())){

            case Constants.ACTIVE:

                pollutionValidityIcon.setImageResource(R.drawable.green_tick);
                pollutionValidity.setText(R.string.pollution_valid);
                break;

            case Constants.EXPIRING_SOON:

                pollutionValidityIcon.setImageResource((R.drawable.orange_tick));
                pollutionValidity.setText(R.string.pollution_expiring_soon);
                break;

            case Constants.EXPIRED:

                pollutionValidityIcon.setImageResource(R.drawable.red_icon);
                pollutionValidity.setText(R.string.pollution_expired);
                break;

        }


    }


    private  class  VehicleInfoAsyncTask extends AsyncTask<URL, Void, String> {


        protected String doInBackground(URL...urls){
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            String result = null;
            try {
                result = Utils.makeHttpRequest(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result){

            if (result == null) {
                return;
            }
            updateUi(result);
        }

    }
}


