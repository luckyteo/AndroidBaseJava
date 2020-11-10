package com.example.native_new.android.androidbasejava.ui.todos.tasks;

import com.example.native_new.android.androidbasejava.model.Task;
import com.example.native_new.android.androidbasejava.mvibase.MviAction;

import lombok.AllArgsConstructor;

interface TasksAction extends MviAction {

    @AllArgsConstructor
    class LoadTasks implements TasksAction {
        private final boolean forceUpdate;
        private final TasksFilterType filterType;

        public static LoadTasks loadAndFilter(boolean forceUpdate, TasksFilterType filterType) {
            return new LoadTasks(forceUpdate, filterType);
        }

        public static LoadTasks load(boolean forceUpdate) {
            return new LoadTasks(forceUpdate, null);
        }
    }

    @AllArgsConstructor
    class ActivateTaskAction implements TasksAction {
        private final Task task;

        public static ActivateTaskAction create(Task task) {
            return new ActivateTaskAction(task);
        }
    }

    @AllArgsConstructor
    class CompleteTaskAction implements TasksAction {
        private final Task task;

        public static CompleteTaskAction create(Task task) {
            return new CompleteTaskAction(task);
        }
    }

    class ClearCompletedTasksAction implements TasksAction {
        private ClearCompletedTasksAction(){
            //nothing
        }
        public static ClearCompletedTasksAction create() {
            return new ClearCompletedTasksAction();
        }
    }
}
