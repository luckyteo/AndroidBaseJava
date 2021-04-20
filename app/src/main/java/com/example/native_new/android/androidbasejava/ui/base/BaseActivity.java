package com.example.native_new.android.androidbasejava.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.example.native_new.android.androidbasejava.utils.ViewModelUtil;

public abstract class BaseActivity<V extends ViewModel, B extends ViewDataBinding> extends AppCompatActivity {

    protected V viewModel;
    protected B binding;

    protected abstract int getResourceLayoutId();

    protected abstract void onInitView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        viewModel = ViewModelUtil.getViewModel(this);
        binding = DataBindingUtil.setContentView(this, getResourceLayoutId());

        onInitView();
    }
}
