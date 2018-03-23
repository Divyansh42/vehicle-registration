package com.example.divyanshu.vehicleregistry;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PoliceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
