package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.ScheduledTaskTypes;
import com.constant_therapy.widget.CTTextView;

public class HomeworkAdapter<T> extends ArrayAdapter<T> {

	private Activity activity;

	private List<T> results;
	private LayoutInflater inflater;
	//ScheduledTaskTypes latestTaskTypeResults;
	Boolean reorder = false;

	private boolean isRadio;

	private HashMap<T, Boolean> hashMap;

	int selectcount;

	public HomeworkAdapter(Activity a,List<T> latestTaskType) {
		super(a, 0, 0, latestTaskType);
		activity = a;
		results = latestTaskType;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		SelectedInit();

	}

	public int getCount() {
		return results.size();
	}

	public void selectCount(int selectcount) {
		this.selectcount = selectcount;
	}

	public int getSelectcount() {

		return selectcount;
	}

	public List<T> getListItem() {
		// TODO Auto-generated method stub
		return results;
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return results.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.custom_homework_list, parent , false);

		holder = new ViewHolder();

		holder.imIcon = (ImageView) convertView.findViewById(R.id.imageIcon);
		holder.imDelete = (ImageView) convertView
				.findViewById(R.id.imageDelete);
		holder.tvItemname = (CTTextView) convertView.findViewById(R.id.tvItem);
		holder.tvLevel = (CTTextView) convertView.findViewById(R.id.tvLevel);
		holder.back = (LinearLayout)convertView.findViewById(R.id.back);
		holder.front = (LinearLayout)convertView.findViewById(R.id.front);
		holder.lvdelete = (LinearLayout)convertView.findViewById(R.id.lvDelete);
		
		holder.imgUndo = (LinearLayout) convertView.findViewById(R.id.lvundo);
		
		
		
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
		

		String text = "Level " + ((ScheduledTaskTypes) getItem(position)).getTaskLevel() + ", "
				+ ((ScheduledTaskTypes) getItem(position)).getTaskCount() + " items";

		holder.tvLevel.setText(text);
		
			
		
		holder.tvItemname.setText("" + ((ScheduledTaskTypes) getItem(position)).getDisplayName());

			holder.imDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					holder.front.setVisibility(View.GONE);
					holder.back.setVisibility(View.VISIBLE);
					
					TimerTask countdownTask = new TimerTask() {
						
						 @Override public void run() { 
							 activity.runOnUiThread(new Runnable() {
					 
								 @Override public void run() {
									 	cancel();
									 	//if(results.size() != 0 && results.get(position)!= null){
									 		try {
												results.remove(results.get(position));
												notifyDataSetChanged();
												holder.front.setVisibility(View.VISIBLE);
												holder.back.setVisibility(View.GONE);
											} catch (IndexOutOfBoundsException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
									 	//}
									
										
									 } 
								 }); 
							 } };
					 
					 final Timer countdown = new Timer(); 
					 countdown.schedule(countdownTask, Constants.REMOVEITEM_DURATION, 2*Constants.REMOVEITEM_DURATION);
					
					holder.imgUndo.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							countdown.cancel();
							
							notifyDataSetChanged();
							holder.front.setVisibility(View.VISIBLE);
							holder.back.setVisibility(View.GONE);
							
							
						}
					});
					
					
					 
				}
			});
		
		
		
		return convertView;
	}

	public static class ViewHolder {
		LinearLayout front;
		LinearLayout back;
		ImageView imIcon;
		CTTextView tvItemname;
		CTTextView tvLevel;
		ImageView imDelete;
		ImageView imReorder;
		LinearLayout imgUndo;
		LinearLayout lvdelete;
	
	}

	public void setisReorder(Boolean order) {
		reorder = order;
	}

	public Boolean isReorder() {
		return reorder;
	}

	public boolean isRadio() {
		return isRadio;
	}
	public void setRadio(boolean isRadio) {
		this.isRadio = isRadio;
		SelectedInit();
	}

	public void SelectedInit(){
		if(hashMap == null)
			hashMap = new HashMap<T, Boolean>();
		for(T t : results){
			hashMap.put(t, false);
		}
	
		notifyDataSetChanged();
	}

	public void SelectedAll(){
		for(T t : results){
			hashMap.put(t, true);
		}
	
		notifyDataSetChanged();
	}
	
	
	public void SelectedToogle(T t){
		if(isRadio)
			SelectedInit();
		
		hashMap.put(t, !hashMap.get(t));
	
		notifyDataSetChanged();
	}
	
	public void SelectedToogleAll(){
		for(T t : results){
			hashMap.put(t, !hashMap.get(t));
		}
		
		notifyDataSetChanged();
	}
	
	public void remove(int position){
		hashMap.remove(getItem(position));
    	remove(getItem(position));
    
    	
	}
	

	
	public ArrayList<T> getSelectedList(){
		ArrayList<T> mSList = new ArrayList<T>();
		Iterator<Entry<T, Boolean>> iter = hashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<T, Boolean> entry = (Map.Entry<T, Boolean>) iter.next(); 
			if(entry.getValue()){
				
				mSList.add(entry.getKey());
			}
		}
		return mSList ;
	}
	
	
	public int getSelectedCount(){
		int i = 0 ;
		Iterator<Entry<T, Boolean>> iter = hashMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<T, Boolean> entry = (Map.Entry<T, Boolean>) iter.next(); 
			if(entry.getValue()){
				i++ ;
			}
		}
		return i ;
	}
	
	
	

	


}
