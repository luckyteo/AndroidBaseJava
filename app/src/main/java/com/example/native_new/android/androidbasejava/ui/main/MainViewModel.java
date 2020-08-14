package com.example.native_new.android.androidbasejava.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.native_new.android.androidbasejava.repository.Repository;
import com.example.native_new.android.androidbasejava.utils.logs.LogTag;

public class MainViewModel extends ViewModel {

    private Repository repository;

    @ViewModelInject
    public MainViewModel(Repository repository) {

        this.repository = repository;
    }

    public void getBooks() {
        repository.getBooks().subscribe(
                books -> LogTag.i("book size %s", books.size()),
                throwable -> LogTag.i("get book error => %s", throwable.getMessage()));

    }
}