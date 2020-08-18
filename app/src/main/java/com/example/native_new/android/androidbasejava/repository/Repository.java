package com.example.native_new.android.androidbasejava.repository;

import androidx.paging.DataSource;

import com.example.native_new.android.androidbasejava.api.ApiService;
import com.example.native_new.android.androidbasejava.db.BooksDao;
import com.example.native_new.android.androidbasejava.db.model.Books;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    @Inject
    BooksDao booksDao;
    @Inject
    ApiService apiService;

    @Inject
    public Repository() {
        // nothing impl
    }

    public Observable<List<Books>> getRemoteBooks() {
        return apiService.getBooks();
    }

    public void insertBook(List<Books> books){
        booksDao.insertBooks(books);
    }

    public DataSource.Factory<Integer, Books> getBooks(){
       return  booksDao.getAllBooks();
    }
}
