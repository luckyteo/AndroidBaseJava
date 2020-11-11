package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviAction;
import com.example.native_new.android.androidbasejava.mvibase.MviIntent;
import com.example.native_new.android.androidbasejava.mvibase.MviResult;
import com.example.native_new.android.androidbasejava.mvibase.MviView;
import com.example.native_new.android.androidbasejava.mvibase.MviViewModel;
import com.example.native_new.android.androidbasejava.mvibase.MviViewState;
import com.example.native_new.android.androidbasejava.utils.UiNotificationStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksViewModel extends ViewModel implements MviViewModel<TasksIntent, TasksViewState> {

    /**
     * Proxy subject used to keep the stream alive even after the UI gets recycled.
     * This is basically used to keep ongoing events and the last cached State alive
     * while the UI disconnects and reconnects on config changes.
     */
    @NonNull
    private PublishSubject<TasksIntent> mIntentsSubject;
    @NonNull
    private Observable<TasksViewState> mStatesObservable;
    @NonNull
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private final TasksActionProcessorHolder mActionProcessorHolder;

    @ViewModelInject
    public TasksViewModel(TasksActionProcessorHolder actionProcessorHolder) {
        mActionProcessorHolder = actionProcessorHolder;

        mIntentsSubject = PublishSubject.create();
        mStatesObservable = compose();
    }

    @Override
    public void processIntents(Observable<TasksIntent> intents) {
        mDisposables.add(intents.subscribe(mIntentsSubject::onNext));
    }

    @Override
    public Observable<TasksViewState> states() {
        return mStatesObservable;
    }

    /**
     * Compose all components to create the stream logic
     */
    private Observable<TasksViewState> compose() {
        return mIntentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(mActionProcessorHolder.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(TasksViewState.idle(), reducer)
                // When a reducer just emits previousState, there's no reason to call render. In fact,
                // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
                // by showing the same snackbar twice in rapid succession).
                .distinctUntilChanged()
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0);
    }

    /**
     * take only the first ever InitialIntent and all intents of other types
     * to avoid reloading data on config changes
     */
    private ObservableTransformer<TasksIntent, TasksIntent> intentFilter =
            intents -> intents.publish(shared ->
                    Observable.merge(
                            shared.ofType(TasksIntent.InitialIntent.class).take(1),
                            shared.filter(intent -> !(intent instanceof TasksIntent.InitialIntent))
                    )
            );

    /**
     * Translate an {@link MviIntent} to an {@link MviAction}.
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    private TasksAction actionFromIntent(MviIntent intent) {
        if (intent instanceof TasksIntent.InitialIntent) {
            return TasksAction.LoadTasks.loadAndFilter(true, TasksFilterType.ALL_TASKS);
        }
        if (intent instanceof TasksIntent.ChangeFilterIntent) {
            return TasksAction.LoadTasks.loadAndFilter(false,
                    ((TasksIntent.ChangeFilterIntent) intent).getFilterType());
        }
        if (intent instanceof TasksIntent.RefreshIntent) {
            return TasksAction.LoadTasks.load(((TasksIntent.RefreshIntent) intent).isForceUpdate());
        }
        if (intent instanceof TasksIntent.ActivateTaskIntent) {
            return TasksAction.ActivateTaskAction.create(
                    ((TasksIntent.ActivateTaskIntent) intent).getTask());
        }
        if (intent instanceof TasksIntent.CompleteTaskIntent) {
            return TasksAction.CompleteTaskAction.create(
                    ((TasksIntent.CompleteTaskIntent) intent).getTask());
        }
        if (intent instanceof TasksIntent.ClearCompletedTasksIntent) {
            return TasksAction.ClearCompletedTasksAction.create();
        }
        throw new IllegalArgumentException("do not know how to treat this intent " + intent);
    }

    @Override
    protected void onCleared() {
        mDisposables.dispose();
    }

    /**
     * The Reducer is where {@link MviViewState}, that the {@link MviView} will use to
     * render itself, are created.
     * It takes the last cached {@link MviViewState}, the latest {@link MviResult} and
     * creates a new {@link MviViewState} by only updating the related fields.
     * This is basically like a big switch statement of all possible types for the {@link MviResult}
     */
    private static final BiFunction<TasksViewState, TasksResult, TasksViewState> reducer =
            (previousState, result) -> {
                TasksViewState.TasksViewStateBuilder stateBuilder = TasksViewState.builder();
                if (result instanceof TasksResult.LoadTasks) {
                    TasksResult.LoadTasks loadResult = (TasksResult.LoadTasks) result;
                    TasksFilterType filterType = loadResult.getFilterType();
                    if (filterType == null) {
                        filterType = previousState.getTasksFilterType();
                    }
                    switch (loadResult.getStatus()) {
                        case SUCCESS:
                            List<Task> tasks = filteredTasks(checkNotNull(loadResult.getTasks()), filterType);
                            return stateBuilder.isLoading(false).tasks(tasks).tasksFilterType(filterType).build();
                        case FAILURE:
                            return stateBuilder.isLoading(false).error(loadResult.getError()).tasksFilterType(filterType).build();
                        case IN_FLIGHT:
                            return stateBuilder.isLoading(true).tasksFilterType(filterType).build();
                    }
                } else if (result instanceof TasksResult.CompleteTaskResult) {
                    TasksResult.CompleteTaskResult completeTaskResult =
                            (TasksResult.CompleteTaskResult) result;
                    switch (completeTaskResult.getStatus()) {
                        case SUCCESS:
                            stateBuilder.taskComplete(completeTaskResult.getUiNotificationStatus() == UiNotificationStatus.SHOW);
                            if (completeTaskResult.getTasks() != null) {
                                List<Task> tasks =
                                        filteredTasks(checkNotNull(completeTaskResult.getTasks()),
                                                previousState.getTasksFilterType());
                                stateBuilder.tasks(tasks);
                            }
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(completeTaskResult.getError()).build();
                        case IN_FLIGHT:
                            return stateBuilder.build();
                    }
                } else if (result instanceof TasksResult.ActivateTaskResult) {
                    TasksResult.ActivateTaskResult activateTaskResult =
                            (TasksResult.ActivateTaskResult) result;
                    switch (activateTaskResult.getStatus()) {
                        case SUCCESS:
                            stateBuilder.taskActivated(activateTaskResult.getUiNotificationStatus() == UiNotificationStatus.SHOW);
                            if (activateTaskResult.getTasks() != null) {
                                List<Task> tasks =
                                        filteredTasks(checkNotNull(activateTaskResult.getTasks()),
                                                previousState.getTasksFilterType());
                                stateBuilder.tasks(tasks);
                            }
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(activateTaskResult.getError()).build();
                        case IN_FLIGHT:
                            return stateBuilder.build();
                    }
                } else if (result instanceof TasksResult.ClearCompletedTasksResult) {
                    TasksResult.ClearCompletedTasksResult clearCompletedTasks =
                            (TasksResult.ClearCompletedTasksResult) result;
                    switch (clearCompletedTasks.getStatus()) {
                        case SUCCESS:
                            stateBuilder.completedTasksCleared(clearCompletedTasks.getUiNotificationStatus() == UiNotificationStatus.SHOW);
                            if (clearCompletedTasks.getTasks() != null) {
                                List<Task> tasks =
                                        filteredTasks(checkNotNull(clearCompletedTasks.getTasks()),
                                                previousState.getTasksFilterType());
                                stateBuilder.tasks(tasks);
                            }
                            return stateBuilder.build();
                        case FAILURE:
                            return stateBuilder.error(clearCompletedTasks.getError()).build();
                        case IN_FLIGHT:
                            return stateBuilder.build();
                    }
                } else {
                    throw new IllegalArgumentException("Don't know this result " + result);
                }
                throw new IllegalStateException("Mishandled result? Should not happen (as always)");
            };

    private static List<Task> filteredTasks(@NonNull List<Task> tasks,
                                            @NonNull TasksFilterType filterType) {
        List<Task> filteredTasks = new ArrayList<>(tasks.size());
        switch (filterType) {
            case ALL_TASKS:
                filteredTasks.addAll(tasks);
                break;
            case ACTIVE_TASKS:
                for (Task task : tasks) {
                    if (task.isActive()) filteredTasks.add(task);
                }
                break;
            case COMPLETED_TASKS:
                for (Task task : tasks) {
                    if (task.isCompleted()) filteredTasks.add(task);
                }
                break;
        }
        return filteredTasks;
    }
}
