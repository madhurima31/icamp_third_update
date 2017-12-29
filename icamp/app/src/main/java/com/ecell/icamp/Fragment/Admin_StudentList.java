package com.ecell.icamp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Adapter.Student_Adapter;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Admin_StudentList extends Fragment {

    private TextView title, st_registered;
    private List<String> li_id, li_name;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recycler, container,false);

        title = (TextView)view.findViewById(R.id.title);
        st_registered = (TextView)view.findViewById(R.id.st_registered);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);

        title.setText("Student List");

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        show();

        return view;
    }

    private void show() {
        Resi_Api client = Service_Generator.createService(Resi_Api.class);
        Call<ArrayList<Student_Profile>> call = client.getStudentProfile();
        call.enqueue(new Callback<ArrayList<Student_Profile>>() {
            @Override
            public void onResponse(Call<ArrayList<Student_Profile>> call, Response<ArrayList<Student_Profile>> response) {
                if (response.code() == 200) {
                    li_id = new ArrayList<>();
                    li_name = new ArrayList<>();
                    for(Student_Profile student : response.body()) {
                        li_id.add(student.getId());
                        li_name.add(student.getName());
                    }
                    adapter = new Student_Adapter(li_id, li_name);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Student_Profile>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No Internet Connection, please check your internet connection ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
