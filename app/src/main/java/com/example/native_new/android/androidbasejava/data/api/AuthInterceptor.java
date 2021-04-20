package com.example.native_new.android.androidbasejava.data.api;

import com.example.native_new.android.androidbasejava.data.AppPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class AuthInterceptor implements Interceptor {
    @Inject
    AppPreferences appPreferences;

    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        String isIgnore = chain.request().header("IgnoreAuth");
        if (isIgnore != null && isIgnore.equals("1")) {
            Request.Builder newRequest = chain.request().newBuilder();
            Timber.i("Ignore authentication");
            newRequest.removeHeader("IgnoreAuth");
            return chain.proceed(newRequest.build());
        }

        //Add Authentication
        Request.Builder newRequest = chain.request().newBuilder();
        String token = appPreferences.getToken();
        if (token != null && !token.equals("")) {
            Timber.i("add Authentication");
            newRequest.addHeader("Authorization", "Bearer " + token);
        }
        return chain.proceed(newRequest.build());
    }

}
