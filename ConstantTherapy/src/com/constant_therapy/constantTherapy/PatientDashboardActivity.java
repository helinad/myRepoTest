package com.constant_therapy.constantTherapy;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.constant_therapy.animation.ActivitySwitcher;
import com.constant_therapy.charts.MultiChartView;
import com.constant_therapy.charts.PieChart;
import com.constant_therapy.charts.PieSlice;
import com.constant_therapy.dashboard.Dashboard;
import com.constant_therapy.dashboard.Scores;
import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.popup.AlertPopup;
import com.constant_therapy.popup.PopoverView;
import com.constant_therapy.popup.PopoverView.PopoverViewDelegate;
import com.constant_therapy.provider.TherapyContract.Dashboards;
import com.constant_therapy.provider.TherapyContract.Session;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.Helper;
import com.constant_therapy.util.ListAdapter;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.util.PatientModel;
import com.constant_therapy.webservice.JSONParser;
import com.constant_therapy.widget.CTTextView;
import com.constant_therapy.widget.MySwitch;
import com.google.gson.Gson;

public class PatientDashboardActivity extends PatientSelectorActivity implements
		MyResultReceiver.Receiver, PopoverViewDelegate, OnItemClickListener,
		OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
	private static final int DASHBOARD_LOADER = 1;

	private static final String TAG = "PatientDashboardActivity";
	PopoverView popoverView;
	Animation animBlink;

	String updateEmail = null;
	String updatePhone = null;
	String updateFname = null;
	String updateLname = null;

	String currentPassword = null;
	String newPassword = null;
	String repeatPassword = null;
	String existingPassword = null;

	int updateCount = 0,click = 0;

	Animator anim1, anim2, anim3;
	Dialog dialog;
	JSONParser jsonParser;
	Dashboard dashBoard;

	MultiChartView overlay;

	ListAdapter listAdapter;
	ListView list;

	ImageView imgCTIcon;
	ImageView imgSetting;
	ImageView imgStart;

	RelativeLayout combined, middlelist;
	View layoutOverlay;
	LinearLayout sett, llSecondRow, llPichart, llStart, patienthelp;
	LinearLayout glowLayer;

	PieChart pg;

	CTTextView tvItem, tvComp;
	CTTextView tvPercent;
	CTTextView tvUser;
	CTTextView tvNoData;

	ImageView pb1, pb2, pb3;

	MediaPlayer mPlayer;

	int countdownNumber;

	static Boolean isOnorOff = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		setContentView(R.layout.constant_therapy_chart);
		Log.v(TAG, "STARTS");

		pg = (PieChart) findViewById(R.id.chart);

		tvItem = (CTTextView) findViewById(R.id.txtCompletedItem);
		tvPercent = (CTTextView) findViewById(R.id.tvPercent);
		tvUser = (CTTextView) findViewById(R.id.tvUsername);
		tvComp = (CTTextView) findViewById(R.id.textView7);
		tvNoData = (CTTextView) findViewById(R.id.nodata);

		imgCTIcon = (ImageView) findViewById(R.id.ctIcon);
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
		imgStart = (ImageView) findViewById(R.id.imgStart);

		list = (ListView) findViewById(R.id.listView1);
		list.setOnItemClickListener(this);

		pb1 = (ImageView) findViewById(R.id.progressBar1);
		pb2 = (ImageView) findViewById(R.id.progressBar2);
		pb3 = (ImageView) findViewById(R.id.progressBar3);

		combined = (RelativeLayout) findViewById(R.id.combinedChart);
		middlelist = (RelativeLayout) findViewById(R.id.rlMiddlelist);
		sett = (LinearLayout) findViewById(R.id.linearLayout12);
		glowLayer = (LinearLayout) findViewById(R.id.glowStart);
		llPichart = (LinearLayout) findViewById(R.id.llPichart);
		llSecondRow = (LinearLayout) findViewById(R.id.llSecondRow);
		llStart = (LinearLayout) findViewById(R.id.llStart);
		patienthelp = (LinearLayout) findViewById(R.id.linearLayout2);

		layoutOverlay = (View) findViewById(R.id.view);

		tvItem.setVisibility(View.INVISIBLE);
		tvComp.setVisibility(View.INVISIBLE);
		registerReceiver();

		imgCTIcon.setOnClickListener(this);
		sett.setOnClickListener(this);
		patienthelp.setOnClickListener(this);
		mPlayer = MediaPlayer.create(PatientDashboardActivity.this,
				R.raw.welcome_to_constant_therapy);

		mPlayer.start();

		isOnorOff = Helper.isRight(PatientDashboardActivity.this);

		shiftLayout(isOnorOff);
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub

				Log.v(TAG, " END PLAYER");

			}
		});

	}

	private void showGlow(View v) {
		animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.glow);

		// set animation listener

		v.setAnimation(animBlink);
		v.startAnimation(animBlink);

		imgStart.bringToFront();

	}

	private void shiftLayout(Boolean isRight) {
		Animation anim1 = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);

		anim1.setDuration(500);

		Animation anim2 = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right);

		anim2.setDuration(500);

		if (isRight) {

			llStart.startAnimation(anim1);

			new Handler().postDelayed(new Runnable() {

				public void run() {
					llSecondRow.removeAllViews();
					llSecondRow.addView(llPichart);
					llSecondRow.addView(middlelist);

					llSecondRow.addView(llStart);

				}

			}, anim1.getDuration());
		} else {

			llStart.startAnimation(anim2);

			new Handler().postDelayed(new Runnable() {

				public void run() {
					llSecondRow.removeAllViews();

					llSecondRow.addView(llStart);
					llSecondRow.addView(llPichart);
					llSecondRow.addView(middlelist);

				}

			}, anim1.getDuration());

		}

	}

	private void loadingStart() {
		pb1.setVisibility(View.VISIBLE);
		pb2.setVisibility(View.VISIBLE);
		pb3.setVisibility(View.VISIBLE);
		layoutOverlay.setVisibility(View.VISIBLE);
		layoutOverlay.setClickable(true);

		anim1 = AnimatorInflater.loadAnimator(this, R.anim.flip_on_vertical);
		anim2 = AnimatorInflater.loadAnimator(this, R.anim.flip_on_vertical);
		anim3 = AnimatorInflater.loadAnimator(this, R.anim.flip_on_vertical);
		anim1.setTarget(pb1);
		anim2.setTarget(pb2);
		anim3.setTarget(pb3);
		anim1.start();
		anim2.start();
		anim3.start();
	}

	private void loadingStop() {

		if (anim1 != null) {
			anim1.cancel();
			anim2.cancel();
			anim3.cancel();
			pb1.setVisibility(View.GONE);
			pb2.setVisibility(View.GONE);
			pb3.setVisibility(View.GONE);
			layoutOverlay.setVisibility(View.GONE);
			layoutOverlay.setClickable(false);
		}
	}

	private void initialLoad() {
		
		if (CTUser.getInstance().getIsLoggedIn() == true) {
			tvUser.setText(CTUser.getInstance().getUsername());
			if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {
				Log.v(TAG, " END PLAYER");
				loadingStart();
				String url = getString(R.string.remote_preurl)
						+ getString(R.string.remote_patient) + "/" + CTUser.getInstance().getUserId()
						+ getString(R.string.remote_dashboard);
				ServiceHelper.execute(getApplicationContext(), mReceiver,
						Constants.DASHBOARD_TOKEN, url);

			} else {
				showInternetAlert("Oops!", getString(R.string.no_internet));

			}

			Log.v(TAG, " STARTS PLAYER");

		} else {
			
			CTUser.getInstance().clear();
			CTUser.getInstance().clearAccessToken();
			
			animatedStartActivity();
			
			finish();
		}
	}

	/*
	 * Method for showing doughnut chart.
	 */

	@SuppressWarnings("deprecation")
	private void displayDonutChart(Dashboard chartItem) {
		float thickness = (pg.getHeight() / 3) - 5;
		Log.d("thickness", "" + thickness);
		ShapeDrawable circle = new ShapeDrawable(new OvalShape());
		circle.setIntrinsicHeight((int) (Math.round(thickness
				+ Constants.POPUP_TASKCOUNT)));
		circle.setIntrinsicWidth((int) (Math.round(thickness
				+ Constants.POPUP_TASKCOUNT)));
		circle.getPaint().setColor(Color.WHITE);
		circle.setBounds(1, 1, 1, 1);
		tvPercent.setBackgroundDrawable(circle);

		if (chartItem != null) {
			final int item = chartItem.getTotalTaskCount();
			if (item == 0) {
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
				tvItem.setVisibility(View.VISIBLE);
				tvComp.setVisibility(View.VISIBLE);
				tvItem.setText(String.valueOf(0) + " items");
			} else {

				double accuracyAverage = (Double) chartItem.getAccuacyAverage();
				double i = Math.round(accuracyAverage * 100.0);
				double j = 100.0 - i;
				double[] percent = { i, j };
				Arrays.sort(percent);

				PieSlice slice = new PieSlice();
				slice.setColor(getResources().getColor(R.color.orange));
				slice.setValue((float) percent[percent.length - 1]);
				slice.setThickness(thickness);
				pg.addSlice(slice);
				slice = new PieSlice();
				slice.setColor(getResources().getColor(R.color.green));
				slice.setValue((float) percent[percent.length - 2]);
				slice.setThickness(thickness);
				pg.addSlice(slice);

				int per = (int) percent[percent.length - 2];
				tvItem.setVisibility(View.VISIBLE);
				tvComp.setVisibility(View.VISIBLE);
				tvPercent.setText(per + "%");

				Log.v("totalItem", "" + item);
				DecimalFormat formatter = new DecimalFormat("#,###,###");
				String formattedString = formatter.format(item);
				tvItem.setText(formattedString + " items");
			}
			/*
			 * TimerTask countdownTask = new TimerTask() {
			 * 
			 * @Override public void run() { runOnUiThread(new Runnable() {
			 * 
			 * @Override public void run() { if (countdownNumber == item) {
			 * cancel(); } tvItem.setText(String.valueOf(countdownNumber) +
			 * " items"); countdownNumber++; } }); } };
			 * 
			 * Timer countdown = new Timer(); countdown.schedule(countdownTask,
			 * 0, 100);
			 */
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
			tvItem.setVisibility(View.VISIBLE);
			tvComp.setVisibility(View.VISIBLE);
			tvItem.setText(String.valueOf(0) + " items");
		}
		Log.v(TAG, "SHOWED DONUT");
	}

	/*
	 * Method for showing middle row bar list
	 */

	private void displayListView(Dashboard dashboardItem) {
		if (dashboardItem != null) {
			listAdapter = new ListAdapter(PatientDashboardActivity.this,
					dashboardItem.getLatestTaskTypeResults());
			listAdapter.notifyDataSetChanged();
			list.setAdapter(listAdapter);
		}
		Log.v(TAG, "LISTVIEW");

	}

	/*
	 * Method for showing combined (bar & line) chart.
	 */

	private void displayCombinedChart(Dashboard dashboardItem) {
		ArrayList<Scores> chartItem = new ArrayList<Scores>();

		float thickness = (combined.getHeight() / Constants.CHART_DISTANCE);
		if (dashboardItem != null)
			chartItem = dashboardItem.getScores();
		if (chartItem.size() == 0) {
			// combined.removeView(overlay);
			tvNoData.setVisibility(View.VISIBLE);
		} else {
			tvNoData.setVisibility(View.INVISIBLE);
			ArrayList<Long> date = new ArrayList<Long>();
			ArrayList<Double> accuracy = new ArrayList<Double>();
			ArrayList<Double> cummulativeAccuracy = new ArrayList<Double>();

			ArrayList<String> XAxis = new ArrayList<String>();
			ArrayList<Integer> lineList = new ArrayList<Integer>();
			ArrayList<Integer> barList = new ArrayList<Integer>();
			int XaisGap = 0;

			for (int i = 0; i < chartItem.size(); i++) {
				date.add((Long) chartItem.get(i).getDate());
				accuracy.add((Double) chartItem.get(i).getAccuracy());
				cummulativeAccuracy.add((Double) chartItem.get(i)
						.getCummulativeAccuracy());
			}

			for (int i = 0; i < date.size(); i++) {

				lineList.add((int) (cummulativeAccuracy.get(i) * 100));
				barList.add((int) (accuracy.get(i) * 100));
				XAxis.add(getDateFromTimeStamp(date.get(i) * 1000));
			}

			int days = getDays(getDateFormat(date.get(0)),
					getDateFormat(date.get(date.size() - 1)));

			if (days <= 0) {
				XaisGap = 1;
			} else {
				XaisGap = (int) Math.ceil(days / Constants.CHART_SLICE);
			}

			if (XAxis.size() < thickness) {

				for (int i = 0; i < thickness - XAxis.size(); i++) {

					barList.add(0);
					XAxis.add(addDate(XAxis.get(XAxis.size() - 1), XaisGap));
				}
			}

			overlay = new MultiChartView(getApplicationContext(), lineList,
					barList, XAxis, XaisGap);

			combined.addView(overlay);
			combined.bringChildToFront(pb3);

		}
		Log.v(TAG, "SHOWED CHART");
	}

	private static String addDate(String dt, int seal) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, seal); // number of days to add
		return dt = sdf.format(c.getTime());
	}

	/*
	 * Method for calculating days between first value and last value.
	 */
	private int getDays(Date date1, Date date2) {
		return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24l));
	}

	private Date getDateFormat(long time) {
		long dv = Long.valueOf(time) * 1000;// its need to be in milisecond
		Date df = new java.util.Date(dv);
		return df;

	}

	private String getDateFromTimeStamp(long time) {

		Date df = new java.util.Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
		String str = null;
		str = sf.format(df);

		return str;
	}

	/*
	 * Method for alert dialog of logout.
	 */

	public void ShowDialogWithDoubleButton(String title) {
		final Dialog dialog = new Dialog(PatientDashboardActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_logout);

		CTTextView text = (CTTextView) dialog.findViewById(R.id.tvTitle);
		text.setText(title);

		Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
		Button okbtn = (Button) dialog.findViewById(R.id.btnok);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		okbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				clearDbAndSession();
				animatedStartActivity();

			}
		});

		dialog.show();

	}

	private void animatedClinicianActivity() {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		
		final Intent intent = new Intent(getApplicationContext(),
				ClinicianActivity.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("patientName", CTUser.getInstance().getUsername());
		intent.putExtra("patientId", CTUser.getInstance().getUserId());
		ActivitySwitcher.animationOut(findViewById(R.id.container),
				getWindowManager(),
				new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						startActivity(intent);
						finish();
					}
				});
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void showPatientSettingDropDown(View v) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.patientsetting, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = popoverView.getWidth() + size.x;
		int height = popoverView.getHeight() + size.y;

		popoverView.setContentSizeForViewInPopover(new Point(width / 3,
				(int) (height / 1.5)));
		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionUp, true);

		LinearLayout logout = (LinearLayout) popoverView
				.findViewById(R.id.llLogout);
		LinearLayout llAdvance = (LinearLayout) popoverView
				.findViewById(R.id.llAdvance);
		MySwitch mySwitch = (MySwitch) popoverView.findViewById(R.id.switch1);
		mySwitch.isOnorOff(isOnorOff);

		LinearLayout tvUpadte = (LinearLayout) popoverView .findViewById(R.id.llUpdate);

		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				shiftLayout(isChecked);
				isOnorOff = isChecked;
				Helper.setIsRight(PatientDashboardActivity.this, isChecked);
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLogOut();
			}
		});

		llAdvance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dialog != null)
					dialog.dismiss();
				animatedClinicianActivity();

			}
		});
		
		
		tvUpadte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updatePopup(CTUser.getInstance().getEmail(), CTUser.getInstance().getPhoneNumber(), CTUser.getInstance().getFirstName(), CTUser.getInstance().getLastName());
			}
		});

	}

	

	private interface UserQuery {
		String[] PROJECTION = new String[] { Users.PATIENT_ID };
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.constanttherapy, menu); return true; }
	 */

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiveResult(resultCode=" + resultCode + ", resultData="
				+ resultData.toString());

		switch (resultCode) {
		case SyncService.STATUS_RUNNING:
			/*
			 * pb1.setVisibility(View.VISIBLE); pb2.setVisibility(View.VISIBLE);
			 * pb3.setVisibility(View.VISIBLE);
			 */
			loadingStart();
			break;
		case SyncService.STATUS_FINISHED:

			this.getLoaderManager().initLoader(DASHBOARD_LOADER, null, this);
			Log.v(TAG, "STOPS");
			break;
		case SyncService.STATUS_ERROR:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.INVALID_ACCESSTOKEN) {
				if (dialog != null)
					dialog.dismiss();
				clearDbAndSession();
				animatedStartActivity();
			} else if (resultData.isEmpty()) {
				if (dialog == null)
					showInternetAlert("Oops!", getString(R.string.no_internet));
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.VALIDATE_EMAIL_TOKEN) {
				this.getLoaderManager().initLoader(
						Constants.VALIDATE_EMAIL_TOKEN, null, this);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.UPDATE_ACCOUNT_TOKEN) {
				updateCount++;
				if (updateCount == 3) {
					updateCount = 0;
					updateDialog.dismiss();
					showAlert("Updated information successfully");
				}
			}

			else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CHANGE_PASSWORD_TOKEN) {
				passwordDialog.dismiss();
				showAlert("Password successfully changed");
				loadingStop();
				break;
			}
		case SyncService.STATUS_NO_NETWORK:
			if (dialog == null && !dialog.isShowing())
				showInternetAlert("Oops!", getString(R.string.no_internet));

			break;
		}
	}

	private void showLogOut() {
		final Dialog dialog = new Dialog(PatientDashboardActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_logout);
		dialog.setCancelable(false);
		Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
		Button okbtn = (Button) dialog.findViewById(R.id.btnok);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		okbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				clearDbAndSession();
				animatedStartActivity();

			}
		});

		dialog.show();

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

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

	@Override
	protected void onResume() {
		// animateIn this activity

		super.onResume();

		initialLoad();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.ctIcon:
			if (dialog == null || !dialog.isShowing())
				AlertPopup.showVersionAlert(PatientDashboardActivity.this,
						getString(R.string.app_version), null);
			break;
		case R.id.linearLayout12:

			showPatientSettingDropDown(imgSetting);

			break;
		case R.id.linearLayout2:
			/*
			 * @Developed By: Arumugam
			 */

			if (isOnorOff == true) {
				final Intent help = new Intent(getApplicationContext(),
						HelpActivity.class);
				help.putExtra("helpid", Constants.PATIENTLOGINLEFT);
				startActivity(help);

			} else {
				final Intent help = new Intent(getApplicationContext(),
						HelpActivity.class);
				help.putExtra("helpid", Constants.PATIENTLOGINRIGHT);
				startActivity(help);

			}

			break;

		default:
			break;
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		switch (id) {
		case DASHBOARD_LOADER:

			return new CursorLoader(this, Dashboards.CONTENT_URI, null, null,
					null, Dashboards.DEFAULT_SORT);

		case Constants.UPDATE_ACCOUNT_INFO:
			return new CursorLoader(this, Users.CONTENT_URI, null, null, null,
					Users.DEFAULT_SORT);
		case Constants.VALIDATE_EMAIL_TOKEN:
			return new CursorLoader(this, Session.CONTENT_URI, null, null,
					null, Session.DEFAULT_SORT);
		case Constants.CHANGE_PASSWORD_TOKEN:
			return new CursorLoader(this, Session.CONTENT_URI, null, null,
					null, Session.DEFAULT_SORT);

		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
		// TODO Auto-generated method stub

		if (data.getCount() != 1) {

			return;
		}
		switch (arg0.getId()) {
		case DASHBOARD_LOADER: {
			if (pg != null)
				pg.removeSlices();
			tvPercent.setText("");

			data.moveToFirst();

			dashBoard = new Dashboard();
			String json = data.getString(data.getColumnIndex(Dashboards.JSON));
			Gson gson = new Gson();
			dashBoard = gson.fromJson(json, Dashboard.class);

			displayCombinedChart(dashBoard);
			displayDonutChart(dashBoard);
			displayListView(dashBoard);

			loadingStop();
			break;
		}

		case Constants.UPDATE_ACCOUNT_INFO: {
			data.moveToFirst();
			// Patients = new

			String email = data.getString(data.getColumnIndex(Users.EMAIL));
			Log.v(TAG, email);
			String pno = data.getString(data.getColumnIndex(Users.PHONENUMBER));
			// Log.v(TAG, pno);
			String fname = data.getString(data.getColumnIndex(Users.FIRSTNAME));
			Log.v(TAG, fname);
			String lname = data.getString(data.getColumnIndex(Users.LASTNAME));
			Log.v(TAG, lname);
			if (updateDialog == null || !updateDialog.isShowing())
				updatePopup(email, pno, fname, lname);
			break;
		}

		case Constants.VALIDATE_EMAIL_TOKEN: {
			data.moveToFirst();
			String json = data.getString(data.getColumnIndex(Session.JSON));

			if (json.contains("invalid")) {
				String oops = getString(R.string.oop);
				String msg = getString(R.string.validEmail);
				showInternetAlert(oops, msg);
			} else if (json.contains("true")) {

				String oops = getString(R.string.oop);
				String msg = getString(R.string.alreadyExists);
				showInternetAlert(oops, msg);
				// alert
			} else if (json.contains("false")) {
				callUpdateService(updateEmail, updatePhone, updateFname,
						updateLname);
				// updateDialog.dismiss();
			}

		}

		default:
			break;
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void popoverViewWillShow(PopoverView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void popoverViewDidShow(PopoverView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void popoverViewWillDismiss(PopoverView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void popoverViewDidDismiss(PopoverView view) {
		// TODO Auto-generated method stub

	}

	
	public void callUpdateService(String email, String phone, String lname,
			String fname) {

		String userIdFromProvider = getUserIdFromProvider();

		String urlEmail = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + userIdFromProvider
				+ getString(R.string.email) + email;
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlEmail);

		String urlPhno = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + userIdFromProvider
				+ getString(R.string.phonenumber) + phone;
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlPhno);

		String urlFname = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + userIdFromProvider
				+ getString(R.string.firstname) + fname;
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlFname);

		String urlLname = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + userIdFromProvider
				+ getString(R.string.lastname) + lname;
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.UPDATE_ACCOUNT_TOKEN, urlLname);

		insertUpdateData(email, phone, fname, lname);
		// updateDialog.dismiss();

	}

	private void insertUpdateData(String eAddr, String pno, String fName,
			String lName) {
		this.getContentResolver().update(Users.CONTENT_URI, null, null, null); // values,
																				// where,
																				// selectionArgs);//delete(Users.CONTENT_URI,
																				// null,
																				// null);
		ContentValues insertValues = new ContentValues();
		insertValues.put(Users.EMAIL, eAddr);
		insertValues.put(Users.FIRSTNAME, fName);
		insertValues.put(Users.LASTNAME, lName);
		insertValues.put(Users.PHONENUMBER, pno);
		insertValues.put(Users.PATIENT_ID, getUserIdFromProvider());
		this.getContentResolver().insert(Users.CONTENT_URI, insertValues);
	}

	public boolean isValidEmail(String inputEmail) {
		if (inputEmail == null) {
			return false;
		} else {
			Log.v(TAG, inputEmail);
			return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail)
					.matches();
		}
	}

	public void callValidateService(String email) {

		String urlValidate = getString(R.string.remote_preurl)
				+ getString(R.string.validate_email);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.VALIDATE_EMAIL_TOKEN, urlValidate, email, false,
				"get");

	}

	public void changePassword() {

		passwordDialog = new Dialog(PatientDashboardActivity.this,
				R.style.DialogAnim);
		passwordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		passwordDialog.setContentView(R.layout.change_password);
		passwordDialog.setCancelable(false);

		CTTextView pwdCancel = (CTTextView) passwordDialog
				.findViewById(R.id.pwdCancel);

		final Button btnSubmit = (Button) passwordDialog
				.findViewById(R.id.btnSubmit);

		final EditText txt_currPwd = (EditText) passwordDialog
				.findViewById(R.id.txt_currPwd);
		final EditText txt_newPwd = (EditText) passwordDialog
				.findViewById(R.id.txt_newPwd);
		final EditText txt_repeatPwd = (EditText) passwordDialog
				.findViewById(R.id.txt_repPwd);

		existingPassword = CTUser.getInstance().getPassword();

		final String title = getString(R.string.error_password_title);
		final String msg = getString(R.string.error_msg_pwd_change);
		final String isEmpty = getString(R.string.is_empty);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentPassword = txt_currPwd.getText().toString();
				newPassword = txt_newPwd.getText().toString();
				repeatPassword = txt_repeatPwd.getText().toString();

				btnSubmit.setClickable(false);
				if ((currentPassword.length() == 0 || newPassword.length() == 0 || repeatPassword
						.length() == 0)) {
					showInternetAlert(getString(R.string.oop), isEmpty);
					btnSubmit.setClickable(true);
				}

				else {

					if (!existingPassword.equalsIgnoreCase(currentPassword)) {
						Log.v(TAG, "Existing pwd:" + existingPassword);
						Log.v(TAG, "Entered pwd:" + currentPassword);
						showInternetAlert(title, msg);
						btnSubmit.setClickable(true);
					} else if (!confirmPasswordValidate(newPassword,
							repeatPassword)) {
						btnSubmit.setClickable(true);
						showInternetAlert(getString(R.string.oop),
								getString(R.string.mismatch));
					} else {
						CTUser.getInstance().setPassword(newPassword);
						postChangedPassword(CTUser.getInstance().getUsername(), currentPassword,
								newPassword);
						// passwordDialog.dismiss();

					}

				}

			}
		});

		pwdCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				passwordDialog.dismiss();

			}
		});
		passwordDialog.show();
		Window window = passwordDialog.getWindow();
		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = findViewById(R.id.container).getWidth() + size.x;
		int height = findViewById(R.id.container).getHeight() + size.y;
		window.setLayout((int) (width / 3.5), (int) (height / 2.25));

	}

	public void postChangedPassword(String username, String oldPassword,
			String newPassword) {
		String urlChangePwd = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/"
				+ getString(R.string.changepassword);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CHANGE_PASSWORD_TOKEN, urlChangePwd, username,
				oldPassword, newPassword);
	}

	public boolean confirmPasswordValidate(String password,
			String confirmPassword) {
		boolean pstatus = false;

		if (password.equalsIgnoreCase(confirmPassword)) {
			pstatus = true;

		}
		Log.v(TAG, "Password validate result:" + pstatus);
		return pstatus;
	}

	private void showAlert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				PatientDashboardActivity.this);

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setInverseBackgroundForced(true)
				.setPositiveButton("Okay",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	@Override
	protected void selectAndRefreshPatient(PatientModel selectedPatient) {
		// TODO Auto-generated method stub
		
	}

}
