package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviAction;
import com.example.native_new.android.androidbasejava.mvibase.MviIntent;
import com.example.native_new.android.androidbasejava.mvibase.MviResult;
import com.example.native_new.android.androidbasejava.mvibase.MviView;
import com.example.native_new.android.androidbasejava.mvibase.MviViewModel;
import com.example.native_new.android.androidbasejava.mvibase.MviViewState;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTaskViewModel extends ViewModel implements MviViewModel<AddEditTaskIntent,
        AddEditTaskViewState> {

    private PublishSubject<AddEditTaskIntent> mIntentsSubject;
    private Observable<AddEditTaskViewState> mStateObservable;

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private AddEditTaskActionProcessorHolder mActionProcessorHolder;

    @ViewModelInject
    public AddEditTaskViewModel(AddEditTaskActionProcessorHolder actionProcessorHolder) {
        mActionProcessorHolder = actionProcessorHolder;

        mIntentsSubject = PublishSubject.create();
        mStateObservable = compose();
    }

    @Override
    public void processIntents(Observable<AddEditTaskIntent> intents) {

    }

    @Override
    public Observable<AddEditTaskViewState> states() {
        return mStateObservable;
    }

    public Observable<AddEditTaskViewState> compose() {
        return mIntentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                // Special case where we do not want to pass this event down the stream
                .filter(action -> !(action instanceof AddEditTaskAction.SkipMe))
                .compose(mActionProcessorHolder.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(AddEditTaskViewState.idle(), reducer)
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
    private ObservableTransformer<AddEditTaskIntent, AddEditTaskIntent> intentFilter =
            intents -> intents.publish(shared ->
                    Observable.merge(
                            shared.ofType(AddEditTaskIntent.InitialIntent.class).take(1),
                            shared.filter(intent -> !(intent instanceof AddEditTaskIntent.InitialIntent))
                    )
            );

    /**
     * Translate an {@link MviIntent} to an {@link MviAction}.
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    private AddEditTaskAction actionFromIntent(MviIntent intent) {
        if (intent instanceof AddEditTaskIntent.InitialIntent) {
            String taskId = ((AddEditTaskIntent.InitialIntent) intent).getTaskId();
            if (taskId == null) {
                // new Task, so nothing to do
                return AddEditTaskAction.SkipMe.create();
            } else {
                return AddEditTaskAction.PopulateTask.create(taskId);
            }
        }
        if (intent instanceof AddEditTaskIntent.SaveTask) {
            AddEditTaskIntent.SaveTask saveTaskIntent = (AddEditTaskIntent.SaveTask) intent;
            final String taskId = saveTaskIntent.getTaskId();
            if (taskId == null) {
                return AddEditTaskAction.CreateTask.create(
                        saveTaskIntent.getTitle(), saveTaskIntent.getDescription());
            } else {
                return AddEditTaskAction.UpdateTask.create(
                        taskId, saveTaskIntent.getTitle(), saveTaskIntent.getDescription());
            }
        }
        // Fail for unhandled intents
        throw new IllegalArgumentException("do not know how to treat this intent " + intent);
    }

    @Override
    protected void onCleared() {
        mDisposable.dispose();
    }

    /**
     * The Reducer is where {@link MviViewState}, that the {@link MviView} will use to
     * render itself, are created.
     * It takes the last cached {@link MviViewState}, the latest {@link MviResult} and
     * creates a new {@link MviViewState} by only updating the related fields.
     * This is basically like a big switch statement of all possible types for the {@link MviResult}
     */
    private static BiFunction<AddEditTaskViewState, AddEditTaskResult, AddEditTaskViewState> reducer =
            (previousState, result) -> {
                AddEditTaskViewState.AddEditTaskViewStateBuilder stateBuilder = previousState.getBuilderWith();
                if (result instanceof AddEditTaskResult.PopulateTask) {
                    AddEditTaskResult.PopulateTask populateTaskResult =
                            (AddEditTaskResult.PopulateTask) result;
                    switch (populateTaskResult.getStatus()) {
                        case SUCCESS:
                            Task task = checkNotNull(populateTaskResult.getTask());
                            if (task.isActive()) {
                                stateBuilder.title(task.getTitle());
                                stateBuilder.description(task.getDescription());
                            }
                            return stateBuilder.build();
                        case FAILURE:
                            Throwable error = checkNotNull(populateTaskResult.getError());
                            return stateBuilder.error(error).build();
                        case IN_FLIGHT:
                            // nothing to do
                            return stateBuilder.build();
                    }
                }
                if (result instanceof AddEditTaskResult.CreateTask) {
                    AddEditTaskResult.CreateTask createTaskResult =
                            (AddEditTaskResult.CreateTask) result;
                    if (createTaskResult.isEmpty()) {
                        return stateBuilder.isEmpty(true).build();
                    } else {
                        return stateBuilder.isEmpty(false).isSaved(true).build();
                    }
                }
                if (result instanceof AddEditTaskResult.UpdateTask) {
                    return stateBuilder.isSaved(true).build();
                }
                // Fail for unhandled results
                throw new IllegalStateException("Mishandled result? Should not happen―as always: " + result);
            };
}
