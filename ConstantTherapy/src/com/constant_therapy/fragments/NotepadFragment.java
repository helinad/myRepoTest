package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CurlFlipLayout;
import com.constant_therapy.widget.DrawView;

public class NotepadFragment extends Fragment{
	ImageView imgClose;

	DrawView drawView;
	CurlFlipLayout llDrawView;
	ViewGroup mRootView;
	
	

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			  
	}
	
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		mRootView = (ViewGroup)inflater.inflate(R.layout.notepad_frame, container, false);
		  
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        
        Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			
		}
        
    }

    

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		imgClose = (ImageView) getActivity().findViewById(R.id.imgClose);
		
		llDrawView = (CurlFlipLayout)getActivity().findViewById(R.id.llNotePad);
		
		drawView = new DrawView(getActivity());
		llDrawView.addView(drawView);
		drawView.changeColour(Color.BLACK);
		drawView.requestFocus();
		DoingTaskActivity.drawView = drawView;
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				llDrawView.next();
				drawView.clearPoints();
			}
		});
		
		

	}
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
       
    }
    

	


	
}

