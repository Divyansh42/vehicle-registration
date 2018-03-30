package com.example.divyanshu.vehicleregistry;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class UserLoginActivity extends AppCompatActivity {
    EditText aadharNo;

    public static final String LOG_TAG = Utils.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button userLogin = findViewById(R.id.user_login);
        aadharNo = (EditText) findViewById(R.id.aadharNo);
        aadharNo.addTextChangedListener(new FourDigitCardFormatWatcher());



        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AadharVerificationAsyncTask aadharVerificationAsyncTask = new AadharVerificationAsyncTask();
                aadharVerificationAsyncTask.execute(aadharNo.getText().toString());
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    class AadharVerificationAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = " ";
            try {
                data = URLEncoder.encode("p", "UTF-8") + "=" + URLEncoder.encode("user", "UTF-8");
                data += "&" + URLEncoder.encode("aadhar", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(APP_URL +"/rest/a_login");

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

                        Intent otpIntent = new Intent(UserLoginActivity.this, OTPVerification.class);
                        startActivity(otpIntent);
                        otpIntent.putExtra("aadhaNo",params[0]);
                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Enter Valid Aadhar No.",
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

    public static class FourDigitCardFormatWatcher implements TextWatcher {

        // Change this to what you want... ' ', '-' etc..
        private static final char space = ' ';

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Remove spacing char
            if (s.length() > 0 && (s.length() % 5) == 0) {
                final char c = s.charAt(s.length() - 1);
                if (space == c) {
                    s.delete(s.length() - 1, s.length());
                }
            }
            // Insert char where needed.
            if (s.length() > 0 && (s.length() % 5) == 0) {
                char c = s.charAt(s.length() - 1);
                // Only if its a digit where there should be a space we insert a space
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }
        }
    }
}
