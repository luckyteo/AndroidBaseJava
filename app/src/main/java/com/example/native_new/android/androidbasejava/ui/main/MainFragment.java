package com.example.native_new.android.androidbasejava.ui.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.MainFragmentBinding;
import com.example.native_new.android.androidbasejava.shareviewmodel.ShareMainDetailViewModel;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends BaseFragment<MainViewModel, MainFragmentBinding> {

    @Override
    protected int getResourceLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getBooks();
        ShareMainDetailViewModel mainDetailViewModel = new ViewModelProvider(requireActivity()).get(ShareMainDetailViewModel.class);
        mainDetailViewModel.setMessage("Da ~ chay vao Main");
    }

    @Override
    protected void onInitView(View root) {
        binding.gotoDetail.setOnClickListener(v -> {
            NavDirections action =
                    MainFragmentDirections
                            .actionMainFragmentToFragmentDetail();
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    protected void subscribeUi() {
        // observer live data
    }
}