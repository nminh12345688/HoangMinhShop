package com.example.hoang_minh_shop.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hoang_minh_shop.models.User;
import com.google.gson.Gson;

public class PreferencesManager {
    private static final String TAG = "PreferencesManager";
    private static PreferencesManager instance;
    private SharedPreferences sharedPreferences;

    private PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences("hoang_minh", Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance(Context context){
        if (instance == null)
            instance = new PreferencesManager(context);
        return instance;
    }

    public void saveUser(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonUser = new Gson().toJson(user);
        editor.putString("user", jsonUser);
        editor.apply();
    }

    public User getUser(){
        String jsonUser = sharedPreferences.getString("user", "");
        Log.d(TAG, "getUser: " + jsonUser);
        if (jsonUser.isEmpty())
            return null;
        return new Gson().fromJson(jsonUser, User.class);
    }

    public void deleteUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }
}
