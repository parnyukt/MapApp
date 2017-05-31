package com.test.tania.mapapp;

import android.app.Application;

import com.test.tania.mapapp.api.IMapApi;
import com.test.tania.mapapp.di.ApiModule;
import com.test.tania.mapapp.di.AppComponent;
import com.test.tania.mapapp.di.AppModule;
import com.test.tania.mapapp.di.DaggerAppComponent;

import javax.inject.Inject;


public class MapApp extends Application {
    private static MapApp sInstance;
    private static AppComponent appComponent;
    @Inject
    IMapApi mapApi;

    public MapApp() {
        super();
        sInstance = this;
    }

    public static MapApp get() {
        return sInstance;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .apiModule(new ApiModule())
                    .appModule(new AppModule())
                    .build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = getAppComponent();

    }

}
