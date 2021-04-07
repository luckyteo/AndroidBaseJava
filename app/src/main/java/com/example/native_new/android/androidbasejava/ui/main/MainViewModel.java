package com.example.native_new.android.androidbasejava.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.native_new.android.androidbasejava.data.db.entities.Books;
import com.example.native_new.android.androidbasejava.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class MainViewModel extends ViewModel {

    public LiveData<PagedList<Books>> stateListBooks;
    private final Repository repository;


    @Inject
    public MainViewModel(Repository repository) {

        this.repository = repository;
    }

    public Disposable getBooks() {
        return repository.getRemoteBooks()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        this::processInsertBook,
                        throwable -> Timber.i("get book error => %s", throwable.getMessage()));

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