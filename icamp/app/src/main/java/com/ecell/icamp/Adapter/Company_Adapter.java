package com.ecell.icamp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Activity.Dashboard_Admin;
import com.ecell.icamp.Model.Response_Api;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1505560 on 09-Dec-17.
 */

public class Company_Adapter extends RecyclerView.Adapter<Company_Adapter.ViewHolder> {

    private LinearLayout co_card;
    private TextView co_skillset, co_name, co_duration,co_stipend,co_location;
    private Button co_bookmark;
    private List<String> li_id,li_skillset, li_name,li_duration,li_stipend,li_location;

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

    public Company_Adapter(List<String> li_id, List<String> li_name, List<String>li_duration,List<String> li_skillset, List<String> li_location, List<String>li_stipend) {
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
//                Student_Profile student_profile = new Student_Profile();
//                String x= "test";
//                student_profile.setName(x);
//
//                Resi_Api client = Service_Generator.createService(Resi_Api.class);
//                Call<Student_Profile> call = client.putstudent(x);
//                call.enqueue(new Callback<Student_Profile>() {
//                    @Override
//                    public void onResponse(Call<Student_Profile> call, Response<Student_Profile> response) {
//                        if (response.code() == 200) {
//                            Student_Profile oauthResponse = response.body();
//                            Log.i("testingput","yes");
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<Student_Profile> call, Throwable throwable) {
//
//                    }
//                });



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
}
