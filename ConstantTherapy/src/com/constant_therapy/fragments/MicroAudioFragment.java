package com.constant_therapy.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class MicroAudioFragment extends Fragment implements OnClickListener{
	
	
	ImageView imgAudio;
	ViewGroup mRootView;
	MediaPlayer mPlayer;
	
	CTTextView choice;
	OnMicroAudioFrameClickListener mCallback;

	    // The container Activity must implement this interface so the frag can deliver messages
	    public interface OnMicroAudioFrameClickListener {
	        /** Called by HeadlinesFragment when a list item is selected */
	        public void onMicroAudioClick(View v);
	    }

	 public static MicroAudioFragment newInstance(int someInt, String someTitle) {
		 MicroAudioFragment fragmentDemo = new MicroAudioFragment();
	        Bundle args = new Bundle();
	        args.putInt("someInt", someInt);
	        args.putString("someTitle", someTitle);
	        fragmentDemo.setArguments(args);
	        return fragmentDemo;
	    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
       
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.audio_frame, container, false);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       
        // Save the current article selection in case we need to recreate the fragment
        //outState.putInt(ARG_POSITION, mCurrentPosition);
    }
    
    
    @Override
   	public void onActivityCreated(Bundle savedInstanceState) {
   		// TODO Auto-generated method stub
   		super.onActivityCreated(savedInstanceState);
   		imgAudio = (ImageView) getActivity().findViewById(R.id.imgAudio);
   		int size = getActivity().findViewById(R.id.frameAudio).getHeight()/2;
   		imgAudio.getLayoutParams().height = 450;
   		imgAudio.getLayoutParams().width = 450;
   		imgAudio.setOnClickListener(this);
   
   		
   	}
       
       
       @Override
       public void onAttach(Activity activity) {
           super.onAttach(activity);

           // This makes sure that the container activity has implemented
           // the callback interface. If not, it throws an exception.
           try {
               mCallback = (OnMicroAudioFrameClickListener) activity;
           } catch (ClassCastException e) {
               throw new ClassCastException(activity.toString()
                       + " must implement OnHeadlineSelectedListener");
           }
       }
       

   	@Override
   	public void onClick(View v) {
   		// TODO Auto-generated method stub
   		mCallback.onMicroAudioClick(v);
   		
   		
   	}
   }


