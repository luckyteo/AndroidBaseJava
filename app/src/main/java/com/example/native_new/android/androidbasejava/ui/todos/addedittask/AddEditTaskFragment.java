package com.example.native_new.android.androidbasejava.ui.todos.addedittask;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.AddtaskFragBinding;
import com.example.native_new.android.androidbasejava.mvibase.MviView;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;
import com.example.native_new.android.androidbasejava.utils.Constants;
import com.example.native_new.android.androidbasejava.utils.logs.LogTag;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Observable;

@AndroidEntryPoint
public class AddEditTaskFragment extends BaseFragment<AddEditTaskViewModel, AddtaskFragBinding> implements MviView<AddEditTaskIntent, AddEditTaskViewState> {

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    @Override
    protected int getResourceLayoutId() {
        return R.layout.addtask_frag;
    }

    @Override
    protected void onInitView(View root) {
//        disposables.add(RxView.clicks(binding.btnDone)
//                .map(unit -> binding.btnDone.getId())
//                .mergeWith(RxView.clicks(binding.btnDone1).map(unit -> binding.btnDone1.getId()))
//                //.mergeWith(RxView.clicks(binding.btnSuccess).map(unit -> "btnSuccess")
//
//                .throttleFirst(300, TimeUnit.MILLISECONDS)
//                .subscribe(this::onClicks));
    }

    private void onClicks(int id) {
        if (id == R.id.btnDone) {
            LogTag.itag(tag(), "click btn Done");
        } else if (id == R.id.btnDone1) {
            LogTag.itag(tag(), "click btn Done 11111111");
        }
    }

    @Override
    protected void subscribeUi() {
        bind();
    }

    @Override
    protected String tag() {
        return AddEditTaskFragment.class.getSimpleName();
    }

    @Override
    public Observable<AddEditTaskIntent> intents() {
        return Observable.merge(initialIntent(), saveTaskIntent());
    }

    @Override
    public void render(AddEditTaskViewState state) {
        if (state.isSaved()) {
            backToTasksList();
            return;
        }
        if (state.isEmpty()) {
            showEmptyTaskError();
        }
        if (!state.getTitle().isEmpty()) {
            setTitle(state.getTitle());
        }
        if (!state.getDescription().isEmpty()) {
            setDescription(state.getDescription());
        }
    }

    private void bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        disposables.add(viewModel.states().subscribe(this::render));
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents());
    }

    private Observable<AddEditTaskIntent.InitialIntent> initialIntent() {
        return Observable.just(AddEditTaskIntent.InitialIntent.create());
    }

    private Observable<AddEditTaskIntent.SaveTask> saveTaskIntent() {
        return RxView.clicks(binding.btnDone)
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .map(ignore -> AddEditTaskIntent.SaveTask.create(getArgumentTaskId(),
                        binding.addTaskTitle.getText().toString(),
                        binding.addTaskDescription.getText().toString()));
    }

    private String getArgumentTaskId() {
        Bundle args = getArguments();
        if (args == null) return null;
        return args.getString(ARGUMENT_EDIT_TASK_ID);
    }

    private void showEmptyTaskError() {
        Snackbar.make(binding.addTaskTitle, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    private void backToTasksList() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.getPreviousBackStackEntry().getSavedStateHandle().set(Constants.BUNDLE_REFRESH, true);
        navController.popBackStack();
    }

    private void setTitle(String title) {
        binding.addTaskTitle.setText(title);
    }

    private void setDescription(String description) {
        binding.addTaskDescription.setText(description);
    }

}
