package com.example.divyanshu.vehicleregistry;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by divyanshu on 21/3/18.
 */

public class Utils {

    /** Tag for the log messages */
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Response code : " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occured");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }



    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static List<Vehicle> extractVehiclesFromJson(String vehiclesJSON) {
        // If the JSON string is empty or null, then return early.
        /*if (TextUtils.isEmpty(vehiclesJSON)) {
            return null;
        }

        ArrayList<Vehicle> vehicleList  = new ArrayList<>();
        try {
            JSONArray vehicles = new JSONArray(vehiclesJSON);


            for(int i = 0; i < vehicles.length(); i++) {
                // Extract out the users List
                JSONObject vehicleJSON = vehicles.getJSONObject(i);
                vehicleList.add(extractVehicleFromVehicleJSON(vehicleJSON));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing vehicle JSON", e);
        }
        return vehicleList;*/


        List<Vehicle> vehicles = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            vehicles = Arrays.asList(objectMapper.readValue(vehiclesJSON, Vehicle[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicles;

    }
/*

    private static Vehicle extractVehicleFromVehicleJSON(JSONObject vehicleJSON) {
        Vehicle newVehicle = new Vehicle();
        */
/*try {
            String rcValidity = vehicleJSON.getString("rcValidity");
            String insuranceValidity = vehicleJSON.getString("insuranceValidity");
            String pollutionValidity = vehicleJSON.getString("pollutionValidity");
            String owner = vehicleJSON.getString("owner");
            String registeredNo = vehicleJSON.getString("registeredNo");
            String modelNo = vehicleJSON.getString("modelNo");
            String chassisNo = vehicleJSON.getString("chassisNo");
            String engineNo = vehicleJSON.getString("engineNo");
            Date dateOfRegistration = null;
            dateOfRegistration = getDateFromString(vehicleJSON.getString("dateOfRegistration"));
            String fuelType = vehicleJSON.getString("fuelType");

            newVehicle.setRcValidity(rcValidity);
            newVehicle.setInsuranceValidity(insuranceValidity);
            newVehicle.setPollutionValidity(pollutionValidity);
            newVehicle.setOwner(owner);
            newVehicle.setRegistered_no(registeredNo);
            newVehicle.setModel_no(modelNo);
            newVehicle.setChassis_no(chassisNo);
            newVehicle.setEngine_no(engineNo);
            newVehicle.setDate_of_registration(dateOfRegistration);
            newVehicle.setFuel_type(fuelType);
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the vehicle", e);

        }*//*

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        String ur;;
        String url;
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(Constants.APP_URL +  "/queries/selectAllCarsByDealer").newBuilder();
        urlBuilder.addQueryParameter("manufacturer", "resource:com.bf.vrp.members.Manufacturer#");


        url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = null;
        Vehicle vehicle = null;
        try {
            response = call.execute();
            System.out.println();
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            vehicle = (objectMapper.readValue(responseBody, Vehicle.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicle;
    }



*/


    public static Date getDateFromString (String dateString){
        Date date = null;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = formatter.parse(dateString);
        }catch (ParseException e){
            Log.e(LOG_TAG, "problem parsing the date");
        }
        return date;
    }
}
