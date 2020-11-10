package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.FragmentTasksBinding;
import com.example.native_new.android.androidbasejava.mvibase.MviIntent;
import com.example.native_new.android.androidbasejava.mvibase.MviView;
import com.example.native_new.android.androidbasejava.mvibase.MviViewModel;
import com.example.native_new.android.androidbasejava.mvibase.MviViewState;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;
import com.example.native_new.android.androidbasejava.ui.base.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class TaskFragment extends BaseFragment<TasksViewModel, FragmentTasksBinding> implements MviView<TasksIntent, TasksViewState> {
    private TasksAdapter mListAdapter;
    private View mNoTasksView;
    private ImageView mNoTaskIcon;
    private TextView mNoTaskMainView;
    private TextView mNoTaskAddView;
    private LinearLayout mTasksView;
    private TextView mFilteringLabelView;
    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;
    private PublishSubject<TasksIntent.RefreshIntent> mRefreshIntentPublisher =
            PublishSubject.create();
    private PublishSubject<TasksIntent.ClearCompletedTasksIntent> mClearCompletedTaskIntentPublisher =
            PublishSubject.create();
    private PublishSubject<TasksIntent.ChangeFilterIntent> mChangeFilterIntentPublisher =
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

    }

    @Override
    protected void subscribeUi() {
        bind();
    }

    @Override
    public Observable<TasksIntent> intents() {
        return null;
    }

    @Override
    public void render(TasksViewState state) {

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
}
