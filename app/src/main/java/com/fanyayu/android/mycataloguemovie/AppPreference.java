package com.fanyayu.android.mycataloguemovie;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private String KEY_DAILY = "daily";
    private String KEY_RELEASE = "release";

    private SharedPreferences preferences;

    AppPreference(Context context) {
        String PREFS_NAME = "AppPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

   void setDaily(boolean dailyStatus){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_DAILY, dailyStatus);
        editor.apply();
    }

    boolean isDaily(){
        return preferences.getBoolean(KEY_DAILY, false);
    }

    void setRelease(boolean releaseStatus){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_RELEASE, releaseStatus);
        editor.apply();
    }

    boolean isRelease(){
        return preferences.getBoolean(KEY_RELEASE, false);
    }
}
