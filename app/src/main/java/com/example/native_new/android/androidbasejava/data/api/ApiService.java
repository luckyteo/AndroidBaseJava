package com.example.native_new.android.androidbasejava.data.api;

import com.example.native_new.android.androidbasejava.data.model.Books;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("book")
    Observable<Books> getBooks();
}
