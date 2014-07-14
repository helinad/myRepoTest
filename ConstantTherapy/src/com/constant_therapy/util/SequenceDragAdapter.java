package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class SequenceDragAdapter extends BaseAdapter {

	private Context context;
	ArrayList<String> sequence;
	MyDragEventListener myDragEventListener;

	public SequenceDragAdapter(Context context, ArrayList<String> sequence,
			MyDragEventListener myDragEventListener) {

		this.context = context;

		this.sequence = sequence;
		new Random();
		this.myDragEventListener = myDragEventListener;
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

		convertView = LayoutInflater.from(context).inflate(
				R.layout.dragword_frame, null);
		holder = new ViewHolder();

		holder.groupItem = (CTTextView) convertView.findViewById(R.id.taskWord);
		// holder.ckvalue=(CheckBox)
		// convertView.findViewById(R.id.radioButton1);

		holder.groupItem.setTag(sequence.get(position));
		holder.groupItem.setTextColor(Color.BLACK);
		holder.groupItem.setText(sequence.get(position));
		DoingTaskActivity.dragViewList.add(convertView);

		convertView.setOnDragListener((OnDragListener) myDragEventListener);
		// convertView.setId(position);
		notifyDataSetChanged();
		return convertView;
	}

	static class ViewHolder {
		CTTextView groupItem;

	}

}