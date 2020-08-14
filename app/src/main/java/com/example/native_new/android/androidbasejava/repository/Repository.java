package com.example.native_new.android.androidbasejava.repository;

import com.example.native_new.android.androidbasejava.api.ApiService;
import com.example.native_new.android.androidbasejava.db.PokeDao;
import com.example.native_new.android.androidbasejava.model.Books;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    @Inject
    PokeDao pokeDao;
    @Inject
    ApiService apiService;

    @Inject
    public Repository() {
        // nothing impl
    }

    public Observable<List<Books>> getBooks() {
        return apiService.getBooks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
