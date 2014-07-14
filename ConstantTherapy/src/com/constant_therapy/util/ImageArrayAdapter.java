package com.constant_therapy.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ImageArrayAdapter extends ArrayAdapter<String> {
	private LayoutInflater mInflater;
	private ArrayList<String> imageUrl = new ArrayList<String>();
	AQuery androidAQuery;

	public ImageArrayAdapter(Context context, ArrayList<String> values) {
		super(context, R.layout.image_choice, values);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		imageUrl = values;
		androidAQuery = new AQuery(context);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrl.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrl.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;

		if (convertView == null) {
			// Inflate the view since it does not exist
			convertView = mInflater.inflate(R.layout.image_choice, null);

			// Create and save off the holder in the tag so we get quick access
			// to inner fields
			// This must be done for performance reasons
			holder = new Holder();
			holder.imgView = (ImageView) convertView
					.findViewById(R.id.imgChoice);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		UrlImageViewHelper.setUrlDrawable(holder.imgView, imageUrl.get(position), R.drawable.ic_launcher);
		
		Log.v("imageUrl", imageUrl.get(position));
		/*androidAQuery.id(holder.imgView).progress(R.id.pgImageChoice).image(imageUrl.get(position), true, true, 0,
				0, new BitmapAjaxCallback() {
					@Override
					public void callback(String url, ImageView iv,
							Bitmap bm, AjaxStatus status) {
						holder.imgView.setImageBitmap(bm);

					}
				}.header("User-Agent", "android"));*/

		return convertView;
	}

	/** View holder for the views we need access to */
	private static class Holder {
		public ImageView imgView;
	}
}
