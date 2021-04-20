package com.example.native_new.android.androidbasejava.usecase;

import androidx.paging.DataSource;

import com.example.native_new.android.androidbasejava.data.models.Books;
import com.example.native_new.android.androidbasejava.repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetBooksUC {
    @Inject
    Repository repository;

    @Inject
    public GetBooksUC() {
        //nothing
    }

    public DataSource.Factory<Integer, Books> execute() {
        return repository.getBooks();
    }
}
