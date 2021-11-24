package com.stocktracking.app.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientClass {

    private static RetrofitClientClass instance = null;
    private ApiInterface myApi;
    //private String BASE_URL = "https://api.tickertape.in/";

    private RetrofitClientClass() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(ApiInterface.class);
    }

    public static synchronized RetrofitClientClass getInstance() {
        if (instance == null) {
            instance = new RetrofitClientClass();
        }
        return instance;
    }

    public ApiInterface getMyApi() {
        return myApi;
    }
}
