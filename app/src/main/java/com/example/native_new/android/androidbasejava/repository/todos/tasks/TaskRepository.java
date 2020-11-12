package com.example.native_new.android.androidbasejava.repository.todos.tasks;

import androidx.annotation.NonNull;

import com.example.native_new.android.androidbasejava.db.TasksDao;
import com.example.native_new.android.androidbasejava.db.mapper.TaskMapper;
import com.example.native_new.android.androidbasejava.db.model.TasksEntity;
import com.example.native_new.android.androidbasejava.model.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class TaskRepository implements TaskDataSource{

    @Inject
    TasksDao tasksDao;

    @Inject
    public TaskRepository(){
        //nothing
    }

    @Override
    public Single<List<Task>> getTasks() {
        List<TasksEntity> tasks = tasksDao.getTasks();
        return Single.just(tasks).flatMap(tasksEntities -> {
            List<Task> lists = new ArrayList<>();
            for (TasksEntity entity :
                    tasksEntities) {
                Task task = new Task(entity.getTitle(), entity.getDescription(), entity.getId(),
                        entity.isCompleted());
                lists.add(task);
            }
            return Single.just(lists);
        });
    }

    @Override
    public Single<Task> getTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public Completable saveTask(@NonNull Task task) {
        tasksDao.insertTaskEntity(TaskMapper.toEntity(task));
        return Completable.complete();
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
