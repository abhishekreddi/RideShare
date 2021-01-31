package com.rideshare.api;



import com.rideshare.models.CarDetailsPojo;
import com.rideshare.models.EditProfilePojo;
import com.rideshare.models.ResponseData;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {


    @Multipart
    @POST("rideshare/register.php")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

    @Multipart
    @POST("rideshare/addcar.php")
    Call<ResponseData> addcar(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );


    @GET("rideshare/getmycars.php?")
    Call<List<CarDetailsPojo>> getmycars(@Query("email") String email);



    @GET("/rideshare/login.php?")
    Call<ResponseData> userLogin(
            @Query("email") String email,
            @Query("password") String password);



    @GET("/rideshare/myprofile.php?")
    Call<List<EditProfilePojo>> editProfile(
            @Query("email") String email);

    @GET("rideshare/updateprofile.php")
    Call<ResponseData> updateProfile(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("gender") String gender,
            @Query("password") String password);


    @GET("rideshare/user_registration.php")
    Call<ResponseData> postaride(
            @Query("from") String name,
            @Query("to") String phonenumber,
            @Query("typeofvehicle") String email,
            @Query("noofseats") String username,
            @Query("amount") String amount,
            @Query("vehicleid") String vehicleid);

    @GET("rideshare/forgotPassword.php")
    Call<ResponseData> forgotPassword
            (

                    @Query("emailid") String emailid
            );




}
