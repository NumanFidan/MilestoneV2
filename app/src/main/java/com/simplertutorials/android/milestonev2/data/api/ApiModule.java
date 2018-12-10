package com.simplertutorials.android.milestonev2.data.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private String BASE_URL= "https://api.themoviedb.org/";

    @Singleton
    @Provides
    public ApiService getApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    public Retrofit getRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
    }

    @Singleton
    @Provides
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }
}
