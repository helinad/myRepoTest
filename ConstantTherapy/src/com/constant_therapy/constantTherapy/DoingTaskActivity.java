package com.constant_therapy.constantTherapy;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.constant_therapy.animation.ActivitySwitcher;
import com.constant_therapy.animation.AnimationFactory;
import com.constant_therapy.animation.AnimationFactory.FlipDirection;
import com.constant_therapy.charts.PieChart;
import com.constant_therapy.dashboard.ActiveItems;
import com.constant_therapy.dashboard.ActiveTask;
import com.constant_therapy.dashboard.ArithematicItems;
import com.constant_therapy.dashboard.ArithematicTasks;
import com.constant_therapy.dashboard.AuditoryItem;
import com.constant_therapy.dashboard.AuditoryTasks;
import com.constant_therapy.dashboard.CurrencyItem;
import com.constant_therapy.dashboard.CurrenyTask;
import com.constant_therapy.dashboard.DragTask;
import com.constant_therapy.dashboard.DragandDropTask;
import com.constant_therapy.dashboard.DraggingTask;
import com.constant_therapy.dashboard.LetterToSound;
import com.constant_therapy.dashboard.LetterToSoundTask;
import com.constant_therapy.dashboard.MatchingData;
import com.constant_therapy.dashboard.MatchingTask;
import com.constant_therapy.dashboard.NamingResponse;
import com.constant_therapy.dashboard.NamingTasks;
import com.constant_therapy.dashboard.NumberPattern;
import com.constant_therapy.dashboard.NumberPatternTasks;
import com.constant_therapy.dashboard.OrderItems;
import com.constant_therapy.dashboard.OrderTasks;
import com.constant_therapy.dashboard.PatternItems;
import com.constant_therapy.dashboard.PatternTasks;
import com.constant_therapy.dashboard.PlayTasks;
import com.constant_therapy.dashboard.PlayingData;
import com.constant_therapy.dashboard.PlayingTask;
import com.constant_therapy.dashboard.PostValues;
import com.constant_therapy.dashboard.SoundToLetter;
import com.constant_therapy.dashboard.SoundToLetterTask;
import com.constant_therapy.dashboard.SpokenWord;
import com.constant_therapy.dashboard.SpokenWordTask;
import com.constant_therapy.dashboard.SummaryResponse;
import com.constant_therapy.dashboard.SymbalTasks;
import com.constant_therapy.dashboard.SymbolItems;
import com.constant_therapy.dashboard.TaskResponse;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.dashboard.WrittenItem;
import com.constant_therapy.dashboard.WrittenTasks;
import com.constant_therapy.fragments.ActiveDragFragment;
import com.constant_therapy.fragments.ActiveDropFragment;
import com.constant_therapy.fragments.ArithmaticFragment;
import com.constant_therapy.fragments.AudioFragment;
import com.constant_therapy.fragments.AuditoryDragFragment;
import com.constant_therapy.fragments.AuditoryFragment;
import com.constant_therapy.fragments.ChoiceFragment;
import com.constant_therapy.fragments.CurrencyFragment;
import com.constant_therapy.fragments.CurrencyFragment.onCheckCurrencyClickListener;
import com.constant_therapy.fragments.DivisionFragment;
import com.constant_therapy.fragments.DragMatchingFragment;
import com.constant_therapy.fragments.DropFragment;
import com.constant_therapy.fragments.ImageAudioFragemt;
import com.constant_therapy.fragments.ImageChoiceFragment;
import com.constant_therapy.fragments.ImageFragment;
import com.constant_therapy.fragments.MatchingImageFragment;
import com.constant_therapy.fragments.MatchingWordFragment;
import com.constant_therapy.fragments.MicroAudioFragment;
import com.constant_therapy.fragments.MicroPhoneFragment;
import com.constant_therapy.fragments.NotepadFragment;
import com.constant_therapy.fragments.OrderDropFragment;
import com.constant_therapy.fragments.OrderingDragFragment;
import com.constant_therapy.fragments.PatternFragment;
import com.constant_therapy.fragments.PlayingCardFragment;
import com.constant_therapy.fragments.QuestionAudioFragment;
import com.constant_therapy.fragments.QuestionFragment;
import com.constant_therapy.fragments.RefreshFragment;
import com.constant_therapy.fragments.SequenceDrag;
import com.constant_therapy.fragments.SequenceDrag.OnSequenceDropListener;
import com.constant_therapy.fragments.SequenceDrop;
import com.constant_therapy.fragments.SpokenWordFragment;
import com.constant_therapy.fragments.SymbolMatchingFragment;
import com.constant_therapy.fragments.WordFragment;
import com.constant_therapy.fragments.WordProblemFragment;
import com.constant_therapy.fragments.WrittenDragFragment;
import com.constant_therapy.fragments.WrittenDropFragment;
import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.network.GPSTracker;
import com.constant_therapy.popup.AlertPopup;
import com.constant_therapy.popup.PopoverView;
import com.constant_therapy.provider.TherapyContract.Dashboards;
import com.constant_therapy.provider.TherapyContract.Patients;
import com.constant_therapy.provider.TherapyContract.PatientsList;
import com.constant_therapy.provider.TherapyContract.Session;
import com.constant_therapy.provider.TherapyContract.Task;
import com.constant_therapy.provider.TherapyContract.TaskHierarchy;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.Helper;
import com.constant_therapy.util.KeypadAdapter;
import com.constant_therapy.util.KeypadButton;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.widget.CTTextView;
import com.constant_therapy.widget.DrawView;
import com.constant_therapy.widget.ProgressHUD;
import com.constant_therapy.widget.ZoomableImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DoingTaskActivity extends BaseActivity implements
		MyResultReceiver.Receiver, LoaderManager.LoaderCallbacks<Cursor>,
		OnClickListener, ChoiceFragment.OnChoiceSelectedListener,
		ImageAudioFragemt.OnSimuliClickListener,
		QuestionAudioFragment.OnQuestionFrameClickListener,
		AudioFragment.OnAudioFrameClickListener, ProgressHUD.OnCancelListener,
		ImageFragment.OnImageClickListener,
		MicroPhoneFragment.OnMicroPhoneClickListener,
		QuestionFragment.OnInstructionClickListener,
		MatchingImageFragment.OnMatchingImageClickListener,
		MatchingWordFragment.OnMatchingWordClickListener,
		PlayingCardFragment.OnPlayCardClickListener,
		MicroAudioFragment.OnMicroAudioFrameClickListener,
		ArithmaticFragment.onCheckAnswerClickListener,
		DivisionFragment.onCheckDivisionAnswerClickListener,
		WordProblemFragment.onCheckWordProblemClickListener,
		WrittenDragFragment.OnWrittenDragListener,
		SymbolMatchingFragment.OnMatchingSymbolClickListener,
		DragMatchingFragment.OnMatchingDragListener,
		DropFragment.OnSoundClickListener,
		PatternFragment.OnPatternClickListener,
		RefreshFragment.OnRefreshClickListener,
		SpokenWordFragment.OnSpokenDragListener,
		ActiveDragFragment.OnActiveDragListener,
		OrderingDragFragment.OnOrderDragListener,
		AuditoryDragFragment.OnAuditoryDragListener, OnSequenceDropListener,
		onCheckCurrencyClickListener {

	private static final String TAG = "DoingTaskActivity";
	private MyResultReceiver mReceiver;

	public static Activity doingTask;

	SpeechRecognizer speechRecognizer;
	MyRecognitionListener listener;

	GPSTracker gps;

	Dialog dialog;
	Toast toast;
	ProgressHUD mProgressHUD;

	private AQuery androidAQuery;

	RelativeLayout rlHome, rlSkip;
	LinearLayout innerLayout;

	TaskResponse tasks = new TaskResponse();
	SummaryResponse summaryResponse;
	DragTask dragTask = new DragTask();
	MatchingTask matchingTasks = new MatchingTask();
	SymbolItems symbolTasks = new SymbolItems();
	LetterToSound letterToSoundTask = new LetterToSound();
	AuditoryItem auditoryTask = new AuditoryItem();
	PatternItems patternTask = new PatternItems();
	ActiveItems activeTask = new ActiveItems();
	OrderItems orderTask = new OrderItems();
	SoundToLetter soundToLetterTask = new SoundToLetter();
	SpokenWord spokenWordTask = new SpokenWord();
	NamingResponse microPhoneTask = new NamingResponse();
	PlayingTask playtask = new PlayingTask();
	DraggingTask taskDrag = new DraggingTask();
	CurrencyItem currencyTask = new CurrencyItem();

	ArithematicItems arithmaticTasks = new ArithematicItems();
	NumberPattern numberPatternTasks = new NumberPattern();
	WrittenItem writtenTasks = new WrittenItem();
	ArrayList<MatchingData> matchingDataList = new ArrayList<MatchingData>();

	public MyDragEventListener myDragEventListener = new MyDragEventListener();

	MicroPhoneFragment microphoneFrame;
	AudioFragment audioFrame;
	ImageAudioFragemt imageAudioFrame;
	DropFragment dropFrame;
	ImageFragment imageFrame;
	ChoiceFragment choiceFrame;
	ImageChoiceFragment imageChoiceFrame;
	FragmentTransaction transaction;
	QuestionAudioFragment questionAudioFrame;
	QuestionFragment questionFrame;
	MatchingImageFragment matchingImageFrame;
	MatchingWordFragment matchingWordFrame;
	PlayingCardFragment playingCardFrame;
	WordFragment wordFrame;
	MicroAudioFragment microAudioFragment;
	ArithmaticFragment arithmaticFragment;
	NotepadFragment notepadFragment;
	DivisionFragment divisionFrame;
	WordProblemFragment wordProblemFrame;
	WrittenDragFragment writtenDragFrame;
	WrittenDropFragment writtentDropFrame;
	SymbolMatchingFragment symbolMatchingFrame;
	DragMatchingFragment dragMatchingFrame;
	SpokenWordFragment spokenWordFrame;
	AuditoryDragFragment auditoryDragFrame;
	AuditoryFragment auditoryFrame;
	PatternFragment patternFrame;
	RefreshFragment refreshFrame;
	ActiveDragFragment activeDragFrame;
	ActiveDropFragment activeDropFrame;
	OrderDropFragment orderDropFrame;
	OrderingDragFragment orderDragFrame;
	SequenceDrag draganddropFragment;
	SequenceDrop sequencedropFragment;
	CurrencyFragment currencyFrame;

	PlayingData playingData;
	Tasks currentTask;
	SymbalTasks currentSymbolTask;

	JSONObject patternObject;
	JSONArray patternArray = new JSONArray();
	JSONArray actionArray = new JSONArray();
	JSONArray eventArray = new JSONArray();
	JSONArray playeventArray = new JSONArray();
	JSONObject playactionArray = new JSONObject();

	CTTextView tvTaskTitle;
	CTTextView tvQuestion;
	CTTextView tvBase;
	CTTextView tvPercent;

	public static Button btnStart;
	public static FrameLayout flWait;
	public static LinearLayout llMicrophone;
	public static GridView gridDrop, gridDrag;
	public static CTTextView tvCheckAnswer, tempDropTv, tvFirstDrag,
			tvSecondDrag;
	public static ImageView imgWrongSymbol;
	public static DrawView drawView;
	public static CTTextView etAnswerfirst, etAnswersecond;
	public static CTTextView etAnswer;
	public static String imgQuestion;
	public static View dragView, innerView;
	public static View dropView;
	public static ImageView imgFirstDrag, tempDropImg;
	public static ImageView imgSecondDrag;
	public static CTTextView tvChoice;

	Animation animation, animBlink;
	Animation shake;
	static Animation animZoomIn;
	ObjectAnimator oa;

	PieChart pg;

	Button btnNext;

	ProgressBar taskProgress;

	MediaPlayer mPlayerLocal, mPlayerLocal1;
	MediaPlayer mPlayerOnline;
	MediaRecorder myRecorder;
	String outputFile = null;

	View pView, child, choiceOverlay, taskOverlay;
	public static View playviewList;

	int progressStatus = 1, tempPos = 0, correctImageCount = 0,
			correctDropCount = 0, sequenceCount = 0;
	String displayName = null, taskLevel;
	String taskTypeId;
	String taskTypeCount;
	String tempView = null;
	static String patientId, userId;
	static long startTime, endTime;
	int childCount, accuracy = 0, currentPos = 0;
	static double latency = 0.000d;
	String currentAns;
	String name;

	public static ArrayList<CTTextView> viewList = new ArrayList<CTTextView>();
	public static ArrayList<CTTextView> answerDropList = new ArrayList<CTTextView>();
	public static ArrayList<ImageView> answerOrderDropList = new ArrayList<ImageView>();
	public static ArrayList<View> matchingList = new ArrayList<View>();
	public static ArrayList<View> pattenList = new ArrayList<View>();
	public static ArrayList<View> symbolList = new ArrayList<View>();
	public static ArrayList<String> removeOccurance = new ArrayList<String>();
	public static List<String> charItem = new ArrayList<String>();
	public static ArrayList<View> dragViewList = new ArrayList<View>();
	public static ArrayList<View> allDragViewList = new ArrayList<View>();
	public static ArrayList<View> dropViewList = new ArrayList<View>();
	public static ArrayList<CTTextView> dropSequenceList = new ArrayList<CTTextView>();
	ArrayList<PlayingData> playingDataList = new ArrayList<PlayingData>();
	List<Integer> pattenPostion = new ArrayList<Integer>();

	ArrayList<String> voiceOutput = new ArrayList<String>();
	ViewAnimator tempAnimator;
	ImageView tempImage;

	boolean viewClick = true, isWordProblem = false;
	boolean firstShow = true;

	boolean correct = false;
	boolean tapped = false;
	long timestamp;
	int playCorrectCount = 0;
	int playIncorrectCount = 0;
	int playTotalcount = 0;
	int symbolCorrectCount = 0;
	int repeatCount = 0, patternCorrectCount = 0, patternCurrentCount = 1;
	ArrayList<String> playAdditionlaData = new ArrayList<String>();
	boolean skip = false, skipDelay = false;
	private static ArrayList<String> skipView = new ArrayList<String>();
	public static ListView listDrag, listDrop;
	RelativeLayout rootView;

	GridView mKeypadGrid;
	KeypadAdapter mKeypadAdapter;
	PopoverView popoverView;
	String tempText = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayName = getIntent().getStringExtra("displayname");
		patientId = getIntent().getStringExtra("patientId");
		taskLevel = getIntent().getStringExtra("taskLevel");
		taskTypeCount = getIntent().getStringExtra("taskCount");
		taskTypeId = getIntent().getStringExtra("typeId");
		getContainer(taskTypeId);

		rlHome = (RelativeLayout) findViewById(R.id.rlHome);
		rlSkip = (RelativeLayout) findViewById(R.id.rlSkip);

		tvTaskTitle = (CTTextView) findViewById(R.id.tvTaskTitle);
		tvTaskTitle.setText(displayName);

		btnNext = (Button) findViewById(R.id.btnNext);
		taskProgress = (ProgressBar) findViewById(R.id.tasProgress);

		taskOverlay = (View) findViewById(R.id.view);

		choiceOverlay = (View) findViewById(R.id.choice_view);
		rootView = (RelativeLayout) findViewById(R.id.container);

		taskOverlay.setVisibility(View.VISIBLE);
		taskOverlay.setClickable(true);

		btnNext.setOnClickListener(this);
		rlHome.setOnClickListener(this);
		rlSkip.setOnClickListener(this);
		findViewById(R.id.ctIcon).setOnClickListener(this);

		registerReceiver();

		doingTask = this;
		userId = "" + CTUser.getInstance().getUserId();

		if (savedInstanceState != null)
			return;
		else {

			if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {

				callTaskService(displayName);

			} else {
				showInternetAlert("Oops!", getString(R.string.no_internet));

			}

		}

	}
	
	/*
	 * method for call webservice to get the task.
	 */

	private void callTaskService(String displayName) {
		String url = Helper.getUrl(DoingTaskActivity.this, displayName);

		if (url == null) {
			url = getString(R.string.remote_preurl)
					+ getString(R.string.remote_patient) + "/" + patientId
					+ getString(R.string.posturl_tasks);
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.DOING_TASKS_TOKEN, url, taskTypeId,
					taskTypeCount, taskLevel, getParameters());

		} else
			ServiceHelper.execute(getApplicationContext(), mReceiver,
					Constants.MULIT_TOKEN, url);
	}
	
	/*
	 * method for getting JSON which contains level and taskcount.
	 */

	private String getParameters() {
		String params = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("level", taskLevel);
			obj.put("taskCount", taskTypeCount);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params = obj.toString();

		return params;
	}
	
	/*
	 * method for creating Post response for pattern recreation task and Drag and drop task.
	 */

	private void createPatternResponse(String type) {
		patternObject = new JSONObject();

		try {
			patternObject.put("type", type);
			patternObject.put("timestamp",
					String.valueOf(Helper.getCurrentTimeStamp()));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		patternArray.put(patternObject);
	}

	/*
	 * method for creating Post action response for pattern recreation task and Drag and drop task.
	 */

	private void createActionAnswer(boolean wasClick, String audio,
			boolean isCorrect, int path) {

		JSONObject obj = new JSONObject();

		try {
			obj.put("wasClicked", wasClick);
			obj.put("value", audio);
			obj.put("correct", isCorrect);
			obj.put("placedSlotNumber", path);
			obj.put("timestamp", Helper.getCurrentTimeStamp());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		actionArray.put(obj);

	}
	
	/*
	 * This is the method for checking the audio which playing currently is correct.
	 */

	private boolean isCorrectAudio(String audio) {
		if (Helper.isLetterToSound(taskTypeId)) {
			if (letterToSoundTask.getTasks().get(progressStatus - 1)
					.getCorrectLetterSoundPath().equalsIgnoreCase(audio)) {
				return true;
			} else
				return false;
		} else if (Helper.isWrittenTask(taskTypeId)) {
			for (int i = 0; i < answerDropList.size(); i++) {
				if (answerDropList.get(i).getTag().toString()
						.equalsIgnoreCase(audio)) {
					return true;

				}
			}
		} else if (Helper.isOrderingTask(taskTypeId)) {
			if (taskTypeId.equalsIgnoreCase("20")) {
				for (int i = 0; i < answerDropList.size(); i++) {
					if (answerDropList.get(i).getTag().toString()
							.equalsIgnoreCase(audio)) {
						return true;

					}
				}
			} else {
				for (int i = 0; i < answerOrderDropList.size(); i++) {
					if (answerOrderDropList.get(i).getTag().toString()
							.equalsIgnoreCase(audio)) {
						return true;

					}
				}
			}
		} else if (Helper.isSpokenWordTask(taskTypeId)) {
			if (spokenWordTask.getTasks().get(progressStatus - 1)
					.getCorrectChoice().getImagePath().equalsIgnoreCase(audio)) {
				return true;

			}

		}

		return false;
	}
	
	/*
	 * method for getCurrent position audio of item
	 */

	private int getCurrentPos(String audio) {

		if (Helper.isLetterToSound(taskTypeId)) {
			if (letterToSoundTask.getTasks().get(progressStatus - 1)
					.getCorrectLetterSoundPath().equalsIgnoreCase(audio)) {
				return 0;
			} else
				return 1;
		} else if (Helper.isSoundToLetter(taskTypeId)) {
			if (soundToLetterTask.getTasks().get(progressStatus - 1)
					.getCorrectLetter().equalsIgnoreCase(audio)) {
				return 0;
			} else
				return 1;
		}
		return 0;
	}
	
	/*
	 * method for creating Post response for Drag and drop task.
	 */


	private void createEventAnswer(String audio, boolean isCorrect, int path) {

		JSONObject obj = new JSONObject();

		try {

			obj.put("type", "itemAttempt");
			obj.put("value", audio);
			obj.put("correct", isCorrect);
			obj.put("index", path);
			obj.put("timestamp", Helper.getCurrentTimeStamp());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eventArray.put(obj);

	}
	
	/*
	 * method for creating Post event response for Drag and drop task.
	 */


	private void createEventAnswer(String type) {

		JSONObject obj = new JSONObject();

		try {

			obj.put("type", type);
			obj.put("timestamp", Helper.getCurrentTimeStamp());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eventArray.put(obj);

	}

	/*
	 * method for creating Post response for Matching task.
	 */

	private void createMatchingAnswer(int path) {

		patternObject = new JSONObject();

		try {
			patternObject.put("timestamp",
					String.valueOf(Helper.getCurrentTimeStamp()));
			patternObject.put("path", path + 1);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		patternArray.put(patternObject);

	}

	/*
	 * method for creating Post event response for Drag and drop task - Written based Task.
	 */

	private void createWrittenAnswer() {

		JSONObject obj = new JSONObject();

		try {
			obj.put("timestamp", Helper.getCurrentTimeStamp());
			obj.put("type", "stimulusAudioRepeat");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eventArray.put(obj);

	}

	private void createPatternAnswer(String type, boolean correct, int pos) {
		patternObject = new JSONObject();

		try {
			patternObject.put("type", type);
			patternObject.put("timestamp",
					String.valueOf(Helper.getCurrentTimeStamp()));
			patternObject.put("correct", correct);
			patternObject.put("index", pos);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		patternArray.put(patternObject);

	}
/*
 * method for initializing the void recorder.
 */
	private void initializeRecorder() {

		speechRecognizer = SpeechRecognizer
				.createSpeechRecognizer(getApplicationContext());
		listener = new MyRecognitionListener();
		speechRecognizer.setRecognitionListener(listener);
	}
	/*
	 * method for start recording.
	 */

	public void startRecord() {
		Log.v(TAG, "Device" + Build.MODEL);
		if (SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {

			if (Build.MODEL.equalsIgnoreCase("GT-N8013")) {

			} else {
				speechRecognizer.startListening(RecognizerIntent
						.getVoiceDetailsIntent(getApplicationContext()));
			}

		}
	}
	/*
	 * method for stop recording.
	 */

	public void stopRecord() {
		if (SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
			speechRecognizer.stopListening();
			speechRecognizer.cancel();
			speechRecognizer = null;

		}
	}

	int audio = 0;

	/*
	 * method for playing audio sequentially
	 */
	private void playSequenceAnswer(final ArrayList<String> url) {

		String[] urls = new String[url.size()];
		for (int i = 0; i < url.size(); i++) {
			urls[i] = getString(R.string.resource_url) + url.get(i);
			Log.v(TAG, urls[i]);
		}
		if (mPlayerOnline != null) {
			if (mPlayerOnline.isPlaying()) {
				mPlayerOnline.stop();
				mPlayerOnline.release();
				mPlayerOnline = null;
			}
		}

		mPlayerOnline = new MediaPlayer();
		mPlayerOnline.setLooping(false);
		mPlayerOnline.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mPlayerOnline.setDataSource(urls[audio]);
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (SecurityException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			mPlayerOnline.prepare();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		}

		mPlayerOnline.start();
		mPlayerOnline.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if (audio < url.size() - 1) {
					audio++;
					playSequenceAnswer(url);

				} else {
					audio = 0;
				}
			}
		});

	}
	

	/*
	 * method for playing audio sequentially
	 */

	private void playSequenceAnswer(final int localUrl, final String url) {

		try {
			if (mPlayerLocal != null) {
				if (mPlayerLocal.isPlaying()) {
					mPlayerLocal.stop();
					mPlayerLocal.release();
				}
			}

			mPlayerLocal = MediaPlayer.create(DoingTaskActivity.this, localUrl);

			mPlayerLocal.start();
			playSound(url, true);
			mPlayerLocal.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					if (!mPlayerOnline.isPlaying())
						mPlayerOnline.start();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * method for playing audio sequentially
	 */
	
	private void playSequenceAnswer(int alert, final int localUrl,
			final String url) {

		try {
			if (mPlayerLocal != null) {
				if (mPlayerLocal.isPlaying()) {
					mPlayerLocal.stop();
					mPlayerLocal.release();
					mPlayerLocal = null;
				}
			}

			if (mPlayerLocal1 != null) {
				if (mPlayerLocal1.isPlaying()) {
					mPlayerLocal1.stop();
					mPlayerLocal1.release();
					mPlayerLocal1 = null;
				}
			}
			mPlayerLocal = MediaPlayer.create(DoingTaskActivity.this, alert);

			mPlayerLocal.start();
			playSound(url, true);
			mPlayerLocal.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// Log.v(TAG, "enter on complete" + url);
					mPlayerLocal1 = MediaPlayer.create(DoingTaskActivity.this,
							localUrl);
					mPlayerLocal1.start();
					mPlayerLocal1
							.setOnCompletionListener(new OnCompletionListener() {

								@Override
								public void onCompletion(MediaPlayer mp) {
									// TODO Auto-generated method stub
									if (!mPlayerOnline.isPlaying())
										mPlayerOnline.start();
								}
							});

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Boolean validationAudio(ArrayList<String> recorded, String original) {

		if (recorded.size() > 0) {
			if (recorded.contains(original)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	private void loadMicrophoneTask(NamingResponse matchingTask, int pos) {
		if (matchingTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			String url = getString(R.string.resource_url)
					+ microPhoneTask.getTasks().get(progressStatus - 1)
							.getItem().getSoundPath();
			startTime = Helper.getCurrentTimeStamp();
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						matchingTask.getTasks().get(pos).getInstructions());
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}
			if (findViewById(R.id.microphone_container) != null) {
				microphoneFrame = new MicroPhoneFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				microphoneFrame = MicroPhoneFragment.newInstance(5, "");
				transaction.add(R.id.microphone_container, microphoneFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.image_container) != null) {

				if (displayName.contains("Read")) {
					wordFrame = new WordFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					wordFrame = WordFragment.newInstance(matchingTask
							.getTasks().get(pos).getItem().getName());
					transaction.add(R.id.image_container, wordFrame);
					transaction.commit();
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							tvChoice.setTextColor(Color.BLACK);
						}
					}, 100);

				} else if (displayName.contains("Picture")) {
					imageFrame = new ImageFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					for (int i = 0; i < matchingTask.getTasks().get(pos)
							.getResources().size(); i++) {
						if (matchingTask.getTasks().get(pos).getResources()
								.get(i).endsWith(".jpg")) {
							imageFrame = ImageFragment.newInstance(5,
									matchingTask.getTasks().get(pos)
											.getResourceUrl()
											+ matchingTask.getTasks().get(pos)
													.getResources().get(i));
							transaction.add(R.id.image_container, imageFrame);
							transaction.commit();
						}
					}

				} else {
					microAudioFragment = new MicroAudioFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					microAudioFragment = MicroAudioFragment.newInstance(5,
							"audio");
					transaction.add(R.id.image_container, microAudioFragment);
					transaction.commit();

				}
			}

			if (displayName.contains("Read")) {

				playLocalFile(R.raw.please_read_aloud_the_word_that_you_see);

			} else if (displayName.contains("Picture")) {

				playLocalFile(R.raw.please_name_the_image_that_you_see);
			} else {

				playSequenceAnswer(R.raw.please_repeat_the_word_that_you_hear,
						url);

			}
		}

		voiceOutput.clear();

		initializeRecorder();

	}

	private void loadCurrencyTask(CurrencyItem currencyTask, int pos) {
		if (currencyTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			eventArray = new JSONArray();
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						currencyTask.getTasks().get(pos).getInstructions());
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.image_container) != null) {

				imageFrame = new ImageFragment();
				transaction = getSupportFragmentManager().beginTransaction();

				imageFrame = ImageFragment.newInstance(5,
						getString(R.string.resource_url)
								+ currencyTask.getTasks().get(pos)
										.getFilePath());
				transaction.add(R.id.image_container, imageFrame);
				transaction.commit();

			}

			if (findViewById(R.id.choice_container) != null) {
				currencyFrame = new CurrencyFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				currencyFrame = CurrencyFragment.newInstance(currencyTask
						.getTasks().get(pos).getText(), true);
				transaction.add(R.id.choice_container, currencyFrame);
				transaction.commit();
			}
		}
		playLocalFile(R.raw.how_much_money_is_displayed);
	}

	private void loadPatternTask(PatternItems patternTask, int pos) {
		if (patternTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			matchingList = new ArrayList<View>();
			pattenList = new ArrayList<View>();
			patternCurrentCount = 1;
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						"Please recreate the following pattern");
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.reload_container) != null) {

				refreshFrame = new RefreshFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.reload_container, refreshFrame);
				transaction.commit();

			}

			if (findViewById(R.id.grid_container) != null) {

				patternFrame = new PatternFragment();
				transaction = getSupportFragmentManager().beginTransaction();

				patternFrame = PatternFragment.newInstance("", patternTask
						.getTasks().get(pos));
				transaction.add(R.id.grid_container, patternFrame);
				transaction.commit();

			}
			playLocalFile(R.raw.please_recreate_the_following_pattern);

			for (int i = 0; i < patternTask.getTasks().get(pos).getActions()
					.size(); i++) {
				pattenPostion.add(patternTask.getTasks().get(pos).getActions()
						.get(i).getLocation());
			}
			Collections.sort(pattenPostion, new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					// TODO Auto-generated method stub
					return o1.compareTo(o2);
				}
			});
			repeatCount = 0;
			new Handler().postDelayed(new Runnable() {

				public void run() {

					List<ObjectAnimator> arrayListObjectAnimators = new ArrayList<ObjectAnimator>();
					for (int i = 0; i < pattenList.size(); i++) {
						for (int j = 0; j < pattenPostion.size(); j++) {
							if (pattenList.get(i).findViewById(R.id.dummyImage)
									.getTag() == pattenPostion.get(j)
									&& pattenList.get(i)
											.findViewById(R.id.taskWord)
											.getVisibility() != View.VISIBLE) {

								ObjectAnimator anim = ObjectAnimator
										.ofObject(
												pattenList.get(i),
												"backgroundColor",
												new ArgbEvaluator(),
												getResources().getColor(
														R.color.yellow),
												getResources().getColor(
														R.color.login_gray));
								arrayListObjectAnimators.add(anim);

							}
						}
					}
					innerView.setVisibility(View.VISIBLE);
					innerView.setClickable(true);
					viewGlow(arrayListObjectAnimators);

				}

			}, 300);
		}

	}

	private void viewGlow(final List<ObjectAnimator> arrayListObjectAnimators) {

		ObjectAnimator[] objectAnimators = arrayListObjectAnimators
				.toArray(new ObjectAnimator[arrayListObjectAnimators.size()]);
		final AnimatorSet animSetXY = new AnimatorSet();
		animSetXY.playSequentially(objectAnimators);
		animSetXY.setDuration(1000);
		animSetXY.start();
		animSetXY.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				innerView.setVisibility(View.GONE);
				innerView.setClickable(false);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				innerView.setVisibility(View.GONE);
				innerView.setClickable(false);
			}
		});
	}

	private void loadWordProblemTask(ArithematicItems matchingTask, int pos) {
		if (matchingTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						matchingTask.getTasks().get(pos).getText());
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.wordproblem_container) != null) {

				wordProblemFrame = new WordProblemFragment();
				transaction = getSupportFragmentManager().beginTransaction();

				wordProblemFrame = WordProblemFragment.newInstance("", false);
				transaction.add(R.id.wordproblem_container, wordProblemFrame);
				transaction.commit();
				if (taskTypeId.equalsIgnoreCase("45")) {
					playLocalFile(R.raw.please_find_the_missing_number_in_the_sequence_below);
				} else {
					playLocalFile(R.raw.please_solve_the_problem_below);
				}

			}
		}

	}

	private void replaceActiveTask(ActiveItems activeTask, int pos) {
		startTime = Helper.getCurrentTimeStamp();

		actionArray = new JSONArray();
		eventArray = new JSONArray();

		if (activeDropFrame != null) {

			activeDropFrame.updateTableView(activeTask.getTasks().get(pos));
		}

		if (activeDragFrame != null) {

			activeDragFrame.updateTableView(activeTask.getTasks().get(pos));
		}

		playLocalFile(R.raw.please_complete_the_sentence_below);
		new Handler().postDelayed(new Runnable() {

			public void run() {

				for (int i = 0; i < gridDrop.getChildCount(); i++) {
					View tempView = gridDrop.getChildAt(i);
					CTTextView tvTemp = (CTTextView) tempView
							.findViewById(R.id.taskWord);
					if (tvTemp.getVisibility() == View.INVISIBLE) {
						tvTemp.setVisibility(View.VISIBLE);
						animationZoomIn(tvTemp);
						tvTemp.setTag("done");
					}
					for (int j = 0; j < gridDrag.getChildCount(); j++) {
						View tempDragView = gridDrag.getChildAt(j);
						CTTextView tvDragTemp = (CTTextView) tempDragView
								.findViewById(R.id.taskWord);

						if (tvTemp
								.getText()
								.toString()
								.equalsIgnoreCase(
										tvDragTemp.getText().toString()) && tempDragView.getVisibility() == View.VISIBLE) {
							tempDragView.setVisibility(View.INVISIBLE);

						}
					}

					

				}

			}

		}, Constants.SKIP_DELAY);

	}

	private void replaceWrittenTask(WrittenItem writtentTask, int pos) {
		startTime = Helper.getCurrentTimeStamp();
		actionArray = new JSONArray();
		eventArray = new JSONArray();
		correctDropCount = 0;
		name = writtentTask.getTasks().get(pos).getItem().getName();

		if (writtenDragFrame != null) {

			writtenDragFrame.updateTableView(writtentTask.getTasks().get(pos));
		}

		if (writtentDropFrame != null) {

			writtentDropFrame.updateTableView(writtentTask.getTasks().get(pos));
		}

		if (findViewById(R.id.image_container) != null) {

			if (taskTypeId.equalsIgnoreCase("29")
					|| taskTypeId.equalsIgnoreCase("7")) {
				audioFrame = new AudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				audioFrame = AudioFragment.newInstance(5,
						getString(R.string.resource_url)
								+ writtentTask.getTasks().get(pos).getItem()
										.getSoundPath());
				transaction.add(R.id.image_container, audioFrame);
				transaction.commit();
				playSequenceAnswer(
						R.raw.please_spell_out_the_word_that_you_hear,
						getString(R.string.resource_url)
								+ writtentTask.getTasks().get(pos).getItem()
										.getSoundPath());
			} else if (taskTypeId.equalsIgnoreCase("28")
					|| taskTypeId.equalsIgnoreCase("6")) {
				imageAudioFrame
						.updateArticleView(getString(R.string.resource_url)
								+ writtentTask.getTasks().get(pos).getItem()
										.getImagePath());
				playLocalFile(R.raw.please_spell_out_the_word_associated_with_the_image_below);
			} else if (taskTypeId.equalsIgnoreCase("30")
					|| taskTypeId.equalsIgnoreCase("8")) {
				wordFrame.updateArticleView(writtentTask.getTasks().get(pos)
						.getItem().getName());
				playLocalFile(R.raw.please_copy_the_word_written_below);

			}

		}

		new Handler().postDelayed(new Runnable() {

			public void run() {

				for (int i = 0; i < gridDrop.getChildCount(); i++) {
					View tempView = gridDrop.getChildAt(i);
					CTTextView tvTemp = (CTTextView) tempView
							.findViewById(R.id.taskWord);

					for (int j = 0; j < gridDrag.getChildCount(); j++) {
						View tempDragView = gridDrag.getChildAt(j);
						CTTextView tvDragTemp = (CTTextView) tempDragView
								.findViewById(R.id.taskWord);
						String word = tvDragTemp.getText().toString();

						if (tvTemp.getText().toString().equalsIgnoreCase(word)
								&& getRepeatCountOfLetter(name, word) == 1) {
							tempDragView.setVisibility(View.INVISIBLE);

						} 
					}

					if (tvTemp.getVisibility() == View.INVISIBLE) {
						tvTemp.setVisibility(View.VISIBLE);
						animationZoomIn(tvTemp);
						tvTemp.setTag("done");
					}

				}

			}

		}, Constants.SKIP_DELAY);

	}

	private void loadWrittenTask(WrittenItem writtentTask, int pos) {
		if (writtentTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			answerDropList = new ArrayList<CTTextView>();
			dropViewList = new ArrayList<View>();
			dragViewList = new ArrayList<View>();
			allDragViewList = new ArrayList<View>();

			actionArray = new JSONArray();
			eventArray = new JSONArray();

			correctDropCount = 0;
			String title = null;

			name = writtentTask.getTasks().get(pos).getItem().getName();

			if (taskTypeId.equalsIgnoreCase("29")
					|| taskTypeId.equalsIgnoreCase("7")) {
				title = "Please spell out the word that you hear";
			} else if (taskTypeId.equalsIgnoreCase("28")
					|| taskTypeId.equalsIgnoreCase("6")) {
				title = "Please spell out the word associated with the image below";
			} else if (taskTypeId.equalsIgnoreCase("30")
					|| taskTypeId.equalsIgnoreCase("8")) {

				title = "Please copy the word written below";
			}

			if (findViewById(R.id.question_container) != null) {

				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment
						.newInstance(5, title);
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();

			}

			if (findViewById(R.id.written_drag_container) != null) {

				writtenDragFrame = new WrittenDragFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				writtenDragFrame = WrittenDragFragment.newInstance("",
						writtentTask.getTasks().get(pos));
				transaction.add(R.id.written_drag_container, writtenDragFrame);
				transaction.commit();
			}

			if (findViewById(R.id.written_drop_container) != null) {

				writtentDropFrame = new WrittenDropFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				writtentDropFrame = WrittenDropFragment.newInstance("",
						writtentTask.getTasks().get(pos));
				transaction.add(R.id.written_drop_container, writtentDropFrame);
				transaction.commit();
			}

			if (findViewById(R.id.image_container) != null) {

				if (taskTypeId.equalsIgnoreCase("29")
						|| taskTypeId.equalsIgnoreCase("7")) {
					audioFrame = new AudioFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					audioFrame = AudioFragment.newInstance(5,
							getString(R.string.resource_url)
									+ writtentTask.getTasks().get(pos)
											.getItem().getSoundPath());
					transaction.add(R.id.image_container, audioFrame);
					transaction.commit();

					playSequenceAnswer(
							R.raw.please_spell_out_the_word_that_you_hear,
							getString(R.string.resource_url)
									+ writtentTask.getTasks().get(pos)
											.getItem().getSoundPath());
				} else if (taskTypeId.equalsIgnoreCase("28")
						|| taskTypeId.equalsIgnoreCase("6")) {
					imageAudioFrame = new ImageAudioFragemt();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					imageAudioFrame = ImageAudioFragemt.newInstance(5,
							getString(R.string.resource_url)
									+ writtentTask.getTasks().get(pos)
											.getItem().getImagePath());
					transaction.add(R.id.image_container, imageAudioFrame);
					transaction.commit();
					playLocalFile(R.raw.please_spell_out_the_word_associated_with_the_image_below);
				} else if (taskTypeId.equalsIgnoreCase("30")
						|| taskTypeId.equalsIgnoreCase("8")) {
					wordFrame = new WordFragment();

					transaction = getSupportFragmentManager()
							.beginTransaction();
					wordFrame = WordFragment.newInstance(writtentTask
							.getTasks().get(pos).getItem().getName());
					transaction.add(R.id.image_container, wordFrame);
					transaction.commit();
					playLocalFile(R.raw.please_copy_the_word_written_below);

				}

			}

			new Handler().postDelayed(new Runnable() {

				public void run() {

					for (int i = 0; i < gridDrop.getChildCount(); i++) {
						View tempView = gridDrop.getChildAt(i);
						CTTextView tvTemp = (CTTextView) tempView
								.findViewById(R.id.taskWord);

						for (int j = 0; j < gridDrag.getChildCount(); j++) {
							View tempDragView = gridDrag.getChildAt(j);
							CTTextView tvDragTemp = (CTTextView) tempDragView
									.findViewById(R.id.taskWord);
							String word = tvDragTemp.getText().toString();
							if (tvTemp.getText().toString()
									.equalsIgnoreCase(word)
									&& getRepeatCountOfLetter(name, word) == 1) {
								tempDragView.setVisibility(View.INVISIBLE);

							} 
						}

						if (tvTemp.getVisibility() == View.INVISIBLE) {
							tvTemp.setVisibility(View.VISIBLE);
							animationZoomIn(tvTemp);
							tvTemp.setTag("done");
						}

					}

				}

			}, Constants.SKIP_DELAY);
		}

	}

	private int getRepeatCountOfLetter(String name, String letter) {
		char[] nameArray = name.toCharArray();
		int count = 0;
		for (int i = 0; i < nameArray.length; i++) {
			if (String.valueOf(nameArray[i]).equalsIgnoreCase(letter))
				count++;
		}

		return count;

	}

	private void loadNumberPatternTask(NumberPattern numberPatternTask, int pos) {
		if (numberPatternTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			Log.v(TAG, "" + startTime);
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						numberPatternTask.getTasks().get(pos).getPattern());
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.wordproblem_container) != null) {

				wordProblemFrame = new WordProblemFragment();
				transaction = getSupportFragmentManager().beginTransaction();

				wordProblemFrame = WordProblemFragment.newInstance("", true);
				transaction.add(R.id.wordproblem_container, wordProblemFrame);
				transaction.commit();

			}
			String url = numberPatternTask.getTasks().get(pos).getResourceUrl()
					+ numberPatternTask.getTasks().get(pos)
							.getInstructionAudioPaths().get(0);
			// playSound(url, false);
		}

	}

	private void replaceNumberPatternTask(NumberPattern numberPatternTask,
			int pos) {
		startTime = Helper.getCurrentTimeStamp();
		if (questionAudioFrame != null) {
			questionAudioFrame.updateArticleView(numberPatternTask.getTasks()
					.get(pos).getPattern());
		}

		String url = numberPatternTask.getTasks().get(pos).getResourceUrl()
				+ numberPatternTask.getTasks().get(pos)
						.getInstructionAudioPaths().get(0);
		// playSound(url, false);

	}

	private void replaceWordProblemTask(ArithematicItems matchingTask, int pos) {
		startTime = Helper.getCurrentTimeStamp();
		if (questionAudioFrame != null) {
			questionAudioFrame.updateArticleView(matchingTask.getTasks()
					.get(pos).getText());
		}

	}

	private void replaceCurrencyTask(CurrencyItem currencyTask, int pos) {
		startTime = Helper.getCurrentTimeStamp();
		if (questionAudioFrame != null) {
			questionAudioFrame.updateArticleView(currencyTask.getTasks()
					.get(pos).getInstructions());
		}

		if (imageFrame != null) {
			imageFrame.updateArticleView(getString(R.string.resource_url)
					+ currencyTask.getTasks().get(pos).getFilePath());
		}
		playLocalFile(R.raw.how_much_money_is_displayed);

	}

	private void playLocalFile(int file) {
		if (mPlayerLocal != null) {
			if (mPlayerLocal.isPlaying()) {
				mPlayerLocal.stop();
				mPlayerLocal.release();
				mPlayerLocal = null;
			}
		}

		if (mPlayerOnline != null) {
			if (mPlayerOnline.isPlaying()) {
				mPlayerOnline.stop();
				mPlayerOnline.release();
				mPlayerOnline = null;
			}
		}

		if (mPlayerLocal1 != null) {
			if (mPlayerLocal1.isPlaying()) {
				mPlayerLocal1.stop();
				mPlayerLocal1.release();
				mPlayerLocal1 = null;
			}
		}

		mPlayerLocal = MediaPlayer.create(DoingTaskActivity.this, file);
		mPlayerLocal.start();

	}

	private void loadSpokenWordTask(final SpokenWord spokenWordTask,
			final int pos) {
		if (spokenWordTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			matchingList = new ArrayList<View>();
			eventArray = new JSONArray();
			actionArray = new JSONArray();

			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						spokenWordTask.getTasks().get(pos).getInstructions());
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}
			if (findViewById(R.id.drop_container) != null) {
				if (taskTypeId.equalsIgnoreCase("42")) {
					dropFrame = new DropFragment(myDragEventListener);
					transaction = getSupportFragmentManager()
							.beginTransaction();
					dropFrame = DropFragment.newInstance(false, "",
							spokenWordTask.getTasks().get(pos)
									.getCorrectChoice().getImagePath());
					transaction.add(R.id.drop_container, dropFrame);

					transaction.commit();

				} else if (taskTypeId.equalsIgnoreCase("41")) {
					dropFrame = new DropFragment(myDragEventListener);
					transaction = getSupportFragmentManager()
							.beginTransaction();
					dropFrame = DropFragment.newInstance(true, spokenWordTask
							.getTasks().get(pos).getCorrectChoice().getName(),
							spokenWordTask.getTasks().get(pos)
									.getCorrectChoice().getImagePath());
					transaction.add(R.id.drop_container, dropFrame);

					transaction.commit();

				}
			}

			if (findViewById(R.id.drag_container) != null) {

				spokenWordFrame = new SpokenWordFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				spokenWordFrame = SpokenWordFragment.newInstance("",
						spokenWordTask.getTasks().get(pos));
				transaction.add(R.id.drag_container, spokenWordFrame, "");
				transaction.commit();

			}

			new Handler().postDelayed(new Runnable() {

				public void run() {
					if (taskTypeId.equalsIgnoreCase("42")) {
						playSequenceAnswer(
								R.raw.please_pick_the_correct_item_that_matches_the_sound,
								getString(R.string.resource_url)
										+ spokenWordTask.getTasks().get(pos)
												.getCorrectChoice()
												.getSoundPath());
					} else if (taskTypeId.equalsIgnoreCase("41")) {
						playLocalFile(R.raw.please_pick_the_correct_item_that_matches_the_text_shown);
					}
				}

			}, Constants.SKIP_DELAY / 2);
		}

	}

	private void replaceAuditoryTask(final AuditoryItem auditoryTask, int pos) {
		startTime = Helper.getCurrentTimeStamp();
		matchingList = new ArrayList<View>();
		dragViewList = new ArrayList<View>();
		eventArray = new JSONArray();
		actionArray = new JSONArray();

		if (auditoryFrame != null) {

			auditoryFrame.updateTableView(auditoryTask.getTasks().get(pos));

		}

		if (auditoryDragFrame != null) {

			auditoryDragFrame.updateTableView(auditoryTask.getTasks().get(pos));

		}
		
		new Handler().postDelayed(new Runnable() {

			public void run() {
				ArrayList<String> audioList = new ArrayList<String>();
				for(int i =0; i<auditoryTask.getTasks()
						.get(progressStatus - 1).getActions().size(); i++){
					audioList.addAll(auditoryTask.getTasks()
						.get(progressStatus - 1).getActions().get(i).getInstructionAudioPaths());
				}
				playSequenceAnswer(audioList);
			}

		}, Constants.SKIP_DELAY / 2);

	}

	private void loadAuditoryTask(final AuditoryItem auditoryTask, int pos) {
		if (auditoryTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			matchingList = new ArrayList<View>();
			dragViewList = new ArrayList<View>();
			eventArray = new JSONArray();
			actionArray = new JSONArray();

			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						"Please complete the following task");
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.audio_container) != null) {
				audioFrame = new AudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				audioFrame = AudioFragment.newInstance(5, "");
				transaction.add(R.id.audio_container, audioFrame);
				transaction.commit();
			}

			if (findViewById(R.id.auditory_container) != null) {

				auditoryFrame = new AuditoryFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				auditoryFrame = AuditoryFragment.newInstance("", auditoryTask
						.getTasks().get(pos));
				transaction.add(R.id.auditory_container, auditoryFrame, "");
				transaction.commit();

			}

			if (findViewById(R.id.drag_container) != null) {

				auditoryDragFrame = new AuditoryDragFragment(
						myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				auditoryDragFrame = AuditoryDragFragment.newInstance("",
						auditoryTask.getTasks().get(pos));
				transaction.add(R.id.drag_container, auditoryDragFrame, "");
				transaction.commit();

			}

			new Handler().postDelayed(new Runnable() {

				public void run() {
					ArrayList<String> audioList = new ArrayList<String>();
					for(int i =0; i<auditoryTask.getTasks()
							.get(progressStatus - 1).getActions().size(); i++){
						audioList.addAll(auditoryTask.getTasks()
							.get(progressStatus - 1).getActions().get(i).getInstructionAudioPaths());
					}
					playSequenceAnswer(audioList);
				}

			}, Constants.SKIP_DELAY / 2);
		}

	}

	private void loadActiveTask(ActiveItems activeTask, int pos) {
		if (activeTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			startTime = Helper.getCurrentTimeStamp();
			answerDropList = new ArrayList<CTTextView>();
			dropViewList = new ArrayList<View>();
			dragViewList = new ArrayList<View>();
			allDragViewList = new ArrayList<View>();

			actionArray = new JSONArray();
			eventArray = new JSONArray();
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						"Please complete the sentence below");
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}
			if (findViewById(R.id.drop_container) != null) {

				activeDropFrame = new ActiveDropFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				activeDropFrame = ActiveDropFragment.newInstance("", activeTask
						.getTasks().get(pos));
				transaction.add(R.id.drop_container, activeDropFrame);

				transaction.commit();

			}

			if (findViewById(R.id.drag_container) != null) {

				activeDragFrame = new ActiveDragFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				activeDragFrame = ActiveDragFragment.newInstance("", activeTask
						.getTasks().get(pos));
				transaction.add(R.id.drag_container, activeDragFrame, "");
				transaction.commit();

			}
			playLocalFile(R.raw.please_complete_the_sentence_below);

			new Handler().postDelayed(new Runnable() {

				public void run() {

					for (int i = 0; i < gridDrop.getChildCount(); i++) {
						View tempView = gridDrop.getChildAt(i);
						CTTextView tvTemp = (CTTextView) tempView
								.findViewById(R.id.taskWord);
						if (tvTemp.getVisibility() == View.INVISIBLE) {
							tvTemp.setVisibility(View.VISIBLE);
							animationZoomIn(tvTemp);
							tvTemp.setTag("done");
						}
						for (int j = 0; j < gridDrag.getChildCount(); j++) {
							View tempDragView = gridDrag.getChildAt(j);
							CTTextView tvDragTemp = (CTTextView) tempDragView
									.findViewById(R.id.taskWord);

							if (tvTemp
									.getText()
									.toString()
									.equalsIgnoreCase(
											tvDragTemp.getText().toString()) && tempDragView.getVisibility() == View.VISIBLE) {
								tempDragView.setVisibility(View.INVISIBLE);

							}
						}

					}

				}

			}, Constants.SKIP_DELAY);
		}
	}

	private void replaceOrderingTask(OrderItems orderTask, int pos) {
		startTime = Helper.getCurrentTimeStamp();
		correctDropCount = 0;
		actionArray = new JSONArray();
		eventArray = new JSONArray();
		boolean isWord = false;
		if (taskTypeId.equalsIgnoreCase("20")) {
			isWord = true;
		}

		if (orderDropFrame != null) {

			orderDropFrame.updateTableView(isWord, orderTask.getTasks()
					.get(pos));

		}

		if (orderDragFrame != null) {

			orderDragFrame.updateTableView(isWord, orderTask.getTasks()
					.get(pos));

		}
	}

	private void loadOrderingTask(OrderItems orderTask, int pos) {
		if (orderTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			answerDropList = new ArrayList<CTTextView>();
			answerOrderDropList = new ArrayList<ImageView>();
			dropViewList = new ArrayList<View>();
			dragViewList = new ArrayList<View>();
			allDragViewList = new ArrayList<View>();
			correctDropCount = 0;

			actionArray = new JSONArray();
			eventArray = new JSONArray();
			boolean isWord = false;
			if (taskTypeId.equalsIgnoreCase("20")) {
				isWord = true;
			}
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						"Please rearrange the words in alphabetical order");
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}
			if (findViewById(R.id.drop_container) != null) {

				orderDropFrame = new OrderDropFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				orderDropFrame = OrderDropFragment.newInstance(isWord,
						orderTask.getTasks().get(pos));
				transaction.add(R.id.drop_container, orderDropFrame);

				transaction.commit();

			}

			if (findViewById(R.id.drag_container) != null) {

				orderDragFrame = new OrderingDragFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				orderDragFrame = OrderingDragFragment.newInstance(isWord,
						orderTask.getTasks().get(pos));
				transaction.add(R.id.drag_container, orderDragFrame, "");
				transaction.commit();

			}
		}
		playLocalFile(R.raw.please_rearrange_the_words_in_alphabetical_order);

	}

	private void loadLetterToSoundTask(LetterToSound letterToSoundTask, int pos) {
		if (letterToSoundTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			eventArray = new JSONArray();
			actionArray = new JSONArray();
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						"Please pick the sound that matches the letter shown");
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}
			if (findViewById(R.id.drop_container) != null) {
				dropFrame = new DropFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				dropFrame = DropFragment.newInstance(true, letterToSoundTask
						.getTasks().get(pos).getLetter(), "");
				transaction.add(R.id.drop_container, dropFrame);

				transaction.commit();
			}

			if (findViewById(R.id.drag_container) != null) {

				dragMatchingFrame = new DragMatchingFragment(
						myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				dragMatchingFrame = DragMatchingFragment.newInstance(true, "",
						letterToSoundTask.getTasks().get(pos).getResources());
				transaction.add(R.id.drag_container, dragMatchingFrame, "");
				transaction.commit();

			}
			playLocalFile(R.raw.please_pick_the_correct_item_that_matches_the_text_shown);
		}

	}

	private void loadSoundToLetterTask(SoundToLetter SoundToLetterTask, int pos) {
		if (SoundToLetterTask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			eventArray = new JSONArray();
			actionArray = new JSONArray();
			if (findViewById(R.id.question_container) != null) {
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment
						.newInstance(5,
								"Please pick the correct letter that matches the sound shown");
				transaction.add(R.id.question_container, questionAudioFrame,
						dragTask.getInstructions());
				transaction.commit();
			}
			if (findViewById(R.id.drop_container) != null) {
				dropFrame = new DropFragment(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				dropFrame = DropFragment.newInstance(false, SoundToLetterTask
						.getTasks().get(pos).getDistractorLetter(), "");
				transaction.add(R.id.drop_container, dropFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.drag_container) != null) {

				dragMatchingFrame = new DragMatchingFragment(
						myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				dragMatchingFrame = DragMatchingFragment.newInstance(
						false,
						SoundToLetterTask.getTasks().get(pos)
								.getCorrectLetter()
								+ ","
								+ SoundToLetterTask.getTasks().get(pos)
										.getDistractorLetter(),
						(ArrayList<String>) SoundToLetterTask.getTasks()
								.get(pos).getResources());
				transaction.add(R.id.drag_container, dragMatchingFrame, "")
						.commit();

			}
			String url = getResources().getString(R.string.resource_url)
					+ SoundToLetterTask.getTasks().get(pos).getCorrectLetter();

			playSequenceAnswer(
					R.raw.please_pick_the_correct_item_that_matches_the_sound_shown,
					url);
		}

	}

	private void postPrefernceService(String json, String sessionId) {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + patientId
				+ getString(R.string.remote_session) + sessionId;
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.PREFERENCE_POST_TOKEN, url, json, true);
	}

	private void callPreferenceService() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_user) + "/" + userId
				+ getString(R.string.remote_preference)
				+ getString(R.string.api_version);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.PREFERENCE_TOKEN, url);

	}

	private void callPostDataService(String json) {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_patient) + "/" + patientId
				+ getString(R.string.remote_response);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.CLINICIAN_TASKS_SAVE_TOKEN, url, json, false);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getAdditionalDataJson() {
		String params = null;
		if (Helper.isMatching(taskTypeId)
				|| Helper.isSymbolMatching(taskTypeId)) {
			JSONArray events = new JSONArray();

			JSONObject obj = new JSONObject();

			Map mapObj = new LinkedHashMap();
			try {
				mapObj.put("clickPath", patternArray);
				mapObj.put("touchCount", new Integer(patternArray.length()));

				obj.put("events", events);
				obj.put("data", new JSONObject(mapObj));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			params = obj.toString();

		} else if (Helper.isPatternTask(taskTypeId)) {

			JSONArray actions = new JSONArray();

			JSONObject obj = new JSONObject();
			try {
				obj.put("events", patternArray);
				obj.put("actions", actions);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = obj.toString();

		} else if (Helper.isLetterToSound(taskTypeId)
				|| Helper.isSoundToLetter(taskTypeId)
				|| Helper.isWrittenTask(taskTypeId)
				|| Helper.isSpokenWordTask(taskTypeId)
				|| Helper.isActiveTask(taskTypeId)
				|| Helper.isOrderingTask(taskTypeId)
				|| Helper.isAuditory(taskTypeId)
				|| Helper.isDragandDrop(taskTypeId)) {

			JSONObject obj = new JSONObject();
			try {
				obj.put("actions", actionArray);
				obj.put("events", eventArray);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = obj.toString();

		} else if (Helper.isPlayingCards(taskTypeId)) {

			JSONArray events = new JSONArray();
			JSONObject obj = new JSONObject();
			JSONObject objAction = new JSONObject();
			try {

				objAction.put("actions", playactionArray);
				objAction.put("events", playeventArray);
				objAction.put("incorrectCount", playIncorrectCount);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = objAction.toString();
		} else if (Helper.isMicrophoneTask(taskTypeId)) {
			JSONArray events = new JSONArray();

			JSONObject obj = new JSONObject();
			try {
				obj.put("events", events);
				obj.put("NuanceResults", voiceOutput);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = obj.toString();
		} else if (Helper.isArithmetic(taskTypeId)
				|| Helper.isWordProblem(taskTypeId)) {
			JSONArray events = new JSONArray();

			JSONObject obj = new JSONObject();
			try {
				obj.put("events", events);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = obj.toString();
		} else if (Helper.isCurrency(taskTypeId)) {

			JSONObject obj = new JSONObject();
			try {
				obj.put("events", eventArray);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params = obj.toString();
		}

		return params;

	}

	private String getTaskJson(int pos) {
		String params = null;
		if (Helper.isMatching(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					matchingTasks.getTasks().get(pos), Tasks.class);

		} else if (Helper.isSymbolMatching(taskTypeId)) {
			params = new GsonBuilder().create().toJson(
					symbolTasks.getTasks().get(pos), SymbalTasks.class);
		} else if (Helper.isDragandDrop(taskTypeId)) {
			params = new GsonBuilder().create().toJson(
					taskDrag.getTasks().get(pos), DragandDropTask.class);
		} else if (Helper.isPatternTask(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					patternTask.getTasks().get(pos), PatternTasks.class);

		} else if (Helper.isWrittenTask(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					writtenTasks.getTasks().get(pos), WrittenTasks.class);

		} else if (Helper.isAuditory(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					auditoryTask.getTasks().get(pos), AuditoryTasks.class);

		} else if (Helper.isOrderingTask(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					orderTask.getTasks().get(pos), OrderTasks.class);

		} else if (Helper.isActiveTask(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					activeTask.getTasks().get(pos), ActiveTask.class);

		} else if (Helper.isSpokenWordTask(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					spokenWordTask.getTasks().get(pos), SpokenWordTask.class);

		} else if (Helper.isLetterToSound(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					letterToSoundTask.getTasks().get(pos),
					LetterToSoundTask.class);

		} else if (Helper.isSoundToLetter(taskTypeId)) {

			params = new GsonBuilder().create().toJson(
					soundToLetterTask.getTasks().get(pos),
					SoundToLetterTask.class);

		} else if (Helper.isPlayingCards(taskTypeId)) {
			params = new GsonBuilder().create().toJson(
					playtask.getTasks().get(pos), PlayTasks.class);

		} else if (Helper.isMicrophoneTask(taskTypeId)) {
			params = new GsonBuilder().create().toJson(
					microPhoneTask.getTasks().get(pos), NamingTasks.class);
		} else if (Helper.isCurrency(taskTypeId)) {
			params = new GsonBuilder().create().toJson(
					currencyTask.getTasks().get(pos), CurrenyTask.class);
		} else if (Helper.isArithmetic(taskTypeId)) {
			params = new GsonBuilder().create()
					.toJson(arithmaticTasks.getTasks().get(pos),
							ArithematicTasks.class);
		} else if (Helper.isWordProblem(taskTypeId)) {
			if (displayName.equalsIgnoreCase("Number Pattern")) {
				params = new GsonBuilder().create().toJson(
						numberPatternTasks.getTasks().get(pos),
						NumberPatternTasks.class);

			} else {
				params = new GsonBuilder().create().toJson(
						arithmaticTasks.getTasks().get(pos),
						ArithematicTasks.class);

			}

		}

		return params;

	}

	private String getTaskType(int pos) {
		String params = null;
		if (Helper.isMatching(taskTypeId)) {

			params = matchingTasks.getTasks().get(pos).getTaskType();

		} else if (Helper.isSymbolMatching(taskTypeId)) {
			params = symbolTasks.getTasks().get(pos).getTaskType();

		} else if (Helper.isPatternTask(taskTypeId)) {
			params = patternTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isLetterToSound(taskTypeId)) {
			params = letterToSoundTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isWrittenTask(taskTypeId)) {
			params = writtenTasks.getTasks().get(pos).getTaskType();

		} else if (Helper.isActiveTask(taskTypeId)) {
			params = activeTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isDragandDrop(taskTypeId)) {
			params = taskDrag.getTasks().get(pos).getTaskType();

		} else if (Helper.isOrderingTask(taskTypeId)) {
			params = orderTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isAuditory(taskTypeId)) {
			params = auditoryTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isSpokenWordTask(taskTypeId)) {
			params = spokenWordTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isSoundToLetter(taskTypeId)) {
			params = soundToLetterTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isCurrency(taskTypeId)) {
			params = currencyTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isPlayingCards(taskTypeId)) {
			params = playtask.getTasks().get(pos).getTaskType();

		} else if (Helper.isMicrophoneTask(taskTypeId)) {
			params = microPhoneTask.getTasks().get(pos).getTaskType();

		} else if (Helper.isArithmetic(taskTypeId)) {
			params = arithmaticTasks.getTasks().get(pos).getTaskType();

		} else if (Helper.isWordProblem(taskTypeId)) {
			if (displayName.equalsIgnoreCase("Number Pattern")) {
				params = numberPatternTasks.getTasks().get(pos).getTaskType();

			} else {
				params = arithmaticTasks.getTasks().get(pos).getTaskType();

			}

		}

		return params;

	}

	private String getResponseOfTask(String sessionId, int pos,
			boolean isSkipped) {
		// TODO Auto-generated method stub
		endTime = Helper.getCurrentTimeStamp();
		ArrayList<PostValues> postList = new ArrayList<PostValues>();
		gps = new GPSTracker(DoingTaskActivity.this);
		double latitude = 0.0d, longitude = 0.0d;
		// check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			// gps.showSettingsAlert();
		}
		long tempLatency = endTime - startTime;

		if (latency == 0.000d) {

			latency = tempLatency;

		} else {
			latency = latency + tempLatency;
			// latency = Double.valueOf(tempLatency);

		}
		DecimalFormat df = new DecimalFormat("#.###");
		String convertedJson = null;
		PostValues postValues = new PostValues();
		postValues.setSkipped(isSkipped);
		postValues.setClientHardwareType(Build.HARDWARE);
		postValues.setClientOSVersion(String.valueOf(Build.VERSION.SDK_INT));
		postValues.setAdditionalDataJson(getAdditionalDataJson());

		postValues.setSessionId(Integer.parseInt(sessionId));

		postValues.setClientVersionNumber(getString(R.string.api_version));

		postValues.setTaskJson(getTaskJson(pos));

		postValues.setLatitude(latitude);
		postValues.setLongitude(longitude);
		postValues.setAccuracy(accuracy);
		postValues.setLatency(Double.valueOf(df.format(latency / 10)));
		postValues.setTimestamp(Helper.getCurrentTimeStamp());
		postValues.setTaskType(getTaskType(pos));

		postValues.setStartTime(startTime);
		postValues.setPatientId(Integer.parseInt(patientId));
		postList.add(postValues);
		convertedJson = new GsonBuilder().create().toJson(postList,
				new TypeToken<ArrayList<PostValues>>() {
				}.getType());
		Log.i("Post Json", "" + convertedJson);

		return convertedJson;
	}

	public void loadPlayingCardTask(PlayingTask playtask, int playCardPos) {
		   skip=true;
		if (playtask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			patternArray = new JSONArray();
			if (findViewById(R.id.question_container) != null) {
				Log.v("matchfragment", "quesmatchfragment1");
				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						playtask.getTasks().get(0).getInstruction());
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}

			if (findViewById(R.id.playing_container) != null) {
				Log.v("playCardPos", "" + playCardPos);

				playingCardFrame = new PlayingCardFragment();
				transaction = getSupportFragmentManager().beginTransaction();

				playingCardFrame = PlayingCardFragment.newInstance("5",
						playtask.getTasks().get(playCardPos), displayName,
						playCardPos, taskLevel);
				;

				transaction.add(R.id.playing_container, playingCardFrame);
				transaction.commit();
			}
			playSound(playtask.getTasks().get(0).getResourceUrl()
					+ playtask.getTasks().get(0).getInstructionAudioPaths()
							.get(0), false);
		}

	}

	private void loadMatchingTask(MatchingTask matchingTasks, int pos) {
		if (matchingTasks.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			symbolCorrectCount = 0;
			matchingDataList = new ArrayList<MatchingData>();
			patternArray = new JSONArray();
			if (findViewById(R.id.matching_container) != null) {
				DoingTaskActivity.matchingList.clear();
				if (matchingTasks.getTasks().get(pos).getResources().size() == 0) {

					matchingWordFrame = new MatchingWordFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					matchingWordFrame = MatchingWordFragment.newInstance("5",
							matchingTasks.tasks.get(pos));
					transaction.add(R.id.matching_container, matchingWordFrame);
					transaction.commit();
				} else {
					matchingImageFrame = new MatchingImageFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					matchingImageFrame = MatchingImageFragment.newInstance("5",
							matchingTasks.tasks.get(pos));
					transaction
							.add(R.id.matching_container, matchingImageFrame);
					transaction.commit();
				}
				currentTask = matchingTasks.tasks.get(pos);
				correctImageCount = 0;
			}

			if (findViewById(R.id.question_container) != null) {

				questionFrame = new QuestionFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionFrame = QuestionFragment.newInstance(5,
						matchingTasks.tasks.get(pos).getInstruction());
				transaction.add(R.id.question_container, questionFrame,
						matchingTasks.tasks.get(pos).getInstruction());
				transaction.commit();

			}
			if (matchingTasks.getTasks().get(pos).getResources().size() == 0) {
				playLocalFile(R.raw.please_find_all_the_matching_words);
			} else if (matchingTasks.getTasks().get(pos).getResources().get(0)
					.endsWith(".jpg")) {
				playLocalFile(R.raw.please_find_all_the_matching_pictures);
			} else {
				playLocalFile(R.raw.please_find_all_the_matching_sounds);
			}
		}

	}

	private void loadSymbolMatchingTask(SymbolItems matchingTasks, int pos) {
		if (matchingTasks.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			symbolCorrectCount = 0;
			matchingDataList.clear();
			patternArray = new JSONArray();
			if (findViewById(R.id.matching_container) != null) {
				DoingTaskActivity.symbolList.clear();

				symbolMatchingFrame = new SymbolMatchingFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				symbolMatchingFrame = SymbolMatchingFragment.newInstance("5",
						matchingTasks.tasks.get(pos));
				transaction.add(R.id.matching_container, symbolMatchingFrame);
				transaction.commit();

				currentSymbolTask = matchingTasks.tasks.get(pos);
				correctImageCount = 0;
			}

			if (findViewById(R.id.question_container) != null) {

				questionFrame = new QuestionFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionFrame = QuestionFragment.newInstance(5,
						matchingTasks.tasks.get(pos).getInstruction());
				transaction.add(R.id.question_container, questionFrame,
						matchingTasks.tasks.get(pos).getInstruction());
				transaction.commit();

			}
			playLocalFile(R.raw.please_find_all_the_symbols_that_match_the_one_on_the_left);
		}

	}

	private void loadArithmaticTask(ArithematicItems arithmatiTasks, int pos) {
		if (arithmatiTasks.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {
			startTime = Helper.getCurrentTimeStamp();
			Log.v(TAG, "" + startTime);
			if (findViewById(R.id.arithmatic_container) != null) {
				if (displayName.equalsIgnoreCase("Division")) {
					divisionFrame = new DivisionFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					divisionFrame = DivisionFragment.newInstance(arithmatiTasks
							.getTasks().get(pos).getText(), "");
					transaction.add(R.id.arithmatic_container, divisionFrame);
					transaction.commit();

				} else {
					String symbol = null;
					arithmaticFragment = new ArithmaticFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					if (displayName.equalsIgnoreCase("Addition")) {
						symbol = "+";
					} else if (displayName.equalsIgnoreCase("Subtraction")) {
						symbol = "-";
					} else if (displayName.equalsIgnoreCase("Multiplication")) {
						symbol = "X";
					}
					arithmaticFragment = ArithmaticFragment.newInstance(
							arithmatiTasks.getTasks().get(pos).getText(),
							symbol);
					transaction.add(R.id.arithmatic_container,
							arithmaticFragment);
					transaction.commit();
				}

			}

			if (findViewById(R.id.question_container) != null) {

				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						"Please solve the problem below");
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();

			}

			if (findViewById(R.id.scrachpad_container) != null) {

				notepadFragment = new NotepadFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.add(R.id.scrachpad_container, notepadFragment);
				transaction.commit();

			}
			playLocalFile(R.raw.please_solve_the_problem_below);
		}

	}

	private void replaceDragandDrop(DraggingTask taskDrag2, int pos) {
		eventArray = new JSONArray();
		actionArray = new JSONArray();
		startTime = Helper.getCurrentTimeStamp();
		if (questionAudioFrame != null) {
			questionAudioFrame
					.updateArticleView(taskDrag.getTasks().get(pos).task);
		}
		// TODO Auto-generated method stub
		if (draganddropFragment != null) {

			draganddropFragment
					.updateTableView(taskDrag.getTasks().get(pos).sequence);
		}

		if (sequencedropFragment != null) {

			sequencedropFragment
					.updateTableView(taskDrag.getTasks().get(pos).sequence);
		}
		playLocalFile(R.raw.please_arrange_the_steps_in_the_correct_sequence);
	}

	public void loadDragandDrop(DraggingTask dragtask, int pos) {
		eventArray = new JSONArray();
		actionArray = new JSONArray();
		startTime = Helper.getCurrentTimeStamp();
		if (dragtask.getTasks().size() == 0) {
			showErrorAlert("Error loading Tasks");
		} else {

			if (findViewById(R.id.question_container) != null) {

				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5,
						dragtask.getTasks().get(pos).task);
				transaction
						.add(R.id.question_container, questionAudioFrame, "");
				transaction.commit();
			}
			if (findViewById(R.id.sequence_drop) != null) {

				sequencedropFragment = new SequenceDrop(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				sequencedropFragment = SequenceDrop.newInstance(dragtask, pos);
				transaction.add(R.id.sequence_drop, sequencedropFragment);
				transaction.commit();
			}
			if (findViewById(R.id.sequence_drag) != null) {

				draganddropFragment = new SequenceDrag(myDragEventListener);
				transaction = getSupportFragmentManager().beginTransaction();
				draganddropFragment = SequenceDrag.newInstance(dragtask, pos);
				transaction.add(R.id.sequence_drag, draganddropFragment);
				transaction.commit();
			}
			playLocalFile(R.raw.please_arrange_the_steps_in_the_correct_sequence);
		}

	}

	private void relpaceArithmaticTask(ArithematicItems arithmatiTasks, int pos) {
		startTime = Helper.getCurrentTimeStamp();
		// if (findViewById(R.id.arithmatic_container) != null) {
		if (displayName.equalsIgnoreCase("Division")) {
			divisionFrame.updateArithmaticView(
					arithmatiTasks.getTasks().get(pos).getText(), "");

		} else {
			String symbol = null;

			if (displayName.equalsIgnoreCase("Addition")) {
				symbol = "+";
			} else if (displayName.equalsIgnoreCase("Subtraction")) {
				symbol = "-";
			} else if (displayName.equalsIgnoreCase("Multiplication")) {
				symbol = "X";
			}
			arithmaticFragment.updateArithmaticView(arithmatiTasks.getTasks()
					.get(pos).getText(), symbol);
		}

		// }

		playLocalFile(R.raw.please_solve_the_problem_below);

	}

	private void loadMultiTask(TaskResponse tasks) {
		startTime = Helper.getCurrentTimeStamp();
		if (tasks.getTasks().size() == 0 || taskTypeId.equalsIgnoreCase("36")
				|| taskTypeId.equalsIgnoreCase("18")
				|| taskTypeId.equalsIgnoreCase("54")
				|| taskTypeId.equalsIgnoreCase("48")

				|| taskTypeId.equalsIgnoreCase("49")) {
			showErrorAlert("Error loading Tasks");

		} else {
			if (findViewById(R.id.image_container) != null) {

				if (tasks.getTasks().get(0).getsImuli().get(0)
						.getOtherAspects().getImage().equalsIgnoreCase("")) {
					audioFrame = new AudioFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					audioFrame = AudioFragment.newInstance(5, "audio");
					transaction.add(R.id.image_container, audioFrame);
					transaction.commit();
				} else if (tasks.getTasks().get(0).getsImuli().get(0)
						.getOtherAspects().getAudio().equalsIgnoreCase("")) {
					imageFrame = new ImageFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					imageFrame = ImageFragment.newInstance(5,
							tasks.getUrlPrex()
									+ tasks.getTasks().get(0).getsImuli()
											.get(0).getOtherAspects()
											.getImage());
					transaction.add(R.id.image_container, imageFrame);
					transaction.commit();
				} else {
					imageAudioFrame = new ImageAudioFragemt();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					imageAudioFrame = ImageAudioFragemt.newInstance(5,
							tasks.getUrlPrex()
									+ tasks.getTasks().get(0).getsImuli()
											.get(0).getOtherAspects()
											.getImage());
					transaction.add(R.id.image_container, imageAudioFrame);
					transaction.commit();
				}

			}

			if (findViewById(R.id.question_container) != null) {

				questionAudioFrame = new QuestionAudioFragment();
				transaction = getSupportFragmentManager().beginTransaction();
				questionAudioFrame = QuestionAudioFragment.newInstance(5, tasks
						.getTasks().get(0).getInstrucIons().getPrimaryAspect()
						.getText());
				transaction.add(R.id.question_container, questionAudioFrame,
						tasks.getTasks().get(0).getInstrucIons()
								.getPrimaryAspect().getText());
				transaction.commit();

			}

			if (findViewById(R.id.choice_container) != null) {

				if (tasks.getTasks().get(0).getResponse().getChoices().get(0)
						.getPrimaryAspect() == null) {

					choiceFrame = new ChoiceFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					choiceFrame = ChoiceFragment.newInstance(tasks.getTasks()
							.get(0).getResponse().getChoices().get(0)
							.getIsCorrect(), "Yes");
					transaction.add(R.id.choice_container, choiceFrame, "Yes")
							.commit();

					choiceFrame = new ChoiceFragment();
					transaction = getSupportFragmentManager()
							.beginTransaction();
					choiceFrame = ChoiceFragment.newInstance(tasks.getTasks()
							.get(0).getResponse().getChoices().get(0)
							.getIsCorrect(), "No");
					transaction.add(R.id.choice_container, choiceFrame, "No")
							.commit();
				} else {
					for (int i = 0; i < tasks.getTasks().get(0).getResponse()
							.getChoices().size(); i++) {
						choiceFrame = new ChoiceFragment();
						transaction = getSupportFragmentManager()
								.beginTransaction();
						choiceFrame = ChoiceFragment.newInstance(tasks
								.getTasks().get(0).getResponse().getChoices()
								.get(i).getIsCorrect(), tasks.getTasks().get(0)
								.getResponse().getChoices().get(i)
								.getPrimaryAspect().getText());
						transaction.add(
								R.id.choice_container,
								choiceFrame,
								tasks.getTasks().get(0).getResponse()
										.getChoices().get(i).getPrimaryAspect()
										.getText()).commit();
					}
				}
			}
		}

	}

	private void stopAnim() {
		flWait.setVisibility(View.INVISIBLE);
		animation.cancel();
		flWait.clearAnimation();
	}

	private void skipSequenceTask() {
		accuracy = 0;
		endTime = Helper.getCurrentTimeStamp();
		sequenceCount = 0;
		for (int i = 0; i < listDrop.getChildCount(); i++) {
			View tempView = listDrop.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);
			if (!tvTemp.getTag().toString().equalsIgnoreCase("done")) {
				tvTemp.setText(tvTemp.getTag().toString());
				tvTemp.setTag("done");
				tvTemp.setTextColor(Color.BLACK);
				tvTemp.setGravity(Gravity.CENTER);
				tvTemp.setTextColor(Color.parseColor("#000000"));

				tvTemp.setBackground(getResources().getDrawable(
						R.drawable.black_border));

				animationZoomIn(tvTemp);
			}

		}

		for (int i = 0; i < dragViewList.size(); i++) {
			dragViewList.get(i).findViewById(R.id.taskWord)
					.setVisibility(View.INVISIBLE);
		}
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
	
		callPostDataService(getResponseOfTask(
				taskDrag.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));

		if (progressStatus < taskDrag.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void playSound(String url, Boolean isSequence) {

		if (mPlayerOnline != null) {
			if (mPlayerOnline.isPlaying()) {
				mPlayerOnline.stop();
				mPlayerOnline.release();
				mPlayerOnline = null;
			}
		}

		mPlayerOnline = new MediaPlayer();
		mPlayerOnline.setLooping(false);
		mPlayerOnline.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mPlayerOnline.setDataSource(url);
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (SecurityException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			mPlayerOnline.prepare();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		}
		if (!isSequence) {
			if (!mPlayerOnline.isPlaying())
				mPlayerOnline.start();
		}
	}

	private void validationLetterToSound(String curAns) {

		if (letterToSoundTask.getTasks().get(progressStatus - 1)
				.getCorrectLetterSoundPath().equalsIgnoreCase(curAns)) {

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			accuracy = 1;
			tempDropImg.setVisibility(View.VISIBLE);
			animationZoomIn(tempDropImg);
			imgSecondDrag.clearAnimation();
			imgFirstDrag.clearAnimation();
			imgSecondDrag.setVisibility(View.INVISIBLE);
			imgFirstDrag.setVisibility(View.INVISIBLE);
			createEventAnswer(curAns, true, 0);
		} else {
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
			tempDropImg.setVisibility(View.VISIBLE);
			animationZoomIn(tempDropImg);
			imgSecondDrag.clearAnimation();
			imgSecondDrag.setVisibility(View.INVISIBLE);
			imgFirstDrag.setVisibility(View.INVISIBLE);
			createEventAnswer(curAns, false, 1);
		}

		endTime = Helper.getCurrentTimeStamp();

		callPostDataService(getResponseOfTask(
				letterToSoundTask.getTasks().get(progressStatus - 1)
						.getSessionId(), progressStatus - 1, false));

		if (progressStatus < letterToSoundTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void validationSpokenWord(String curAns) {
		if (spokenWordTask.getTasks().get(progressStatus - 1)
				.getCorrectChoice().getImagePath().equalsIgnoreCase(curAns)) {
			tempDropImg.setVisibility(View.VISIBLE);
			tempDropImg.setBackgroundColor(Color.WHITE);
			animationZoomIn(tempDropImg);

			createEventAnswer(currentAns.replace(".jpg", ""), true, currentPos);
			createActionAnswer(false, currentAns.replace(".jpg", ""), true,
					currentPos);
			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			accuracy = 1;

		} else {
			createActionAnswer(false, currentAns.replace(".jpg", ""), false,
					currentPos);
			createEventAnswer(currentAns.replace(".jpg", ""), false, currentPos);
			dragView.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dragView.setVisibility(View.VISIBLE);
				}

			});

			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;

		}

		if (accuracy == 1) {

			for (int i = 0; i < gridDrag.getChildCount(); i++) {
				View view = gridDrag.getChildAt(i);
				ImageView tempView = (ImageView) view
						.findViewById(R.id.taskImage);
				tempView.setVisibility(View.INVISIBLE);

			}

			endTime = Helper.getCurrentTimeStamp();

			callPostDataService(getResponseOfTask(spokenWordTask.getTasks()
					.get(progressStatus - 1).getSessionId(),
					progressStatus - 1, false));

			if (progressStatus < spokenWordTask.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				progressStatus++;
			}
			btnNext.setVisibility(View.VISIBLE);
		}
	}

	private void validationSoundToLetter(String curAns) {

		if (soundToLetterTask.getTasks().get(progressStatus - 1)
				.getCorrectLetter().equalsIgnoreCase(curAns)) {

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			accuracy = 1;
			tempDropTv.setVisibility(View.VISIBLE);
			tempDropTv.setText(currentAns);
			animationZoomIn(tempDropTv);
			tvSecondDrag.clearAnimation();
			tvFirstDrag.clearAnimation();
			tvSecondDrag.setVisibility(View.INVISIBLE);
			tvFirstDrag.setVisibility(View.INVISIBLE);
			createEventAnswer(curAns, true, 0);
		} else {
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
			tempDropTv.setVisibility(View.VISIBLE);
			tempDropTv.setText(soundToLetterTask.getTasks()
					.get(progressStatus - 1).getCorrectLetter());
			animationZoomIn(tempDropTv);
			tvSecondDrag.clearAnimation();
			tvFirstDrag.clearAnimation();
			tvSecondDrag.setVisibility(View.INVISIBLE);
			tvFirstDrag.setVisibility(View.INVISIBLE);
			createEventAnswer(curAns, false, 1);
		}

		endTime = Helper.getCurrentTimeStamp();

		callPostDataService(getResponseOfTask(
				soundToLetterTask.getTasks().get(progressStatus - 1)
						.getSessionId(), progressStatus - 1, false));

		if (progressStatus < soundToLetterTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipWrittenTask() {

		endTime = Helper.getCurrentTimeStamp();
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);

		for (int i = 0; i < gridDrop.getChildCount(); i++) {
			View tempView = gridDrop.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);
			if (!tvTemp.getTag().toString().equalsIgnoreCase("done")) {
				tvTemp.setText(tvTemp.getTag().toString());
				tvTemp.setTag("done");
				tvTemp.setBackground(getResources().getDrawable(
						R.drawable.bg_sandal));
				animationZoomIn(tvTemp);
			}

		}

		for (int i = 0; i < gridDrag.getChildCount(); i++) {
			View tempView = gridDrag.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);

			tvTemp.setVisibility(View.INVISIBLE);

		}
		playLocalFile(R.raw.wrong_answer);
		accuracy = 0;
		callPostDataService(getResponseOfTask(
				writtenTasks.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));

		if (progressStatus < writtenTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipOrderTask() {
		endTime = Helper.getCurrentTimeStamp();

		if (taskTypeId.equalsIgnoreCase("20")) {

			for (int i = 0; i < gridDrop.getChildCount(); i++) {
				View tempView = gridDrop.getChildAt(i);
				CTTextView tvTemp = (CTTextView) tempView
						.findViewById(R.id.taskWord);
				if (!tvTemp.getTag().toString().equalsIgnoreCase("done")) {
					tvTemp.setText(tvTemp.getTag().toString());
					tvTemp.setTag("done");
					tvTemp.setBackground(getResources().getDrawable(
							R.drawable.black_border));
					animationZoomIn(tvTemp);
				}

			}

			for (int i = 0; i < gridDrag.getChildCount(); i++) {
				View tempView = gridDrag.getChildAt(i);
				CTTextView tvTemp = (CTTextView) tempView
						.findViewById(R.id.taskWord);
				tvTemp.setVisibility(View.INVISIBLE);

			}

		} else {
			for (int i = 0; i < gridDrop.getChildCount(); i++) {
				View tempView = gridDrop.getChildAt(i);
				ImageView tvTemp = (ImageView) tempView
						.findViewById(R.id.taskImage);
				if (!tvTemp.getTag().toString().equalsIgnoreCase("done")) {

					tvTemp.setTag("done");
					tvTemp.setBackground(getResources().getDrawable(
							R.drawable.bg_sandal));
					animationZoomIn(tvTemp);
				}

			}

			for (int i = 0; i < gridDrag.getChildCount(); i++) {
				View tempView = gridDrag.getChildAt(i);
				ImageView tvTemp = (ImageView) tempView
						.findViewById(R.id.taskImage);
				tvTemp.setVisibility(View.INVISIBLE);

			}
		}

		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		accuracy = 0;

		callPostDataService(getResponseOfTask(
				orderTask.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));

		if (progressStatus < orderTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipAuditoryTask() {
		endTime = Helper.getCurrentTimeStamp();

		for (int i = 0; i < gridDrop.getChildCount(); i++) {
			View view = gridDrop.getChildAt(i);
			ImageView tempView = (ImageView) view.findViewById(R.id.taskImage);

			if (tempView.getTag() != null
					&& !tempView.getTag().toString().equalsIgnoreCase("")) {
				tempView.setVisibility(View.VISIBLE);
			}
		}

		for (int i = 0; i < gridDrag.getChildCount(); i++) {
			View view = gridDrag.getChildAt(i);
			ImageView tempView = (ImageView) view.findViewById(R.id.taskImage);

			tempView.setVisibility(View.INVISIBLE);

		}

		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		accuracy = 0;

		callPostDataService(getResponseOfTask(
				auditoryTask.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));

		if (progressStatus < auditoryTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipActiveTask() {
		endTime = Helper.getCurrentTimeStamp();
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);

		for (int i = 0; i < gridDrop.getChildCount(); i++) {
			View tempView = gridDrop.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);
			if (!tvTemp.getTag().toString().equalsIgnoreCase("done")) {
				tvTemp.setText(tvTemp.getTag().toString());
				tvTemp.setTag("done");
				tvTemp.setBackground(getResources().getDrawable(
						R.drawable.black_border));
				animationZoomIn(tvTemp);
			}

		}

		for (int i = 0; i < gridDrag.getChildCount(); i++) {
			View tempView = gridDrag.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);

			tvTemp.setVisibility(View.INVISIBLE);

		}
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		accuracy = 0;

		callPostDataService(getResponseOfTask(
				activeTask.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));

		if (progressStatus < activeTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipLetterToSound() {
		endTime = Helper.getCurrentTimeStamp();

		if (letterToSoundTask.getTasks().get(progressStatus - 1)
				.getCorrectLetterSoundPath()
				.equalsIgnoreCase(imgFirstDrag.getTag().toString())) {
			animationMove(imgFirstDrag, tempDropImg);
			tempDropImg.clearAnimation();
			tempDropImg.setVisibility(View.VISIBLE);
			animationZoomIn(tempDropImg);
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
			imgSecondDrag.clearAnimation();
			imgFirstDrag.clearAnimation();
			imgSecondDrag.setVisibility(View.INVISIBLE);
			imgFirstDrag.setVisibility(View.INVISIBLE);

		} else {
			animationMove(imgFirstDrag, tempDropImg);
			tempDropImg.clearAnimation();
			tempDropImg.setVisibility(View.VISIBLE);
			animationZoomIn(tempDropImg);
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
			imgSecondDrag.clearAnimation();
			imgFirstDrag.clearAnimation();
			imgSecondDrag.setVisibility(View.INVISIBLE);
			imgFirstDrag.setVisibility(View.INVISIBLE);

		}

		callPostDataService(getResponseOfTask(
				letterToSoundTask.getTasks().get(progressStatus - 1)
						.getSessionId(), progressStatus - 1, true));

		if (progressStatus < letterToSoundTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipSoundToLetter() {
		endTime = Helper.getCurrentTimeStamp();

		if (soundToLetterTask.getTasks().get(progressStatus - 1)
				.getCorrectLetter()
				.equalsIgnoreCase(tvFirstDrag.getTag().toString())) {
			animationMove(tvFirstDrag, tempDropTv);
			tempDropTv.clearAnimation();
			tempDropTv.setVisibility(View.VISIBLE);
			tempDropTv.setText(soundToLetterTask.getTasks()
					.get(progressStatus - 1).getCorrectLetter());
			animationZoomIn(tempDropTv);
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
			tvSecondDrag.clearAnimation();
			tvFirstDrag.clearAnimation();
			tvSecondDrag.setVisibility(View.INVISIBLE);
			tvFirstDrag.setVisibility(View.INVISIBLE);

		} else {
			animationMove(tvFirstDrag, tempDropTv);
			tempDropTv.clearAnimation();
			tempDropTv.setVisibility(View.VISIBLE);
			animationZoomIn(tempDropImg);
			tempDropTv.setText(soundToLetterTask.getTasks()
					.get(progressStatus - 1).getCorrectLetter());
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
			tvSecondDrag.clearAnimation();
			tvFirstDrag.clearAnimation();
			tvSecondDrag.setVisibility(View.INVISIBLE);
			tvFirstDrag.setVisibility(View.INVISIBLE);

		}

		callPostDataService(getResponseOfTask(
				soundToLetterTask.getTasks().get(progressStatus - 1)
						.getSessionId(), progressStatus - 1, true));

		if (progressStatus < soundToLetterTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	private void skipWordProblem() {
		endTime = Helper.getCurrentTimeStamp();

		btnNext.setVisibility(View.VISIBLE);
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		tvCheckAnswer.setText("0");
		etAnswer.setTextColor(getResources().getColor(R.color.text_green));
		if (displayName.equalsIgnoreCase("Number Pattern")) {
			etAnswer.setText(numberPatternTasks.getTasks()
					.get(progressStatus - 1).getAnswer());
		} else {
			etAnswer.setText(arithmaticTasks.getTasks().get(progressStatus - 1)
					.getAnswer());
		}
		imgWrongSymbol.setVisibility(View.VISIBLE);
		accuracy = 0;

		if (displayName.equalsIgnoreCase("Number Pattern")) {
			if (progressStatus < numberPatternTasks.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				callPostDataService(getResponseOfTask(numberPatternTasks
						.getTasks().get(progressStatus - 1).getSessionId(),
						progressStatus - 1, true));
				progressStatus++;

			} else {
				callPostDataService(getResponseOfTask(
						numberPatternTasks.getTasks()
								.get(numberPatternTasks.getTasks().size() - 1)
								.getSessionId(), numberPatternTasks.getTasks()
								.size() - 1, true));
			}
		} else {

			if (progressStatus < arithmaticTasks.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				callPostDataService(getResponseOfTask(arithmaticTasks
						.getTasks().get(progressStatus - 1).getSessionId(),
						progressStatus - 1, true));
				progressStatus++;

			} else {
				callPostDataService(getResponseOfTask(arithmaticTasks
						.getTasks().get(arithmaticTasks.getTasks().size() - 1)
						.getSessionId(), arithmaticTasks.getTasks().size() - 1,
						true));
			}
		}

	}

	private void skipCurrencyTask() {
		endTime = Helper.getCurrentTimeStamp();

		btnNext.setVisibility(View.VISIBLE);
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		tvCheckAnswer.setText("0.00");
		etAnswerfirst.setTextColor(getResources().getColor(R.color.text_green));
		etAnswersecond
				.setTextColor(getResources().getColor(R.color.text_green));

		etAnswerfirst.setText(currencyTask.getTasks().get(progressStatus - 1)
				.getAnswer().split("\\.")[0]);
		etAnswersecond.setText(currencyTask.getTasks().get(progressStatus - 1)
				.getAnswer().split("\\.")[1]);

		imgWrongSymbol.setVisibility(View.VISIBLE);
		accuracy = 0;

		if (progressStatus < currencyTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(
					currencyTask.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, true));
			progressStatus++;

		} else {
			callPostDataService(getResponseOfTask(
					currencyTask.getTasks()
							.get(currencyTask.getTasks().size() - 1)
							.getSessionId(),
					currencyTask.getTasks().size() - 1, true));
		}

	}

	private void skipSpokenWordTawsk() {

		endTime = Helper.getCurrentTimeStamp();
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		accuracy = 0;
		for (int i = 0; i < gridDrag.getChildCount(); i++) {
			View view = gridDrag.getChildAt(i);
			ImageView tempView = (ImageView) view.findViewById(R.id.taskImage);
			tempView.setVisibility(View.INVISIBLE);

		}
		tempDropImg.setVisibility(View.VISIBLE);
		animationZoomIn(tempDropImg);

		callPostDataService(getResponseOfTask(
				spokenWordTask.getTasks().get(progressStatus - 1)
						.getSessionId(), progressStatus - 1, true));

		if (progressStatus < spokenWordTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}
		btnNext.setVisibility(View.VISIBLE);
	}

	@SuppressWarnings("deprecation")
	private void replaceTasks(int position) {
		startTime = Helper.getCurrentTimeStamp();
		choiceOverlay.setVisibility(View.GONE);
		choiceOverlay.setClickable(false);

		if (tasks.getTasks().size() > 0) {

			if (!tasks.getTasks().get(0).getsImuli().get(0).getOtherAspects()
					.getImage().equalsIgnoreCase("")) {
				if (imageAudioFrame != null) {
					imageAudioFrame.updateArticleView(tasks.getUrlPrex()
							+ tasks.getTasks().get(position).getsImuli().get(0)
									.getOtherAspects().getImage());
				}

				if (questionFrame != null) {
					questionFrame.updateArticleView(tasks.getTasks()
							.get(position).getInstrucIons().getPrimaryAspect()
							.getText());
				}

			} else if (tasks.getTasks().get(0).getsImuli().get(0)
					.getOtherAspects().getAudio().equalsIgnoreCase("")) {
				if (imageFrame != null) {
					imageFrame.updateArticleView(tasks.getUrlPrex()
							+ tasks.getTasks().get(position).getsImuli().get(0)
									.getOtherAspects().getImage());
				}

				if (questionFrame != null) {
					questionFrame.updateArticleView(tasks.getTasks()
							.get(position).getInstrucIons().getPrimaryAspect()
							.getText());
				}
			} else {

				if (questionFrame != null) {
					questionFrame.updateArticleView(tasks.getTasks()
							.get(position).getInstrucIons().getPrimaryAspect()
							.getText());
				}
			}

			try {
				if (tasks.getTasks().get(0).getResponse().getChoices().get(0)
						.getPrimaryAspect() == null) {
					for (int i = 0; i < DoingTaskActivity.viewList.size(); i++) {
						DoingTaskActivity.viewList.get(i).setTag(
								tasks.getTasks().get(position).getResponse()
										.getChoices().get(0).getIsCorrect());
					}

				} else {
					for (int i = 0; i < DoingTaskActivity.viewList.size(); i++) {

						DoingTaskActivity.viewList.get(i).setTag(
								tasks.getTasks().get(position).getResponse()
										.getChoices().get(i).getIsCorrect());
						DoingTaskActivity.viewList.get(i).setText(
								tasks.getTasks().get(position).getResponse()
										.getChoices().get(i).getPrimaryAspect()
										.getText());
					}

				}
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		for (int i = 0; i < DoingTaskActivity.viewList.size(); i++) {

			DoingTaskActivity.viewList.get(i).setBackgroundDrawable(
					getResources().getDrawable(R.drawable.selector));
		}

	}

	private void shiftLayout() {

		if (progressStatus == 1) {
			if (Helper.isMultiTask(taskTypeId)) {
				loadMultiTask(tasks);
			}
		} else {
			if (Helper.isMultiTask(taskTypeId)) {
				replaceTasks(progressStatus - 1);
			} else if (Helper.isMatching(taskTypeId)) {
				loadMatchingTask(matchingTasks, progressStatus - 1);
			} else if (Helper.isWrittenTask(taskTypeId)) {

				replaceWrittenTask(writtenTasks, progressStatus - 1);
			} else if (Helper.isActiveTask(taskTypeId)) {

				clearAnimation();

				replaceActiveTask(activeTask, progressStatus - 1);
			} else if (Helper.isOrderingTask(taskTypeId)) {

				clearAnimation();
				correctDropCount = 0;
				replaceOrderingTask(orderTask, progressStatus - 1);
			} else if (Helper.isAuditory(taskTypeId)) {

				clearAnimation();
				DoingTaskActivity.matchingList.clear();

				replaceAuditoryTask(auditoryTask, progressStatus - 1);

			} else if (Helper.isDragandDrop(taskTypeId)) {

				replaceDragandDrop(taskDrag, progressStatus - 1);
			} else if (Helper.isLetterToSound(taskTypeId)) {
				tempDropImg.clearAnimation();
				tempDropImg.setVisibility(View.INVISIBLE);
				loadLetterToSoundTask(letterToSoundTask, progressStatus - 1);
			} else if (Helper.isSpokenWordTask(taskTypeId)) {
				tempDropImg.clearAnimation();
				tempDropImg.setVisibility(View.INVISIBLE);
				loadSpokenWordTask(spokenWordTask, progressStatus - 1);
			} else if (Helper.isSoundToLetter(taskTypeId)) {
				tempDropTv.clearAnimation();
				tempDropTv.setVisibility(View.INVISIBLE);
				loadSoundToLetterTask(soundToLetterTask, progressStatus - 1);
			} else if (Helper.isSymbolMatching(taskTypeId)) {
				loadSymbolMatchingTask(symbolTasks, progressStatus - 1);
			} else if (Helper.isPlayingCards(taskTypeId)) {
				loadPlayingCardTask(playtask, progressStatus - 1);
			} else if (Helper.isMicrophoneTask(taskTypeId)) {
				loadMicrophoneTask(microPhoneTask, progressStatus - 1);
			} else if (Helper.isPatternTask(taskTypeId)) {
				patternArray = new JSONArray();
				matchingList = new ArrayList<View>();
				pattenList = new ArrayList<View>();
				pattenPostion = new ArrayList<Integer>();
				patternCorrectCount = 0;
				innerView.setVisibility(View.GONE);
				innerView.setClickable(false);
				loadPatternTask(patternTask, progressStatus - 1);
			} else if (Helper.isArithmetic(taskTypeId)) {
				drawView.clearPoints();
				etAnswer.setText("");
				etAnswer.setTextColor(getResources().getColor(R.color.black));
				etAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
				tvCheckAnswer.setText("");
				imgWrongSymbol.setVisibility(View.INVISIBLE);
			} else if (Helper.isWordProblem(taskTypeId)) {
				drawView.clearPoints();
				etAnswer.setText("");
				etAnswer.setTextColor(getResources().getColor(R.color.black));
				tvCheckAnswer.setText("");
				etAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
				imgWrongSymbol.setVisibility(View.INVISIBLE);
				if (displayName.equalsIgnoreCase("Number Pattern")) {
					replaceNumberPatternTask(numberPatternTasks,
							progressStatus - 1);
				} else {

					replaceWordProblemTask(arithmaticTasks, progressStatus - 1);
				}
			} else if (Helper.isCurrency(taskTypeId)) {
				drawView.clearPoints();
				etAnswerfirst.setText("");
				etAnswerfirst.setTextColor(getResources().getColor(
						R.color.black));
				etAnswersecond.setText("");
				etAnswersecond.setTextColor(getResources().getColor(
						R.color.black));

				tvCheckAnswer.setText("");
				imgWrongSymbol.setVisibility(View.INVISIBLE);
				etAnswerfirst.setInputType(InputType.TYPE_CLASS_NUMBER);
				etAnswersecond.setInputType(InputType.TYPE_CLASS_NUMBER);
				replaceCurrencyTask(currencyTask, progressStatus - 1);
			}
		}
		Animation anim2 = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right);

		anim2.setDuration(250);

		child.startAnimation(anim2);

		new Handler().postDelayed(new Runnable() {

			public void run() {
				if (Helper.isArithmetic(taskTypeId)) {
					etAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
					relpaceArithmaticTask(arithmaticTasks, progressStatus - 1);
				}
			}

		}, anim2.getDuration());

	}

	private void clearAnimation() {
		for (int i = 0; i < answerDropList.size(); i++) {
			answerDropList.get(i).clearAnimation();
		}

		for (int i = 0; i < answerOrderDropList.size(); i++) {
			answerOrderDropList.get(i).clearAnimation();
		}

		for (int i = 0; i < dragViewList.size(); i++) {
			dragViewList.get(i).clearAnimation();
			dragViewList.get(i).setVisibility(View.INVISIBLE);
		}

	}

	private void showAlert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				DoingTaskActivity.this);

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
		alertDialog.setCanceledOnTouchOutside(false);
		// show it
		alertDialog.show();

	}

	private void showErrorAlert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				DoingTaskActivity.this);

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false)

		.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, close
				// current activity
				dialog.cancel();
				finish();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		// show it
		alertDialog.show();

	}

	/*
	 * Method for showing internet alert.
	 */

	private void showInternetAlert(final String title, String message) {
		dialog = new Dialog(DoingTaskActivity.this);
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
				if (!title.equalsIgnoreCase(getResources().getString(
						R.string.app_version))
						&& progressStatus == 1) {
					DoingTaskActivity.this.finish();
				}

			}
		});

		dialog.show();

	}

	private void validationWordProblem(String inputAns) {

		if (inputAns.equalsIgnoreCase(arithmaticTasks.getTasks()
				.get(progressStatus - 1).getAnswer())) {
			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			etAnswer.setTextColor(getResources().getColor(R.color.text_green));
			etAnswer.setText(arithmaticTasks.getTasks().get(progressStatus - 1)
					.getAnswer());
			accuracy = 1;
		} else {
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			tvCheckAnswer.setText(inputAns);
			imgWrongSymbol.setVisibility(View.VISIBLE);
			etAnswer.setTextColor(getResources().getColor(R.color.text_green));
			etAnswer.setText(arithmaticTasks.getTasks().get(progressStatus - 1)
					.getAnswer());
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
		}

		endTime = Helper.getCurrentTimeStamp();
		callPostDataService(getResponseOfTask(
				arithmaticTasks.getTasks().get(progressStatus - 1)
						.getSessionId(), progressStatus - 1, false));
		if (progressStatus < arithmaticTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}

		btnNext.setVisibility(View.VISIBLE);

	}

	private void validationCurrencyTask(String inputFirstAns, String inputSecond) {

		String ans = inputFirstAns + "." + inputSecond;
		String oriAns = currencyTask.getTasks().get(progressStatus - 1)
				.getAnswer();
		String[] corrAns = new String[2];
		corrAns = currencyTask.getTasks().get(progressStatus - 1).getAnswer()
				.split("\\.");

		if (Double.parseDouble(ans) == Double.parseDouble(currencyTask
				.getTasks().get(progressStatus - 1).getAnswer())) {
			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			etAnswerfirst.setTextColor(getResources().getColor(
					R.color.text_green));
			etAnswerfirst.setText(inputFirstAns);
			etAnswersecond.setTextColor(getResources().getColor(
					R.color.text_green));
			if (etAnswersecond.getText().toString() == null
					|| etAnswersecond.getText().toString() == "") {
				etAnswersecond.setText("0");
			} else {
				etAnswersecond.setText((inputSecond));
			}
			accuracy = 1;
		} else {
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			tvCheckAnswer
					.setText(String.format("%.2f", Double.parseDouble(ans)));
			imgWrongSymbol.setVisibility(View.VISIBLE);
			etAnswerfirst.setTextColor(getResources().getColor(
					R.color.text_green));

			etAnswerfirst.setText(corrAns[0]);
			etAnswerfirst.setTextColor(getResources().getColor(
					R.color.text_green));
			etAnswersecond.setTextColor(getResources().getColor(
					R.color.text_green));
			etAnswersecond.setText(corrAns[1]);
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
		}

		endTime = Helper.getCurrentTimeStamp();

		callPostDataService(getResponseOfTask(
				currencyTask.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, false));

		if (progressStatus < currencyTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}

		btnNext.setVisibility(View.VISIBLE);
	}

	private void validationNumberPattern(String inputAns) {
		if (inputAns.equalsIgnoreCase(numberPatternTasks.getTasks()
				.get(progressStatus - 1).getAnswer())) {
			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			etAnswer.setTextColor(getResources().getColor(R.color.text_green));
			etAnswer.setText(numberPatternTasks.getTasks()
					.get(progressStatus - 1).getAnswer());
			accuracy = 1;
		} else {
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			tvCheckAnswer.setText(inputAns);
			imgWrongSymbol.setVisibility(View.VISIBLE);
			etAnswer.setTextColor(getResources().getColor(R.color.text_green));
			etAnswer.setText(numberPatternTasks.getTasks()
					.get(progressStatus - 1).getAnswer());
			playLocalFile(R.raw.wrong_answer);
			accuracy = 0;
		}

		endTime = Helper.getCurrentTimeStamp();
		callPostDataService(getResponseOfTask(numberPatternTasks.getTasks()
				.get(progressStatus - 1).getSessionId(), progressStatus - 1,
				false));
		if (progressStatus < numberPatternTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}

		btnNext.setVisibility(View.VISIBLE);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void showZoomImage(String imageUrl) {
		final Dialog dialog = new Dialog(DoingTaskActivity.this,
				R.style.TaskPopAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setContentView(R.layout.zoom);
		androidAQuery = new AQuery(DoingTaskActivity.this);

		dialog.setCancelable(true);

		ImageView imgclose = (ImageView) dialog.findViewById(R.id.imgClose);
		final ZoomableImageView imgZoom = (ZoomableImageView) dialog
				.findViewById(R.id.imgZoom);

		androidAQuery.id(imgZoom).progress(R.id.imageProgress)
				.image(imageUrl, false, false, 0, 0, new BitmapAjaxCallback() {

					@Override
					public void callback(String url, ImageView iv, Bitmap bm,
							AjaxStatus status) {
						imgZoom.setImageBitmap(bm);

					}
				}.header("User-Agent", "android"));

		imgclose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
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
		window.setLayout((int) (width / 2.75), (int) (height / 2.5));

	}

	/*
	 * Method for animating the activty.
	 */
	private void animatedStartActivity(Boolean isSession) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(),
				LoginActivity.class);
		intent.putExtra("isSession", isSession);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

	/** clear provider and our user model ... e.g. for logout */
	private void clearDbAndSession() {

		CTUser.getInstance().clear();
		getContentResolver().delete(Users.CONTENT_URI, null, null);
		getContentResolver().delete(Dashboards.CONTENT_URI, null, null);

		getContentResolver().delete(PatientsList.CONTENT_URI, null, null);
		getContentResolver().delete(Patients.CONTENT_URI, null, null);
	}

	private void memoryTaskSkip() {
		endTime = Helper.getCurrentTimeStamp();
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		if (progressStatus < matchingTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(
					matchingTasks.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, true));
			progressStatus++;
		}
		for (int i = 0; i < DoingTaskActivity.matchingList.size(); i++) {
			View v = DoingTaskActivity.matchingList.get(i);
			ViewAnimator viewAnimator = (ViewAnimator) v
					.findViewById(R.id.viewFlipper);
			ImageView imageCheck = (ImageView) v.findViewById(R.id.checkImage);
			imageCheck.setVisibility(View.VISIBLE);
			if (v.findViewById(R.id.dummyImage).getVisibility() == View.VISIBLE) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);

			}
		}
		playLocalFile(R.raw.wrong_answer);

		btnNext.setVisibility(View.VISIBLE);

	}

	private void symbolMatchingTaskSkip() {
		endTime = Helper.getCurrentTimeStamp();
		Log.v(TAG, "" + endTime);
		Log.v(TAG, "Differnce" + (endTime - startTime));
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		if (progressStatus < symbolTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(
					symbolTasks.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, true));
			progressStatus++;
		}
		for (int i = 0; i < DoingTaskActivity.symbolList.size(); i++) {
			View v = DoingTaskActivity.symbolList.get(i);

			ImageView imageCheck = (ImageView) v.findViewById(R.id.checkImage);
			String[] url = currentSymbolTask.getMatchingSymbols().toString()
					.split("=");
			String imageUrl = url[0].substring(1);
			if (imageUrl.equalsIgnoreCase(v.findViewById(R.id.taskImage)
					.getTag().toString())
					&& imageCheck.getVisibility() != View.VISIBLE) {
				imageCheck.setVisibility(View.VISIBLE);
				imageCheck.setImageResource(R.drawable.wrong);
				shake = AnimationUtils.loadAnimation(this, R.anim.bounce);
				v.startAnimation(shake);
			}

		}
		playLocalFile(R.raw.wrong_answer);

		btnNext.setVisibility(View.VISIBLE);

	}

	private void patternTaskSkip() {
		endTime = Helper.getCurrentTimeStamp();

		for (int i = 0; i < DoingTaskActivity.matchingList.size(); i++) {
			View v = DoingTaskActivity.matchingList.get(i);

			CTTextView tvWord = (CTTextView) v.findViewById(R.id.taskWord);
			if (tvWord.getVisibility() != View.VISIBLE) {

				if (pattenPostion.contains(matchingList.get(i)
						.findViewById(R.id.dummyImage).getTag())) {
					playLocalFile(R.raw.correct_answer);
					tvWord.setVisibility(View.VISIBLE);
					tvWord.setText(""
							+ getPatternText((Integer) matchingList.get(i)
									.findViewById(R.id.dummyImage).getTag()));

				}

			}
		}

		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		accuracy = 0;
		callPostDataService(getResponseOfTask(
				patternTask.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));
		if (progressStatus < patternTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;
		}

		btnNext.setVisibility(View.VISIBLE);

	}

	private void symbolMatchingValidation() {
		endTime = Helper.getCurrentTimeStamp();
		int count = 0;

		for (int i = 0; i < DoingTaskActivity.symbolList.size(); i++) {
			View v = DoingTaskActivity.symbolList.get(i);

			ImageView imageCheck = (ImageView) v.findViewById(R.id.checkImage);
			String[] url = currentSymbolTask.getMatchingSymbols().toString()
					.split("=");
			String imageUrl = url[0].substring(1);
			count = Integer.parseInt(url[1].substring(0, url[1].length() - 1));
			if (imageUrl.equalsIgnoreCase(v.findViewById(R.id.taskImage)
					.getTag().toString())
					&& imageCheck.getVisibility() != View.VISIBLE) {
				imageCheck.setVisibility(View.VISIBLE);
				imageCheck.setImageResource(R.drawable.wrong);
				shake = AnimationUtils.loadAnimation(this, R.anim.bounce);
				v.startAnimation(shake);
			}

		}
		if (symbolCorrectCount == count) {
			accuracy = 1;
			playLocalFile(R.raw.correct_answer);
			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);

		} else {
			accuracy = 0;
			playLocalFile(R.raw.wrong_answer);
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		}

		if (progressStatus < symbolTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(
					symbolTasks.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, false));
			progressStatus++;
		}

		btnNext.setVisibility(View.VISIBLE);

	}

	private String getUserId() {
		String[] projectionUserId = new String[] { Users.PATIENT_ID };

		Cursor cursor = this.getContentResolver().query(Users.CONTENT_URI,
				projectionUserId, null, null, Users.DEFAULT_SORT);

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

	private void delay(final ViewAnimator viewAnimator) {

		new Handler().postDelayed(new Runnable() {

			public void run() {
				AnimationFactory.flipTransition(tempAnimator,
						FlipDirection.RIGHT_LEFT);
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.RIGHT_LEFT);
				playLocalFile(R.raw.wrong_answer);
				skipDelay = false;
			}

		}, Constants.SKIP_DELAY);
	}

	private void validationSequenceTask(View v, View dropView) {

		correctDropCount = 0;
		if (v == dropView) {
			CTTextView tvView = (CTTextView) dropView
					.findViewById(R.id.taskWord);
			if (tvView.getTag().toString().equalsIgnoreCase(currentAns)) {

				// tvView.setVisibility(View.VISIBLE);
				tvView.setText(currentAns);
				tvView.setTag("done");
				tvView.setTextColor(Color.BLACK);
				tvView.setGravity(Gravity.CENTER);
				tvView.setBackground(getResources().getDrawable(
						R.drawable.black_border));
				animationZoomIn(tvView);
				playLocalFile(R.raw.correct_answer);
				createActionAnswer(false, currentAns, true, currentPos);
				createEventAnswer(currentAns, true, currentPos);

			} else {
				createActionAnswer(false, currentAns, false, currentPos);
				createEventAnswer(currentAns, false, currentPos);
				dragView.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dragView.setVisibility(View.VISIBLE);
					}

				});
				playLocalFile(R.raw.wrong_answer);
			}
		}
		for (int i = 0; i < listDrop.getChildCount(); i++) {
			View tempView = listDrop.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);
			if (tvTemp.getTag().toString().equalsIgnoreCase("done"))
				correctDropCount++;
		}

		if (correctDropCount == listDrop.getChildCount()) {
			correctDropCount = 0;

			for (int i = 0; i < listDrag.getChildCount(); i++) {
				View tempView = listDrag.getChildAt(i);
				CTTextView tvTemp = (CTTextView) tempView
						.findViewById(R.id.taskWord);

				tvTemp.setVisibility(View.INVISIBLE);

			}

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);
			endTime = Helper.getCurrentTimeStamp();
			accuracy = 1;

			callPostDataService(getResponseOfTask(
					taskDrag.getTasks().get(progressStatus - 1).getSessionId(),
					progressStatus - 1, false));

			if (progressStatus < taskDrag.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				progressStatus++;
			}
			btnNext.setVisibility(View.VISIBLE);
		}

	}

	private void pictureValidation(final ImageView image,
			final ImageView viewAnimator) {
		new Handler().postDelayed(new Runnable() {

			public void run() {

				playLocalFile(R.raw.correct_answer);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.correct);
				correctImageCount++;
				viewAnimator.setVisibility(View.VISIBLE);
				viewAnimator.setImageResource(R.drawable.correct);
				correctImageCount++;
				accuracy = 1;
				skipDelay = false;
				if (correctImageCount == currentTask.getItems().size()) {
					endTime = Helper.getCurrentTimeStamp();
					callPostDataService(getResponseOfTask(matchingTasks
							.getTasks().get(progressStatus - 1).getSessionId(),
							progressStatus - 1, false));
					if (progressStatus < matchingTasks.getTasks().size() + 1) {
						taskProgress.setProgress(progressStatus);
						progressStatus++;
					}

					btnNext.setVisibility(View.VISIBLE);
					toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);

				}

			}

		}, Constants.SKIP_DELAY);
	}

	private void playingTaskTimer(final View v, final String taskLevel, int i) 
	{

		if (PlayingCardFragment.viewFlipper.getDisplayedChild() == PlayingCardFragment.viewFlipper
				.getChildCount() - 1)
		{
			// btnNext.setVisibility(View.VISIBLE);
			viewClick=false;
			DoingTaskActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					if (PlayingCardFragment.viewFlipper.isFlipping())
						PlayingCardFragment.viewFlipper.stopFlipping();
					if (viewClick == false) {

						// update UI here
						endTime = Helper.getCurrentTimeStamp();
						taskProgress.setProgress(progressStatus);
						callPostDataService(getResponseOfTask(playtask
								.getTasks().get(progressStatus - 1)
								.getSessionId(), progressStatus - 1, false));
						progressStatus++;
						DoingTaskActivity.playviewList
								.setVisibility(View.VISIBLE);
						DoingTaskActivity.playviewList.setClickable(true);
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								btnNext.setVisibility(View.VISIBLE);

							}
						}, 1500);

					}

				}
			});

		}

		if (viewClick == false) {

			if (displayName.equalsIgnoreCase("Playing Card Slapjack")) {

				if (PlayingCardFragment.resources
						.getResources()
						.get(0)
						.equalsIgnoreCase(
								PlayingCardFragment.resources.getResources()
										.get(i))) {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Handler handler = new Handler();
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									DoingTaskActivity.playviewList
									.setVisibility(View.VISIBLE);
							      DoingTaskActivity.playviewList.setClickable(true);
									playLocalFile(R.raw.wrong_answer);
									correct = false;
									tapped = false;
									timestamp = Helper.getCurrentTimeStamp();
									shake = AnimationUtils.loadAnimation(
											getApplicationContext(),
											R.anim.shake);

									PlayingCardFragment.viewFlipper
											.startAnimation(shake);

								}
							}, 1000);

						}

					});

				}

			} else {
				if (taskLevel.equalsIgnoreCase("1")) {
					if (PlayingCardFragment.removeOccurance
							.get(PlayingCardFragment.getChild() - 1)
							.equalsIgnoreCase(
									PlayingCardFragment.removeOccurance
											.get(PlayingCardFragment.getChild()))) {

						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										v.setClickable(false);
										playLocalFile(R.raw.wrong_answer);
										correct = false;
										tapped = false;
										timestamp = Helper
												.getCurrentTimeStamp();
										shake = AnimationUtils.loadAnimation(
												getApplicationContext(),
												R.anim.shake);

										PlayingCardFragment.viewFlipper
												.startAnimation(shake);

									}
								}, 1000);

							}

						});
					}
				}

				else if (taskLevel.equalsIgnoreCase("2")) {
					if (PlayingCardFragment.getChild() > 1)
						if (PlayingCardFragment.removeOccurance.get(
								PlayingCardFragment.getChild() - 2)
								.equalsIgnoreCase(
										PlayingCardFragment.removeOccurance
												.get(PlayingCardFragment
														.getChild()))
								) {

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Handler handler = new Handler();
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											v.setClickable(false);
											playLocalFile(R.raw.wrong_answer);
											correct = false;
											tapped = false;
											timestamp = Helper
													.getCurrentTimeStamp();
											shake = AnimationUtils
													.loadAnimation(
															getApplicationContext(),
															R.anim.shake);

											PlayingCardFragment.viewFlipper
													.startAnimation(shake);

										}
									}, 1000);

								}

							});
						}

				} else if (taskLevel.equalsIgnoreCase("3")) {
					if (PlayingCardFragment.getChild() > 2)
						if (PlayingCardFragment.removeOccurance.get(
								PlayingCardFragment.getChild() - 3)
								.equalsIgnoreCase(
										PlayingCardFragment.removeOccurance
												.get(PlayingCardFragment
														.getChild()))
								) {

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Handler handler = new Handler();
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											v.setClickable(false);
											playLocalFile(R.raw.wrong_answer);
											correct = false;
											tapped = false;
											timestamp = Helper
													.getCurrentTimeStamp();
											shake = AnimationUtils
													.loadAnimation(
															getApplicationContext(),
															R.anim.shake);

											PlayingCardFragment.viewFlipper
													.startAnimation(shake);

										}
									}, 1000);

								}

							});
						}

				}
			}

		}

	}

	private void playingTaskValidation(final View v) {

		if (viewClick == true) {
			if (PlayingCardFragment.viewFlipper.getDisplayedChild() != 0) {

				if (PlayingCardFragment.getChild() == PlayingCardFragment.viewFlipper
						.getChildCount() - 1) {

					PlayingCardFragment.viewFlipper.stopFlipping();
					DoingTaskActivity.playviewList.setVisibility(View.VISIBLE);
					DoingTaskActivity.playviewList.setClickable(true);
					btnNext.setVisibility(View.VISIBLE);
					endTime = Helper.getCurrentTimeStamp();
					taskProgress.setProgress(progressStatus);
					callPostDataService(getResponseOfTask(playtask.getTasks()
							.get(progressStatus - 1).getSessionId(),
							progressStatus - 1, false));
					progressStatus++;
				}
				if (PlayingCardFragment.resources
						.getResources()
						.get(0)
						.equalsIgnoreCase(
								PlayingCardFragment.resources.getResources()
										.get(PlayingCardFragment.viewFlipper
												.getDisplayedChild()))) {
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							v.clearAnimation();
							timestamp = Helper.getCurrentTimeStamp();
							playCorrectCount++;
							playLocalFile(R.raw.correct_answer);
							correct = true;
							tapped = true;

						}
					}, 200);

					try {
						createPlayActionAnswer(correct, tapped, timestamp,
								PlayingCardFragment.viewFlipper
										.getDisplayedChild());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					createPlayEventAnswer("instructionRepeat", timestamp);

				} else {
					shake = AnimationUtils.loadAnimation(this, R.anim.shake);
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {

							PlayingCardFragment.viewFlipper.startAnimation(shake);
							timestamp = Helper.getCurrentTimeStamp();
							playIncorrectCount++;
							playLocalFile(R.raw.wrong_answer);
							correct = false;
							tapped = false;

						}
					}, 200);

					DoingTaskActivity.playviewList
							.setVisibility(View.INVISIBLE);
					DoingTaskActivity.playviewList.setClickable(false);
					try {
						createPlayActionAnswer(correct, tapped, timestamp,
								PlayingCardFragment.viewFlipper
										.getDisplayedChild());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					createPlayEventAnswer("instructionRepeat", timestamp);
				}
				playTotalcount++;

				v.setClickable(false);

			}
		}

	}

	private void createPlayEventAnswer(String type, long timestamp) {

		JSONObject obj = new JSONObject();

		try {

			obj.put("type", type);
			obj.put("timestamp", timestamp);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playeventArray.put(obj);

	}

	private void createPlayActionAnswer(boolean tapped, boolean correct,
			long timestamp, int i) throws JSONException {

		JSONObject obj = new JSONObject();

		try {
			obj.put("tapped", tapped);
			obj.put("correct", correct);
			obj.put("timestamp", timestamp);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playactionArray.put("" + i, obj);

	}

	private void playingMemoryVlaidation(View v, String taskLevel) {
		if (PlayingCardFragment.getChild() == PlayingCardFragment.viewFlipper
				.getChildCount() - 1) {
			PlayingCardFragment.viewFlipper.stopFlipping();
			DoingTaskActivity.playviewList.setVisibility(View.VISIBLE);
			DoingTaskActivity.playviewList.setClickable(true);
			btnNext.setVisibility(View.VISIBLE);
			endTime = Helper.getCurrentTimeStamp();
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(
					playtask.getTasks().get(progressStatus - 1).getSessionId(),
					progressStatus - 1, false));
			progressStatus++;
		}
		if (taskLevel.equalsIgnoreCase("1")) {
			if (viewClick == true) {

				if (PlayingCardFragment.getChild() != 0) {

					if (PlayingCardFragment.removeOccurance
							.get(PlayingCardFragment.getChild() - 1)
							.equalsIgnoreCase(
									PlayingCardFragment.removeOccurance
											.get(PlayingCardFragment.getChild()))) {

						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								timestamp = Helper.getCurrentTimeStamp();
								playCorrectCount++;
								playLocalFile(R.raw.correct_answer);
								correct = true;
								tapped = true;

							}
						}, 200);

					} else {
						shake = AnimationUtils
								.loadAnimation(this, R.anim.shake);
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								correct = false;
								tapped = false;
								timestamp = Helper.getCurrentTimeStamp();
								playCorrectCount++;

								PlayingCardFragment.viewFlipper
										.startAnimation(shake);
								playLocalFile(R.raw.wrong_answer);
							}
						}, 200);

					}
					playTotalcount++;
					playingData = new PlayingData();
					playingData.setTapped(tapped);
					playingData.setCorrect(correct);
					playingData.setTimestamp(timestamp);

					playingDataList.add(playingData);
					v.setClickable(false);
				}

			}
		} else if (taskLevel.equalsIgnoreCase("2")) {
			if (viewClick == true) {

				if (PlayingCardFragment.getChild() > 1) {

					if (PlayingCardFragment.removeOccurance
							.get(PlayingCardFragment.getChild() - 2)
							.equalsIgnoreCase(
									PlayingCardFragment.removeOccurance
											.get(PlayingCardFragment.getChild()))) {
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								timestamp = Helper.getCurrentTimeStamp();
								playCorrectCount++;
								playLocalFile(R.raw.correct_answer);
								correct = true;
								tapped = true;

							}
						}, 200);

					} else {
						shake = AnimationUtils
								.loadAnimation(this, R.anim.shake);
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {

								correct = false;
								tapped = false;
								timestamp = Helper.getCurrentTimeStamp();
								playCorrectCount++;

								PlayingCardFragment.viewFlipper
										.startAnimation(shake);
								playLocalFile(R.raw.wrong_answer);
							}
						}, 200);

					}
					playTotalcount++;
					playingData = new PlayingData();
					playingData.setTapped(tapped);
					playingData.setCorrect(correct);
					playingData.setTimestamp(timestamp);

					playingDataList.add(playingData);
					v.setClickable(false);
				}

			}

		} else if (taskLevel.equalsIgnoreCase("3")) {
			if (viewClick == true) {

				if (PlayingCardFragment.getChild() > 2) {

					if (PlayingCardFragment.removeOccurance
							.get(PlayingCardFragment.getChild() - 3)
							.equalsIgnoreCase(
									PlayingCardFragment.removeOccurance
											.get(PlayingCardFragment.getChild()))) {
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								timestamp = Helper.getCurrentTimeStamp();
								playCorrectCount++;
								playLocalFile(R.raw.correct_answer);
								correct = true;
								tapped = true;

							}
						}, 200);

					} else {
						shake = AnimationUtils
								.loadAnimation(this, R.anim.shake);
						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								correct = false;
								tapped = false;
								timestamp = Helper.getCurrentTimeStamp();
								playCorrectCount++;

								PlayingCardFragment.viewFlipper
										.startAnimation(shake);
								playLocalFile(R.raw.wrong_answer);
							}
						}, 200);

					}
					playTotalcount++;
					playingData = new PlayingData();
					playingData.setTapped(tapped);
					playingData.setCorrect(correct);
					playingData.setTimestamp(timestamp);

					playingDataList.add(playingData);
					v.setClickable(false);
				}

			}

		}
	}

	public void animation(float left_margin, float topMargin, View v) {
		Animation animation = new TranslateAnimation(left_margin, 0, topMargin,
				0);
		animation.setDuration(1000);
		v.startAnimation(animation);
	}

	private void animationZoomIn(View v) {
		animZoomIn = AnimationUtils.loadAnimation(doingTask, R.anim.zoom_in);
		v.startAnimation(animZoomIn);

	}

	private void animationMove(View v, View toView) {

		float toX = toView.getLeft();
		float toY = toView.getTop();

		TranslateAnimation move = new TranslateAnimation(v.getLeft(), toX,
				v.getTop(), toY);
		move.setDuration(1000);

		// move.setFillAfter(true);
		v.startAnimation(move);

		// v.setVisibility(View.INVISIBLE);

	}

	private int getPatternText(int pos) {
		for (int i = 0; i < pattenPostion.size(); i++) {
			if (pattenPostion.get(i) == pos) {
				return i + 1;
			}
		}
		return 0;
	}

	private void skipValidations() {
		playLocalFile(R.raw.wrong_answer);

		new Handler().postDelayed(new Runnable() {

			public void run() {
				if (DoingTaskActivity.viewList.size() == 2) {
					for (int i = 0; i < DoingTaskActivity.viewList.size(); i++) {
						if (tasks.getTasks().get(progressStatus - 1)
								.getResponse().getChoices().get(0)
								.getIsCorrect() == true) {
							if (DoingTaskActivity.viewList.get(i).getText()
									.toString().equalsIgnoreCase("Yes"))
								DoingTaskActivity.viewList
										.get(i)
										.setBackgroundColor(
												getResources()
														.getColor(
																R.color.correct_choice_green));
						} else {
							if (DoingTaskActivity.viewList.get(i).getText()
									.toString().equalsIgnoreCase("No"))
								DoingTaskActivity.viewList
										.get(i)
										.setBackgroundColor(
												getResources()
														.getColor(
																R.color.correct_choice_green));
						}

					}
				} else if (DoingTaskActivity.viewList.size() >= 3) {
					for (int i = 0; i < DoingTaskActivity.viewList.size(); i++) {
						if (DoingTaskActivity.viewList.get(i).getTag()
								.toString().equalsIgnoreCase("true"))
							DoingTaskActivity.viewList
									.get(i)
									.setBackgroundColor(
											getResources()
													.getColor(
															R.color.correct_choice_green));

					}
				}

				btnNext.setVisibility(View.VISIBLE);
				if (progressStatus < tasks.getTasks().size() + 1) {
					taskProgress.setProgress(progressStatus);

					progressStatus++;
				}

				choiceOverlay.setVisibility(View.VISIBLE);
				choiceOverlay.setClickable(true);
			}

		}, Constants.SKIP_DELAY);
	}

	private View getChildViewFromGrid(View v) {

		for (int i = 0; i < gridDrop.getChildCount(); i++) {
			if (v == gridDrop.getChildAt(i))
				return gridDrop.getChildAt(i);
		}

		return null;
	}

	private View getListChildViewFronGrid(View v) {

		for (int i = 0; i < listDrop.getChildCount(); i++) {
			if (v == listDrop.getChildAt(i))
				return listDrop.getChildAt(i);
		}

		return null;
	}

	private void validationAuditoryTask(View v, View dropView) {

		if (v == dropView) {
			ImageView tvView = (ImageView) dropView
					.findViewById(R.id.taskImage);

			if (tvView.getTag().toString().equalsIgnoreCase(currentAns)) {
				tvView.setVisibility(View.VISIBLE);
				tvView.setBackground(getResources().getDrawable(
						R.drawable.bg_sandal));
				animationZoomIn(tvView);
				playLocalFile(R.raw.correct_answer);
				correctDropCount++;
				createActionAnswer(false, currentAns, true, currentPos);
				createEventAnswer(currentAns, true, currentPos);

			} else {
				dragView.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dragView.setVisibility(View.VISIBLE);
					}

				});
				playLocalFile(R.raw.wrong_answer);
				createActionAnswer(false, currentAns, false, currentPos);
				createEventAnswer(currentAns, false, currentPos);
			}
		} else {
			playLocalFile(R.raw.wrong_answer);
			createActionAnswer(false, currentAns, false, currentPos);
			createEventAnswer(currentAns, false, currentPos);
			dragView.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dragView.setVisibility(View.VISIBLE);
				}

			});
		}

		if (correctDropCount == auditoryTask.getTasks().get(progressStatus - 1)
				.getActions().size()) {
			correctDropCount = 0;

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);

			for (int i = 0; i < matchingList.size(); i++) {

				matchingList.get(i).findViewById(R.id.taskImage)
						.setVisibility(View.INVISIBLE);

			}

			endTime = Helper.getCurrentTimeStamp();
			accuracy = 1;
			correctDropCount = 0;

			callPostDataService(getResponseOfTask(
					auditoryTask.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, false));

			if (progressStatus < auditoryTask.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				progressStatus++;
			}
			btnNext.setVisibility(View.VISIBLE);

		}

	}

	private void validationWrittenTask(View v, View dropView) {
		correctDropCount = 0;
		if (v == dropView) {
			CTTextView tvView = (CTTextView) dropView
					.findViewById(R.id.taskWord);

			if (tvView.getTag().toString().equalsIgnoreCase(currentAns)) {

				// tvView.setVisibility(View.VISIBLE);
				tvView.setText(currentAns);
				tvView.setTag("done");
				tvView.setBackground(getResources().getDrawable(
						R.drawable.bg_sandal));
				animationZoomIn(tvView);
				playLocalFile(R.raw.correct_answer);
				createActionAnswer(false, currentAns, true, currentPos);
				createEventAnswer(currentAns, true, currentPos);
			} else {
				createEventAnswer(currentAns, false, currentPos);
				createActionAnswer(false, currentAns, false, currentPos);
				dragView.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dragView.setVisibility(View.VISIBLE);
					}

				});
				playLocalFile(R.raw.wrong_answer);
			}

		} else {
			createEventAnswer(currentAns, false, currentPos);
			createActionAnswer(false, currentAns, false, currentPos);
			dragView.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dragView.setVisibility(View.VISIBLE);
				}

			});
			playLocalFile(R.raw.wrong_answer);
		}

		for (int i = 0; i < gridDrop.getChildCount(); i++) {
			View tempView = gridDrop.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);
			if (tvTemp.getTag().toString().equalsIgnoreCase("done"))
				correctDropCount++;
		}

		if (correctDropCount == gridDrop.getChildCount()) {
			correctDropCount = 0;
			String url = getString(R.string.resource_url)
					+ writtenTasks.getTasks().get(progressStatus - 1).getItem()
							.getSoundPath();
			// playSound(url, true);
			for (int i = 0; i < gridDrag.getChildCount(); i++) {
				View tempView = gridDrag.getChildAt(i);
				CTTextView tvTemp = (CTTextView) tempView
						.findViewById(R.id.taskWord);

				tvTemp.setVisibility(View.INVISIBLE);

			}

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);

			endTime = Helper.getCurrentTimeStamp();
			accuracy = 1;

			callPostDataService(getResponseOfTask(
					writtenTasks.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, false));

			if (progressStatus < writtenTasks.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				progressStatus++;
			}
			btnNext.setVisibility(View.VISIBLE);
		}

	}

	private void validationActiveTask(View v, View dropView) {
		correctDropCount = 0;
		if (v == dropView) {
			CTTextView tvView = (CTTextView) dropView
					.findViewById(R.id.taskWord);

			if (tvView.getTag().toString().equalsIgnoreCase(currentAns)) {

				// tvView.setVisibility(View.VISIBLE);
				tvView.setText(currentAns);
				tvView.setTag("done");
				tvView.setBackground(getResources().getDrawable(
						R.drawable.black_border));
				animationZoomIn(tvView);
				playLocalFile(R.raw.correct_answer);
				createActionAnswer(false, currentAns, true, currentPos);
				createEventAnswer(currentAns, true, currentPos);
			} else {
				createEventAnswer(currentAns, false, currentPos);
				createActionAnswer(false, currentAns, false, currentPos);
				dragView.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dragView.setVisibility(View.VISIBLE);
					}

				});
				playLocalFile(R.raw.wrong_answer);
			}

		} else {
			createActionAnswer(false, currentAns, false, currentPos);
			createEventAnswer(currentAns, false, currentPos);
			dragView.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dragView.setVisibility(View.VISIBLE);
				}

			});
			playLocalFile(R.raw.wrong_answer);
		}

		for (int i = 0; i < gridDrop.getChildCount(); i++) {
			View tempView = gridDrop.getChildAt(i);
			CTTextView tvTemp = (CTTextView) tempView
					.findViewById(R.id.taskWord);
			if (tvTemp.getTag().toString().equalsIgnoreCase("done"))
				correctDropCount++;
		}

		if (correctDropCount == gridDrop.getChildCount()) {
			correctDropCount = 0;

			for (int i = 0; i < gridDrag.getChildCount(); i++) {
				View tempView = gridDrag.getChildAt(i);
				CTTextView tvTemp = (CTTextView) tempView
						.findViewById(R.id.taskWord);

				tvTemp.setVisibility(View.INVISIBLE);

			}

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);

			endTime = Helper.getCurrentTimeStamp();
			accuracy = 1;

			callPostDataService(getResponseOfTask(
					activeTask.getTasks().get(progressStatus - 1)
							.getSessionId(), progressStatus - 1, false));

			if (progressStatus < activeTask.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				progressStatus++;
			}
			btnNext.setVisibility(View.VISIBLE);
		}

	}

	private void skipMicrophoneTask() {
		endTime = Helper.getCurrentTimeStamp();

		if (!displayName.contains("Practice")) {
			toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
			accuracy = 0;
		} else {
			accuracy = 1;
		}
		btnStart.setText(getString(R.string.play));
		btnNext.setVisibility(View.VISIBLE);
		btnStart.setVisibility(View.INVISIBLE);

		String audioUrl = microPhoneTask.getTasks().get(progressStatus - 1)
				.getResourceUrl()
				+ microPhoneTask.getTasks().get(progressStatus - 1).getItem()
						.getSoundPath();

		playSequenceAnswer(R.raw.wrong_answer, R.raw.the_correct_answer_is,
				audioUrl);
		if (progressStatus < microPhoneTask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(microPhoneTask.getTasks()
					.get(progressStatus - 1).getSessionId(),
					progressStatus - 1, true));
			progressStatus++;

		} else {
			callPostDataService(getResponseOfTask(microPhoneTask.getTasks()
					.get(microPhoneTask.getTasks().size() - 1).getSessionId(),
					microPhoneTask.getTasks().size() - 1, true));
		}

	}

	private void skipArithmeticTask() {
		endTime = Helper.getCurrentTimeStamp();
		btnNext.setVisibility(View.VISIBLE);
		toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
		playLocalFile(R.raw.wrong_answer);
		tvCheckAnswer.setText("0");
		etAnswer.setTextColor(getResources().getColor(R.color.text_green));
		etAnswer.setText(arithmaticTasks.getTasks().get(progressStatus - 1)
				.getAnswer());

		imgWrongSymbol.setVisibility(View.VISIBLE);
		accuracy = 0;

		if (progressStatus < arithmaticTasks.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			callPostDataService(getResponseOfTask(arithmaticTasks.getTasks()
					.get(progressStatus - 1).getSessionId(),
					progressStatus - 1, true));
			progressStatus++;

		} else {
			callPostDataService(getResponseOfTask(arithmaticTasks.getTasks()
					.get(arithmaticTasks.getTasks().size() - 1).getSessionId(),
					arithmaticTasks.getTasks().size() - 1, true));
		}

	}

	private void skipPlayingCard() {

		btnNext.setVisibility(View.VISIBLE);
		accuracy = 0;
		skip = false;
		if(PlayingCardFragment.viewFlipper.isFlipping())
		PlayingCardFragment.viewFlipper.stopFlipping();
		PlayingCardFragment.mTimer.cancel();
		endTime = Helper.getCurrentTimeStamp();
		callPostDataService(getResponseOfTask(
				playtask.getTasks().get(progressStatus - 1).getSessionId(),
				progressStatus - 1, true));
		if (progressStatus < playtask.getTasks().size() + 1) {
			taskProgress.setProgress(progressStatus);
			progressStatus++;

		}

	}

	private void validationOrderTask(View v, View dropView) {

		if (taskTypeId.equalsIgnoreCase("20")) {
			if (v == dropView) {

				CTTextView tvView = (CTTextView) dropView
						.findViewById(R.id.taskWord);

				if (tvView.getTag().toString().equalsIgnoreCase(currentAns)) {

					tvView.setVisibility(View.VISIBLE);
					tvView.setBackground(getResources().getDrawable(
							R.drawable.black_border));
					animationZoomIn(tvView);
					tvView.setTag("done");
					playLocalFile(R.raw.correct_answer);
					correctDropCount++;
					createEventAnswer(currentAns, true, currentPos);
					createActionAnswer(false, currentAns, true, currentPos);
				} else {
					createEventAnswer(currentAns, false, currentPos);
					createActionAnswer(false, currentAns, false, currentPos);
					dragView.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							dragView.setVisibility(View.VISIBLE);
						}

					});
					playLocalFile(R.raw.wrong_answer);
				}
			} else {
				createEventAnswer(currentAns, false, currentPos);
				createActionAnswer(false, currentAns, false, currentPos);
				dragView.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dragView.setVisibility(View.VISIBLE);
					}

				});
				playLocalFile(R.raw.wrong_answer);
			}
		} else {

			if (v == dropView) {
				ImageView tvView = (ImageView) dropView
						.findViewById(R.id.taskImage);
				if (tvView.getTag().toString().equalsIgnoreCase(currentAns)) {

					tvView.setVisibility(View.VISIBLE);
					tvView.setBackground(getResources().getDrawable(
							R.drawable.bg_sandal));
					animationZoomIn(tvView);
					tvView.setTag("done");
					playLocalFile(R.raw.correct_answer);
					correctDropCount++;
					createEventAnswer(currentAns, true, currentPos);
					createActionAnswer(false, currentAns, true, currentPos);
				} else {
					createEventAnswer(currentAns, false, currentPos);
					createActionAnswer(false, currentAns, false, currentPos);
					dragView.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							dragView.setVisibility(View.VISIBLE);
						}

					});
					playLocalFile(R.raw.wrong_answer);
				}
			} else {
				playLocalFile(R.raw.wrong_answer);
				createEventAnswer(currentAns, false, currentPos);
				createActionAnswer(false, currentAns, false, currentPos);
				dragView.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						dragView.setVisibility(View.VISIBLE);
					}

				});
			}
		}

		if (correctDropCount == gridDrop.getChildCount()) {
			correctDropCount = 0;

			toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			playLocalFile(R.raw.correct_answer);

			for (int i = 0; i < allDragViewList.size(); i++) {
				if (taskTypeId.equalsIgnoreCase("20")) {
					allDragViewList.get(i).findViewById(R.id.taskWord)
							.setVisibility(View.INVISIBLE);
				} else {
					allDragViewList.get(i).findViewById(R.id.taskImage)
							.setVisibility(View.INVISIBLE);
				}
			}

			endTime = Helper.getCurrentTimeStamp();
			accuracy = 1;
			correctDropCount = 0;

			callPostDataService(getResponseOfTask(
					orderTask.getTasks().get(progressStatus - 1).getSessionId(),
					progressStatus - 1, false));

			if (progressStatus < orderTask.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);
				progressStatus++;
			}
			btnNext.setVisibility(View.VISIBLE);

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rlHome:

			
			taskOverlay.setVisibility(View.VISIBLE);
			taskOverlay.setClickable(true);
			
			if (progressStatus > 1) {

				if (Helper.isMultiTask(taskTypeId)) {
					Intent i = new Intent(getApplicationContext(),
							SummaryActivity.class);
					i.putExtra("displayName", displayName);
					i.putExtra("userId", userId);
					i.putExtra("patientId", patientId);
					i.putExtra("sessionId", "0");
					i.putExtra("typeId", taskTypeId);
					startActivity(i);
				} else {
					mProgressHUD = ProgressHUD.show(DoingTaskActivity.this,
							"Loading", true, true, this);
					mProgressHUD.setCancelable(false);
					callPreferenceService();

				}
			} else {
				finish();
			}

			break;

		case R.id.rlSkip:
			if (toast != null)
				toast.cancel();
			if (btnNext.getVisibility() == View.GONE) {

				if (Helper.isMultiTask(taskTypeId)) {
					toast = AlertPopup.showWrongToast(DoingTaskActivity.this);
					endTime = Helper.getCurrentTimeStamp();
					skipValidations();
					accuracy = 0;
				} else if (Helper.isMatching(taskTypeId)) {
					accuracy = 0;
					memoryTaskSkip();
				} else if (Helper.isDragandDrop(taskTypeId)) {
					accuracy = 0;
					skipSequenceTask();
				} else if (Helper.isSymbolMatching(taskTypeId)) {
					accuracy = 0;
					symbolMatchingTaskSkip();
				} else if (Helper.isOrderingTask(taskTypeId)) {
					accuracy = 0;
					skipOrderTask();
				} else if (Helper.isCurrency(taskTypeId)) {
					accuracy = 0;
					skipCurrencyTask();
				} else if (Helper.isAuditory(taskTypeId)) {
					accuracy = 0;
					skipAuditoryTask();
				} else if (Helper.isSpokenWordTask(taskTypeId)) {
					accuracy = 0;
					skipSpokenWordTawsk();
				} else if (Helper.isWrittenTask(taskTypeId)) {
					accuracy = 0;
					skipWrittenTask();
				} else if (Helper.isActiveTask(taskTypeId)) {
					accuracy = 0;
					skipActiveTask();
				} else if (Helper.isLetterToSound(taskTypeId)) {
					accuracy = 0;
					skipLetterToSound();
				} else if (Helper.isSoundToLetter(taskTypeId)) {
					accuracy = 0;
					skipSoundToLetter();
				} else if (Helper.isPatternTask(taskTypeId)) {
					accuracy = 0;
					patternTaskSkip();
				} else if (Helper.isPlayingCards(taskTypeId)) {
					skipPlayingCard();
				} else if (Helper.isMicrophoneTask(taskTypeId)) {
					/*
					 * if(speechRecognizer != null); stopRecord();
					 */
					skipMicrophoneTask();
				} else if (Helper.isArithmetic(taskTypeId)) {
					accuracy = 0;
					skipArithmeticTask();
				} else if (Helper.isWordProblem(taskTypeId)) {
					accuracy = 0;
					skipWordProblem();
				}
			}

			break;
		case R.id.btnNext:
			btnNext.setVisibility(View.GONE);
			if (toast != null)
				toast.cancel();
			if (mPlayerLocal != null) {
				if (mPlayerLocal.isPlaying()) {
					mPlayerLocal.stop();
					mPlayerLocal.release();
					mPlayerLocal = null;
				}
			}

			if (mPlayerLocal1 != null) {
				if (mPlayerLocal1.isPlaying()) {
					mPlayerLocal1.stop();
					mPlayerLocal1.release();
					mPlayerLocal1 = null;
				}
			}

			if (mPlayerOnline != null) {
				if (mPlayerOnline.isPlaying()) {
					mPlayerOnline.stop();
					mPlayerOnline.release();
					mPlayerOnline = null;
				}
			}
			int count = 0;
			if (Helper.isMultiTask(taskTypeId)) {
				count = tasks.getTasks().size();
			} else if (Helper.isMatching(taskTypeId)) {
				count = matchingTasks.getTasks().size();
			} else if (Helper.isAuditory(taskTypeId)) {
				count = auditoryTask.getTasks().size();
			} else if (Helper.isPatternTask(taskTypeId)) {
				count = patternTask.getTasks().size();
			} else if (Helper.isDragandDrop(taskTypeId)) {
				count = taskDrag.getTasks().size();
			} else if (Helper.isWrittenTask(taskTypeId)) {
				count = writtenTasks.getTasks().size();
			} else if (Helper.isOrderingTask(taskTypeId)) {
				count = orderTask.getTasks().size();
			} else if (Helper.isActiveTask(taskTypeId)) {
				count = activeTask.getTasks().size();
			} else if (Helper.isSpokenWordTask(taskTypeId)) {
				count = spokenWordTask.getTasks().size();
			} else if (Helper.isLetterToSound(taskTypeId)) {
				count = letterToSoundTask.getTasks().size();
			} else if (Helper.isSoundToLetter(taskTypeId)) {
				count = soundToLetterTask.getTasks().size();
			} else if (Helper.isSymbolMatching(taskTypeId)) {
				count = symbolTasks.getTasks().size();
			} else if (Helper.isPlayingCards(taskTypeId)) {
				count = playtask.getTasks().size();
			} else if (Helper.isArithmetic(taskTypeId)) {
				count = arithmaticTasks.getTasks().size();
			} else if (Helper.isCurrency(taskTypeId)) {
				count = currencyTask.getTasks().size();
			} else if (Helper.isWordProblem(taskTypeId)) {
				if (displayName.equalsIgnoreCase("Number Pattern")) {
					count = numberPatternTasks.getTasks().size();
				} else {
					count = arithmaticTasks.getTasks().size();
				}
			} else if (Helper.isMicrophoneTask(taskTypeId)) {
				// stopRecord();
				btnStart.setVisibility(View.GONE);
				btnStart.setText(getString(R.string.play));
				count = microPhoneTask.getTasks().size();
			}

			if (progressStatus == (count + 1)) {
				rlSkip.setClickable(false);
				rlHome.setClickable(false);
				if (Helper.isMultiTask(taskTypeId)) {
					Intent i = new Intent(getApplicationContext(),
							SummaryActivity.class);
					i.putExtra("displayName", displayName);
					i.putExtra("userId", userId);
					i.putExtra("patientId", patientId);
					i.putExtra("typeId", taskTypeId);
					i.putExtra("sessionId", "0");
					startActivity(i);
				} else {
					taskOverlay.setVisibility(View.VISIBLE);
					taskOverlay.setClickable(true);
					mProgressHUD = ProgressHUD.show(DoingTaskActivity.this,
							"Loading", true, true, this);
					mProgressHUD.setCancelable(false);
					callPreferenceService();
				}

			} else {
				shiftLayout();

			}

			break;
		case R.id.ctIcon:
			// if (dialog == null || !dialog.isShowing())
			AlertPopup.showVersionAlert(DoingTaskActivity.this,
					getString(R.string.app_version), null);
			break;

		default:
			break;
		}
	}

	@Override
	public void onChoiceSelected(View v, String tag) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			pView = v;
			childCount = ((LinearLayout) findViewById(R.id.choice_container))
					.getChildCount();

			btnNext.setVisibility(View.VISIBLE);
			if (v.getTag().toString().equalsIgnoreCase("true")) {
				pView.setBackgroundColor(getResources().getColor(
						R.color.correct_choice_green));

				toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
			} else {
				toast = AlertPopup.showWrongToast(DoingTaskActivity.this);

			}

			if (progressStatus < tasks.getTasks().size() + 1) {
				taskProgress.setProgress(progressStatus);

				progressStatus++;
			}

			choiceOverlay.setVisibility(View.VISIBLE);
			choiceOverlay.setClickable(true);
		}
	}

	@Override
	public void onFragmentClick(View v) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			if (Helper.isMultiTask(taskTypeId)) {
				playSound(
						tasks.getUrlPrex()
								+ tasks.getTasks().get(progressStatus - 1)
										.getsImuli().get(0).getOtherAspects()
										.getAudio(), false);
			} else if (Helper.isPlayingCards(taskTypeId)) {
				playSound(playtask.getTasks().get(0).getResourceUrl()
						+ playtask.getTasks().get(0).getInstructionAudioPaths()
								.get(0), false);
				createPlayEventAnswer("instructionRepeat", timestamp);

			} else if (Helper.isMicrophoneTask(taskTypeId)) {

				if (displayName.contains("Read")) {
					playLocalFile(R.raw.please_read_aloud_the_word_that_you_see);
				} else if (displayName.contains("Picture")) {
					playLocalFile(R.raw.please_name_the_image_that_you_see);
				} else {
					playLocalFile(R.raw.please_repeat_the_word_that_you_hear);
				}
			} else if (Helper.isArithmetic(taskTypeId)) {
				playLocalFile(R.raw.please_solve_the_problem_below);
			} else if (Helper.isDragandDrop(taskTypeId)) {
				playLocalFile(R.raw.please_arrange_the_steps_in_the_correct_sequence);
			} else if (Helper.isOrderingTask(taskTypeId)) {
				playLocalFile(R.raw.please_rearrange_the_words_in_alphabetical_order);
			} else if (Helper.isWordProblem(taskTypeId)) {
				if (taskTypeId.equalsIgnoreCase("45")) {
					playLocalFile(R.raw.please_find_the_missing_number_in_the_sequence_below);
				} else {
					playLocalFile(R.raw.please_solve_the_problem_below);
				}
			} else if (Helper.isPatternTask(taskTypeId)) {
				playLocalFile(R.raw.please_recreate_the_following_pattern);
				createPatternResponse("instructionRepeat");
			} else if (Helper.isActiveTask(taskTypeId)) {
				playLocalFile(R.raw.please_complete_the_sentence_below);
			} else if (Helper.isLetterToSound(taskTypeId)) {
				playLocalFile(R.raw.please_pick_the_correct_item_that_matches_the_text_shown);
			} else if (Helper.isSoundToLetter(taskTypeId)) {
				String url = getResources().getString(R.string.resource_url)
						+ soundToLetterTask.getTasks().get(progressStatus - 1)
								.getCorrectLetter();

				playSequenceAnswer(
						R.raw.please_pick_the_correct_item_that_matches_the_sound_shown,
						url);

			} else if (Helper.isAuditory(taskTypeId)) {
				createPatternResponse("instructionRepeat");
				playSequenceAnswer(auditoryTask.getTasks()
						.get(progressStatus - 1).getActions().get(0)
						.getInstructionAudioPaths());

			} else if (Helper.isCurrency(taskTypeId)) {
				playLocalFile(R.raw.how_much_money_is_displayed);
				createEventAnswer("instructionRepeat");
			} else if (Helper.isWrittenTask(taskTypeId)) {
				if (taskTypeId.equalsIgnoreCase("29")
						|| taskTypeId.equalsIgnoreCase("7")) {

					playSequenceAnswer(
							R.raw.please_spell_out_the_word_that_you_hear,
							getString(R.string.resource_url)
									+ writtenTasks.getTasks()
											.get(progressStatus - 1).getItem()
											.getSoundPath());
				} else if (taskTypeId.equalsIgnoreCase("28")
						|| taskTypeId.equalsIgnoreCase("6")) {

					playLocalFile(R.raw.please_spell_out_the_word_associated_with_the_image_below);
				} else if (taskTypeId.equalsIgnoreCase("30")
						|| taskTypeId.equalsIgnoreCase("8")) {

					playLocalFile(R.raw.please_copy_the_word_written_below);

				}
			} else if (Helper.isSpokenWordTask(taskTypeId)) {
				if (taskTypeId.equalsIgnoreCase("42")) {
					playSequenceAnswer(
							R.raw.please_pick_the_correct_item_that_matches_the_sound,
							getString(R.string.resource_url)
									+ spokenWordTask.getTasks()
											.get(progressStatus - 1)
											.getCorrectChoice().getSoundPath());
				} else if (taskTypeId.equalsIgnoreCase("41")) {
					playLocalFile(R.raw.please_pick_the_correct_item_that_matches_the_text_shown);
				}
			}
		}

	}

	@Override
	public void onSimuliAudioClick(View v) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			if (v.getTag().equals("audio")) {
				if (Helper.isMultiTask(taskTypeId)) {
					playSound(
							tasks.getUrlPrex()
									+ tasks.getTasks().get(progressStatus - 1)
											.getsImuli().get(0)
											.getOtherAspects().getAudio(),
							false);
				} else if (Helper.isWrittenTask(taskTypeId)) {
					String url = getString(R.string.resource_url)
							+ writtenTasks.getTasks().get(progressStatus - 1)
									.getItem().getSoundPath();
					playSound(url, false);
					createWrittenAnswer();
				} else {

					playLocalFile(R.raw.please_name_the_image_that_you_see);
				}

			}
		}
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.task_container;
	}

	private void getContainer(String typeId) {
		innerLayout = (LinearLayout) findViewById(R.id.fragment_container);
		child = this.getLayoutInflater().inflate(
				Helper.getContainter(typeId,
						Helper.isRight(DoingTaskActivity.this)), null);
		innerLayout.addView(child);
	}

	@Override
	public void onAudioFragmentClick(View v) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			if (Helper.isMultiTask(taskTypeId)) {
				playSound(
						tasks.getUrlPrex()
								+ tasks.getTasks().get(progressStatus - 1)
										.getsImuli().get(0).getOtherAspects()
										.getAudio(), false);
			} else if (Helper.isWrittenTask(taskTypeId)) {
				String url = getString(R.string.resource_url)
						+ writtenTasks.getTasks().get(progressStatus - 1)
								.getItem().getSoundPath();
				playSound(url, false);
			} else if (Helper.isAuditory(taskTypeId)) {
				playSequenceAnswer(auditoryTask.getTasks()
						.get(progressStatus - 1).getActions().get(0)
						.getInstructionAudioPaths());
				createPatternResponse("stimulusAudioRepeat");
			} else {

				playLocalFile(R.raw.please_name_the_image_that_you_see);
			}
		}

	}

	@Override
	public void onSimuliImageZoom(View v) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			if (!v.getTag().equals("audio")) {
				showZoomImage(v.getTag().toString());
			} else if (Helper.isCurrency(taskTypeId)) {
				createEventAnswer("stimulusImageZoom");
			}
		}

	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub

		Log.d(TAG, "onReceiveResult(resultCode=" + resultCode + ",resultData="
				+ resultData.toString());

		switch (resultCode) {
		case SyncService.STATUS_RUNNING:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.DOING_TASKS_TOKEN
					|| resultData.getInt(Intent.EXTRA_TEXT) == Constants.MULIT_TOKEN) {

				mProgressHUD = ProgressHUD.show(DoingTaskActivity.this,
						"Loading", true, true, this);
				mProgressHUD.setCancelable(false);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.PREFERENCE_TOKEN
					|| resultData.getInt(Intent.EXTRA_TEXT) == Constants.PREFERENCE_POST_TOKEN
					|| resultData.getInt(Intent.EXTRA_TEXT) == Constants.SUMMARY_RESPONSE_TOKEN) {
				/*
				 * mProgressHUD = ProgressHUD.show(DoingTaskActivity.this,
				 * "Submitting Responses", true, true, this);
				 */
				mProgressHUD.setMessage("Submitting Responses");

			}
			break;
		case SyncService.STATUS_FINISHED:
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.DOING_TASKS_TOKEN) {

				this.getLoaderManager().initLoader(Constants.DOING_TASKS_TOKEN,
						null, this);

			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.SUMMARY_RESPONSE_TOKEN) {
				this.getLoaderManager().initLoader(
						Constants.SUMMARY_RESPONSE_TOKEN, null, this);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.MULIT_TOKEN) {
				this.getLoaderManager().initLoader(Constants.MULIT_TOKEN, null,
						this);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.PREFERENCE_TOKEN) {
				mProgressHUD.dismiss();
				this.getLoaderManager().initLoader(Constants.PREFERENCE_TOKEN,
						null, this);
			} else if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.PREFERENCE_POST_TOKEN) {
				mProgressHUD.dismiss();
				String sessionId = null;
				if (Helper.isMatching(taskTypeId)) {
					if (progressStatus < matchingTasks.getTasks().size() + 1) {

						sessionId = matchingTasks.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = matchingTasks.getTasks()
								.get(matchingTasks.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isSymbolMatching(taskTypeId)) {
					if (progressStatus < symbolTasks.getTasks().size() + 1) {

						sessionId = symbolTasks.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = symbolTasks.getTasks()
								.get(symbolTasks.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isCurrency(taskTypeId)) {
					if (progressStatus < currencyTask.getTasks().size() + 1) {

						sessionId = currencyTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = currencyTask.getTasks()
								.get(currencyTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isSpokenWordTask(taskTypeId)) {
					if (progressStatus < spokenWordTask.getTasks().size() + 1) {

						sessionId = spokenWordTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = spokenWordTask.getTasks()
								.get(spokenWordTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isAuditory(taskTypeId)) {
					if (progressStatus < auditoryTask.getTasks().size() + 1) {

						sessionId = auditoryTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = auditoryTask.getTasks()
								.get(auditoryTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isActiveTask(taskTypeId)) {
					if (progressStatus < activeTask.getTasks().size() + 1) {

						sessionId = activeTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = activeTask.getTasks()
								.get(activeTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isOrderingTask(taskTypeId)) {
					if (progressStatus < orderTask.getTasks().size() + 1) {

						sessionId = orderTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = orderTask.getTasks()
								.get(orderTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isWrittenTask(taskTypeId)) {
					if (progressStatus < writtenTasks.getTasks().size() + 1) {

						sessionId = writtenTasks.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = writtenTasks.getTasks()
								.get(writtenTasks.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isLetterToSound(taskTypeId)) {
					if (progressStatus < letterToSoundTask.getTasks().size() + 1) {

						sessionId = letterToSoundTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = letterToSoundTask.getTasks()
								.get(letterToSoundTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isDragandDrop(taskTypeId)) {
					if (progressStatus < taskDrag.getTasks().size() + 1) {

						sessionId = taskDrag.getTasks().get(progressStatus - 1)
								.getSessionId();
					} else {

						sessionId = taskDrag.getTasks()
								.get(taskDrag.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isSoundToLetter(taskTypeId)) {
					if (progressStatus < soundToLetterTask.getTasks().size() + 1) {

						sessionId = soundToLetterTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = soundToLetterTask.getTasks()
								.get(soundToLetterTask.getTasks().size() - 1)
								.getSessionId();
					}

				} else if (Helper.isPatternTask(taskTypeId)) {
					if (progressStatus < patternTask.getTasks().size() + 1) {

						sessionId = patternTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = patternTask.getTasks()
								.get(patternTask.getTasks().size() - 1)
								.getSessionId();
					}
				} else if (Helper.isPlayingCards(taskTypeId)) {
					if (progressStatus < playtask.getTasks().size() + 1) {

						sessionId = playtask.getTasks().get(progressStatus - 1)
								.getSessionId();
					} else {

						sessionId = playtask.getTasks()
								.get(playtask.getTasks().size() - 1)
								.getSessionId();
					}
				} else if (Helper.isMicrophoneTask(taskTypeId)) {
					if (progressStatus < microPhoneTask.getTasks().size() + 1) {

						sessionId = microPhoneTask.getTasks()
								.get(progressStatus - 1).getSessionId();
					} else {

						sessionId = microPhoneTask.getTasks()
								.get(microPhoneTask.getTasks().size() - 1)
								.getSessionId();
					}
				} else if (Helper.isArithmetic(taskTypeId)
						|| Helper.isWordProblem(taskTypeId)) {
					if (displayName.equalsIgnoreCase("Number Pattern")) {
						if (progressStatus < numberPatternTasks.getTasks()
								.size() + 1) {

							sessionId = numberPatternTasks.getTasks()
									.get(progressStatus - 1).getSessionId();
						} else {

							sessionId = numberPatternTasks
									.getTasks()
									.get(numberPatternTasks.getTasks().size() - 1)
									.getSessionId();
						}
					} else {
						if (progressStatus < arithmaticTasks.getTasks().size() + 1) {

							sessionId = arithmaticTasks.getTasks()
									.get(progressStatus - 1).getSessionId();
						} else {

							sessionId = arithmaticTasks.getTasks()
									.get(arithmaticTasks.getTasks().size() - 1)
									.getSessionId();
						}
					}
				}
				playLocalFile(R.raw.congratulations_you_have_completed_your_tasks);
				Intent i = new Intent(getApplicationContext(),
						SummaryActivity.class);
				i.putExtra("displayName", displayName);
				i.putExtra("userId", userId);
				i.putExtra("patientId", patientId);
				i.putExtra("sessionId", sessionId);
				i.putExtra("typeId", taskTypeId);
				startActivity(i);

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
			mProgressHUD.dismiss();
			break;
		case SyncService.STATUS_NO_NETWORK:

			Log.v("STATUS_NO_NETWORK", "STATUS_NO_NETWORK");
			if (dialog == null)
				showInternetAlert("Oops!", getString(R.string.no_internet));
			mProgressHUD.dismiss();
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
	public void onBackPressed() {
		// do nothing.

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
		// TODO Auto-generated method stub
		switch (id) {
		case Constants.DOING_TASKS_TOKEN:
			return new CursorLoader(this, Task.CONTENT_URI, null, null, null,
					Task.DEFAULT_SORT);
		case Constants.SUMMARY_RESPONSE_TOKEN:
			return new CursorLoader(this, TaskHierarchy.CONTENT_URI, null,
					null, null, TaskHierarchy.DEFAULT_SORT);
		case Constants.MULIT_TOKEN:
			return new CursorLoader(this, Task.CONTENT_URI, null, null, null,
					Task.DEFAULT_SORT);
		case Constants.PREFERENCE_TOKEN:
			return new CursorLoader(this, Session.CONTENT_URI, null, null,
					null, Session.DEFAULT_SORT);
		case Constants.LOGIN_TOKEN:
			return new CursorLoader(this, Users.CONTENT_URI, null, null, null,
					Users.DEFAULT_SORT);
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

		case Constants.DOING_TASKS_TOKEN: {
			data.moveToFirst();

			String json = data.getString(data.getColumnIndex(Task.JSON));

			if (json.matches("error") || json == null) {
				showErrorAlert("Error loading Tasks");

				return;
			}

			if (Helper.isMatching(taskTypeId)) {
				matchingTasks = new MatchingTask();
				Gson gson = new Gson();
				matchingTasks = gson.fromJson(json, MatchingTask.class);

				taskProgress.setMax(matchingTasks.getTasks().size());
				loadMatchingTask(matchingTasks, progressStatus - 1);
			} else if (Helper.isLetterToSound(taskTypeId)) {
				letterToSoundTask = new LetterToSound();
				Gson gson = new Gson();
				letterToSoundTask = gson.fromJson(json, LetterToSound.class);

				taskProgress.setMax(letterToSoundTask.getTasks().size());
				loadLetterToSoundTask(letterToSoundTask, progressStatus - 1);
			} else if (Helper.isDragandDrop(taskTypeId)) {
				taskDrag = new DraggingTask();
				Gson gson = new Gson();
				taskDrag = gson.fromJson(json, DraggingTask.class);
				taskProgress.setMax(taskDrag.getTasks().size());
				loadDragandDrop(taskDrag, progressStatus - 1);
			} else if (Helper.isAuditory(taskTypeId)) {
				auditoryTask = new AuditoryItem();
				Gson gson = new Gson();
				auditoryTask = gson.fromJson(json, AuditoryItem.class);

				taskProgress.setMax(auditoryTask.getTasks().size());
				loadAuditoryTask(auditoryTask, progressStatus - 1);
			} else if (Helper.isCurrency(taskTypeId)) {
				currencyTask = new CurrencyItem();
				Gson gson = new Gson();
				currencyTask = gson.fromJson(json, CurrencyItem.class);

				taskProgress.setMax(currencyTask.getTasks().size());
				loadCurrencyTask(currencyTask, progressStatus - 1);
			} else if (Helper.isPatternTask(taskTypeId)) {
				patternTask = new PatternItems();
				Gson gson = new Gson();
				patternTask = gson.fromJson(json, PatternItems.class);

				taskProgress.setMax(patternTask.getTasks().size());
				loadPatternTask(patternTask, progressStatus - 1);
			} else if (Helper.isActiveTask(taskTypeId)) {
				activeTask = new ActiveItems();
				Gson gson = new Gson();
				activeTask = gson.fromJson(json, ActiveItems.class);

				taskProgress.setMax(activeTask.getTasks().size());
				loadActiveTask(activeTask, progressStatus - 1);
			} else if (Helper.isOrderingTask(taskTypeId)) {
				orderTask = new OrderItems();
				Gson gson = new Gson();
				orderTask = gson.fromJson(json, OrderItems.class);

				taskProgress.setMax(orderTask.getTasks().size());
				loadOrderingTask(orderTask, progressStatus - 1);
			} else if (Helper.isSoundToLetter(taskTypeId)) {
				soundToLetterTask = new SoundToLetter();
				Gson gson = new Gson();
				soundToLetterTask = gson.fromJson(json, SoundToLetter.class);

				taskProgress.setMax(soundToLetterTask.getTasks().size());
				loadSoundToLetterTask(soundToLetterTask, progressStatus - 1);
			} else if (Helper.isSpokenWordTask(taskTypeId)) {
				spokenWordTask = new SpokenWord();
				Gson gson = new Gson();
				spokenWordTask = gson.fromJson(json, SpokenWord.class);
				taskProgress.setMax(spokenWordTask.getTasks().size());
				loadSpokenWordTask(spokenWordTask, progressStatus - 1);
			} else if (Helper.isSymbolMatching(taskTypeId)) {
				symbolTasks = new SymbolItems();
				Gson gson = new Gson();
				symbolTasks = gson.fromJson(json, SymbolItems.class);

				taskProgress.setMax(symbolTasks.getTasks().size());
				loadSymbolMatchingTask(symbolTasks, progressStatus - 1);
			} else if (Helper.isMultiTask(taskTypeId)) {
				if (taskTypeId.equalsIgnoreCase("17")) {
					showErrorAlert("Error loading Tasks");
				} else {
					tasks = new TaskResponse();
					Gson gson = new Gson();
					tasks = gson.fromJson(json, TaskResponse.class);

					taskProgress.setMax(tasks.getTasks().size());
					shiftLayout();
				}

			} else if (Helper.isPlayingCards(taskTypeId)) {

				playtask = new PlayingTask();
				Gson gson = new Gson();
				playtask = gson.fromJson(json, PlayingTask.class);

				taskProgress.setMax(playtask.getTasks().size());
				loadPlayingCardTask(playtask, progressStatus - 1);

			} else if (Helper.isMicrophoneTask(taskTypeId)) {
				microPhoneTask = new NamingResponse();
				Gson gson = new Gson();
				microPhoneTask = gson.fromJson(json, NamingResponse.class);
				taskProgress.setMax(microPhoneTask.getTasks().size());

				loadMicrophoneTask(microPhoneTask, progressStatus - 1);
			} else if (Helper.isArithmetic(taskTypeId)) {
				arithmaticTasks = new ArithematicItems();
				Gson gson = new Gson();
				arithmaticTasks = gson.fromJson(json, ArithematicItems.class);
				taskProgress.setMax(arithmaticTasks.getTasks().size());

				loadArithmaticTask(arithmaticTasks, progressStatus - 1);
			} else if (Helper.isWrittenTask(taskTypeId)) {
				writtenTasks = new WrittenItem();
				Gson gson = new Gson();
				writtenTasks = gson.fromJson(json, WrittenItem.class);
				taskProgress.setMax(writtenTasks.getTasks().size());

				loadWrittenTask(writtenTasks, progressStatus - 1);
			} else if (Helper.isWordProblem(taskTypeId)) {

				if (displayName.equalsIgnoreCase("Number Pattern")) {
					numberPatternTasks = new NumberPattern();
					Gson gson = new Gson();
					numberPatternTasks = gson.fromJson(json,
							NumberPattern.class);
					taskProgress.setMax(numberPatternTasks.getTasks().size());

					loadNumberPatternTask(numberPatternTasks,
							progressStatus - 1);
				} else {
					arithmaticTasks = new ArithematicItems();
					Gson gson = new Gson();
					arithmaticTasks = gson.fromJson(json,
							ArithematicItems.class);
					taskProgress.setMax(arithmaticTasks.getTasks().size());

					loadWordProblemTask(arithmaticTasks, progressStatus - 1);
				}
			}

			taskOverlay.setVisibility(View.GONE);
			taskOverlay.setClickable(false);

			this.getContentResolver().delete(Task.CONTENT_URI, null, null);
			mProgressHUD.dismiss();
			break;

		}

		case Constants.MULIT_TOKEN: {
			data.moveToFirst();

			String json = data.getString(data.getColumnIndex(Task.JSON));

			tasks = new TaskResponse();
			Gson gson = new Gson();
			tasks = gson.fromJson(json, TaskResponse.class);
			taskProgress.setMax(tasks.getTasks().size());
			loadMultiTask(tasks);
			taskOverlay.setVisibility(View.GONE);
			taskOverlay.setClickable(false);

			this.getContentResolver().delete(Task.CONTENT_URI, null, null);
			mProgressHUD.dismiss();
			break;

		}
		case Constants.SUMMARY_RESPONSE_TOKEN: {
			data.moveToFirst();

			String json = data.getString(data
					.getColumnIndex(TaskHierarchy.JSON));
			Log.v(TAG, json);
			summaryResponse = new SummaryResponse();
			Gson gson = new Gson();
			summaryResponse = gson.fromJson(json, SummaryResponse.class);
			mProgressHUD.dismiss();

			this.getContentResolver().delete(TaskHierarchy.CONTENT_URI, null,
					null);
			break;
		}

		case Constants.PREFERENCE_TOKEN: {
			data.moveToFirst();

			String json = data.getString(data.getColumnIndex(Session.JSON));

			if (Helper.isMatching(taskTypeId)) {
				if (progressStatus < matchingTasks.getTasks().size() + 1) {
					postPrefernceService(json,
							matchingTasks.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							matchingTasks.getTasks()
									.get(matchingTasks.getTasks().size() - 1)
									.getSessionId());
				}

			} else if (Helper.isPatternTask(taskTypeId)) {
				if (progressStatus < patternTask.getTasks().size() + 1) {
					postPrefernceService(json,
							patternTask.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							patternTask.getTasks()
									.get(patternTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isCurrency(taskTypeId)) {
				if (progressStatus < currencyTask.getTasks().size() + 1) {
					postPrefernceService(json,
							currencyTask.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							currencyTask.getTasks()
									.get(currencyTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isDragandDrop(taskTypeId)) {
				if (progressStatus < taskDrag.getTasks().size() + 1) {
					postPrefernceService(json,
							taskDrag.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							taskDrag.getTasks()
									.get(taskDrag.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isAuditory(taskTypeId)) {
				if (progressStatus < auditoryTask.getTasks().size() + 1) {
					postPrefernceService(json,
							auditoryTask.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							auditoryTask.getTasks()
									.get(auditoryTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isOrderingTask(taskTypeId)) {
				if (progressStatus < orderTask.getTasks().size() + 1) {
					postPrefernceService(json,
							orderTask.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							orderTask.getTasks()
									.get(orderTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isActiveTask(taskTypeId)) {
				if (progressStatus < activeTask.getTasks().size() + 1) {
					postPrefernceService(json,
							activeTask.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							activeTask.getTasks()
									.get(activeTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isSpokenWordTask(taskTypeId)) {
				if (progressStatus < spokenWordTask.getTasks().size() + 1) {
					postPrefernceService(json,
							spokenWordTask.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							spokenWordTask.getTasks()
									.get(spokenWordTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isWrittenTask(taskTypeId)) {
				if (progressStatus < writtenTasks.getTasks().size() + 1) {
					postPrefernceService(json,
							writtenTasks.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							writtenTasks.getTasks()
									.get(writtenTasks.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isSymbolMatching(taskTypeId)) {
				if (progressStatus < symbolTasks.getTasks().size() + 1) {
					postPrefernceService(json,
							symbolTasks.getTasks().get(progressStatus - 1)
									.getSessionId());

				} else {
					postPrefernceService(
							json,
							symbolTasks.getTasks()
									.get(symbolTasks.getTasks().size() - 1)
									.getSessionId());
				}

			} else if (Helper.isPlayingCards(taskTypeId)) {
				if (progressStatus < playtask.getTasks().size() + 1) {
					postPrefernceService(json,
							playtask.getTasks().get(progressStatus - 1)
									.getSessionId());
				} else {
					postPrefernceService(
							json,
							playtask.getTasks()
									.get(playtask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isLetterToSound(taskTypeId)) {
				if (progressStatus < letterToSoundTask.getTasks().size() + 1) {
					postPrefernceService(json, letterToSoundTask.getTasks()
							.get(progressStatus - 1).getSessionId());
				} else {
					postPrefernceService(json, letterToSoundTask.getTasks()
							.get(letterToSoundTask.getTasks().size() - 1)
							.getSessionId());
				}
			} else if (Helper.isSoundToLetter(taskTypeId)) {
				if (progressStatus < soundToLetterTask.getTasks().size() + 1) {
					postPrefernceService(json, soundToLetterTask.getTasks()
							.get(progressStatus - 1).getSessionId());
				} else {
					postPrefernceService(json, soundToLetterTask.getTasks()
							.get(soundToLetterTask.getTasks().size() - 1)
							.getSessionId());
				}
			} else if (Helper.isMicrophoneTask(taskTypeId)) {

				if (progressStatus < microPhoneTask.getTasks().size() + 1) {
					postPrefernceService(json,
							microPhoneTask.getTasks().get(progressStatus - 1)
									.getSessionId());
				} else {
					postPrefernceService(
							json,
							microPhoneTask.getTasks()
									.get(microPhoneTask.getTasks().size() - 1)
									.getSessionId());
				}
			} else if (Helper.isWordProblem(taskTypeId)
					|| Helper.isArithmetic(taskTypeId)) {

				if (displayName.equalsIgnoreCase("Number Pattern")) {
					if (progressStatus < numberPatternTasks.getTasks().size() + 1) {
						postPrefernceService(json, numberPatternTasks
								.getTasks().get(progressStatus - 1)
								.getSessionId());
					} else {
						postPrefernceService(
								json,
								numberPatternTasks
										.getTasks()
										.get(numberPatternTasks.getTasks()
												.size() - 1).getSessionId());
					}
				} else {
					if (progressStatus < arithmaticTasks.getTasks().size() + 1) {
						postPrefernceService(json, arithmaticTasks.getTasks()
								.get(progressStatus - 1).getSessionId());
					} else {
						postPrefernceService(json, arithmaticTasks.getTasks()
								.get(arithmaticTasks.getTasks().size() - 1)
								.getSessionId());
					}
				}
			}

			break;
		}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancel(DialogInterface d) {
		// TODO Auto-generated method stub

		mProgressHUD.dismiss();
	}

	@Override
	public void onImageZoom(View v) {
		// TODO Auto-generated method stub
		showZoomImage(v.getTag().toString());
	}

	@Override
	public void OnStartButtonClick(View v) {
		// TODO Auto-generated method stub
		btnStart = (Button) v;
		if (btnNext.getVisibility() == View.GONE) {
			if (mPlayerLocal != null) {
				mPlayerLocal.stop();
				mPlayerLocal = null;
			}

			if (mPlayerLocal1 != null) {
				mPlayerLocal1.stop();
				mPlayerLocal1 = null;
			}
			if (btnStart.getText().toString()
					.equalsIgnoreCase(getString(R.string.start))) {

				startRecord();
				btnStart.setText(getString(R.string.stop));

			} else {
				btnStart.setText(getString(R.string.play));
				stopRecord();
				btnStart.setVisibility(View.INVISIBLE);

				flWait.setVisibility(View.VISIBLE);
				animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
				flWait.startAnimation(animation);
				final String audioUrl = microPhoneTask.getTasks()
						.get(progressStatus - 1).getResourceUrl()
						+ microPhoneTask.getTasks().get(progressStatus - 1)
								.getItem().getSoundPath();

				new Handler().postDelayed(new Runnable() {

					public void run() {
						stopAnim();
						if (validationAudio(voiceOutput, microPhoneTask
								.getTasks().get(progressStatus - 1).getItem()
								.getName())) {

							toast = AlertPopup
									.showCorrectToast(DoingTaskActivity.this);

							accuracy = 1;

							playSequenceAnswer(R.raw.correct_answer,
									R.raw.correct_the_answer_is, audioUrl);
							llMicrophone.setBackground(getResources()
									.getDrawable(R.drawable.circle_green_bg));

						} else {

							if (!displayName.contains("Practice")) {
								toast = AlertPopup
										.showWrongToast(DoingTaskActivity.this);
								accuracy = 0;
								playSequenceAnswer(R.raw.wrong_answer,
										R.raw.the_correct_answer_is, audioUrl);
							} else {
								accuracy = 1;
								playSequenceAnswer(R.raw.correct_answer,
										R.raw.the_correct_answer_is, audioUrl);
							}

						}

						btnStart.setVisibility(View.VISIBLE);

						btnNext.setVisibility(View.VISIBLE);
						endTime = Helper.getCurrentTimeStamp();
						callPostDataService(getResponseOfTask(microPhoneTask
								.getTasks().get(progressStatus - 1)
								.getSessionId(), progressStatus - 1, false));
						if (progressStatus < microPhoneTask.getTasks().size() + 1) {
							taskProgress.setProgress(progressStatus);
							progressStatus++;
						}

					}

				}, animation.getDuration());

			}

		}

	}

	@Override
	public void onInstructionClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMatchingImageClick(View v, int position) {
		// TODO Auto-generated method stub
		ViewAnimator viewAnimator = (ViewAnimator) v
				.findViewById(R.id.viewFlipper);
		ImageView imageCheck = (ImageView) v.findViewById(R.id.checkImage);
		if (v.findViewById(R.id.dummyImage).getVisibility() == View.VISIBLE
				&& !skipDelay) {

			createMatchingAnswer(position);

			if (currentTask.getResources().get(position).endsWith(".mp3")) {
				String url = currentTask.getResourceUrl()
						+ currentTask.getResources().get(position);
				playSound(url, false);
			}
			AnimationFactory.flipTransition(viewAnimator,
					FlipDirection.LEFT_RIGHT);
			tempPos++;
			if (tempView == null) {
				tempAnimator = viewAnimator;
				tempView = currentTask.getResources().get(position);
				tempImage = imageCheck;
			} else {
				if (tempView.equalsIgnoreCase(currentTask.getResources().get(
						position))) {
					tempView = null;
					tempPos = 0;
					skipDelay = true;
					pictureValidation(tempImage, imageCheck);

				} else {
					tempView = null;
					tempPos = 0;
					skipDelay = true;
					delay(viewAnimator);

				}
			}

		}
	}

	@Override
	public void onMatchingWordClick(View v, int position) {
		// TODO Auto-generated method stub
		ViewAnimator viewAnimator = (ViewAnimator) v
				.findViewById(R.id.viewFlipper);
		ImageView imageCheck = (ImageView) v.findViewById(R.id.checkImage);
		if (v.findViewById(R.id.dummyImage).getVisibility() == View.VISIBLE
				&& !skipDelay) {

			createMatchingAnswer(position);
			AnimationFactory.flipTransition(viewAnimator,
					FlipDirection.LEFT_RIGHT);
			tempPos++;
			if (tempView == null) {
				tempAnimator = viewAnimator;
				tempView = currentTask.getItems().get(position);
				tempImage = imageCheck;
			} else {
				if (tempView.equalsIgnoreCase(currentTask.getItems().get(
						position))) {
					tempView = null;
					tempPos = 0;
					skipDelay = true;
					pictureValidation(tempImage, imageCheck);

				} else {
					tempView = null;
					tempPos = 0;
					skipDelay = true;
					delay(viewAnimator);

				}
			}

		}
	}

	@Override
	public void onMatchingSymbolClick(View v, int position) {
		// TODO Auto-generated method stub

		ImageView imageCheck = (ImageView) v.findViewById(R.id.checkImage);
		if (imageCheck.getVisibility() != View.VISIBLE) {
			createMatchingAnswer(position);
			String[] url = currentSymbolTask.getMatchingSymbols().toString()
					.split("=");
			String imageUrl = url[0].substring(1);
			if (imageUrl.equalsIgnoreCase(currentSymbolTask.getItems().get(
					position))) {
				playLocalFile(R.raw.correct_answer);
				imageCheck.setVisibility(View.VISIBLE);
				imageCheck.setImageResource(R.drawable.correct);
				shake = AnimationUtils.loadAnimation(this, R.anim.bounce);
				v.startAnimation(shake);
				symbolCorrectCount++;
			} else {
				playLocalFile(R.raw.wrong_answer);
				shake = AnimationUtils.loadAnimation(this, R.anim.fast_shake);
				v.startAnimation(shake);
			}
		}

	}

	@Override
	public void onCheckSymbolClick(View v) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			symbolMatchingValidation();
		}

	}

	@Override
	public void onMicroAudioClick(View v) {
		// TODO Auto-generated method stub
		String url = microPhoneTask.getTasks().get(progressStatus - 1)
				.getResourceUrl()
				+ microPhoneTask.getTasks().get(progressStatus - 1).getItem()
						.getSoundPath();
		playSound(url, false);
	}

	@Override
	public void onCheckAnswerClick(View v, String inputAns) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			if (v.getTag().toString().equalsIgnoreCase("answer")) {
				showGridWindow(rootView, (View) etAnswer, etAnswer);
			} else {
				if (inputAns.length() > 0) {
					if (inputAns.startsWith("0"))
						inputAns = "" + Integer.parseInt(inputAns);

					if (inputAns.equalsIgnoreCase(arithmaticTasks.getTasks()
							.get(progressStatus - 1).getAnswer())) {
						toast = AlertPopup
								.showCorrectToast(DoingTaskActivity.this);
						playLocalFile(R.raw.correct_answer);
						etAnswer.setTextColor(getResources().getColor(
								R.color.text_green));
						etAnswer.setText(arithmaticTasks.getTasks()
								.get(progressStatus - 1).getAnswer());
						accuracy = 1;
					} else {
						toast = AlertPopup
								.showWrongToast(DoingTaskActivity.this);
						tvCheckAnswer.setText(inputAns);
						imgWrongSymbol.setVisibility(View.VISIBLE);
						etAnswer.setTextColor(getResources().getColor(
								R.color.text_green));
						etAnswer.setText(arithmaticTasks.getTasks()
								.get(progressStatus - 1).getAnswer());
						playLocalFile(R.raw.wrong_answer);
						accuracy = 0;
					}

					endTime = Helper.getCurrentTimeStamp();
					callPostDataService(getResponseOfTask(arithmaticTasks
							.getTasks().get(progressStatus - 1).getSessionId(),
							progressStatus - 1, false));
					if (progressStatus < arithmaticTasks.getTasks().size() + 1) {
						taskProgress.setProgress(progressStatus);
						progressStatus++;
					}

					btnNext.setVisibility(View.VISIBLE);

				} else {

					showAlert(getString(R.string.enter_response));
				}
				etAnswer.setCursorVisible(false);
				etAnswer.setInputType(0);
			}
		}
	}

	class MyRecognitionListener implements RecognitionListener {

		@Override
		public void onBeginningOfSpeech() {
			Log.d("Speech", "onBeginningOfSpeech");
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			Log.d("Speech", "onBufferReceived");
		}

		@Override
		public void onEndOfSpeech() {
			Log.d("Speech", "onEndOfSpeech");
		}

		@Override
		public void onError(int error) {
			Log.d("Speech", "onError");
			/*
			 * speechRecognizer.startListening(RecognizerIntent
			 * .getVoiceDetailsIntent(getApplicationContext()));
			 */
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			Log.d("Speech", "onEvent");
		}

		@Override
		public void onPartialResults(Bundle partialResults) {
			Log.d("Speech", "onPartialResults");
		}

		@Override
		public void onReadyForSpeech(Bundle params) {
			Log.d("Speech", "onReadyForSpeech");
		}

		@Override
		public void onResults(Bundle results) {
			Log.d("Speech", "==== onResults: ====");
			ArrayList<String> strlist = results
					.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			// tv.append("\nPossible matches:");

			for (int i = 0; i < strlist.size(); i++) {
				Log.d("Speech", strlist.get(i));
				// tv.append("\n> " + strlist.get(i));
				voiceOutput.add(strlist.get(i));
			}
			Log.d("Speech", "");
			// tv.append("\n");

			speechRecognizer.startListening(RecognizerIntent
					.getVoiceDetailsIntent(getApplicationContext()));

		}

		@Override
		public void onRmsChanged(float rmsdB) {
			// Log.d("Speech", "onRmsChanged");
		}

	}

	@Override
	public void onCheckDivisionAnswerClick(View v, String inputAns) {
		// TODO Auto-generated method stub
		if (btnNext.getVisibility() == View.GONE) {
			if (v.getTag().toString().equalsIgnoreCase("answer")) {
				showGridWindow(rootView, (View) etAnswer, etAnswer);
			} else {
				if (inputAns.length() > 0) {
					if (inputAns.startsWith("0"))
						inputAns = "" + Integer.parseInt(inputAns);
					validationWordProblem(inputAns);
				} else {
					showAlert(getString(R.string.enter_response));
				}
				etAnswer.setCursorVisible(false);
				etAnswer.setInputType(0);
			}
		}

	}

	@Override
	public void onCheckWordProblemClick(View v, String inputAns) {
		// TODO Auto-generated method stub
		isWordProblem = true;
		if (btnNext.getVisibility() == View.GONE) {
			if (v.getTag().toString().equalsIgnoreCase("answer")) {
				showGridWindow(rootView, (View) etAnswer, etAnswer);
			} else {
				if (inputAns.length() > 0) {

					if (inputAns.startsWith("0"))
						inputAns = "" + Integer.parseInt(inputAns);

					if (displayName.equalsIgnoreCase("Number Pattern")) {
						validationNumberPattern(inputAns);
					} else {
						validationWordProblem(inputAns);
					}
				} else {
					showAlert(getString(R.string.enter_response));
				}
				etAnswer.setCursorVisible(false);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(),
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}

		}
	}

	@Override
	public void onWrittenClick(View v, int position) {
		// TODO Auto-generated method stub
		if(btnNext.getVisibility() == View.GONE){
			dragView = v;
	
			String tag = v.findViewById(R.id.taskWord).getTag().toString();
			if (tag.equalsIgnoreCase("a"))
				tag = "a1";
			currentAns = tag;
	
			currentPos = position;
			String url = getString(R.string.resource_url) + tag + ".mp3";
			playSound(url, false);
			createActionAnswer(true, tag, isCorrectAudio(tag), position);
		}

	}

	@Override
	public void onWrittenDrag(View v, int position) {
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {

			dragView = v;
			// TODO Auto-generated method stub
			currentAns = v.findViewById(R.id.taskWord).getTag().toString();
			currentPos = position;
			Log.v(TAG, "" + currentAns);
			// v.setVisibility(View.INVISIBLE);
			ClipData.Item item = new ClipData.Item("");

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);

			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onMatchingDrag(View v) {
		// TODO Auto-generated method stub
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {

			dragView = v;

			Log.v(TAG, "enterintdrag");
			currentAns = v.getTag().toString();

			ClipData.Item item = new ClipData.Item("");

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);

			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onSoundClick(View v) {
		// TODO Auto-generated method stub
		if (Helper.isSoundToLetter(taskTypeId)) {
			String url = getString(R.string.resource_url)
					+ soundToLetterTask.getTasks().get(progressStatus - 1)
							.getPhonemeSoundPath();
			Log.v(TAG, url);
			playSound(url, false);
			createActionAnswer(true,
					soundToLetterTask.getTasks().get(progressStatus - 1)
							.getCorrectLetter(), false, 0);
		} else if (Helper.isSpokenWordTask(taskTypeId)) {
			String url = getString(R.string.resource_url)
					+ spokenWordTask.getTasks().get(progressStatus - 1)
							.getCorrectChoice().getSoundPath();
			Log.v(TAG, url);
			playSound(url, false);

		}
	}

	@Override
	public void onMatchingSoundClick(View v) {
		// TODO Auto-generated method stub
		if(btnNext.getVisibility() == View.GONE){
			String url = null;
			if (Helper.isLetterToSound(taskTypeId)) {
				url = getString(R.string.resource_url) + v.getTag().toString();
				Log.v(TAG, url);
				playSound(url, false);
				createActionAnswer(true, v.getTag().toString(), isCorrectAudio(v
						.getTag().toString()), getCurrentPos(v.getTag().toString()));
			}
		}
	}

	@Override
	public void onRefreshClick(View view) {

		if (btnNext.getVisibility() != View.VISIBLE
				&& innerView.getVisibility() == View.GONE) {

			innerView.setVisibility(View.VISIBLE);
			innerView.setClickable(true);
			List<ObjectAnimator> arrayListObjectAnimators = new ArrayList<ObjectAnimator>();
			for (int i = 0; i < pattenList.size(); i++) {
				for (int j = 0; j < pattenPostion.size(); j++) {
					if (pattenList.get(i).findViewById(R.id.dummyImage)
							.getTag() == pattenPostion.get(j)
							&& pattenList.get(i).findViewById(R.id.taskWord)
									.getVisibility() != View.VISIBLE) {

						ObjectAnimator anim = ObjectAnimator.ofObject(
								pattenList.get(i), "backgroundColor",
								new ArgbEvaluator(),
								getResources().getColor(R.color.yellow),
								getResources().getColor(R.color.login_gray));
						arrayListObjectAnimators.add(anim);

					}
				}
			}

			viewGlow(arrayListObjectAnimators);

			createPatternResponse("hint");
		}

	}

	@Override
	public void onPatternClick(View v, int position) {
		// TODO Auto-generated method stub

		CTTextView tvWord = (CTTextView) v.findViewById(R.id.taskWord);
		if (tvWord.getVisibility() != View.VISIBLE
				&& btnNext.getVisibility() != View.VISIBLE) {

			if (pattenPostion.contains(position)
					&& patternCurrentCount == getPatternText(position)) {
				playLocalFile(R.raw.correct_answer);
				tvWord.setVisibility(View.VISIBLE);
				tvWord.setText("" + getPatternText(position));
				patternCorrectCount++;
				createPatternAnswer("placeholderTap", true, position);
				patternCurrentCount++;

			} else {
				playLocalFile(R.raw.wrong_answer);
				shake = AnimationUtils.loadAnimation(this, R.anim.fast_shake);
				v.startAnimation(shake);
				createPatternAnswer("placeholderTap", false, position);
			}

			if (patternCorrectCount == pattenPostion.size()) {
				toast = AlertPopup.showCorrectToast(DoingTaskActivity.this);
				playLocalFile(R.raw.correct_answer);
				accuracy = 1;
				endTime = Helper.getCurrentTimeStamp();
				callPostDataService(getResponseOfTask(patternTask.getTasks()
						.get(progressStatus - 1).getSessionId(),
						progressStatus - 1, false));
				if (progressStatus < patternTask.getTasks().size() + 1) {
					taskProgress.setProgress(progressStatus);
					progressStatus++;
				}
				btnNext.setVisibility(View.VISIBLE);
			}
		}

	}

	public class MyDragEventListener implements View.OnDragListener {

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

				if (v == dropView) {

					if (Helper.isLetterToSound(taskTypeId)) {
						validationLetterToSound(currentAns);
						return true;
					} else if (Helper.isSoundToLetter(taskTypeId)) {
						validationSoundToLetter(currentAns);
						return true;
					} else if (Helper.isSpokenWordTask(taskTypeId)) {
						validationSpokenWord(currentAns);
					}
					return true;
				} else if (Helper.isDragandDrop(taskTypeId)) {
					View view = getListChildViewFronGrid(v);
					validationSequenceTask(v, view);
					return true;
				} else if (Helper.isWrittenTask(taskTypeId)) {

					View view = getChildViewFromGrid(v);
					validationWrittenTask(v, view);
					return true;
				} else if (Helper.isOrderingTask(taskTypeId)) {
					View view = getChildViewFromGrid(v);
					validationOrderTask(v, view);
					return true;
				} else if (Helper.isAuditory(taskTypeId)) {
					View view = getChildViewFromGrid(v);
					validationAuditoryTask(v, view);
					return true;
				} else if (Helper.isActiveTask(taskTypeId)) {
					View view = getChildViewFromGrid(v);
					validationActiveTask(v, view);
					return true;
				} else {
					dragView.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							dragView.setVisibility(View.VISIBLE);
						}

					});
					return false;
				}

			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {
					// dragView.setVisibility(View.VISIBLE);
				} else {
					dragView.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							dragView.setVisibility(View.VISIBLE);
						}

					});

				}

				return true;

			default: // unknown case

				return false;

			}
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
		try {
			Helper.trimCache(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		Log.v(TAG, "OnResume");
		startTime = Helper.getCurrentTimeStamp();

	}

	@Override
	public void onSpokenDrag(View v, int position) {
		// TODO Auto-generated method stub
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {

			dragView = v;
			Log.v(TAG, "enterintdrag");
			currentAns = v.findViewById(R.id.taskImage).getTag().toString();
			currentPos = position;
			ClipData.Item item = new ClipData.Item("");

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);

			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onActiveDrag(View v, int position) {
		// TODO Auto-generated method stub
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {
			dragView = v;

			currentAns = v.findViewById(R.id.taskWord).getTag().toString();
			currentPos = position;
			Log.v(TAG, "" + currentAns);

			ClipData.Item item = new ClipData.Item("");

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);

			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onOrderDrag(View v, int position) {
		// TODO Auto-generated method stub
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {

			dragView = v;

			if (taskTypeId.equalsIgnoreCase("20")) {
				currentAns = v.findViewById(R.id.taskWord).getTag().toString();
			} else {
				currentAns = v.findViewById(R.id.taskImage).getTag().toString();
			}
			currentPos = position;

			ClipData.Item item = new ClipData.Item("");

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);

			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onAuditoryDrag(View v, int position) {
		// TODO Auto-generated method stub
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {

			dragView = v;
			currentAns = v.findViewById(R.id.taskImage).getTag().toString();
			currentPos = position;
			Log.v(TAG, "" + currentAns);
			ClipData.Item item = new ClipData.Item("");

			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);

			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);

			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onSequenceDrag(View v, int position) {
		// TODO Auto-generated method stub
		if (v.getVisibility() == View.VISIBLE && btnNext.getVisibility() == View.GONE) {

			dragView = v;

			currentAns = v.findViewById(R.id.taskWord).getTag().toString();
			currentPos = position;
			ClipData.Item item = new ClipData.Item("");
			String[] clipDescription = { ClipDescription.MIMETYPE_TEXT_PLAIN };
			ClipData dragData = new ClipData("", clipDescription, item);
			View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
			if (v.startDrag(dragData, shadow, v, 0))
				v.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onSequenceClick(View v, int position) {
		// TODO Auto-generated method stub
		if(btnNext.getVisibility() == View.GONE){
			String tag = v.findViewById(R.id.taskWord).getTag().toString();
			createActionAnswer(true, tag, true, position);
		}
	}

	@Override
	public void OnPlayCardClick(final View v, final String taskLevel) {

		viewClick = true;

		PlayingCardFragment.viewFlipper.setInAnimation(AnimationUtils
				.loadAnimation(getApplicationContext(), R.anim.slide_right));
		PlayingCardFragment.viewFlipper.setOutAnimation(AnimationUtils
				.loadAnimation(getApplicationContext(), R.anim.slide_left));

		PlayingCardFragment.viewFlipper.getInAnimation().setAnimationListener(
				new Animation.AnimationListener() {
					public void onAnimationStart(Animation animation) 
					{
					
							

				
					}
						

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationEnd(Animation animation)
					{
						if (PlayingCardFragment.viewFlipper.getDisplayedChild() == 1) 
						{
							viewClick = false;
						}
						if(viewClick==false)
						
						playingTaskTimer(v, taskLevel,PlayingCardFragment.getChild());
						
						viewClick=false;
						

					}
				});
		if (skip == false) {
			DoingTaskActivity.playviewList
			.setVisibility(View.VISIBLE);
           DoingTaskActivity.playviewList.setClickable(true);
		
				PlayingCardFragment.viewFlipper.stopFlipping();
			
			
		
		} else if (PlayingCardFragment.getChild() == 0) {
			// v.setClickable(false);
		
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					PlayingCardFragment.viewFlipper.showNext();
					PlayingCardFragment.viewFlipper.setFlipInterval((int) 2
							* Constants.FLIP_INTERVEL);
					PlayingCardFragment.viewFlipper.startFlipping();

				}
			}, 200);

		}

		else if (displayName.equalsIgnoreCase("Playing Card Slapjack")) {
			playingTaskValidation(v);

		} else {

			playingMemoryVlaidation(v, taskLevel);

		}
		// Toast.makeText(getApplicationContext(),
		// String.valueOf(PlayingCardFragment.viewFlipper.indexOfChild(v)),
		// Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onCheckCurrencyClick(View v, String firstAns, String secondAns) {
		if (btnNext.getVisibility() == View.GONE) {
			if (v.getTag().toString().equalsIgnoreCase("answerFirst")
					|| v.getTag().toString().equalsIgnoreCase("answerSecond")) {
				showGridWindow(rootView, (View) v, (CTTextView) v);
			} else {

				String[] corrAns = new String[2];
				corrAns = currencyTask.getTasks().get(progressStatus - 1)
						.getAnswer().split("\\.");
				Log.v("secondAns", "" + corrAns[1]);

				if (firstAns.length() > 0 && secondAns.length() > 0
						&& Integer.valueOf(corrAns[1]) > 0
						|| Integer.valueOf(corrAns[1]) == 0) {

					validationCurrencyTask(firstAns, secondAns);

				}

				else {
					showAlert(getString(R.string.enter_response));
				}
			}
		}
	}

	@Override
	public void onOrderClick(View v, int position) {
		// TODO Auto-generated method stub
		if(btnNext.getVisibility() == View.GONE){
			String tag = null;
	
			if (taskTypeId.equalsIgnoreCase("20")) {
				tag = v.findViewById(R.id.taskWord).getTag().toString();
				String url = getString(R.string.resource_url) + tag + ".mp3";
				playSound(url, false);
	
			} else {
				tag = v.findViewById(R.id.taskImage).getTag().toString();
				String url = getString(R.string.resource_url)
						+ tag.replace(".jpg", ".mp3");
				playSound(url, false);
			}
			currentAns = tag;
			currentPos = position;
			}

	}

	@Override
	public void onSpokenClick(View v, int position) {
		// TODO Auto-generated method stub
		if(btnNext.getVisibility() == View.GONE){
			String tag = null;
	
			tag = v.findViewById(R.id.taskImage).getTag().toString();
			String url = getString(R.string.resource_url)
					+ tag.replace(".jpg", ".mp3");
			playSound(url, false);
	
			currentAns = tag;
			currentPos = position;
		}

	}

	@Override
	public void onAuditoryClick(View v, int position) {
		// TODO Auto-generated method stub
		if(btnNext.getVisibility() == View.GONE){
			String tag = null;
	
			tag = v.findViewById(R.id.taskImage).getTag().toString();
			String url = getString(R.string.resource_url)
					+ tag.replace(".jpg", ".mp3");
			playSound(url, false);
	
			currentAns = tag;
			currentPos = position;
		}

	}

	public void showGridWindow(ViewGroup rootView, View parent,
			final CTTextView answer) {

		popoverView = new PopoverView(getApplicationContext(),
				R.layout.level_popupgridview, false);

		WindowManager wm = (WindowManager) this
				.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		Point size = new Point();
		size.x = display.getWidth();
		size.y = display.getHeight();
		int width = popoverView.getWidth() + size.x;
		int height = popoverView.getHeight() + size.y;
		popoverView.setBackgroundColor(Color.TRANSPARENT);
		if (dm.DENSITY_MEDIUM == getResources().getDisplayMetrics().densityDpi) {
			popoverView.setContentSizeForViewInPopover(new Point(
					(int) (width / 3.2), (int) (height / 3.6)));
		} else {
			popoverView.setContentSizeForViewInPopover(new Point(width / 4,
					(int) (height / 2.6)));
		}

		popoverView.showPopoverFromRectInViewGroup(rootView,
				PopoverView.getFrameForView(parent),
				PopoverView.PopoverArrowDirectionAny, true);
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
				setItemCount(location, answer);

			}
		});

	}

	private void setItemCount(int location, CTTextView answer) {
		switch (location) {
		case 0:
			String value = "7";
			ProcessKeypadInput(value, answer);
			break;

		case 1:
			value = "8";
			ProcessKeypadInput(value, answer);
			break;

		case 2:
			value = "9";

			ProcessKeypadInput(value, answer);
			break;

		case 3:
			value = "4";
			ProcessKeypadInput(value, answer);
			break;

		case 4:
			value = "5";
			ProcessKeypadInput(value, answer);
			break;

		case 5:
			value = "6";
			ProcessKeypadInput(value, answer);
			break;

		case 6:
			value = "1";
			ProcessKeypadInput(value, answer);
			break;

		case 7:
			value = "2";
			ProcessKeypadInput(value, answer);
			break;

		case 8:
			value = "3";
			ProcessKeypadInput(value, answer);
			break;

		case 9:
			if (answer.getText().length() > 0) {
				tempText = answer.getText().toString();
				answer.setText(tempText.substring(0, tempText.length() - 1));
			} else {
				// showInternetAlert("Error!", "No item to delete");
			}
			break;

		case 10:
			value = "0";
			ProcessKeypadInput(value, answer);
			break;

		case 11:
			popoverView.dismissPopover(false);
			// showInternetAlert("Warning!", "No items selected");
			break;
		}

	}

	private void ProcessKeypadInput(String c, CTTextView answer) {
		answer.append(c);
		String temp = answer.getText().toString();
		// int intValue = Integer.parseInt(temp);
	}

}
