package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.widget.CTTextView;

public class ActiveDragAdapter extends BaseAdapter {
	MyDragEventListener myDragEventListener;
	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	AQuery androidAQuery;

	List<String> resources = new ArrayList<String>();
	String resourceUrl = null;
	int count;
	private LayoutInflater layoutInflator;

	int size;
	MediaPlayer mPlayerOnline;
	private DisplayMetrics mDisplayMetrics;

	public ActiveDragAdapter(Context c, List<String> resources, MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.myDragEventListener = myDragEventListener;
		layoutInflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDisplayMetrics = c.getResources().getDisplayMetrics();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resources.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		final Holder holder = new Holder();
		View rowView;

		rowView = layoutInflator.inflate(R.layout.active_word, null);
		holder.taskWord = (CTTextView) rowView.findViewById(R.id.taskWord);
		
		size = getBarHeight(6);
		holder.taskWord.getLayoutParams().height = (int) (size/2.5);
		holder.taskWord.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.txt_title));
		holder.taskWord.setTypeface(null, Typeface.NORMAL);
		/**
		 * Bind a click listener to initiate the flip transitions
		 */
		holder.taskWord.setText(resources.get(position));
		holder.taskWord.setTag(resources.get(position));
		holder.taskWord.setVisibility(View.VISIBLE);
	
		rowView.setOnDragListener(myDragEventListener);  
		
		
		//if(isExist(removeItem, resources.get(position))){
			
			DoingTaskActivity.dragViewList.add(rowView);
		//}else{
			//DoingTaskActivity.dragViewList.remove(rowView);
		//}
		

		return rowView;
	}

	// references to our images

	public class Holder {
		CTTextView taskWord;
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {

			return 220;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {

			return 220;
		}

		case DisplayMetrics.DENSITY_LOW: {

			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {

			return 200;
		}

		default:

			return 350;
		}

	}
}

