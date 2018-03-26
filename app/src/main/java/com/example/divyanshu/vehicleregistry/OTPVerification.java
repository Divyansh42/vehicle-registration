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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.divyanshu.vehicleregistry.Constants.APP_URL;

public class OTPVerification extends AppCompatActivity {


    public static final String LOG_TAG = Utils.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);


        Button otpVerifyButton = findViewById(R.id.verify_otp);


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
            String jsonResponse="";
            String otp = params[0];
            StringBuilder data = new StringBuilder();
            int temp;
            System.out.println(otp);
            try {
                URL url = new URL(APP_URL +"/rest/verifyOTP?p=user&otp=8521");
                //String urlParams = "p =" + "police" + "&username=" + userId + "&password=" + password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                /*OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();*/
               /* DataOutputStream wr = new DataOutputStream( httpURLConnection.getOutputStream()) ;
                wr.write(urlParams.getBytes());
                wr.close();
*/
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
              /*  while ((temp = is.read()) != -1) {
                    data.append((char) temp);
                }

                is.close();
                System.out.println(httpURLConnection.getErrorStream());

*/              String x = in.readLine();
                System.out.println(x);


                if (httpURLConnection.getResponseCode() == 200) {

                    JSONObject jsonObject = new JSONObject(x);
                    Log.v(LOG_TAG, "json object created");
                    if(((String)jsonObject.get("result")).equals("valid")){

                        Intent otpIntent = new Intent(OTPVerification.this, VehicleList.class);
                        startActivity(otpIntent);
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
