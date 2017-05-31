package com.test.tania.mapapp.di;

import com.test.tania.mapapp.MapsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, AppModule.class})
public interface AppComponent {
    void inject(MapsActivity activity);
}
