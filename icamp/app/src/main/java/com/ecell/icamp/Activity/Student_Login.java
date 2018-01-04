package com.ecell.icamp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;
import com.mongodb.Cursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Niklaus on 26-Feb-17.
 */

public class Student_Login extends Activity {


    private Button login, register;

    private int count, verifing;

    private static SharedPreferences preferences;
    private String prefName = "MyPref";
    private static final String INDEX = "-1";
    private static final String USER = "NULL";
    //private static final String PASS = "NULL";


    EditText email,password;
    TextView error;
    String co_email,co_password,indexed;
    private MongoClient mongo;
    Cursor cursor;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;
    Document myDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.login_password);
        login = (Button)findViewById(R.id.login);
        error = (TextView)findViewById(R.id.error_message);
        register = (Button)findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mongo = new MongoClient("ecell.org.in", 27017);
                new connection().execute();


            }
        });


//        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
//        indexed = preferences.getString(INDEX, "-1");
//        username = preferences.getString(USER, "NULL");
//
//
//        if (username.equals("NULL")){
//            login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    verify();
//                }
//            });
//        } else
//            nextActivity(username, indexed);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Student_Register.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                co_email=email.getText().toString();
                co_password = password.getText().toString();
                Log.i("checkingtext",co_email);
                collection = database.getCollection("students");
                myDoc = collection.find(eq("email",co_email)).first();

                if(!(myDoc.isEmpty())){
                    JSONObject jsonObject= new JSONObject(myDoc.toJson());
                    String pass = jsonObject.getString("password");
                    Log.i("checkingpass",pass);
                    Log.i("checkingcopass",co_password);
                    Log.i("checkingboolean", String.valueOf(pass.matches(co_password)));
                    String id = jsonObject.getString("id");
                    if(pass.equals(co_password)){
                        Log.i("checkingbooleaninside", String.valueOf(pass.equals(co_password)));
                        Intent i=new Intent(getBaseContext(),Dashboard_Student.class);
                        i.putExtra("co_id",id);
                        startActivity(i);
                    }
                    else if(!(pass.equals(co_password))){
                        error.setText("incorrect password");
                    }
                }else{
                    error.setText("incorrect email id");

                }




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


//    private void verify() {
//        username = login_username.getText().toString();
//        password = login_password.getText().toString();
//
//        Resi_Api client = Service_Generator.createService(Resi_Api.class);
//        Call<ArrayList<Student_Profile>> call = client.getStudentProfile();
//        call.enqueue(new Callback<ArrayList<Student_Profile>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Student_Profile>> call, Response<ArrayList<Student_Profile>> response) {
//                if (response.code() == 200) {
//                    verifing = 0;
//                    count = 0;
//                    for(Student_Profile student : response.body()) {
//                        count++;
//                        if (username.compareTo(student.getMobile())==0){
//                            verifing++;
//                            if (verifing == 1){
//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.putString(USER, student.getMobile());
//                                editor.putString(INDEX, String.valueOf(count-1));
//                                editor.commit();
//                                nextActivity(student.getMobile(), String.valueOf(count-1));
//                            }
//                        }
//                    }
//                    if (verifing == 0)
//                        Toast.makeText(getBaseContext(), "USERNAME or PASSWORD is Incorrect", Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ArrayList<Student_Profile>> call, Throwable throwable) {
//                Toast.makeText(getBaseContext(), "No Internet Connection, please check your internet connection ", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    private void nextActivity(String str, String index) {
//        if(str != null){
//            Intent intent = new Intent(getBaseContext(), Dashboard_Student.class);
//            intent.putExtra("indexed", index);
//            startActivity(intent);
//            finish();
//        }
//    }
//
//    public static void logout(){
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(USER, "NULL");
//        editor.putString(INDEX, "-1");
//        editor.commit();
//    }
}