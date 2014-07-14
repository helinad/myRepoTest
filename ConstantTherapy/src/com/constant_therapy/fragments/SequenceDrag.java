package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.DraggingTask;
import com.constant_therapy.util.SequenceDragAdapter;

public class SequenceDrag extends Fragment {

	ViewGroup mRootView;
	LinearLayout wordLayout;
	DraggingTask dragtask;
	ArrayList<View> views;
	SequenceDragAdapter adapter;
	int randomCount = 0;
	View vi, pView;
	static MyDragEventListener myDragEventListener;
	OnSequenceDropListener mCallback;
	public static ListView list1;
	int pos;
	private float mDownX;
	private float mDownY;
	private final float SCROLL_THRESHOLD = 10;
	private boolean isOnClick;


	public SequenceDrag(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	ArrayList<String> resources;

	public SequenceDrag() {

	}

	public static SequenceDrag newInstance(DraggingTask dragtask, int pos) {
		SequenceDrag fragmentDemo = new SequenceDrag(myDragEventListener);
		Bundle args = new Bundle();

		args.putSerializable("sequence", dragtask);
		args.putInt("pos", pos);
		fragmentDemo.setArguments(args);
		return fragmentDemo;
	}

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnSequenceDropListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onSequenceDrag(View v, int position);

		public void onSequenceClick(View v, int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.dragging_frame,
				container, false);

		// view
		return mRootView;

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
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		list1 = (ListView) mRootView.findViewById(R.id.list1);
		DoingTaskActivity.listDrag = list1;
		if (list1.getChildCount() > 0)
			list1.removeAllViews();

		resources = new ArrayList<String>();

		Bundle args = getArguments();

		if (args != null) {
			dragtask = (DraggingTask) args.getSerializable("sequence");
			pos = args.getInt("pos");
			updateTableView(dragtask.getTasks().get(pos).sequence);

			list1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent ev) {
					ListView parent = (ListView) v;
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
									mCallback.onSequenceClick(pView, position);
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
										mCallback.onSequenceDrag(pView, position);
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
		/*
		 * wordLayout=(LinearLayout) getActivity().findViewById( R.id.drag1);
		 * LayoutInflater inflater = (LayoutInflater) getActivity()
		 * .getBaseContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
		 * Log.v("",""+dragtask.getTasks().get(0).sequence.size());
		 * 
		 * for(int i=0;i<dragtask.getTasks().get(0).sequence.size();i++) {
		 * //Log.v("",""+dragtask.getTasks().get(0).sequence.get(i)); vi =
		 * inflater.inflate(R.layout.dragword_frame,null); TextView word =
		 * (TextView) vi.findViewById(R.id.taskWord);
		 * word.setText(dragtask.getTasks().get(0).sequence.get(i));
		 * wordLayout.addView(vi);
		 */

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the
		// fragment
		// outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnSequenceDropListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	public void updateTableView(ArrayList<String> sequence) {

		resources.clear();
		for (int i = 0; i < sequence.size(); i++) {

			resources.add(sequence.get(i));

		}
		Log.v("resources1", "" + resources.get(0));
		long seed = System.nanoTime();
		Collections.shuffle(resources, new Random(seed));

		adapter = new SequenceDragAdapter(getActivity().getBaseContext(),
				resources, myDragEventListener);
		Log.v("11", "" + dragtask.getTasks().get(pos).sequence.get(0));
		list1.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

}
