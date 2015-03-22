package com.project.myapplication2;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Brian on 18/03/2015.
 */
public class ConnectionManager {
    String token;
    String tempEndpoint;
    HttpClientManager client = new HttpClientManager();
    public void login(String... params){

        String endpoint = params[0];
        String tenant = params[1];
        String username = params[2];
        String password = params[3];

        tempEndpoint = endpoint;

        client.execute(endpoint,tenant,username,password);

    }

    public String getToken(){
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                token = client.getToken();
                Log.e("TAG", "Http entity : " + token);
            }
        }.start();
        return token;
    }

    public String getInstances(){
        final String[] instanceList = {null};
        new AsyncTask<String,String,String>(){

            @Override
            protected String doInBackground(String[] params) {

                String temp = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://"+tempEndpoint+":5000/v3/instances");
                    JSONObject token = new JSONObject(getToken());
                    StringEntity request = new StringEntity(token.toString());
                    request.setContentEncoding("UTF-8");
                    request.setContentType("application/json");

                    httppost.setHeader("X-auth-token", "application/json");
                    httppost.setEntity(request);
                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                    HttpEntity entity = response.getEntity();

                    temp = EntityUtils.toString(entity);

                    Log.e("TAG", "Instances : " + temp);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                instanceList[0] = temp;
                return temp;
            }
        };
        return instanceList[0];
    }

    public  void getImages(){

    }

    public void getVolumes(){
        
    }
}
