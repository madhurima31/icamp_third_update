package com.ecell.icamp.RestApi;

import com.ecell.icamp.Model.Company_Profile;
import com.ecell.icamp.Model.Response_Api;
import com.ecell.icamp.Model.Student_Profile;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by 1505560 on 07-Dec-17.
 */

public interface Resi_Api {

    @GET("/api/1/databases/mydb/collections/Students/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv")
    Call<ArrayList<Student_Profile>> getStudentProfile();

    @POST("/api/1/databases/mydb/collections/Students/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv")
    Call<Response_Api> postStudentDetails(@Body Student_Profile student_profile);


    //@GET("/api/1/databases/mydb/collections/Students/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv")
    //Call<ArrayList<Student_Profile>> getStudentProfile(@Query("apikey")String apikey);

    @GET("/api/1/databases/mydb/collections/Companies/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv")
    Call<ArrayList<Company_Profile>> getCompanyProfile();

    @POST("/api/1/databases/mydb/collections/Companies/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv")
    Call<Response_Api> postCompanyDetails(@Body Company_Profile company_profile);

    @Multipart
    @PUT("/api/1/databases/mydb/collections/Students/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv")
    Call<Student_Profile> putteststudent(@Part("name") String name);

    @Multipart
    @PUT("/api/1/databases/mydb/collections/Students/?apiKey=3I-Dj5d07Prh1AprHaS_UpmfD5MghRxv&q={\"_id\":5a29aa8cf36d281a4226e50c}")
    Call<Student_Profile> putstudent(@Part("name") String name);


}
