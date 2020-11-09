package com.example.native_new.android.androidbasejava.repository;

import androidx.annotation.NonNull;

import com.example.native_new.android.androidbasejava.db.model.TasksEntity;
import com.example.native_new.android.androidbasejava.model.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TaskDataSource {

    List<TasksEntity> getTasks();

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
