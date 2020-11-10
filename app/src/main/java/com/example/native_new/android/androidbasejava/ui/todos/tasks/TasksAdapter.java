package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.model.Task;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksAdapter extends BaseAdapter {

    private final PublishSubject<Task> mTaskClickObservable = PublishSubject.create();
    private final PublishSubject<Task> mTaskToggleObservable = PublishSubject.create();
    private List<Task> mTasks;

    public TasksAdapter(List<Task> tasks) {
        setList(tasks);
    }

    public void replaceData(List<Task> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    Observable<Task> getTaskClickObservable() {
        return mTaskClickObservable;
    }

    Observable<Task> getTaskToggleObservable() {
        return mTaskToggleObservable;
    }

    private void setList(List<Task> tasks) {
        this.mTasks = checkNotNull(tasks);
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
        }

        final Task task = getItem(position);

        TextView titleTV = rowView.findViewById(R.id.title);
        titleTV.setText(task.getTitleForList());

        CheckBox completeCB = rowView.findViewById(R.id.complete);

        // Active/completed task UI
        completeCB.setChecked(task.isCompleted());
        if (task.isCompleted()) {
            rowView.setBackground(ResourcesCompat.getDrawable(viewGroup.getResources(),
                    R.drawable.list_completed_touch_feedback, null));
        } else {
            rowView.setBackground(ResourcesCompat.getDrawable(viewGroup.getResources(),
                    R.drawable.touch_feedback, null));
        }

        completeCB.setOnClickListener(ignored -> mTaskToggleObservable.onNext(task));

        rowView.setOnClickListener(ignored -> mTaskClickObservable.onNext(task));

        return rowView;
    }
}
