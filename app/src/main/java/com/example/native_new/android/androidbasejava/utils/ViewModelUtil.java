package com.example.native_new.android.androidbasejava.utils;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class ViewModelUtil {
    private ViewModelUtil() {
        // nothting
    }

    public static <T extends ViewModel> ViewModelProvider.Factory createFor(T model) {
        return new ViewModelProvider.Factory() {
            @NotNull
            @SuppressWarnings("unchecked")
            @Override
            public <K extends ViewModel> K create(@NotNull Class<K> modelClass) {
                if (modelClass.isAssignableFrom(model.getClass())) {
                    return (K) model;
                }
                throw new IllegalArgumentException("unexpected model class " + modelClass);
            }
        };
    }
}
