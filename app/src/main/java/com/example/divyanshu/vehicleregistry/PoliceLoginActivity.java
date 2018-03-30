package com.example.divyanshu.vehicleregistry;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

import static com.example.divyanshu.vehicleregistry.Constants.APP_URL;

public class PoliceLoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = Utils.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_login_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button policeLogin = findViewById(R.id.police_login);

        policeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(LOG_TAG, "proceed Clicked");

                EditText userId = (EditText) findViewById(R.id.user_id);
                EditText password = (EditText) findViewById(R.id.password);
                System.out.println(userId.getText().toString() + password.getText().toString());
                PoliceLoginAsyncTask policeLoginAsyncTask = new PoliceLoginAsyncTask();
                policeLoginAsyncTask.execute(userId.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    class PoliceLoginAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = " ";
            try {
                data = URLEncoder.encode("p", "UTF-8") + "=" + URLEncoder.encode("police", "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }



            try {
                URL url = new URL(APP_URL +"/rest/login");

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
                    if(((String)jsonObject.get("login")).equals("successful")){

                        Intent policeIntent = new Intent(PoliceLoginActivity.this, PoliceActivity.class);
                        startActivity(policeIntent);
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
