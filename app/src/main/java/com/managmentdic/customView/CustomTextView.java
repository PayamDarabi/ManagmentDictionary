package com.managmentdic.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.managmentdic.R;

/**
 * Created by payam on 12/5/2016.
 */
public class CustomTextView extends TextView {
    String fontName = "ROBOTOREGULAR.ttf";

    public CustomTextView(Context context) {
        super(context);
        setFont();

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TextViewWithCustomFont);
        if(a.hasValue(0)) {
            fontName = a.getString(0);
        }
        setFont();
    }


    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TextViewWithCustomFont);
        if(a.hasValue(0)) {
            fontName = a.getString(0);
        }
        setFont();
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
        if (typeface != null)
            setTypeface(typeface);
    }

}
