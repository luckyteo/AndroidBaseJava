package com.example.native_new.android.androidbasejava.ui.todos.tasks;


import androidx.annotation.Nullable;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviViewState;

import java.util.Collections;
import java.util.List;

import lombok.Builder;


@Builder
public class TasksViewState implements MviViewState {
    private final boolean isLoading;
    private final TasksFilterType tasksFilterType;
    private final List<Task> tasks;

    @Nullable
    private final Throwable error;

    private final boolean taskComplete;
    private final boolean taskActivated;
    private final boolean completedTasksCleared;

    private final TasksViewStateBuilder buildWith;

    public static TasksViewState idle() {
        return new TasksViewStateBuilder().isLoading(false)
                .tasksFilterType(TasksFilterType.ALL_TASKS)
                .tasks(Collections.emptyList())
                .error(null)
                .taskComplete(false)
                .taskActivated(false)
                .completedTasksCleared(false)
                .build();
    }
}

