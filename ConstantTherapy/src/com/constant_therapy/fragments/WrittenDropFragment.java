package com.constant_therapy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.dashboard.WrittenTasks;
import com.constant_therapy.fragments.WrittenDragFragment.OnWrittenDragListener;
import com.constant_therapy.util.MatchFragmentAdapter;
import com.constant_therapy.util.WrittenDragAdapter;
import com.constant_therapy.util.WrittenDropAdapter;

public class WrittenDropFragment extends Fragment {

	ViewGroup mRootView;
	GridView gridLay;

	WrittenTasks resources;
	AQuery androidAQuery;

	int padding;

	private DisplayMetrics mDisplayMetrics;



	static MyDragEventListener myDragEventListener;

	public WrittenDropFragment(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	public WrittenDropFragment() {

	}

	

	public static WrittenDropFragment newInstance(String tasks,
			WrittenTasks taskarray) {

		WrittenDropFragment fragmentDemo = new WrittenDropFragment(
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

		mRootView = (ViewGroup) inflater.inflate(R.layout.written_grid,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.grid_Layout);
		gridLay.setPadding(0, 10, 0, 0);
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (WrittenTasks) args.getSerializable("taskArray");
	
			updateTableView(resources);
		}

	}

	public void updateTableView(WrittenTasks taskImages) {
		gridLay.setNumColumns(taskImages.getItem().getName().length());
		WrittenDropAdapter adapter = new WrittenDropAdapter(getActivity(),
				taskImages.getFilledLetterIndices(), taskImages.getItem()
						.getName(), myDragEventListener);
		gridLay.setAdapter(adapter);
		DoingTaskActivity.gridDrop = gridLay;

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
		
	}

	

}
