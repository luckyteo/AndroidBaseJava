package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import android.view.View;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.FragmentTasksBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;

public class TaskFragment extends BaseFragment<TasksViewModel, FragmentTasksBinding> {
    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_tasks;
    }

    @Override
    protected void onInitView(View root) {

    }

    @Override
    protected void subscribeUi() {

    }
}
