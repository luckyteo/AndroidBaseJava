package com.example.native_new.android.androidbasejava.di;

import com.example.native_new.android.androidbasejava.utils.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class SchedulersModule {

    private SchedulersModule() {
    }

    @Provides
    @Singleton
    public static SchedulerProvider providerSchedulers(){
        return SchedulerProvider.getInstance();
    }
}
