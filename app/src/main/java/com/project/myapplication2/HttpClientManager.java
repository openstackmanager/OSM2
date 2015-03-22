package com.project.myapplication2;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Brian on 18/03/2015.
 */
public class HttpClientManager extends AsyncTask<String,String,String>{
    String token = null;
    @Override
    protected String doInBackground(String... params) {
        connect(params);
        return  getToken();
    }

    public String connect(String... params){

        String endpoint = params[0];
        String tenant = params[1];
        String username = params[2];
        String password = params[3];


        try {

            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://95.44.212.163:5000/v3/auth/tokens");
            HttpPost httppost = new HttpPost("http://"+endpoint+":5000/v3/auth/tokens");

            //Building the json object
            String a = "{\n" +
                    "  \"auth\":{\n" +
                    "        \"identity\": {\n" +
                    "            \"methods\": [\n" +
                    "                \"password\"\n" +
                    "            ],\n" +
                    "            \"password\": {\n" +
                    "                \"user\": {\n" +
                   // "                    \"id\": \"216fe186a8bc4ee2809fac384dea9fe1\",\n" +
                    "                    \"id\": "+username+",\n" +
                    "                    \"password\": "+password+"\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }\n" +
                    "     }\n" +
                    "}";
            JSONObject auth1 = new JSONObject(a);

            Log.i("TAG", "passing your data" + auth1.toString());

            StringEntity auth = new StringEntity(auth1.toString());
            auth.setContentEncoding("UTF-8");
            auth.setContentType("application/json");

            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(auth);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            Log.i("TAG", "Server response is " + response.getEntity());
            Log.i("TAG", "Server response is " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());// Server response cannont be of type JSONObject

            //Check if response from http request is accepted and start menu activity
            if (response.getStatusLine().getStatusCode() == 201) {
                HttpEntity entity = response.getEntity();
                //passing response to token String
                String temp = EntityUtils.toString(entity);
                setToken(temp);
            }
            //use onpreexicute and on post execute to inteface with the ui thread

        } catch (IOException e) {
            Log.e("TAG", "IOException" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public String setToken(String temp){
        this.token = temp;
        return null;
    }

    public String getToken(){
        return this.token;
    }
}
