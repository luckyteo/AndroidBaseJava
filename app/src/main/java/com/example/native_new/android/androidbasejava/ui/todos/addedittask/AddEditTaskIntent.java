package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import androidx.annotation.NonNull;

import com.example.native_new.android.androidbasejava.mvibase.MviIntent;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AddEditTaskIntent extends MviIntent {
    @Getter
    class InitialIntent implements AddEditTaskIntent {
        private String taskId;
        private InitialIntent() {
            // nothing
        }

        public static InitialIntent create() {
            return new InitialIntent();
        }
    }

    @AllArgsConstructor
    @Getter
    class SaveTask implements AddEditTaskIntent {
        private final String taskId;
        private final String title;
        private final String description;

        public static SaveTask create(@NonNull String taskId, @NonNull String title,
                                      @NonNull String description) {
            return new SaveTask(taskId, title, description);
        }
    }
}
