package com.constant_therapy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.constant_therapy.constantTherapy.R;

public class CurlFlipLayout extends CurlLayout {
	// Game name
	public static final String NAME = "SpaceBlaster";

	private Context mContext;
	//
	private int screenWidth;
	private int screenHeight;
	private int totalPages;
	//
	private CurlView mBookView;
	

	public CurlFlipLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public CurlFlipLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();

	}
	private ScrollView scrollView;
	//
	private void init() {
		
		totalPages = 2;
		Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();		
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		//
		mBookView = new CurlView(mContext, screenWidth, screenHeight);
		// prepare view to take all screenshoot
		LinearLayout ll = new LinearLayout(mContext);
		ll.setOrientation(LinearLayout.VERTICAL);
		LayoutInflater factory = LayoutInflater.from(mContext);
		View view = factory.inflate(R.layout.scrach_page, null);		
		ll.addView(view, screenWidth,screenHeight);
		View view2 = factory.inflate(R.layout.scrach_page, null);		
		ll.addView(view2, screenWidth,screenHeight);
		// hide scrollview's commponent to have clear bitmap
		scrollView = new ScrollView(mContext);
		scrollView.addView(ll);
		scrollView.setVerticalScrollBarEnabled(false);
		scrollView.setHorizontalScrollBarEnabled(false);		
		scrollView.setFadingEdgeLength(0);
		addView(scrollView);
		// add true view and some actions
		View view3 = factory.inflate(R.layout.scrach_page, null);		
		addView(view3, screenWidth, screenHeight);
		View view4 = factory.inflate(R.layout.scrach_page, null);		
		addView(view4, screenWidth, screenHeight);
		
		addView(mBookView);		
		mBookView.setView(view3, view4);
		// view 1
		
	}
	//
	public void next(){
		mBookView.next();
	}
	//
	public void pre(){
		mBookView.pre();
	}
	//

	private Bitmap mBitmap;
	private Bitmap page1, page2;

	public void getPageContent() {
		if(mBitmap==null){
			setBitmap();
		}
	}

	private void setBitmap() {		
		mBitmap = Bitmap.createBitmap(screenWidth, screenHeight*totalPages, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		scrollView.draw(canvas);

		page1 = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas1 = new Canvas(page1);
		canvas1.drawBitmap(mBitmap, 0, 0, new Paint());
		page2 = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas2 = new Canvas(page2);
		canvas2.drawBitmap(mBitmap, 0, -screenHeight, new Paint());
		//removeAllViews();
		scrollView.setVisibility(View.GONE);
		//
		mBookView.init(page1, page2);
		//
	}	
}

