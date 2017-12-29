package com.ecell.icamp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecell.icamp.Model.Company_Profile;
import com.ecell.icamp.Model.Response_Api;
import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Company_Register extends AppCompatActivity {

    private EditText co_name, co_location, co_duration, co_stipend;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyregister);

        co_name = (EditText)findViewById(R.id.co_name);
        co_location = (EditText)findViewById(R.id.co_location);
        co_duration = (EditText)findViewById(R.id.co_duration);
        co_stipend = (EditText)findViewById(R.id.co_stipend);

        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              send();
          }
      });
    }

    private void send() {
        Company_Profile company_profile = new Company_Profile();
        company_profile.setId(""+(new Random().nextInt(8999)+1000));
        company_profile.setName(co_name.getText().toString());
        company_profile.setLocation(co_location.getText().toString());
        company_profile.setDuration(co_duration.getText().toString());
        company_profile.setStipend(co_stipend.getText().toString());

        Resi_Api client = Service_Generator.createService(Resi_Api.class);
        Call<Response_Api> call = client.postCompanyDetails(company_profile);
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