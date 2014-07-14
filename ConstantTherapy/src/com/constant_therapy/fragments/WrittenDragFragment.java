package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
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
import com.constant_therapy.dashboard.WrittenTasks;
import com.constant_therapy.util.WrittenDragAdapter;

public class WrittenDragFragment extends Fragment implements OnGestureListener {

	ViewGroup mRootView;
	GridView gridLay;

	WrittenTasks resources;
	AQuery androidAQuery;

	int padding;

	private DisplayMetrics mDisplayMetrics;
	View pView;
	int vPosition;
	//private GestureDetectorCompat mDetector;

	static MyDragEventListener myDragEventListener;
	OnWrittenDragListener mCallback;
	private float mDownX;
	private float mDownY;
	private final float SCROLL_THRESHOLD = 10;
	private boolean isOnClick;

	public WrittenDragFragment(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	public WrittenDragFragment() {

	}

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnWrittenDragListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onWrittenDrag(View v, int position);

		public void onWrittenClick(View v, int position);
	}

	public static WrittenDragFragment newInstance(String tasks,
			WrittenTasks taskarray) {

		WrittenDragFragment fragmentDemo = new WrittenDragFragment(
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

		mRootView = (ViewGroup) inflater.inflate(R.layout.written_drop_grid,
				container, false);
	

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.grid_Layout);
		//mDetector = new GestureDetectorCompat(getActivity(), this);
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (WrittenTasks) args.getSerializable("taskArray");
			gridLay.setNumColumns(7);

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
					if (isOnClick && ev.getPointerCount() == 1) {
						Log.i("", "onClick ");
						// TODO onClick code
						if (position != -1) {
							pView = parent.getChildAt(position);
							try{
								mCallback.onWrittenClick(pView, position);
							}catch (ConcurrentModificationException e) {
								// TODO: handle exception
								parent.getChildAt(position).setVisibility(View.VISIBLE);
							}
						}
					}
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
									mCallback.onWrittenDrag(pView, position);
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

	}

	public void updateTableView(WrittenTasks taskImages) {
		List<String> letters = new ArrayList<String>();
		char[] nameArray = new char[taskImages.getItem().getName().length()];
		nameArray = taskImages.getItem().getName().toCharArray();
		for (int i = 0; i < nameArray.length; i++) {
			letters.add(String.valueOf(nameArray[i]));
		}

		letters.addAll(taskImages.getDistractorLetters());
		long seed = System.nanoTime();
		Collections.shuffle(letters, new Random(seed));
		WrittenDragAdapter adapter = new WrittenDragAdapter(getActivity(),
				letters, taskImages.getFilledLetterIndices(), taskImages
						.getItem().getName(), myDragEventListener);
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
			mCallback = (OnWrittenDragListener) activity;
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

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		mCallback.onWrittenDrag(pView, vPosition);
		return true;
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
		mCallback.onWrittenClick(pView, vPosition);
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
