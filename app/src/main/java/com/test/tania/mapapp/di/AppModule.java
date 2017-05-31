package com.test.tania.mapapp.di;

import android.content.Context;

import com.test.tania.mapapp.MapApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    public Context context() {
        return MapApp.get();
    }

}
