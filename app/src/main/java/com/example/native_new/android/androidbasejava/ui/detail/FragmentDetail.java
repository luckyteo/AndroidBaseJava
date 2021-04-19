package com.example.native_new.android.androidbasejava.ui.detail;

import android.view.View;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.DetailFragmentBinding;
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
        //nothing
    }

    @Override
    protected boolean isHideBackButton() {
        return false;
    }
}