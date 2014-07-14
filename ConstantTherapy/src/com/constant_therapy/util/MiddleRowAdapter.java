package com.constant_therapy.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.LatestTaskTypeResults;
import com.constant_therapy.widget.CTTextView;

public class MiddleRowAdapter extends BaseAdapter {

	private Activity activity;

	private ArrayList<LatestTaskTypeResults> results = new ArrayList<LatestTaskTypeResults>();
	private static LayoutInflater inflater = null;
	LatestTaskTypeResults latestTaskTypeResults;

	int selectcount;

	public MiddleRowAdapter(Activity a, ArrayList<LatestTaskTypeResults> latestTaskType) {
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
	public LatestTaskTypeResults getItem(int position) {
		// TODO Auto-generated method stub
		return results.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.middle_row_list, null);

		holder = new ViewHolder();

		holder.imIcon = (ImageView) convertView.findViewById(R.id.imageIcon);
		holder.bar = (ProgressBar) convertView.findViewById(R.id.seekBar1);
		holder.tvItemname = (CTTextView) convertView.findViewById(R.id.tvItem);
		holder.tvLevel = (CTTextView) convertView.findViewById(R.id.tvLevel);
		holder.tvPercent = (CTTextView) convertView.findViewById(R.id.tvPercent);
		holder.tvMins = (CTTextView) convertView.findViewById(R.id.tvMins);
		
		//latestTaskTypeResults = new HashMap<String, Object>();
		
		latestTaskTypeResults = results.get(position);
		
		String text = "" + latestTaskTypeResults.getCompletedTaskCount() + " items, Level "
				+ latestTaskTypeResults.getTaskLevel();

		holder.tvLevel.setText(text);
		holder.tvItemname.setText(""+latestTaskTypeResults.getTaskType());

		double accuracy = (Double) latestTaskTypeResults.getAccuracy();
		
		
		
		int latency = (int) Math.round(latestTaskTypeResults.getAvgLatency());
		String minis = latency +" <font size=\"2\"> sec</font>";
		
		
		holder.bar.setProgress((int) Math.round(accuracy * 100));
		if (accuracy == 0.0 && latestTaskTypeResults.getAvgLatency() == 0.0) {
			holder.tvPercent.setText("-- "+"%");
			holder.tvMins.setText("-- sec");
			if(latestTaskTypeResults.getPercentSkipped() == 0){
				holder.bar.setBackgroundColor(activity.getResources().getColor(R.color.top_dark_gray));
			}else{
				holder.bar.setBackgroundColor(activity.getResources().getColor(R.color.yellow));
			}

		}else {
			holder.tvPercent.setText(""+(int) (accuracy * 100)+"%");
			holder.tvMins.setText(Html.fromHtml(minis));
			holder.bar.setBackgroundColor(activity.getResources().getColor(R.color.orange));
		}
		
		

		return convertView;
	}

	public static class ViewHolder {
		ImageView imIcon;
		CTTextView tvItemname;
		CTTextView tvLevel;
		ProgressBar bar;
		CTTextView tvPercent;
		CTTextView tvMins;
	}

}
