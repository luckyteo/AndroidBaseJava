package com.example.native_new.android.androidbasejava.ui.main;

import android.view.View;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.MainFragmentBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends BaseFragment<MainViewModel, MainFragmentBinding> {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getResourceLayoutId() {
        return R.layout.main_fragment;
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
        viewModel.getBooks();
    }
}