package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import androidx.lifecycle.ViewModel;

import com.example.native_new.android.androidbasejava.mvibase.MviViewModel;

import io.reactivex.rxjava3.core.Observable;

public class TasksViewModel extends ViewModel implements MviViewModel<TasksIntent, TasksViewState> {
    @Override
    public void processIntents(Observable<TasksIntent> intents) {

    }

    @Override
    public Observable<TasksViewState> states() {
        return null;
    }
}
