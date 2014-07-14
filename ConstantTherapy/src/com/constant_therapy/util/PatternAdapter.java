package com.constant_therapy.util;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.widget.CTTextView;

public class PatternAdapter extends BaseAdapter {

	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	AQuery androidAQuery;

	ArrayList<Integer> resources = new ArrayList<Integer>();
	String resourceUrl = null;

	private LayoutInflater layoutInflator;

	int size;
	MediaPlayer mPlayerOnline;
	private DisplayMetrics mDisplayMetrics;
	int count;

	public PatternAdapter(Context c, ArrayList<Integer> resources,
			int count) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.count = count;

		layoutInflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDisplayMetrics = c.getResources().getDisplayMetrics();
		DoingTaskActivity.matchingList.clear();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
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

		rowView = layoutInflator.inflate(R.layout.custom_pattern, null);
		holder.taskWord = (CTTextView) rowView.findViewById(R.id.taskWord);
		holder.dummyImage = (ImageView) rowView.findViewById(R.id.dummyImage);
		 size = getBarHeight(getCount());
		 holder.taskWord.getLayoutParams().height = size;
		 holder.taskWord.getLayoutParams().width = (int) (size*3);
		 holder.dummyImage.getLayoutParams().height = size;
		 holder.dummyImage.getLayoutParams().width = (int) (size*3);

		 holder.taskWord.setVisibility(View.GONE);
		 
		 holder.dummyImage.setTag(position);
		
		 DoingTaskActivity.pattenList.add(rowView);
		 if(resources.contains(position))
			 DoingTaskActivity.matchingList.add(rowView);

		return rowView;
	}

	// references to our images

	public class Holder {

		CTTextView taskWord;
		ImageView dummyImage;
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {
			switch (count) {
			case 16:
				return 115;

			case 25:

				return 85;
			case 36:

				return 70;
			
			default:
				break;
			}
			return 100;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {
			switch (count) {
			case 16:
				return 115;

			case 25:

				return 90;
			case 36:

				return 70;
			
			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_LOW: {
			switch (count) {
			case 16:
				return 250;

			case 25:

				return 165;
			case 36:

				return 125;
			
			default:
				break;
			}
			return 170;
		
		}

		case DisplayMetrics.DENSITY_TV: {
			switch (count) {
			case 16:
				return 103;

			case 25:

				return 79;
			case 36:

				return 64;
			
			default:
				break;
			}
			return 170;
		}
		default:
			switch (count) {
			case 16:
				return 155;

			case 25:

				return 120;
			case 36:

				return 96;
			
			
			default:
				break;
			}
			return 155;
		}
		
		
	}

}
