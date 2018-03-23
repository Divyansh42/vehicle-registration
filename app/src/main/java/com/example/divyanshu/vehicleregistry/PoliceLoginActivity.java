package com.example.divyanshu.vehicleregistry;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PoliceLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_login_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditText userId = (EditText) findViewById(R.id.user_id);

        EditText password = (EditText) findViewById(R.id.password);


        Button policeLogin = findViewById(R.id.police_login);

        policeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent policeIntent = new Intent(PoliceLoginActivity.this, PoliceActivity.class);
                startActivity(policeIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
