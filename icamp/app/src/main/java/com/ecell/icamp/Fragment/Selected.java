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

import com.ecell.icamp.Adapter.Company_Adapter;
import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * A simple {@link Fragment} subclass.
 */
public class Selected extends Fragment {
    private TextView title, st_registered;
    private List<String> li_id, li_name;
    private List<String>li_skillset,li_duration,li_stipend,li_location;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button bookmark;
    FindIterable<Document> iterDoc ;
    List<Document> lists;
    JSONObject jsonObject;
    String name;

    String skillset;
    String duration;
    String stipend;
    String location;
//    private ArrayList<Document> listdocument;
    private TextView co_skillset, co_name, co_duration,co_stipend,co_location;
    private Button co_bookmark;
    ArrayList<Document> listdocument = new ArrayList<>();


    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection,collection_students;
    private MongoCredential credential;
    Document doc_students,doc_selected1,doc_selected2,doc_selected3;
    String id,selected_company1,selected_company2,selected_company3;


    public Selected() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_companies, container, false);

        st_registered = (TextView)view.findViewById(R.id.st_registered);
     //   recyclerView = (RecyclerView)view.findViewById(R.id.recycler);


        co_skillset = (TextView)view.findViewById(R.id.co_skillset);
        co_name = (TextView)view.findViewById(R.id.company_name);
        co_location = (TextView) view.findViewById(R.id.co_location);
        co_duration = (TextView)view.findViewById(R.id.co_duration);
        co_stipend = (TextView)view.findViewById(R.id.co_stipend);
        co_bookmark = (Button)view.findViewById(R.id.bookmark);

//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(view.getContext());
//        recyclerView.setLayoutManager(layoutManager);
        mongo = new MongoClient("ecell.org.in", 27017);
        new connection().execute();

        //   show();
        return view;
    }

    public static Selected newInstance() {
        Selected fragment = new Selected();
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
                collection_students = database.getCollection("students");

                iterDoc = collection.find();
                doc_students = collection_students.find(eq("email","mmmm4")).first();
                //   FindIterable<Document> iterDoc = collection.find({},{"name":1,id:0});
                jsonObject=new JSONObject(doc_students.toJson());
                selected_company1=jsonObject.getString("selected_company1");
                selected_company2=jsonObject.getString("selected_company2");
                selected_company3=jsonObject.getString("selected_company3");

                Log.i("selectcheck",selected_company1);

                doc_selected1 = collection.find(eq("id",selected_company1)).first();
                doc_selected2 = collection.find(eq("id",selected_company2)).first();
                doc_selected3 = collection.find(eq("id",selected_company3)).first();

                Log.i("selectcheckdoc1",doc_selected1.toJson());
                Log.i("selectcheckdoc2",doc_selected2.toJson());
                Log.i("selectcheckdoc3",doc_selected3.toJson());


                if(!(selected_company1.isEmpty())) {
                    listdocument.add(doc_selected1);

                }
                if(!(selected_company2.isEmpty())) {
                    listdocument.add(doc_selected2);

                }
                if(!(selected_company3.isEmpty())) {
                    listdocument.add(doc_selected3);

                }


                Log.i("selectcheckplease", String.valueOf(listdocument.get(0)));
//                lists = (List<Document>)collection.find().into(new ArrayList<Document>());
//                lists = (List<Document>)collection.find().into(listdocument);
                li_name = new ArrayList<>();
                li_id = new ArrayList<>();
                li_skillset = new ArrayList<>();
                li_duration = new ArrayList<>();
                li_stipend = new ArrayList<>();
                li_location = new ArrayList<>();


//                jsonObject=new JSONObject(doc_selected1.toJson());
//                jsonObject=new JSONObject(doc_selected1.toJson());
//
//                name=jsonObject.getString("name");
//                id=jsonObject.getString("id");
//                skillset=jsonObject.getString("skillset");
//                duration=jsonObject.getString("duration");
//                stipend=jsonObject.getString("stipend");
//                location=jsonObject.getString("location");
//
//                Log.i("hogabei", id);
//                Log.i("hogaben", name);
//                Log.i("hogabes", stipend);
////
//                co_duration.setText(duration);
//                co_location.setText(location);
//                co_name.setText(name);
//                co_skillset.setText(skillset);
//                co_stipend.setText(stipend);


                for(Document document : listdocument){



                    jsonObject=new JSONObject(document.toJson());

                    name=jsonObject.getString("name");
                    id=jsonObject.getString("id");
                    skillset=jsonObject.getString("skillset");
                    duration=jsonObject.getString("duration");
                    stipend=jsonObject.getString("stipend");
                    location=jsonObject.getString("location");
//
//                    co_duration.setText(duration);
//                    co_location.setText(location);
//                    co_name.setText(name);
//                    co_skillset.setText(skillset);
//                    co_stipend.setText(stipend);




                    Log.i("hogabe", id);
                    li_name.add(name);
                    li_id.add(id);
                    li_location.add(location);
                    li_stipend.add(stipend);
                    li_duration.add(duration);
                    li_skillset.add(skillset);


                }
//                adapter = new Company_Adapter(getContext(),li_id, li_name, li_duration,li_skillset,li_location,li_stipend);
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
   //         recyclerView.setAdapter(adapter);
   //         adapter.notifyDataSetChanged();
        }
    }
}
