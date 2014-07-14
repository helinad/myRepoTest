package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
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
import com.constant_therapy.util.ActiveDragAdapter.Holder;
import com.constant_therapy.widget.CTTextView;

public class OrderDragAdapter extends BaseAdapter {
	MyDragEventListener myDragEventListener;
	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	AQuery androidAQuery;

	List<String> resources = new ArrayList<String>();
	String resourceUrl = null;
	int count;
	private LayoutInflater layoutInflator;

	int size;
	MediaPlayer mPlayerOnline;
	private DisplayMetrics mDisplayMetrics;
	boolean isWord;

	public OrderDragAdapter(Context c, List<String> resources, boolean isWord, MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.isWord = isWord;
		this.myDragEventListener = myDragEventListener;
		layoutInflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDisplayMetrics = c.getResources().getDisplayMetrics();
		androidAQuery = new AQuery(mContext);
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

		rowView = layoutInflator.inflate(R.layout.order_image_word, null);
		holder.taskWord = (CTTextView) rowView.findViewById(R.id.taskWord);
		holder.taskImage = (ImageView) rowView.findViewById(R.id.taskImage);
		size = getBarHeight(6);
		holder.taskWord.getLayoutParams().height = size;
		holder.taskWord.getLayoutParams().width = size;
		holder.taskImage.getLayoutParams().height = size;
		holder.taskImage.getLayoutParams().width = size;
		
		holder.taskWord.setBackground(mContext.getResources().getDrawable(R.drawable.black_border));
		holder.taskWord.setTag("");
		holder.taskImage.setTag("");
		
		rowView.setBackgroundColor(0);
		if(isWord){
			holder.taskWord.setText(resources.get(position));
			holder.taskWord.setTag(resources.get(position));
			holder.taskWord.setVisibility(View.VISIBLE);
			holder.taskImage.setVisibility(View.INVISIBLE);
			
		
		}else{
			holder.taskImage.setVisibility(View.VISIBLE);
			holder.taskWord.setVisibility(View.INVISIBLE);
			
			String imageUrl = mContext.getString(R.string.resource_url) + resources.get(position);
			androidAQuery.id(holder.taskImage).image(imageUrl, true, true, 0, 0,
					new BitmapAjaxCallback() {
						@Override
						public void callback(String url, ImageView iv, Bitmap bm,
								AjaxStatus status) {

							holder.taskImage.setImageBitmap(bm);
							
							holder.taskImage.setTag(resources.get(position));
						}
					}.header("User-Agent", "android"));
			
		}
			rowView.setOnDragListener(myDragEventListener);  
			DoingTaskActivity.dragViewList.add(rowView);
		
		return rowView;
	}

	// references to our images

	public class Holder {
		CTTextView taskWord;
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

			return 190;
		}

	}
}

