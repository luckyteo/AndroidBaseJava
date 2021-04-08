package com.example.native_new.android.androidbasejava.usercase;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.native_new.android.androidbasejava.data.db.entities.Books;
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

    public LiveData<PagedList<Books>> execute() {
        return new LivePagedListBuilder<>(repository.getBooks(), /* page size */ 50)
                .build();
    }
}
