package com.faw.hongqi.util;


import android.util.Log;


/**
 * Created by liangjie on 2015/4/10 by Android studio.
 * Description:日志打印统一
 */
public class LogUtil {
    static String TAG = "CAR_MANUAL";
    public static boolean debug = true;

    public static void logError(String error) {
        if (debug) {
            Log.e(TAG, error);
        }
    }

    public static void logVerbose(String info) {
        if (debug) {
            Log.v(TAG, info);
        }
    }

    public static void logDebug(String debugInfo) {
        if (debug) {
            Log.d(TAG, debugInfo);
        }
    }

    public static void logInfo(String info) {
        if (debug) {
            Log.i(TAG, info);
        }
    }

    public static void logWarn(String warn) {
        if (debug) {
            Log.w(TAG, warn);
        }
    }

}
