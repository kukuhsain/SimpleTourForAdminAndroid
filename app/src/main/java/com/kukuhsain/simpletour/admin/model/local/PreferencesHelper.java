package com.kukuhsain.simpletour.admin.model.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.kukuhsain.simpletour.admin.SimpleTourApp;

/**
 * Created by kukuh on 20/10/16.
 */

public class PreferencesHelper {
    private static PreferencesHelper INSTANCE;
    private SharedPreferences sharedPreferences;

    private PreferencesHelper() {
        sharedPreferences = SimpleTourApp.getInstance()
                .getSharedPreferences("simpletour.admin.sp", Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesHelper();
        }
        return INSTANCE;
    }

    public void putAccessToken(String accessToken) {
        sharedPreferences.edit().putString("accessToken", accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString("accessToken", "");
    }
}
