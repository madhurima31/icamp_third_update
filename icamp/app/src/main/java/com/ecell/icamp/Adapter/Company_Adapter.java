package com.ecell.icamp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecell.icamp.Activity.Dashboard_Admin;
import com.ecell.icamp.R;
import com.mongodb.Cursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by 1505560 on 09-Dec-17.
 */

public class Company_Adapter extends RecyclerView.Adapter<Company_Adapter.ViewHolder> {

    private LinearLayout co_card;
    private TextView co_skillset, co_name, co_duration,co_stipend,co_location;
    private Button co_bookmark;
    private List<String> li_id,li_skillset, li_name,li_duration,li_stipend,li_location;

    private MongoClient mongo;
    Cursor cursor;
    private MongoDatabase database;
    private MongoCollection<Document> collection,collectioncompany;
    private MongoCredential credential;
    Document myDoc;
    FindIterable<Document> iterDoc,iterDoc_company;
    List<Document> lists;
    JSONObject jsonObject;
    Context context;

    String id,selected_company1,selected_company2,selected_company3;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            co_card = (LinearLayout)view.findViewById(R.id.company_card);
            co_skillset = (TextView)view.findViewById(R.id.co_skillset);
            co_name = (TextView)view.findViewById(R.id.company_name);
            co_location = (TextView) view.findViewById(R.id.co_location);
            co_duration = (TextView)view.findViewById(R.id.co_duration);
            co_stipend = (TextView)view.findViewById(R.id.co_stipend);
            co_bookmark = (Button)view.findViewById(R.id.bookmark);
        }
    }

    public Company_Adapter(Context context,List<String> li_id, List<String> li_name, List<String>li_duration,List<String> li_skillset, List<String> li_location, List<String>li_stipend) {
        this.context=context;
        this.li_id = li_id;

        this.li_name = li_name;
        this.li_skillset = li_skillset;
        this.li_stipend = li_stipend;
        this.li_duration = li_duration;
        this.li_location = li_location;
    }

    @Override
    public Company_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_company, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        co_skillset.setText(li_skillset.get(position));
        co_name.setText(li_name.get(position));
        co_location.setText(li_location.get(position));
        co_stipend.setText(li_stipend.get(position));
        co_duration.setText(li_duration.get(position));


        co_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mongo = new MongoClient("ecell.org.in", 27017);
               // new connection().execute(position);
                connection task = new connection();
                task.company_id= li_id.get(position);
                task.c = context;

                task.execute();



            }
        });

        co_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard_Admin.fragmentChange(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return li_id.size();
       // return 3;
    }

    public  class connection extends AsyncTask<String , Void, String> {


        public String company_id;
        public Context c;


        public connection() {

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                credential = MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                Log.d("Con status", "Connected");

                database = mongo.getDatabase("esummit_18");

                Log.d("Database Name", "DBName = " + database.getName().toString());

                collection = database.getCollection("students");
                collectioncompany = database.getCollection("company");

                iterDoc = collection.find();
                iterDoc_company = collectioncompany.find();
                //   FindIterable<Document> iterDoc = collection.find({},{"name":1,id:0});

//student doc
//                myDoc = collection.find(eq("email","mmmm4")).first();

                Log.i("checkinglastime",myDoc.toJson());

                jsonObject=new JSONObject(myDoc.toJson());
                selected_company1=jsonObject.getString("selected_company1");
                selected_company2=jsonObject.getString("selected_company2");
                selected_company3=jsonObject.getString("selected_company3");



                Log.i("checkingemptiness1", String.valueOf(selected_company1.isEmpty()));
                Log.i("checkingemptiness11", String.valueOf(!(selected_company1.isEmpty())));
                Log.i("checkingemptiness2", String.valueOf(selected_company2.isEmpty()));
                Log.i("checkingcompanyname", company_id);
                Log.i("checkingemptiness222", String.valueOf(!(selected_company1.isEmpty()) && selected_company2.isEmpty()));
                Log.i("checkingemptiness333", String.valueOf(!(selected_company1.isEmpty()) && !(selected_company2.isEmpty()) && selected_company3.isEmpty()));

                if(selected_company1.isEmpty()){
                 //   myDoc.remove("selected_company1");

                    collection.updateOne(Filters.eq("email", "mmmm4"), Updates.set("selected_company1", company_id));
                  //  myDoc.append("selected_company1",company_id);
//                    Bson filter = Filters.eq("email", "mmmm4");
//                    Bson updates = Updates.set("selected_company1", company_id);
//                    collection.findOneAndUpdate(filter, updates);
                }

                else if(!(selected_company1.isEmpty()) && selected_company2.isEmpty()){
                    collection.updateOne(Filters.eq("email", "mmmm4"), Updates.set("selected_company2", company_id));
                }
                else if(!(selected_company1.isEmpty()) && !(selected_company2.isEmpty()) && selected_company3.isEmpty()){
                    collection.updateOne(Filters.eq("email", "mmmm4"), Updates.set("selected_company3", company_id));
                }

                else if(!selected_company3.isEmpty()){
                    Log.i("checkingdialog","yes");
                    new AlertDialog.Builder(c)
                            .setMessage("You can select maximum 3 companies.")
                            .setNegativeButton("OK", null)
                            .create().show();

                }




//                List<Document> bookmarklist = new ArrayList<>();
//                bookmarklist.add(myDoc);

              //  bookmarklist.add(company.getId());


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
