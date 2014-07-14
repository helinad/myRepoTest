package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.AuditoryTasks;
import com.constant_therapy.dashboard.SymbalTasks;
import com.constant_therapy.util.AuditoryDropAdapter;
import com.constant_therapy.util.SymbolMatchAdapter;

public class AuditoryFragment extends Fragment {

	ViewGroup mRootView;
	GridView gridLay;

	AuditoryTasks resources;
	AQuery androidAQuery;
	ImageView imgQuestion;
	LinearLayout llCheckAns;

	int padding;

	private DisplayMetrics mDisplayMetrics;

	static MyDragEventListener myDragEventListener;

	public AuditoryFragment(MyDragEventListener myDragEventListener) {
		this.myDragEventListener = myDragEventListener;
	}

	public AuditoryFragment() {

	}

	public static AuditoryFragment newInstance(String tasks,
			AuditoryTasks taskarray) {

		AuditoryFragment fragmentDemo = new AuditoryFragment(
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
		androidAQuery = new AQuery(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.auditory_drop_grid,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.auditory_grid);
		gridLay.setBackgroundColor(getActivity().getResources().getColor(
				R.color.bg_light_gray));
		gridLay.setPadding(5, 5, 5, 5);
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (AuditoryTasks) args.getSerializable("taskArray");
			gridLay.setNumColumns(resources.getColumns());
			mDisplayMetrics = getResources().getDisplayMetrics();
			/*
			 * padding = getBarHeight(resources.getItems().size());
			 * //gridLay.setPadding(padding/2, 0, (int) (padding*1.5), 0);
			 */
			updateTableView((AuditoryTasks) args.getSerializable("taskArray"));
		}

	}

	public void updateTableView(AuditoryTasks taskImages) {
		gridLay.setNumColumns(taskImages.getColumns());
		ArrayList<String> images = new ArrayList<String>();
		ArrayList<Integer> location = new ArrayList<Integer>();
		ArrayList<Integer> actionlocation = new ArrayList<Integer>();

		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		for (int i = 0; i < taskImages.getAutofill().size(); i++) {
			images.add(taskImages.getAutofill().get(i).getItem().getImagePath());
			location.add(taskImages.getAutofill().get(i).getLocation());

			hashmap.put(taskImages.getAutofill().get(i).getLocation(),
					taskImages.getAutofill().get(i).getItem().getImagePath());

		}

		for (int i = 0; i < taskImages.getActions().size(); i++) {
			images.add(taskImages.getActions().get(i).getItem().getImagePath());

			actionlocation.add(taskImages.getActions().get(i).getLocation());

			hashmap.put(taskImages.getActions().get(i).getLocation(),
					taskImages.getActions().get(i).getItem().getImagePath());

		}
		  Map<Integer, String> map = new TreeMap<Integer, String>(hashmap); 
	        

		AuditoryDropAdapter adapter = new AuditoryDropAdapter(getActivity(),
				map, location, actionlocation, myDragEventListener);
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
		/*
		 * try { mCallback = (OnMatchingSymbolClickListener) activity; } catch
		 * (ClassCastException e) { throw new
		 * ClassCastException(activity.toString() +
		 * " must implement OnHeadlineSelectedListener"); }
		 */
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {
			switch (count) {
			case 9:
				return 200;

			case 16:

				return 200;
			case 36:

				return 160;
			case 49:

				return 160;
			case 30:

				return 180;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {
			switch (count) {
			case 9:
				return 200;

			case 16:

				return 200;
			case 36:

				return 170;
			case 49:

				return 160;
			case 30:

				return 180;

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
			case 9:
				return 200;

			case 16:

				return 200;
			case 36:

				return 160;
			case 49:

				return 160;
			case 30:

				return 180;

			default:
				break;
			}
			return 170;
		}

		default:
			switch (count) {
			case 9:
				return 270;

			case 16:

				return 260;
			case 36:

				return 250;
			case 49:

				return 250;
			case 30:

				return 180;

			default:
				break;
			}
			return 170;
		}

	}

}
