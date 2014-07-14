package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class DragMatchingFragment extends Fragment implements OnTouchListener, OnGestureListener {

	ViewGroup mRootView;
	GridView gridLay;
	CTTextView tvFirst;
	CTTextView tvSecond;
	ImageView imgFirst, imgSecond;
	static MyDragEventListener myDragEventListener;
	private GestureDetectorCompat mDetector;
	
	AQuery androidAQuery;
	ArrayList<String> resources = new ArrayList<String>();

	int padding;

	private DisplayMetrics mDisplayMetrics;
	View pView;
	boolean isSound = false;
	private float mDownX;
	private float mDownY;
	private final float SCROLL_THRESHOLD = 10;
	private boolean isOnClick;

	OnMatchingDragListener mCallback;
	
	public DragMatchingFragment(MyDragEventListener myDragEventListener){
    	this.myDragEventListener = myDragEventListener;
    }
    
    public DragMatchingFragment(){
    	
    }

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnMatchingDragListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onMatchingDrag(View v);
		public void onMatchingSoundClick(View v);
	}

	public static DragMatchingFragment newInstance(Boolean sound,
			String taskarray, ArrayList<String> resource) {

		DragMatchingFragment fragmentDemo = new DragMatchingFragment(myDragEventListener);
		Bundle args = new Bundle();
		args.putBoolean("sound", sound);
		args.putString("taskArray", taskarray);
		
		args.putStringArrayList("resource", resource);
		fragmentDemo.setArguments(args);
		return fragmentDemo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.drag_matchingframe,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tvFirst = (CTTextView) getActivity().findViewById(R.id.tvDragFirst);
		tvSecond = (CTTextView) getActivity().findViewById(R.id.tvDragSecond);
		imgFirst = (ImageView) getActivity().findViewById(R.id.imgFirst);
		imgSecond = (ImageView) getActivity().findViewById(R.id.imgSecond);
		
		mDetector = new GestureDetectorCompat(getActivity(), this);


		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			if (!args.getBoolean("sound")) {
				isSound = false;
				updateTableView(args.getString("taskArray"));
			} else {
				isSound = true;
				resources = args.getStringArrayList("resource");
				tvFirst.setVisibility(View.INVISIBLE);
				tvSecond.setVisibility(View.INVISIBLE);
				
				imgFirst.setVisibility(View.VISIBLE);
				imgSecond.setVisibility(View.VISIBLE);
				long seed = System.nanoTime();
				Collections.shuffle(resources, new Random(seed));
				imgFirst.setTag(resources.get(0));
				imgSecond.setTag(resources.get(1));
				
				imgFirst.setOnDragListener((OnDragListener) myDragEventListener);
				imgSecond
						.setOnDragListener((OnDragListener) myDragEventListener);
				imgFirst.setOnTouchListener(this);
				imgSecond.setOnTouchListener(this);
				
				/*imgFirst.setOnClickListener(this);
				imgSecond.setOnClickListener(this);
				imgFirst.setOnLongClickListener(this);
				imgSecond.setOnLongClickListener(this);
				*/
				
				
				
				
				DoingTaskActivity.imgFirstDrag = imgFirst;
				DoingTaskActivity.imgSecondDrag = imgSecond;
				
			}
		}
		setRetainInstance(true);
	}

	public void updateTableView(String taskImages) {
		imgFirst.setVisibility(View.INVISIBLE);
		imgSecond.setVisibility(View.INVISIBLE);
		
		tvFirst.setVisibility(View.VISIBLE);
		tvSecond.setVisibility(View.VISIBLE);
		
		String[] str = taskImages.split(",");
		tvFirst.setText(str[0]);
		tvSecond.setText(str[1]);
		List<String> sortList = new ArrayList<String>();
		sortList = Arrays.asList(str);
		long seed = System.nanoTime();
		Collections.shuffle(sortList, new Random(seed));
		
		tvFirst.setTag(sortList.get(0));
		tvSecond.setTag(sortList.get(1));
		
		tvFirst.setOnDragListener((OnDragListener) myDragEventListener);
		tvSecond.setOnDragListener((OnDragListener) myDragEventListener);
		
		
		tvFirst.setOnTouchListener(this);
		tvSecond.setOnTouchListener(this);
		
		
		DoingTaskActivity.tvFirstDrag = tvFirst;
		DoingTaskActivity.tvSecondDrag = tvSecond;

		///tvFirst.setOnTouchListener(this);
		//.setOnTouchListener(this);
		
	}

	@Override
	public void onStart() {
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already
		// been
		// applied to the fragment at this point so we can safely call the
		// method
		// below that sets the article text.

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnMatchingDragListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		
		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mDownX = ev.getX();
			mDownY = ev.getY();
			isOnClick = true;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			if (isOnClick) {
				Log.i("", "onClick ");
				// TODO onClick code
				if (ev.getPointerCount() == 1) {
					
					mCallback.onMatchingSoundClick(v);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isOnClick
					&& (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD || Math
							.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
				Log.i("", "movement detected");
				isOnClick = false;
				if (ev.getPointerCount() == 1) {
					
					mCallback.onMatchingDrag(v);
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		if(e.getAction() == MotionEvent.ACTION_DOWN ){
			mCallback.onMatchingDrag(pView);
			return true;
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		if(isSound)
			mCallback.onMatchingSoundClick(pView);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
