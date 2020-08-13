package com.example.native_new.android.androidbasejava.data.api;

import com.example.native_new.android.androidbasejava.data.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokeApiService {
    @GET("pokemon")
    Observable<PokemonResponse> getPokemons();
}
