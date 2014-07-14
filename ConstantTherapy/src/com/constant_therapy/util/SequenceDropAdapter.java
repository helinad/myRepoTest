package com.constant_therapy.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class SequenceDropAdapter extends BaseAdapter {

	private Context context;
	ArrayList<String> sequence;
	MyDragEventListener myDragEventListener;
	LinearLayout dropLayout;

	public SequenceDropAdapter(Context context, ArrayList<String> sequence,MyDragEventListener myDragEventListener) {

		this.context = context;

		this.sequence = sequence;
		this.myDragEventListener = myDragEventListener;
		DoingTaskActivity.dropSequenceList.clear();
	}

	@Override
	public int getCount() {
		return sequence.size();
	}

	@Override
	public Object getItem(int position) {

		return sequence.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ViewHolder holder;
if(convertView==null)
{
			convertView = LayoutInflater.from(context).inflate(
					R.layout.dropword_frame, null);


			holder = new ViewHolder();
			holder.groupItem = (CTTextView) convertView
					.findViewById(R.id.taskWord);
			//dropLayout=(LinearLayout)convertView.findViewById(R.id.dropLayout);
			// holder.ckvalue=(CheckBox)
			// convertView.findViewById(R.id.radioButton1);
	    holder.groupItem.setTag(sequence.get(position));
		holder.groupItem.setText(sequence.get(position));
		  holder.groupItem.setVisibility(View.INVISIBLE);
	  	DoingTaskActivity.dropSequenceList.add(holder.groupItem);
}
	    else
	       { 
	        holder = (ViewHolder) convertView.getTag(); 
	        // if convertVIew is not null initialize the holder using convertView.getTag

	       }  
	  	convertView.setId(position);
		convertView.setOnDragListener((OnDragListener)myDragEventListener);
        notifyDataSetChanged();
		return convertView;
	}
	 
	static class ViewHolder
	{
		CTTextView groupItem;

	}

}