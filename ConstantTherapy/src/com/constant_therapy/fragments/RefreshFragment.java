package com.constant_therapy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.SymbalTasks;

public class RefreshFragment extends Fragment implements OnClickListener {

	ViewGroup mRootView;

	ImageView imgRefresh;

	int padding;

	private DisplayMetrics mDisplayMetrics;

	OnRefreshClickListener mCallback;

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnRefreshClickListener {
		/** Called by HeadlinesFragment when a list item is selected */

		public void onRefreshClick(View v);
	}

	public static RefreshFragment newInstance(String tasks) {

		RefreshFragment fragmentDemo = new RefreshFragment();
		Bundle args = new Bundle();
		args.putString("someString", tasks);

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

		mRootView = (ViewGroup) inflater.inflate(R.layout.refresh_frame,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		imgRefresh = (ImageView) getActivity().findViewById(R.id.imgRefresh);
		
		imgRefresh.setOnClickListener(this);

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
			mCallback = (OnRefreshClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mCallback.onRefreshClick(v);
	}

}
