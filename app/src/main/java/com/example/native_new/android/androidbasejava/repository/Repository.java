package com.example.native_new.android.androidbasejava.repository;

import androidx.lifecycle.LiveData;

import com.example.native_new.android.androidbasejava.data.api.PokeApiService;
import com.example.native_new.android.androidbasejava.data.db.PokeDao;
import com.example.native_new.android.androidbasejava.data.model.Pokemon;
import com.example.native_new.android.androidbasejava.data.model.PokemonResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private PokeDao pokeDao;

    @Inject
    PokeApiService apiService;

    @Inject
    public Repository(PokeDao pokeDao, PokeApiService apiService) {
        this.pokeDao = pokeDao;
        this.apiService = apiService;
    }


    public Observable<PokemonResponse> getPokemons(){
        return apiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon){
        pokeDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){
        pokeDao.deletePokemon(pokemonName);
    }

    public void deleteAll(){
        pokeDao.deleteAll();
    }

    public LiveData<List<Pokemon>> getFavoritePokemon(){
        return pokeDao.getFavoritePokemons();
    }
}
