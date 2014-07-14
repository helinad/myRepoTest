package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class DropFragment extends Fragment implements OnClickListener{
	public ImageView  imgDropAudio, imgTempDrop;
	public CTTextView tvDrop, tvTempDrop;
	ViewGroup mRootView;
	MediaPlayer mPlayer;
	String image = null;
	OnSoundClickListener mCallback;
	FrameLayout dropView;
	static MyDragEventListener myDragEventListener;
	AQuery androidAQuery;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnSoundClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
            public void onSoundClick(View v);
        
    }
    
    public DropFragment(MyDragEventListener myDragEventListener){
    	this.myDragEventListener = myDragEventListener;
    }
    
    public DropFragment(){
    	
    }

	 public static DropFragment newInstance(Boolean sound, String imageUrl, String image) {
		 DropFragment fragmentDemo = new DropFragment(myDragEventListener);
	        Bundle args = new Bundle();
	        args.putBoolean("sound", sound);
	        args.putString("imageUrl", imageUrl);
	        args.putString("image", image);
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
		mRootView = (ViewGroup)inflater.inflate(R.layout.drop_frame, container, false);
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

    public void updateArticleView(String imageUrl) {
    	imgDropAudio.setVisibility(View.INVISIBLE);
    	imgTempDrop.setVisibility(View.INVISIBLE);
    	tvTempDrop.setVisibility(View.INVISIBLE);
    	tvDrop.setText(imageUrl);
    	
		  
    }

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		imgDropAudio = (ImageView) getActivity().findViewById(R.id.imgDropAudio);
		imgTempDrop = (ImageView) getActivity().findViewById(R.id.imgDrop);
		
		tvDrop = (CTTextView) getActivity().findViewById(R.id.tvDrop);
		tvTempDrop = (CTTextView) getActivity().findViewById(R.id.tvTempDrop);
		
		dropView = (FrameLayout)getActivity().findViewById(R.id.flDrop);
		
		dropView.setOnDragListener((OnDragListener) myDragEventListener);
		androidAQuery = new AQuery(getActivity());
		
		imgTempDrop.setVisibility(View.INVISIBLE);
		tvTempDrop.setVisibility(View.INVISIBLE);
		
		
		 Bundle args = getArguments();
			if (args != null) {
				// Set article based on argument passed in
				image = args.getString("image");
				if(args.getBoolean("sound")){
					
					updateArticleView(args.getString("imageUrl"));
				}else{
				
					tvTempDrop.setVisibility(View.INVISIBLE);
					tvDrop.setVisibility(View.INVISIBLE);
					imgDropAudio.setOnClickListener(this);
					
					
				}
				
				if(image.length() > 0 && image.contains(".jpg")){
					imgTempDrop.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_sandal));
					String imageUrl = getActivity().getString(R.string.resource_url) + image;

					androidAQuery.id(imgTempDrop).image(imageUrl, true, true, 0, 0,
							new BitmapAjaxCallback() {
								@Override
								public void callback(String url, ImageView iv, Bitmap bm,
										AjaxStatus status) {

									imgTempDrop.setImageBitmap(bm);
									//imgTempDrop.setScaleType(ScaleType.FIT_XY);
									imgTempDrop.setTag(image);
								}
							}.header("User-Agent", "android"));
				}
				
			}
			
			DoingTaskActivity.tempDropImg = imgTempDrop;
			DoingTaskActivity.dropView = dropView;
			DoingTaskActivity.tempDropTv = tvTempDrop;
			
			
	}
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnSoundClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		mCallback.onSoundClick(v);
		
		
	}
}
