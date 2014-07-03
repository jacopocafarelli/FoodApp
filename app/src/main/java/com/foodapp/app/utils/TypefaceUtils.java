package com.foodapp.app.utils;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceUtils {

    private static final String FONT_FOLDER_PREFIX = "fonts/";
    private static final String FONT_EXTENSION_TTF = ".ttf";

    public static Typeface getCustomTypeface(Context context, String fontFamily, String fontStyle) {
        String fontPath = FONT_FOLDER_PREFIX + fontFamily + "-" + fontStyle + FONT_EXTENSION_TTF;
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }
}