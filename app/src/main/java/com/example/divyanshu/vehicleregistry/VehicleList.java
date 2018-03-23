package com.example.divyanshu.vehicleregistry;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.divyanshu.vehicleregistry.Constants.APP_URL;
import static com.example.divyanshu.vehicleregistry.Utils.createUrl;

public class VehicleList extends AppCompatActivity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        VehicleAsyncTask asyncTask = new VehicleAsyncTask();
        asyncTask.execute(createUrl(APP_URL + "/users/"));
    }

    private void updateUi(String result){
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            vehicles =  Utils.extractVehiclesFromJson(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        VehicleAdapter adapter = new VehicleAdapter(this, 0, vehicles );
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent vehicleInfo = new Intent(VehicleList.this, UserActivity.class);
                startActivity(vehicleInfo);
            }
        });
    }


    private  class  VehicleAsyncTask extends AsyncTask<URL, Void, String> {


        protected String doInBackground(URL...urls){
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            String result = null;
            try {
                result = Utils.makeHttpRequest (urls[0], "GET", null);
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
