package com.example.native_new.android.androidbasejava.ui;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.MainActivityBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseActionBarActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActionBarActivity<MainActivityViewModel, MainActivityBinding> implements MainActions {

    @Override
    protected int getResourceLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void onInitView() {
        setSupportActionBar(binding.toolbar);
    }
}