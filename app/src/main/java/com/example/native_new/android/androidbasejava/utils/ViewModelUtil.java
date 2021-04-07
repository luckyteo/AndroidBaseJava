package com.example.native_new.android.androidbasejava.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

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

    /**
     * @return view model instance
     */
    public static <V extends ViewModel> V getViewModel(@NonNull ViewModelStoreOwner owner) {
        final Type[] types =
                ((ParameterizedType) Objects.requireNonNull(owner.getClass().getGenericSuperclass()))
                        .getActualTypeArguments();
        return new ViewModelProvider(owner).get((Class<V>) types[0]);
    }
}
