package com.constant_therapy.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.PatternTasks;
import com.constant_therapy.util.PatternAdapter;

public class PatternFragment extends Fragment{
	
	ViewGroup mRootView;
	GridView gridLay;
	
	PatternTasks resources;
	AQuery androidAQuery;
	ImageView imgQuestion;
	LinearLayout llCheckAns;
	View view;
	int padding;

	private DisplayMetrics mDisplayMetrics;
	
	
	OnPatternClickListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnPatternClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onPatternClick(View v, int position);
       
    }
	public static PatternFragment newInstance(String tasks, PatternTasks taskarray) {

		PatternFragment fragmentDemo = new PatternFragment();
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

		mRootView = (ViewGroup) inflater.inflate(R.layout.written_grid,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.grid_Layout);
		view = (View)getActivity().findViewById(R.id.inner_view);
		
		DoingTaskActivity.innerView = view;
		
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (PatternTasks) args.getSerializable("taskArray");
			gridLay.setNumColumns(resources.getColumns());
			
			updateTableView((PatternTasks) args.getSerializable("taskArray"));
		}
		
		
		gridLay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				mCallback.onPatternClick(v, position);
			}
		});
		
		

	}
	
	

	
	
	



	public void updateTableView(PatternTasks taskImages) {
		ArrayList<Integer> itemPosition = new ArrayList<Integer>();
		
		for(int i =0; i< taskImages.getActions().size(); i++){
			itemPosition.add(taskImages.getActions().get(i).getLocation());
		}
		
		PatternAdapter adapter = new PatternAdapter(getActivity(), itemPosition, taskImages.getColumns()*taskImages.getRows());
		gridLay.setAdapter(adapter);
		
		

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
            mCallback = (OnPatternClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH:{
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
			
		case DisplayMetrics.DENSITY_MEDIUM:{
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
			
		case DisplayMetrics.DENSITY_LOW:{
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
			
		case DisplayMetrics.DENSITY_TV:{
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

