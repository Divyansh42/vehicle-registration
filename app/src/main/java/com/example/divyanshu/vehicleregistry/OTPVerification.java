package com.example.divyanshu.vehicleregistry;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.divyanshu.vehicleregistry.Constants.APP_URL;

public class OTPVerification extends AppCompatActivity {


    public static final String LOG_TAG = Utils.class.getSimpleName();
    int aadharNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);


        Button otpVerifyButton = findViewById(R.id.verify_otp);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("aadharNo")) {
                aadharNo = intentThatStartedThisActivity.getIntExtra("aadharNo", 0);
            }
        }


        otpVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText otp = (EditText) findViewById(R.id.otp);
                OTPVerificationAsyncTask otpVerificationAsyncTask = new OTPVerificationAsyncTask();
                otpVerificationAsyncTask.execute(otp.getText().toString());
            }
        });
    }

    class OTPVerificationAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = " ";
            try {
                data = URLEncoder.encode("p", "UTF-8") + "=" + URLEncoder.encode("user", "UTF-8");
                data += "&" + URLEncoder.encode("otp", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(APP_URL +"/rest/verifyOTP");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println(httpURLConnection.getResponseCode());
                in.close();


                if (httpURLConnection.getResponseCode() == 200) {

                    JSONObject jsonObject = new JSONObject(line);
                    Log.v(LOG_TAG, "json object created");
                    if(((String)jsonObject.get("result")).equals("valid")){

                        Intent vehicleListIntent = new Intent(OTPVerification.this, VehicleList.class);
                        vehicleListIntent.putExtra("aadharNo",aadharNo);
                        startActivity(vehicleListIntent);
                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Login Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(LOG_TAG, "Response code : " + httpURLConnection.getResponseCode());
                }

                httpURLConnection.disconnect();

                return data.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (JSONException e) {
                e.printStackTrace();
                return  "Exception" + e.getMessage();
            }
        }



    }
}
