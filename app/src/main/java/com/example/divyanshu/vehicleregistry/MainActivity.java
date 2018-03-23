package com.example.divyanshu.vehicleregistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton user = findViewById(R.id.user);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent userLoginIntent = new Intent(MainActivity.this, UserLoginActivity.class);
                startActivity(userLoginIntent);

            }
        });

        ImageButton police = findViewById(R.id.police);

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent policeLoginIntent = new Intent(MainActivity.this, PoliceLoginActivity.class);
                startActivity(policeLoginIntent);

            }
        });

    }
}
