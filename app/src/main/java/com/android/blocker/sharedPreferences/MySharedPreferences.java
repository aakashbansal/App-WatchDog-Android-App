package com.android.blocker.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    // for manipulation of long integer values
    public static long getLongIntValue(Context context, String spName, String key, long defaultVal) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        long value = sp.getLong(key, defaultVal);
        return value;
    }

    public static void storeLongIntValue(Context context, String spName, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putLong(key, value);
        e.commit();
    }


    // for manipulation of string values
    public static String getStringValue(Context context, String spName, String key, String defaultVal) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String value = sp.getString(key, defaultVal);
        return value;
    }

    public static void storeStringValue(Context context, String spName, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(key, value);
        e.commit();
    }


    // for manipulation of boolean values
    public static boolean getBooleanValue(Context context, String spName, String key, boolean defaultVal) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defaultVal);
        return value;
    }

    public static void storeBooleanValue(Context context, String spName, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean(key, value);
        e.commit();
    }
}
