package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviResult;
import com.example.native_new.android.androidbasejava.utils.LceStatus;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AddEditTaskResult extends MviResult {

    @AllArgsConstructor
    @Getter
    class PopulateTask implements AddEditTaskResult{
        private final LceStatus status;
        private final Task task;
        private final Throwable error;

        static PopulateTask success(@Nonnull Task task){
            return new PopulateTask(LceStatus.SUCCESS, task, null);
        }

        static PopulateTask failure(Throwable error){
            return new PopulateTask(LceStatus.FAILURE, null, error);
        }

        static PopulateTask inFlight(){
            return new PopulateTask(LceStatus.IN_FLIGHT, null, null);
        }
    }

    @AllArgsConstructor
    @Getter
    class CreateTask implements AddEditTaskResult{
       private boolean isEmpty;

        static CreateTask success(){
            return new CreateTask(false);
        }

        static CreateTask empty(){
            return new CreateTask(true);
        }
    }

    class UpdateTask implements AddEditTaskResult{
        static UpdateTask create(){
            return new UpdateTask();
        }
    }
}
