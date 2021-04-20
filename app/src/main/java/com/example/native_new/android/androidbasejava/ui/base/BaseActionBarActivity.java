package com.example.native_new.android.androidbasejava.ui.base;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.native_new.android.androidbasejava.R;

public abstract class BaseActionBarActivity<V extends ViewModel, B extends ViewDataBinding> extends BaseActivity<V, B> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
    }

    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed(); // Implemented by activity
        });
    }

    public void showToolBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }

    public void hideToolBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    public void showBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void hideBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NavDestination destination =
                Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination();
        if (destination != null && destination.getId() == R.id.mainFragment) {
            hideBackButton();
        }
    }
}
