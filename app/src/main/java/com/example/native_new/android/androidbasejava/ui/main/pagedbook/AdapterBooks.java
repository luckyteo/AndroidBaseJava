package com.example.native_new.android.androidbasejava.ui.main.pagedbook;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.native_new.android.androidbasejava.data.models.Books;
import com.example.native_new.android.androidbasejava.databinding.ListItemBinding;

import org.jetbrains.annotations.NotNull;

public class AdapterBooks extends PagedListAdapter<Books, VHBooks> {

    private static final DiffUtil.ItemCallback<Books> diffCallback =
            new DiffUtil.ItemCallback<Books>() {
                // Books details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Books oldBooks, Books newBooks) {
                    return oldBooks.getId().equals(newBooks.getId());
                }

                @Override
                public boolean areContentsTheSame(Books oldBooks,
                                                  @NotNull Books newBooks) {
                    return oldBooks.equals(newBooks);
                }
            };

    public AdapterBooks() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public VHBooks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = ListItemBinding.inflate(layoutInflater, parent, false);
        return new VHBooks(binding);
    }

    @Override
    public int getItemCount() {
        if (getCurrentList() != null) {
            return getCurrentList().size();
        }
        return super.getItemCount();
    }

    @Override
    public void onBindViewHolder(@NonNull VHBooks holder, int position) {
        Books item = getItem(position);
        if (item != null) {
            holder.bindData(item);
        }
        // Null defines a placeholder item - PagedListAdapter automatically
        // invalidates this row when the actual object is loaded from the
        // database.
        // -- check holder.clear()
    }
}
