package com.example.native_new.android.androidbasejava.mvibase;

import io.reactivex.Observable;

/**
 * Object representing a UI that will
 * a) emit its intents to a view model,
 * b) subscribes to a view model for rendering its UI.
 *
 * @param <I> Top class of the {@link MviIntent} that the {@link com.example.android.architecture.blueprints.todoapp.mvibase.MviView} will be emitting.
 * @param <S> Top class of the {@link MviViewState} the {@link com.example.android.architecture.blueprints.todoapp.mvibase.MviView} will be subscribing to.
 */
public interface MviView<I extends MviIntent, S extends MviViewState> {
    /**
     * Unique {@link Observable <I>} used by the {@link MviViewModel}
     * to listen to the {@link com.example.android.architecture.blueprints.todoapp.mvibase.MviView}.
     * All the {@link com.example.android.architecture.blueprints.todoapp.mvibase.MviView}'s {@link MviIntent}s must go through this {@link Observable <I>}.
     */
    Observable<I> intents();

    /**
     * Entry point for the {@link com.example.android.architecture.blueprints.todoapp.mvibase.MviView} to render itself based on a {@link MviViewState}.
     */
    void render(S state);
}
