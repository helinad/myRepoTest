package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.ActiveTask;
import com.constant_therapy.dashboard.OrderTasks;
import com.constant_therapy.util.ActiveDropAdapter;
import com.constant_therapy.util.OrderDropAdapter;

public class OrderDropFragment extends Fragment {

	ViewGroup mRootView;
	public GridView gridLay;

	OrderTasks resources;
	AQuery androidAQuery;

	int padding;

	private DisplayMetrics mDisplayMetrics;

	static MyDragEventListener myDragEventListener;

	public OrderDropFragment(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	public OrderDropFragment() {

	}

	public static OrderDropFragment newInstance(boolean isWord,
			OrderTasks taskarray) {

		OrderDropFragment fragmentDemo = new OrderDropFragment(
				myDragEventListener);
		Bundle args = new Bundle();
		args.putBoolean("isWord", isWord);
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

		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (OrderTasks) args.getSerializable("taskArray");

			updateTableView( args.getBoolean("isWord"), resources);
		}

	}

	public void updateTableView(boolean isWord, OrderTasks taskImages) {
		gridLay.setNumColumns(taskImages.getItems().size());
		
		List<String> items = new ArrayList<String>();
		if(isWord){
			for (int i = 0; i < taskImages.getItems().size(); i++) {
	
				items.add(taskImages.getItems().get(i).getName());
			}
			OrderDropAdapter adapter = new OrderDropAdapter(getActivity(), items, true,
					myDragEventListener);
			gridLay.setAdapter(adapter);
		}else{
			for (int i = 0; i < taskImages.getItems().size(); i++) {
				
				items.add(taskImages.getItems().get(i).getImagePath());
			}
			OrderDropAdapter adapter = new OrderDropAdapter(getActivity(), items,false,
					myDragEventListener);
			gridLay.setAdapter(adapter);
			
		}
		DoingTaskActivity.gridDrop = gridLay;

	}

	private int getMissing(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i != list.get(i))
				return i;
		}
		return 0;
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

