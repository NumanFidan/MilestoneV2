package com.simplertutorials.android.milestonev2.data.api;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class ApiClient {

    @Inject
    public  Retrofit retrofit;

    public  Retrofit getClient(){
        return retrofit;
    }
}
