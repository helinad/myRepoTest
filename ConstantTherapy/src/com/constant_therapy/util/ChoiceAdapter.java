package com.constant_therapy.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Choices;
import com.constant_therapy.widget.CTTextView;

public class ChoiceAdapter<T> extends ArrayAdapter<T>{
	private Activity activity;

	private List<T> results;
	private LayoutInflater inflater;
	public ChoiceAdapter(Activity context, List<T> objects) {
		super(context, 0, 0, objects);
		activity = context;
		results = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return results.size();
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
			convertView = inflater.inflate(R.layout.choice_frame, parent , false);

		holder = new ViewHolder();

		
		holder.tvItemname = (CTTextView) convertView.findViewById(R.id.tvChoice);
		
		
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
		


		
			
		
		holder.tvItemname.setText("" + ((Choices) getItem(position)).getIsCorrect());

			
		
		
		
		return convertView;
	}

	public static class ViewHolder {
		
		CTTextView tvItemname;
		
	
	}

}
