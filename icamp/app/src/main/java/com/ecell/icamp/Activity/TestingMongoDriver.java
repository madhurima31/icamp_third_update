package com.ecell.icamp.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ecell.icamp.R;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestingMongoDriver extends AppCompatActivity {

    private ArrayList<String> listNew;
    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_mongo_driver);

        listNew = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNew);
        ListView listView = (ListView) findViewById(R.id.mongolist);
        listView.setAdapter(adapter);

        mongo = new MongoClient("ecell.org.in", 27017);


        new connection().execute();

    }

    public  class connection extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                credential = MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                Log.d("Con status", "Connected");

                database = mongo.getDatabase("esummit_18");

                Log.d("Database Name", "DBName = " + database.getName().toString());

                collection = database.getCollection("company");
       //         collection = database.getCollection("company").find({},{"name":1,"id":0});

//                Class<Document> x= collection.getDocumentClass();
//                Log.i("checkcheckcheck",x.getName());

                // Getting the iterable object
                FindIterable<Document> iterDoc = collection.find();
             //   FindIterable<Document> iterDoc = collection.find({},{"name":1,id:0});

                List<Document> lists = (List<Document>)collection.find().into(new ArrayList<Document>());
                for(Document document : lists){
//                    listNew.add(document.toString());
                    listNew.add(document.toJson());


                    JSONObject jsonObject=new JSONObject(document.toJson());
                  //  jsonObject=jsonObject.getJSONObject("name");
                    String name=jsonObject.getString("name");

                    //  jsonObject=jsonObject.getString("name");
                    Log.i("hogabe", name.toString());
//                    List<Document> fields = (List<Document>)document.get("id");
//
//                    for (Document course : fields) {
//                        Log.i("checkingstrings",( course.getString("name")
//                                ));
//
//                    }

                }
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
            adapter.notifyDataSetChanged();
        }
    }
}
