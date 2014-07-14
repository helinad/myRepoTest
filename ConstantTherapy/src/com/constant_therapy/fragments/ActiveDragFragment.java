package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.ActiveTask;
import com.constant_therapy.util.ActiveDragAdapter;

public class ActiveDragFragment extends Fragment {

	ViewGroup mRootView;
	GridView gridLay;

	ActiveTask resources;
	AQuery androidAQuery;

	int padding;
	View pView;
	private float mDownX;
	private float mDownY;
	private final float SCROLL_THRESHOLD = 10;
	private boolean isOnClick;

	
	private DisplayMetrics mDisplayMetrics;

	static MyDragEventListener myDragEventListener;
	OnActiveDragListener mCallback;

	public ActiveDragFragment(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	public ActiveDragFragment() {

	}

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnActiveDragListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onActiveDrag(View v, int position);

	}

	public static ActiveDragFragment newInstance(String tasks,
			ActiveTask taskarray) {

		ActiveDragFragment fragmentDemo = new ActiveDragFragment(
				myDragEventListener);
		Bundle args = new Bundle();
		args.putString("someString", tasks);
		args.putSerializable("taskArray", taskarray);
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

		mRootView = (ViewGroup) inflater.inflate(R.layout.spoke_word_frame,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.grid);

		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (ActiveTask) args.getSerializable("taskArray");

			updateTableView(resources);
		}

		gridLay.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				GridView parent = (GridView) v;
				int x = (int) ev.getX();
				int y = (int) ev.getY();

				int position = parent.pointToPosition(x, y);
				if (position != -1) {
					pView = parent.getChildAt(position);

				}

				switch (ev.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mDownX = ev.getX();
					mDownY = ev.getY();
					isOnClick = true;
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					
					break;
				case MotionEvent.ACTION_MOVE:
					if (isOnClick
							&& (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD || Math
									.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
						Log.i("", "movement detected");
						isOnClick = false;
						if (position != -1 && ev.getPointerCount() == 1) {
							pView = parent.getChildAt(position);
							try{
								if(pView != null)
									mCallback.onActiveDrag(pView, position);
							}catch (ConcurrentModificationException e) {
								// TODO: handle exception
								pView.setVisibility(View.VISIBLE);
							}
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
		setRetainInstance(true);

	}

	public void updateTableView(ActiveTask taskImages) {
		List<String> letters = new ArrayList<String>();
		

		for (int i = 0; i < taskImages.getDistractors().size(); i++) {
			letters.add(taskImages.getDistractors().get(i).getText());
		}
		for (int i = 0; i < taskImages.getSegments().size(); i++) {
			letters.add(taskImages.getSegments().get(i).getText());
		}

		long seed = System.nanoTime();
		Collections.shuffle(letters, new Random(seed));
		gridLay.setNumColumns(3);

		ActiveDragAdapter adapter = new ActiveDragAdapter(getActivity(),
				letters, myDragEventListener);
		gridLay.setAdapter(adapter);
		DoingTaskActivity.gridDrag = gridLay;

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
			mCallback = (OnActiveDragListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {
			switch (count) {
			case 6:
				return 200;

			case 12:

				return 220;
			case 20:

				return 220;
			case 24:

				return 220;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {
			switch (count) {
			case 6:
				return 205;

			case 12:

				return 250;
			case 20:

				return 280;
			case 24:

				return 220;
			case 30:

				return 280;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_LOW: {
			switch (count) {
			case 6:
				return 170;

			case 12:

				return 110;
			case 20:

				return 170;
			case 24:

				return 170;
			case 30:

				return 170;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 220;
			case 20:

				return 220;
			case 24:

				return 220;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
		}

		default:
			switch (count) {
			case 6:
				return 340;

			case 12:

				return 360;
			case 20:

				return 360;
			case 24:

				return 360;
			case 30:

				return 370;

			default:
				break;
			}
			return 170;

		}

	}

}
