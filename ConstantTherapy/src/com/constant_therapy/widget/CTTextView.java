package com.constant_therapy.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CTTextView extends TextView {

    public CTTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CTTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CTTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
      //  Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Avenir Next.ttc");
       // setTypeface(tf ,1);

    }

}
