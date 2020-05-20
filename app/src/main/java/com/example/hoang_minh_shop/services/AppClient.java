package com.example.hoang_minh_shop.services;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {
    private final String baseUrl = "https://sukastore.000webhostapp.com/";
    private Retrofit retrofit;

    private static AppClient instance;

    private AppClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
    }

    public static AppClient getInstance() {
        if (instance == null)
            instance = new AppClient();
        return instance;
    }

    public ApiServices createRequest(){
        return retrofit.create(ApiServices.class);
    }
}
