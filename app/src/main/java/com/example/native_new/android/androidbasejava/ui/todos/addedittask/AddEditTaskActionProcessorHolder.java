package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.repository.todos.tasks.TaskRepository;
import com.example.native_new.android.androidbasejava.utils.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;

public class AddEditTaskActionProcessorHolder {
    @Inject
    TaskRepository mTasksRepository;
    @Inject
    SchedulerProvider mSchedulerProvider;

    @Inject
    public AddEditTaskActionProcessorHolder() {
        //nothing
    }


    private final ObservableTransformer<AddEditTaskAction.PopulateTask,
            AddEditTaskResult.PopulateTask>
            populateTaskProcessor = actions -> actions.flatMap(action ->
            mTasksRepository.getTask(action.getTaskId())
                    .toObservable()
                    .map(AddEditTaskResult.PopulateTask::success)
                    .onErrorReturn(AddEditTaskResult.PopulateTask::failure)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .startWithItem(AddEditTaskResult.PopulateTask.inFlight())
    );

    private final ObservableTransformer<AddEditTaskAction.CreateTask,
            AddEditTaskResult.CreateTask> createTaskProcessor = actions -> actions.map(action -> {
        Task task = new Task(action.getTitle(), action.getDescription());
        if (task.isEmpty()) {
            return AddEditTaskResult.CreateTask.empty();
        }
        mTasksRepository.saveTask(task);
        return AddEditTaskResult.CreateTask.success();
    });

    private final ObservableTransformer<AddEditTaskAction.UpdateTask,
            AddEditTaskResult.UpdateTask> updateTaskProcessor =
            actions -> actions.flatMap(action ->
                    mTasksRepository.saveTask(new Task(action.getTitle(), action.getDescription()
                            , action.getTaskId())).andThen(Observable.just(AddEditTaskResult.UpdateTask.create()))
            );

    ObservableTransformer<AddEditTaskAction, AddEditTaskResult> actionProcessor =
            actions -> actions.publish(shared -> Observable.merge(
                    shared.ofType(AddEditTaskAction.PopulateTask.class).compose(populateTaskProcessor),
                    shared.ofType(AddEditTaskAction.CreateTask.class).compose(createTaskProcessor),
                    shared.ofType(AddEditTaskAction.UpdateTask.class).compose(updateTaskProcessor))
                    .mergeWith( // Error for not implemented actions
                            shared.filter(v -> !(v instanceof AddEditTaskAction.PopulateTask) &&
                                    !(v instanceof AddEditTaskAction.CreateTask) &&
                                    !(v instanceof AddEditTaskAction.UpdateTask))
                                    .flatMap(w -> Observable.error(new IllegalArgumentException("Unknown Action type: " + w))))
            );

}
