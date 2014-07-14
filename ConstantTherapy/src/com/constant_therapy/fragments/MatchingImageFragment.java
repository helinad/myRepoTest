package com.constant_therapy.fragments;

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

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.util.MatchFragmentAdapter;

public class MatchingImageFragment extends Fragment{
	
	ViewGroup mRootView;
	GridView gridLay;
	
	Tasks resources;
	AQuery androidAQuery;

	int padding;

	private DisplayMetrics mDisplayMetrics;
	
	
	OnMatchingImageClickListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnMatchingImageClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onMatchingImageClick(View v, int position);
    }
	public static MatchingImageFragment newInstance(String tasks, Tasks taskarray) {

		MatchingImageFragment fragmentDemo = new MatchingImageFragment();
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

		mRootView = (ViewGroup) inflater.inflate(R.layout.temp_matching, container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.grid_Layout);
		
		//gridLay.setOnClickListener(this);
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (Tasks) args.getSerializable("taskArray");
			gridLay.setNumColumns(resources.getColumns());
			mDisplayMetrics = getResources().getDisplayMetrics();
			padding = getBarHeight(resources.getResources().size());
			gridLay.setPadding(padding, 0, padding, 0);
			
			updateTableView((Tasks) args.getSerializable("taskArray"));
		}
		
		
		gridLay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				mCallback.onMatchingImageClick(v, position);
			}
		});
		
		
		setRetainInstance(true);
	}
	
	

	
	
	



	public void updateTableView(Tasks taskImages) {
		MatchFragmentAdapter adapter = new MatchFragmentAdapter(getActivity(),
				taskImages.getResources(), taskImages.getResourceUrl());
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
            mCallback = (OnMatchingImageClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH:{
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
			
		case DisplayMetrics.DENSITY_MEDIUM:{
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
				return 350;
				
			case 12:

				return 380;
			case 20:

				return 400;
			case 24:

				return 360;
			case 30:

				return 480;

			default:
				break;
			}
			return 170;

			
			}
			
		
		
	}
	

	
}
