package com.constant_therapy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class CurlLayout extends RelativeLayout {

	public CurlLayout(Context context) {
		super(context);

	}

	public CurlLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		try {
			// Init game
			getPageContent();
		} catch (Exception e) {
			// bug
			e.printStackTrace();
		}
	}

	//
	abstract protected void getPageContent();
}
