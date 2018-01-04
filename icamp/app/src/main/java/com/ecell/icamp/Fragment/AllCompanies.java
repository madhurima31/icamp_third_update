package com.ecell.icamp.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Activity.TestingMongoDriver;
import com.ecell.icamp.Adapter.Company_Adapter;
import com.ecell.icamp.Adapter.Student_Adapter;
import com.ecell.icamp.Model.Company_Profile;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCompanies extends Fragment {
    private TextView title, st_registered;
    private List<String> li_id, li_name;
    private List<String>li_skillset,li_duration,li_stipend,li_location;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button bookmark;
    FindIterable<Document> iterDoc;
    List<Document> lists;
    JSONObject jsonObject;
    String name;
    String id;
    String skillset;
    String duration;
    String stipend;
    String location;


    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;

    public AllCompanies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_companies, container, false);

        st_registered = (TextView)view.findViewById(R.id.st_registered);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);




        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mongo = new MongoClient("ecell.org.in", 27017);
        new connection().execute();

     //   show();
        return view;
    }

    public static AllCompanies newInstance() {
        AllCompanies fragment = new AllCompanies();
        return fragment;
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

                 iterDoc = collection.find();
                //   FindIterable<Document> iterDoc = collection.find({},{"name":1,id:0});

                 lists = (List<Document>)collection.find().into(new ArrayList<Document>());
                li_name = new ArrayList<>();
                li_id = new ArrayList<>();
                li_skillset = new ArrayList<>();
                li_duration = new ArrayList<>();
                li_stipend = new ArrayList<>();
                li_location = new ArrayList<>();


                for(Document document : lists){



                     jsonObject=new JSONObject(document.toJson());

                    name=jsonObject.getString("name");
                     id=jsonObject.getString("id");
                     skillset=jsonObject.getString("skillset");
                     duration=jsonObject.getString("duration");
                     stipend=jsonObject.getString("stipend");
                     location=jsonObject.getString("location");



                    li_name.add(name);
                    li_id.add(id);
                    li_location.add(location);
                    li_stipend.add(stipend);
                    li_duration.add(duration);
                    li_skillset.add(skillset);


                }
                adapter = new Company_Adapter(getContext(),li_id, li_name, li_duration,li_skillset,li_location,li_stipend);
//                recyclerView.setAdapter(adapter);
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
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


}
