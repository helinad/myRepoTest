package com.constant_therapy.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.constant_therapy.animation.SwipeListener;
import com.constant_therapy.constantTherapy.R;

public class SwipeListView extends ListView{

	
	private int REL_SWIPE_MIN_DISTANCE;
	private int REL_SWIPE_MAX_OFF_PATH;
	private int REL_SWIPE_THRESHOLD_VELOCITY;
	private SwipeListener mSwipeListener;
	private int mItemHeightNormal;
	private int mItemHeightExpanded;
	private int mItemHeightHalf;
	public SwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		Resources res = getResources();
		mItemHeightNormal = res.getDimensionPixelSize(R.dimen.normal_height);
		mItemHeightHalf = mItemHeightNormal / 2;
		mItemHeightExpanded = res
				.getDimensionPixelSize(R.dimen.expanded_height);

		// 手势滑动部分.
		DisplayMetrics dm = getResources().getDisplayMetrics();
		REL_SWIPE_MIN_DISTANCE = (int) (25.0f * dm.densityDpi / 160.0f + 0.5);
		REL_SWIPE_MAX_OFF_PATH = (int) (25.0f * dm.densityDpi / 160.0f + 0.5);
		REL_SWIPE_THRESHOLD_VELOCITY = (int) (25.0f * dm.densityDpi / 160.0f + 0.5);
		final GestureDetector gestureDetector = new GestureDetector(context,
				new MyGestureDetector());

		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
	}
	
	class MyGestureDetector extends SimpleOnGestureListener {

		private int temp_position = -1;

		// Detect a single-click and call my own handler.
		@Override
		public boolean onSingleTapUp(MotionEvent e) {

			int pos = pointToPosition((int) e.getX(), (int) e.getY());
			if(pos != -1){
				mSwipeListener.onClickItem(pos);
			}
			return false;
		}
		
		@Override
		public void onLongPress(MotionEvent e){

			int pos = pointToPosition((int) e.getX(), (int) e.getY());
			View v = SwipeListView.this.getChildAt(pos);
			
			//mSwipeListener.onLongClickItem(v,pos);
			
		}

		@Override
		public boolean onDown(MotionEvent e) {
			temp_position = pointToPosition((int) e.getX(), (int) e.getY());
			return super.onDown(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
				return false;

			if (e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {

				int pos = SwipeListView.this.pointToPosition(
						(int) e1.getX(), (int) e2.getY());

				if (pos >= 0 && temp_position == pos) {
				}
				//Swipe left side
				if(pos != -1)
					mSwipeListener.onSwipeItem(false, pos);
			} else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {

				int pos = SwipeListView.this.pointToPosition(
						(int) e1.getX(), (int) e2.getY());
				if (pos >= 0 && temp_position == pos) {
				}
				//Swipe right side
				if(pos != -1)
					mSwipeListener.onSwipeItem(true, pos);
			}
			return false;
		}

	}
	public void setSwipeListener(SwipeListener mSwipeListener) {
		this.mSwipeListener = mSwipeListener;
	}

}


