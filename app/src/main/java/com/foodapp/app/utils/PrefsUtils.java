package com.foodapp.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {

    public static final String PREFS = "FoodApp";

    // SharedPreferences methods
    // STRINGS
    public static void setStringInPrefs(Context context, String stringTag, String stringToWrite) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(stringTag, stringToWrite);
        editor.commit();
    }

    public static String getStringFromPrefs(Context context, String stringTag, String stringDefault) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        String stringToReturn = prefs.getString(stringTag, stringDefault);
        return stringToReturn;
    }

    // INTEGER
    public static void setIntInPrefs(Context context, String stringTag, int intToWrite) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(stringTag, intToWrite);
        editor.commit();
    }

    public static int getIntFromPrefs(Context context, String stringTag, int intDefault) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        int intToReturn = prefs.getInt(stringTag, intDefault);
        return intToReturn;
    }

    // LONG
    public static void setLongInPrefs(Context context, String stringTag, long longToWrite) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(stringTag, longToWrite);
        editor.commit();
    }

    public static long getLongFromPrefs(Context context, String stringTag, int longDefault) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        long longToReturn = prefs.getLong(stringTag, longDefault);
        return longToReturn;
    }

    // BOOLEAN
    public static void setBooleanInPrefs(Context context, String stringTag, boolean booleanToWrite) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(stringTag, booleanToWrite);
        editor.commit();
    }

    public static boolean getBooleanFromPrefs(Context context, String stringTag, boolean booleanDefault) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        boolean booleanToReturn = prefs.getBoolean(stringTag, booleanDefault);
        return booleanToReturn;
    }

    // REMOVE
    public static void removeFromPrefs(Context context, String stringTag) {
        SharedPreferences prefs = context.getSharedPreferences(PrefsUtils.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(stringTag);
        editor.commit();
    }
}
