package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.FragmentTasksBinding;
import com.example.native_new.android.androidbasejava.mvibase.MviIntent;
import com.example.native_new.android.androidbasejava.mvibase.MviView;
import com.example.native_new.android.androidbasejava.mvibase.MviViewModel;
import com.example.native_new.android.androidbasejava.mvibase.MviViewState;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding4.swiperefreshlayout.RxSwipeRefreshLayout;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

@AndroidEntryPoint
public class TaskFragment extends BaseFragment<TasksViewModel, FragmentTasksBinding> implements MviView<TasksIntent, TasksViewState> {
    private TasksAdapter mListAdapter;

    private final PublishSubject<TasksIntent.RefreshIntent> mRefreshIntentPublisher =
            PublishSubject.create();
    private final PublishSubject<TasksIntent.ClearCompletedTasksIntent> mClearCompletedTaskIntentPublisher =
            PublishSubject.create();
    private final PublishSubject<TasksIntent.ChangeFilterIntent> mChangeFilterIntentPublisher =
            PublishSubject.create();

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_tasks;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new TasksAdapter(new ArrayList<>(0));
    }

    @Override
    protected void onInitView(View root) {


        // Set up tasks view
        binding.tasksList.setAdapter(mListAdapter);

//        // Set up floating action button
//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
//
//        fab.setImageResource(R.drawable.ic_add);
//        fab.setOnClickListener(ignored -> showAddTask());

        // Set up progress indicator
        binding.refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        // Set the scrolling view in the custom SwipeRefreshLayout.
        binding.refreshLayout.setScrollUpChild(binding.tasksList);

        setHasOptionsMenu(true);
    }

    @Override
    protected void subscribeUi() {
        bind();
    }

    @Override
    public Observable<TasksIntent> intents() {
        return Observable.merge(initialIntent(), refreshIntent(), adapterIntents(),
                clearCompletedTaskIntent()).mergeWith(changeFilterIntent());
    }

    @Override
    public void render(TasksViewState state) {
        binding.refreshLayout.setRefreshing(state.isLoading());
        if (state.getError() != null) {
            showLoadingTasksError();
            return;
        }

        if (state.isTaskActivated()) showMessage(getString(R.string.task_marked_active));

        if (state.isTaskComplete()) showMessage(getString(R.string.task_marked_complete));

        if (state.isCompletedTasksCleared())
            showMessage(getString(R.string.completed_tasks_cleared));

        if (state.getTasks() == null || state.getTasks().isEmpty()) {
            switch (state.getTasksFilterType()) {
                case ACTIVE_TASKS:
                    showNoActiveTasks();
                    break;
                case COMPLETED_TASKS:
                    showNoCompletedTasks();
                    break;
                default:
                    showNoTasks();
                    break;
            }
        } else {
            mListAdapter.replaceData(state.getTasks());

            binding.tasksLL.setVisibility(View.VISIBLE);
            binding.noTasks.setVisibility(View.GONE);

            switch (state.getTasksFilterType()) {
                case ACTIVE_TASKS:
                    showActiveFilterLabel();
                    break;
                case COMPLETED_TASKS:
                    showCompletedFilterLabel();
                    break;
                default:
                    showAllFilterLabel();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // conflicting with the initial intent but needed when coming back from the
        // AddEditTask activity to refresh the list.
        mRefreshIntentPublisher.onNext(TasksIntent.RefreshIntent.create(false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    /**
     * Connect the {@link MviView} with the {@link MviViewModel}
     * We subscribe to the {@link MviViewModel} before passing it the {@link MviView}'s {@link MviIntent}s.
     * If we were to pass {@link MviIntent}s to the {@link MviViewModel} before listening to it,
     * emitted {@link MviViewState}s could be lost
     */
    private void bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        disposables.add(viewModel.states().subscribe(this::render));
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents());

        disposables.add(
                mListAdapter.getTaskClickObservable().subscribe(task -> showTaskDetailsUi(task.getId())));
    }

    private void showTaskDetailsUi(String taskId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some MviIntent stubbing.
//        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
//        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId);
//        startActivity(intent);
        toast("Show Task detail UI");
    }

    /**
     * The initial Intent the {@link MviView} emit to convey to the {@link MviViewModel}
     * that it is ready to receive data.
     * This initial Intent is also used to pass any parameters the {@link MviViewModel} might need
     * to render the initial {@link MviViewState} (e.g. the task id to load).
     */
    private Observable<TasksIntent.InitialIntent> initialIntent() {
        return Observable.just(TasksIntent.InitialIntent.create());
    }

    private Observable<TasksIntent.RefreshIntent> refreshIntent() {
        return RxSwipeRefreshLayout.refreshes(binding.refreshLayout)
                .map(ignored -> TasksIntent.RefreshIntent.create(false))
                .mergeWith(mRefreshIntentPublisher);
    }

    private Observable<TasksIntent.ClearCompletedTasksIntent> clearCompletedTaskIntent() {
        return mClearCompletedTaskIntentPublisher;
    }

    private Observable<TasksIntent.ChangeFilterIntent> changeFilterIntent() {
        return mChangeFilterIntentPublisher;
    }

    private Observable<TasksIntent> adapterIntents() {
        return mListAdapter.getTaskToggleObservable().map(task -> {
            if (!task.isCompleted()) {
                return TasksIntent.CompleteTaskIntent.create(task);
            } else {
                return TasksIntent.ActivateTaskIntent.create(task);
            }
        });
    }

    private void showNoActiveTasks() {
        showNoTasksViews(getResources().getString(R.string.no_tasks_active),
                R.drawable.ic_check_circle_24dp, false);
    }

    private void showNoTasks() {
        showNoTasksViews(getResources().getString(R.string.no_tasks_all),
                R.drawable.ic_assignment_turned_in_24dp, true);
    }

    private void showNoCompletedTasks() {
        showNoTasksViews(getResources().getString(R.string.no_tasks_completed),
                R.drawable.ic_verified_user_24dp, false);
    }

    private void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    private void showNoTasksViews(String mainText, int iconRes, boolean showAddView) {
        binding.tasksLL.setVisibility(View.GONE);
        binding.noTasks.setVisibility(View.VISIBLE);

        binding.noTasksMain.setText(mainText);
        binding.noTasksIcon.setImageDrawable(getResources().getDrawable(iconRes));
        binding.noTasksAdd.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    private void showActiveFilterLabel() {
        binding.filteringLabel.setText(getResources().getString(R.string.label_active));
    }

    private void showCompletedFilterLabel() {
        binding.filteringLabel.setText(getResources().getString(R.string.label_completed));
    }

    private void showAllFilterLabel() {
        binding.filteringLabel.setText(getResources().getString(R.string.label_all));
    }

    private void showAddTask() {
//        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
//        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    private void showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error));
    }

    private void showMessage(String message) {
        View view = getView();
        if (view == null) return;
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
