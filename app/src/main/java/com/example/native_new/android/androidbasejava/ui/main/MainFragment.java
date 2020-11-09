package com.example.native_new.android.androidbasejava.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.MainFragmentBinding;
import com.example.native_new.android.androidbasejava.shareviewmodel.ShareMainDetailViewModel;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;
import com.example.native_new.android.androidbasejava.ui.main.pagedbook.AdapterBooks;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends BaseFragment<MainViewModel, MainFragmentBinding> {

    private AdapterBooks adapterBooks;
    @Override
    protected int getResourceLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables.add(viewModel.getBooks());
        ShareMainDetailViewModel mainDetailViewModel = new ViewModelProvider(requireActivity()).get(ShareMainDetailViewModel.class);
        mainDetailViewModel.setMessage("Da ~ chay vao Main");
    }

    @Override
    protected void onInitView(View root) {
        setupRecyl();
    }

    @Override
    protected void subscribeUi() {
        // observer live data
        viewModel.getStateListBooks().observe(getViewLifecycleOwner(), books -> adapterBooks.submitList(books));
    }

    private void setupRecyl() {
        binding.recyl.hasFixedSize();
        binding.recyl.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        if (adapterBooks==null){
            adapterBooks = new AdapterBooks();
        }
        if (!adapterBooks.hasStableIds()) {
            adapterBooks.setHasStableIds(true);
        }
        binding.recyl.setAdapter(adapterBooks);
    }
}