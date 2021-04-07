package com.example.native_new.android.androidbasejava.ui;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.MainActivityBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<MainActivityViewModel, MainActivityBinding> {

    @Override
    protected int getResourceLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onInitView() {
        setSupportActionBar(binding.toolbar);
    }
}