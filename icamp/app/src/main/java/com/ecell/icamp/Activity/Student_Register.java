package com.ecell.icamp.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecell.icamp.Model.Response_Api;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_Register extends AppCompatActivity {

    private EditText st_name, st_gender, st_email, st_mobile, st_college, st_branch, st_roll, st_year, st_graduate;
    private Button send;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentregister);

        st_name = (EditText)findViewById(R.id.st_name);
        st_gender = (EditText)findViewById(R.id.st_gender);
        st_email = (EditText)findViewById(R.id.st_email);
        st_mobile = (EditText)findViewById(R.id.st_mobile);
        st_college = (EditText)findViewById(R.id.st_college);
        st_branch = (EditText)findViewById(R.id.st_branch);
        st_roll = (EditText)findViewById(R.id.st_roll);
        st_year = (EditText)findViewById(R.id.st_year);
        st_graduate = (EditText)findViewById(R.id.st_graduate);

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

                collection = database.getCollection("students");

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                Document document = new Document("id", ts+""+(new Random().nextInt(8999)+1000))
                        .append("email", st_email.getText().toString())
                        .append("phone", st_mobile.getText().toString())
                   //     .append("password", co_password.getText().toString())
                        .append("gender",st_gender.getText().toString())
                        .append("name", st_name.getText().toString())
                        .append("branch",st_branch.getText().toString())
                        .append("roll", st_roll.getText().toString())
                        .append("year",st_year.getText().toString())
                        .append("graduate",st_graduate.getText().toString());

                collection.insertOne(document);
                Toast.makeText(Student_Register.this,"Successfully registered",Toast.LENGTH_SHORT).show();
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