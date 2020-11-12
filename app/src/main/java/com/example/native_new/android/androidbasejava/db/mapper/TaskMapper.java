package com.example.native_new.android.androidbasejava.db.mapper;

import com.example.native_new.android.androidbasejava.db.model.TasksEntity;
import com.example.native_new.android.androidbasejava.model.Task;

public class TaskMapper {

    private TaskMapper() {
        //nothing
    }

    public static Task toModel(TasksEntity entity) {
        return new Task(entity.getTitle(), entity.getDescription(), entity.getId());
    }

    public static TasksEntity toEntity(Task model) {
        return TasksEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .build();
    }
}
