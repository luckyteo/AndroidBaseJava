package com.example.native_new.android.androidbasejava.ui.detail;

import android.view.View;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.DetailFragmentBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;


public class FragmentDetail extends BaseFragment<FragmentDetailViewModel, DetailFragmentBinding> {

    public static FragmentDetail newInstance() {
        return new FragmentDetail();
    }

    @Override
    protected int getResourceLayoutId() {
        return R.layout.detail_fragment;
    }

    @Override
    protected void onInitView(View root) {

    }

    @Override
    protected void subscribeUi() {

    }
}