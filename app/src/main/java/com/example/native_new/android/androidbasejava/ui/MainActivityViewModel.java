package com.example.native_new.android.androidbasejava.ui;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    @Inject
    MainActivityViewModel() {
    }
}
