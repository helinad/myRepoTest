package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.util.SpokenWordAdapter.Holder;

public class AuditoryDropAdapter extends BaseAdapter {
	MyDragEventListener myDragEventListener;
	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	AQuery androidAQuery;

	ArrayList<HashMap<Integer, String>> resources = new ArrayList<HashMap<Integer,String>>();
	ArrayList<Integer> location = new ArrayList<Integer>();
	ArrayList<Integer> locationlocation = new ArrayList<Integer>();
	Map<Integer, String> hashmap = new HashMap<Integer, String>();;
	
	int gridHeight;
	private LayoutInflater layoutInflator;

	int size;
	int count = 1;
	MediaPlayer mPlayerOnline;
	private DisplayMetrics mDisplayMetrics;

	public AuditoryDropAdapter(Context c, Map<Integer,String> resources, ArrayList<Integer> location,
			ArrayList<Integer> locationlocation, MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.hashmap = resources;
		this.locationlocation = locationlocation;
		this.location = location;
		this.myDragEventListener = myDragEventListener;
		androidAQuery = new AQuery(mContext);
		layoutInflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDisplayMetrics = c.getResources().getDisplayMetrics();
		count = 1;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
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
		holder.taskImage.setVisibility(View.INVISIBLE);
		rowView.setBackgroundColor(mContext.getResources().getColor(R.color.top_dark_gray));

		holder.taskImage.setTag("");
		
		/*if(locationlocation.contains(position)){
			hashmap = new HashMap<Integer, String>();
			hashmap = resources.get(count);
			count ++;
		}
		*/
		
		if( position == 4){
			//hashmap = new HashMap<Integer, String>();
			//hashmap = resources.get(0);
			final String image =  hashmap.get(position);
			holder.taskImage.setVisibility(View.VISIBLE);
			String imageUrl = mContext.getString(R.string.resource_url) +  image;
		
			Log.v("", imageUrl);
			androidAQuery.id(holder.taskImage).image(imageUrl, true, true, 0, 0,
					new BitmapAjaxCallback() {
						@Override
						public void callback(String url, ImageView iv, Bitmap bm,
								AjaxStatus status) {
	
							holder.taskImage.setImageBitmap(bm);
							holder.taskImage.setTag(image);
						}
					}.header("User-Agent", "android"));
	
		
	 }else if(hashmap.containsKey(position) && position != 4){
		 holder.taskImage.setVisibility(View.INVISIBLE);
			final String image =  hashmap.get(position);
			String imageUrl = mContext.getString(R.string.resource_url) + image;
		
			androidAQuery.id(holder.taskImage).image(imageUrl, true, true, 0, 0,
					new BitmapAjaxCallback() {
						@Override
						public void callback(String url, ImageView iv, Bitmap bm,
								AjaxStatus status) {
	
							holder.taskImage.setImageBitmap(bm);
							holder.taskImage.setTag(image);
							
						}
					}.header("User-Agent", "android")); 
	 }
		rowView.setOnDragListener(myDragEventListener);
		DoingTaskActivity.dragViewList.add(rowView);

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

			return 150;
		}

		case DisplayMetrics.DENSITY_LOW: {

			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {

			return 150;
		}

		default:

			return 225;
		}

	}
}

