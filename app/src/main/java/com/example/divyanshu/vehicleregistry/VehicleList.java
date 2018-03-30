package com.example.divyanshu.vehicleregistry;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.divyanshu.vehicleregistry.Constants.APP_URL;
import static com.example.divyanshu.vehicleregistry.Utils.createUrl;

public class VehicleList extends AppCompatActivity{

    int aadharNo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("aadharNo")) {
                aadharNo = intentThatStartedThisActivity.getIntExtra("aadharNo", 0);
            }
        }
        VehicleAsyncTask asyncTask = new VehicleAsyncTask();
        asyncTask.execute(createUrl(APP_URL + "/rest/user/" + aadharNo + "/vehicle"));
        final ListView vehicleList = (ListView) findViewById(R.id.list);
        vehicleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent vehicleInfo = new Intent(VehicleList.this, UserActivity.class);
                Vehicle vehicle = (Vehicle) vehicleList.getItemAtPosition(position);
                vehicleInfo.putExtra("vehicleVin", vehicle.getVin());
                vehicleInfo.putExtra("aadharNo", aadharNo);
                startActivity(vehicleInfo);
            }
        });
    }

    private void updateUi(String result){
        System.out.println(result);
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            vehicles =  Utils.extractVehiclesFromJson(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        VehicleAdapter adapter = new VehicleAdapter(this, 0, vehicles );
        ListView vehicleList = (ListView) findViewById(R.id.list);
        vehicleList.setAdapter(adapter);

    }


    private  class  VehicleAsyncTask extends AsyncTask<URL, Void, String> {


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
