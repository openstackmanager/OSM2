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
        Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public class runLogin extends AsyncTask<Void,String,Void> {

         @Override
         protected Void doInBackground(Void... params) {


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://95.44.212.163:5000/v3/auth/tokens");

                //Building the json object
                String a ="{\n" +
                        "  \"auth\":{\n"+
                        "        \"identity\": {\n" +
                        "            \"methods\": [\n" +
                        "                \"password\"\n" +
                        "            ],\n" +
                        "            \"password\": {\n" +
                        "                \"user\": {\n" +
                        "                    \"id\": \"216fe186a8bc4ee2809fac384dea9fe1\",\n" +
                        "                    \"password\": \"openstack\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "     }\n" +
                        "}";
                JSONObject auth1 = new JSONObject(a);

                Log.i("TAG", "passing your data"+auth1.toString());

                StringEntity auth = new StringEntity(auth1.toString());
                auth.setContentEncoding("UTF-8");
                auth.setContentType("application/json");

                httppost.setHeader("Content-type", "application/json");
                httppost.setEntity(auth);

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Log.i("TAG", "Server response is "+response.getEntity());
                Log.i("TAG", "Server response is "+response.getStatusLine().getStatusCode()+" " +response.getStatusLine().getReasonPhrase());// Server response cannont be of type JSONObject

                //Check if response from http request is accepted and start menu activity
                if (response.getStatusLine().getStatusCode() == 201)
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
                    //use onpreexicute and on post execute to inteface with the ui thread

            }catch (IOException e) {
                Log.e("TAG", "IOException" + e.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
             return null;
         }
     }
 }
