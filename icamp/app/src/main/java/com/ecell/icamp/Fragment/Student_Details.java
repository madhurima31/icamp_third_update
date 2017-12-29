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

public class Student_Details extends Fragment {

    private TextView st_id, st_name, st_gender, st_email, st_mobile, st_college, st_branch, st_roll, st_year, st_graduate;
    private Button st_logout;
    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studentdetails, container,false);

        st_id = (TextView)view.findViewById(R.id.st_id);
        st_name = (TextView)view.findViewById(R.id.st_name);
        st_gender = (TextView)view.findViewById(R.id.st_gender);
        st_email = (TextView)view.findViewById(R.id.st_email);
        st_mobile = (TextView)view.findViewById(R.id.st_mobile);
        st_college = (TextView)view.findViewById(R.id.st_college);
        st_branch = (TextView)view.findViewById(R.id.st_branch);
        st_roll = (TextView)view.findViewById(R.id.st_roll);
        st_year = (TextView)view.findViewById(R.id.st_year);
        st_graduate = (TextView)view.findViewById(R.id.st_graduate);
        st_logout = (Button)view.findViewById(R.id.st_logout);

        show();

        st_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student_Login.logout();
                Intent intent = new Intent(getContext(), Activity_Landing.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void show() {
        Resi_Api client = Service_Generator.createService(Resi_Api.class);
        Call<ArrayList<Student_Profile>> call = client.getStudentProfile();
        call.enqueue(new Callback<ArrayList<Student_Profile>>() {
            @Override
            public void onResponse(Call<ArrayList<Student_Profile>> call, Response<ArrayList<Student_Profile>> response) {
                index = Dashboard_Student.index;
                if (response.code() == 200) {
                    st_id.setText("   " + response.body().get(index).getId());
                    st_name.setText(response.body().get(index).getName());
                    st_gender.setText("   " + response.body().get(index).getGender());
                    st_email.setText("   " + response.body().get(index).getEmail());
                    st_mobile.setText("   " + response.body().get(index).getMobile());
                    st_college.setText("   " + response.body().get(index).getCollege());
                    st_branch.setText("   " + response.body().get(index).getBranch());
                    st_roll.setText("   " + response.body().get(index).getRoll());
                    st_year.setText("   " + response.body().get(index).getYear());
                    st_graduate.setText("   " + response.body().get(index).getGraduate());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Student_Profile>> call, Throwable throwable) {
                Toast.makeText(getContext(), "No Internet Connection, please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}