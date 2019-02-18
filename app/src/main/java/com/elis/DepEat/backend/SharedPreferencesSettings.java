package com.elis.DepEat.backend;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesSettings {

    private static android.content.SharedPreferences sharedPreferences;
    private static android.content.SharedPreferences.Editor editor;
    private static String PACKAGE_NAME = "com.elis.DepEat";


    public static void setSharedPreferences(Context context, String key, String value) {
        editor = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSharedPreferences(Context context, String key, boolean value) {
        editor = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setSharedPreferences(Context context, String key, int value) {
        editor = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getStringFromPreferences(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, null);
    }

    public static boolean getBooleanFromPreferences(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

}


