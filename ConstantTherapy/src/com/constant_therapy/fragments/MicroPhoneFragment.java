package com.constant_therapy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;

public class MicroPhoneFragment extends Fragment implements OnClickListener{
	LinearLayout llMicrophone;
	Button btnStart;
	FrameLayout flWait;
	ViewGroup mRootView;
	OnMicroPhoneClickListener mCallback;
	

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnMicroPhoneClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
           
            public void OnStartButtonClick(View v);
        
    }

	 public static MicroPhoneFragment newInstance(int someInt, String imageUrl) {
		 MicroPhoneFragment fragmentDemo = new MicroPhoneFragment();
	        Bundle args = new Bundle();
	        args.putInt("someInt", someInt);
	        args.putString("imageUrl", imageUrl);
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
		mRootView = (ViewGroup)inflater.inflate(R.layout.microphone_frame, container, false);
		
		
		
		  
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        
       
        
    }

   

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		llMicrophone = (LinearLayout) getActivity().findViewById(R.id.imgMicrophone);
		btnStart = (Button) getActivity().findViewById(R.id.btnStart);
		flWait = (FrameLayout) getActivity().findViewById(R.id.imgOuter);
		
		llMicrophone.setBackground(getResources().getDrawable(R.drawable.circle_bg));
		
		btnStart.setClickable(true);
		btnStart.setText(getString(R.string.start));
		btnStart.setOnClickListener(this);
		btnStart.setVisibility(View.VISIBLE);
		flWait.setVisibility(View.INVISIBLE);
		DoingTaskActivity.btnStart = btnStart;
		DoingTaskActivity.flWait = flWait;
		DoingTaskActivity.llMicrophone = llMicrophone;
		
		

	}
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnMicroPhoneClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMicroPhoneClickListener");
        }
    }
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		
		mCallback.OnStartButtonClick(v);
		
		
	}
}

