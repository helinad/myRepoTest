package com.constant_therapy.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.constant_therapy.provider.TherapyContract.CompliancelistColumns;
import com.constant_therapy.provider.TherapyContract.DashboardColumns;
import com.constant_therapy.provider.TherapyContract.Help;
import com.constant_therapy.provider.TherapyContract.LatestTaskTypelistColumns;
import com.constant_therapy.provider.TherapyContract.PatientsColumns;
import com.constant_therapy.provider.TherapyContract.PatientsListColumns;
import com.constant_therapy.provider.TherapyContract.ScoresListColumns;
import com.constant_therapy.provider.TherapyContract.SelectionListColumns;
import com.constant_therapy.provider.TherapyContract.Session;
import com.constant_therapy.provider.TherapyContract.Task;
import com.constant_therapy.provider.TherapyContract.TaskHomeworkColumns;
import com.constant_therapy.provider.TherapyContract.TypeBaseline;
import com.constant_therapy.provider.TherapyContract.TypeHierarchyColumns;
import com.constant_therapy.provider.TherapyContract.UserColumns;

public class TherapyDatabase extends SQLiteOpenHelper {
	
	private static final String TAG = "ConstantTherapyDatabase";
	
	private static final String DATABASE_NAME = "constanttherapy.db";
	private static final int DATABASE_VERSION = 8;
	
	public TherapyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	interface Tables {
		String USERS = "user";
		String DASHBOARD = "dashboard";
		String PATIENTS = "patients";
		String PATIENTSLIST = "patientslist";
		String SELECTIONLIST = "selectionList";
		String SCORESLIST = "scoreslist";
		String LATESTRESULTSLIST = "latestresultslist";
		String COMPILANCELIST = "compilancelist";
		String TYPEHIERARCHY = "typehierarchy";
		String TASKHOMEWORK = "taskhomework";
		String TYPEBASELINE = "typebaseline";
		String HELP = "helpoverlay";
		String TASK = "task";
		String SESSION = "session";
	}
	
	interface Views {
		String CUSTOM_ORDERS = "custom_orders";
	}
	
	private interface Triggers {
		String DASHBOARD_DELETE = "dashboard_delete";
		String SCORES_DELETE = "scores_delete";
		String LATESTRESULTS_DELETE = "latestresults_delete";
		
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.v("create", "create table");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.USERS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ UserColumns.PATIENT_ID + " TEXT NOT NULL, " 
				+ UserColumns.USERNAME + " TEXT,"
				+ UserColumns.FIRSTNAME + " TEXT,"
				+ UserColumns.LASTNAME + " TEXT,"
				+ UserColumns.USER_TYPE + " TEXT,"
				+ UserColumns.EMAIL + " TEXT,"
				+ UserColumns.PHONENUMBER + " TEXT," 
				+ UserColumns.ACCESSTOKEN + " TEXT,"
				+ UserColumns.ISDEMO + " BOOLEAN,"
				+ "UNIQUE (" + UserColumns.PATIENT_ID + ") ON CONFLICT REPLACE)"
				);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.DASHBOARD + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ DashboardColumns.PATIENT_ID + " TEXT NOT NULL,"
				+ DashboardColumns.JSON + " STRING,"
				+ "UNIQUE (" + DashboardColumns.PATIENT_ID + ") ON CONFLICT REPLACE)"
				);
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.PATIENTS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ PatientsColumns.PATIENT_ID + " TEXT NOT NULL, " 
				+ PatientsColumns.USERNAME + " TEXT,"
				+ PatientsColumns.FIRSTNAME + " TEXT,"
				+ PatientsColumns.LASTNAME + " TEXT,"
				+ PatientsColumns.USER_TYPE + " TEXT,"
				+ PatientsColumns.EMAIL + " TEXT,"
				+ PatientsColumns.ACCESSTOKEN + " TEXT,"
				+ PatientsColumns.ISDEMO + " BOOLEAN,"
				+ "UNIQUE (" + PatientsColumns.PATIENT_ID + ") ON CONFLICT REPLACE)"
				);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.PATIENTSLIST + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ PatientsListColumns.PATIENT_ID + " TEXT NOT NULL,"
				+ PatientsListColumns.USERNAME + " TEXT,"
				+ PatientsListColumns.IMAGEPATH + " TEXT,"
				+ "UNIQUE (" + PatientsListColumns.PATIENT_ID + ") ON CONFLICT REPLACE)"
				);
		
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.SELECTIONLIST + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ SelectionListColumns.PATIENT_ID + " TEXT NOT NULL,"
				+ SelectionListColumns.JSON + " STRING,"
				
				+ "UNIQUE (" + SelectionListColumns.JSON + ") ON CONFLICT REPLACE)"
				);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.COMPILANCELIST + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ CompliancelistColumns.PATIENT_ID + " TEXT,"
				+ CompliancelistColumns.JSON + " STRING,"
				+ CompliancelistColumns.DATE + " LONG,"
				+ CompliancelistColumns.DURATION + " TEXT,"
				+ CompliancelistColumns.INCLINIC + " BOOLEAN,"
				+ CompliancelistColumns.COMPLETEDTASKCOUNT + " INTEGER,"
				+ "UNIQUE (" + CompliancelistColumns.DATE + ") ON CONFLICT REPLACE)"
				);
				
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.SCORESLIST + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ ScoresListColumns.PATIENT_ID + " TEXT,"
				+ ScoresListColumns.JSON + " STRING,"
				+ ScoresListColumns.DATE + " LONG,"
				+ ScoresListColumns.ACCURACY + " DOUBLE,"
				+ ScoresListColumns.CUMULATIVEACCURACY + " DOUBLE,"
				+ "UNIQUE (" + ScoresListColumns.DATE + ") ON CONFLICT REPLACE)"
				);	
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.LATESTRESULTSLIST + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ LatestTaskTypelistColumns.PATIENT_ID + " TEXT,"
				+ LatestTaskTypelistColumns.JSON + " STRING,"
				
				+ "UNIQUE (" + LatestTaskTypelistColumns.JSON + ") ON CONFLICT REPLACE)"
				);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.TYPEHIERARCHY + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				
				+ TypeHierarchyColumns.JSON + " STRING,"
								
				+ "UNIQUE (" + TypeHierarchyColumns.JSON + ") ON CONFLICT REPLACE)"
				);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.TYPEBASELINE+ " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				
				+ TypeBaseline.SCORE_JSON + " STRING,"
								
				+ "UNIQUE (" + TypeBaseline.SCORE_JSON + ") ON CONFLICT REPLACE)"
				);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.TASKHOMEWORK + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				
				+ TaskHomeworkColumns.JSON + " STRING,"
						
				+ "UNIQUE (" + TaskHomeworkColumns.JSON + ") ON CONFLICT REPLACE)"
				);	
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.HELP + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ Help.JSON + " STRING,"
				+ "UNIQUE (" + Help.JSON + ") ON CONFLICT REPLACE)"
				);	
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.TASK + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ Task.JSON + " STRING,"
				+ "UNIQUE (" + Task.JSON + ") ON CONFLICT REPLACE)"
				);	
		db.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.SESSION + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ Session.JSON + " STRING,"
				+ "UNIQUE (" + Session.JSON + ") ON CONFLICT REPLACE)"
				);	
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(oldVersion<newVersion){
			Log.d(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.USERS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.DASHBOARD);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PATIENTS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PATIENTSLIST);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.SELECTIONLIST);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.SCORESLIST);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.LATESTRESULTSLIST);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.COMPILANCELIST);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.TYPEHIERARCHY);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.TYPEBASELINE);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.TASKHOMEWORK);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.HELP);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.TASK);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.SESSION);
			
			db.execSQL("DROP TRIGGER IF EXISTS " + Triggers.DASHBOARD_DELETE);
			db.execSQL("DROP TRIGGER IF EXISTS " + Triggers.SCORES_DELETE);
			db.execSQL("DROP TRIGGER IF EXISTS " + Triggers.LATESTRESULTS_DELETE);
			
			
			// add more execs for more tables
			onCreate(db);
		}
	}

}
