package com.ecell.icamp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Activity.Activity_Landing;
import com.ecell.icamp.Activity.Dashboard_Student;
import com.ecell.icamp.Activity.Student_Login;
import com.ecell.icamp.Model.Company_Profile;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Company_Details extends Fragment {

    private TextView co_id, co_name, co_location, co_duration, co_stipend;
    private Button co_logout;
    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companydetails, container,false);

        co_id = (TextView)view.findViewById(R.id.co_id);
        co_name = (TextView)view.findViewById(R.id.co_name);
        co_location = (TextView)view.findViewById(R.id.co_location);
        co_duration = (TextView)view.findViewById(R.id.co_duration);
        co_stipend = (TextView)view.findViewById(R.id.co_stipend);
        co_logout = (Button)view.findViewById(R.id.co_logout);

        show();

//        co_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Student_Login.logout();
//                Intent intent = new Intent(getContext(), Activity_Landing.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

    private void show() {
        Resi_Api client = Service_Generator.createService(Resi_Api.class);
        Call<ArrayList<Company_Profile>> call = client.getCompanyProfile();
        call.enqueue(new Callback<ArrayList<Company_Profile>>() {
            @Override
            public void onResponse(Call<ArrayList<Company_Profile>> call, Response<ArrayList<Company_Profile>> response) {
                index = 4;
                if (response.code() == 200) {
                    co_id.setText("   " + response.body().get(index).getId());
                    co_name.setText(response.body().get(index).getName());
                    co_location.setText("   " + response.body().get(index).getLocation());
                    co_duration.setText("   " + response.body().get(index).getDuration());
                    co_stipend.setText("   " + response.body().get(index).getStipend());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Company_Profile>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No Internet Connection, please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}