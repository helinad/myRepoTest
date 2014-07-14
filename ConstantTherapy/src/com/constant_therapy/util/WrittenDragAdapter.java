package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class WrittenDragAdapter extends BaseAdapter {

	private Context mContext;

	List<String> resources = new ArrayList<String>();
	List<Integer> indices = new ArrayList<Integer>();
	String name;
	
	private LayoutInflater layoutInflator;

	MyDragEventListener myDragEventListener;
	int size;
	char[] nameArray;
	List<String> removeItem = new ArrayList<String>();
	private DisplayMetrics mDisplayMetrics;
	public WrittenDragAdapter(Context c, List<String> resources, List<Integer> indices, String name, MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.indices = indices;
		this.name = name;
		this.myDragEventListener = myDragEventListener;
		Collections.sort(indices, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		
		
		nameArray = new char[name.length()];
		nameArray = name.toCharArray();
		removeItem = removeItem(indices, nameArray);
		DoingTaskActivity.charItem = removeItem;
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

		rowView = layoutInflator.inflate(R.layout.written_word, null);
		holder.taskText = (CTTextView)rowView.findViewById(R.id.taskWord);
		//holder.taskText.setTextSize(mContext.getResources().getDimension(R.dimen.txt_drag));
		size = getBarHeight(6);
		holder.taskText.getLayoutParams().height = size/2;
		holder.taskText.getLayoutParams().width = size/2;
		holder.taskText.setBackground(mContext.getResources().getDrawable(R.drawable.bg_sandal));
		holder.taskText.setText(resources.get(position));
		holder.taskText.setTag(resources.get(position));
		holder.taskText.setVisibility(View.VISIBLE);
	
		rowView.setOnDragListener(myDragEventListener);  
		
		
			holder.taskText.setTag(resources.get(position));
			holder.taskText.setVisibility(View.VISIBLE);
			DoingTaskActivity.dragViewList.add(rowView);
		
		DoingTaskActivity.allDragViewList.add(rowView);
		
		return rowView;
	}

	// references to our images

	public class Holder {
	
		CTTextView taskText;
	}
	
	private Boolean isExist(List<String> itemList, String item){
		for(int i = 0; i < itemList.size(); i++){
			if(itemList.get(i).equalsIgnoreCase(item)){
				return true;
			}
		}
		
		return false;
	}
	
	private Boolean isViewExist(String item){
		for(int i = 0; i < DoingTaskActivity.dragViewList.size(); i++){
			if(item.equalsIgnoreCase(DoingTaskActivity.dragViewList.get(i).findViewById(R.id.taskWord).getTag().toString())){
				return true;
			}
		}
		
		return false;
	}
	
	private List<String> removeItem(List<Integer> list, char[] letter){
		List<String> item = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			item.add(String.valueOf(letter[list.get(i)]));
		}
		return item;
	}

		
		
	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH:{
			switch (count) {
			case 6:
				return 200;
				
			case 12:

				return 165;
			case 20:

				return 125;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}
			
		case DisplayMetrics.DENSITY_MEDIUM:{
			switch (count) {
			case 6:
				return 200;
				
			case 12:

				return 165;
			case 20:

				return 120;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}
			
		case DisplayMetrics.DENSITY_LOW:{
			switch (count) {
			case 6:
				return 250;
				
			case 12:

				return 130;
			case 20:

				return 180;
			case 24:

				return 120;
			case 30:

				return 120;

			default:
				break;
			}
			return 170;
		}
			
		case DisplayMetrics.DENSITY_TV:{
			switch (count) {
			case 6:
				return 180;
				
			case 12:

				return 150;
			case 20:

				return 125;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}
			
		default:
			switch (count) {
			case 6:
				return 270;
				
			case 12:

				return 230;
			case 20:

				return 170;
			case 24:

				return 170;
			case 30:

				return 135;

			default:
				break;
			}
			
		}
		return 0;
	}


}

