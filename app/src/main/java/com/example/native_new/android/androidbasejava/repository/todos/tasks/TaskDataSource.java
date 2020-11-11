package com.example.native_new.android.androidbasejava.repository.todos.tasks;

import androidx.annotation.NonNull;

import com.example.native_new.android.androidbasejava.model.Task;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


public interface TaskDataSource {
    default Single<List<Task>> getTasks(boolean forceUpdate) {
        if (forceUpdate) refreshTasks();
        return getTasks();
    }

    Single<List<Task>> getTasks();

    Single<Task> getTask(@NonNull String taskId);

    Completable saveTask(@NonNull Task task);

    Completable completeTask(@NonNull Task task);

    Completable completeTask(@NonNull String taskId);

    Completable activateTask(@NonNull Task task);

    Completable activateTask(@NonNull String taskId);

    Completable clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    Completable deleteTask(@NonNull String taskId);
}
