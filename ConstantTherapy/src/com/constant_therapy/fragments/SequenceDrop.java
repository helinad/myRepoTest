package com.constant_therapy.fragments;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.DraggingTask;
import com.constant_therapy.util.SequenceDragAdapter;
import com.constant_therapy.util.SequenceDropAdapter;

public  class SequenceDrop extends Fragment {
	
	

	ViewGroup mRootView;
    LinearLayout wordLayout;
    DraggingTask dragtask;
	ArrayList<View> views;
	public static SequenceDropAdapter adapter;
    public static ListView list1;
    public static long getdragchildcount;
    static MyDragEventListener myDragEventListener;
	View vi;
	int pos;
	ArrayList<String> resources;

	 public static SequenceDrop newInstance(DraggingTask dragtask, int pos) 
	 {
		    SequenceDrop fragmentDemo = new SequenceDrop(myDragEventListener);
	        Bundle args = new Bundle();
	        args.putSerializable("sequence", dragtask);
	        args.putInt("pos", pos);
	        fragmentDemo.setArguments(args);
	        return fragmentDemo;
	    }
		public SequenceDrop(MyDragEventListener myDragEventListener)
		{
	    	this.myDragEventListener = myDragEventListener;
	    }
	    
	    public SequenceDrop(){
	    	
	    }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
		{

			mRootView = (ViewGroup) inflater.inflate(R.layout.dragging_frame,
					container, false);

			// view
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
   	public void onActivityCreated(Bundle savedInstanceState)
    {
   		// TODO Auto-generated method stub
   		super.onActivityCreated(savedInstanceState);
   	 list1=(ListView) mRootView.findViewById(R.id.list1);
   	DoingTaskActivity.listDrop=list1;
 	if (list1.getChildCount() > 0)
			list1.removeAllViews();
	 resources = new ArrayList<String>();

		Bundle args = getArguments();
		if (args != null) {

			dragtask = (DraggingTask) args.getSerializable("sequence");
	pos=args.getInt("pos");
	updateTableView(dragtask.getTasks().get(pos).sequence);
	
		
		}
     
   		}
  
 
     		
   	

 

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       
        // Save the current article selection in case we need to recreate the fragment
        //outState.putInt(ARG_POSITION, mCurrentPosition);
    }
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
      
    }
	public void updateTableView(ArrayList<String> sequence) {
		
		resources.clear();
		for(int i= 0; i <sequence.size(); i++ ){
			
				resources.add(sequence.get(i));
			
		}
	Log.v("resources1",""+resources.get(0));
	
	
		 adapter= new SequenceDropAdapter(getActivity().getBaseContext(),resources,myDragEventListener);
		    list1.setAdapter(adapter);
		    adapter.notifyDataSetChanged();
		

	}

 }



