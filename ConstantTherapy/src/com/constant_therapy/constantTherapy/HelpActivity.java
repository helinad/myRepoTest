package com.constant_therapy.constantTherapy;

/*
 * @Developed By: Arumugam
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ViewFlipper;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.dashboard.HelpOverlay;
import com.constant_therapy.provider.TherapyContract.Help;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.widget.CTTextView;
import com.google.gson.Gson;

public class HelpActivity extends Activity implements
		MyResultReceiver.Receiver, LoaderManager.LoaderCallbacks<Cursor> {

	HelpOverlay helpHierarchy;
	private MyResultReceiver mReceiver;
	private static final String TAG = "HelpTasks";
	Dialog dialog;
	ViewFlipper viewFlipper;
	private AQuery androidAQuery;

	int helpId = 0;

	String url = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		androidAQuery = new AQuery(HelpActivity.this);
		setContentView(R.layout.help);
		viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
		registerReceiver();
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {

				helpId = extras.getInt("helpid");
				Log.v("helpId", "" + helpId);

			}
		}
		callService(helpId);

	}

	private void setFlipperImage(String res) {
		Log.i("Set Filpper Called", res + "");
		final ImageView image = new ImageView(getApplicationContext());
		androidAQuery.id(image).image(res, true, true, 0, 0,
				new BitmapAjaxCallback() {
					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {
						image.setImageBitmap(bm);
						image.setScaleType(ScaleType.FIT_XY);
					}
				}.header("User-Agent", "android"));
		viewFlipper.addView(image);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiveResult(resultCode=" + resultCode + ",resultData="
				+ resultData.toString());

		switch (resultCode) {
		case SyncService.STATUS_RUNNING:
			
			break;
		case SyncService.STATUS_FINISHED:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.HELP_OVERLAY_TOKEN) {

				this.getLoaderManager().initLoader(
						Constants.HELP_OVERLAY_TOKEN, null, this);

			}

			Log.v(TAG, "ENDS");
			break;
		case SyncService.STATUS_ERROR:
			if (resultData.isEmpty()) {
				Log.v("STATUS_ERROR_CALLED", "STATUS_ERROR_CALLED");
				if (dialog == null)
						showInternetAlert("Oops!", getString(R.string.no_internet));

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.INVALID_ACCESSTOKEN) {
				if (dialog != null)
						dialog.dismiss();

			}
			break;
		case SyncService.STATUS_NO_NETWORK:
			Log.v("STATUS_NO_NETWORK", "STATUS_NO_NETWORK");
			if (dialog == null)
					showInternetAlert("Oops!", getString(R.string.no_internet));

			break;
		}

	}

	@Override
	public void registerReceiver() {
		// TODO Auto-generated method stub

		mReceiver = new MyResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case Constants.HELP_OVERLAY_TOKEN:

			return new CursorLoader(this, Help.CONTENT_URI, null, null, null,
					Help.DEFAULT_SORT);

		default:

			return null;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
		// TODO Auto-generated method stub
		if (data.getCount() < 1) {

			return;
		}
		switch (arg0.getId()) {

		case Constants.HELP_OVERLAY_TOKEN:
			data.moveToFirst();
			helpHierarchy = new HelpOverlay();
			String json = data.getString(data.getColumnIndex(Help.JSON));
			Gson gson = new Gson();
			helpHierarchy = gson.fromJson(json, HelpOverlay.class);
			
			for (int i = 0; i < helpHierarchy.getHelpImage().size(); i++) {
				// This will create dynamic image view and add them to
				// ViewFlipper
				setFlipperImage(helpHierarchy.getHelpImage().get(i));
			}

			viewFlipper.setAutoStart(true);
			viewFlipper.setFlipInterval(Integer.parseInt(helpHierarchy
					.getDuration()));
			viewFlipper.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:

						return true;
					case MotionEvent.ACTION_CANCEL:
					case MotionEvent.ACTION_OUTSIDE:

						return true;
					case MotionEvent.ACTION_UP:

						if (viewFlipper.getDisplayedChild() == helpHierarchy
								.getHelpImage().size() - 1) {

							if (viewFlipper.isFlipping())// Checking flipper is
															// flipping or not.
							{
								viewFlipper.stopFlipping();

								// stops the flipping .
							}
							finish();
						} else {

							viewFlipper.showNext();
						}

						return true;
					}
					return false;
				}
			});

			break;

		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	private void showInternetAlert(final String title, String message) {
		dialog = new Dialog(HelpActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		dialog.setCancelable(false);

		CTTextView text = (CTTextView) dialog.findViewById(R.id.tv);
		text.setText(title);
		CTTextView mess = (CTTextView) dialog.findViewById(R.id.etsearch);
		if (message != null) {

			mess.setText(message);
			mess.setVisibility(View.VISIBLE);
		} else {
			mess.setVisibility(View.GONE);
		}
		Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
		// if button is clicked, close the custom dialog
		if (title.equalsIgnoreCase(getResources().getString(
				R.string.app_version))) {
			dialogButton.setText("Ok");
		} else {
			dialogButton.setText("Okay");
		}
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		dialog.show();

	}

	private void callService(int helpId) {

		switch (helpId) {
		case Constants.CLINICIANSUMMARY:
			url = getString(R.string.helpoverlaycliniciansummary);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.HELP_OVERLAY_TOKEN, url);
			break;
		case Constants.CLINICIANTASKS:
			url = getString(R.string.helpoverlaycliniciantasks);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.HELP_OVERLAY_TOKEN, url);

			break;
		case Constants.PATIENTLOGINSUMMARY:
			url = getString(R.string.helpoverlayadvancesummary);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.HELP_OVERLAY_TOKEN, url);
			break;
		case Constants.PATIENTLOGINTASK:
			url = getString(R.string.helpoverlayadvancetasks);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.HELP_OVERLAY_TOKEN, url);
			break;
		case Constants.PATIENTLOGINLEFT:
			url = getString(R.string.helpoverlayleft);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.HELP_OVERLAY_TOKEN, url);
			break;
		case Constants.PATIENTLOGINRIGHT:
			url = getString(R.string.helpoverlayright);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.HELP_OVERLAY_TOKEN, url);
			break;

		default:

			break;
		}

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onRestart() {
		super.onRestart();

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {
		// do nothing.

		return;
	}

}
