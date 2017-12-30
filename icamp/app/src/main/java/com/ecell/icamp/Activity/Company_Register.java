package com.ecell.icamp.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecell.icamp.Adapter.Company_Adapter;
import com.ecell.icamp.Model.Company_Profile;
import com.ecell.icamp.Model.Response_Api;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Company_Register extends AppCompatActivity {

    private EditText co_name, co_location, co_skillset,co_duration, co_stipend,co_email,co_password,co_phone;
    private Button send;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyregister);
        co_email = (EditText)findViewById(R.id.co_email);
        co_phone = (EditText)findViewById(R.id.co_phone);
        co_password = (EditText)findViewById(R.id.co_password);


        co_skillset = (EditText)findViewById(R.id.co_skillset);
        co_name = (EditText)findViewById(R.id.co_name);
        co_location = (EditText)findViewById(R.id.co_location);
        co_duration = (EditText)findViewById(R.id.co_duration);
        co_stipend = (EditText)findViewById(R.id.co_stipend);

        send = (Button)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mongo = new MongoClient("ecell.org.in", 27017);
              new connection().execute();

          }
      });
    }

    public  class connection extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                credential = MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                Log.i("constatus", "Connected");

                database = mongo.getDatabase("esummit_18");

                Log.i("databaseName", "DBName = " + database.getName().toString());

                collection = database.getCollection("company");

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                Document document = new Document("id", ts+""+(new Random().nextInt(8999)+1000))
                        .append("email", co_email.getText().toString())
                        .append("status",0)
                        .append("phone", co_phone.getText().toString())
                        .append("password", co_password.getText().toString())
                        .append("name", co_name.getText().toString())
                        .append("location", co_location.getText().toString())
                        .append("skillset", co_skillset.getText().toString())
                        .append("stipend", co_stipend.getText().toString())
                        .append("duration", co_duration.getText().toString());

                collection.insertOne(document);
                Toast.makeText(Company_Register.this,"Successfully registered",Toast.LENGTH_SHORT).show();
                Log.i("checkingregistered","success");



            }

            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }


}