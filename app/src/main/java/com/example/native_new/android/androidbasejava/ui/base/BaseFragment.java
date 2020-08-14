package com.example.native_new.android.androidbasejava.ui.base;

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

import com.example.native_new.android.androidbasejava.utils.logs.LogTag;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseFragment<T extends ViewModel, E extends ViewDataBinding> extends Fragment {
    public final CompositeDisposable disposables = new CompositeDisposable();
    protected E binding;
    protected T viewModel;


    protected abstract int getResourceLayoutId();

    protected abstract void onInitView(View root);

    protected abstract void subscribeUi();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogTag.i("BaseFragment onCreateView");
        View root = inflater.inflate(getResourceLayoutId(), container, false);
        binding = DataBindingUtil.bind(root);
        onInitView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogTag.i("BaseFragment onViewCreate");
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        subscribeUi();
    }

    /**
     * @return view model instance
     */
    private T getViewModel() {
        final Type[] types =
                ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass()))
                        .getActualTypeArguments();
        return new ViewModelProvider(this).get((Class<T>) types[0]);
    }
}

