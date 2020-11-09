package com.example.native_new.android.androidbasejava.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.native_new.android.androidbasejava.db.model.Books;
import com.google.android.play.core.tasks.Tasks;

@Database(entities = {Books.class, Tasks.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract BooksDao getBooksDao();
    public abstract TasksDao getTasksDao();
}
