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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int  position;
        ListView list = (ListView) findViewById(R.id.list);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("vehicleVin")) {
                position = intentThatStartedThisActivity.getIntExtra(Intent.EXTRA_TEXT, 0);
                String vin = intentThatStartedThisActivity.getStringExtra("vehicleVin");
               /* GifView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);*/
                VehicleInfoAsyncTask vehicleInfoAsyncTask = new VehicleInfoAsyncTask();
                vehicleInfoAsyncTask.execute(Utils.createUrl(Constants.APP_URL + "/rest/user/8523/vehicle/"+vin));

            }
        }



        ImageView rcValidityIcon = (ImageView)findViewById(R.id.rc_validity_icon);
        TextView rcValidity = (TextView)findViewById(R.id.rc_validity);

        ImageView insuranceValidityIcon = (ImageView) findViewById(R.id.insurance_validity_icon);
        TextView insuranceValidity = (TextView) findViewById(R.id.insurance_validity);

        ImageView pollutionValidityIcon = (ImageView) findViewById(R.id.pollution_validity_icon);
        TextView pollutionValidity = (TextView) findViewById(R.id.pollution_validity);

        TextView owner = (TextView) findViewById(R.id.owner_name);
        TextView registeredNo = (TextView) findViewById(R.id.registered_no);
        TextView modelNo = (TextView) findViewById(R.id.model_no);
        TextView chassisNo = (TextView) findViewById(R.id.chassis_no);
        TextView engineNo = (TextView) findViewById(R.id.engine_no);
        TextView dateOfRegistration = (TextView) findViewById(R.id.date_of_registration);
        TextView fuelType = (TextView) findViewById(R.id.fuel_type);


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


