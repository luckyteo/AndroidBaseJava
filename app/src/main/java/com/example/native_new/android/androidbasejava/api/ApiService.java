package com.example.native_new.android.androidbasejava.api;

import com.example.native_new.android.androidbasejava.db.model.Books;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("book")
    Observable<List<Books>> getBooks();
}
