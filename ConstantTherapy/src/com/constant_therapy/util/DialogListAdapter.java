package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class DialogListAdapter extends BaseAdapter {

	private Activity activity;

	private List<PatientModel> results = new ArrayList<PatientModel>();
	private static LayoutInflater inflater = null;
	PatientModel patient;
	private Boolean isIcon;
	
	public static final int NO_PATIENT_SELECTED = -1;

	private int mSelectedPosition = NO_PATIENT_SELECTED;

	public DialogListAdapter(Activity a, List<PatientModel> patient, Boolean icon) {
		activity = a;
		results = patient;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		isIcon = icon;
		mSelectedPosition = NO_PATIENT_SELECTED;
	}

	public void setSelectedIndex(int newPosition) { mSelectedPosition = newPosition; }
	public int getSelectedIndex() {return mSelectedPosition;}
	public PatientModel getSelectedItem() {return getItem(mSelectedPosition); }
	
	public void setSelectedPosition(String selectMe) {
		int theIndex = getIndexInPatientModelList(selectMe);
		if (NO_PATIENT_SELECTED == theIndex) 
			throw new IllegalArgumentException("tried to set selection in dialog list for item that is not there");
		mSelectedPosition = theIndex;
	}
	
	private int getIndexInPatientModelList(String item) {

		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getUsername().equals(item)) {
				return i;
			}
		}
		return NO_PATIENT_SELECTED;
	}

	public int getCount() {
		return results.size();
	}

	@Override
	public PatientModel getItem(int position) {
		// TODO Auto-generated method stub
		return results.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null)
			convertView = inflater.inflate(R.layout.dialog_custom_list, null);

		holder = new ViewHolder();

		
		holder.itemname = (CTTextView) convertView.findViewById(R.id.textView1);
		holder.icon = (ImageView) convertView.findViewById(R.id.imageIcon);
		holder.tick = (ImageView) convertView.findViewById(R.id.imageTick);
		
		
		
		patient = results.get(position);
		
		
		
		
		holder.itemname.setText(""+patient.getUsername());
		
		String imagePath = (String) patient.getImagePath();
		if(imagePath != null && position != 0 && imagePath.length() != 0){
			String [] image = imagePath.split("/");
			int id = activity.getResources().getIdentifier("com.constant_therapy.constantTherapy:drawable/" + image[1].replace(".png", ""), null, null);
			holder.icon.setImageResource(id);
			holder.icon.setVisibility(View.VISIBLE);
		}else{
			if(!isIcon){
				holder.icon.setVisibility(View.GONE);
			}else{
				holder.icon.setVisibility(View.INVISIBLE);
			}
		}
		
		
		ListView lvDocument = (ListView) parent;
		
		if (lvDocument.isItemChecked(position)) {

			((ListView) parent).setItemChecked(position, true);
			holder.tick.setVisibility(View.VISIBLE);

		} else {
			((ListView) parent).setItemChecked(position, false);
			holder.tick.setVisibility(View.INVISIBLE);
		}
		
		notifyDataSetChanged();
		
		return convertView;
	}

	public static class ViewHolder {
		CTTextView itemname;
		ImageView icon;
		ImageView tick;
	}
}
