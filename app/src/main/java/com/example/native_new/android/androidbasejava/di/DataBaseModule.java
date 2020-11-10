package com.example.native_new.android.androidbasejava.di;

import android.app.Application;

import androidx.room.Room;

import com.example.native_new.android.androidbasejava.db.AppDB;
import com.example.native_new.android.androidbasejava.db.BooksDao;
import com.example.native_new.android.androidbasejava.db.TasksDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {
    private DataBaseModule() {
        // nothing
    }

    @Provides
    @Singleton
    public static AppDB provideDB(Application application) {
        return Room.databaseBuilder(application, AppDB.class, AppDB.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static BooksDao provideBooksDao(AppDB appDB) {
        return appDB.getBooksDao();
    }

    @Provides
    @Singleton
    public static TasksDao provideTasksDao(AppDB appDB) {
        return appDB.getTasksDao();
    }
}