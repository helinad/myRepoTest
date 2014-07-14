package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.LatestTaskTypeResults;
import com.constant_therapy.widget.CTTextView;

public class ListAdapter extends BaseAdapter {

	private Activity activity;

	private ArrayList<LatestTaskTypeResults> results = new ArrayList<LatestTaskTypeResults>();
	private static LayoutInflater inflater = null;
	LatestTaskTypeResults latestTaskTypeResults;

	int selectcount;

	public ListAdapter(Activity a, ArrayList<LatestTaskTypeResults> latestTaskType) {
		activity = a;
		results = latestTaskType;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.barchartlist, null);

		holder = new ViewHolder();

		holder.bar = (ProgressBar) convertView.findViewById(R.id.seekBar1);
		holder.itemname = (CTTextView) convertView.findViewById(R.id.tvItem);
		holder.level = (CTTextView) convertView.findViewById(R.id.tvLevel);
		
		latestTaskTypeResults = new LatestTaskTypeResults();
		
		latestTaskTypeResults = results.get(position);
		
		String text = "" + latestTaskTypeResults.getCompletedTaskCount() + " items, Level "
				+ latestTaskTypeResults.getTaskLevel();

		holder.level.setText(text);
		holder.itemname.setText(""+latestTaskTypeResults.getTaskType());

		double accuracy = (Double) latestTaskTypeResults.getAccuracy();
		
		holder.bar.setProgress((int) Math.round(accuracy * 100));
		if (accuracy == 0.0) {
			if(latestTaskTypeResults.getPercentSkipped() == 0){
				holder.bar.setBackgroundColor(activity.getResources().getColor(R.color.top_dark_gray));
			}else{
				holder.bar.setBackgroundColor(activity.getResources().getColor(R.color.yellow));
			}
		} else {
			holder.bar.setBackgroundColor(activity.getResources().getColor(R.color.orange));
		}

		return convertView;
	}

	public static class ViewHolder {
		CTTextView itemname;
		CTTextView level;
		ProgressBar bar;
	}

}
