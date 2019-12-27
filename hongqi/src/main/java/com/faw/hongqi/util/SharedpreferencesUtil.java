package com.faw.hongqi.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

public class SharedpreferencesUtil {
    static String PREFERENCES_NAME = "share";
    private static String IS_UPDATA_DB = "is_update_bd";
    static String VERSION_CODE = "version_code";
    static String ISUNZIP = "is_unzip";

    public static Boolean isUploadBD(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_UPDATA_DB, true);
    }

    public static void setUpLoadBD(Context context, boolean role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(IS_UPDATA_DB, role).commit();
    }

    public static void setVersionCode(Context context, String versionCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString(VERSION_CODE, versionCode).commit();
    }
    public static String getVersionCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(VERSION_CODE, "");
    }

    public static void setIsUnzip(Context context, String unzip) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString(ISUNZIP, unzip).commit();
    }
    public static String getIsUnzip(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(ISUNZIP, "");
    }
}
