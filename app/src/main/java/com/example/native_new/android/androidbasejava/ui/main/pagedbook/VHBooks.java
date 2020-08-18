package com.example.native_new.android.androidbasejava.ui.main.pagedbook;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.native_new.android.androidbasejava.databinding.ListItemBinding;
import com.example.native_new.android.androidbasejava.db.model.Books;
import com.example.native_new.android.androidbasejava.utils.logs.LogTag;

public class VHBooks extends RecyclerView.ViewHolder {
    private ListItemBinding binding;

    public VHBooks(@NonNull View itemView) {

        binding = DataBindingUtil.findBinding(itemView);
        super(binding.getRoot());
    }

    public void bindData(Books book) {
        if (binding != null) {
            binding.setItem(book);
            LogTag.i("luckyteo", "data item [%s]", book);
            Glide.with(binding.pokemonImage.getContext()).load(book.getAvatar()).into(binding.pokemonImage);

            binding.executePendingBindings();
        }
    }
}
