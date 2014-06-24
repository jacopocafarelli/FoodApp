package com.foodapp.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.foodapp.app.R;
import com.foodapp.app.utils.TypefaceUtils;

public class TypefaceTextView extends TextView {

    public TypefaceTextView(Context context) {
        super(context);
        init(null);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TypefaceTextView, 0, 0);
        try {
            String fontFamily = a.getString(R.styleable.TypefaceTextView_fontFamily);
            String fontStyle = a.getString(R.styleable.TypefaceTextView_fontStyle);

            if (!isInEditMode() && !TextUtils.isEmpty(fontFamily)) {
                Typeface tf = TypefaceUtils.getCustomTypeface(getContext(), fontFamily, fontStyle);
                setTypeface(tf);
                setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
        } finally {
            a.recycle();
        }
    }
}
