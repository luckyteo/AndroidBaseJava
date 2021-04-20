package com.example.native_new.android.androidbasejava.ui.splash;

import android.os.Handler;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.databinding.SplashFragmentBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;

public class SplashFragment extends BaseFragment<SplashViewModel, SplashFragmentBinding> {
    @Override
    protected int getResourceLayoutId() {
        return R.layout.splash_fragment;
    }

    @Override
    protected void onInitView(View root) {

    }

    @Override
    protected void subscribeUi() {

        new Handler().postDelayed(() -> {
            NavController navController = NavHostFragment.findNavController(this);
            NavDirections navDirections = SplashFragmentDirections.actionSplashToMain();
            navController.navigate(navDirections);
        }, 1000);
    }

    @Override
    protected boolean isHideBackButton() {
        return false;
    }

    @Override
    protected void setupToolbar() {
        mainActions.hideToolBar();
    }
}
