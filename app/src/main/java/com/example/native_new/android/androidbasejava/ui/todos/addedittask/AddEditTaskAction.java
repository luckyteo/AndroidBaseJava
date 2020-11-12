package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import androidx.annotation.NonNull;

import com.example.native_new.android.androidbasejava.mvibase.MviAction;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AddEditTaskAction extends MviAction {

    @AllArgsConstructor
    @Getter
    class PopulateTask implements AddEditTaskAction {
        private String taskId;

        public static PopulateTask create(@NonNull String taskId) {
            return new PopulateTask(taskId);
        }
    }

    @AllArgsConstructor
    @Getter
    class CreateTask implements AddEditTaskAction {
        private String title;
        private String description;

        public static CreateTask create(@NonNull String title, @NonNull String description) {
            return new CreateTask(title, description);
        }
    }

    @AllArgsConstructor
    @Getter
    class UpdateTask implements AddEditTaskAction {
        private String taskId;
        private String title;
        private String description;

        public static UpdateTask create(@NonNull String taskId, @NonNull String title,
                                        @NonNull String description) {
            return new UpdateTask(taskId, title, description);
        }
    }

    class SkipMe implements AddEditTaskAction {
        public static SkipMe create() {
            return new SkipMe();
        }
    }
}
