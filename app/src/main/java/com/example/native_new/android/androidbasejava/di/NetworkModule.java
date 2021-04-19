package com.example.native_new.android.androidbasejava.di;

import android.app.Application;

import com.example.native_new.android.androidbasejava.BuildConfig;
import com.example.native_new.android.androidbasejava.data.api.ApiAuth;
import com.example.native_new.android.androidbasejava.data.api.ApiService;
import com.example.native_new.android.androidbasejava.data.api.AuthInterceptor;
import com.example.native_new.android.androidbasejava.data.api.ConnectivityInterceptor;
import com.example.native_new.android.androidbasejava.utils.Constants;

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

    private NetworkModule() {
        // nothing
    }

    ////////////////////////
    // CREATE CLIENT     //
    //////////////////////
    @Provides
    @Singleton
    public static OkHttpClient.Builder getClient(Application application) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        AuthInterceptor authInterceptor = new AuthInterceptor();
        ConnectivityInterceptor connectivityInterceptor = new ConnectivityInterceptor(application);

        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(loggingInterceptor);
    }

    ////////////////////////
    // CREATE RETROFIT   //
    //////////////////////
    @Provides
    @Singleton
    public static Retrofit retrofitBuild(OkHttpClient.Builder client) {
        return new Retrofit.Builder()
                .client(client.build())
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    ////////////////////////
    // CREATE API SERVICE//
    //////////////////////
    @Provides
    @Singleton
    public static ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public static ApiAuth apiAuth(Retrofit builder) {
        return builder.create(ApiAuth.class);
    }
}
