package com.example.native_new.android.androidbasejava.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.native_new.android.androidbasejava.db.model.Books;
import com.example.native_new.android.androidbasejava.db.model.TasksEntity;

@Database(entities = {Books.class, TasksEntity.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public static final String DB_NAME = "db_base.sqlite";
    public abstract BooksDao getBooksDao();
    public abstract TasksDao getTasksDao();
}
