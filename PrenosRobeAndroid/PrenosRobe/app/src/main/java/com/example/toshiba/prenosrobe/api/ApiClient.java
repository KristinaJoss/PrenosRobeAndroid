package com.example.toshiba.prenosrobe.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Time;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL="http://192.168.1.5:8080/";
    private static Retrofit retrofit = null;
    static private Gson gson = new GsonBuilder().registerTypeAdapter(Time.class, new TimeDeserializer()).setDateFormat("yyyy-MM-dd HH:mm").create();

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }
}
