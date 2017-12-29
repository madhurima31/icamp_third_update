package com.ecell.icamp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecell.icamp.Model.Student_Profile;
import com.ecell.icamp.R;
import com.ecell.icamp.RestApi.Resi_Api;
import com.ecell.icamp.RestApi.Service_Generator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Niklaus on 26-Feb-17.
 */

public class Student_Login extends Activity {

    private EditText login_username, login_password;
    private Button login, register;
    private String username, password, indexed;
    private int count, verifing;

    private static SharedPreferences preferences;
    private String prefName = "MyPref";
    private static final String INDEX = "-1";
    private static final String USER = "NULL";
    //private static final String PASS = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_username = (EditText)findViewById(R.id.login_username);
        login_password = (EditText)findViewById(R.id.login_password);
        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        indexed = preferences.getString(INDEX, "-1");
        username = preferences.getString(USER, "NULL");
        //password = preferences.getString(PASS, "NULL");

        if (username.equals("NULL")){
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    verify();
                }
            });
        } else
            nextActivity(username, indexed);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Student_Register.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void verify() {
        username = login_username.getText().toString();
        password = login_password.getText().toString();

        Resi_Api client = Service_Generator.createService(Resi_Api.class);
        Call<ArrayList<Student_Profile>> call = client.getStudentProfile();
        call.enqueue(new Callback<ArrayList<Student_Profile>>() {
            @Override
            public void onResponse(Call<ArrayList<Student_Profile>> call, Response<ArrayList<Student_Profile>> response) {
                if (response.code() == 200) {
                    verifing = 0;
                    count = 0;
                    for(Student_Profile student : response.body()) {
                        count++;
                        if (username.compareTo(student.getMobile())==0){
                            verifing++;
                            if (verifing == 1){
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(USER, student.getMobile());
                                editor.putString(INDEX, String.valueOf(count-1));
                                editor.commit();
                                nextActivity(student.getMobile(), String.valueOf(count-1));
                            }
                        }
                    }
                    if (verifing == 0)
                        Toast.makeText(getBaseContext(), "USERNAME or PASSWORD is Incorrect", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Student_Profile>> call, Throwable throwable) {
                Toast.makeText(getBaseContext(), "No Internet Connection, please check your internet connection ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void nextActivity(String str, String index) {
        if(str != null){
            Intent intent = new Intent(getBaseContext(), Dashboard_Student.class);
            intent.putExtra("indexed", index);
            startActivity(intent);
            finish();
        }
    }

    public static void logout(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER, "NULL");
        editor.putString(INDEX, "-1");
        editor.commit();
    }
}