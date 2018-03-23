package com.example.divyanshu.vehicleregistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OTPVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);


        Button otpVerifyButton = findViewById(R.id.verify_otp);

        otpVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent usersIntent = new Intent(OTPVerification.this, VehicleList.class);
                startActivity(usersIntent);

            }
        });
    }
}
