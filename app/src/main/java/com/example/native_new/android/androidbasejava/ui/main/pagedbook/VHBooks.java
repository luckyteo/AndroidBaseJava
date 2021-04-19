package com.example.native_new.android.androidbasejava.ui.main.pagedbook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.native_new.android.androidbasejava.data.models.Books;
import com.example.native_new.android.androidbasejava.databinding.ListItemBinding;

public class VHBooks extends RecyclerView.ViewHolder {
    private final ListItemBinding binding;

    public VHBooks(@NonNull ListItemBinding binding) {

        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindData(Books book) {
        if (binding != null) {
            binding.setItem(book);
//            Glide.with(binding.pokemonImage.getContext()).load(book.getAvatar()).into(binding.pokemonImage);

            binding.executePendingBindings();
        }
    }
}
