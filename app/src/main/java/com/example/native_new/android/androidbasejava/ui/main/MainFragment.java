package com.example.native_new.android.androidbasejava.ui.main;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.native_new.android.androidbasejava.R;
import com.example.native_new.android.androidbasejava.data.AppPreferences;
import com.example.native_new.android.androidbasejava.databinding.MainFragmentBinding;
import com.example.native_new.android.androidbasejava.ui.base.BaseFragment;
import com.example.native_new.android.androidbasejava.ui.custom.RecyclerItemClickListener;
import com.example.native_new.android.androidbasejava.ui.main.pagedbook.AdapterBooks;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class MainFragment extends BaseFragment<MainViewModel, MainFragmentBinding> {

    private AdapterBooks adapterBooks;
    @Inject
    AppPreferences pref;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void onInitView(View root) {
        setupRecyl();
        pref.setToken("data");
        Timber.i("Token here %s", pref.getToken());
    }

    @Override
    protected void subscribeUi() {
        // observer live data
        viewModel.getStateListBooks().observe(getViewLifecycleOwner(), books -> adapterBooks.submitList(books));
    }

    @Override
    protected boolean isHideBackButton() {
        return true;
    }

    private void setupRecyl() {
        binding.recyl.hasFixedSize();
        binding.recyl.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        if (adapterBooks == null) {
            adapterBooks = new AdapterBooks();
        }
        if (!adapterBooks.hasStableIds()) {
            adapterBooks.setHasStableIds(true);
        }
        binding.recyl.setAdapter(adapterBooks);

        binding.recyl.addOnItemTouchListener(
                new RecyclerItemClickListener(requireContext(), binding.recyl,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // do whatever
                                NavController navController =
                                        NavHostFragment.findNavController(MainFragment.this);
                                NavDirections navDirections =
                                        MainFragmentDirections.actionMainFragmentToFragmentDetail();
                                navController.navigate(navDirections);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
        );
    }
}