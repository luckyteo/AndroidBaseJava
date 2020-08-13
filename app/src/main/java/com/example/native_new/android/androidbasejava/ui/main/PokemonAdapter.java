package com.example.native_new.android.androidbasejava.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.native_new.android.androidbasejava.data.model.Pokemon;
import com.example.native_new.android.androidbasejava.databinding.ListItemBinding;

import java.util.ArrayList;
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private Context mContext;
    private ArrayList<Pokemon> mList;
    private ListItemBinding binding;

    public PokemonAdapter(Context mContext, ArrayList<Pokemon> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        binding = ListItemBinding.inflate(inflater,parent,false);
        return new PokemonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.itemBinding.pokemonName.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getUrl())
                .into(holder.itemBinding.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder{
        private ListItemBinding itemBinding;

        public PokemonViewHolder(ListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

    public  void updateList(ArrayList<Pokemon> updatedList){
        mList = updatedList;
        notifyDataSetChanged();
    }

    public  Pokemon getPokemonAt(int position){
        return mList.get(position);
    }
}