package com.example.native_new.android.androidbasejava.di;

import android.app.Application;

import com.example.native_new.android.androidbasejava.data.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public static Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    public AppPreferences providesSharedPreferences(Application application) {
        return new AppPreferences(application);
    }
}
