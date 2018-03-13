package com.example.toshiba.prenosrobe.api;

import com.example.toshiba.prenosrobe.data.ClaimerOffer;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.Language;
import com.example.toshiba.prenosrobe.data.OfferStatus;
import com.example.toshiba.prenosrobe.data.User;
import com.example.toshiba.prenosrobe.data.UserVehicle;
import com.example.toshiba.prenosrobe.data.VehicleType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {

    @POST("user/register")
    Call<User> register(@Body User userDto);

    @POST("user/login")
    Call<User> login(@Body User userDto);

    @GET("driverOffers")
    Call<List<DriverOffer>> getAllDriverOffers();

    @GET("myDriverOffers")
    Call<List<DriverOffer>> getMyDriverOffers(@Header("token") String token);

    @GET("myClaimerOffers")
    Call<List<ClaimerOffer>> getMyClaimerOffers(@Header("token") String token);

    @GET("vehicleTypes")
    Call<List<VehicleType>> getAllVehicleTypes(@Header("token") String token);

    @GET("userVehicle/{registrationNumber}")
    Call<UserVehicle> getUserVehicleByRegistrationNumber(@Header("token") String token, @Path("registrationNumber") String registrationNumber);

    @GET("offerStatuses")
    Call<List<OfferStatus>> getAllOfferStatuses();

    @GET("languages")
    Call<List<Language>> getAllLanguages();

    @POST("driverOffer/add")
    Call<DriverOffer> addDriverOffer(@Header("token") String token, @Body DriverOffer driverOffer);
}
