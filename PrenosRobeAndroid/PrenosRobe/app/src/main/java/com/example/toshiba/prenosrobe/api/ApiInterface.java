package com.example.toshiba.prenosrobe.api;

import com.example.toshiba.prenosrobe.data.ClaimerOffer;
import com.example.toshiba.prenosrobe.data.DriverOffer;
import com.example.toshiba.prenosrobe.data.Language;
import com.example.toshiba.prenosrobe.data.OfferStatus;
import com.example.toshiba.prenosrobe.data.User;
import com.example.toshiba.prenosrobe.data.UserVehicle;
import com.example.toshiba.prenosrobe.data.VehicleType;
import com.example.toshiba.prenosrobe.dto.HomeSearchDto;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface
{
    @POST("api/user/register")
    Call<RestRespondeDto<User>> register(@Body User userDto);

    @POST("api/user/login")
    Call<RestRespondeDto<User>> login(@Body User userDto);

    @POST("api/user/logout")
    Call<Void> logout(@Header("token") String token);

    @GET("api/driverOffers")
    Call<RestRespondeDto<List<DriverOffer>>> getAllDriverOffers();

    @GET("api/myDriverOffers")
    Call<RestRespondeDto<List<DriverOffer>>> getMyDriverOffers(@Header("token") String token);

    @GET("api/myClaimerOffers")
    Call<RestRespondeDto<List<ClaimerOffer>>> getMyClaimerOffers(@Header("token") String token);

    @GET("api/vehicleTypes")
    Call<RestRespondeDto<List<VehicleType>>> getAllVehicleTypes(@Header("token") String token);

    @GET("api/userVehicle/{registrationNumber}")
    Call<RestRespondeDto<UserVehicle>> getUserVehicleByRegistrationNumber(@Header("token") String token, @Path("registrationNumber") String registrationNumber);

    @GET("api/offerStatuses")
    Call<RestRespondeDto<List<OfferStatus>>> getAllOfferStatuses();

    @GET("api/languages")
    Call<RestRespondeDto<List<Language>>> getAllLanguages();

    @POST("api/driverOffer/add")
    Call<RestRespondeDto<DriverOffer>> addDriverOffer(@Header("token") String token, @Body DriverOffer driverOffer);

    @POST("api/driverOffer/homeSearch")
    Call<RestRespondeDto<List<DriverOffer>>> getDriverOffersByLocationAndDate(@Body HomeSearchDto homeSearchDto);
}
