package com.example.divyanshu.vehicleregistry;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url, String request, Vehicle vehicle) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000/* milliseconds */);
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setConnectTimeout(15000 /*milliseconds*/ );
            urlConnection.setRequestMethod(request);
            JSONObject postData = new JSONObject();
            if(vehicle != null){
                try {
                    postData.put("owner", vehicle.getOwner());
                    postData.put("registeredNo",vehicle.getRegistered_no());
                    postData.put("modelNo", vehicle.getModel_no());
                    postData.put("chassisNo", vehicle.getChassis_no());
                    postData.put("engineNo", vehicle.getEngine_no());
                    postData.put("dateOfRegistration", vehicle.getDate_of_registration().getTime());
                    postData.put("fuelType", vehicle.getFuel_type());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the user JSON results.", e);
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
    private static String readFromStream(InputStream inputStream) throws IOException {
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
        if (TextUtils.isEmpty(vehiclesJSON)) {
            return null;
        }

        ArrayList<Vehicle> userList  = new ArrayList<>();
        try {
            JSONArray vehicles = new JSONArray(vehiclesJSON);


            for(int i = 0; i < vehicles.length(); i++) {
                // Extract out the users List
                JSONObject vehicleJSON = vehicles.getJSONObject(i);
                userList.add(extractVehicleFromUserJSON(vehicleJSON));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing user JSON", e);
        }
        return userList;
    }

    private static Vehicle extractVehicleFromUserJSON(JSONObject vehicleJSON){
        Vehicle newVehicle = new Vehicle();
        try {
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
            Log.e(LOG_TAG, "Problem parsing the user", e);
        }
        return newVehicle;

    }




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
