package com.example.native_new.android.androidbasejava.ui.detail;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.DetailFragmentBinding;
import com.example.native_new.android.androidbasejava.shareviewmodel.ShareMainDetailViewModel;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;


public class FragmentDetail extends BaseFragment<FragmentDetailViewModel, DetailFragmentBinding> {

    @Override
    protected int getResourceLayoutId() {
        return R.layout.detail_fragment;
    }

    @Override
    protected void onInitView(View root) {
        // on init view
    }

    @Override
    protected void subscribeUi() {
        // observer live data
        ShareMainDetailViewModel model = new ViewModelProvider(requireActivity()).get(ShareMainDetailViewModel.class);
        model.getMessage().observe(getViewLifecycleOwner(), this::updateMessage);
    }

    @Override
    protected String tag() {
        return FragmentDetail.class.getSimpleName();
    }

    private void updateMessage(String msg) {
        binding.message.setText(msg);
    }
}