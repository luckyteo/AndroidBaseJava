package com.example.native_new.android.androidbasejava.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.native_new.android.androidbasejava.db.model.Books;

@Database(entities = {Books.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract BooksDao getBooksDao();
}
