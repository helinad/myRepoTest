package com.constant_therapy.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.PlayTasks;
import com.constant_therapy.widget.CTTextView;

public class PlayingCardFragment extends Fragment implements OnClickListener {
	ImageView imgSimuli, imgSimuliAudio;
	ViewGroup mRootView;
	public static PlayTasks resources;
	public static ArrayList<String> resourcearray;
	public static ArrayList<String> removeOccurance = new ArrayList<String>();
	public static ViewFlipper viewFlipper;
	AQuery androidAQuery;
	boolean firstClick = false;
	MediaPlayer mPlayerLocal;
	Animation shake;
	public static Timer mTimer;
	OnPlayCardClickListener mCallback;
	String displayname;
	int playCardPos;
	public static List<View> viewflipperList;
	static String taskLevel;

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnPlayCardClickListener {
		/** Called by HeadlinesFragment when a list item is selected */

		public void OnPlayCardClick(View v, String taskLevel);

	}

	public static PlayingCardFragment newInstance(String stringtask,
			PlayTasks playTasks, String displayName, int playCardPos,
			String taskLevel) {

		PlayingCardFragment fragmentDemo = new PlayingCardFragment();
		Bundle args = new Bundle();
		args.putString("someString", stringtask);
		args.putSerializable("taskArray", playTasks);
		args.putString("displayname", displayName);
		args.putInt("playCardPos", playCardPos);
		args.putString("taskLevel", taskLevel);
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

		androidAQuery = new AQuery(getActivity().getApplicationContext());
		mRootView = (ViewGroup) inflater.inflate(R.layout.playcardmatching,
				container, false);

		// view
		return mRootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		viewFlipper = (ViewFlipper) getActivity().findViewById(
				R.id.ViewFlipper01);

		if (viewFlipper.getChildCount() > 0)
			viewFlipper.removeAllViews();
		viewflipperList = new ArrayList<View>();

		Bundle args = getArguments();
		if (args != null) {

			resources = (PlayTasks) args.getSerializable("taskArray");
			playCardPos = args.getInt("playCardPos");
			displayname = args.getString("displayname");
			taskLevel = args.getString("taskLevel");
			removeOccurance.clear();
			for (int k = 0; k < resources.getResources().size(); k++) {
				if (resources.getResources().get(k).endsWith(".jpg")) {
					removeOccurance.add(resources.getResources().get(k));
				}
			}
			for (int i = 0; i < removeOccurance.size(); i++) {
				// This will create dynamic image view and add them to
				// ViewFlipper

				setFlipperImage(removeOccurance.get(i),
						resources.getResourceUrl(), i, displayname);

			}

		}

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnPlayCardClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OnMicroPhoneClickListener");
		}
	}

	private void setFlipperImage(final String res, String resurl, final int i,
			final String displayname) {

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getBaseContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		View vi = inflater.inflate(R.layout.playingimage, viewFlipper, false);
		mTimer = new Timer();
		final ImageView image = (ImageView) vi.findViewById(R.id.imageView1);
		CTTextView b = (CTTextView) vi.findViewById(R.id.button1);
		View PlayView = (View) vi.findViewById(R.id.view);
		DoingTaskActivity.playviewList = PlayView;
		if (i == 0) {

			b.setText("Tap to Start");

		} else {

			b.setVisibility(View.GONE);
		}
		vi.setClickable(false);
		vi.setOnClickListener(this);
		androidAQuery.id(image).image(resurl + res, true, true, 0, 0,
				new BitmapAjaxCallback() {
					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {

						image.setImageBitmap(bm);

					}
				}.header("User-Agent", "android"));
		viewflipperList.add(vi);

		viewFlipper.addView(vi);

	}

	public static void isLastDisplayed() {

		if (viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1) {
			viewFlipper.stopFlipping();
			mTimer.cancel();

		}
	}

	public static int getChild() {
		return viewFlipper.getDisplayedChild();

	}

	protected boolean isFirstClick(boolean b) {
		return b;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		mCallback.OnPlayCardClick(v, taskLevel);
	}

}
