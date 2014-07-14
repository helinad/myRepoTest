package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.R;


public class ImageAudioFragemt extends Fragment implements OnClickListener{
	ImageView imgSimuli, imgSimuliAudio;
	ViewGroup mRootView;
	MediaPlayer mPlayer;
	private AQuery androidAQuery;
	OnSimuliClickListener mCallback;
	

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnSimuliClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onSimuliAudioClick(View v);
        public void onSimuliImageZoom(View v);
        
    }

	 public static ImageAudioFragemt newInstance(int someInt, String imageUrl) {
		 ImageAudioFragemt fragmentDemo = new ImageAudioFragemt();
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
		mRootView = (ViewGroup)inflater.inflate(R.layout.image_frame, container, false);
		
		
		
		  
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
			updateArticleView(args.getString("imageUrl"));
		}
        
    }

    public void updateArticleView(String imageUrl) {

    	  //imgSimuli.setImageResource(id);
    	androidAQuery = new AQuery(getActivity());
    	androidAQuery.id(imgSimuli).image(imageUrl, true, true, 0, 0, new
    			  BitmapAjaxCallback() {
    			  
    			  @Override public void callback(String url, ImageView iv, Bitmap bm,
    			  AjaxStatus status){
    				  imgSimuli.setImageBitmap(bm);
    				  //imgSimuli.setScaleType(ScaleType.CENTER_CROP); 
    				  } }.header("User-Agent", "android"));
    	  imgSimuli.setTag(imageUrl);
		
    	
		  
    }

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		imgSimuli = (ImageView) getActivity().findViewById(R.id.imgTask);
		imgSimuliAudio = (ImageView) getActivity().findViewById(R.id.imgAudio);
	//	androidAQuery = new AQuery(getActivity());
		imgSimuliAudio.setOnClickListener(this);
		imgSimuliAudio.setTag("audio");
		imgSimuli.setOnClickListener(this);

		
		//updateArticleView(R.drawable.taskact);

	}
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnSimuliClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mCallback.onSimuliAudioClick(v);
		mCallback.onSimuliImageZoom(v);
		
		
	}
}
