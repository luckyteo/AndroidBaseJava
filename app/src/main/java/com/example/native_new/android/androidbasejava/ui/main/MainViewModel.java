package com.example.native_new.android.androidbasejava.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.native_new.android.androidbasejava.db.model.Books;
import com.example.native_new.android.androidbasejava.repository.Repository;
import com.example.native_new.android.androidbasejava.utils.logs.LogTag;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public LiveData<PagedList<Books>> stateListBooks;
    private Repository repository;


    @ViewModelInject
    public MainViewModel(Repository repository) {

        this.repository = repository;
    }

    public Disposable getBooks() {
        return repository.getRemoteBooks()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        this::processInsertBook,
                        throwable -> LogTag.i("get book error => %s", throwable.getMessage()));
    }

    private void processInsertBook(List<Books> books) {
        repository.insertBook(books);
    }

    public LiveData<PagedList<Books>> getStateListBooks() {
        stateListBooks = new LivePagedListBuilder<>(repository.getBooks(), /* page size */ 50)
                .build();
        return stateListBooks;
    }
}