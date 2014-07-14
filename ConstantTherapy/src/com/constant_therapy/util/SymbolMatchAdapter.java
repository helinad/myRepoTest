package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.widget.ShadowImageView;

public class SymbolMatchAdapter extends BaseAdapter {

	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	AQuery androidAQuery;

	List<String> resources = new ArrayList<String>();
	String resourceUrl = null;

	private LayoutInflater layoutInflator;

	int size;
	MediaPlayer mPlayerOnline;
	private DisplayMetrics mDisplayMetrics;

	public SymbolMatchAdapter(Context c, List<String> resources,
			String resourceUrl) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.resourceUrl = resourceUrl;
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

		rowView = layoutInflator.inflate(R.layout.symbol_image, null);
		holder.taskImage = (ShadowImageView) rowView.findViewById(R.id.taskImage);
		holder.checkImage = (ImageView) rowView.findViewById(R.id.checkImage);

		size = getBarHeight(getCount());
		holder.taskImage.getLayoutParams().height = size;
		holder.taskImage.getLayoutParams().width = size;

		holder.checkImage.setVisibility(View.INVISIBLE);
		holder.checkImage.getLayoutParams().height = (int) (size / 2);
		holder.checkImage.getLayoutParams().width = (int) (size / 2);

		/**
		 * Bind a click listener to initiate the flip transitions
		 */

		String imageUrl = resourceUrl + resources.get(position);
		holder.taskImage.setTag("");
		androidAQuery.id(holder.taskImage).image(imageUrl, true, true, 0, 0,
				new BitmapAjaxCallback() {
					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {

						holder.taskImage.setImageBitmap(bm);
						holder.taskImage.setScaleType(ScaleType.FIT_XY);
						holder.taskImage.setTag(resources.get(position));
					}
				}.header("User-Agent", "android"));

		
			DoingTaskActivity.symbolList.add(rowView);

		return rowView;
	}

	// references to our images

	public class Holder {

		ImageView checkImage;
		ShadowImageView taskImage;

	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {
			switch (count) {
			case 9:
				return 150;

			case 16:

				return 110;
			case 36:

				return 70;
			case 49:

				return 60;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {
			switch (count) {
			case 9:
				return 170;

			case 16:

				return 125;
			case 36:

				return 80;
			case 49:

				return 70;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
			
		}

		case DisplayMetrics.DENSITY_LOW: {
			switch (count) {
			case 9:
				return 150;

			case 16:

				return 110;
			case 36:

				return 70;
			case 49:

				return 60;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {
			switch (count) {
			case 9:
				return 150;

			case 16:

				return 110;
			case 36:

				return 70;
			case 49:

				return 60;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
		}

		default:
			switch (count) {
			case 9:
				return 240;

			case 16:

				return 180;
			case 36:

				return 115;
			case 49:

				return 100;
			case 30:

				return 240;

			default:
				break;
			}
			return 170;
		}

	}

}
