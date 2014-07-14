package com.constant_therapy.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ViewAnimator;
import android.widget.ImageView.ScaleType;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.Tasks;

public class SpokenWordAdapter extends BaseAdapter {
	MyDragEventListener myDragEventListener;
	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	AQuery androidAQuery;

	ArrayList<String> resources = new ArrayList<String>();
	String resourceUrl = null;
	int count;
	private LayoutInflater layoutInflator;

	int size;
	MediaPlayer mPlayerOnline;
	private DisplayMetrics mDisplayMetrics;

	public SpokenWordAdapter(Context c, ArrayList<String> resources,
			String resourceUrl, MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.resourceUrl = resourceUrl;

		this.myDragEventListener = myDragEventListener;
		androidAQuery = new AQuery(mContext);
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

		rowView = layoutInflator.inflate(R.layout.spoken_image, null);
		holder.taskImage = (ImageView) rowView.findViewById(R.id.taskImage);
		size = getBarHeight(getCount());
		holder.taskImage.getLayoutParams().height = size;
		holder.taskImage.getLayoutParams().width = size;
		holder.taskImage.setTag("");

		/**
		 * Bind a click listener to initiate the flip transitions
		 */

		

		String imageUrl = resourceUrl + resources.get(position);
		androidAQuery.id(holder.taskImage).image(imageUrl, true, true, 0, 0,
				new BitmapAjaxCallback() {
					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {

						holder.taskImage.setImageBitmap(bm);
						// holder.taskImage.setScaleType(ScaleType.CENTER);
						holder.taskImage.setTag(resources.get(position));
					}
				}.header("User-Agent", "android"));

		rowView.setOnDragListener(myDragEventListener);
		DoingTaskActivity.matchingList.add(rowView);
		

		return rowView;
	}

	// references to our images

	public class Holder {
		ImageView taskImage;
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {

			return 220;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {

			return 220;
		}

		case DisplayMetrics.DENSITY_LOW: {

			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {

			return 180;
		}

		default:

			return 300;
		}

	}
}
