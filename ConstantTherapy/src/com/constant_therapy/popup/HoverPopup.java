package com.constant_therapy.popup;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Compliance;
import com.constant_therapy.widget.CTTextView;

public class HoverPopup extends PopupWindow {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapDrawable mBackground;

	private ViewGroup mRootView;

	private Display mDefaultDisplay;

	private ImageView mArrowTop;


	private ImageView mArrowBottom;
	private ImageView mArrowLeft;

	private ImageView mArrowRight;

	private LinearLayout mQuickActionLayout;

	private int[] mAnchorLocations;

	private int mScreenWidth;

	private int mScreenHeight;

	CTTextView tvHome;
	CTTextView tvTask;
	CTTextView tvMins; 
	
	private LayoutInflater mInflater;

	private OnClickQuickActionListener mClickListener;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public HoverPopup(Context context) {
		super(context);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		mDefaultDisplay = wm.getDefaultDisplay();

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		initParams();
		
		// Quick Action 布局
		initQuickAction(context);
	}

	// ===========================================================
	// Public Methods
	// ===========================================================

	
	/**
	 * 显示Quick Action
	 * 
	 * @param anchor
	 */
	public void show(View anchor) {
		if (!isShowing()) {

			// 位置
			Direction showDirection = computeDisplayPosition(anchor);
			
			Log.e("QuickAction", "showDirection = " + test(showDirection));
			
			// 根据位置，显示箭头
			int[] locations = preShow(anchor, showDirection);
			
			// 显示PopupWindow
			if (locations != null) {
				showAtLocation(anchor, Gravity.NO_GRAVITY, locations[0], locations[1]);
			}
			
		} else {
			dismiss();
		}
	}

	
	
	/**
	 * @param quickAction
	 */
	public void addQuickAction(View quickAction) {
		mQuickActionLayout.addView(quickAction);
	}

		
	
	public void setOnClickQuickActionListener(OnClickQuickActionListener listener) {
		mClickListener = listener;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Private Methods
	// ===========================================================

	private void initParams() {
		// 点击在PopupWindow外时，dismiss popup window
		setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
	
		// 设置相关属性
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
	
		// 设置动画效果
		//setAnimationStyle(R.style.quickaction);

		// 宽高自适应
		setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
	}
	
	private void initQuickAction(Context context) {
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRootView = (ViewGroup) mInflater.inflate(R.layout.hoverpopup, null);
		mQuickActionLayout = (LinearLayout) mRootView
				.findViewById(R.id.layout_quickaction);
		mArrowTop = (ImageView) mRootView.findViewById(R.id.arrow_top);
		mArrowBottom = (ImageView) mRootView.findViewById(R.id.arrow_bottom);
		mArrowLeft = (ImageView) mRootView.findViewById(R.id.arrow_left);
		mArrowRight = (ImageView) mRootView.findViewById(R.id.arrow_right);
		 tvHome = (CTTextView) mRootView.findViewById(R.id.tvHome);
		 tvTask = (CTTextView) mRootView.findViewById(R.id.tvTask);
		 tvMins = (CTTextView) mRootView.findViewById(R.id.tvMins);
		mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		setContentView(mRootView);
	
	}
	
	private String getDateFromTimeStamp(long time) {

		Date df = new java.util.Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
		String str = null;
		str = sf.format(df);

		return str;
	}
	
	public void updateItems(Compliance list){
		if (list.getDate() != 0L) {
			if (list.getInClinic()) {
				tvHome.setText("InClinic: "
						+ getDateFromTimeStamp(list.getDate() * 1000));
			} else {
				tvHome.setText("Home: "
						+ getDateFromTimeStamp(list.getDate() * 1000));
			}

			tvTask.setText(list.getCompletedTaskCount() + " tasks completed");
			tvMins.setText(list.getCompletedTaskCount() + " min of therapy");
		} else {
			tvHome.setText("Home: " + list.getDuration());
			tvTask.setText("0 tasks completed");
			tvMins.setText("0 min of therapy");
		}
	}

	/**
	 * Popup Window自动放置（支持上下左右四个位置）。
	 */
	private Direction computeDisplayPosition(View anchor) {

		Direction showDirection = null;

		mAnchorLocations = new int[2];
		// 获取针对整个Window的绝对位置
		anchor.getLocationOnScreen(mAnchorLocations);

		mScreenWidth = mDefaultDisplay.getWidth();
		mScreenHeight = mDefaultDisplay.getHeight();

		mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int popupWidth = mRootView.getMeasuredWidth();
		int popupHeight = mRootView.getMeasuredHeight();

		// ？ 计算有点问题，没有减去状态栏和标题栏的高度
		
		// QuickAction可以显示在anthor上下左右，但仅能显示在一侧
		boolean canShowTop = mAnchorLocations[1] - popupHeight > 0;
		boolean canShowBottom = mAnchorLocations[1] + anchor.getHeight() + popupHeight < mScreenHeight; 
		boolean canShowRight = mAnchorLocations[0] + anchor.getWidth() + popupWidth < mScreenWidth;
		boolean canShowLeft = mAnchorLocations[0] - popupWidth > 0;

		// ？ 判断条件不严谨
		
		if (!canShowTop && canShowBottom) {
			showDirection = Direction.BOTTOM;
		} else if (canShowTop && !canShowBottom) {
			showDirection = Direction.TOP;
		} else if (!canShowLeft && canShowRight) {
			showDirection = Direction.RIGHT;
		} else if (canShowLeft && !canShowRight) {
			showDirection = Direction.LEFT;
		}

//		Log.e("Test", "up -- ay = " + mAnchorLocations[1]
//				+ " , ah = " + anchor.getHeight()
//				+ " , ph = " + popupHeight
//				+ " , sh = " + mScreenHeight);
		
		Log.e("Test", "right -- ax = " + mAnchorLocations[0]
				+ " , aw = " + anchor.getWidth()
				+ " , pw = " + popupWidth
				+ " , sw = " + mScreenWidth);
		
		
		return showDirection;
	}

	public int[] preShow(View anchor, Direction showDirection) {
		
		if (mBackground == null) {
			// 默认设置为透明
			setBackgroundDrawable(new BitmapDrawable());
		} else {
			setBackgroundDrawable(mBackground);
		}
		
		if (showDirection == null) {
			return null;
		}
		
		int[] locations = new int[2]; 
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) 
				mQuickActionLayout.getLayoutParams();
		
		RelativeLayout.LayoutParams arrowParams = null;
		int arrowPos = 0;
		int anchorCenterX = 0;
		int anchorCenterY = 0;
		
		// * 上下箭头如果设置为gone，获取popup window height会总是在变动
		
		switch (showDirection) {
		case TOP:
			mArrowTop.setVisibility(View.INVISIBLE);
			mArrowBottom.setVisibility(View.VISIBLE);
			mArrowLeft.setVisibility(View.GONE);
			mArrowRight.setVisibility(View.GONE);
			
			params.setMargins(0, 0, 0, -6);
			mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			anchorCenterX = mAnchorLocations[0] + anchor.getWidth() / 2;
			
			locations[0] = anchorCenterX - mRootView.getMeasuredWidth() / 2;
			locations[1] = mAnchorLocations[1] - mRootView.getMeasuredHeight();
			
			if (locations[0] <= 0) {
				locations[0] = 0;
			} else if (locations[0] + mRootView.getMeasuredWidth() >= mScreenWidth) {
				locations[0] = mScreenWidth - mRootView.getMeasuredWidth();
			}
			
			// 箭头
			arrowParams = (RelativeLayout.LayoutParams) 
					mArrowBottom.getLayoutParams();
			arrowPos = anchorCenterX - locations[0] - mArrowBottom.getMeasuredWidth() / 2;
			arrowParams.setMargins(arrowPos, 0, 0, 0);
			
			break;
			
		case BOTTOM:
			mArrowTop.setVisibility(View.VISIBLE);
			mArrowBottom.setVisibility(View.INVISIBLE);
			mArrowLeft.setVisibility(View.GONE);
			mArrowRight.setVisibility(View.GONE);
			
			// 中间
			params.setMargins(0, mArrowTop.getMeasuredHeight() -3, 0, 0);
			
			mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			anchorCenterX = mAnchorLocations[0] + anchor.getWidth() / 2;
			
			// Popup Window
			locations[0] = anchorCenterX - mRootView.getMeasuredWidth() / 2;
			locations[1] = mAnchorLocations[1] + anchor.getHeight();
			
			if (locations[0] <= 0) {
				locations[0] = 0;
			} else if (locations[0] + mRootView.getMeasuredWidth() >= mScreenWidth) {
				locations[0] = mScreenWidth - mRootView.getMeasuredWidth();
			}
			
			// 箭头
			arrowParams = (RelativeLayout.LayoutParams) 
					mArrowTop.getLayoutParams();
			arrowPos = anchorCenterX - locations[0] - mArrowTop.getMeasuredWidth() / 2;
			arrowParams.setMargins(arrowPos, 0, 0, 0);
			
			break;
			
		case LEFT:
			mArrowTop.setVisibility(View.INVISIBLE);
			mArrowBottom.setVisibility(View.INVISIBLE);
			mArrowLeft.setVisibility(View.GONE);
			mArrowRight.setVisibility(View.VISIBLE);
			params.setMargins(0, 0, -3, 0);
			mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			anchorCenterY = mAnchorLocations[1] + anchor.getHeight() / 2;
			
			locations[0] = mAnchorLocations[0] - mRootView.getMeasuredWidth();
			locations[1] = anchorCenterY - mRootView.getMeasuredHeight() / 2;
			
			if (locations[1] <= 0) {
				locations[1] = 0;
			} else if (locations[1] + mRootView.getMeasuredHeight() >= mScreenHeight) {
				locations[1] = mScreenHeight - mRootView.getMeasuredHeight();
			}			
			
			// 箭头
			arrowParams = (RelativeLayout.LayoutParams) 
					mArrowRight.getLayoutParams();
			arrowPos = anchorCenterY - locations[1] - mArrowRight.getMeasuredHeight() / 2;
			arrowParams.setMargins(0, arrowPos, 0, 0);
			
			break;
			
		case RIGHT:
			mArrowTop.setVisibility(View.INVISIBLE);
			mArrowBottom.setVisibility(View.INVISIBLE);
			mArrowLeft.setVisibility(View.VISIBLE);
			mArrowRight.setVisibility(View.GONE);
			params.setMargins(mArrowLeft.getMeasuredWidth() -3, 0, 0, 0);
			mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			anchorCenterY = mAnchorLocations[1] + anchor.getHeight() / 2;
			
			locations[0] = mAnchorLocations[0] + anchor.getWidth();
			locations[1] = anchorCenterY - mRootView.getMeasuredHeight() / 2;
			
			if (locations[1] <= 0) {
				locations[1] = 0;
			} else if (locations[1] + mRootView.getMeasuredHeight() >= mScreenHeight) {
				locations[1] = mScreenHeight - mRootView.getMeasuredHeight();
			}			
			
			// 箭头
			arrowParams = (RelativeLayout.LayoutParams) 
					mArrowLeft.getLayoutParams();
			arrowPos = anchorCenterY - locations[1] - mArrowLeft.getMeasuredHeight() / 2;
			arrowParams.setMargins(0, arrowPos, 0, 0);
			break;
			
		}
		
		return locations;
	}

	private String test(Direction direction) {
		String value = new String();

		if (direction == null) {
			return value;
		}
		
		switch (direction) {
		case TOP:
			value = "TOP";
			break;

		case BOTTOM:
			value = "BOTTOM";
			break;
			
		case LEFT:
			value = "LEFT";
			break;
		case RIGHT:
			value = "RIGHT";
			break;
		}
		return value;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public enum Direction {
		LEFT, RIGHT, TOP, BOTTOM
	}

	public interface OnClickQuickActionListener{
		public void OnClickQuickAction(int actionId);
	}
	
}
