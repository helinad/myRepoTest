package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.AuditoryTasks;
import com.constant_therapy.dashboard.SpokenWordTask;
import com.constant_therapy.fragments.SpokenWordFragment.OnSpokenDragListener;
import com.constant_therapy.util.AuditoryDragAdapter;
import com.constant_therapy.util.SpokenWordAdapter;

public class AuditoryDragFragment extends Fragment {

	ViewGroup mRootView;
	GridView gridLay;

	AuditoryTasks resources;
	AQuery androidAQuery;

	int padding;
	private float mDownX;
	private float mDownY;
	private final float SCROLL_THRESHOLD = 10;
	private boolean isOnClick;
	View pView;


	private DisplayMetrics mDisplayMetrics;

	static MyDragEventListener myDragEventListener;
	OnAuditoryDragListener mCallback;

	public AuditoryDragFragment(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	public AuditoryDragFragment() {

	}

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnAuditoryDragListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onAuditoryDrag(View v, int position);
		public void onAuditoryClick(View v, int position);

	}

	public static AuditoryDragFragment newInstance(String tasks, AuditoryTasks taskarray) {

		AuditoryDragFragment fragmentDemo = new AuditoryDragFragment(
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

		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (AuditoryTasks) args.getSerializable("taskArray");

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
					if (isOnClick) {
						Log.i("", "onClick ");
						// TODO onClick code
						if (position != -1 && ev.getPointerCount() == 1) {
								
							pView = parent.getChildAt(position);
							try{
								mCallback.onAuditoryClick(pView, position);
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
								mCallback.onAuditoryDrag(pView, position);
							}catch (ConcurrentModificationException e) {
								// TODO: handle exception
								parent.getChildAt(position).setVisibility(View.VISIBLE);
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

	public void updateTableView(AuditoryTasks taskImages) {
		gridLay.setNumColumns(5);
		ArrayList<String> resources = new ArrayList<String>();
		for (int i = 0; i < taskImages.getDistractors().size(); i++) {
				resources.add(taskImages.getDistractors().get(i).getImagePath());
			
		}
		
		for (int i = 0; i < taskImages.getActions().size(); i++) {
			resources.add(taskImages.getActions().get(i).getItem().getImagePath());
		
		}
		long seed = System.nanoTime();
		Collections.shuffle(resources, new Random(seed));
		AuditoryDragAdapter adapter = new AuditoryDragAdapter(getActivity(),
				resources, getActivity().getString(R.string.resource_url), myDragEventListener);
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
			mCallback = (OnAuditoryDragListener) activity;
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
