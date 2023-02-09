package com.managmentdic.widgets;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.managmentdic.R;

/**
 * Created by payam on 12/25/2016.
 */
public class MakeLayoutParams {
    Activity context;
    private float height;
    private float width;

    public MakeLayoutParams(Activity context) {
        this.context = context;
        init();

    }

    private void init() {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        height = metrics.heightPixels;
        width = metrics.widthPixels;
    }


    public void JustifyInScrollView(Activity rowView, TextView tv, boolean justify) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        layoutParams.setMargins((int) (width * 0.08), 0,
                (int) (width * 0.1), 0);

        tv.setLayoutParams(layoutParams);
        ScrollView sv = (ScrollView) rowView.findViewById(R.id.scrollView);
        sv.setPadding(0, 0, 0, 0);
        sv.requestLayout();
        if (justify)
            TextViewJustify.justifyText(tv, (int) (width * 0.81));

    }
}
