package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
import com.constant_therapy.util.ActiveDropAdapter.Holder;
import com.constant_therapy.widget.CTTextView;

public class OrderDropAdapter extends BaseAdapter {

	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();

	
	List<String> itemList = new ArrayList<String>();
	String name = null;
	View pView;
	int pos;
	char[] nameArray;

	private LayoutInflater layoutInflator;
	MyDragEventListener myDragEventListener;

	int size;
	private DisplayMetrics mDisplayMetrics;
	boolean isWord;
	AQuery androidAQuery;

	public OrderDropAdapter(Context c, List<String> itemList, boolean isWord,
			MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.isWord = isWord;
		this.itemList = itemList;
		this.myDragEventListener = myDragEventListener;
		Collections.sort(itemList, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		androidAQuery = new AQuery(mContext);
		layoutInflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDisplayMetrics = c.getResources().getDisplayMetrics();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
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
		holder.taskText = (CTTextView) rowView.findViewById(R.id.taskWord);
		holder.taskImage = (ImageView) rowView.findViewById(R.id.taskImage);
		size = getBarHeight(6);
		holder.taskText.getLayoutParams().height = (int) (size/1.5);
		holder.taskImage.getLayoutParams().height = (int) (size/1.5);
		
		holder.taskText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.btn_ok));
		holder.taskText.setTypeface(null, Typeface.NORMAL);
		holder.taskText.setTag("");
		holder.taskImage.setTag("");
		if(isWord){
			holder.taskImage.setVisibility(View.INVISIBLE);
			holder.taskText.setVisibility(View.INVISIBLE);
			holder.taskText.setText(itemList.get(position));
			holder.taskText.setTag(itemList.get(position));
			rowView.setOnDragListener(myDragEventListener);
			DoingTaskActivity.answerDropList.add(holder.taskText);
		}else{
			holder.taskImage.setVisibility(View.INVISIBLE);
			holder.taskText.setVisibility(View.INVISIBLE);		
			
			String imageUrl = mContext.getString(R.string.resource_url) + itemList.get(position);
		
			androidAQuery.id(holder.taskImage).image(imageUrl, true, true, 0, 0,
					new BitmapAjaxCallback() {
						@Override
						public void callback(String url, ImageView iv, Bitmap bm,
								AjaxStatus status) {

							holder.taskImage.setImageBitmap(bm);
							
							holder.taskImage.setTag(itemList.get(position));
						}
					}.header("User-Agent", "android"));

			
			rowView.setOnDragListener(myDragEventListener);
			DoingTaskActivity.answerOrderDropList.add(holder.taskImage);
		}
		

		return rowView;
	}

	// references to our images

	public class Holder {

		CTTextView taskText;
		ImageView taskImage;
	}

	private int getMissing(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i != list.get(i))
				return i;
		}
		return 0;
	}

	private Boolean isViewExist(String item) {
		for (int i = 0; i < DoingTaskActivity.dropViewList.size(); i++) {
			if (item.equalsIgnoreCase(DoingTaskActivity.dropViewList.get(i)
					.findViewById(R.id.taskWord).getTag().toString())) {
				return true;
			}
		}

		return false;
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 165;
			case 20:

				return 125;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 165;
			case 20:

				return 120;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_LOW: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 130;
			case 20:

				return 180;
			case 24:

				return 120;
			case 30:

				return 120;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {
			switch (count) {
			case 6:
				return 220;

			case 12:

				return 150;
			case 20:

				return 125;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}

		default:
			switch (count) {
			case 6:
				return 350;

			case 12:

				return 230;
			case 20:

				return 170;
			case 24:

				return 170;
			case 30:

				return 135;

			default:
				break;
			}

		}
		return 0;
	}

}


