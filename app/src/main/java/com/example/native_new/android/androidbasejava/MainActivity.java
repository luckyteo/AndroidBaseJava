package com.example.native_new.android.androidbasejava;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.native_new.android.androidbasejava.databinding.MainActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    private MainActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

    }
}