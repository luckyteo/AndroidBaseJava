package com.example.native_new.android.androidbasejava;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate(); // Injection happens in super.onCreate()
    }
}
