package com.example.native_new.android.androidbasejava.utils.schedulers;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Scheduler;

/**
 * Allow providing different types of {@link Scheduler}s.
 */
public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
