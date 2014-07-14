package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.TaskTypeScores;
import com.constant_therapy.dashboard.TasksHierarchy;
import com.constant_therapy.dashboard.TasksType;
import com.constant_therapy.widget.CTTextView;

public class LeafAdapter extends BaseAdapter {

	private Activity activity;

	private List<TasksHierarchy> results = new ArrayList<TasksHierarchy>();
	private static LayoutInflater inflater = null;
	TasksHierarchy latestTaskTypeResults;
	TaskTypeScores typeScores;
	private TasksType taskScores;

	int selectcount;

	public LeafAdapter(Activity a, List<TasksHierarchy> latestTaskType, TaskTypeScores scores) {
		activity = a;
		results = latestTaskType;
		typeScores = scores;
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
	public TasksHierarchy getItem(int position) {
		// TODO Auto-generated method stub
		return results.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.tasks_custom_list, null);

		holder = new ViewHolder();

		holder.img = (ImageView) convertView.findViewById(R.id.imageIcon);
		holder.title = (CTTextView) convertView.findViewById(R.id.tvItem);
		holder.tvLevel = (CTTextView) convertView.findViewById(R.id.tvLevel);
		holder.tvPercent = (CTTextView) convertView.findViewById(R.id.tvPercent);
		
			latestTaskTypeResults = results.get(position);
		
		taskScores = new TasksType();
		
		holder.title.setText(latestTaskTypeResults.getDisplayName());
		taskScores = Helper.getSystemname(typeScores, latestTaskTypeResults.getSystemName());
		
		
		ShapeDrawable circle = new ShapeDrawable(new OvalShape());
		
		if(taskScores == null || taskScores.getAccuracyBaseline() == 0.0d){
			circle.getPaint().setColor(activity.getResources().getColor(R.color.top_dark_gray));
			holder.tvLevel.setText("--");
		}else{
			circle.getPaint().setColor(Helper.colorForScoreAndMean(taskScores.getAccuracyBaseline(), taskScores.getAccuracyMean()));
			holder.tvLevel.setText(""+Math.round(taskScores.getAccuracyBaseline()*100)+"%");
		}
		circle.setBounds(1, 1, 1, 1);
		holder.tvLevel.setBackgroundDrawable(circle);
		
		ShapeDrawable rec = new ShapeDrawable();
		if(taskScores == null || taskScores.getAccuracyAverage() == 0.0d){
			rec.getPaint().setColor(activity.getResources().getColor(R.color.top_dark_gray));
			holder.tvPercent.setText("--");
		}else{
			rec.getPaint().setColor(Helper.colorForScoreAndMean(taskScores.getAccuracyAverage(), taskScores.getAccuracyMean()));
			holder.tvPercent.setText(""+Math.round(taskScores.getAccuracyAverage()*100)+"%");
		}
		rec.setBounds(1, 1, 1, 1);
		holder.tvPercent.setBackgroundDrawable(rec);

		return convertView;
	}

	public static class ViewHolder {
		ImageView img;
		CTTextView title;
		CTTextView tvLevel;
		CTTextView tvPercent;
	}


}
