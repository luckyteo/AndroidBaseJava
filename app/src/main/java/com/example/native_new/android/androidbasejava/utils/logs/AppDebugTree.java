package com.example.native_new.android.androidbasejava.utils.logs;

import com.example.native_new.android.androidbasejava.utils.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class AppDebugTree extends Timber.DebugTree {
    @Override
    protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
        return String.format("%s (%s:%s)#%s", Constants.TAG, element.getFileName(),
                element.getLineNumber(), element.getMethodName());
    }
}
