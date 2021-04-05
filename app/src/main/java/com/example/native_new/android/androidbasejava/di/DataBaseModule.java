package com.example.native_new.android.androidbasejava.di;

import android.app.Application;

import androidx.room.Room;

import com.example.native_new.android.androidbasejava.db.AppDB;
import com.example.native_new.android.androidbasejava.db.BooksDao;
import com.example.native_new.android.androidbasejava.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {
    private DataBaseModule() {
        // nothing
    }

    @Provides
    @Singleton
    public static AppDB providePokemonDB(Application application) {
        return Room.databaseBuilder(application, AppDB.class, Constants.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static BooksDao provideBooksDao(AppDB appDB) {
        return appDB.getBooksDao();
    }
}