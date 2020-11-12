package com.example.native_new.android.androidbasejava.db.model;

public final class TasksEntityBuilder {
    private String title;
    private String description;
    private boolean completed;
    private String id;

    private TasksEntityBuilder() {
    }

    public static TasksEntityBuilder aTasksEntity() {
        return new TasksEntityBuilder();
    }

    public TasksEntityBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TasksEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TasksEntityBuilder withCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public TasksEntityBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public TasksEntity build() {
        TasksEntity tasksEntity = new TasksEntity(id);
        tasksEntity.setTitle(title);
        tasksEntity.setDescription(description);
        tasksEntity.setCompleted(completed);
        return tasksEntity;
    }
}
