package com.example.native_new.android.androidbasejava.usecase;

import com.example.native_new.android.androidbasejava.data.models.Books;
import com.example.native_new.android.androidbasejava.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class RequestBooksUC {

    @Inject
    Repository repository;

    @Inject
    public RequestBooksUC() {
        //nothing
    }

    public Disposable execute() {
        return repository.getRemoteBooks()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        this::processInsertBook,
                        throwable -> Timber.i("get book error => %s", throwable.getMessage()));
    }

    private void processInsertBook(List<Books> books) {
        repository.insertBook(books);
    }
}
