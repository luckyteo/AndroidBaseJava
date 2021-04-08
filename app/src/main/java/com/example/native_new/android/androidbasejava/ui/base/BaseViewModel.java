package com.example.native_new.android.androidbasejava.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {
    protected final CompositeDisposable disposables = new CompositeDisposable();

    protected void addDispose(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
