package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviIntent;

import lombok.AllArgsConstructor;
import lombok.Getter;

interface TasksIntent extends MviIntent {

    class InitialIntent implements TasksIntent {
        private InitialIntent() {
            // nothing
        }

        public static InitialIntent create() {
            return new InitialIntent();
        }
    }

    @AllArgsConstructor
    @Getter
    class RefreshIntent implements TasksIntent {
        private final boolean forceUpdate;

        public static RefreshIntent create(boolean forceUpdate) {
            return new RefreshIntent(forceUpdate);
        }
    }

    @AllArgsConstructor
    @Getter
    class ActivateTaskIntent implements TasksIntent {
        private final Task task;

        public static ActivateTaskIntent create(Task task) {
            return new ActivateTaskIntent(task);
        }
    }

    @AllArgsConstructor
    @Getter
    class CompleteTaskIntent implements TasksIntent {
        private final Task task;

        public static CompleteTaskIntent create(Task task) {
            return new CompleteTaskIntent(task);
        }
    }

    class ClearCompletedTasksIntent implements TasksIntent {
        private ClearCompletedTasksIntent() {
            //nothing
        }

        public static ClearCompletedTasksIntent create() {
            return new ClearCompletedTasksIntent();
        }
    }

    @AllArgsConstructor
    @Getter
    class ChangeFilterIntent implements TasksIntent {
        private final TasksFilterType filterType;

        public static ChangeFilterIntent create(TasksFilterType filterType) {
            return new ChangeFilterIntent(filterType);
        }
    }
}
