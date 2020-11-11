package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviResult;
import com.example.native_new.android.androidbasejava.utils.LceStatus;
import com.example.native_new.android.androidbasejava.utils.UiNotificationStatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

interface TasksResult extends MviResult {
    @AllArgsConstructor
    @Getter
    class LoadTasks implements TasksResult {
        @NonNull
        private final LceStatus status;

        @Nullable
        private final List<Task> tasks;

        @Nullable
        private final TasksFilterType filterType;

        @Nullable
        private final Throwable error;

        @NonNull
        static TasksResult.LoadTasks success(@NonNull List<Task> tasks, @Nullable TasksFilterType filterType) {
            return new TasksResult.LoadTasks(LceStatus.SUCCESS, tasks, filterType, null);
        }

        @NonNull
        static TasksResult.LoadTasks failure(Throwable error) {
            return new TasksResult.LoadTasks(LceStatus.FAILURE, null, null, error);
        }

        @NonNull
        static TasksResult.LoadTasks inFlight() {
            return new TasksResult.LoadTasks(LceStatus.IN_FLIGHT, null, null, null);
        }
    }

    @AllArgsConstructor
    @Getter
    class ActivateTaskResult implements TasksResult {
        @NonNull
        private final LceStatus status;

        @Nullable
        private final UiNotificationStatus uiNotificationStatus;

        @Nullable
        private final List<Task> tasks;

        @Nullable
        private final Throwable error;

        @NonNull
        static TasksResult.ActivateTaskResult hideUiNotification() {
            return new TasksResult.ActivateTaskResult(LceStatus.SUCCESS, UiNotificationStatus.HIDE, null, null);
        }

        @NonNull
        static TasksResult.ActivateTaskResult success(@NonNull List<Task> tasks) {
            return new TasksResult.ActivateTaskResult(LceStatus.SUCCESS, UiNotificationStatus.SHOW, tasks, null);
        }

        @NonNull
        static TasksResult.ActivateTaskResult failure(Throwable error) {
            return new TasksResult.ActivateTaskResult(LceStatus.FAILURE, null, null, error);
        }

        @NonNull
        static TasksResult.ActivateTaskResult inFlight() {
            return new TasksResult.ActivateTaskResult(LceStatus.IN_FLIGHT, null, null, null);
        }
    }

    @AllArgsConstructor
    @Getter
    class CompleteTaskResult implements TasksResult {
        @NonNull
        private final LceStatus status;

        @Nullable
        private final UiNotificationStatus uiNotificationStatus;

        @Nullable
        private final List<Task> tasks;

        @Nullable
        private final Throwable error;

        @NonNull
        static TasksResult.CompleteTaskResult hideUiNotification() {
            return new TasksResult.CompleteTaskResult(LceStatus.SUCCESS, UiNotificationStatus.HIDE, null, null);
        }

        @NonNull
        static TasksResult.CompleteTaskResult success(@NonNull List<Task> tasks) {
            return new TasksResult.CompleteTaskResult(LceStatus.SUCCESS, UiNotificationStatus.SHOW, tasks, null);
        }

        @NonNull
        static TasksResult.CompleteTaskResult failure(Throwable error) {
            return new TasksResult.CompleteTaskResult(LceStatus.FAILURE, null, null, error);
        }

        @NonNull
        static TasksResult.CompleteTaskResult inFlight() {
            return new TasksResult.CompleteTaskResult(LceStatus.IN_FLIGHT, null, null, null);
        }
    }

    @AllArgsConstructor
    @Getter
    class ClearCompletedTasksResult implements TasksResult {
        @NonNull
        private final LceStatus status;

        @Nullable
        private final UiNotificationStatus uiNotificationStatus;

        @Nullable
        private final List<Task> tasks;

        @Nullable
        private final Throwable error;

        @NonNull
        static TasksResult.ClearCompletedTasksResult hideUiNotification() {
            return new TasksResult.ClearCompletedTasksResult(LceStatus.SUCCESS, UiNotificationStatus.HIDE, null, null);
        }

        @NonNull
        static TasksResult.ClearCompletedTasksResult success(@NonNull List<Task> tasks) {
            return new TasksResult.ClearCompletedTasksResult(LceStatus.SUCCESS,
                    UiNotificationStatus.SHOW, tasks,
                    null);
        }

        @NonNull
        static TasksResult.ClearCompletedTasksResult failure(Throwable error) {
            return new TasksResult.ClearCompletedTasksResult(LceStatus.FAILURE, null, null, error);
        }

        @NonNull
        static TasksResult.ClearCompletedTasksResult inFlight() {
            return new TasksResult.ClearCompletedTasksResult(LceStatus.IN_FLIGHT, null, null, null);
        }
    }
}
