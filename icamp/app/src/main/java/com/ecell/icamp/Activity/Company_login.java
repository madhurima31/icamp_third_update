package com.ecell.icamp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.R;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class Company_login extends AppCompatActivity {
    Button register,login;
    EditText email,password;
    TextView error;
    String co_email,co_password;
    private MongoClient mongo;
    Cursor cursor;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;
    Document myDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        register = (Button)findViewById(R.id.register);
        login = (Button)findViewById(R.id.login);
        email = (EditText)findViewById(R.id.company_email);
        password = (EditText)findViewById(R.id.company_password);
        error = (TextView)findViewById(R.id.error_message);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mongo = new MongoClient("ecell.org.in", 27017);
                new connection().execute();


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Company_Register.class);
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
                collection = database.getCollection("company");
                myDoc = collection.find(eq("email",co_email)).first();
                Log.i("checkinglastime",myDoc.toJson());
                Log.i("checkinglastime", ((String.valueOf( myDoc.size()))));

                if(!(myDoc.isEmpty())){
                    JSONObject jsonObject= new JSONObject(myDoc.toJson());
                    String pass = jsonObject.getString("password");
                    Log.i("checkingpass",pass);
                    Log.i("checkingcopass",co_password);
                    Log.i("checkingboolean", String.valueOf(pass.matches(co_password)));
                    String id = jsonObject.getString("id");
                    if(pass.equals(co_password)){
                        Log.i("checkingbooleaninside", String.valueOf(pass.equals(co_password)));
                        Intent i=new Intent(getBaseContext(),Dashboard_Company.class);
                        i.putExtra("co_id",id);
                        startActivity(i);
                    }
                    else if(!(pass.equals(co_password))){
                        error.setText("incorrect password");
                    }
                }else{
                    error.setText("incorrect email id");

                }



//                BasicDBObject query = new BasicDBObject();
//                query.put("email", co_email);
//
//                FindIterable<Document> q = collection.find(query);
//                Log.i("checkingresult", String.valueOf(q.limit(0)));



//                Document user = null;
//                user = collection.find(eq("email", co_email)).first();
//                Log.i("checkingdoc", user.toJson());



//                Bson filter = new Document("email",co_email);
//                Document user = collection.find().filter(filter).first();
//                Log.i("checkingresult",user.toJson());

 //               Document u = collection.find(query).first();

               // FindIterable<Document> q = collection.find(query);

            //    String js = q.first().toJson();
//                List<Document> lists = (List<Document>)collection.find(query);
//
//                Document d = lists.get(0);
//                Log.i("checkingtojson",d.toJson());


             //   Log.i("checkingresult",u.toJson());
               // MongoCursor<Document> cur = collection.find(query).iterator();
             //   Log.i("checkingresultjson",cur.next().toJson());


         //       Log.i("checkingresult", String.valueOf(q.limit(0)));
          //      cursor = (Cursor) collection.find(query);

//                Log.i("checkingresultcursor",cursor.toString());
//                try {
//                    while(cursor.hasNext()) {
//
//                    }
//                } finally {
//                    cursor.close();
//                }



//
//                BasicDBObject query = new BasicDBObject();
//
//                query.put("email", co_email);
//                cur = coll.find(query);
//
//                while(cur.hasNext()) {
//                    System.out.println(cur.next());
//                }

//                DBObject doc = collection.findOne();
//                System.out.println(dbObject);
                Toast.makeText(Company_login.this,"Successfully registered",Toast.LENGTH_SHORT).show();
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
