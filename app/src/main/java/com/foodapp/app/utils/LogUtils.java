package com.foodapp.app.utils;

import android.util.Log;

import com.foodapp.app.common.Config;

public class LogUtils {

    private static final String APP_LOG_TAG = "FoodApp";

    public static void logVerbose(String message) {
        if (Config.IS_DEVELOPING) {
            Log.v(APP_LOG_TAG, message);
        }
    }

    public static void logDebug(String message) {
        if (Config.IS_DEVELOPING) {
            Log.d(APP_LOG_TAG, message);
        }
    }

    public static void logInfo(String message) {
        if (Config.IS_DEVELOPING) {
            Log.i(APP_LOG_TAG, message);
        }
    }

    public static void logError(String message) {
        if (Config.IS_DEVELOPING) {
            Log.e(APP_LOG_TAG, message);
        }
    }

    public static void logWarning(String message) {
        if (Config.IS_DEVELOPING) {
            Log.w(APP_LOG_TAG, message);
        }
    }

    public static void logWTF(String message) {
        if (Config.IS_DEVELOPING) {
            Log.wtf(APP_LOG_TAG, message);
        }
    }
}
