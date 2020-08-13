package com.example.native_new.android.androidbasejava.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.native_new.android.androidbasejava.data.model.Pokemon;

@Database(entities = {Pokemon.class},version = 2,exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {
    public abstract PokeDao pokeDao();
}
