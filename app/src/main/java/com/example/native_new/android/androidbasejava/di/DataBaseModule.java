package com.example.native_new.android.androidbasejava.di;

import android.app.Application;

import androidx.room.Room;

import com.example.native_new.android.androidbasejava.db.PokeDao;
import com.example.native_new.android.androidbasejava.db.PokemonDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {
    private DataBaseModule(){
        // nothing
    }
    @Provides
    @Singleton
    public static PokemonDB providePokemonDB(Application application){
        return Room.databaseBuilder(application,PokemonDB.class,"Favorite Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static PokeDao providePokeDao(PokemonDB pokemonDB){
        return pokemonDB.pokeDao();
    }
}