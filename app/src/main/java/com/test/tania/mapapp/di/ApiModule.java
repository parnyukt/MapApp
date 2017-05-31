package com.test.tania.mapapp.di;

import com.test.tania.mapapp.api.IMapApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Provides
    @Singleton
    public OkHttpClient.Builder okHttpClient() {

        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    public Retrofit retrofit(OkHttpClient.Builder okHttpClientBuilder) {

        String baseUrl = "http://www.json-generator.com/api/json/get/";

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public IMapApi restApi(Retrofit retrofit) {
        return retrofit.create(IMapApi.class);
    }
}
