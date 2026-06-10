package com.example.aplicatibid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit(){
        Gson gson = new GsonBuilder()
                .create();
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.106:8080/").addConverterFactory(GsonConverterFactory.create(gson))
        .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

