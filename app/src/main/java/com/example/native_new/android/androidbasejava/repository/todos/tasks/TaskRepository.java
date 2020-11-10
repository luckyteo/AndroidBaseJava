package com.example.native_new.android.androidbasejava.repository.todos.tasks;

import androidx.annotation.NonNull;

import com.example.native_new.android.androidbasejava.db.TasksDao;
import com.example.native_new.android.androidbasejava.db.model.TasksEntity;
import com.example.native_new.android.androidbasejava.model.Task;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TaskRepository implements TaskDataSource{

    @Inject
    TasksDao tasksDao;

    @Inject
    public TaskRepository(){
        //nothing
    }

    @Override
    public List<TasksEntity> getTasks() {
        return tasksDao.getTasks();
    }

    @Override
    public Single<Task> getTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable saveTask(@NonNull Task task) {
        return null;
    }

    @Override
    public Completable completeTask(@NonNull Task task) {
        return null;
    }

    @Override
    public Completable completeTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable activateTask(@NonNull Task task) {
        return null;
    }

    @Override
    public Completable activateTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable clearCompletedTasks() {
        return null;
    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public Completable deleteTask(@NonNull String taskId) {
        return null;
    }
}
