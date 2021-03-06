package com.example.native_new.android.androidbasejava;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.example.native_new.android.androidbasejava.crashreport.FakeCrashLibrary;
import com.example.native_new.android.androidbasejava.utils.logs.AppDebugTree;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate(); // Injection happens in super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(new AppDebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
