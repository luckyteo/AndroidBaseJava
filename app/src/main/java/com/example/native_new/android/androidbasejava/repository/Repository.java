package com.example.native_new.android.androidbasejava.repository;

import androidx.paging.DataSource;

import com.example.native_new.android.androidbasejava.data.api.ApiService;
import com.example.native_new.android.androidbasejava.data.db.BooksDao;
import com.example.native_new.android.androidbasejava.data.db.entities.Books;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    @Inject
    BooksDao booksDao;
    @Inject
    ApiService apiService;

    @Singleton
    @Inject
    public Repository() {
        // nothing impl
    }

    public Observable<List<Books>> getRemoteBooks() {
        return apiService.getBooks();
    }

    public void insertBook(List<Books> books) {
        booksDao.insertBooks(books);
    }

    public DataSource.Factory<Integer, Books> getBooks() {
        return booksDao.getAllBooks();
    }
}
