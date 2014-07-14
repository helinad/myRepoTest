package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ImageChoiceFragment extends Fragment{
	ImageView imgSimuli, imgSimuliAudio;
	ProgressBar imgProgress;
	ViewGroup mRootView;
	
	private AQuery androidAQuery;
	
	public static ImageChoiceFragment newInstance(int someInt, String imageUrl) {
		ImageChoiceFragment fragmentDemo = new ImageChoiceFragment();
		Bundle args = new Bundle();
		args.putInt("someInt", someInt);
		args.putString("imageUrl", imageUrl);
		fragmentDemo.setArguments(args);
		return fragmentDemo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.image_choice,
				container, false);

		return mRootView;
	}

	@Override
	public void onStart() {
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already
		// been
		// applied to the fragment at this point so we can safely call the
		// method
		// below that sets the article text.

		

	}

	public void updateArticleView(String imageUrl) {

		UrlImageViewHelper.setUrlDrawable(imgSimuli, imageUrl, R.drawable.ic_launcher);
		/*androidAQuery = new AQuery(getActivity());
		androidAQuery.id(imgSimuli).progress(imgProgress).image(imageUrl, true, true, 0, 0,
				new BitmapAjaxCallback() {

					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {
						imgSimuli.setImageBitmap(bm);

					}
				}.header("User-Agent", "android"));*/
		imgSimuli.setTag(imageUrl);
		Log.v("ImageChoice", ""+imageUrl);
		

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		imgSimuli = (ImageView) getActivity().findViewById(R.id.imgChoice);
		imgProgress = (ProgressBar) getActivity().findViewById(R.id.pgImageChoice);
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			updateArticleView(args.getString("imageUrl"));
		}

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		
	}

	
}
