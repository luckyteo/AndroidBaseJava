package com.example.native_new.android.androidbasejava.ui.main;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.native_new.android.androidbasejava.data.models.Books;
import com.example.native_new.android.androidbasejava.ui.base.BaseViewModel;
import com.example.native_new.android.androidbasejava.usecase.GetBooksUC;
import com.example.native_new.android.androidbasejava.usecase.RequestBooksUC;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class MainViewModel extends BaseViewModel {

    RequestBooksUC requestBooksUC;
    GetBooksUC getBooksUC;

    @Getter
    LiveData<PagedList<Books>> stateListBooks;

    @Inject
    public MainViewModel(RequestBooksUC requestBooksUC,
                         GetBooksUC getBooksUC) {
        this.requestBooksUC = requestBooksUC;
        this.getBooksUC = getBooksUC;
        //nothing
        generateListBooks();
        addDispose(requestBooksUC.execute());
    }


    private void generateListBooks() {
        if (stateListBooks == null) {
            stateListBooks = new LivePagedListBuilder<>(getBooksUC.execute(), /* page size */ 50)
                    .build();
        }
    }
}