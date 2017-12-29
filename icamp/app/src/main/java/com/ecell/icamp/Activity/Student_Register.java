package com.ecell.icamp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecell.icamp.Model.Response_Api;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_Register extends AppCompatActivity {

    private EditText st_name, st_gender, st_email, st_mobile, st_college, st_branch, st_roll, st_year, st_graduate;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentregister);

        st_name = (EditText)findViewById(R.id.st_name);
        st_gender = (EditText)findViewById(R.id.st_gender);
        st_email = (EditText)findViewById(R.id.st_email);
        st_mobile = (EditText)findViewById(R.id.st_mobile);
        st_college = (EditText)findViewById(R.id.st_college);
        st_branch = (EditText)findViewById(R.id.st_branch);
        st_roll = (EditText)findViewById(R.id.st_roll);
        st_year = (EditText)findViewById(R.id.st_year);
        st_graduate = (EditText)findViewById(R.id.st_graduate);

        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              send();
          }
      });
    }

    private void send() {
        Student_Profile student_profile = new Student_Profile();
        student_profile.setId(""+(new Random().nextInt(8999)+1000));
        student_profile.setName(st_name.getText().toString());
        student_profile.setGender(st_gender.getText().toString());
        student_profile.setEmail(st_email.getText().toString());
        student_profile.setMobile(st_mobile.getText().toString());
        student_profile.setCollege(st_college.getText().toString());
        student_profile.setBranch(st_branch.getText().toString());
        student_profile.setRoll(st_roll.getText().toString());
        student_profile.setYear(st_year.getText().toString());
        student_profile.setGraduate(st_graduate.getText().toString());

        Resi_Api client = Service_Generator.createService(Resi_Api.class);
        Call<Response_Api> call = client.postStudentDetails(student_profile);
        call.enqueue(new Callback<Response_Api>() {
            @Override
            public void onResponse(Call<Response_Api> call, Response<Response_Api> response) {
                if (response.code() == 200) {
                    Response_Api oauthResponse = response.body();
                }
            }
            @Override
            public void onFailure(Call<Response_Api> call, Throwable throwable) {
                Toast.makeText(getBaseContext(), "No Internet Connection, please check your internet connection ", Toast.LENGTH_LONG).show();
            }
        });
    }
}