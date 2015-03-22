package com.project.myapplication2;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
        final Button button2 = (Button) findViewById(R.id.button);
        final TextView endpoint1 =  (TextView)findViewById(R.id.editText);
        final TextView tenant1 =  (TextView)findViewById(R.id.editText2);
        final TextView username1 =  (TextView)findViewById(R.id.editText3);
        final TextView password1 =  (TextView)findViewById(R.id.editText4);

        username1.setText("216fe186a8bc4ee2809fac384dea9fe1");

        final ConnectionManager session = new ConnectionManager();
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String endpoint = endpoint1.getText().toString();
                String tenant = tenant1.getText().toString();
                String username =username1.getText().toString() ;
                String password = password1.getText().toString();

                Log.i("TAG", "passing your data" + endpoint +" "+ tenant+ " "+ username +" "+ password);

                //passing login params to Asynctask
                session.login(endpoint,tenant,username,password);
                Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_LONG).show();
                Log.e("TAG", "Http entity from main activity : " + session.getToken());

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
             session.getInstances();
             Log.e("TAG", "Http entity from main activity : " + session.getInstances());
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

        Intent intent = new Intent(this, MenuActivityNavDrawer.class);
        startActivity(intent);
    }

 }
