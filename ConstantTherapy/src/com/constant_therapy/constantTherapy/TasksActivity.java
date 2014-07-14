package com.constant_therapy.constantTherapy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.animation.ActivitySwitcher;
import com.constant_therapy.animation.SwipeListener;
import com.constant_therapy.dashboard.Dashboard;
import com.constant_therapy.dashboard.Homework;
import com.constant_therapy.dashboard.ScheduledTaskTypes;
import com.constant_therapy.dashboard.TaskTypeScores;
import com.constant_therapy.dashboard.TasksHierarchy;
import com.constant_therapy.dashboard.TasksType;
import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.popup.AlertPopup;
import com.constant_therapy.popup.PopoverView;
import com.constant_therapy.popup.PopoverView.PopoverViewDelegate;
import com.constant_therapy.provider.TherapyContract.Patients;
import com.constant_therapy.provider.TherapyContract.PatientsList;
import com.constant_therapy.provider.TherapyContract.Session;
import com.constant_therapy.provider.TherapyContract.TaskHierarchy;
import com.constant_therapy.provider.TherapyContract.TaskHomework;
import com.constant_therapy.provider.TherapyContract.TypeBaseline;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.Helper;
import com.constant_therapy.util.HomeworkAdapter;
import com.constant_therapy.util.KeypadAdapter;
import com.constant_therapy.util.KeypadButton;
import com.constant_therapy.util.LeafAdapter;
import com.constant_therapy.util.LevelPopupAdapter;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.util.NLevelAdapter;
import com.constant_therapy.util.NLevelItem;
import com.constant_therapy.util.NLevelView;
import com.constant_therapy.util.PatientModel;
import com.constant_therapy.widget.CTTextView;
import com.constant_therapy.widget.DragSortListView;
import com.constant_therapy.widget.MySwitch;
import com.constant_therapy.widget.ProgressHUD;
import com.constant_therapy.widget.SwipeListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//Test changes for commit in Git
public class TasksActivity extends PatientSelectorActivity implements
		MyResultReceiver.Receiver, View.OnClickListener, PopoverViewDelegate,
		AnimationListener, OnItemLongClickListener,
		LoaderManager.LoaderCallbacks<Cursor>, OnCancelListener {

	private static final String TAG = "TasksActivity";
	public Handler handler;
	Animation animBlink;

	MediaPlayer mTaskAlert, mActivitySwitch, mTaskWarning, mPlayerLocal;

	MyDragEventListener myDragEventListener = new MyDragEventListener();

	Dashboard dashBoard, latestResult;
	TasksHierarchy tasksHierarchy;
	TasksHierarchy selectedItem = new TasksHierarchy();
	List<TasksHierarchy> searchResults = new ArrayList<TasksHierarchy>();
	List<TasksHierarchy> taskHierarchyList = new ArrayList<TasksHierarchy>();
	List<TasksHierarchy> taskHierarchyCombinedList = new ArrayList<TasksHierarchy>();
	List<TasksHierarchy> listDataChild = new ArrayList<TasksHierarchy>();
	List<TasksHierarchy> listNoHierachy = new ArrayList<TasksHierarchy>();
// Test to commit or push in Git
	static List<NLevelItem> nLevelItemlist;
	List<NLevelItem> nLevelItemResults = new ArrayList<NLevelItem>();
	TasksType taskType;
	TaskTypeScores taskTypeScores;
	TasksType taskType1, taskType2;;
	PatientModel patientModel;
	Dialog dialog, alertDialog;

	Animator anim1, anim2;

	Homework taskHomework;
	HomeworkAdapter<ScheduledTaskTypes> homeworkAdapter;
	LeafAdapter leafAdapter;
	NLevelAdapter adapter;

	SwipeListView expandableList;
	DragSortListView lvHomework;
	SwipeListView lvNoHierarchy;

	ProgressHUD mProgressHUD;

	Button btnSummary;
	Button btnPerformance;
	Button btnSave;
	Button btnRestore;

	View layoutOverlay;

	LinearLayout setting, helpOverlay;
	LinearLayout glowRegion;
	LinearLayout llNote;
	RelativeLayout rlPatient;
	RelativeLayout targetLayout;
	RelativeLayout rlTaskList;
	RelativeLayout rlBaseline;
	RelativeLayout rlCurrent;

	ImageView imgToggle;
	ImageView imgTasklist;
	ImageView imgBaseline;
	ImageView imgCurrent;
	ImageView imgCTIcon;
	ImageView imgSetting;
	ImageView imgPatient;

	View view, touchView, removeView;

	CTTextView tvItem, tvComp;
	CTTextView tvPercent;
	CTTextView tvUser;
	CTTextView tvNoData;
	CTTextView tvPatient;
	CTTextView tvMiddleText;
	CTTextView tvNodata1, tvNodata2;
	CTTextView tvMonth;
	CTTextView tvRange;
	CTTextView tvHome;
	CTTextView tvTask;
	CTTextView tvMins;
	CTTextView tvTitle;

	EditText etSearch;

	ImageView pb1, pb2;

	MediaPlayer mPlayer;

	private AQuery androidAQuery;

	int countdownNumber;
	int maxLevel = 0;
	final String SOURCELIST_TAG = "listSource";
	final String TARGETLIST_TAG = "listTarget";
	final String TARGETLAYOUT_TAG = "targetLayout";

	String userId;
	String searchString = null;
	String username = null;

	String currentPassword = null;
	String newPassword = null;
	String repeatPassword = null;
	String existingPassword = null;

	int textLength = 0, click = 0;

	static String systemTaskname;
	static String url = null, displayName = null, typeId = null;
	static Context context;

	static int selectedPosition = 0;
	public static int listOriginalSize = 0, count = 0, groupPos = 0,
			childPos = 0, sortCount = 0, selectPos = 0,
			selectHomeworkPosition = 0;

	boolean x = false, firstShow = true, isSearch = false;
	static Boolean isOnorOff = true;

	String updateEmail = null;
	String updatePhone = null;
	String updateFname = null;
	String updateLname = null;

	PopupWindow popupWindow;
	PopupWindow gridpopup;
	//Dialog updateDialog, passwordDialog;
	private LinkedList<String> groups = new LinkedList<String>();
	LevelPopupAdapter groupAdapter;
	private static String selectedFromList;
	CTTextView tvLevel, tvCount;
	String tvlval;
	String tempText = null;
	Boolean isItemCount = false;

	GridView mKeypadGrid;
	KeypadAdapter mKeypadAdapter;
	LayoutInflater layoutInflater;
	int updateCount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.tasks_dashboard);
		Log.v(TAG, "STARTS");

		tvPatient = (CTTextView) findViewById(R.id.tvPatient);
		tvNoData = (CTTextView) findViewById(R.id.tvNodata);
		tvTitle = (CTTextView) findViewById(R.id.tvTitle);

		btnSummary = (Button) findViewById(R.id.btnSummary);
		btnPerformance = (Button) findViewById(R.id.btnPerformance);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnRestore = (Button) findViewById(R.id.btnRestore);

		imgToggle = (ImageView) findViewById(R.id.imgType);
		imgTasklist = (ImageView) findViewById(R.id.imgTaskList);
		imgBaseline = (ImageView) findViewById(R.id.imgBaseLine);
		imgCurrent = (ImageView) findViewById(R.id.imgCurrent);
		imgCTIcon = (ImageView) findViewById(R.id.ctIcon);
		imgSetting = (ImageView) findViewById(R.id.imgSetting);
		imgPatient = (ImageView) findViewById(R.id.imgPatient);

		etSearch = (EditText) findViewById(R.id.etSearch);

		expandableList = (SwipeListView) findViewById(R.id.lvTasks);
		lvHomework = (DragSortListView) findViewById(R.id.lvHomework);
		lvNoHierarchy = (SwipeListView) findViewById(R.id.lvNoType);

		helpOverlay = (LinearLayout) findViewById(R.id.linearLayout2);
		setting = (LinearLayout) findViewById(R.id.linearLayout12);
		glowRegion = (LinearLayout) findViewById(R.id.glowRegion);
		llNote = (LinearLayout) findViewById(R.id.llNote);
		rlPatient = (RelativeLayout) findViewById(R.id.rlPatient);
		targetLayout = (RelativeLayout) findViewById(R.id.targetlayout);
		rlTaskList = (RelativeLayout) findViewById(R.id.rlTaskList);
		rlBaseline = (RelativeLayout) findViewById(R.id.rlBaseLine);
		rlCurrent = (RelativeLayout) findViewById(R.id.rlCurrent);

		layoutOverlay = (View) findViewById(R.id.view);
		androidAQuery = new AQuery(TasksActivity.this);

		pb1 = (ImageView) findViewById(R.id.progressBar1);
		pb2 = (ImageView) findViewById(R.id.progressBar2);
		
		if (CTUser.getInstance().isUserTypePatient()) {
			initPatient();
		} else {
			tvPatient.setText(CTUser.getInstance().getCurrentPatientUsername());
		}

		registerReceiver();

		mTaskAlert = MediaPlayer.create(TasksActivity.this, R.raw.buttonclick3);
		mActivitySwitch = MediaPlayer.create(TasksActivity.this,
				R.raw.buttondoubleclick);
		mTaskWarning = MediaPlayer.create(TasksActivity.this,
				R.raw.taskwithalerts);

		btnSummary.setOnClickListener(this);
		imgToggle.setOnClickListener(this);
		setting.setOnClickListener(this);
		rlPatient.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnRestore.setOnClickListener(this);
		rlBaseline.setOnClickListener(this);
		rlTaskList.setOnClickListener(this);
		rlCurrent.setOnClickListener(this);
		imgCTIcon.setOnClickListener(this);
		helpOverlay.setOnClickListener(this);

		lvHomework.setDropListener(onDrop);
		lvHomework.setRemoveListener(onRemove);
		lvHomework.setDragScrollProfile(ssProfile);

		expandableList.setTag(SOURCELIST_TAG);
		lvHomework.setTag(TARGETLIST_TAG);
		targetLayout.setTag(TARGETLAYOUT_TAG);

		expandableList.setOnDragListener(myDragEventListener);
		lvHomework.setOnDragListener(myDragEventListener);

		lvNoHierarchy.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		lvNoHierarchy.setOnDragListener(myDragEventListener);
		lvNoHierarchy.setSwipeListener(mSwipeListener);
		expandableList.setSwipeListener(mSwipeListener);

		expandableList.setOnItemLongClickListener(this);
		lvNoHierarchy.setOnItemLongClickListener(this);

		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (count > 0) {
					isSearch = true;
				}
				if (imgToggle.getTag().equals("no_type")) {
					filterNoHierarchy(etSearch.getText().toString());
				} else if (imgToggle.getTag().equals("type")) {
					if (nLevelItemlist != null)
						expandTaskHierarchyView(nLevelItemlist);
					filterTaskHierarchy(etSearch.getText().toString());
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// Auto-generated method stub

			}
		});

		lvHomework.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				selectHomeworkPosition = pos;
				showHomeAssignmentPopup(homeworkAdapter.getItem(pos),
						taskTypeScores);
			}

		});

	}

	private void initPatient() {
		tvPatient.setText(getString(R.string.simple_mode));
		imgPatient.setVisibility(View.GONE);
		tvTitle.setText(getString(R.string.advanced_mode));
		llNote.setVisibility(View.INVISIBLE);

	}

	private List<NLevelItem> reorderList(List<NLevelItem> searchResult) {
		List<NLevelItem> tempList = new ArrayList<NLevelItem>();
		for (NLevelItem nL : searchResult) {
			if (nL.getParent() == null) {
				tempList.add(nL);
			} else {
				NLevelItem parent = getParent(nL.getParent().getWrappedObject()
						.getDisplayName());
				if (parent.getParent() != null) {
					NLevelItem parent1 = getParent(parent.getParent()
							.getWrappedObject().getDisplayName());

					if (parent1.getParent() != null) {
						NLevelItem parent2 = getParent(parent1.getParent()
								.getWrappedObject().getDisplayName());
						if (parent2.getParent() != null) {
							if (!isAlreadyExist(tempList, parent1))
								tempList.add(parent1);
							if (!isAlreadyExist(tempList, parent))
								tempList.add(parent);
							tempList.add(nL);
						} else {
							if (!isAlreadyExist(tempList, parent2))
								tempList.add(parent2);
							if (!isAlreadyExist(tempList, parent1))
								tempList.add(parent1);
							if (!isAlreadyExist(tempList, parent))
								tempList.add(parent);
							tempList.add(nL);
						}

					} else {
						if (!isAlreadyExist(tempList, parent1))
							tempList.add(parent1);
						if (!isAlreadyExist(tempList, parent))
							tempList.add(parent);
						tempList.add(nL);
					}

				} else {
					if (!isAlreadyExist(tempList, parent)) {
						tempList.add(parent);
					}
					tempList.add(nL);
				}

			}
		}

		return tempList;
	}

	private Boolean isAlreadyExist(List<NLevelItem> searchResults,
			NLevelItem item) {

		for (NLevelItem nL : searchResults) {
			if (nL.getWrappedObject().getDisplayName()
					.equalsIgnoreCase(item.getWrappedObject().getDisplayName()))
				return true;

		}

		return false;
	}

	private void loadingTaskStart() {
		pb1.setVisibility(View.VISIBLE);
		layoutOverlay.setVisibility(View.VISIBLE);
		layoutOverlay.setClickable(true);
		anim1 = AnimatorInflater.loadAnimator(this, R.anim.flip_on_vertical);
		anim1.setTarget(pb1);

		anim1.start();

	}

	private void loadingHomeworkStart() {

		pb2.setVisibility(View.VISIBLE);
		layoutOverlay.setVisibility(View.VISIBLE);
		layoutOverlay.setClickable(true);
		anim2 = AnimatorInflater.loadAnimator(this, R.anim.flip_on_vertical);
		anim2.setTarget(pb2);
		anim2.start();

	}

	private void loadingTaskStop() {
		if (anim1 != null) {
			anim1.cancel();
			pb1.setVisibility(View.GONE);
			layoutOverlay.setVisibility(View.GONE);
			layoutOverlay.setClickable(false);
		}

	}

	private void loadingHomeworkStop() {
		if (anim2 != null) {
			anim2.cancel();
			pb2.setVisibility(View.GONE);
			layoutOverlay.setVisibility(View.GONE);
			layoutOverlay.setClickable(false);
		}

	}

	private NLevelItem getParent(String name) {
		NLevelItem temp = new NLevelItem();
		for (NLevelItem nL : nLevelItemlist) {
			if (nL.getWrappedObject().getDisplayName().equalsIgnoreCase(name)) {
				temp = nL;
				break;
			}

		}

		return temp;
	}

	private void filterTaskHierarchy(String charText) {

		charText = charText.toLowerCase(Locale.getDefault());

		nLevelItemResults.clear();
		if (nLevelItemlist != null) {
			if (charText.length() == 0) {
				isSearch = false;
				nLevelItemResults.addAll(nLevelItemlist);
				adapter = new NLevelAdapter(nLevelItemResults);
				expandableList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				for (NLevelItem wp : nLevelItemlist) {
					if (wp.getWrappedObject().getDisplayName()
							.toLowerCase(Locale.getDefault())
							.contains(charText.toLowerCase())) {
						nLevelItemResults.add(wp);
					}

				}

				nLevelItemResults = reorderList(nLevelItemResults);
				adapter = new NLevelAdapter(nLevelItemResults);
				expandableList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		}

	}

	private void expandTaskHierarchyView(List<NLevelItem> list) {
		for (int i = 0; i < list.size(); i++) {

			if (!list.get(i).isExpanded()
					&& list.get(i).getWrappedObject().getLevel() != 0) {
				list.get(i).toggle();
			}
		}

	}

	private List<TasksHierarchy> filterNoHierarchy(String charText) {

		charText = charText.toLowerCase(Locale.getDefault());

		searchResults.clear();

		if (charText.length() == 0) {
			isSearch = false;
			searchResults.addAll(listNoHierachy);
		} else {
			for (TasksHierarchy wp : listNoHierachy) {
				if (wp.getDisplayName().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					searchResults.add(wp);
				}
			}
		}

		removeDuplicate(searchResults);
		leafAdapter = new LeafAdapter(TasksActivity.this, searchResults,
				taskTypeScores);
		lvNoHierarchy.setAdapter(leafAdapter);
		leafAdapter.notifyDataSetChanged();
		return searchResults;
	}

	
	/** get task hierarchy from web service */
	private void fetchTaskHierarchy() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_clinician_tasks);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN, url);

	}

	private void showToast() {
		AlertPopup.showSaveToast(TasksActivity.this);
	}

	private void callSaveService(String json) {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + CTUser.getInstance().getPatientIdForDisplay()
				+ getString(R.string.remote_save);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CLINICIAN_TASKS_SAVE_TOKEN, url, json, true);
		animBlink = null;

	}

	private void callHomeWorkService() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + CTUser.getInstance().getPatientIdForDisplay()
				+ getString(R.string.remote_active);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CLINICIAN_TASKS_HOMEWORK_TOKEN, url);

	}

	private void callRestoreHomeWorkService() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + CTUser.getInstance().getPatientIdForDisplay()
				+ getString(R.string.remote_active);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN, url);

	}

	/** get baseline scores for task hierarchy */
	private void callBaseLineService() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + CTUser.getInstance().getPatientIdForDisplay()
				+ getString(R.string.remote_typescores);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN, url);

	}

	private List<TasksHierarchy> combinedList(TasksHierarchy listItems) {
		List<TasksHierarchy> items = new ArrayList<TasksHierarchy>();
		List<TasksHierarchy> items1 = new ArrayList<TasksHierarchy>();
		List<TasksHierarchy> items2 = new ArrayList<TasksHierarchy>();
		if (listItems.getTasksHierarchyLevel1().size() > 0) {
			for (int i = 0; i < listItems.getTasksHierarchyLevel1().size(); i++) {
				items1 = listItems.getTasksHierarchyLevel1().get(i)
						.getTasksHierarchyLevel1();
				items.addAll(items1);
				for (int k = 0; k < items1.size(); k++) {
					items2 = items1.get(k).getTasksHierarchyLevel1();
					if (items2.size() > 0 && items2.get(0).getLevel() == 3) {
						for (int l = 0; l < items2.size(); l++) {
							items.addAll(items2.get(l)
									.getTasksHierarchyLevel1());
						}
					} else {
						items.addAll(items2);
					}

				}
			}
			items = removeParentFromList(items);
			items = removeDuplicate(items);
		}
		return items;
	}
	
	private List<TasksHierarchy> removeParentFromList(List<TasksHierarchy> listItems) {
		List<TasksHierarchy> items = new ArrayList<TasksHierarchy>();
		
		for(int i = 0; i < listItems.size(); i++){
			if(listItems.get(i).getLevel() == 0){
				items.add(listItems.get(i));
			}
		}
		return items;
	}

	private void LoadNoHierarchyItems(TasksHierarchy listItems,
			TaskTypeScores scores) {
		expandableList.setVisibility(View.GONE);
		lvNoHierarchy.setVisibility(View.VISIBLE);
		if (listItems != null) {

			listNoHierachy = combinedList(listItems);
			leafAdapter = new LeafAdapter(TasksActivity.this, listNoHierachy,
					scores);
			lvNoHierarchy.setAdapter(leafAdapter);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<TasksHierarchy> removeDuplicate(List<TasksHierarchy> items) {

		HashSet hs = new HashSet();
		hs.addAll(items);
		items.clear();
		items.addAll(hs);

		return items;
	}

	private void LoadHomeworkItems(Homework listItems) {

		if (listItems != null && !listItems.getScheduledTaskTypes().isEmpty()
				&& listItems.getScheduledTaskTypes().size() != 0) {
			tvNoData.setVisibility(View.GONE);
			homeworkAdapter = new HomeworkAdapter<ScheduledTaskTypes>(
					TasksActivity.this, listItems.getScheduledTaskTypes());
			lvHomework.setAdapter(homeworkAdapter);

		} else {

			lvHomework.setAdapter(null);
			homeworkAdapter = null;
			tvNoData.setVisibility(View.VISIBLE);
		}
	}

	private void sortTaskList(ImageView imgView) {
		if (sortCount == 0) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setRotation(90);
			Collections.sort(taskHierarchyList,
					new Comparator<TasksHierarchy>() {
						@Override
						public int compare(TasksHierarchy s1, TasksHierarchy s2) {
							return s1.getDisplayName().compareToIgnoreCase(
									s2.getDisplayName());
						}
					});
			for (int i = 0; i < taskHierarchyList.size(); i++) {

				Collections.sort(taskHierarchyList.get(i)
						.getTasksHierarchyLevel1(),
						new Comparator<TasksHierarchy>() {
							@Override
							public int compare(TasksHierarchy s1,
									TasksHierarchy s2) {
								return s1.getDisplayName().compareToIgnoreCase(
										s2.getDisplayName());
							}
						});

				for (int j = 0; j < taskHierarchyList.get(i)
						.getTasksHierarchyLevel1().size(); j++) {
					Collections.sort(taskHierarchyList.get(i)
							.getTasksHierarchyLevel1().get(j)
							.getTasksHierarchyLevel1(),
							new Comparator<TasksHierarchy>() {
								@Override
								public int compare(TasksHierarchy s1,
										TasksHierarchy s2) {
									return s1.getDisplayName()
											.compareToIgnoreCase(
													s2.getDisplayName());
								}
							});
				}

			}
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			// loadHierarchy(taskHierarchyList, taskTypeScores);

			sortCount++;
		} else if (sortCount == 1) {
			imgView.setRotation(270);
			Collections.sort(taskHierarchyList,
					new Comparator<TasksHierarchy>() {
						@Override
						public int compare(TasksHierarchy s1, TasksHierarchy s2) {
							return s2.getDisplayName().compareToIgnoreCase(
									s1.getDisplayName());
						}
					});
			for (int i = 0; i < taskHierarchyList.size(); i++) {

				Collections.sort(taskHierarchyList.get(i)
						.getTasksHierarchyLevel1(),
						new Comparator<TasksHierarchy>() {
							@Override
							public int compare(TasksHierarchy s1,
									TasksHierarchy s2) {
								return s2.getDisplayName().compareToIgnoreCase(
										s1.getDisplayName());
							}
						});

				for (int j = 0; j < taskHierarchyList.get(i)
						.getTasksHierarchyLevel1().size(); j++) {
					Collections.sort(taskHierarchyList.get(i)
							.getTasksHierarchyLevel1().get(j)
							.getTasksHierarchyLevel1(),
							new Comparator<TasksHierarchy>() {
								@Override
								public int compare(TasksHierarchy s1,
										TasksHierarchy s2) {
									return s2.getDisplayName()
											.compareToIgnoreCase(
													s1.getDisplayName());
								}
							});
				}

			}
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			sortCount++;
		} else if (sortCount == 2) {
			imgView.setVisibility(View.GONE);
			imgView.setRotation(90);
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			sortCount = 0;
		}
	}

	private void sortBaselineList(ImageView imgView) {
		if (sortCount == 0) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setRotation(90);
			Collections.sort(taskHierarchyList,
					new Comparator<TasksHierarchy>() {
						@Override
						public int compare(TasksHierarchy s1, TasksHierarchy s2) {
							taskType1 = new TasksType();
							taskType2 = new TasksType();

							taskType1 = Helper.getSystemname(taskTypeScores,
									s1.getSystemName());
							taskType2 = Helper.getSystemname(taskTypeScores,
									s2.getSystemName());

							if (taskType1 == null || taskType2 == null)
								return 0;

							return taskType1.getAccuracyBaseline().compareTo(
									taskType2.getAccuracyBaseline());

						}
					});
			for (int i = 0; i < taskHierarchyList.size(); i++) {

				Collections.sort(taskHierarchyList.get(i)
						.getTasksHierarchyLevel1(),
						new Comparator<TasksHierarchy>() {
							@Override
							public int compare(TasksHierarchy s1,
									TasksHierarchy s2) {
								taskType1 = new TasksType();
								taskType2 = new TasksType();

								taskType1 = Helper.getSystemname(
										taskTypeScores, s1.getSystemName());
								taskType2 = Helper.getSystemname(
										taskTypeScores, s2.getSystemName());

								if (taskType1 == null || taskType2 == null)
									return 0;

								return taskType1
										.getAccuracyBaseline()
										.compareTo(
												taskType2.getAccuracyBaseline());
							}
						});

				for (int j = 0; j < taskHierarchyList.get(i)
						.getTasksHierarchyLevel1().size(); j++) {

					Collections.sort(taskHierarchyList.get(i)
							.getTasksHierarchyLevel1().get(j)
							.getTasksHierarchyLevel1(),
							new Comparator<TasksHierarchy>() {
								@Override
								public int compare(TasksHierarchy s1,
										TasksHierarchy s2) {
									taskType1 = new TasksType();
									taskType2 = new TasksType();

									taskType1 = Helper.getSystemname(
											taskTypeScores, s1.getSystemName());
									taskType2 = Helper.getSystemname(
											taskTypeScores, s2.getSystemName());

									if (taskType1 == null || taskType2 == null)
										return 0;

									return taskType1
											.getAccuracyBaseline()
											.compareTo(
													taskType2
															.getAccuracyBaseline());
								}
							});
				}

			}
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			sortCount++;
		} else if (sortCount == 1) {
			imgView.setRotation(270);
			Collections.sort(taskHierarchyList,
					new Comparator<TasksHierarchy>() {
						@Override
						public int compare(TasksHierarchy s1, TasksHierarchy s2) {
							taskType1 = new TasksType();
							taskType2 = new TasksType();

							taskType1 = Helper.getSystemname(taskTypeScores,
									s1.getSystemName());
							taskType2 = Helper.getSystemname(taskTypeScores,
									s2.getSystemName());

							if (taskType1 == null || taskType2 == null)
								return 0;

							return taskType2.getAccuracyBaseline().compareTo(
									taskType1.getAccuracyBaseline());
						}
					});
			for (int i = 0; i < taskHierarchyList.size(); i++) {

				Collections.sort(taskHierarchyList.get(i)
						.getTasksHierarchyLevel1(),
						new Comparator<TasksHierarchy>() {
							@Override
							public int compare(TasksHierarchy s1,
									TasksHierarchy s2) {
								taskType1 = new TasksType();
								taskType2 = new TasksType();

								taskType1 = Helper.getSystemname(
										taskTypeScores, s1.getSystemName());
								taskType2 = Helper.getSystemname(
										taskTypeScores, s2.getSystemName());

								if (taskType1 == null || taskType2 == null)
									return 0;

								return taskType2
										.getAccuracyBaseline()
										.compareTo(
												taskType1.getAccuracyBaseline());
							}
						});

				for (int j = 0; j < taskHierarchyList.get(i)
						.getTasksHierarchyLevel1().size(); j++) {

					Collections.sort(taskHierarchyList.get(i)
							.getTasksHierarchyLevel1().get(j)
							.getTasksHierarchyLevel1(),
							new Comparator<TasksHierarchy>() {
								@Override
								public int compare(TasksHierarchy s1,
										TasksHierarchy s2) {
									taskType1 = new TasksType();
									taskType2 = new TasksType();

									taskType1 = Helper.getSystemname(
											taskTypeScores, s1.getSystemName());
									taskType2 = Helper.getSystemname(
											taskTypeScores, s2.getSystemName());

									if (taskType1 == null || taskType2 == null)
										return 0;

									return taskType2
											.getAccuracyBaseline()
											.compareTo(
													taskType1
															.getAccuracyBaseline());
								}
							});
				}

			}
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			sortCount++;
		} else if (sortCount == 2) {
			imgView.setVisibility(View.GONE);
			imgView.setRotation(90);
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			sortCount = 0;
		}
	}

	private void sortCurrentList(ImageView imgView) {
		if (sortCount == 0) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setRotation(90);

			Collections.sort(taskHierarchyList,
					new Comparator<TasksHierarchy>() {
						@Override
						public int compare(TasksHierarchy s1, TasksHierarchy s2) {
							taskType1 = new TasksType();
							taskType2 = new TasksType();

							taskType1 = Helper.getSystemname(taskTypeScores,
									s1.getSystemName());
							taskType2 = Helper.getSystemname(taskTypeScores,
									s2.getSystemName());

							if (taskType1 == null || taskType2 == null)
								return 0;

							return taskType1.getAccuracyAverage().compareTo(
									taskType2.getAccuracyAverage());
						}
					});
			for (int i = 0; i < taskHierarchyList.size(); i++) {

				Collections.sort(taskHierarchyList.get(i)
						.getTasksHierarchyLevel1(),
						new Comparator<TasksHierarchy>() {
							@Override
							public int compare(TasksHierarchy s1,
									TasksHierarchy s2) {
								taskType1 = new TasksType();
								taskType2 = new TasksType();

								taskType1 = Helper.getSystemname(
										taskTypeScores, s1.getSystemName());
								taskType2 = Helper.getSystemname(
										taskTypeScores, s2.getSystemName());

								if (taskType1 == null || taskType2 == null)
									return 0;

								return taskType1.getAccuracyAverage()
										.compareTo(
												taskType2.getAccuracyAverage());
							}
						});

				for (int j = 0; j < taskHierarchyList.get(i)
						.getTasksHierarchyLevel1().size(); j++) {

					Collections.sort(taskHierarchyList.get(i)
							.getTasksHierarchyLevel1().get(j)
							.getTasksHierarchyLevel1(),
							new Comparator<TasksHierarchy>() {
								@Override
								public int compare(TasksHierarchy s1,
										TasksHierarchy s2) {
									taskType1 = new TasksType();
									taskType2 = new TasksType();

									taskType1 = Helper.getSystemname(
											taskTypeScores, s1.getSystemName());
									taskType2 = Helper.getSystemname(
											taskTypeScores, s2.getSystemName());

									if (taskType1 == null || taskType2 == null)
										return 0;

									return taskType1
											.getAccuracyAverage()
											.compareTo(
													taskType2
															.getAccuracyAverage());
								}
							});
				}

			}
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			sortCount++;
		} else if (sortCount == 1) {
			imgView.setRotation(270);
			Collections.sort(taskHierarchyList,
					new Comparator<TasksHierarchy>() {
						@Override
						public int compare(TasksHierarchy s1, TasksHierarchy s2) {
							taskType1 = new TasksType();
							taskType2 = new TasksType();

							taskType1 = Helper.getSystemname(taskTypeScores,
									s1.getSystemName());
							taskType2 = Helper.getSystemname(taskTypeScores,
									s2.getSystemName());

							if (taskType1 == null || taskType2 == null)
								return 0;

							return taskType2.getAccuracyAverage().compareTo(
									taskType1.getAccuracyAverage());
						}
					});
			for (int i = 0; i < taskHierarchyList.size(); i++) {

				Collections.sort(taskHierarchyList.get(i)
						.getTasksHierarchyLevel1(),
						new Comparator<TasksHierarchy>() {
							@Override
							public int compare(TasksHierarchy s1,
									TasksHierarchy s2) {
								taskType1 = new TasksType();
								taskType2 = new TasksType();

								taskType1 = Helper.getSystemname(
										taskTypeScores, s1.getSystemName());
								taskType2 = Helper.getSystemname(
										taskTypeScores, s2.getSystemName());

								if (taskType1 == null || taskType2 == null)
									return 0;

								return taskType2.getAccuracyAverage()
										.compareTo(
												taskType1.getAccuracyAverage());
							}
						});

				for (int j = 0; j < taskHierarchyList.get(i)
						.getTasksHierarchyLevel1().size(); j++) {

					Collections.sort(taskHierarchyList.get(i)
							.getTasksHierarchyLevel1().get(j)
							.getTasksHierarchyLevel1(),
							new Comparator<TasksHierarchy>() {
								@Override
								public int compare(TasksHierarchy s1,
										TasksHierarchy s2) {
									taskType1 = new TasksType();
									taskType2 = new TasksType();

									taskType1 = Helper.getSystemname(
											taskTypeScores, s1.getSystemName());
									taskType2 = Helper.getSystemname(
											taskTypeScores, s2.getSystemName());

									if (taskType1 == null || taskType2 == null)
										return 0;

									return taskType2
											.getAccuracyAverage()
											.compareTo(
													taskType1
															.getAccuracyAverage());
								}
							});
				}

			}
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			sortCount++;
		} else if (sortCount == 2) {
			imgView.setVisibility(View.GONE);
			// loadHierarchy(taskHierarchyList, taskTypeScores);
			if (!isSearch) {
				loadHierarchy(taskHierarchyList, taskTypeScores);
			} else {
				reloadHierarchy(taskHierarchyList, taskTypeScores);
			}
			sortCount = 0;
		}
	}
	
	
	

	private void sortNoHierachyTaskList(ImageView imgView) {
		List<TasksHierarchy> listHierachy = new ArrayList<TasksHierarchy>();
		
		if (sortCount == 0) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setRotation(90);
			if(!isSearch){
				listHierachy = listNoHierachy;
			}else{
				listHierachy = filterNoHierarchy(etSearch.getText().toString());
			}
			Collections.sort(listHierachy, new Comparator<TasksHierarchy>() {
				@Override
				public int compare(TasksHierarchy s1, TasksHierarchy s2) {
					return s1.getDisplayName().compareToIgnoreCase(
							s2.getDisplayName());
				}
			});

			
			leafAdapter = new LeafAdapter(TasksActivity.this,
						listHierachy, taskTypeScores);
			lvNoHierarchy.setAdapter(leafAdapter);

			sortCount++;
		} else if (sortCount == 1) {
			imgView.setRotation(270);
			if(!isSearch){
				listHierachy = listNoHierachy;
			}else{
				listHierachy = filterNoHierarchy(etSearch.getText().toString());
			}
			Collections.sort(listHierachy, new Comparator<TasksHierarchy>() {
				@Override
				public int compare(TasksHierarchy s1, TasksHierarchy s2) {
					return s2.getDisplayName().compareToIgnoreCase(
							s1.getDisplayName());
				}
			});
			
			leafAdapter = new LeafAdapter(TasksActivity.this,
						listHierachy, taskTypeScores);
			lvNoHierarchy.setAdapter(leafAdapter);

			sortCount++;
		} else if (sortCount == 2) {
			imgView.setVisibility(View.GONE);
			imgView.setRotation(90);

			if (!isSearch) {
				LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);
			} else {
				listNoHierachy = combinedList(tasksHierarchy);
				expandableList.setVisibility(View.GONE);
				lvNoHierarchy.setVisibility(View.VISIBLE);
				filterNoHierarchy(etSearch.getText().toString());
			}

			sortCount = 0;
		}
	}
	
	private List<TasksHierarchy> rearrangeListBeforeSorting(List<TasksHierarchy> listHierachy){
		List<TasksHierarchy> mainHierachy = new ArrayList<TasksHierarchy>();
		List<TasksHierarchy> nullHierachy = new ArrayList<TasksHierarchy>();
		removeDuplicate(listHierachy);
		for(int i = 0; i < listHierachy.size(); i++){
			if(Helper.getSystemname(taskTypeScores, listHierachy.get(i).getSystemName()) == null)
				nullHierachy.add(listHierachy.get(i));
			else
				mainHierachy.add(listHierachy.get(i));
		}
		Log.v(TAG, ""+nullHierachy.size());
		mainHierachy.addAll(nullHierachy);
		return mainHierachy;
	}

	private void sortNoHierachyCurrentList(ImageView imgView) {
		List<TasksHierarchy> listHierachy = new ArrayList<TasksHierarchy>();
		
		if (sortCount == 0) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setRotation(90);
			if(!isSearch){
				listHierachy = listNoHierachy;
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}else{
				listHierachy = filterNoHierarchy(etSearch.getText().toString());
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}
			Collections.sort(listHierachy, new Comparator<TasksHierarchy>() {
				@Override
				public int compare(TasksHierarchy s1, TasksHierarchy s2) {
					taskType1 = new TasksType();
					taskType2 = new TasksType();

					taskType1 = Helper.getSystemname(taskTypeScores,
							s1.getSystemName());
					taskType2 = Helper.getSystemname(taskTypeScores,
							s2.getSystemName());

					if (taskType1 == null || taskType2 == null)
						return 0;

					return taskType1.getAccuracyAverage().compareTo(
							taskType2.getAccuracyAverage());
				}
			});
			
			
				leafAdapter = new LeafAdapter(TasksActivity.this,
						listHierachy, taskTypeScores);
				lvNoHierarchy.setAdapter(leafAdapter);

			

			sortCount++;
		} else if (sortCount == 1) {
			imgView.setRotation(270);
			if(!isSearch){
				listHierachy = listNoHierachy;
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}else{
				listHierachy = filterNoHierarchy(etSearch.getText().toString());
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}
			Collections.sort(listHierachy, new Comparator<TasksHierarchy>() {
				@Override
				public int compare(TasksHierarchy s1, TasksHierarchy s2) {
					taskType1 = new TasksType();
					taskType2 = new TasksType();

					taskType1 = Helper.getSystemname(taskTypeScores,
							s1.getSystemName());
					taskType2 = Helper.getSystemname(taskTypeScores,
							s2.getSystemName());

					if (taskType1 == null || taskType2 == null)
						return 0;

					return taskType2.getAccuracyAverage().compareTo(
							taskType1.getAccuracyAverage());
				}
			});
			
			
			leafAdapter = new LeafAdapter(TasksActivity.this,
						listHierachy, taskTypeScores);
			lvNoHierarchy.setAdapter(leafAdapter);

			
			sortCount++;
		} else if (sortCount == 2) {
			imgView.setVisibility(View.GONE);
			imgView.setRotation(90);

			if (!isSearch) {
				LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);

			} else {
				LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);
				filterNoHierarchy(etSearch.getText().toString());
			}

			sortCount = 0;
		}
	}

	private void sortNoHierachyBaselineList(ImageView imgView) {
		List<TasksHierarchy> listHierachy = new ArrayList<TasksHierarchy>();
		if (sortCount == 0) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setRotation(90);
		
			if(!isSearch){
				listHierachy = listNoHierachy;
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}else{
				listHierachy = filterNoHierarchy(etSearch.getText().toString());
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}
			Collections.sort(listHierachy, new Comparator<TasksHierarchy>() {
				@Override
				public int compare(TasksHierarchy s1, TasksHierarchy s2) {
					taskType1 = new TasksType();
					taskType2 = new TasksType();

					taskType1 = Helper.getSystemname(taskTypeScores,
							s1.getSystemName());
					taskType2 = Helper.getSystemname(taskTypeScores,
							s2.getSystemName());

					if (taskType1 == null || taskType2 == null)
						return 0;

					return taskType1.getAccuracyBaseline().compareTo(
							taskType2.getAccuracyBaseline());
				}
			});
			
			
				leafAdapter = new LeafAdapter(TasksActivity.this,
						listHierachy, taskTypeScores);
				lvNoHierarchy.setAdapter(leafAdapter);

			
			sortCount++;
		} else if (sortCount == 1) {
			imgView.setRotation(270);
			if(!isSearch){
				listHierachy = listNoHierachy;
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}else{
				listHierachy = filterNoHierarchy(etSearch.getText().toString());
				listHierachy = rearrangeListBeforeSorting(listHierachy);
			}
			
			Collections.sort(listHierachy, new Comparator<TasksHierarchy>() {
				@Override
				public int compare(TasksHierarchy s1, TasksHierarchy s2) {
					taskType1 = new TasksType();
					taskType2 = new TasksType();

					taskType1 = Helper.getSystemname(taskTypeScores,
							s1.getSystemName());
					taskType2 = Helper.getSystemname(taskTypeScores,
							s2.getSystemName());

					if (taskType1 == null || taskType2 == null)
						return 0;

					return taskType2.getAccuracyBaseline().compareTo(
							taskType1.getAccuracyBaseline());
				}
			});
			
			
				leafAdapter = new LeafAdapter(TasksActivity.this,
						listHierachy, taskTypeScores);
				lvNoHierarchy.setAdapter(leafAdapter);


			sortCount++;
		} else if (sortCount == 2) {
			imgView.setVisibility(View.GONE);
			imgView.setRotation(90);

			// LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);
			if (!isSearch) {
				LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);

			} else {
				LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);
				filterNoHierarchy(etSearch.getText().toString());
			}

			sortCount = 0;
		}
	}
	private Boolean isExist(List<ScheduledTaskTypes> list, String display) {

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDisplayName().equalsIgnoreCase(display))
				return true;

		}

		return false;

	}

	/*
	 * Method for alert dialog of logout.
	 */

	private void showLogOut() {
		final Dialog dialog = new Dialog(TasksActivity.this);
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

	@SuppressWarnings({ "static-access", "deprecation" })
	private void showSettingDropDown(View v) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.setting, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = popoverView.getWidth() + size.x;
		int height = popoverView.getHeight() + size.y;

		popoverView.setContentSizeForViewInPopover(new Point(width / 3,
				(int) (height / 1.75)));
		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionUp, true);

		LinearLayout logout = (LinearLayout) popoverView
				.findViewById(R.id.llLogout);
		LinearLayout llUpdate = (LinearLayout) popoverView
				.findViewById(R.id.llUpdate);
		MySwitch mySwitch = (MySwitch) popoverView.findViewById(R.id.switch1);
		mySwitch.isOnorOff(isOnorOff);
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				isOnorOff = isChecked;
				Helper.setIsRight(TasksActivity.this, isChecked);
			}
		});

		llUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updatePopup(CTUser.getInstance().getEmail(), CTUser.getInstance().getPhoneNumber(), CTUser.getInstance().getFirstName(), CTUser.getInstance().getLastName());
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showLogOut();
			}
		});

	}

	private void showAlert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				TasksActivity.this);

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

	/** duplicate of PatientSelectorActivity.updateAccountInfo() except that PatientSelectorActivity
	 * uses Constants.UPDATE_ACCOUNT_TOKEN instead of Constants.UPDATE_ACCOUNT_INFO ... also in 
	 * this version the data is put directly into the provider here instead of waiting for the
	 * response from the server (which might reject the action!) ... but there is no Processor
	 * defined for Constants.UPDATE_ACCOUNT_INFO ... so that's why we didn't wait for the provider,
	 * the provider won't get updated without a processor being defined for the response ...
	 * why is this so different from ClinicianActivity? */
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

		// those web service calls don't return anything that can update our provider...
		// though it's not clear that we need to keep updated info in the provider,
		// we actually haven't made that decision yet
		storeUpdatedAccountInfo(email, phone, fname, lname);

	}

	/*
	 * Method for retrieving patientId from database.
	 */

	private String retriveUserId() {

		Cursor cursor = this.getContentResolver().query(Users.CONTENT_URI,
				null, null, null, Users.DEFAULT_SORT);

		String userId = null;
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(Users.PATIENT_ID);
				userId = cursor.getString(index);
			}
		}
		cursor.close();

		return userId;

	}

	/*
	 * Method for showing internet alert.
	 */

	/** show an alert about internet availability, and when OK is clicked
	 * to acknowledge it, refresh some stuff (if we can) */
	@Override
	protected void showInternetAlert(String title, String message) {
		dialog = new Dialog(TasksActivity.this);
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
				loadingTaskStop();
				loadingHomeworkStop();
				loadHierarchy(taskHierarchyList, taskTypeScores);
				LoadHomeworkItems(taskHomework);

			}
		});

		dialog.show();

	}

	private void showVersionAlert(String title, String message) {
		alertDialog = new Dialog(TasksActivity.this);
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setContentView(R.layout.dialog);
		alertDialog.setCancelable(false);
		CTTextView text = (CTTextView) alertDialog.findViewById(R.id.tv);
		text.setText(title);
		CTTextView mess = (CTTextView) alertDialog.findViewById(R.id.etsearch);
		if (message != null) {

			mess.setText(message);
			mess.setVisibility(View.VISIBLE);
		} else {
			mess.setVisibility(View.GONE);
		}
		Button dialogButton = (Button) alertDialog.findViewById(R.id.btncancel);
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
				alertDialog.dismiss();

			}
		});

		alertDialog.show();

	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void showGridWindow(ViewGroup rootView, View parent,
			final CTTextView tvCount) {
		isItemCount = true;
		popoverView = new PopoverView(this, R.layout.level_popupgridview, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = popoverView.getWidth() + size.x;
		int height = popoverView.getHeight() + size.y;
		DisplayMetrics dm = new DisplayMetrics();
		if (dm.DENSITY_MEDIUM == getResources().getDisplayMetrics().densityDpi) {
			popoverView.setContentSizeForViewInPopover(new Point(
					(int) (width / 3.2), (int) (height / 3.6)));
		} else {
			popoverView.setContentSizeForViewInPopover(new Point(width / 4,
					(int) (height / 2.7)));
		}
		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(parent),
				PopoverView.PopoverArrowDirectionRight, true);
		popoverView.removeArrow();

		mKeypadGrid = (GridView) popoverView.findViewById(R.id.grdButtons);
		mKeypadAdapter = new KeypadAdapter(this);
		mKeypadAdapter.setOnButtonClickListener(this);
		// Set adapter of the keypad grid
		mKeypadGrid.setAdapter(mKeypadAdapter);

		mKeypadAdapter.setOnButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				KeypadButton keypadButton = (KeypadButton) btn.getTag();
				int location = keypadButton.ordinal();
				setItemCount(location);

			}
		});

	}

	private void setItemCount(int location) {
		switch (location) {
		case 0:
			String value = "7";
			ProcessKeypadInput(value, tvCount);
			break;

		case 1:
			value = "8";
			ProcessKeypadInput(value, tvCount);
			break;

		case 2:
			value = "9";

			ProcessKeypadInput(value, tvCount);
			break;

		case 3:
			value = "4";
			ProcessKeypadInput(value, tvCount);
			break;

		case 4:
			value = "5";
			ProcessKeypadInput(value, tvCount);
			break;

		case 5:
			value = "6";
			ProcessKeypadInput(value, tvCount);
			break;

		case 6:
			value = "1";
			ProcessKeypadInput(value, tvCount);
			break;

		case 7:
			value = "2";
			ProcessKeypadInput(value, tvCount);
			break;

		case 8:
			value = "3";
			ProcessKeypadInput(value, tvCount);
			break;

		case 9:
			if (tvCount.getText().length() > 0) {
				tempText = tvCount.getText().toString();
				tvCount.setText(tempText.substring(0, tempText.length() - 1));
			} else {
				// showInternetAlert("Error!", "No item to delete");
			}
			break;

		case 10:
			value = "0";
			ProcessKeypadInput(value, tvCount);
			break;

		case 11:

			if (tvCount.getText().length() < 1) {
				value = "15";
				ProcessKeypadInput(value, tvCount);
				popoverView.dismissPopover(false);
				// showInternetAlert("Warning!", "No items selected");
			} else
				popoverView.dismissPopover(false);
			break;

		}

	}

	@SuppressWarnings("deprecation")
	private void showHomeAssignmentPopup(
			final ScheduledTaskTypes scheduledTaskTypes, TaskTypeScores scores) {
		dialog = new Dialog(TasksActivity.this, R.style.TaskPopAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.homeworkassignment_popup);
		dialog.setCancelable(true);
		CTTextView tvDisplayName = (CTTextView) dialog
				.findViewById(R.id.tvDisplay);
		final RelativeLayout root = (RelativeLayout) dialog
				.findViewById(R.id.taskContainer);
		CTTextView tvBase = (CTTextView) dialog.findViewById(R.id.tvBaseline);
		CTTextView tvCurr = (CTTextView) dialog.findViewById(R.id.tvCurrent);
		tvLevel = (CTTextView) dialog.findViewById(R.id.tvLevel);
		tvCount = (CTTextView) dialog.findViewById(R.id.tvCount);
		CTTextView tvDescription = (CTTextView) dialog
				.findViewById(R.id.tvDescription);
		final ImageView imgRes = (ImageView) dialog
				.findViewById(R.id.imgResorce);
		ImageView imgclose = (ImageView) dialog.findViewById(R.id.imgClose);
		LinearLayout llStart = (LinearLayout) dialog.findViewById(R.id.llDo);
		LinearLayout llSave = (LinearLayout) dialog.findViewById(R.id.llSave);
		tvDisplayName.setText(scheduledTaskTypes.getDisplayName());

		taskHierarchyCombinedList = combinedList(tasksHierarchy);
		TasksHierarchy levelitems = null;

		TasksType taskScores = new TasksType();
		levelitems = Helper.getLevelItems(scheduledTaskTypes.getDisplayName(),
				taskHierarchyCombinedList);
		maxLevel = Helper.getMaxLevel(scheduledTaskTypes.getDisplayName(),
				taskHierarchyCombinedList);
		if (levelitems != null) {
			tvDescription.setText(levelitems.getDescription());

			String imageUrl = levelitems.getResourceUrl()
					+ Constants.IMAGE_CONSTANT + "/"
					+ levelitems.getSampleImagePath();
			androidAQuery.id(imgRes).image(imageUrl, true, true, 0, 0,
					new BitmapAjaxCallback() {
						@Override
						public void callback(String url, ImageView iv,
								Bitmap bm, AjaxStatus status) {
							imgRes.setImageBitmap(bm);
							imgRes.setScaleType(ScaleType.FIT_XY);
						}
					}.header("User-Agent", "android"));
			taskScores = Helper.getSystemname(scores,
					levelitems.getSystemName());

		}

		ShapeDrawable circle = new ShapeDrawable(new OvalShape());
		if (taskScores == null || taskScores.getAccuracyBaseline() == 0.0d) {
			circle.getPaint().setColor(
					getResources().getColor(R.color.top_dark_gray));
			tvBase.setText("--");
		} else {
			circle.getPaint().setColor(
					Helper.colorForScoreAndMean(
							taskScores.getAccuracyBaseline(),
							taskScores.getAccuracyMean()));
			tvBase.setText(""
					+ Math.round(taskScores.getAccuracyBaseline() * 100) + "%");
		}
		circle.setBounds(1, 1, 1, 1);
		tvBase.setBackgroundDrawable(circle);
		tvBase.setWidth(tvBase.getHeight());
		ShapeDrawable rec = new ShapeDrawable();
		if (taskScores == null || taskScores.getAccuracyAverage() == 0.0d) {
			rec.getPaint().setColor(
					getResources().getColor(R.color.top_dark_gray));
			tvCurr.setText("--");
		} else {
			rec.getPaint().setColor(
					Helper.colorForScoreAndMean(
							taskScores.getAccuracyAverage(),
							taskScores.getAccuracyMean()));
			tvCurr.setText(""
					+ Math.round(taskScores.getAccuracyAverage() * 100) + "%");
		}
		rec.setBounds(1, 1, 1, 1);
		tvCurr.setBackgroundDrawable(rec);

		ShapeDrawable rec1 = new ShapeDrawable();
		rec1.getPaint().setColor(Color.WHITE);
		tvLevel.setText("" + scheduledTaskTypes.getTaskLevel());
		tvCount.setText("" + scheduledTaskTypes.getTaskCount());

		tvLevel.setBackgroundDrawable(rec1);
		tvCount.setBackgroundDrawable(rec1);
		playLocalFile(R.raw.please_select_item_count_and_difficulty_for_task);

		imgclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mPlayerLocal != null) {
					if (mPlayerLocal.isPlaying()) {
						mPlayerLocal.stop();
						mPlayerLocal.release();
						mPlayerLocal = null;
					}
				}
				dialog.dismiss();
			}
		});

		llStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mPlayerLocal != null) {
					if (mPlayerLocal.isPlaying()) {
						mPlayerLocal.stop();
						mPlayerLocal.release();
						mPlayerLocal = null;
					}
				}
				// clearing cache

				try {
					Helper.trimCache(getApplicationContext());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (Helper.isMicrophoneTask(scheduledTaskTypes.getTaskTypeId())
						&& !SpeechRecognizer
								.isRecognitionAvailable(getApplicationContext())) {

					showInternetAlert("Oops!",
							getString(R.string.alert_microphone));

				} else {
					callDeleteService();
					new Handler().postDelayed(new Runnable() {

						public void run() {
							Intent i = new Intent(getApplicationContext(),
									DoingTaskActivity.class);
							i.putExtra("displayname",
									scheduledTaskTypes.getDisplayName());
							// here we pass in the ID of the patient who will have the responses
							// assigned to them, so if we are logged in as a clinician, we need
							// to pass in the currently selected patient's ID
							if (CTUser.getInstance().isUserTypePatient())
								i.putExtra("patientId", ""+CTUser.getInstance().getUserId());
							else 
								i.putExtra("patientId", ""+CTUser.getInstance().getCurrentPatient().getPatientId());
							i.putExtra("taskLevel", tvLevel.getText()
									.toString());
							i.putExtra("taskCount", tvCount.getText()
									.toString());
							i.putExtra("typeId",
									scheduledTaskTypes.getTaskTypeId());
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_bottom,
									R.anim.slide_out_top);

						}

					}, Constants.GLOW_ANIM_DURATION);

				}
				dialog.dismiss();
			}
		});

		llSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (tvLevel.getText().toString().length() > 0) {
						int level = Integer.parseInt(tvLevel.getText()
								.toString());
						int count = Integer.parseInt(tvCount.getText()
								.toString());
						homeworkAdapter.getItem(selectHomeworkPosition)
								.setTaskLevel(level);
						homeworkAdapter.getItem(selectHomeworkPosition)
								.setTaskCount(count);
						homeworkAdapter.notifyDataSetChanged();
						showBlink(glowRegion);
					}
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
				}
			}
		});

		tvLevel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				groups.clear();
				for (int k = 1; k <= maxLevel; k++) {

					groups.add(String.valueOf(k));

				}

				showListWindow(v, groups);
			}
		});

		tvCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvCount.setText("");
				showGridWindow(root, v, tvCount);
			}
		});
		dialog.show();

		Window window = dialog.getWindow();
		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = findViewById(R.id.container).getWidth() + size.x;
		int height = findViewById(R.id.container).getHeight() + size.y;
		window.setLayout(width / 3, (int) (height / 2.5));

	}

	private void ProcessKeypadInput(String c, CTTextView tvCount) {
		tvCount.append(c);
		String temp = tvCount.getText().toString();
		int intValue = Integer.parseInt(temp);
		
		if (intValue > 200) {
			// display dialog
			showVersionAlert("", "You cannot assign more than 200 items");
			tvCount.setText("200");
		} else if (intValue == 0) {
			showVersionAlert("", "Enter a value greater than 0");
			tvCount.setText("");
		}

	}

	private void showListWindow(View parent, LinkedList<String> groups) {

		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		view = layoutInflater.inflate(R.layout.task_popuplevel, null);

		final ListView lvGroup = (ListView) view.findViewById(R.id.lvGroup);
		lvGroup.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		groupAdapter = new LevelPopupAdapter(this, groups);
		lvGroup.setAdapter(groupAdapter);

		popupWindow = new PopupWindow(view, 230, 250);
		lvGroup.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedFromList = (String) (lvGroup
						.getItemAtPosition(position));
				groupAdapter.setItemSelected(position);
				groupAdapter.notifyDataSetChanged();
				// setTaskClickVal(selectedFromList);
				tvLevel.setText(selectedFromList);

			}
		});

		popupWindow.setFocusable(true);

		popupWindow.setOutsideTouchable(true);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		int xPos = windowManager.getDefaultDisplay().getWidth() / 3
				- popupWindow.getWidth() / 3;

		popupWindow.showAsDropDown(parent, xPos, 1);
	}

	/*
	 * private void setTaskClickVal(String tvlval) {
	 * 
	 * this.tvlval = tvlval;
	 * 
	 * }
	 * 
	 * private String getTaskClickVal() { return tvlval; }
	 */

	@SuppressWarnings({ "deprecation", "static-access" })
	private void showTaskPopup(final TasksHierarchy taskItem,
			TaskTypeScores scores) {
		dialog = new Dialog(TasksActivity.this, R.style.TaskPopAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.task_popup);

		dialog.setCancelable(true);
		final RelativeLayout root = (RelativeLayout) dialog
				.findViewById(R.id.taskContainer);
		CTTextView tvDisplayName = (CTTextView) dialog
				.findViewById(R.id.tvDisplay);
		CTTextView tvBase = (CTTextView) dialog.findViewById(R.id.tvBaseline);
		CTTextView tvCurr = (CTTextView) dialog.findViewById(R.id.tvCurrent);
		tvLevel = (CTTextView) dialog.findViewById(R.id.tvLevel);
		tvCount = (CTTextView) dialog.findViewById(R.id.tvCount);

		CTTextView tvDescription = (CTTextView) dialog
				.findViewById(R.id.tvDescription);
		final ImageView imgRes = (ImageView) dialog
				.findViewById(R.id.imgResorce);
		ImageView imgclose = (ImageView) dialog.findViewById(R.id.imgClose);
		LinearLayout llStart = (LinearLayout) dialog.findViewById(R.id.llStart);
		ProgressBar taskProgress = (ProgressBar) dialog
				.findViewById(R.id.taskProgress);

		String imageUrl = taskItem.getResourceUrl() + Constants.IMAGE_CONSTANT
				+ "/" + taskItem.getSampleImagePath();
		tvDisplayName.setText(taskItem.getDisplayName());
		tvDescription.setText(taskItem.getDescription());
		TasksType taskScores = new TasksType();
		taskScores = Helper.getSystemname(scores, taskItem.getSystemName());

		ShapeDrawable circle = new ShapeDrawable(new OvalShape());

		if (taskScores == null || taskScores.getAccuracyBaseline() == 0.0d) {
			circle.getPaint().setColor(
					getResources().getColor(R.color.top_dark_gray));
			tvBase.setText("--");
		} else {
			circle.getPaint().setColor(
					Helper.colorForScoreAndMean(
							taskScores.getAccuracyBaseline(),
							taskScores.getAccuracyMean()));
			tvBase.setText(""
					+ Math.round(taskScores.getAccuracyBaseline() * 100) + "%");
		}
		circle.setBounds(1, 1, 1, 1);
		tvBase.setBackgroundDrawable(circle);
		tvBase.setWidth(tvBase.getHeight());
		ShapeDrawable rec = new ShapeDrawable();
		if (taskScores == null || taskScores.getAccuracyAverage() == 0.0d) {
			rec.getPaint().setColor(
					getResources().getColor(R.color.top_dark_gray));
			tvCurr.setText("--");
		} else {
			rec.getPaint().setColor(
					Helper.colorForScoreAndMean(
							taskScores.getAccuracyAverage(),
							taskScores.getAccuracyMean()));
			tvCurr.setText(""
					+ Math.round(taskScores.getAccuracyAverage() * 100) + "%");
		}
		rec.setBounds(1, 1, 1, 1);
		tvCurr.setBackgroundDrawable(rec);

		ShapeDrawable rec1 = new ShapeDrawable();
		rec1.getPaint().setColor(Color.WHITE);
		tvLevel.setText("" + Constants.POPUP_TASKLEVEL);
		tvCount.setText("" + Constants.POPUP_TASKCOUNT);

		tvLevel.setBackgroundDrawable(rec1);
		tvCount.setBackgroundDrawable(rec1);

		androidAQuery.id(imgRes).progress(taskProgress)
				.image(imageUrl, false, false, 0, 0, new BitmapAjaxCallback() {
					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {
						imgRes.setImageBitmap(bm);
						imgRes.setScaleType(ScaleType.FIT_XY);
					}
				}.header("User-Agent", "android"));

		playLocalFile(R.raw.please_select_item_count_and_difficulty_for_task);

		imgclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mPlayerLocal != null) {
					if (mPlayerLocal.isPlaying()) {
						mPlayerLocal.stop();
						mPlayerLocal.release();
						mPlayerLocal = null;
					}
				}
				dialog.dismiss();
			}
		});

		llStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mPlayerLocal != null) {
					if (mPlayerLocal.isPlaying()) {
						mPlayerLocal.stop();
						mPlayerLocal.release();
						mPlayerLocal = null;
					}
				}

				// clearing cache

				try {
					Helper.trimCache(getApplicationContext());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (Helper.isMicrophoneTask(taskItem.getTypeId())
						&& !SpeechRecognizer
								.isRecognitionAvailable(getApplicationContext())) {
					dialog.dismiss();
					showVersionAlert("Oops!",
							getString(R.string.alert_microphone));

				} else {
					dialog.dismiss();
					callDeleteService();
					new Handler().postDelayed(new Runnable() {

						public void run() {
							Intent i = new Intent(getApplicationContext(),
									DoingTaskActivity.class);
							i.putExtra("displayname", taskItem.getDisplayName());
							if (CTUser.getInstance().isUserTypePatient())
								i.putExtra("patientId", ""+CTUser.getInstance().getUserId());
							else 
								i.putExtra("patientId", ""+CTUser.getInstance().getCurrentPatient().getPatientId());
							i.putExtra("taskLevel", tvLevel.getText()
									.toString());
							i.putExtra("taskCount", tvCount.getText()
									.toString());
							i.putExtra("typeId", taskItem.getTypeId());

							startActivity(i);

						}

					}, Constants.GLOW_ANIM_DURATION);

				}

			}
		});
		tvLevel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				groups.clear();
				for (int k = 1; k <= taskItem.getMaxLevel(); k++) {

					groups.add(String.valueOf(k));

				}

				showListWindow(v, groups);
			}
		});

		tvCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				tvCount.setText("");
				showGridWindow(root, v, tvCount);
			}
		});
		dialog.show();

		Window window = dialog.getWindow();
		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = findViewById(R.id.container).getWidth() + size.x;
		int height = findViewById(R.id.container).getHeight() + size.y;
		window.setLayout(width / 3, (int) (height / 2.5));

	}

	private void playLocalFile(int file) {
		if (mPlayerLocal != null) {
			if (mPlayerLocal.isPlaying()) {
				mPlayerLocal.stop();
				mPlayerLocal.release();
			}
		}

		mPlayerLocal = MediaPlayer.create(TasksActivity.this, file);
		mPlayerLocal.start();

	}
	
	/** handle situation where user has selected a different patient from the selector dropdown */
	protected void selectAndRefreshPatient(PatientModel selectedPatient) {
		
		CTUser.getInstance().setCurrentPatient(selectedPatient.getUsername());

		tvPatient.setText(CTUser.getInstance().getPatientUsernameForDisplay());

		callBaseLineService();
		loadingTaskStart();
		loadingHomeworkStart();

		if (animBlink != null) {
			animBlink.cancel();
			glowRegion.clearAnimation();
		}

	}

	/*
	 * Method for retrieving patient list from the database.
	 */

	private ArrayList<PatientModel> retrievePatientsList() {
		ArrayList<PatientModel> patientList = new ArrayList<PatientModel>();
		PatientModel model = null;
		Cursor cursor = this.getContentResolver().query(Patients.CONTENT_URI,
				null, null, null, Patients.DEFAULT_SORT);

		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					String id = cursor.getString(cursor
							.getColumnIndex(PatientsList.PATIENT_ID));
					String name = cursor.getString(cursor
							.getColumnIndex(PatientsList.USERNAME));
					String image = retrievePatientImagePath(cursor
							.getString(cursor
									.getColumnIndex(PatientsList.PATIENT_ID)));
					model = new PatientModel(name, image, id, null, "");
					patientList.add(model);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();

		return patientList;

	}

	/*
	 * Method for retrieving imagepath of selected patient from the database by
	 * passing the patient id.
	 */
	private String retrievePatientImagePath(String patientId) {
		String selection = PatientsList.PATIENT_ID + " = '" + (patientId) + "'";
		String imagePath = null;
		Cursor cursor = this.getContentResolver().query(
				PatientsList.CONTENT_URI, null, selection, null,
				PatientsList.DEFAULT_SORT);

		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {

				int index = cursor
						.getColumnIndexOrThrow(PatientsList.IMAGEPATH);
				imagePath = cursor.getString(index);

			}
		}
		cursor.close();

		return imagePath;

	}

	private void showBlink(View v) {
		animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);

		// set animation listener
		animBlink.setAnimationListener(TasksActivity.this);

		v.setAnimation(animBlink);
		v.startAnimation(animBlink);

	}

	private void callDeleteService() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_deletesince) + ""
				+ Helper.getCurrentTimeStamp();
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.DELETE_TOKEN, url);

	}

	private void reloadHierarchy(List<TasksHierarchy> hierarchyList,
			final TaskTypeScores scores) {
		loadHierarchy(hierarchyList, scores);
		expandTaskHierarchyView(nLevelItemlist);
		filterTaskHierarchy(etSearch.getText().toString());

	}

	private void loadHierarchy(List<TasksHierarchy> hierarchyList,
			final TaskTypeScores scores) {

		expandableList.setVisibility(View.VISIBLE);
		lvNoHierarchy.setVisibility(View.GONE);

		nLevelItemlist = new ArrayList<NLevelItem>();
		List<TasksHierarchy> parentList = new ArrayList<TasksHierarchy>();
		List<TasksHierarchy> childList = new ArrayList<TasksHierarchy>();
		List<TasksHierarchy> grandchildList = new ArrayList<TasksHierarchy>();

		final LayoutInflater inflater = LayoutInflater.from(this);
		for (int i = 0; i < hierarchyList.size(); i++) {
			parentList = hierarchyList.get(i).getTasksHierarchyLevel1();
			NLevelItem grandParent = new NLevelItem(hierarchyList.get(i), null,
					new NLevelView() {

						@Override
						public View getView(NLevelItem item) {
							View convertView = inflater.inflate(
									R.layout.tasks_custom_list, null);

							return setItems(convertView, item, scores);
						}
					});
			if (hierarchyList.get(i).getVisibility())
				nLevelItemlist.add(grandParent);

			for (int j = 0; j < parentList.size(); j++) {
				childList = parentList.get(j).getTasksHierarchyLevel1();
				NLevelItem parent = new NLevelItem(parentList.get(j),
						grandParent, new NLevelView() {

							@Override
							public View getView(NLevelItem item) {
								View convertView = inflater.inflate(
										R.layout.child_custom, null);

								return setItems(convertView, item, scores);
							}
						});
				if (parentList.get(j).getVisibility())
					nLevelItemlist.add(parent);

				for (int k = 0; k < childList.size(); k++) {
					grandchildList = childList.get(k).getTasksHierarchyLevel1();

					NLevelItem child1 = new NLevelItem(childList.get(k),
							parent, new NLevelView() {

								@Override
								public View getView(NLevelItem item) {
									View convertView = inflater.inflate(
											R.layout.grandchild_custom, null);

									return setItems(convertView, item, scores);
								}
							});
					if (childList.get(k).getVisibility())
						nLevelItemlist.add(child1);
					if (childList.get(k).getLevel() == 3) {
						for (int l = 0; l < grandchildList.size(); l++) {

							NLevelItem child2 = new NLevelItem(
									grandchildList.get(l), child1,
									new NLevelView() {

										@Override
										public View getView(NLevelItem item) {
											View convertView = inflater
													.inflate(
															R.layout.leastchild,
															null);

											return setItems(convertView, item,
													scores);
										}
									});
							if (grandchildList.get(l).getVisibility())
								nLevelItemlist.add(child2);
						}
					}

				}

			}

		}
		adapter = new NLevelAdapter(nLevelItemlist);
		expandableList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		if (adapter.getCount() != 0) {
			((NLevelAdapter) expandableList.getAdapter()).toggle(0);
			((NLevelAdapter) expandableList.getAdapter()).toggle(1);
			((NLevelAdapter) expandableList.getAdapter()).getFilter().filter();
		}

	}

	@SuppressWarnings("deprecation")
	private View setItems(View convertView, NLevelItem item,
			TaskTypeScores scores) {
		TasksType taskScores;
		ImageView img = (ImageView) convertView.findViewById(R.id.imageIcon);
		CTTextView title = (CTTextView) convertView.findViewById(R.id.tvItem);
		CTTextView tvLevel = (CTTextView) convertView
				.findViewById(R.id.tvLevel);
		CTTextView tvPercent = (CTTextView) convertView
				.findViewById(R.id.tvPercent);

		if (item.getWrappedObject().getLevel() != 0) {
			if (item.isExpanded()) {
				img.setImageResource(R.drawable.arrowright);
				img.setRotation(90);
			} else {
				img.setImageResource(R.drawable.arrowright);
				img.setRotation(0);
			}
		}
		title.setText(item.getWrappedObject().getDisplayName());
		taskScores = new TasksType();
		taskScores = Helper.getSystemname(scores, item.getWrappedObject()
				.getSystemName());

		ShapeDrawable circle = new ShapeDrawable(new OvalShape());

		if (taskScores == null || taskScores.getAccuracyBaseline() == 0.0d) {
			circle.getPaint().setColor(
					getResources().getColor(R.color.top_dark_gray));
			tvLevel.setText("--");
		} else {
			circle.getPaint().setColor(
					Helper.colorForScoreAndMean(
							taskScores.getAccuracyBaseline(),
							taskScores.getAccuracyMean()));
			tvLevel.setText(""
					+ Math.round(taskScores.getAccuracyBaseline() * 100) + "%");
		}
		circle.setBounds(1, 1, 1, 1);
		tvLevel.setBackgroundDrawable(circle);

		ShapeDrawable rec = new ShapeDrawable();
		if (taskScores == null || taskScores.getAccuracyAverage() == 0.0d) {
			rec.getPaint().setColor(
					getResources().getColor(R.color.top_dark_gray));
			tvPercent.setText("--");
		} else {
			rec.getPaint().setColor(
					Helper.colorForScoreAndMean(
							taskScores.getAccuracyAverage(),
							taskScores.getAccuracyMean()));
			tvPercent.setText(""
					+ Math.round(taskScores.getAccuracyAverage() * 100) + "%");
		}
		rec.setBounds(1, 1, 1, 1);
		tvPercent.setBackgroundDrawable(rec);

		return convertView;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void showPatientSettingDropDown(View v) {

		RelativeLayout rootView = (RelativeLayout) findViewById(R.id.container);

		popoverView = new PopoverView(this, R.layout.generalsetting, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = popoverView.getWidth() + size.x;
		int height = popoverView.getHeight() + size.y;

		popoverView.setContentSizeForViewInPopover(new Point(width / 3,
				(int) (height / 1.75)));
		popoverView.setDelegate(this);
		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(v),
				PopoverView.PopoverArrowDirectionUp, true);

		LinearLayout llUpdate = (LinearLayout) popoverView
				.findViewById(R.id.llUpdate);

		LinearLayout llMode = (LinearLayout) popoverView
				.findViewById(R.id.llMode);

		MySwitch mySwitch = (MySwitch) popoverView.findViewById(R.id.switch1);
		mySwitch.isOnorOff(isOnorOff);
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				isOnorOff = isChecked;
				Helper.setIsRight(TasksActivity.this, isChecked);

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
				
				updatePopup(CTUser.getInstance().getEmail(), CTUser.getInstance().getPhoneNumber(), CTUser.getInstance().getFirstName(), CTUser.getInstance().getLastName());
			}
		});

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

		Log.d(TAG, "onReceiveResult(resultCode=" + resultCode + ", resultData="
				+ resultData.toString());

		switch (resultCode) {
		case SyncService.STATUS_RUNNING:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN) {
				loadingHomeworkStart();
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN) {
				loadingTaskStart();
				loadingHomeworkStart();

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.DELETE_TOKEN) {
				// mProgressHUD = ProgressHUD.show(TasksActivity.this,
				// "Loading Items", true, true, this);
				// mProgressHUD.setCancelable(false);

			}
			break;
		case SyncService.STATUS_FINISHED:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN) {

				callBaseLineService();
				this.getLoaderManager().initLoader(
						Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN, null, this);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN) {
				this.getLoaderManager().initLoader(
						Constants.CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN, null,
						this);

				callHomeWorkService();

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_TASKS_HOMEWORK_TOKEN) {

				this.getLoaderManager().initLoader(
						Constants.CLINICIAN_TASKS_HOMEWORK_TOKEN, null, this);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN) {
				this.getLoaderManager().initLoader(
						Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN, null,
						this);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.DELETE_TOKEN) {
				// mProgressHUD.dismiss();
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

			}
			Log.v(TAG, "ENDS");
			break;
		case SyncService.STATUS_ERROR:
			if (resultData.isEmpty()) {
				if (dialog == null)
					showInternetAlert("Oops!", getString(R.string.no_internet));
				loadingTaskStop();
				loadingHomeworkStop();

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.INVALID_ACCESSTOKEN) {
				if (dialog != null)
					dialog.dismiss();
				clearDbAndSession();
				animatedStartActivity(false);

				loadingTaskStop();
				loadingHomeworkStop();
			}
			break;
		case SyncService.STATUS_NO_NETWORK:
			if (dialog == null || !dialog.isShowing())
				showInternetAlert("Oops!", getString(R.string.no_internet));

			break;
		}
	}

	@Override
	public void onCancel(DialogInterface d) {

		mProgressHUD.dismiss();
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
		try {
			Helper.trimCache(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		Log.v(TAG, "OnResume");
		if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {
			
			// may have switched patients if we went elsewhere to add patient
			// and then came back
			if (CTUser.getInstance().isUserTypeClinician())
				tvPatient.setText(CTUser.getInstance().getCurrentPatientUsername());
			
			loadingTaskStart();
			loadingHomeworkStart();
			fetchTaskHierarchy();

		} else {
			showInternetAlert("Oops!", getString(R.string.no_internet));

		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imgType:
			if (count % 2 != 0) {
				imgToggle.setImageResource(R.drawable.task_type_hierarchy);
				imgToggle.setTag("type");
				expandableList.setVisibility(View.VISIBLE);
				lvNoHierarchy.setVisibility(View.GONE);
				if (!isSearch) {
					if (nLevelItemlist != null) {
						adapter = new NLevelAdapter(nLevelItemlist);
						expandableList.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
				} else {
					filterTaskHierarchy(etSearch.getText().toString());
				}

			} else {
				imgToggle.setImageResource(R.drawable.task_type_no_hierarchy);
				imgToggle.setTag("no_type");
				if (!isSearch) {
					LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);
				} else if (tasksHierarchy != null
						&& tasksHierarchy.getTasksHierarchyLevel1().size() > 0) {
					listNoHierachy = combinedList(tasksHierarchy);
					expandableList.setVisibility(View.GONE);
					lvNoHierarchy.setVisibility(View.VISIBLE);
					filterNoHierarchy(etSearch.getText().toString());
				}
			}
			count++;
			break;
		case R.id.rlPatient:
			view = v;
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
		case R.id.btnSummary:

			if (dialog != null)
				dialog.dismiss();
			btnSummary.setBackground(getResources().getDrawable(
					R.drawable.summary_tab));
			Intent intent = new Intent(getApplicationContext(),
					ClinicianActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);

			overridePendingTransition(0, 0);
			mActivitySwitch.start();
			break;
		case R.id.btnRestore:
			if (homeworkAdapter != null) {
				if (animBlink != null)
					animBlink.cancel();
				glowRegion.clearAnimation();
				homeworkAdapter.clear();
				homeworkAdapter = null;
				callRestoreHomeWorkService();

			}

			break;
		case R.id.rlTaskList:
			if (imgBaseline.getVisibility() == View.VISIBLE
					|| imgCurrent.getVisibility() == View.VISIBLE) {
				imgBaseline.setVisibility(View.GONE);
				imgCurrent.setVisibility(View.GONE);
				sortCount = 0;
			}
			if (imgToggle.getTag().equals("type")) {
				sortTaskList(imgTasklist);
			} else if (imgToggle.getTag().equals("no_type")) {
				sortNoHierachyTaskList(imgTasklist);
			}

			break;
		case R.id.rlBaseLine:
			if (imgTasklist.getVisibility() == View.VISIBLE
					|| imgCurrent.getVisibility() == View.VISIBLE) {
				imgTasklist.setVisibility(View.GONE);
				imgCurrent.setVisibility(View.GONE);
				sortCount = 0;
			}
			if (imgToggle.getTag().equals("type")) {
				sortBaselineList(imgBaseline);
			} else if (imgToggle.getTag().equals("no_type")) {
				sortNoHierachyBaselineList(imgBaseline);
			}
			break;
		case R.id.rlCurrent:
			if (imgTasklist.getVisibility() == View.VISIBLE
					|| imgBaseline.getVisibility() == View.VISIBLE) {
				imgTasklist.setVisibility(View.GONE);
				imgBaseline.setVisibility(View.GONE);
				sortCount = 0;
			}
			if (imgToggle.getTag().equals("type")) {
				sortCurrentList(imgCurrent);
			} else if (imgToggle.getTag().equals("no_type")) {
				sortNoHierachyCurrentList(imgCurrent);
			}
			break;
		case R.id.btnSave:
			if (homeworkAdapter != null && homeworkAdapter.getCount() > 0) {
				if (animBlink != null)
					animBlink.cancel();
				glowRegion.clearAnimation();
				if (taskHomework == null)
					taskHomework = new Homework();
				taskHomework.setScheduledTaskTypes(homeworkAdapter
						.getListItem());
				String json = new GsonBuilder().create().toJson(taskHomework,
						Homework.class);
				callSaveService(json);
			}
			showToast();
			break;
		case R.id.ctIcon:
			if (dialog == null || !dialog.isShowing())
				AlertPopup.showVersionAlert(TasksActivity.this,
						getString(R.string.app_version), null);
			break;
		case R.id.linearLayout2:

			if (CTUser.getInstance().isUserTypePatient()) {

				Intent help = new Intent(getApplicationContext(),
						HelpActivity.class);
				help.putExtra("helpid", Constants.PATIENTLOGINTASK);
				startActivity(help);

			} else {
				Intent help = new Intent(getApplicationContext(),
						HelpActivity.class);
				help.putExtra("helpid", Constants.CLINICIANTASKS);
				startActivity(help);

			}
			break;
		default:
			break;
		}

	}

	private void removeItem(View v, final int which) {
		final LinearLayout back = (LinearLayout) v.findViewById(R.id.back);
		final LinearLayout front = (LinearLayout) v.findViewById(R.id.front);

		LinearLayout imag = (LinearLayout) v.findViewById(R.id.lvundo);

		front.setVisibility(View.GONE);
		back.setVisibility(View.VISIBLE);
		v.setVisibility(View.VISIBLE);
		homeworkAdapter.notifyDataSetChanged();

		TimerTask countdownTask = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						cancel();
						try {
							if (homeworkAdapter != null
									&& homeworkAdapter.getItem(which) != null) {
								homeworkAdapter.remove(homeworkAdapter
										.getItem(which));
								homeworkAdapter.notifyDataSetChanged();
								front.setVisibility(View.VISIBLE);
								back.setVisibility(View.GONE);
								showBlink(glowRegion);
							}
						} catch (IndexOutOfBoundsException e) {

							front.setVisibility(View.GONE);
							back.setVisibility(View.VISIBLE);
						}

					}
				});
			}
		};

		final Timer countdown = new Timer();
		countdown.schedule(countdownTask, Constants.REMOVEITEM_DURATION,
				2 * Constants.REMOVEITEM_DURATION);

		imag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				countdown.cancel();

				homeworkAdapter.notifyDataSetChanged();
				front.setVisibility(View.VISIBLE);
				back.setVisibility(View.GONE);

			}
		});

		Log.v("delete", "remove");
	}

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			ScheduledTaskTypes item = homeworkAdapter.getItem(from);

			homeworkAdapter.notifyDataSetChanged();
			homeworkAdapter.remove(item);
			homeworkAdapter.insert(item, to);
			showBlink(glowRegion);
			Log.v("drop", "drop");
		}

	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(final int which) {
			// homeworkAdapter.remove(homeworkAdapter.getItem(which));
			View v = lvHomework.getChildAt(which);
			if (v != null)
				removeItem(v, which);
		}

	};

	private DragSortListView.DragScrollProfile ssProfile = new DragSortListView.DragScrollProfile() {
		@Override
		public float getSpeed(float w, long t) {
			if (w > 0.8f) {
				// Traverse all views in a millisecond
				return ((float) homeworkAdapter.getCount()) / 0.001f;
			} else {
				return 10.0f * w;
			}
		}
	};

	/** implementing the drop listener */

	@Override
	public void popoverViewWillShow(PopoverView view) {

	}

	@Override
	public void popoverViewDidShow(PopoverView view) {
		// Auto-generated method stub

	}

	@Override
	public void popoverViewWillDismiss(PopoverView view) {

		if (isItemCount) {
			isItemCount = false;
			if (tvCount.getText().length() < 1) {

				ProcessKeypadInput("15", tvCount);

			}
		}

	}

	@Override
	public void popoverViewDidDismiss(PopoverView view) {
		// Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == animBlink) {
			glowRegion.clearAnimation();
			animation.cancel();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// Auto-generated method stub
		/*
		 * if (animation == animBlink) { showBlink(btnSave); }
		 */
	}

	protected class MyDragEventListener implements View.OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			final int action = event.getAction();

			switch (action) {
			case DragEvent.ACTION_DRAG_STARTED:

				return true; // reject

			case DragEvent.ACTION_DRAG_ENTERED:

				return true;
			case DragEvent.ACTION_DRAG_LOCATION:

				return true;
			case DragEvent.ACTION_DRAG_EXITED:

				return true;
			case DragEvent.ACTION_DROP:
				ClipData.Item item = event.getClipData().getItemAt(0);
				Log.v("description", "" + event.getClipDescription().getLabel());
				// If apply only if drop on buttonTarget
				if (v == lvHomework) {

					String[] droppedItem = item.getText().toString().split(",");
					ScheduledTaskTypes items = new ScheduledTaskTypes();

					items.setDisplayName(droppedItem[0]);
					items.setTaskTypeId(droppedItem[1]);
					items.setTaskLevel(Constants.TASKLEVEL);
					items.setTaskCount(Constants.TASKCOUNT);

					if (homeworkAdapter != null
							&& homeworkAdapter.getListItem() != null) {

						if (isExist(homeworkAdapter.getListItem(),
								items.getDisplayName())) {
							mTaskWarning.start();
						} else {
							mTaskAlert.start();
						}
					} else {
						mTaskAlert.start();
					}
					if (homeworkAdapter != null) {
						int pos = lvHomework.pointToPosition(
								(int) event.getX(), (int) event.getY());
						if (pos == -1) {
							pos = homeworkAdapter.getCount();
						}
						homeworkAdapter.insert(items, pos);

					} else {
						List<ScheduledTaskTypes> list = new ArrayList<ScheduledTaskTypes>();
						list.add(items);
						homeworkAdapter = new HomeworkAdapter<ScheduledTaskTypes>(
								TasksActivity.this, list);
						lvHomework.setAdapter(homeworkAdapter);
						tvNoData.setVisibility(View.GONE);
					}
					homeworkAdapter.notifyDataSetChanged();
					Log.v("drop", "sucess");
					mTaskAlert.start();
					showBlink(glowRegion);

					return true;
				} else {
					Log.v("drop", "not sucess");
					return false;
				}

			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {

				} else {

				}
				;
				return true;
			default: // unknown case

				return false;

			}
		}
	}

	private SwipeListener mSwipeListener = new SwipeListener() {
		ScheduledTaskTypes items;

		@Override
		public void onSwipeItem(boolean isRight, int pos) {

			if (imgToggle.getTag().equals("no_type")) {
				items = new ScheduledTaskTypes();
				if (leafAdapter != null && leafAdapter.getItem(pos) != null) {
					items.setDisplayName(leafAdapter.getItem(pos)
							.getDisplayName());
					items.setTaskTypeId(leafAdapter.getItem(pos).getTypeId());
					items.setTaskLevel(Constants.TASKLEVEL);
					items.setTaskCount(Constants.TASKCOUNT);

					if (homeworkAdapter != null
							&& homeworkAdapter.getListItem() != null) {
						if (isExist(homeworkAdapter.getListItem(),
								items.getDisplayName())) {
							mTaskWarning.start();
						} else {
							mTaskAlert.start();
						}
					} else {
						mTaskAlert.start();
					}
					if (homeworkAdapter != null) {
						homeworkAdapter.insert(items,
								homeworkAdapter.getCount());
					} else {
						List<ScheduledTaskTypes> list = new ArrayList<ScheduledTaskTypes>();
						list.add(items);
						homeworkAdapter = new HomeworkAdapter<ScheduledTaskTypes>(
								TasksActivity.this, list);
						lvHomework.setAdapter(homeworkAdapter);
						tvNoData.setVisibility(View.GONE);
					}

					homeworkAdapter.notifyDataSetChanged();
					Log.v("drop", "sucess");
					showBlink(glowRegion);
				}

			} else if (imgToggle.getTag().equals("type")
					&& adapter.getSelectedItem(pos).getLevel() == 0) {
				items = new ScheduledTaskTypes();

				items.setDisplayName(adapter.getSelectedItem(pos)
						.getDisplayName());
				items.setTaskTypeId(adapter.getSelectedItem(pos).getTypeId());
				items.setTaskLevel(Constants.TASKLEVEL);
				items.setTaskCount(Constants.TASKCOUNT);

				if (homeworkAdapter != null
						&& homeworkAdapter.getListItem() != null) {
					if (isExist(homeworkAdapter.getListItem(),
							items.getDisplayName())) {
						mTaskWarning.start();
					} else {
						mTaskAlert.start();
					}
				} else {
					mTaskAlert.start();
				}
				if (homeworkAdapter != null) {
					homeworkAdapter.insert(items, homeworkAdapter.getCount());
				} else {
					List<ScheduledTaskTypes> list = new ArrayList<ScheduledTaskTypes>();
					list.add(items);
					homeworkAdapter = new HomeworkAdapter<ScheduledTaskTypes>(
							TasksActivity.this, list);
					lvHomework.setAdapter(homeworkAdapter);
					tvNoData.setVisibility(View.GONE);
				}

				homeworkAdapter.notifyDataSetChanged();
				Log.v("drop", "sucess");
				showBlink(glowRegion);
			}

		}

		@Override
		public void onClickItem(int pos) {

			if (imgToggle.getTag().equals("type") && taskTypeScores != null) {

				if (adapter.getSelectedItem(pos).getLevel() == 0) {

					showTaskPopup(adapter.getSelectedItem(pos), taskTypeScores);
				} else {

					((NLevelAdapter) expandableList.getAdapter()).toggle(pos);
					((NLevelAdapter) expandableList.getAdapter()).getFilter()
							.filter();
				}

			} else if (imgToggle.getTag().equals("no_type")
					&& taskTypeScores != null) {
				showTaskPopup(leafAdapter.getItem(pos), taskTypeScores);
			}

		}

		@Override
		public void onLongClickItem(View v, int pos) {
			// Auto-generated method stub

		}

	};

	class MyTaskList implements Comparator<TasksHierarchy> {

		@Override
		public int compare(TasksHierarchy e1, TasksHierarchy e2) {
			if (e1.getDisplayName().equalsIgnoreCase(e2.getDisplayName())) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View v, int pos,
			long arg3) {

		if (imgToggle.getTag().equals("type")
				&& adapter.getSelectedItem(pos).getLevel() == 0) {
			selectedItem = adapter.getSelectedItem(pos);

			url = selectedItem.getResourceUrl();
			displayName = selectedItem.getDisplayName();
			typeId = selectedItem.getTypeId();
			ClipData.Item item = new ClipData.Item(displayName + "," + typeId);

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);
			if (v == null)
				v = expandableList.getChildAt(pos);
			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
			shadow.getView().setBackgroundColor(
					getResources().getColor(R.color.task_list_gray));
			v.startDrag(dragData, shadow, v, 0);
		} else if (imgToggle.getTag().equals("no_type")) {
			selectedItem = leafAdapter.getItem(pos);

			url = selectedItem.getResourceUrl();
			displayName = selectedItem.getDisplayName();
			typeId = selectedItem.getTypeId();
			ClipData.Item item = new ClipData.Item(displayName + "," + typeId);

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);
			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
			shadow.getView().setBackgroundColor(
					getResources().getColor(R.color.task_list_gray));
			v.startDrag(dragData, shadow, v, 0);
		}
		return false;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		switch (id) {
		case Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN:
			return new CursorLoader(this, TaskHierarchy.CONTENT_URI, null,
					null, null, TaskHierarchy.DEFAULT_SORT);

		case Constants.CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN:
			return new CursorLoader(this, TypeBaseline.CONTENT_URI, null, null,
					null, TypeBaseline.DEFAULT_SORT);

		case Constants.CLINICIAN_TASKS_HOMEWORK_TOKEN:

			return new CursorLoader(this, TaskHomework.CONTENT_URI, null, null,
					null, TaskHomework.DEFAULT_SORT);

		case Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN:

			return new CursorLoader(this, TaskHomework.CONTENT_URI, null, null,
					null, TaskHomework.DEFAULT_SORT);

		case Constants.UPDATE_ACCOUNT_INFO:
			return new CursorLoader(this, Users.CONTENT_URI, null, null, null,
					Users.DEFAULT_SORT);
		case Constants.VALIDATE_EMAIL_TOKEN:
			return new CursorLoader(this, Session.CONTENT_URI, null, null,
					null, Session.DEFAULT_SORT);
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {

		if (data.getCount() < 1) {

			return;
		}
		Log.v(TAG, "" + arg0.getId());
		switch (arg0.getId()) {

		case Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN: {

			data.moveToFirst();
			tasksHierarchy = new TasksHierarchy();
			String json = data.getString(data
					.getColumnIndex(TaskHierarchy.JSON));

			Gson gson = new Gson();
			tasksHierarchy = gson.fromJson(json, TasksHierarchy.class);
			this.getContentResolver().delete(TaskHierarchy.CONTENT_URI, null,
					null);

			break;
		}

		case Constants.CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN: {
			data.moveToFirst();
			String json = null;
			do {
				taskTypeScores = new TaskTypeScores();
				json = data.getString(data
						.getColumnIndex(TypeBaseline.SCORE_JSON));
			} while (data.moveToNext());
			Gson gson = new Gson();
			taskTypeScores = gson.fromJson(json, TaskTypeScores.class);
			if (tasksHierarchy != null)
				taskHierarchyList = tasksHierarchy.getTasksHierarchyLevel1();

			if (imgToggle.getTag().equals("type")
					&& taskHierarchyList.size() > 0) {
				if (!isSearch) {
					loadHierarchy(taskHierarchyList, taskTypeScores);
				} else {
					reloadHierarchy(taskHierarchyList, taskTypeScores);
				}

			} else if (imgToggle.getTag().equals("no_type")
					&& taskHierarchyList.size() > 0) {
				if (!isSearch) {
					LoadNoHierarchyItems(tasksHierarchy, taskTypeScores);
				} else {
					listNoHierachy = combinedList(tasksHierarchy);
					expandableList.setVisibility(View.GONE);
					lvNoHierarchy.setVisibility(View.VISIBLE);
					filterNoHierarchy(etSearch.getText().toString());
				}

			}
			getContentResolver().delete(TypeBaseline.CONTENT_URI, null, null);

			break;
		}

		case Constants.CLINICIAN_TASKS_HOMEWORK_TOKEN: {
			data.moveToFirst();
			Log.v(TAG, "home");
			taskHomework = new Homework();
			String json = data
					.getString(data.getColumnIndex(TaskHomework.JSON));

			Gson gson = new Gson();
			taskHomework = gson.fromJson(json, Homework.class);

			LoadHomeworkItems(taskHomework);

			getContentResolver().delete(TaskHomework.CONTENT_URI, null, null);
			loadingTaskStop();
			loadingHomeworkStop();

			break;
		}

		case Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN: {
			Log.v(TAG, "restore");
			data.moveToFirst();
			taskHomework = new Homework();
			String json = data
					.getString(data.getColumnIndex(TaskHomework.JSON));

			Gson gson = new Gson();
			taskHomework = gson.fromJson(json, Homework.class);
			LoadHomeworkItems(taskHomework);

			loadingHomeworkStop();
			break;
		}

		case Constants.UPDATE_ACCOUNT_INFO: {
			data.moveToFirst();
			// Patients = new

			String email = data.getString(data.getColumnIndex(Users.EMAIL));
			String pno = data.getString(data.getColumnIndex(Users.PHONENUMBER));
			String fname = data.getString(data.getColumnIndex(Users.FIRSTNAME));
			String lname = data.getString(data.getColumnIndex(Users.LASTNAME));
			
			// update the dialog that shows the info to the user
			//updatePopup(email, pno, fname, lname);
			
			// update our user model
			CTUser.getInstance().updateAccountInfo(email, pno, lname, fname);;
			
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

			}

		}

		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// Auto-generated method stub

	}
	
	

}
