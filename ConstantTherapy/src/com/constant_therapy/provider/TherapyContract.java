package com.constant_therapy.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class TherapyContract {
	interface UserColumns {
		String PATIENT_ID = "id";
		String USERNAME = "username";
		String FIRSTNAME = "firstName";
		String LASTNAME = "lastName";
		String EMAIL = "email";
		String USER_TYPE = "type";
		String PHONENUMBER = "phone";
		String ACCESSTOKEN = "accessToken";
		String ISDEMO = "isDemo";
	}

	interface PatientsColumns {
		String PATIENT_ID = "id";
		String USERNAME = "username";
		String FIRSTNAME = "firstName";
		String LASTNAME = "lastName";
		String EMAIL = "email";
		String USER_TYPE = "type";
		String ACCESSTOKEN = "accessToken";
		String ISDEMO = "isDemo";
	}

	interface PatientsListColumns {
		String PATIENT_ID = "id";
		String USERNAME = "username";
		String IMAGEPATH = "imagePath";
	}

	interface DashboardColumns {
		String PATIENT_ID = "id";
		String JSON = "json";
		String ACCURACY = "accuracyAverage";

	}

	interface ScoresListColumns {
		String PATIENT_ID = "id";
		String JSON = "json";
		String DATE = "date";
		String ACCURACY = "accuracy";
		String CUMULATIVEACCURACY = "cummulativeAccuracy";

	}

	interface LatestTaskTypelistColumns {
		String PATIENT_ID = "id";
		String JSON = "json";

	}

	interface CompliancelistColumns {
		String PATIENT_ID = "id";
		String JSON = "json";
		String DATE = "date";
		String DURATION = "duration";
		String COMPLETEDTASKCOUNT = "completedTaskCount";
		String INCLINIC = "inClinic";
	}

	interface SelectionListColumns {
		String PATIENT_ID = "id";
		String JSON = "json";

	}

	interface TypeHierarchyColumns {
		String PATIENT_ID = "id";
		String JSON = "json";

	}

	interface TypeBaselineColumns {
		String PATIENT_ID = "id";

		String SCORE_JSON = "scorejson";

	}

	interface TaskHomeworkColumns {
		String PATIENT_ID = "id";
		String JSON = "json";

	}

	interface HelpColumns {
		String PATIENT_ID = "id";
		String JSON = "json";
	}
	
	interface TaskColumns {
		String PATIENT_ID = "id";
		String JSON = "json";
	}
	
	interface SessionColumns {
		String PATIENT_ID = "id";
		String JSON = "json";
	}

	public static final String CONTENT_AUTHORITY = "com.constant_therapy.provider";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	private static final String PATH_User = "users";
	private static final String PATH_DASHBOARD = "dashboard";
	private static final String PATH_PATIENTS = "patients";
	private static final String PATH_PATIENTSLIST = "patientslist";
	private static final String PATH_SELECTIONLIST = "selectionlist";
	private static final String PATH_SCORESLIST = "scoreslist";
	private static final String PATH_LATESTTASKLIST = "latestresultslist";
	private static final String PATH_COMPILANCELIST = "compilancelist";
	private static final String PATH_TYPEHIERARCHY = "typehierarchy";
	private static final String PATH_TASKHOMEWORK = "taskhomework";
	private static final String PATH_TYPEBASELINE = "typebaseline";
	private static final String PATH_HELP = "helpoverlay";
	private static final String PATH_TASK = "task";
	private static final String PATH_SESSION = "session";

	/**
	 * Orders contain customer information and address information along with
	 * the described fields.
	 */
	public static class Users implements UserColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_User).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.users";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.users";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * Dashboard contain accuracy information of doughnut chart along with the
	 * described fields.
	 */
	public static class Dashboards implements DashboardColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_DASHBOARD).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.dashboard";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.dashboard";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * Patients contains the information of middle row list along with the
	 * described fields.
	 */
	public static class Patients implements PatientsColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_PATIENTS).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.patients";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.patients";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * Patients contains the information of middle row list along with the
	 * described fields.
	 */
	public static class PatientsList implements PatientsListColumns,
			BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_PATIENTSLIST).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.patientslist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.patientslist";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * Patients contains the information of middle row list along with the
	 * described fields.
	 */
	public static class SelectionList implements SelectionListColumns,
			BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_SELECTIONLIST).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.selectionlist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.selectionlist";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * Scores contain accuracy information of Combined chart along with the
	 * described fields.
	 */
	public static class ScoresList implements ScoresListColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_SCORESLIST).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.scoreslist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.scoreslist";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * LatestTypeResults contains the information of middle row list along with
	 * the described fields.
	 */
	public static class LatestTypeResultsList implements
			LatestTaskTypelistColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_LATESTTASKLIST).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.latestresultslist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.latestresultslist";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	/**
	 * Scores contain accuracy information of Combined chart along with the
	 * described fields.
	 */
	public static class ComplianceList implements CompliancelistColumns,
			BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_COMPILANCELIST).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.compilancelist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.compilancelist";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	public static class TaskHierarchy implements TypeHierarchyColumns,
			BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_TYPEHIERARCHY).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.typehierarchy";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.typehierarchy";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	public static class TaskHomework implements TaskHomeworkColumns,
			BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_TASKHOMEWORK).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.taskhomework";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.taskhomework";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	public static class TypeBaseline implements TypeBaselineColumns,
			BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_TYPEBASELINE).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.typebaseline";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.typebaseline";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}

	public static class Help implements HelpColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_HELP).build();
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.helpoverlay";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.helpoverlay";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}
	
	
	public static class Task implements TaskColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_TASK).build();
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.task";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.task";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}
	
	public static class Session implements SessionColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_SESSION).build();
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.constant_therapy.session";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.constant_therapy.session";

		/** Default ORDER BY */
		public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";

		public static Uri buildOrdersUri(String orderId) {
			return CONTENT_URI.buildUpon().appendPath(orderId).build();
		}

		public static String getOrderId(Uri uri) {
			return uri.getPathSegments().get(1);
		}

	}


	private TherapyContract() {
		// TODO Auto-generated constructor stub

	}
}
