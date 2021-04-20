package com.example.native_new.android.androidbasejava.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.native_new.android.androidbasejava.ui.MainActions;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class BaseFragment<V extends ViewModel, B extends ViewDataBinding> extends Fragment {
    protected B binding;
    protected V viewModel;
    protected MainActions mainActions;


    protected abstract int getResourceLayoutId();

    protected abstract void onInitView(View root);

    protected abstract void subscribeUi();

    protected abstract boolean isHideBackButton();

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mainActions = (MainActions) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getResourceLayoutId(), container, false);
        binding = DataBindingUtil.bind(root);
        onInitView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeUi();
        setupToolbar();
    }

    /**
     * @return view model instance
     */
    private V getViewModel() {
        final Type[] types =
                ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass()))
                        .getActualTypeArguments();
        return new ViewModelProvider(this).get((Class<V>) types[0]);
    }

    protected void setupToolbar() {
        mainActions.showToolBar();
        if (!isHideBackButton()) {
            mainActions.showBackButton();
        }
    }
}

