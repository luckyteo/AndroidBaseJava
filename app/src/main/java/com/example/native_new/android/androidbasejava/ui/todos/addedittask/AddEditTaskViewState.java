package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import com.example.native_new.android.androidbasejava.mvibase.MviViewState;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class AddEditTaskViewState implements MviViewState {

    private final String title;
    private final String description;
    private final boolean isEmpty;
    private final boolean isSaved;
    private final Throwable error;

    private final AddEditTaskViewStateBuilder builderWith;

    public static AddEditTaskViewState idle() {
        return new AddEditTaskViewStateBuilder()
                .title("")
                .description("")
                .error(null)
                .isEmpty(false)
                .isSaved(false)
                .build();
    }

    public AddEditTaskViewStateBuilder getBuilderWith() {
        return new AddEditTaskViewStateBuilder()
                .title(title)
                .description(description)
                .isEmpty(isEmpty)
                .isSaved(isSaved)
                .error(error);
    }
}
