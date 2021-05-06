package com.example.native_new.android.androidbasejava.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class ViewModelUtil {
    private ViewModelUtil() {
        // nothting
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
