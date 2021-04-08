package com.example.native_new.android.androidbasejava.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.native_new.android.androidbasejava.R;

import static android.content.Context.MODE_PRIVATE;

public class AppPreferences {
    private final SharedPreferences preferences;

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public AppPreferences(Context context) {
//        preferences       =   PreferenceManager.getDefaultSharedPreferences(context);
        preferences = context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
        //edit = preferences.edit();
    }


    public void setToken(String strValue) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key", strValue);
        editor.apply();
    }

    public String getToken() {
        return preferences.getString("key", "");
    }
}

