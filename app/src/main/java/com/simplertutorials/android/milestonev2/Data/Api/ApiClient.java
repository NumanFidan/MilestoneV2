package com.simplertutorials.android.milestonev2.Data.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit =null;
    private static String POPULAR_MOVIES_BASE_URL= "https://api.themoviedb.org/";

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(POPULAR_MOVIES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();

        }
        return retrofit;
    }
}
