package com.example.native_new.android.androidbasejava.shareviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareMainDetailViewModel extends ViewModel {
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void setMessage(String messageVals) {
        message.setValue(messageVals);
    }
}
