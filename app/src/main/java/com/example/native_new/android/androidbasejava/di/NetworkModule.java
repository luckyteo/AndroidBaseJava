package com.example.native_new.android.androidbasejava.di;

import com.example.native_new.android.androidbasejava.BuildConfig;
import com.example.native_new.android.androidbasejava.data.api.ApiService;
import com.example.native_new.android.androidbasejava.data.api.ApiServiceWithAuth;
import com.example.native_new.android.androidbasejava.data.api.AuthApiService;
import com.example.native_new.android.androidbasejava.utils.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    private @interface AuthInterceptorOkHttpClient {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    private @interface BaseOkHttpClient {
    }

    private NetworkModule() {
        // nothing
    }

    @Provides
    public static OkHttpClient.Builder getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);
    }

    @Provides
    public static Retrofit retrofitBuild(OkHttpClient.Builder client) {
        return new Retrofit.Builder()
                .client(client.build())
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public static ApiServiceWithAuth apiServiceWithAuth(@AuthInterceptorOkHttpClient OkHttpClient client) {

        //add interceptor JWT
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

                .create(ApiServiceWithAuth.class);
    }

    @Provides
    @Singleton
    public static AuthApiService authApiService(@AuthInterceptorOkHttpClient OkHttpClient client) {

        //add interceptor JWT
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

                .create(AuthApiService.class);
    }
}
