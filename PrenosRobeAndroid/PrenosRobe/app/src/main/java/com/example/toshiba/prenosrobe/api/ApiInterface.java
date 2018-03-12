package com.example.toshiba.prenosrobe.api;

import com.example.toshiba.prenosrobe.data.ClaimerOffer;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("user/register")
    Call<User> register(@Body User userDto);

    @GET("driverOffers")
    Call<List<DriverOffer>> getAllDriverOffers();

    @GET("myDriverOffers")
    Call<List<DriverOffer>> getMyDriverOffers(@Header("token") String token);

    @GET("myClaimerOffers")
    Call<List<ClaimerOffer>> getMyClaimerOffers(@Header("token") String token);
}
