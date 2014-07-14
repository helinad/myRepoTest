package com.constant_therapy.constantTherapy;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.constant_therapy.charts.PieChart;
import com.constant_therapy.charts.PieSlice;
import com.constant_therapy.dashboard.SummaryResponse;
import com.constant_therapy.dashboard.TaskResults;
import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.provider.TherapyContract.TaskHierarchy;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.Helper;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.widget.CTTextView;
import com.constant_therapy.widget.ProgressHUD;
import com.google.gson.Gson;

public class SummaryActivity extends CTActivity implements
		MyResultReceiver.Receiver, LoaderManager.LoaderCallbacks<Cursor>,
		OnCancelListener, OnClickListener {

	private static final String TAG = "SummaryActivity";
	private MyResultReceiver mReceiver;

	SummaryResponse summaryResponse = new SummaryResponse();

	ProgressHUD mProgressHUD;

	PieChart pg;

	Dialog dialog;

	CTTextView tvTaskName;
	CTTextView tvMax;
	CTTextView tvMin;
	CTTextView tvAvg;
	CTTextView tvPercent;

	Button btnYes;
	Button btnNo;

	String sessionId;
	String userId;
	String patientId;
	String displayName;
	String taskTypeId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.summary_popup);
		getWindow().setFlags(
			    WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setFinishOnTouchOutside(false);

		pg = (PieChart) findViewById(R.id.chart);
		tvPercent = (CTTextView) findViewById(R.id.tvPercent);
		tvTaskName = (CTTextView) findViewById(R.id.tvTaskname);
		tvMax = (CTTextView) findViewById(R.id.tvMax);
		tvMin = (CTTextView) findViewById(R.id.tvMin);
		tvAvg = (CTTextView) findViewById(R.id.tvAvg);
		btnYes = (Button) findViewById(R.id.btnYes);
		btnNo = (Button) findViewById(R.id.btnNo);

		btnNo.setOnClickListener(this);
		btnYes.setOnClickListener(this);

		displayName = getIntent().getStringExtra("displayName");
		userId = getIntent().getStringExtra("userId");
		patientId = getIntent().getStringExtra("patientId");
		sessionId = getIntent().getStringExtra("sessionId");
		taskTypeId = getIntent().getStringExtra("typeId");

		registerReceiver();

	}

	public static String format(float f) {
		if (f == (int) f)
			return String.format("%.2f", f);
		else
			return String.format("%.2f", f);
	}

	private void loadSummaryValues(String displayName, SummaryResponse response) {
		if (response.getTaskResults().size() > 0) {
			int taskLevel = response.getTaskResults().get(0).getTaskLevel();
			int taskCount = response.getCompletedTaskCount();
			tvTaskName.setText(displayName + " (Level " + taskLevel + ")\n"
					+ (taskCount) + " items");

			DecimalFormat df = new DecimalFormat("###.00");
			String max = df.format(response.getTaskResults().get(0)
					.getMaxLatency());
			String min = df.format(response.getTaskResults().get(0)
					.getMinLatency());
			String avg = df.format(response.getTaskResults().get(0)
					.getAvgLatency());

			tvMax.setText("" + String.format("%.2f", Double.parseDouble(max))
					+ " sec");
			tvMin.setText("" + String.format("%.2f", Double.parseDouble(min))
					+ " sec");
			tvAvg.setText("" + String.format("%.2f", Double.parseDouble(avg))
					+ " sec");

		} else {
			tvMax.setText("" + 0 + " sec");
			tvMin.setText("" + 0 + " sec");
			tvAvg.setText("" + 0 + " sec");
			tvTaskName.setText(displayName + " (Level " + 1 + ")\n" + (4)
					+ " items");

		}
		displayDonutChart(response);
	}

	private void finishActivity() {
		new Handler().postDelayed(new Runnable() {

			public void run() {
				DoingTaskActivity.doingTask.finish();
				overridePendingTransition(R.anim.slide_in_top,
						R.anim.slide_out_bottom);

			}

		}, Constants.SKIP_DELAY / 2);
	}

	@SuppressWarnings("deprecation")
	private void displayDonutChart(SummaryResponse response) {

		float thickness = (pg.getHeight() / 3) - 5;

		Log.v("thickness", "" + thickness);
		ShapeDrawable circle = new ShapeDrawable(new OvalShape());
		circle.setIntrinsicHeight((int) (Math.round(thickness
				+ Constants.POPUP_TASKCOUNT)));
		circle.setIntrinsicWidth((int) (Math.round(thickness
				+ Constants.POPUP_TASKCOUNT)));
		circle.getPaint().setColor(Color.WHITE);
		circle.setBounds(1, 1, 1, 1);
		tvPercent.setBackgroundDrawable(circle);
		if (response.getTaskResults().size() > 0) {
			TaskResults chartItem = response.getTaskResults().get(0);
			if (chartItem != null) {

				PieSlice slice = new PieSlice();

				slice.setColor(getResources().getColor(R.color.green));
				slice.setValue(Math.round(response.getPercentCorrect() * 100.0));
				slice.setThickness(thickness);
				pg.addSlice(slice);

				slice = new PieSlice();
				slice.setColor(getResources().getColor(R.color.orange));
				slice.setValue(Math.round(response.getPercentIncorrect() * 100.0));
				slice.setThickness(thickness);
				pg.addSlice(slice);

				slice = new PieSlice();
				slice.setColor(getResources().getColor(R.color.yellow));
				slice.setValue(Math.round(response.getPercentSkipped() * 100.0));
				slice.setThickness(thickness);
				pg.addSlice(slice);

				Integer i = (int) Math.round(chartItem.getAccuracy() * 100.0);
				tvPercent.setText(i + "%");

			} else {
				PieSlice slice = new PieSlice();
				slice.setColor(getResources().getColor(R.color.orange));
				slice.setValue(100);
				slice.setThickness(thickness);
				pg.addSlice(slice);
				slice = new PieSlice();
				slice.setColor(getResources().getColor(R.color.orange));
				slice.setValue(0);
				slice.setThickness(thickness);
				pg.addSlice(slice);
				tvPercent.setText(0 + "%");

			}
		} else {
			thickness = 120;
			PieSlice slice = new PieSlice();
			slice.setColor(getResources().getColor(R.color.orange));
			slice.setValue(100);
			slice.setThickness(thickness);
			pg.addSlice(slice);
			slice = new PieSlice();
			slice.setColor(getResources().getColor(R.color.orange));
			slice.setValue(0);
			slice.setThickness(thickness);
			pg.addSlice(slice);
			tvPercent.setText(0 + "%");

			circle = new ShapeDrawable(new OvalShape());
			circle.setIntrinsicHeight((int) (Math.round(thickness
					+ Constants.POPUP_TASKCOUNT)));
			circle.setIntrinsicWidth((int) (Math.round(thickness
					+ Constants.POPUP_TASKCOUNT)));
			circle.getPaint().setColor(Color.WHITE);
			circle.setBounds(1, 1, 1, 1);
			tvPercent.setBackgroundDrawable(circle);
		}

		Log.v(TAG, "SHOWED DONUT");
	}

	private void callSummaryResponse(String sessionId) {
		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + patientId
				+ getString(R.string.remote_summaryresponse) + sessionId;
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.SUMMARY_RESPONSE_TOKEN, url);
	}

	private void callBaselineService(String sessionId, String baseline) {
		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + patientId
				+ getString(R.string.remote_session) + sessionId
				+ getString(R.string.remote_end);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.TASK_BASELINE_TOKEN, url, baseline, true, "get");
	}

	/*
	 * Method for showing internet alert.
	 */

	private void showInternetAlert(String title, String message) {
		dialog = new Dialog(SummaryActivity.this);
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

	@Override
	public void onCancel(DialogInterface arg0) {
		// TODO Auto-generated method stub
		mProgressHUD.dismiss();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
		// TODO Auto-generated method stub
		switch (id) {

		case Constants.SUMMARY_RESPONSE_TOKEN:
			return new CursorLoader(this, TaskHierarchy.CONTENT_URI, null,
					null, null, TaskHierarchy.DEFAULT_SORT);

		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
		// TODO Auto-generated method stub
		if (data.getCount() < 1) {

			return;
		}
		switch (arg0.getId()) {

		case Constants.SUMMARY_RESPONSE_TOKEN: {
			data.moveToFirst();

			String json = data.getString(data
					.getColumnIndex(TaskHierarchy.JSON));
			Log.v(TAG, json);
			summaryResponse = new SummaryResponse();
			Gson gson = new Gson();
			summaryResponse = gson.fromJson(json, SummaryResponse.class);
			pg.removeSlices();
			loadSummaryValues(displayName, summaryResponse);

			this.getContentResolver().delete(TaskHierarchy.CONTENT_URI, null,
					null);
			break;
		}

		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiveResult(resultCode=" + resultCode + ", resultData="
				+ resultData.toString());

		switch (resultCode) {
		case SyncService.STATUS_RUNNING:

			break;
		case SyncService.STATUS_FINISHED:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.SUMMARY_RESPONSE_TOKEN) {

				this.getLoaderManager().initLoader(
						Constants.SUMMARY_RESPONSE_TOKEN, null, this);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.TASK_BASELINE_TOKEN) {

			}

			Log.v(TAG, "ENDS");
			break;
		case SyncService.STATUS_ERROR:
			if (resultData.isEmpty()) {

				if (dialog == null)
					showInternetAlert("Oops!", getString(R.string.no_internet));

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.INVALID_ACCESSTOKEN) {
				if (dialog != null)
					dialog.dismiss();
				clearDbAndSession();
				animatedStartActivity(true);

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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnYes:
			if (Helper.isMultiTask(taskTypeId)) {
				finish();
				finishActivity();
			} else {
				finish();
				finishActivity();
				callBaselineService(sessionId, "1");
			}
			btnYes.setClickable(false);
			btnNo.setClickable(false);
			break;

		case R.id.btnNo:
			if (Helper.isMultiTask(taskTypeId)) {
				finish();
				finishActivity();
			} else {
				finish();
				finishActivity();
				callBaselineService(sessionId, "0");
			}
			btnYes.setClickable(false);
			btnNo.setClickable(false);

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
	public void onBackPressed() {
		// do nothing.

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
	protected void onResume() {

		super.onResume();
		Log.v(TAG, "OnResume");
		if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {

			if (Helper.isMultiTask(taskTypeId)) {
				loadSummaryValues(displayName, summaryResponse);
			} else {
				callSummaryResponse(sessionId);
			}

		} else {
			showInternetAlert("Oops!", getString(R.string.no_internet));

		}

	}

}
