package com.example.divyanshu.vehicleregistry;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button userLogin = findViewById(R.id.user_login);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent userIntent = new Intent(UserLoginActivity.this, OTPVerification.class);
                startActivity(userIntent);

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
