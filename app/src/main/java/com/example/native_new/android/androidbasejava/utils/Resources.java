package com.example.native_new.android.androidbasejava.utils;

import androidx.annotation.Nullable;

public class Resources<T> {
    private Status status;
    private T data;
    private String message;

    public Resources(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resources<T> success(@Nullable T data) {
        return new Resources<>(Status.SUCCESS, data, null);
    }

    public static <T> Resources<T> error(String message, @Nullable T data) {
        return new Resources<>(Status.ERROR, data, message);
    }

    public static <T> Resources<T> loading(@Nullable T data) {
        return new Resources<>(Status.LOADING, data, null);
    }
}
