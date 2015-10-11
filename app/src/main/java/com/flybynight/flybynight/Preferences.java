package com.flybynight.flybynight;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by closestudios on 10/11/15.
 */
public class Preferences {

    private static Preferences instance;
    private SharedPreferences prefs;

    public static Preferences getPrefs(Context context) {
        if(instance == null) {
            instance = new Preferences();
            instance.prefs = context.getSharedPreferences("caster.prefs",Context.MODE_PRIVATE);
        }
        return instance;
    }

    public String getToken() {
        return prefs.getString("token",null);
    }

    public void setToken(String token) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("token",token);
        edit.apply();
    }

}