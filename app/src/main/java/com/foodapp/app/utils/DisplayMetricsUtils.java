package com.foodapp.app.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

public class DisplayMetricsUtils {

    private static final String STATUS_BAR_HEIGHT = "status_bar_height";
    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    private static final String DIMEN = "dimen";
    private static final String ANDROID = "android";

    public static int toPx(double dp, Context context) {
        return (int) (dp * getDpi(context) + 0.5f);
    }

    public static double toDp(int px, Context context) {
        return px / getDpi(context);
    }

    public static int getDisplayWidth(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return getHeight(context);
        } else {
            return getWidth(context);
        }
    }

    public static int getDisplayHeight(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return getWidth(context);
        } else {
            return getHeight(context);
        }
    }

    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getDpi(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static boolean isPhone(Context context) {
        return toDp(getWidth(context), context) <= 480;
    }

    public static boolean isTablet(Context context) {
        return !isPhone(context);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(STATUS_BAR_HEIGHT, DIMEN, ANDROID);
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getNavigationBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier(NAVIGATION_BAR_HEIGHT, DIMEN, ANDROID);
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
            return 0;
        } else {
            return 0;
        }
    }
}
