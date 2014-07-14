package com.constant_therapy.provider;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.constant_therapy.provider.TherapyContract.ComplianceList;
import com.constant_therapy.provider.TherapyContract.Dashboards;
import com.constant_therapy.provider.TherapyContract.Help;
import com.constant_therapy.provider.TherapyContract.LatestTypeResultsList;
import com.constant_therapy.provider.TherapyContract.Patients;
import com.constant_therapy.provider.TherapyContract.PatientsList;
import com.constant_therapy.provider.TherapyContract.ScoresList;
import com.constant_therapy.provider.TherapyContract.SelectionList;
import com.constant_therapy.provider.TherapyContract.Session;
import com.constant_therapy.provider.TherapyContract.Task;
import com.constant_therapy.provider.TherapyContract.TaskHierarchy;
import com.constant_therapy.provider.TherapyContract.TaskHomework;
import com.constant_therapy.provider.TherapyContract.TypeBaseline;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.provider.TherapyDatabase.Tables;
import com.constant_therapy.util.SelectionBuilder;


public class TherapyProvider extends ContentProvider {

	private static final String TAG = "TherapyProvider";

	private static final UriMatcher sUriMatcher = buildUriMatcher();
	private TherapyDatabase mDatabase;

	public static final int USERS = 100;
	public static final int USERS_ID = 101;

	public static final int DASHBOARD = 200;
	public static final int DASHBOARD_ID = 201;

	public static final int SCORES = 300;
	public static final int SCORES_ID = 301;

	public static final int CUSTOM_ORDERS = 400;

	public static final int LATESTRESULTS_ID = 501;
	public static final int LATESTRESULTS = 500;

	public static final int PATIENTS = 600;
	public static final int PATIENTS_ID = 601;
	
	public static final int PATIENTSLIST = 700;
	public static final int PATIENTSLIST_ID = 701;
	
	public static final int SELECTIONLIST = 800;
	public static final int SELECTIONLIST_ID = 801;
	
	public static final int SCORESLIST = 900;
	public static final int SCORESLIST_ID = 901;

	
	public static final int LATESTRESULTSLIST_ID = 1001;
	public static final int LATESTRESULTSLIST = 1000;
	
	public static final int COMPILANCELIST_ID = 1101;
	public static final int COMPILANCELIST = 1100;
	
	public static final int TYPE_HIERARCHY_ID = 1201;
	public static final int TYPE_HIERARCHY = 1200;
	
	public static final int TASK_HOMEWORK_ID = 1301;
	public static final int TASK_HOMEWORK = 1300;
	
	public static final int TYPE_BASELINE_ID = 1401;
	public static final int TYPE_BASELINE = 1400;
	
	public static final int HELP_ID = 1501;
	public static final int HELP = 1500;
	
	public static final int TASK_ID = 1601;
	public static final int TASK = 1600;
	
	public static final int SESSION_ID = 1701;
	public static final int SESSION = 1700;
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		//Log.v(TAG, "delete(uri=" + uri + ")");

		final SQLiteDatabase db = mDatabase.getWritableDatabase();

		final SelectionBuilder builder = buildSimpleSelection(uri);
		int retVal = builder.where(where, whereArgs).delete(db);
		getContext().getContentResolver().notifyChange(uri, null);
		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case USERS:
			return Users.CONTENT_TYPE;
		case USERS_ID:
			return Users.CONTENT_ITEM_TYPE;
		case DASHBOARD:
			return Dashboards.CONTENT_TYPE;
		case DASHBOARD_ID:
			return Dashboards.CONTENT_ITEM_TYPE;
		
		case SCORESLIST:
			return ScoresList.CONTENT_TYPE;
		case SCORESLIST_ID:
			return ScoresList.CONTENT_ITEM_TYPE;
		case LATESTRESULTSLIST:
			return LatestTypeResultsList.CONTENT_TYPE;
		case LATESTRESULTSLIST_ID:
			return LatestTypeResultsList.CONTENT_ITEM_TYPE;
		case PATIENTS:
			return Patients.CONTENT_TYPE;
		case PATIENTS_ID:
			return Patients.CONTENT_ITEM_TYPE;
		case PATIENTSLIST:
			return Patients.CONTENT_TYPE;
		case PATIENTSLIST_ID:
			return Patients.CONTENT_ITEM_TYPE;
		case SELECTIONLIST:
			return SelectionList.CONTENT_TYPE;
		case SELECTIONLIST_ID:
			return SelectionList.CONTENT_ITEM_TYPE;
		case COMPILANCELIST:
			return ComplianceList.CONTENT_TYPE;
		case COMPILANCELIST_ID:
			return ComplianceList.CONTENT_ITEM_TYPE;
		case TYPE_HIERARCHY:
			return TaskHierarchy.CONTENT_TYPE;
		case TYPE_HIERARCHY_ID:
			return TaskHierarchy.CONTENT_ITEM_TYPE;
		case TASK_HOMEWORK:
			return TaskHomework.CONTENT_TYPE;
		case TASK_HOMEWORK_ID:
			return TaskHomework.CONTENT_ITEM_TYPE;
		case TYPE_BASELINE:
			return TypeBaseline.CONTENT_TYPE;
		case TYPE_BASELINE_ID:
			return TypeBaseline.CONTENT_ITEM_TYPE;
		case HELP:
			return Help.CONTENT_TYPE;
		case HELP_ID:
			return Help.CONTENT_ITEM_TYPE;
		case TASK:
			return Help.CONTENT_TYPE;
		case TASK_ID:
			return Help.CONTENT_ITEM_TYPE;
		case SESSION:
			return Session.CONTENT_TYPE;
		case SESSION_ID:
			return Session.CONTENT_ITEM_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//Log.v(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");

		final SQLiteDatabase db = mDatabase.getWritableDatabase();
		final int match = sUriMatcher.match(uri);

		switch (match) {
		case USERS: {
			db.insertOrThrow(Tables.USERS, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Users.buildOrdersUri(values.getAsString(Users.PATIENT_ID));
		}
		case DASHBOARD: {
			db.insertOrThrow(Tables.DASHBOARD, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Dashboards.buildOrdersUri(values
					.getAsString(Dashboards.PATIENT_ID));
		}
				
		case SCORESLIST: {
			db.insertOrThrow(Tables.SCORESLIST, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return ScoresList.buildOrdersUri(values.getAsString(ScoresList.PATIENT_ID));
		}

		case LATESTRESULTSLIST: {
			db.insertOrThrow(Tables.LATESTRESULTSLIST, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return LatestTypeResultsList.buildOrdersUri(values
					.getAsString(LatestTypeResultsList.PATIENT_ID));
		}
		
		case PATIENTS: {
			db.insertOrThrow(Tables.PATIENTS, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Patients.buildOrdersUri(values
					.getAsString(Patients.PATIENT_ID));
		}
		
		case PATIENTSLIST: {
			db.insertOrThrow(Tables.PATIENTSLIST, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return PatientsList.buildOrdersUri(values
					.getAsString(Patients.PATIENT_ID));
		}
		
		case SELECTIONLIST: {
			db.insertOrThrow(Tables.SELECTIONLIST, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return SelectionList.buildOrdersUri(values
					.getAsString(SelectionList.PATIENT_ID));
		}
		
		case COMPILANCELIST: {
			db.insertOrThrow(Tables.COMPILANCELIST, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return ComplianceList.buildOrdersUri(values
					.getAsString(ComplianceList.PATIENT_ID));
		}
		
		case TYPE_HIERARCHY: {
			db.insertOrThrow(Tables.TYPEHIERARCHY, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return TaskHierarchy.buildOrdersUri(values
					.getAsString(Patients.PATIENT_ID));
		}
		
		case TASK_HOMEWORK: {
			db.insertOrThrow(Tables.TASKHOMEWORK, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return TaskHomework.buildOrdersUri(values
					.getAsString(TaskHomework.PATIENT_ID));
		}
		
		
		case TYPE_BASELINE: {
			db.insertOrThrow(Tables.TYPEBASELINE, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return TypeBaseline.buildOrdersUri(values
					.getAsString(TypeBaseline.PATIENT_ID));
		}
		
		case HELP: {
			
			db.insertOrThrow(Tables.HELP, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Help.buildOrdersUri(values
					.getAsString(Help.PATIENT_ID));
		}
		case TASK: {
			
			db.insertOrThrow(Tables.TASK, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Task.buildOrdersUri(values.getAsString(Task.PATIENT_ID));
		}
		
		case SESSION: {
			
			db.insertOrThrow(Tables.SESSION, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Session.buildOrdersUri(values.getAsString(Session.PATIENT_ID));
		}

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		final Context context = getContext();
		mDatabase = new TherapyDatabase(context);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//Log.v(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection)
		//		+ ")");
		final SQLiteDatabase db = mDatabase.getReadableDatabase();

		final int match = sUriMatcher.match(uri);
		switch (match) {
		default:
			final SelectionBuilder builder = buildExpandedSelection(uri, match);

			Cursor c = builder.where(selection, selectionArgs).query(db,
					projection, sortOrder);
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Build and return a {@link UriMatcher} that catches all {@link Uri}
	 * variations supported by this {@link ContentProvider}.
	 */
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = TherapyContract.CONTENT_AUTHORITY;

		matcher.addURI(authority, "users", USERS);
		matcher.addURI(authority, "users/*", USERS_ID);

		matcher.addURI(authority, "dashboard", DASHBOARD);
		matcher.addURI(authority, "dashboard/*", DASHBOARD_ID);
		
		matcher.addURI(authority, "scoreslist", SCORESLIST);
		matcher.addURI(authority, "scoreslist/*", SCORESLIST_ID);

		matcher.addURI(authority, "latestresultslist", LATESTRESULTSLIST);
		matcher.addURI(authority, "latestresultslist/*", LATESTRESULTSLIST_ID);
		
		
		matcher.addURI(authority, "patients", PATIENTS);
		matcher.addURI(authority, "patients/*", PATIENTS_ID);
		
		matcher.addURI(authority, "patientslist", PATIENTSLIST);
		matcher.addURI(authority, "patientslist/*", PATIENTSLIST_ID);
		
		matcher.addURI(authority, "selectionlist", SELECTIONLIST);
		matcher.addURI(authority, "selectionlist/*", SELECTIONLIST_ID);
		
		matcher.addURI(authority, "compilancelist", COMPILANCELIST);
		matcher.addURI(authority, "compilancelist/*", COMPILANCELIST_ID);
		
		matcher.addURI(authority, "typehierarchy", TYPE_HIERARCHY);
		matcher.addURI(authority, "typehierarchy/*", TYPE_HIERARCHY_ID);
		
		matcher.addURI(authority, "taskhomework", TASK_HOMEWORK);
		matcher.addURI(authority, "taskhomework/*", TASK_HOMEWORK_ID);
		
		matcher.addURI(authority, "typebaseline", TYPE_BASELINE);
		matcher.addURI(authority, "typebaseline/*", TYPE_BASELINE_ID);

		matcher.addURI(authority, "helpoverlay", HELP);
		matcher.addURI(authority, "helpoverlay/*", HELP_ID);
		
		matcher.addURI(authority, "task", TASK);
		matcher.addURI(authority, "task/*", TASK_ID);
		
		matcher.addURI(authority, "session", SESSION);
		matcher.addURI(authority, "session/*", SESSION_ID);

		return matcher;
	}

	/**
	 * Build an advanced {@link SelectionBuilder} to match the requested
	 * {@link Uri}. This is usually only used by {@link #query}, since it
	 * performs table joins useful for {@link Cursor} data.
	 */
	private SelectionBuilder buildExpandedSelection(Uri uri, int match) {

		final SelectionBuilder builder = new SelectionBuilder();

		switch (match) {
		case USERS: {
			return builder.table(Tables.USERS);
		}
		case USERS_ID: {
			final String orderId = Users.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId);
			return builder.table(Tables.USERS).where(Users.PATIENT_ID + "=?",
					orderId);
		}
		case DASHBOARD: {
			return builder.table(Tables.DASHBOARD);
		}
		case DASHBOARD_ID: {
			final String customerId = Dashboards.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: customerId=" + customerId);
			return builder.table(Tables.DASHBOARD).where(
					Dashboards.PATIENT_ID + "=?", customerId);
		}
				
		case PATIENTS: {
			return builder.table(Tables.PATIENTS);
		}
		case PATIENTS_ID: {
			final String orderId2 = Patients.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.PATIENTS).where(
					Patients.PATIENT_ID + "=?", orderId2);
		}
		
		case PATIENTSLIST: {
			return builder.table(Tables.PATIENTSLIST);
		}
		case PATIENTSLIST_ID: {
			final String orderId2 = PatientsList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.PATIENTSLIST).where(
					PatientsList.PATIENT_ID + "=?", orderId2);
		}
		
		case SELECTIONLIST: {
			return builder.table(Tables.SELECTIONLIST);
		}
		case SELECTIONLIST_ID: {
			final String orderId2 = SelectionList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.SELECTIONLIST).where(
					SelectionList.PATIENT_ID + "=?", orderId2);
		}
		
		
		case SCORESLIST: {
			return builder.table(Tables.SCORESLIST);
		}
		case SCORESLIST_ID: {
			final String addressId = ScoresList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: addressId=" + addressId);
			return builder.table(Tables.SCORESLIST).where(ScoresList.PATIENT_ID + "=?",
					addressId);
		}
		case LATESTRESULTSLIST: {
			return builder.table(Tables.LATESTRESULTSLIST);
		}
		case LATESTRESULTSLIST_ID: {
			final String orderId2 = LatestTypeResultsList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.LATESTRESULTSLIST).where(
					LatestTypeResultsList.PATIENT_ID + "=?", orderId2);
		}
		
		case COMPILANCELIST: {
			return builder.table(Tables.COMPILANCELIST);
		}
		case COMPILANCELIST_ID: {
			final String orderId2 = ComplianceList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.COMPILANCELIST).where(
					ComplianceList.PATIENT_ID + "=?", orderId2);
		}
		
		case TYPE_HIERARCHY: {
			return builder.table(Tables.TYPEHIERARCHY);
		}
		case TYPE_HIERARCHY_ID: {
			final String orderId2 = TaskHierarchy.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TYPEHIERARCHY).where(
					TaskHierarchy.PATIENT_ID + "=?", orderId2);
		}
			
		case TYPE_BASELINE: {
			return builder.table(Tables.TYPEBASELINE);
		}
		case TYPE_BASELINE_ID: {
			final String orderId2 = TypeBaseline.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TYPEBASELINE).where(
					TypeBaseline.PATIENT_ID + "=?", orderId2);
		}
				
		case TASK_HOMEWORK: {
			return builder.table(Tables.TASKHOMEWORK);
		}
		case TASK_HOMEWORK_ID: {
			final String orderId2 = TaskHomework.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TASKHOMEWORK).where(
					TaskHomework.PATIENT_ID + "=?", orderId2);
		}
		case HELP:{
			
			return builder.table(Tables.HELP);
		}
		case HELP_ID:{
    		final String orderId2 = Task.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.HELP).where(
					Task.PATIENT_ID + "=?", orderId2);
		} 
		case TASK:{
			return builder.table(Tables.TASK);
		}case TASK_ID:{
   		final String orderId2 = Task.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TASK).where(
					Task.PATIENT_ID + "=?", orderId2);
		}
		case SESSION:{
			return builder.table(Tables.SESSION);
		}case SESSION_ID:{
   		final String orderId2 = Session.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.SESSION).where(
					Session.PATIENT_ID + "=?", orderId2);
		}
		default: {
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		}
	}

	/**
	 * Build a simple {@link SelectionBuilder} to match the requested
	 * {@link Uri}. This is usually enough to support {@link #insert},
	 * {@link #update}, and {@link #delete} operations.
	 */
	private SelectionBuilder buildSimpleSelection(Uri uri) {
		final SelectionBuilder builder = new SelectionBuilder();
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case USERS: {
			return builder.table(Tables.USERS);
		}
		case USERS_ID: {
			final String orderId = Users.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId);
			return builder.table(Tables.USERS).where(Users.PATIENT_ID + "=?",
					orderId);
		}
		case DASHBOARD: {
			return builder.table(Tables.DASHBOARD);
		}
		case DASHBOARD_ID: {
			final String customerId = Dashboards.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: customerId=" + customerId);
			return builder.table(Tables.DASHBOARD).where(
					Dashboards.PATIENT_ID + "=?", customerId);
		}
		
		case PATIENTS: {
			return builder.table(Tables.PATIENTS);
		}
		case PATIENTS_ID: {
			final String orderId2 = Patients.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.PATIENTS).where(
					Patients.PATIENT_ID + "=?", orderId2);
		}
		
		case PATIENTSLIST: {
			return builder.table(Tables.PATIENTSLIST);
		}
		case PATIENTSLIST_ID: {
			final String orderId2 = PatientsList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.PATIENTSLIST).where(
					PatientsList.PATIENT_ID + "=?", orderId2);
		}
		
		case SELECTIONLIST: {
			return builder.table(Tables.SELECTIONLIST);
		}
		case SELECTIONLIST_ID: {
			final String orderId2 = SelectionList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.SELECTIONLIST).where(
					SelectionList.PATIENT_ID + "=?", orderId2);
		}
		
		case SCORESLIST: {
			return builder.table(Tables.SCORESLIST);
		}
		case SCORESLIST_ID: {
			final String addressId = ScoresList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: addressId=" + addressId);
			return builder.table(Tables.SCORESLIST).where(ScoresList.PATIENT_ID + "=?",
					addressId);
		}
		case LATESTRESULTSLIST: {
			return builder.table(Tables.LATESTRESULTSLIST);
		}
		case LATESTRESULTSLIST_ID: {
			final String orderId2 = LatestTypeResultsList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.LATESTRESULTSLIST).where(
					LatestTypeResultsList.PATIENT_ID + "=?", orderId2);
		}
		
		case COMPILANCELIST: {
			return builder.table(Tables.COMPILANCELIST);
		}
		case COMPILANCELIST_ID: {
			final String orderId2 = ComplianceList.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.COMPILANCELIST).where(
					ComplianceList.PATIENT_ID + "=?", orderId2);
		}
		
		case TYPE_HIERARCHY: {
			return builder.table(Tables.TYPEHIERARCHY);
		}
		case TYPE_HIERARCHY_ID: {
			final String orderId2 = TaskHierarchy.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TYPEHIERARCHY).where(
					TaskHierarchy.PATIENT_ID + "=?", orderId2);
		}
		
		case TASK_HOMEWORK: {
			return builder.table(Tables.TASKHOMEWORK);
		}
		case TASK_HOMEWORK_ID: {
			final String orderId2 = TaskHomework.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TASKHOMEWORK).where(
					TaskHomework.PATIENT_ID + "=?", orderId2);
		}
		
		case TYPE_BASELINE: {
			return builder.table(Tables.TYPEBASELINE);
		}
		case TYPE_BASELINE_ID: {
			final String orderId2 = TypeBaseline.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TYPEBASELINE).where(
					TypeBaseline.PATIENT_ID + "=?", orderId2);
		}
		case HELP:{
			
			return builder.table(Tables.HELP);
		}
		case HELP_ID:{
    		final String orderId2 = Help.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.HELP).where(
					Help.PATIENT_ID + "=?", orderId2);
		}
		
		case TASK:{
			return builder.table(Tables.TASK);
		}case TASK_ID:{
   		final String orderId2 = Task.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.TASK).where(
					Task.PATIENT_ID + "=?", orderId2);
		}
		
		case SESSION:{
			return builder.table(Tables.SESSION);
		}case SESSION_ID:{
   		final String orderId2 = Session.getOrderId(uri);
			Log.d(TAG, "SimpleSelection: orderId=" + orderId2);
			return builder.table(Tables.SESSION).where(
					Session.PATIENT_ID + "=?", orderId2);
		}
		default: {
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		}
	}

	/**
	 * Apply the given set of {@link ContentProviderOperation}, executing inside
	 * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
	 * any single one fails.
	 */
	@Override
	public ContentProviderResult[] applyBatch(
			ArrayList<ContentProviderOperation> operations)
			throws OperationApplicationException {

		if (operations == null) {
			throw new OperationApplicationException(
					"Operations had a null value while trying to apply them in batch.");
		}

		final SQLiteDatabase db = mDatabase.getWritableDatabase();
		db.beginTransaction();
		try {
			final int numOperations = operations.size();
			final ContentProviderResult[] results = new ContentProviderResult[numOperations];
			for (int i = 0; i < numOperations; i++) {
				results[i] = operations.get(i).apply(this, results, i);
			}
			db.setTransactionSuccessful();
			return results;
		} finally {
			db.endTransaction();
		}
	}
}
