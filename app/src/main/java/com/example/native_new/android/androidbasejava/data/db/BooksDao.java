package com.example.native_new.android.androidbasejava.data.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.native_new.android.androidbasejava.data.models.Books;

import java.util.List;

@Dao
public interface BooksDao {

    @Query("SELECT * FROM books")
    DataSource.Factory<Integer, Books> getAllBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooks(List<Books> books);
}