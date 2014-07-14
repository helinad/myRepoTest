package com.constant_therapy.constantTherapy;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentValues;
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
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.constant_therapy.animation.ActivitySwitcher;
import com.constant_therapy.charts.ChartView;
import com.constant_therapy.charts.MultiChartView;
import com.constant_therapy.charts.PieChart;
import com.constant_therapy.charts.PieSlice;
import com.constant_therapy.dashboard.Compliance;
import com.constant_therapy.dashboard.Dashboard;
import com.constant_therapy.dashboard.LatestTaskTypeResults;
import com.constant_therapy.dashboard.Scores;
import com.constant_therapy.dashboard.SelectionList;
import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.popup.AlertPopup;
import com.constant_therapy.popup.HoverPopup;
import com.constant_therapy.popup.PopoverView;
import com.constant_therapy.popup.PopoverView.PopoverViewDelegate;
import com.constant_therapy.provider.TherapyContract.ComplianceList;
import com.constant_therapy.provider.TherapyContract.Dashboards;
import com.constant_therapy.provider.TherapyContract.LatestTypeResultsList;
import com.constant_therapy.provider.TherapyContract.ScoresList;
import com.constant_therapy.provider.TherapyContract.Session;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.ComplianceAdapter;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.DialogListAdapter;
import com.constant_therapy.util.Helper;
import com.constant_therapy.util.MiddleRowAdapter;
import com.constant_therapy.util.MonthAdapter;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.util.PatientModel;
import com.constant_therapy.widget.CTTextView;
import com.constant_therapy.widget.MySwitch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClinicianActivity extends PatientSelectorActivity implements
		MyResultReceiver.Receiver, PopoverViewDelegate, View.OnClickListener,
		LoaderManager.LoaderCallbacks<Cursor> {

	private static final String LOG_TAG = "ClinicianActivity";
	
	public Handler handler;

	MediaPlayer mActivitySwitch;

	Animator anim1, anim2, anim3;
	public static ArrayList<View> combinedChartObjList = new ArrayList<View>();
	public static View accuracyView, latencyView;
	Dashboard dashBoard, latestResult;
	PatientModel patientModel;

	Dialog dialog, dialogTemp;

	MultiChartView overlay;
	ChartView chartView;
	GridView calendarView;
	PopoverView toolTip;
	View view, pView;
	PopupWindow popup;
	HoverPopup hoverPop;

	MiddleRowAdapter listAdapter;
	MonthAdapter monthAdapter;
	ComplianceAdapter complianceAdapter1, complianceAdapter2,
			complianceAdapter3, complianceAdapter4, complianceAdapter5,
			complianceAdapter6;
	ListView middleList;

	View layoutOverlay;
	RelativeLayout combined;
	RelativeLayout rlPatient;
	RelativeLayout rlMiddletext;
	LinearLayout setting, clhelp;
	LinearLayout seemore;
	RelativeLayout accuracyChart;
	RelativeLayout latencyChart;
	RelativeLayout rlDate;
	LinearLayout btnRight;
	LinearLayout btnLeft;
	LinearLayout llNote;

	ImageView imgBack;
	ImageView imgCTIcon;
	ImageView imgSetting;
	ImageView imgPatient;

	PieChart pg;
	Point p;

	Button btnTasks;
	Button btnPerformance;

	CTTextView tvItem, tvComp;
	CTTextView tvPercent;
	CTTextView tvUser;
	CTTextView tvNoData;
	CTTextView tvPatientName;
	CTTextView tvPatient;
	CTTextView tvMiddleText;
	CTTextView tvNodata1, tvNodata2;
	CTTextView tvMonth;
	CTTextView tvRange;
	CTTextView tvHome;
	CTTextView tvTask;
	CTTextView tvSec;;
	CTTextView tvMins;
	CTTextView tvDate;
	CTTextView tvMonth1;
	CTTextView tvMonth2;
	CTTextView tvMonth3;
	CTTextView tvMonth4;
	CTTextView tvMonth5;
	CTTextView tvMonth6;
	CTTextView tvTitle;

	Button btnTask;
	Button btnDuration;

	GridView calendar1;
	GridView calendar2;
	GridView calendar3;
	GridView calendar4;
	GridView calendar5;
	GridView calendar6;

	ImageView pb1, pb2, pb3;
	ImageView mArrowBottom;
	ProgressBar pbAccuracy, pg1, pg2;

	MediaPlayer mPlayer;

	int countdownNumber;

	String userId;

	static String systemTaskname;

	static int gridPosition = 0;
	int[] location = new int[2];

	boolean rangeChange = false, rangeClear = false;
	static Boolean isOnorOff = true;

	static ArrayList<Compliance> calendarList = new ArrayList<Compliance>();
	static List<Scores> scoresList = new ArrayList<Scores>();
	static List<Compliance> complianceList = new ArrayList<Compliance>();
	int updateCount = 0;
	Compliance selectedCalendarItem = null;
	static Calendar minRange = Calendar.getInstance();
	static Calendar maxRange = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

		setContentView(R.layout.clinician_dashboard);
		Log.v(LOG_TAG, "STARTS");
		pg = (PieChart) findViewById(R.id.chart);

		tvItem = (CTTextView) findViewById(R.id.txtCompletedItem);
		tvPercent = (CTTextView) findViewById(R.id.tvPercent);
		tvUser = (CTTextView) findViewById(R.id.tvUsername);
		tvComp = (CTTextView) findViewById(R.id.textView7);
		tvNoData = (CTTextView) findViewById(R.id.no_data);
		tvTitle = (CTTextView) findViewById(R.id.tvTitle);

		middleList = (ListView) findViewById(R.id.listView1);

		tvPatientName = (CTTextView) findViewById(R.id.tvPatient);
		tvMiddleText = (CTTextView) findViewById(R.id.tvMiddletext);
		tvMonth = (CTTextView) findViewById(R.id.tvMonth);

		imgCTIcon = (ImageView) findViewById(R.id.ctIcon);
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
		imgPatient = (ImageView) findViewById(R.id.imgPatient);

		pb1 = (ImageView) findViewById(R.id.progressBar1);
		pb2 = (ImageView) findViewById(R.id.progressBar2);
		pb3 = (ImageView) findViewById(R.id.progressBar3);

		btnTasks = (Button) findViewById(R.id.btnTasks);
		btnPerformance = (Button) findViewById(R.id.btnPerformance);

		combined = (RelativeLayout) findViewById(R.id.combinedChart);
		rlMiddletext = (RelativeLayout) findViewById(R.id.rlMiddletext);
		rlPatient = (RelativeLayout) findViewById(R.id.rlPatient);
		setting = (LinearLayout) findViewById(R.id.linearLayout12);
		seemore = (LinearLayout) findViewById(R.id.llSeemore);
		llNote = (LinearLayout) findViewById(R.id.llNote);
		clhelp = (LinearLayout) findViewById(R.id.linearLayout2);

		layoutOverlay = (View) findViewById(R.id.view);

		calendarView = (GridView) findViewById(R.id.calendar);

		tvItem.setVisibility(View.INVISIBLE);
		tvComp.setVisibility(View.INVISIBLE);

		registerReceiver();

		rlMiddletext.setOnClickListener(this);
		rlPatient.setOnClickListener(this);
		setting.setOnClickListener(this);
		seemore.setOnClickListener(this);
		btnTasks.setOnClickListener(this);
		imgCTIcon.setOnClickListener(this);
		clhelp.setOnClickListener(this);
		mActivitySwitch = MediaPlayer.create(ClinicianActivity.this,
				R.raw.buttondoubleclick);

		isOnorOff = Helper.isRight(ClinicianActivity.this);
		
		middleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				LatestTaskTypeResults item = listAdapter.getItem(position);
				if (dialog == null || !dialog.isShowing())
					showChartView(item);

			}
		});

		calendarView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				GridView parent = (GridView) v;

				int x = (int) event.getX();
				int y = (int) event.getY();

				int position = parent.pointToPosition(x, y);
				Log.v("item", "" + position);

				// ClinicianActivity.this.mDetector.onTouchEvent(event);
				gridPosition = position;

				pView = parent.getChildAt(position);

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.v("actiondown", "");
					System.out.print("actiondown");
					gridPosition = position;
					if (position != -1 && position > 6) {

						pView = parent.getChildAt(position);

						showPop(pView);

						updateItems(monthAdapter.getItem(position));
					}
					break;
				case MotionEvent.ACTION_MOVE:
					Log.v("actionmove", "");
					System.out.print("actionmove");
					gridPosition = position;
					if (position != -1 && position > 6) {
						pView = parent.getChildAt(position);
						if(popoverView != null){
							popoverView.setVisibility(View.GONE);
							popoverView.setDrawingCacheEnabled(true);
						}
						showPop(pView);

						/*
						 * p = new Point(); pView.getLocationOnScreen(location);
						 * p.x = location[0]; p.y = location[1]; Log.v("point",
						 * p.x +","+ p.y); int OFFSET_X = popup.getWidth()/2;
						 * int OFFSET_Y = popup.getHeight();
						 * 
						 * popup.showAtLocation(pView, Gravity.NO_GRAVITY, p.x +
						 * OFFSET_X, p.y + OFFSET_Y); popup.update(pView,
						 * popup.getWidth(), popup.getHeight());
						 */
						updateItems(monthAdapter.getItem(position));
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.v("actionup", "");
					System.out.print("actionup");
					/*
					 * if(popup != null) popup.dismiss();
					 */
					//popoverView.dissmissPopover(false);

					break;

				case MotionEvent.ACTION_CANCEL:
					popoverView.dismissPopover(false);
					break;
				default:
					break;
				}

				return true;

			}
		});

		// expect username and user ID to be already set in the CTUser

		if (CTUser.getInstance().isUserTypePatient()) {
			
			// patient login
			initPatient();
			
		} else {
			
			// clinician login
			
			if (CTUser.getInstance().hasSelectedCurrentUser()) {
				
				// the fact that this CTUser has a selected current user means we already have
				// a patient list - CTUser guarantees that ... so we don't have to refresh
				// the patient list from the web service or the provider
				
				// show currently selected user in settings (pulldown will be set up separately)
				tvPatientName.setText(CTUser.getInstance().getCurrentPatientUsername());
				
			} else {

				// are there patients waiting for us in the provider?
				CTUser.getInstance().refreshPatientsFromProvider(this);

				if (false == CTUser.getInstance().hasPatients()) {
					
					// no patients for this clinician ... assume we just logged in and have
					// not fetched them from the web service yet ...
					// will result in unnecessary fetch from web service in the case where
					// the clinician has no patients e.g. is a new clinician ... not sure how we 
					// can avoid this other than by somehow knowing whether someone else has 
					// already initialized the CTUser patient list 

					if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {

						// note that calling this will NOT lead to updates for anything but 
						// the patient list pulldown and picker dialog, so the only point in 
						// calling this is if you are then intending to show a modal dialog 
						// to pick a patient ... and that is indeed what the result receiver
						// eventually does
						getPatientListFromWebService();

					} else {
						
						// no patients, no internet ... bummer
						showInternetAlert("Oops!", getString(R.string.no_internet));

					}
				}
			}
		}
	}
	
	@Override
	protected void onResume() {

		super.onResume();
		Log.v(LOG_TAG, "OnResume");
		
		
		// Did a server response to a patient list refresh come in while we were asleep?
		if (CTUser.getInstance().isUserTypeClinician()) 
			CTUser.getInstance().refreshPatientsFromProvider(this);

		setUpDisplay();
	}
	
	/** populate the display with patient lists, items, etc. etc.
	 * ... assumes that CTUser is all set up with currently logged
	 * in user and with up-to-date list of patients if any */
	private void setUpDisplay() {
		
		if (CTUser.getInstance().isUserTypeClinician()) {
			
			// logged in as clinician
			
			if (CTUser.getInstance().hasSelectedCurrentUser()) {
				
				// we have patients, and we have one selected - just show it
				btnTasks.setBackground(getResources().getDrawable(
						R.drawable.tasks_tabs_brw));
				refreshPatientDisplay();
				
			} else {
				
				// no currently selected patient ... show the patient list dialog,
				// and then they will add/choose
				showPatientPickerPopup();
				
			}
		} else {
			
			// logged in as patient
			getPatientDashboard();
			
		}

	}


	private void showPop(View v) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.tooltip, true);

		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		popoverView.setContentSizeForViewInPopover(wm, 5.5f, 5.0f);
		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionDown, true);

		tvHome = (CTTextView) popoverView.findViewById(R.id.tvHome);
		tvTask = (CTTextView) popoverView.findViewById(R.id.tvTask);
		tvMins = (CTTextView) popoverView.findViewById(R.id.tvMins);

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

	private void updateItems(Compliance list) {
		if (list != null) {
			if (list.getDate() != 0L) {
				if (list.getInClinic()) {
					tvHome.setText("InClinic: "
							+ getDateFromTimeStamp(list.getDate() * 1000));
				} else {
					tvHome.setText("Home: "
							+ getDateFromTimeStamp(list.getDate() * 1000));
				}

				tvTask.setText(list.getCompletedTaskCount()
						+ " tasks completed");
				tvMins.setText(list.getCompletedTaskCount() + " min of therapy");
			} else {
				tvHome.setText("Home: " + list.getDuration());
				tvTask.setText("0 tasks completed");
				tvMins.setText("0 min of therapy");
			}
		}
	}

	
	/** sets the currently selected patient in the user model, then 
	 * calls the patient dashboard service to get the info about the currently
	 * selected patient - used when the user is logged in as a clinician and
	 * has e.g. used the pulldown to switch patients */
	@Override
	protected void selectAndRefreshPatient(PatientModel selectedPatient) {
		
		CTUser.getInstance().setCurrentPatient(selectedPatient.getUsername());
		refreshPatientDisplay();
	}


	/** refreshes the user interface by updating the displayed patient name
	 * (only relevant for clinician logged in scenario) and then calling
	 * the patient dashboard service to get the info about the currently
	 * selected patient - used when the user is logged in as a clinician and
	 * has e.g. used the pulldown to switch patients */
	private void refreshPatientDisplay() {
		
		tvPatientName.setText(CTUser.getInstance().getCurrentPatientUsername());
		getPatientDashboard();
	}

/** gets the info for the currently selected (or logged in) patient, from the web service */
	private void getPatientDashboard() {

		if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {
			String url = getString(R.string.remote_preurl)
					+ getString(R.string.remote_patient) + "/"
					+ CTUser.getInstance().getPatientIdForDisplay() + 
					getString(R.string.remote_dashboard);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.DASHBOARD_TOKEN, url);

		} else {
			showInternetAlert("Oops!", getString(R.string.no_internet));

		}

	}

	/*
	 * Method for calling webservice to get the middle row details.
	 */

	private void callMiddleRowWebservice(PatientModel selectedValue) {
		String patientId = (String) selectedValue.getPatientId();
		String value = (String) selectedValue.getUsername();
		String key = (String) selectedValue.getKey();
		tvMiddleText.setText(value);
		if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {
			String url = getString(R.string.remote_preurl)
					+ getString(R.string.remote_patient) + "/" + patientId
					+ getString(R.string.remote_dashboard_summary) + key;
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.CLINICIAN_MIDDLE_ROW_TOKEN, url);

		} else {
			showInternetAlert("Oops!", getString(R.string.no_internet));

		}

	}

	/*
	 * Method for calling webservice to get the dashboard details of selected
	 * patient.
	 */

	private void callCombinedChartWebservice(LatestTaskTypeResults item) {

		String taskType = item.getTaskTypeSytemname();
		int taskLevel = item.getTaskLevel();

		if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {
			String url = getString(R.string.remote_preurl)
					+ getString(R.string.remote_patient) + "/"
					+ CTUser.getInstance().getPatientIdForDisplay() + 
					getString(R.string.remote_compilance);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.CLINICIAN_PLOT_LATENCY_TOKEN, url, taskType, ""
							+ taskLevel);
			String url1 = getString(R.string.remote_preurl)
					+ getString(R.string.remote_patient) + "/"
					+ CTUser.getInstance().getPatientIdForDisplay() + 
					getString(R.string.remote_lineplot);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.CLINICIAN_PLOT_ACCURACY_TOKEN, url1, taskType, ""
							+ taskLevel);

		} else {
			showInternetAlert("Oops!", getString(R.string.no_internet));

		}

	}

	private void displayCalendar(Dashboard dashboardItem) {
		if (dashboardItem != null)
			calendarList = dashboardItem.getCompliance();
		if (calendarList.size() != 0) {
			ArrayList<String> date = new ArrayList<String>();

			for (int i = 0; i < calendarList.size(); i++) {
				date.add(getDateFromTimeStamp(calendarList.get(i).getDate() * 1000));
			}

			Collections.sort(date, new compareDate());

			int month = getStringToDate(date.get(date.size() - 1)).get(
					Calendar.MONTH);
			int year = getStringToDate(date.get(date.size() - 1)).get(
					Calendar.YEAR);
			setMonth(date.get(0), date.get(date.size() - 1));
			// set maximum range in calendar
			maxRange.set(Calendar.MONTH, month);
			maxRange.set(Calendar.YEAR, year);
			// set minimum range in calendar
			minRange.set(Calendar.MONTH,
					getStringToDate(date.get(0)).get(Calendar.MONTH));
			minRange.set(Calendar.YEAR,
					getStringToDate(date.get(0)).get(Calendar.YEAR));

			monthAdapter = new MonthAdapter(getApplicationContext(), month,
					year, getResources().getDisplayMetrics());
			calendarView.setAdapter(monthAdapter);
			handler = new Handler();
			handler.post(calendarUpdater);
		} else {
			Calendar c = Calendar.getInstance();

			monthAdapter = new MonthAdapter(getApplicationContext(),
					c.get(Calendar.MONTH), c.get(Calendar.YEAR), getResources()
							.getDisplayMetrics());
			calendarView.setAdapter(monthAdapter);
		}
		Log.v(LOG_TAG, "SHOWED CALENDAR");
	}

	private void setMonth(String date1, String date2) {

		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final DateFormat df1 = new SimpleDateFormat("MMM yyyy");
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(date1));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String month = df1.format(c.getTime());
		final Calendar c1 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date2));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String month2 = df1.format(c1.getTime());
		if (month.equalsIgnoreCase(month2)) {
			c1.add(Calendar.MONTH, -1);
			tvMonth.setText(df1.format(c1.getTime()) + "-" + month);
		} else {
			tvMonth.setText(month + "-" + month2);
		}

	}

	private Calendar getStringToDate(String calendar) {

		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(calendar));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return c;

	}

	/*
	 * Method for showing doughnut chart.
	 */

	@SuppressWarnings("deprecation")
	private void displayDonutChart(Dashboard chartItem) {
		float thickness = (pg.getHeight() / 3) - 5;
		Log.v("thickness", "" + thickness);
		ShapeDrawable circle = new ShapeDrawable(new OvalShape());
		circle.setIntrinsicHeight((int) (Math.round(thickness
				+ Constants.POPUP_TASKCOUNT)));
		circle.setIntrinsicWidth((int) (Math.round(thickness
				+ Constants.POPUP_TASKCOUNT)));
		circle.getPaint().setColor(Color.WHITE);
		circle.setBounds(1, 1, 1, 1);
		// setBackground(Drawable) would work fine but only available in API level 16
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

		Log.v(LOG_TAG, "SHOWED DONUT");
	}

	/*
	 * Method for showing middle row bar list
	 */

	private void displayListView(Dashboard dashboardItems) {
		ArrayList<LatestTaskTypeResults> latestResults = new ArrayList<LatestTaskTypeResults>();
		if (dashboardItems != null)
			latestResults = dashboardItems.getLatestTaskTypeResults();
		if (latestResults.size() != 0) {

			listAdapter = new MiddleRowAdapter(ClinicianActivity.this,
					latestResults);
			listAdapter.notifyDataSetChanged();
			middleList.setAdapter(listAdapter);
		} else {
			tvMiddleText.setText("Please select a date range");

			middleList.setAdapter(null);
		}
		Log.v(LOG_TAG, "SHOWED LISTVIEW");
	}

	private void displayMiddleTableView(Dashboard dashboardItems) {
		ArrayList<LatestTaskTypeResults> latestResults = new ArrayList<LatestTaskTypeResults>();
		latestResults = dashboardItems.getTaskResults();
		if (latestResults.size() != 0) {

			listAdapter = new MiddleRowAdapter(ClinicianActivity.this,
					latestResults);
			listAdapter.notifyDataSetChanged();
			middleList.setAdapter(listAdapter);
		} else {
			tvMiddleText.setText("Please select a date range");

			middleList.setAdapter(null);
		}
		Log.v(LOG_TAG, "SHOWED LISTVIEW");
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

			overlay = new MultiChartView(getApplicationContext(), lineList,
					barList, XAxis, XaisGap);

			combined.addView(overlay);
			combined.bringChildToFront(pb3);

		}
		Log.v(LOG_TAG, "SHOWED CHART");
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

	@SuppressWarnings({ "deprecation", "static-access" })
	private void showTooltip(View v, Compliance list) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		toolTip = new PopoverView(this, R.layout.tooltip, true);

		// popoverView.setContentSizeForViewInPopover(new Point(550, 300));
		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = toolTip.getWidth() + size.x;
		int height = toolTip.getHeight() + size.y;

		toolTip.setContentSizeForViewInPopover(new Point(width / 6, height / 6));

		toolTip.setDelegate(this);
		toolTip.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionDown, true);

		CTTextView tvHome = (CTTextView) toolTip.findViewById(R.id.tvHome);
		CTTextView tvTask = (CTTextView) toolTip.findViewById(R.id.tvTask);
		CTTextView tvMins = (CTTextView) toolTip.findViewById(R.id.tvMins);
		if (list.getDate() != 0L) {
			if (list.getInClinic()) {
				tvHome.setText("InClinic: "
						+ getDateFromTimeStamp(list.getDate() * 1000));
			} else {
				tvHome.setText("Home: "
						+ getDateFromTimeStamp(list.getDate() * 1000));
			}

			tvTask.setText(list.getCompletedTaskCount() + " tasks completed");
			tvMins.setText(list.getCompletedTaskCount() + " min of therapy");
		} else {
			tvHome.setText("Home: " + list.getDuration());

			tvTask.setText("0 tasks completed");
			tvMins.setText("0 min of therapy");
		}

	}

	private void showAlert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				ClinicianActivity.this);

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

	/*
	 * Method for showing middle row top list.
	 */

	private void showSelectionDropDown(ArrayList<SelectionList> itemList, View v) {
		ArrayList<PatientModel> selectionList = new ArrayList<PatientModel>();
		String selectedPatientId = null;
		// now we get to see that PatientModel is not just a model of a patient - it serves
		// confusing double duty - it serves here as the model of a patient plus a key that
		// indicates a schedule ID or session ID ... someday we will refactor this
		if(CTUser.getInstance().isUserTypeClinician())
			 selectedPatientId = CTUser.getInstance().getCurrentPatient().getPatientId();
		else
			selectedPatientId = String.valueOf(CTUser.getInstance().getUserId());
		
		for (int i = 0; i < itemList.size(); i++) {
			PatientModel model = new PatientModel(itemList.get(i).getValue(),
					null, selectedPatientId, itemList.get(i).getKey(), null);
			selectionList.add(model);
		}
		int listSize = 0;
		if (selectionList.size() < 10) {
			listSize = 10 - selectionList.size();
		}
		for (int i = 0; i < listSize; i++) {
			PatientModel model = new PatientModel("", null, "", "", "");
			selectionList.add(model);
		}
		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		PopoverView popoverView = new PopoverView(this, R.layout.popup_list,
				false);

		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		popoverView.setContentSizeForViewInPopover(wm, 2.0f, 2.0f);

		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrame(v), PopoverView.PopoverArrowDirectionLeft,
				true);

		final ListView list = (ListView) popoverView
				.findViewById(R.id.listView1);
		dialogListAdapter = new DialogListAdapter(ClinicianActivity.this,
				selectionList, false);
		list.setAdapter(dialogListAdapter);

		if (itemList.size() > 0) {
			list.setSelection(0);
			list.setItemChecked(0, true);
		}
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long arg3) {

				// did they select one of the empty entries?
				if (((PatientModel)dialogListAdapter.getItem(pos)).getUsername().length() > 0) {
					
					boolean checked = list.isItemChecked(pos);
					ImageView imTick = (ImageView) v
							.findViewById(R.id.imageTick);

					if (checked) {
						if (imTick.getVisibility() == View.INVISIBLE) {
							// middleList.setAdapter(null);
							callMiddleRowWebservice(dialogListAdapter.getItem(pos));
						}
						list.setItemChecked(pos, true);
						imTick.setVisibility(View.VISIBLE);
					} else {
						list.setItemChecked(pos, false);
						imTick.setVisibility(View.INVISIBLE);
					}

				} 
				
				// ESR : why was this here - how is this case even possible?
				//else {
				//	list.setItemChecked(selectedPosition, true);
				//}
			}
		});

	}

	private void showSettingDropDown(View v) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.setting, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		popoverView.setContentSizeForViewInPopover(wm, 3.0f, 1.75f);

		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionUp, true);

		LinearLayout logout = (LinearLayout) popoverView
				.findViewById(R.id.llLogout);
		MySwitch mySwitch = (MySwitch) popoverView.findViewById(R.id.switch1);
		mySwitch.isOnorOff(isOnorOff);
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// shiftLayout(isChecked);
				isOnorOff = isChecked;
				Helper.setIsRight(ClinicianActivity.this, isChecked);
			}
		});
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showLogOut();
			}
		});

		LinearLayout llUpdate = (LinearLayout) popoverView
				.findViewById(R.id.llUpdate);
		llUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updatePopup(CTUser.getInstance().getEmail(), CTUser.getInstance().getPhoneNumber(), CTUser.getInstance().getFirstName(), CTUser.getInstance().getLastName());
			}
		});

	}

	private void showPatientSettingDropDown(View v) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.generalsetting, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		popoverView.setContentSizeForViewInPopover(wm, 3.0f, 1.75f);

		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionUp, true);

		LinearLayout llUpdate = (LinearLayout) popoverView
				.findViewById(R.id.llUpdate);
		
		LinearLayout llMode = (LinearLayout) popoverView.findViewById(R.id.llMode);

		MySwitch mySwitch = (MySwitch) popoverView.findViewById(R.id.switch1);
		mySwitch.isOnorOff(isOnorOff);
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// shiftLayout(isChecked);
				isOnorOff = isChecked;
				Helper.setIsRight(ClinicianActivity.this, isChecked);

			}
		});

		llMode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dialog != null)
					dialog.dismiss();
				animatedPatientActivity();
			}
		});
		
		llUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updatePopup(CTUser.getInstance().getEmail(), CTUser.getInstance().getPhoneNumber(), CTUser.getInstance().getFirstName(), CTUser.getInstance().getLastName());
			}
		});

	}

	private void showCalendarPopup(View v, RelativeLayout rootView) {

		// RelativeLayout rootView = (RelativeLayout)
		// findViewById(R.id.chartcontainer);

		PopoverView popoverView = new PopoverView(this, R.layout.caledar_popup,
				false);

		WindowManager wm = (WindowManager) this
				.getSystemService(ClinicianActivity.WINDOW_SERVICE);
		popoverView.setContentSizeForViewInPopover(wm, 1.5f, 1.5f);

		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionUp, true);

		CTTextView tvDate = (CTTextView) popoverView.findViewById(R.id.tvDate);
		final CTTextView tvMonth1 = (CTTextView) popoverView
				.findViewById(R.id.tvMonth1);
		final CTTextView tvMonth2 = (CTTextView) popoverView
				.findViewById(R.id.tvMonth2);
		final CTTextView tvClear = (CTTextView) popoverView
				.findViewById(R.id.tvClear);

		Button btnLeft = (Button) popoverView.findViewById(R.id.btnLeftArrow);
		Button btnRight = (Button) popoverView.findViewById(R.id.btnRightarrow);

		final GridView calendar1 = (GridView) popoverView
				.findViewById(R.id.calendar1);
		final GridView calendar2 = (GridView) popoverView
				.findViewById(R.id.calendar2);
		final Calendar c = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");

		for (int i = 0; i < 2; i++) {

			if (i == 0) {
				tvMonth2.setText(sdf.format(c.getTime()));
				complianceAdapter2 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar2.setAdapter(complianceAdapter2);
				complianceAdapter2.setItems(calendarList);
				complianceAdapter2.notifyDataSetChanged();
			} else if (i == 1) {
				c.add(Calendar.MONTH, -1);

				tvMonth1.setText(sdf.format(c.getTime()));
				complianceAdapter1 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar1.setAdapter(complianceAdapter1);
				complianceAdapter1.setItems(calendarList);
				complianceAdapter1.notifyDataSetChanged();
			}
			btnLeft.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (c.get(Calendar.MONTH) >= minRange.get(Calendar.MONTH)
							&& c.get(Calendar.YEAR) >= minRange
									.get(Calendar.YEAR)) {
						for (int i = 0; i < 2; i++) {

							if (i == 0) {
								tvMonth2.setText(sdf.format(c.getTime()));
								complianceAdapter2 = new ComplianceAdapter(
										getApplicationContext(), c
												.get(Calendar.MONTH), c
												.get(Calendar.YEAR),
										getResources().getDisplayMetrics());
								calendar2.setAdapter(complianceAdapter2);
								complianceAdapter2.setItems(calendarList);
								complianceAdapter2.notifyDataSetChanged();
							} else if (i == 1) {
								c.add(Calendar.MONTH, -1);

								tvMonth1.setText(sdf.format(c.getTime()));
								complianceAdapter1 = new ComplianceAdapter(
										getApplicationContext(), c
												.get(Calendar.MONTH), c
												.get(Calendar.YEAR),
										getResources().getDisplayMetrics());
								calendar1.setAdapter(complianceAdapter1);
								complianceAdapter1.setItems(calendarList);
								complianceAdapter1.notifyDataSetChanged();
							}
						}

					}
				}
			});

			btnRight.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
					if (c.get(Calendar.MONTH) <= maxRange.get(Calendar.MONTH)
							&& c.get(Calendar.YEAR) <= maxRange
									.get(Calendar.YEAR)) {
						for (int i = 0; i < 2; i++) {

							if (i == 0) {
								tvMonth2.setText(sdf.format(c.getTime()));
								complianceAdapter2 = new ComplianceAdapter(
										getApplicationContext(), c
												.get(Calendar.MONTH), c
												.get(Calendar.YEAR),
										getResources().getDisplayMetrics());
								calendar2.setAdapter(complianceAdapter2);
								complianceAdapter2.setItems(calendarList);
								complianceAdapter2.notifyDataSetChanged();
							} else if (i == 1) {
								c.add(Calendar.MONTH, +1);

								tvMonth1.setText(sdf.format(c.getTime()));
								complianceAdapter1 = new ComplianceAdapter(
										getApplicationContext(), c
												.get(Calendar.MONTH), c
												.get(Calendar.YEAR),
										getResources().getDisplayMetrics());
								calendar1.setAdapter(complianceAdapter1);
								complianceAdapter1.setItems(calendarList);
								complianceAdapter1.notifyDataSetChanged();
							}
						}
					}
				}
			});

			tvDate.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Calendar c = Calendar.getInstance();
					tvRange.setText(getString(R.string.alltime));
					rangeClear = true;
					rangeChange = false;
					for (int i = 0; i < 2; i++) {

						if (i == 0) {
							tvMonth2.setText(sdf.format(c.getTime()));
							complianceAdapter2 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar2.setAdapter(complianceAdapter2);
							complianceAdapter2.setItems(calendarList);
							complianceAdapter2.notifyDataSetChanged();
						} else if (i == 1) {
							c.add(Calendar.MONTH, -1);

							tvMonth1.setText(sdf.format(c.getTime()));
							complianceAdapter1 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar1.setAdapter(complianceAdapter1);
							complianceAdapter1.setItems(calendarList);
							complianceAdapter1.notifyDataSetChanged();
						}
					}
				}
			});

			tvClear.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Calendar c = Calendar.getInstance();
					tvRange.setText(getString(R.string.alltime));
					rangeClear = true;
					rangeChange = false;
					for (int i = 0; i < 2; i++) {

						if (i == 0) {
							tvMonth2.setText(sdf.format(c.getTime()));
							complianceAdapter2 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar2.setAdapter(complianceAdapter2);
							complianceAdapter2.setItems(calendarList);
							complianceAdapter2.notifyDataSetChanged();
						} else if (i == 1) {
							c.add(Calendar.MONTH, -1);

							tvMonth1.setText(sdf.format(c.getTime()));
							complianceAdapter1 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar1.setAdapter(complianceAdapter1);
							complianceAdapter1.setItems(calendarList);
							complianceAdapter1.notifyDataSetChanged();
						}
					}
				}
			});

			calendar1.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
			calendar1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,
						long id) { 

					calendar2.clearChoices();
					calendar2.invalidateViews();

					if (calendar1.isItemChecked(pos)) {
						if (pos > 6) {

							rangeChange = true;

							selectedCalendarItem = complianceAdapter1
									.getItem(pos);
							calendar1.setItemChecked(pos, true);
							if (complianceAdapter1.getItem(pos).getDate() != 0L) {
								Long timeStamp = complianceAdapter1
										.getItem(pos).getDate();
								tvRange.setText(getDateFormat(timeStamp * 1000)
										+ "-" + getDateFormat(timeStamp * 1000));
							} else {
								String date = complianceAdapter1.getItem(pos)
										.getDuration();
								tvRange.setText(date + "-" + date);
							}
						}
					} else {
						calendar1.setItemChecked(pos, false);

					}
				}
			});

			calendar2.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
			calendar2.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,
						long id) {

					calendar1.clearChoices();
					calendar1.invalidateViews();
					rangeChange = true;

					if (calendar2.isItemChecked(pos)) {
						if (pos > 6) {

							calendar2.setItemChecked(pos, true);
							selectedCalendarItem = complianceAdapter2
									.getItem(pos);
							if (selectedCalendarItem.getDate() != 0L) {
								Long timeStamp = complianceAdapter2
										.getItem(pos).getDate();
								tvRange.setText(getDateFormat(timeStamp * 1000)
										+ "-" + getDateFormat(timeStamp * 1000));
							} else {
								String date = complianceAdapter2.getItem(pos)
										.getDuration();
								tvRange.setText(date + "-" + date);
							}
						}
					} else {
						calendar2.setItemChecked(pos, false);

					}
				}
			});

		}

	}

	private void LoadAccuracyChart(RelativeLayout layout, List<Scores> chartItem) {

		float thickness = (layout.getHeight() / Constants.CHART_DISTANCE);
		ArrayList<Long> date = new ArrayList<Long>();
		ArrayList<Double> accuracy = new ArrayList<Double>();
		ArrayList<Double> cummulativeAccuracy = new ArrayList<Double>();

		ArrayList<String> XAxis = new ArrayList<String>();
		ArrayList<Integer> lineList = new ArrayList<Integer>();
		ArrayList<Integer> barList = new ArrayList<Integer>();
		int XaisGap = 0;
		if (chartItem.size() != 0) {
			tvNodata1.setVisibility(View.INVISIBLE);
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
				XaisGap = (int) Math.ceil(days / 7.0);
			}

			chartView = new ChartView(getApplicationContext(), lineList,
					barList, XAxis, XaisGap, true);
			layout.addView(chartView);
			combinedChartObjList.add(layout);
			accuracyView = layout;
			
		} else {
			// accuracyChart.removeAllViews();
			tvNodata1.setVisibility(View.VISIBLE);

		}

	}

	private void LoadLatencyChart(RelativeLayout layout,
			List<Compliance> chartItem) {

		float thickness = (layout.getHeight() / Constants.CHART_DISTANCE);

		ArrayList<Long> date = new ArrayList<Long>();
		ArrayList<Integer> duration = new ArrayList<Integer>();
		ArrayList<Integer> completedTask = new ArrayList<Integer>();

		ArrayList<String> XAxis = new ArrayList<String>();
		ArrayList<Integer> lineList = new ArrayList<Integer>();
		ArrayList<Integer> barList = new ArrayList<Integer>();
		int XaisGap = 0;

		if (chartItem.size() != 0) {
			tvNodata2.setVisibility(View.INVISIBLE);
			for (int i = 0; i < chartItem.size(); i++) {
				date.add(chartItem.get(i).getDate());
				duration.add(Integer.parseInt(chartItem.get(i).getDuration()));
				completedTask.add(chartItem.get(i).getCompletedTaskCount());
			}

			for (int i = 0; i < date.size(); i++) {

				// lineList.add(completedTask.get(i));
				lineList.add(duration.get(i));
				barList.add(duration.get(i));
				XAxis.add(getDateFromTimeStamp(date.get(i) * 1000));
			}

			int days = getDays(getDateFormat(date.get(0)),
					getDateFormat(date.get(date.size() - 1)));

			if (days <= 0) {
				XaisGap = 1;
			} else {
				XaisGap = (int) Math.ceil(days / 7.0);
			}

			chartView = new ChartView(getApplicationContext(), lineList,
					barList, XAxis, XaisGap, false);
			layout.addView(chartView);
			latencyView = layout;
			combinedChartObjList.add(layout);
		} else {
			// latencyChart.removeAllViews();
			tvNodata2.setVisibility(View.VISIBLE);

		}
	}

	/*
	 * Method for showing initial alert of patient list.
	 */

	private void showPatientPickerPopup() {

		List<PatientModel> patientList = CTUser.getInstance().getPatientList();
		final Dialog dialog = new Dialog(ClinicianActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_list);
		dialog.setCancelable(false);
		final ListView list = (ListView) dialog.findViewById(R.id.listView1);

		dialogListAdapter = new DialogListAdapter(ClinicianActivity.this,
				patientList, true);
		list.setAdapter(dialogListAdapter);

		// default to load up zeroth patient e.g. if there is only one patient and they don't change the selectio
		if (0 < patientList.size())
			selectAndRefreshPatient(dialogListAdapter.getItem(0));

		Button dialogButton = (Button) dialog.findViewById(R.id.button1);

		dialogButton.setEnabled(false);
		
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CTUser.getInstance().setCurrentPatient(dialogListAdapter.getSelectedItem().getUsername());
				dialog.dismiss();
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long arg3) {

				boolean checked = list.isItemChecked(pos);
				ImageView imTick = (ImageView) v.findViewById(R.id.imageTick);
				if (checked) {
					
					dialogListAdapter.setSelectedIndex(pos);
					CTUser.getInstance().setCurrentPatient(dialogListAdapter.getSelectedItem().getUsername());
					Button dialogButton = (Button) dialog.findViewById(R.id.button1);
					dialogButton.setEnabled(true);

					if (imTick.getVisibility() == View.INVISIBLE) {
						selectAndRefreshPatient(dialogListAdapter.getSelectedItem());
					}
					list.setItemChecked(pos, true);
					imTick.setVisibility(View.VISIBLE);
				} else {
					list.setItemChecked(pos, false);
					imTick.setVisibility(View.INVISIBLE);
				}

			}
		});

		dialog.show();

	}

	/*
	 * Method for alert dialog of logout.
	 */

	private void showLogOut() {
		final Dialog dialog = new Dialog(ClinicianActivity.this);
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
				animatedStartActivity(false);

			}
		});

		dialog.show();

	}

	/** show an alert about internet availability, and when OK is clicked
	 * to acknowledge it, refresh some stuff (if we can) */
	@Override
	protected void showInternetAlert(String title, String message) {
		dialog = new Dialog(ClinicianActivity.this);
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

				loadingStop();
				pg.removeSlices();
				displayCombinedChart(dashBoard);
				displayDonutChart(dashBoard);
				displayListView(dashBoard);
				displayCalendar(dashBoard);

			}
		});

		dialog.show();

	}

	private void loadChartRange(Compliance item) {
		String mSelectionScoreClause = null, mSelectionCompilanceClause = null;
		if (item.getDate() != 0L) {
			Long timeStamp = item.getDate();
			mSelectionScoreClause = ScoresList.DATE + " <=" + timeStamp;
			mSelectionCompilanceClause = ComplianceList.DATE + " <="
					+ timeStamp;
		} else {
			String date = item.getDuration();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(date));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			mSelectionScoreClause = ScoresList.DATE + " <="
					+ (c.getTimeInMillis() / 1000);
			mSelectionCompilanceClause = ComplianceList.DATE + " <="
					+ (c.getTimeInMillis() / 1000);
		}
		LoadAccuracyChart(accuracyChart,
				retriveAccuracyChartUpdate(mSelectionScoreClause));
		LoadLatencyChart(latencyChart,
				retriveLatencyUpdates(mSelectionCompilanceClause));
		rangeChange = false;

	}

	private List<Scores> retriveAccuracyChartUpdate(String clause) {

		List<Scores> list = new ArrayList<Scores>();
		Scores model;
		Cursor cursor = this.getContentResolver().query(ScoresList.CONTENT_URI,
				ScoreQuery.PROJECTION, clause, null, ScoresList.DEFAULT_SORT);

		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					Long date = cursor.getLong(cursor
							.getColumnIndex(ScoresList.DATE));
					Double accuracy = cursor.getDouble(cursor
							.getColumnIndex(ScoresList.ACCURACY));
					Double cumulativeAccuracy = cursor.getDouble(cursor
							.getColumnIndex(ScoresList.CUMULATIVEACCURACY));

					model = new Scores(date, accuracy, cumulativeAccuracy);
					list.add(model);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();

		return list;

	}

	/*
	 * Method for retrieving combined chart values from database;
	 */

	private List<Compliance> retriveLatencyUpdates(String clause) {

		List<Compliance> list = new ArrayList<Compliance>();
		Compliance model;

		Cursor cursor = this.getContentResolver().query(
				ComplianceList.CONTENT_URI, ComplianceQuery.PROJECTION, clause,
				null, ComplianceList.DEFAULT_SORT);

		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					Long date = cursor.getLong(cursor
							.getColumnIndex(ComplianceList.DATE));
					String duration = cursor.getString(cursor
							.getColumnIndex(ComplianceList.DURATION));
					int count = cursor.getInt(cursor
							.getColumnIndex(ComplianceList.COMPLETEDTASKCOUNT));
					boolean inclinic = Boolean.parseBoolean(cursor
							.getString(cursor
									.getColumnIndex(ComplianceList.INCLINIC)));
					model = new Compliance(date, duration, count, inclinic);
					list.add(model);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();

		return list;

	}

	private void showLargeComplianceCalendarView() {
		dialog = new Dialog(ClinicianActivity.this, R.style.ActivityAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.large_calendar);
		dialog.setCancelable(true);

		setting = (LinearLayout) dialog.findViewById(R.id.linearLayout12);
		imgBack = (ImageView) dialog.findViewById(R.id.imageIcon);
		tvPatient = (CTTextView) dialog.findViewById(R.id.tvPatient);

		CTTextView tvToday = (CTTextView) dialog.findViewById(R.id.tvDate);
		tvMonth1 = (CTTextView) dialog.findViewById(R.id.tvMonth1);
		tvMonth2 = (CTTextView) dialog.findViewById(R.id.tvMonth2);
		tvMonth3 = (CTTextView) dialog.findViewById(R.id.tvMonth3);
		tvMonth4 = (CTTextView) dialog.findViewById(R.id.tvMonth4);
		tvMonth5 = (CTTextView) dialog.findViewById(R.id.tvMonth5);
		tvMonth6 = (CTTextView) dialog.findViewById(R.id.tvMonth6);

		btnLeft = (LinearLayout) dialog.findViewById(R.id.llLeftArrow);
		btnRight = (LinearLayout) dialog.findViewById(R.id.llRightarrow);
		btnTask = (Button) dialog.findViewById(R.id.btnTask);
		btnDuration = (Button) dialog.findViewById(R.id.btnDuration);

		calendar1 = (GridView) dialog.findViewById(R.id.calendar1);
		calendar2 = (GridView) dialog.findViewById(R.id.calendar2);
		calendar3 = (GridView) dialog.findViewById(R.id.calendar3);
		calendar4 = (GridView) dialog.findViewById(R.id.calendar4);
		calendar5 = (GridView) dialog.findViewById(R.id.calendar5);
		calendar6 = (GridView) dialog.findViewById(R.id.calendar6);

		imgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});

		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CTUser.getInstance().isUserTypePatient())
					showPatientSettingDropDown(imgSetting);
				else
					showSettingDropDown(imgSetting);
			}
		});

		tvPatient.setText(CTUser.getInstance().getPatientUsernameForDisplay());

		final Calendar c = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");

		for (int i = 0; i < 6; i++) {

			if (i == 0) {
				tvMonth6.setText(sdf.format(c.getTime()));
				complianceAdapter6 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar6.setAdapter(complianceAdapter6);

			} else if (i == 1) {
				c.add(Calendar.MONTH, -1);

				tvMonth5.setText(sdf.format(c.getTime()));
				complianceAdapter5 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar5.setAdapter(complianceAdapter5);

			} else if (i == 2) {
				c.add(Calendar.MONTH, -1);

				tvMonth4.setText(sdf.format(c.getTime()));
				complianceAdapter4 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar4.setAdapter(complianceAdapter4);

			} else if (i == 3) {
				c.add(Calendar.MONTH, -1);

				tvMonth3.setText(sdf.format(c.getTime()));
				complianceAdapter3 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar3.setAdapter(complianceAdapter3);

			} else if (i == 4) {
				c.add(Calendar.MONTH, -1);

				tvMonth2.setText(sdf.format(c.getTime()));
				complianceAdapter2 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar2.setAdapter(complianceAdapter2);

			} else if (i == 5) {
				c.add(Calendar.MONTH, -1);

				tvMonth1.setText(sdf.format(c.getTime()));
				complianceAdapter1 = new ComplianceAdapter(
						getApplicationContext(), c.get(Calendar.MONTH),
						c.get(Calendar.YEAR), getResources()
								.getDisplayMetrics());
				calendar1.setAdapter(complianceAdapter1);

			}
		}

		handler = new Handler();
		handler.post(largeCalendarUpdater);
		c.add(Calendar.MONTH, +4);
		btnLeft.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Log.v(TAG, "click: "+c.get(Calendar.MONTH));
				if (c.get(Calendar.MONTH) >= minRange.get(Calendar.MONTH)
						&& c.get(Calendar.YEAR) >= minRange.get(Calendar.YEAR)) {
					int flag = 1;
					for (int i = 0; i < 6; i++) {

						if (i == 0) {
							tvMonth6.setText(sdf.format(c.getTime()));
							complianceAdapter6 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar6.setAdapter(complianceAdapter6);

						} else if (i == 1) {
							c.add(Calendar.MONTH, -flag);

							tvMonth5.setText(sdf.format(c.getTime()));
							complianceAdapter5 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar5.setAdapter(complianceAdapter5);

						} else if (i == 2) {
							c.add(Calendar.MONTH, -flag);

							tvMonth4.setText(sdf.format(c.getTime()));
							complianceAdapter4 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar4.setAdapter(complianceAdapter4);

						} else if (i == 3) {
							c.add(Calendar.MONTH, -flag);

							tvMonth3.setText(sdf.format(c.getTime()));
							complianceAdapter3 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar3.setAdapter(complianceAdapter3);

						} else if (i == 4) {
							c.add(Calendar.MONTH, -flag);

							tvMonth2.setText(sdf.format(c.getTime()));
							complianceAdapter2 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar2.setAdapter(complianceAdapter2);

						} else if (i == 5) {
							c.add(Calendar.MONTH, -flag);

							tvMonth1.setText(sdf.format(c.getTime()));
							complianceAdapter1 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar1.setAdapter(complianceAdapter1);

						}
					}

					c.add(Calendar.MONTH, +4);
					// Log.v(TAG, "month: "+c.get(Calendar.MONTH));
					handler = new Handler();
					handler.post(largeCalendarUpdater);
				}
			}
		});

		btnRight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Log.v(TAG, "click: "+c.get(Calendar.MONTH));
				if (c.get(Calendar.MONTH) <= maxRange.get(Calendar.MONTH)
						&& c.get(Calendar.YEAR) <= maxRange.get(Calendar.YEAR)) {
					int flag = 1;
					c.add(Calendar.MONTH, +1);
					for (int i = 0; i < 6; i++) {

						if (i == 0) {
							tvMonth6.setText(sdf.format(c.getTime()));
							complianceAdapter6 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar6.setAdapter(complianceAdapter6);

						} else if (i == 1) {
							c.add(Calendar.MONTH, -flag);

							tvMonth5.setText(sdf.format(c.getTime()));
							complianceAdapter5 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar5.setAdapter(complianceAdapter5);

						} else if (i == 2) {
							c.add(Calendar.MONTH, -flag);

							tvMonth4.setText(sdf.format(c.getTime()));
							complianceAdapter4 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar4.setAdapter(complianceAdapter4);

						} else if (i == 3) {
							c.add(Calendar.MONTH, -flag);

							tvMonth3.setText(sdf.format(c.getTime()));
							complianceAdapter3 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar3.setAdapter(complianceAdapter3);

						} else if (i == 4) {
							c.add(Calendar.MONTH, -flag);

							tvMonth2.setText(sdf.format(c.getTime()));
							complianceAdapter2 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar2.setAdapter(complianceAdapter2);

						} else if (i == 5) {
							c.add(Calendar.MONTH, -flag);

							tvMonth1.setText(sdf.format(c.getTime()));
							complianceAdapter1 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar1.setAdapter(complianceAdapter1);

						}
					}

					c.add(Calendar.MONTH, +5);
					// Log.v(TAG, "month: "+c.get(Calendar.MONTH));
					handler = new Handler();
					handler.post(largeCalendarUpdater);
				}
			}
		});

		tvToday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Calendar c = Calendar.getInstance();
				if (c.get(Calendar.MONTH) >= minRange.get(Calendar.MONTH)
						&& c.get(Calendar.YEAR) >= minRange.get(Calendar.YEAR)) {
					for (int i = 0; i < 6; i++) {

						if (i == 0) {
							tvMonth6.setText(sdf.format(c.getTime()));
							complianceAdapter6 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar6.setAdapter(complianceAdapter6);

						} else if (i == 1) {
							c.add(Calendar.MONTH, -1);

							tvMonth5.setText(sdf.format(c.getTime()));
							complianceAdapter5 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar5.setAdapter(complianceAdapter5);

						} else if (i == 2) {
							c.add(Calendar.MONTH, -1);

							tvMonth4.setText(sdf.format(c.getTime()));
							complianceAdapter4 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar4.setAdapter(complianceAdapter4);

						} else if (i == 3) {
							c.add(Calendar.MONTH, -1);

							tvMonth3.setText(sdf.format(c.getTime()));
							complianceAdapter3 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar3.setAdapter(complianceAdapter3);

						} else if (i == 4) {
							c.add(Calendar.MONTH, -1);

							tvMonth2.setText(sdf.format(c.getTime()));
							complianceAdapter2 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar2.setAdapter(complianceAdapter2);

						} else if (i == 5) {
							c.add(Calendar.MONTH, -1);

							tvMonth1.setText(sdf.format(c.getTime()));
							complianceAdapter1 = new ComplianceAdapter(
									getApplicationContext(), c
											.get(Calendar.MONTH), c
											.get(Calendar.YEAR), getResources()
											.getDisplayMetrics());
							calendar1.setAdapter(complianceAdapter1);

						}
					}

					handler = new Handler();
					handler.post(largeCalendarUpdater);
				}
			}
		});

		btnTask.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				btnTask.setBackground(getResources().getDrawable(
						R.drawable.blue_buton));
				btnDuration.setBackgroundColor(getResources().getColor(
						R.color.black));
				int flag = 1;
				for (int i = 0; i < 6; i++) {

					if (i == 0) {
						tvMonth6.setText(sdf.format(c.getTime()));
						complianceAdapter6 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar6.setAdapter(complianceAdapter6);

					} else if (i == 1) {
						c.add(Calendar.MONTH, -flag);

						tvMonth5.setText(sdf.format(c.getTime()));
						complianceAdapter5 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar5.setAdapter(complianceAdapter5);

					} else if (i == 2) {
						c.add(Calendar.MONTH, -flag);

						tvMonth4.setText(sdf.format(c.getTime()));
						complianceAdapter4 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar4.setAdapter(complianceAdapter4);

					} else if (i == 3) {
						c.add(Calendar.MONTH, -flag);

						tvMonth3.setText(sdf.format(c.getTime()));
						complianceAdapter3 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar3.setAdapter(complianceAdapter3);

					} else if (i == 4) {
						c.add(Calendar.MONTH, -flag);

						tvMonth2.setText(sdf.format(c.getTime()));
						complianceAdapter2 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar2.setAdapter(complianceAdapter2);

					} else if (i == 5) {
						c.add(Calendar.MONTH, -flag);

						tvMonth1.setText(sdf.format(c.getTime()));
						complianceAdapter1 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar1.setAdapter(complianceAdapter1);

					}
				}

				c.add(Calendar.MONTH, +5);

				handler = new Handler();
				handler.post(largeCalendarUpdater);
			}

		});

		btnDuration.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				btnDuration.setBackground(getResources().getDrawable(
						R.drawable.blue_buton));
				btnTask.setBackgroundColor(getResources().getColor(
						R.color.black));

				int flag = 1;
				for (int i = 0; i < 6; i++) {

					if (i == 0) {
						tvMonth6.setText(sdf.format(c.getTime()));
						complianceAdapter6 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar6.setAdapter(complianceAdapter6);

					} else if (i == 1) {
						c.add(Calendar.MONTH, -flag);

						tvMonth5.setText(sdf.format(c.getTime()));
						complianceAdapter5 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar5.setAdapter(complianceAdapter5);

					} else if (i == 2) {
						c.add(Calendar.MONTH, -flag);

						tvMonth4.setText(sdf.format(c.getTime()));
						complianceAdapter4 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar4.setAdapter(complianceAdapter4);

					} else if (i == 3) {
						c.add(Calendar.MONTH, -flag);

						tvMonth3.setText(sdf.format(c.getTime()));
						complianceAdapter3 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar3.setAdapter(complianceAdapter3);

					} else if (i == 4) {
						c.add(Calendar.MONTH, -flag);

						tvMonth2.setText(sdf.format(c.getTime()));
						complianceAdapter2 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar2.setAdapter(complianceAdapter2);

					} else if (i == 5) {
						c.add(Calendar.MONTH, -flag);

						tvMonth1.setText(sdf.format(c.getTime()));
						complianceAdapter1 = new ComplianceAdapter(
								getApplicationContext(), c.get(Calendar.MONTH),
								c.get(Calendar.YEAR), getResources()
										.getDisplayMetrics());
						calendar1.setAdapter(complianceAdapter1);

					}
				}

				c.add(Calendar.MONTH, +5);

				handler = new Handler();
				handler.post(largeCalendarUpdater);
			}
		});

		dialog.show();

	}

	private void showChartView(LatestTaskTypeResults items) {
		dialog = new Dialog(ClinicianActivity.this, R.style.ActivityAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.chart_main);
		dialog.setCancelable(true);

		pg1 = (ProgressBar) dialog.findViewById(R.id.progressBar1);
		pg2 = (ProgressBar) dialog.findViewById(R.id.progressBar2);
		pbAccuracy = (ProgressBar) dialog.findViewById(R.id.seekBar1);
		final RelativeLayout root = (RelativeLayout) dialog
				.findViewById(R.id.chartcontainer);
		imgBack = (ImageView) dialog.findViewById(R.id.imageIcon);

		tvItem = (CTTextView) dialog.findViewById(R.id.tvItem);
		tvPercent = (CTTextView) dialog.findViewById(R.id.tvPercent);
		tvSec = (CTTextView) dialog.findViewById(R.id.tvMins);
		tvRange = (CTTextView) dialog.findViewById(R.id.tvDate);
		tvNodata1 = (CTTextView) dialog.findViewById(R.id.no_data1);
		tvNodata2 = (CTTextView) dialog.findViewById(R.id.no_data2);
		tvPatient = (CTTextView) dialog.findViewById(R.id.tvPatient);

		RelativeLayout rl = (RelativeLayout) dialog.findViewById(R.id.relative);
		latencyChart = (RelativeLayout) dialog.findViewById(R.id.chartLatency);
		accuracyChart = (RelativeLayout) dialog
				.findViewById(R.id.chartAccuracy);
		setting = (LinearLayout) dialog.findViewById(R.id.linearLayout12);
		callCombinedChartWebservice(items);
		String text = "" + items.getTaskType() + " - Level "
				+ items.getTaskLevel();

		tvItem.setText(text);

		tvPatient.setText(CTUser.getInstance().getPatientUsernameForDisplay());

		tvPercent.setText("" + (int) (items.getAccuracy() * 100) + "%");

		int latency = (int) Math.round(items.getAvgLatency());
		String minis = latency + " <font size=\"2\"> sec</font>";
		tvSec.setText(Html.fromHtml(minis));

		pbAccuracy.setProgress((int) Math.round(items.getAccuracy() * 100));
		if (items.getAccuracy() == 0.0) {

			if (items.getPercentSkipped() == 0) {
				pbAccuracy.setBackgroundColor(getResources().getColor(
						R.color.top_dark_gray));
			} else {
				pbAccuracy.setBackgroundColor(Color.YELLOW);
			}
		} else {
			pbAccuracy.setBackgroundColor(getResources().getColor(
					R.color.orange));
		}

		imgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});

		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showCalendarPopup(v, root);
			}
		});

		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showLogOut();
			}
		});

		dialog.show();

	}

	private void initPatient() {
		tvPatientName.setText(getString(R.string.simple_mode));
		imgPatient.setVisibility(View.GONE);
		tvTitle.setText(getString(R.string.advanced_mode));
		llNote.setVisibility(View.INVISIBLE);

	}

	private void animatedPatientActivity() {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(),
				PatientDashboardActivity.class);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

		ActivitySwitcher.animationIn(findViewById(R.id.container),
				getWindowManager(),
				new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						startActivity(intent);
						finish();
					}
				});
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {

		Log.d(LOG_TAG, "onReceiveResult(resultCode=" + resultCode + ", resultData="
				+ resultData.toString());

		switch (resultCode) {
		case SyncService.STATUS_RUNNING:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.DASHBOARD_TOKEN) {
				loadingStart();
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_PLOT_ACCURACY_TOKEN) {
				pg1.setVisibility(View.VISIBLE);
				pg2.setVisibility(View.VISIBLE);

			}

			break;
		case SyncService.STATUS_FINISHED:

			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.DASHBOARD_TOKEN) {

				this.getLoaderManager().initLoader(Constants.DASHBOARD_TOKEN,
						null, this);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_MIDDLE_ROW_TOKEN) {

				this.getLoaderManager().initLoader(
						Constants.CLINICIAN_MIDDLE_ROW_TOKEN, null, this);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_PLOT_ACCURACY_TOKEN) {

				this.getLoaderManager().initLoader(
						Constants.CLINICIAN_PLOT_ACCURACY_TOKEN, null, this);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.GET_PATIENT_LIST_TOKEN) {

				// unfortunately the patient list comes without the image paths for each user,
				// which are obtained via a separate web service call, so chain that call now
				String url = getString(R.string.remote_preurl)
						+ getString(R.string.remote_clinician_alert);
				ServiceHelper.execute(getApplicationContext(), mReceiver,
						Constants.GET_PATIENT_IMAGEPATH_TOKEN, url);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.GET_PATIENT_IMAGEPATH_TOKEN) {
				
				// OK now we have all patient info, so show it
				showPatientPickerPopup();
				
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
				// PROBLEM: what if the change password fails ... we didn't want to 
				// save the new password in the CTUser until now since we don't know
				// if the server call succeeded ... but if we wait until the server
				// response comes back, we don't know what the new password is to set
				// it into CTUser ... the password-setting web service call probably 
				// does not return it ...
				CTUser.getInstance().confirmProspectiveNewPassword();
				showAlert("Password successfully changed");

			}

			Log.v(LOG_TAG, "ENDS");

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

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_PLOT_ACCURACY_TOKEN) {

				pg1.setVisibility(View.GONE);
				pg2.setVisibility(View.GONE);
			}

			break;
		case SyncService.STATUS_NO_NETWORK:
			if (!dialog.isShowing())
				showInternetAlert("Oops!", getString(R.string.no_internet));

			break;
		}
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			// monthAdapter.populateMonth();

			monthAdapter.setItems(calendarList);
			monthAdapter.notifyDataSetChanged();
		}
	};

	public Runnable largeCalendarUpdater = new Runnable() {

		@Override
		public void run() {

			complianceAdapter6.setItems(calendarList);
			complianceAdapter6.notifyDataSetChanged();
			complianceAdapter5.setItems(calendarList);
			complianceAdapter5.notifyDataSetChanged();
			complianceAdapter4.setItems(calendarList);
			complianceAdapter4.notifyDataSetChanged();
			complianceAdapter3.setItems(calendarList);
			complianceAdapter3.notifyDataSetChanged();
			complianceAdapter2.setItems(calendarList);
			complianceAdapter2.notifyDataSetChanged();
			complianceAdapter1.setItems(calendarList);
			complianceAdapter1.notifyDataSetChanged();
		}
	};

	@Override
	public void popoverViewWillShow(PopoverView view) {

		Log.v(LOG_TAG, "WILLSHOW");
	}

	@Override
	public void popoverViewDidShow(PopoverView view) {

		Log.v(LOG_TAG, "DIDSHOW");

	}

	@Override
	public void popoverViewWillDismiss(PopoverView view) {

		Log.v(LOG_TAG, "WILLDISMISS");
	}

	@Override
	public void popoverViewDidDismiss(PopoverView view) {

		Log.v(LOG_TAG, "DIDDISMISS");

		if (rangeChange) {
			loadChartRange(selectedCalendarItem);
		} else if (rangeClear) {
			LoadAccuracyChart(accuracyChart, scoresList);

			LoadLatencyChart(latencyChart, complianceList);
			rangeClear = false;
		}
	}

	private static class compareDate implements Comparator<String> {

		DateFormat f = new SimpleDateFormat("MM/dd/yyyy");

		@Override
		public int compare(String o1, String o2) {
			try {
				return f.parse(o1).compareTo(f.parse(o2));
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	private interface ScoreQuery {
		String[] PROJECTION = new String[] { ScoresList.DATE,
				ScoresList.CUMULATIVEACCURACY, ScoresList.ACCURACY };
	}

	private interface ComplianceQuery {
		String[] PROJECTION = new String[] { ComplianceList.DATE,
				ComplianceList.DURATION, ComplianceList.INCLINIC,
				ComplianceList.COMPLETEDTASKCOUNT };
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
		if (dialog != null)
			dialog.dismiss();
		combinedChartObjList.clear();
		return;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rlMiddletext:
			if (dashBoard != null)
				showSelectionDropDown(dashBoard.getSelectionList(), v);
			break;
		case R.id.rlPatient:
			if (CTUser.getInstance().isUserTypeClinician()) {
				showPatientDropDown();
			} else {
				if (dialog != null)
					dialog.dismiss();

				animatedPatientActivity();
			}

			break;
		case R.id.linearLayout12:

			if (CTUser.getInstance().isUserTypePatient())
				showPatientSettingDropDown(imgSetting);
			else
				showSettingDropDown(imgSetting);
			break;
		case R.id.llSeemore:
			if (dialog == null || !dialog.isShowing())
				showLargeComplianceCalendarView();
			break;
		case R.id.ctIcon:
			if (dialog == null || !dialog.isShowing())
				AlertPopup.showVersionAlert(ClinicianActivity.this,
						getString(R.string.app_version), null);
			break;
		case R.id.btnTasks:
			btnTasks.setBackground(getResources().getDrawable(
					R.drawable.tasks_tabs));
			Intent intent = new Intent(getApplicationContext(),
					TasksActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);

			overridePendingTransition(0, 0);
			mActivitySwitch.start();
			btnTasks.setBackground(getResources().getDrawable(
					R.drawable.tasks_tabs_brw));
			break;
		case R.id.linearLayout2:
			/*
			 * @Developed By: Arumugam
			 */
			if (CTUser.getInstance().isUserTypePatient()) {

				Intent help = new Intent(getApplicationContext(),
						HelpActivity.class);
				help.putExtra("helpid", Constants.PATIENTLOGINSUMMARY);

				startActivity(help);

			} else {

				Intent help = new Intent(getApplicationContext(),
						HelpActivity.class);
				help.putExtra("helpid", Constants.CLINICIANSUMMARY);

				startActivity(help);

			}
			break;
		default:
			break;
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {

		switch (id) {
		case Constants.DASHBOARD_TOKEN:
			return new CursorLoader(this, Dashboards.CONTENT_URI, null, null,
					null, Dashboards.DEFAULT_SORT);

		case Constants.CLINICIAN_MIDDLE_ROW_TOKEN:
			return new CursorLoader(this, LatestTypeResultsList.CONTENT_URI,
					null, null, null, LatestTypeResultsList.DEFAULT_SORT);
		case Constants.CLINICIAN_PLOT_ACCURACY_TOKEN:
			return new CursorLoader(this, ScoresList.CONTENT_URI, null, null,
					null, ScoresList.DEFAULT_SORT);
		case Constants.CLINICIAN_PLOT_LATENCY_TOKEN:
			return new CursorLoader(this, ComplianceList.CONTENT_URI, null,
					null, null, ComplianceList.DEFAULT_SORT);
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

		if (data.getCount() < 1) return;

		String json = null;
		Gson gson = null;
		Type type = null;

		switch (arg0.getId()) {

		case Constants.DASHBOARD_TOKEN: 

			if (pg != null)
				pg.removeSlices();
			tvPercent.setText("");

			data.moveToFirst();

			dashBoard = new Dashboard();
			json = data.getString(data.getColumnIndex(Dashboards.JSON));
			gson = new Gson();
			dashBoard = gson.fromJson(json, Dashboard.class);
			pg.removeSlices();
			displayCalendar(dashBoard);
			displayCombinedChart(dashBoard);
			displayDonutChart(dashBoard);
			displayListView(dashBoard);
			if (dashBoard.getSelectionList().size() != 0) {
				tvMiddleText.setText((String) dashBoard.getSelectionList()
						.get(0).getValue());

			}
			loadingStop();
			break;

		case Constants.CLINICIAN_MIDDLE_ROW_TOKEN: 

			data.moveToFirst();
			latestResult = new Dashboard();
			json = data.getString(data
					.getColumnIndex(LatestTypeResultsList.JSON));

			gson = new Gson();
			latestResult = gson.fromJson(json, Dashboard.class);
			displayMiddleTableView(latestResult);

			break;

		case Constants.CLINICIAN_PLOT_ACCURACY_TOKEN: 
			type = new TypeToken<List<Scores>>() {}.getType();
			List<Scores> firstTranslationList = new ArrayList<Scores>();
			data.moveToFirst();
			json = data.getString(data.getColumnIndex(ScoresList.JSON));

			firstTranslationList = new Gson().fromJson(json, type);
			scoresList = firstTranslationList;

			this.getLoaderManager().initLoader(
					Constants.CLINICIAN_PLOT_LATENCY_TOKEN, null, this);
			break;


		case Constants.CLINICIAN_PLOT_LATENCY_TOKEN: 
			type = new TypeToken<List<Compliance>>() {}.getType();
			List<Compliance> localComplianceList = new ArrayList<Compliance>();
			data.moveToFirst();
			json = data.getString(data
					.getColumnIndex(ComplianceList.JSON));
			localComplianceList = new Gson().fromJson(json, type);
			complianceList = localComplianceList;
			LoadLatencyChart(latencyChart, complianceList);
			LoadAccuracyChart(accuracyChart, scoresList);
			pg1.setVisibility(View.GONE);
			pg2.setVisibility(View.GONE);

			break;


		case Constants.UPDATE_ACCOUNT_INFO: 
			data.moveToFirst();
			// Patients = new

			String email = data.getString(data.getColumnIndex(Users.EMAIL));
			Log.v(LOG_TAG, email);
			String pno = data.getString(data.getColumnIndex(Users.PHONENUMBER));
			// Log.v(TAG, pno);
			String fname = data.getString(data.getColumnIndex(Users.FIRSTNAME));
			Log.v(LOG_TAG, fname);
			String lname = data.getString(data.getColumnIndex(Users.LASTNAME));
			Log.v(LOG_TAG, lname);
			
			break;


		case Constants.VALIDATE_EMAIL_TOKEN: 

			data.moveToFirst();

			json = data.getString(data.getColumnIndex(Session.JSON));

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
				// is valid and not present in db ... so store it along with other user acct info that
				// may have been edited
				updateAccountInfo();
			}
			break;


		default:
			throw new IllegalStateException("invalid load finished");
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

		// No op
	}

	/** we call the WebSignupActivity() with startActivityForResult() so it can 
	 * hand control back to us and we can tell what happened */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    if (requestCode == RESULT_CODE_ADD_PATIENT) {
	        // Make sure the request was successful
	    	
	        if (resultCode == RESULT_OK) {
	            
	        	// a new patient was added and selected - use the CTUser to revise ourself since we are
	        	// now viewing a different patient

	        	setUpDisplay();
	        	
	        } else {
	        	// don't worry about it ... add patient failed, we just go back to
	        	// what we were doing when we sent the intent
	        }
	    }
	}
	
	
}
