package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.SymbalTasks;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.util.MatchWordAdapter;
import com.constant_therapy.util.SymbolMatchAdapter;

public class SymbolMatchingFragment  extends Fragment implements OnClickListener{
	
	ViewGroup mRootView;
	GridView gridLay;
	
	SymbalTasks resources;
	AQuery androidAQuery;
	ImageView imgQuestion;
	LinearLayout llCheckAns;

	int padding;

	private DisplayMetrics mDisplayMetrics;
	
	
	OnMatchingSymbolClickListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnMatchingSymbolClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onMatchingSymbolClick(View v, int position);
        
        public void onCheckSymbolClick(View v);
    }
	public static SymbolMatchingFragment newInstance(String tasks, SymbalTasks taskarray) {

		SymbolMatchingFragment fragmentDemo = new SymbolMatchingFragment();
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

		mRootView = (ViewGroup) inflater.inflate(R.layout.symbol_frame,
				container, false);

		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gridLay = (GridView) getActivity().findViewById(R.id.grid_Layout);
		imgQuestion = (ImageView)getActivity().findViewById(R.id.imgQuestion);
		llCheckAns = (LinearLayout)getActivity().findViewById(R.id.llCheckAns);
		
		llCheckAns.setOnClickListener(this);
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			resources = (SymbalTasks) args.getSerializable("taskArray");
			gridLay.setNumColumns(resources.getColumns());
			mDisplayMetrics = getResources().getDisplayMetrics();
			padding = getBarHeight(resources.getItems().size());
			gridLay.setPadding(padding/2, 0, (int) (padding*1.5), 0);
			
			updateTableView((SymbalTasks) args.getSerializable("taskArray"));
		}
		
		
		gridLay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				mCallback.onMatchingSymbolClick(v, position);
			}
		});
		
		

	}
	
	

	
	
	



	public void updateTableView(SymbalTasks taskImages) {
		androidAQuery = new AQuery(getActivity());
		String[] url = taskImages.getMatchingSymbols().toString().split("=");
		String imageUrl = taskImages.getResourceUrl() + url[0].substring(1);
		
	  
        androidAQuery.id(imgQuestion).image(imageUrl, true, true, 0,0, new BitmapAjaxCallback() {
					@Override
					public void callback(String url,
							ImageView iv, Bitmap bm,
							AjaxStatus status) {

						imgQuestion.setImageBitmap(bm);
						//imgQuestion.setScaleType(ScaleType.FIT_XY);
						
					}
				}.header("User-Agent", "android"));
		SymbolMatchAdapter adapter = new SymbolMatchAdapter(getActivity(), taskImages.getItems(), taskImages.getResourceUrl());
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
            mCallback = (OnMatchingSymbolClickListener) activity;
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 mCallback.onCheckSymbolClick(v);
	}
	
	

	
}
