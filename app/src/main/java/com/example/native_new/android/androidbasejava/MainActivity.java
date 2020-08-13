package com.example.native_new.android.androidbasejava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.native_new.android.androidbasejava.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}