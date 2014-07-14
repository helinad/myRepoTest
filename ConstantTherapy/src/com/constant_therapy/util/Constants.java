package com.constant_therapy.util;

public class Constants {
	
	//Tokens for redirecting processors
	public static final int LOGIN_TOKEN = 800;
	
	/** login with access token */
	public static final int ACCESS_TOKEN_LOGIN_TOKEN = 801;
	
	public static final int DASHBOARD_TOKEN = 100;
	
	public static final int GET_PATIENT_LIST_TOKEN = 200;
	public static final int GET_PATIENT_IMAGEPATH_TOKEN = 201;
	
	public static final int CLINICIAN_MIDDLE_ROW_TOKEN = 202;
	public static final int CLINICIAN_PLOT_LATENCY_TOKEN = 203;
	public static final int CLINICIAN_PLOT_ACCURACY_TOKEN = 204;
	
	public static final int CLINICIAN_TASKS_HIERARCHY_TOKEN = 205;
	public static final int CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN = 206;
	public static final int CLINICIAN_TASKS_HOMEWORK_TOKEN = 207;
	public static final int CLINICIAN_TASKS_SAVE_TOKEN = 208;
	public static final int CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN = 209;
	
	public static final int DOING_TASKS_TOKEN = 211;
	public static final int DELETE_TOKEN = 212;
	public static final int SUMMARY_RESPONSE_TOKEN = 213;
	public static final int MULIT_TOKEN = 214;
	public static final int PREFERENCE_TOKEN = 215;
	public static final int PREFERENCE_POST_TOKEN = 216;
	public static final int TASK_BASELINE_TOKEN = 217;
	
	
	public static final int UPDATE_ACCOUNT_INFO = 218;
	public static final int VALIDATE_EMAIL_TOKEN = 219;
	public static final int UPDATE_ACCOUNT_TOKEN = 230;
	public static final int CHANGE_PASSWORD_TOKEN = 231;
	/*
	 * @Developed By: Arumugam
	 */
	public static final int HELP_OVERLAY_TOKEN = 210;
	
	// Constants for Login validation.
	public static final int PATIENT = 101;
	public static final int CLINICAN = 102;
	public static final int INVALID_ACCESSTOKEN = 103;
	
	/*
	 * @Developed By: Arumugam
	 */
	
	public static final int PATIENTLOGINSUMMARY = 1;
	public static final int PATIENTLOGINTASK = 2;
	public static final int CLINICIANSUMMARY = 3;
	public static final int CLINICIANTASKS = 4;
	public static final int PATIENTLOGINLEFT = 5;
	public static final int PATIENTLOGINRIGHT = 6;
	
	
	
	// x axis round off value.
	public static final Double CHART_SLICE = 7.0d;
	
	// define the No of x axis labels based of device.
	public static final int CHART_DISTANCE = 18;
	
	
	//Dashboard chart values font size
	
	public static final int MAINCHART_LABEL = 35;
	public static final int MAINCHART_TICKLABEL = 25;
	
	public static final int SUBCHART_LABEL = 38;
	public static final int SUBCHART_TICKLABEL = 25;
	
	public static final int CHART_INTERVAL = 22;
	
	public static final int GLOW_ANIM_DURATION = 500;
	
	//Default value for assigned homework
	public static final int TASKCOUNT = 10;
	public static final int TASKLEVEL = 1;
	
	
	//Default value for Task Popup
		public static final int POPUP_TASKCOUNT = 15;
		public static final int POPUP_TASKLEVEL = 1;
	
	//Task list items.
	
	public static final int SWIPE_THRESHOLD  = 150;
	
	
	public static final int REMOVEITEM_DURATION = 3000; // 3 sec
	
	public static final int SKIP_DELAY = 1000; // 1 sec
	
	public static final int SHIFT_DELAY = 1500; // 1 sec
	
	public static final int FLIP_INTERVEL = 1000;
	
	public static final String IMAGE_CONSTANT = "2.0.0";
	
	
	public static final String TEMP_JSON = "https://www.dropbox.com/s/8ajfp0vr48opbnp/task.json";
		
}
