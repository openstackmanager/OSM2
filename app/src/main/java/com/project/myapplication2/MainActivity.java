package com.project.myapplication2;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = (Button) findViewById(R.id.button1);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new runLogin().execute();
                Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public class runLogin extends AsyncTask<Void,String,Void> {

         @Override
         protected Void doInBackground(Void... params) {


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://95.44.212.163:5000/v3/auth/tokens");

                //Building the json object, nesting all the json objects into one because parsing a single string causes errors
                JSONObject auth = new JSONObject();
                JSONObject identity = new JSONObject();
                JSONObject user = new JSONObject();
                JSONObject other = new JSONObject();
                JSONObject credentials = new JSONObject();

              /*  credentials.put("name","admin");
                credentials.put("password","openstack");
                user.put("user", credentials);
                other.put("methods",["password"]){};
                other.put("password", user);
                identity.put("identity", other);
                auth.put("auth", identity);*/

                String a ="{\n" +
                        "    \"auth\": {\n" +
                        "        \"identity\": {\n" +
                        "            \"methods\": [\n" +
                        "                \"password\"\n" +
                        "            ],\n" +
                        "            \"password\": {\n" +
                        "                \"user\": {\n" +
                        "                    \"name\": \"admin\",\n" +
                        "                    \"password\": \"openstack\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                auth.put("auth",a);

                Log.i("TAG", "passing your data"+auth.toString());

                StringEntity params1 = new StringEntity(auth.toString());
                params1.setContentEncoding("UTF-8");
                params1.setContentType("application/json");

                httppost.setHeader("Content-type", "application/json");
                httppost.setEntity((params1));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Log.i("TAG", "Server response is "+response.getEntity());

                String token = response.toString();
                Log.i("TAG", "Server response is "+response.getStatusLine().getStatusCode()+" " +response.getStatusLine().getReasonPhrase());// Server response cannont be of type JSONObject

                //Check if response from http request is accepted and start menu activity
                if (true)
                {
                    HttpEntity entity = response.getEntity();
                    String re = EntityUtils.toString(entity);
                        /*
                        JSONObject jsonResponse = new JSONObject(new String(buffer));
                        JSONArray mtUsers = jsonResponse.getJSONArray("GetUserNamesResult");
                        */
                    Log.e("TAG", "Http entity : " + re);
                    callMenu();
                }
            }catch (IOException e) {
                Log.e("TAG", "IOException" + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
             return null;
         }
     }
 }
