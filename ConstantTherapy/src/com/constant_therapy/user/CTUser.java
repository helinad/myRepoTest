package com.constant_therapy.user;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.constant_therapy.provider.TherapyContract.Patients;
import com.constant_therapy.provider.TherapyContract.PatientsList;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.util.PatientModel;
import com.constant_therapy.util.SharedPrefHelper;
import com.constant_therapy.webservice.WebServiceUser;

/** singleton representing the currently logged in user, to be accessed by all 
 * activities in place of per-activity user info params ... uses the initialization
 * on demand holder idiom to be thread safe in terms of creating the instance
 * ... note that we are not actually an activity with a UI but we inherit from
 * Activity so we can access the application Context via Context.getApplicationContext()  */
public class CTUser {
	
	/** user ID */
	private  Integer mUserId = INVALID_USER_ID;
	/** username */
	private  String mUsername = null;
	/** login password for this user */
	private  String mPassword = null;
	/** first name of user */
	private  String mFirstName = null;
	/** last name of user */
	private  String mLastName = null;
	/** email of user */
	private  String mEmail = null;
	/** whether user is a demo user (deprecated flag as of 2.3 version of web services */
	private  Boolean mIsDemoFlag = false;
	/** web service access token */
	private String mAccessToken = null;
	/** user type, for now only either clinician or patient */
	private String mUserType = null;
	/** are we logged in or not?  we can have an access token but due to internet
	 * issues we can be not currently really logged in */
	private Boolean mIsLoggedIn = false;
	/** phone number ... often not filled in since it does not get sent by some of the main
	 * web service responses */
	private String mPhoneNumber = null;
	
	/** temporary cache for a password that we've requested to update on the server,
	 * but which may not have been accepted ... avoids having to do a separate
	 * server request to refresh our whole CTUser record after a successful password
	 * change request ... note that most of the time both this field and the
	 * mPassword field will be null, since the server call that gets user info
	 * via their access token does not return the password, so normally the
	 * password is not in the Users part of the provider ... however if the
	 * user actually had to log in by typing in a username and password
	 * (instead of just using a cached access token) then that username and
	 * password will be stored in CTUser until the next time the app is 
	 * stopped by the OS - so sometimes mPassword will be non-null prior to
	 * any user attempt to change the password */
	private String mProspectivePassword = null;
	
	/** application context, provided at start of app by the Splash activity which is always the 
	 * first activity shown */
	private Context mContext = null;
		
	// FOR CLINICIAN USER TYPES ONLY
	/** currently selected patient of logged in clinician - may be null if none selected 
	 * e.g. if this clinician has no associated patients this will be null */
	private String mCurrentSelectedPatient = null;
	/** list of patients associated with clinician - may be null if no patients are currently associated */
	private List<PatientModel> mPatients = null;

	public static final String USER_TYPE_CLINICIAN = "clinician";
	public static final String USER_TYPE_PATIENT = "patient";
	public static final Integer INVALID_USER_ID = -1;

	/** Private constructor prevents instantiation from other classes */
	private CTUser() { 
		clear(); 
	}
	
	public void clear() {
		mUserId = INVALID_USER_ID;
		mUsername = null;
		mPassword = null;
		mFirstName = null;
		mLastName = null;
		mEmail = null;
		mIsDemoFlag = false;
		mUserType = null;
		mIsLoggedIn = false;
		mPatients = null;
		mCurrentSelectedPatient = null;
		mPhoneNumber = null;
		mProspectivePassword = null;
	}
	
	/** pass in getApplicationContext(), not Activity.this nor Activity.getContext() !!!! */
	public void init(Context theContext) {
		mContext = theContext;
		refreshFromProvider();
		// give precedence to access token from provider, in case
		// it has been returned to app while activities were all 
		// stopped - only an activity would call CTUser's setAccessToken()
		if ((null == mAccessToken) || (mAccessToken.length() == 0)) 
			getAccessTokenFromSharedPref();
	}
	
	/** save a prospective new password until such time as we get confirmation
	 * that the server has accepted our change password request ... use this
	 * before a server call to update a password is made */
	public void cacheProspectiveNewPassword(String prospectivePassword) {
		mProspectivePassword = prospectivePassword;
	}
	
	/** confirm previously saved prospective new password - call this when 
	 * the server confirms the application of the new setting */
	public void confirmProspectiveNewPassword() {
		if (null == mProspectivePassword) throw new IllegalStateException("Tried to confirm nonexistent prospective new password");
		mPassword = mProspectivePassword;
		mProspectivePassword = null;
	}
	
	/** get the access token from shared prefs ... we always look in the provider
	 * first and if we have one from the provider we use that since an access token
	 * may have come in to our SyncService while our activities were stopped and
	 * the service would still put it into the provider - but if the provider
	 * does not have one we use the one from the shared pref */
	private void getAccessTokenFromSharedPref() {
		mAccessToken = SharedPrefHelper.getAccessToken(mContext);
	}
	
	/** save the access token to shared prefs ... we always look in the provider
	 * first and if we have one from the provider we use that since an access token
	 * may have come in to our SyncService while our activities were stopped and
	 * the service would still put it into the provider - but if the provider
	 * does not have one we use the one from the shared pref, so we save it there
	 * ... the key thing to keep in mind is that the provider is not actually
	 * a persistence mechanism for us, it just represents the last info sent to
	 * us from the server, and if the server sent us an incomplete record that did
	 * not include an access token, we won't be able to get the access token
	 * from the server ... we use the access token to refresh everything else */
	private void saveAccessTokenToSharedPref() {
		// don't forget prior access token - that's not what this is for
		if ((null != mAccessToken) && (0 < mAccessToken.length())) 
			SharedPrefHelper.saveAccessToken(mContext,  mAccessToken);
	}
 
	/** SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 *  or the first access to SingletonHolder.INSTANCE, not before. */
	private static class SingletonHolder { 
		private static final CTUser INSTANCE = new CTUser();
	}
 
	/** get the singleton instance of CTUser */
	public static CTUser getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	/** tells whether we currently have a valid selected user */
	public Boolean hasSelectedCurrentUser() {
		
		if ((mCurrentSelectedPatient != null) && (false == this.isAssociatedPatient(mCurrentSelectedPatient)))
			throw new IllegalStateException("currently selected user is not a patient of the current clinician");
		
		return (mCurrentSelectedPatient != null);
	}
	
	/* update this record based on info we got from JSON in a web service call, converted to
	 * our POJO user */
	public void update(WebServiceUser theData) {
		mUserId = Integer.valueOf(theData.id);
		mUsername = theData.username;
		mPassword = theData.password;
		mFirstName = theData.firstName;
		mLastName = theData.lastName;
		mEmail = theData.email;
		mIsDemoFlag = theData.isDemo;
		// don't kill my existing access token by overwiting with null or empty
		setAccessToken(theData.accessToken);
		mUserType = theData.type;
		mCurrentSelectedPatient = null;
		mPatients = null;
	}
	
	/** add a patient to the list of patients associated with this user ... will be exception if this user is not clinician */
	public void addToListOfPatients(PatientModel thePatient) {
		if (!isUserTypeClinician()) throw new IllegalStateException("Attempted to update list of patients for a non-clinician");
		if (null == mPatients) mPatients = new ArrayList<PatientModel>();
		mPatients.add(thePatient);
	}
	
	/** clears the list of patients associated with this user - will be exception if this user is not a clinician */
	public void clearListOfPatients() {
		if (!isUserTypeClinician()) throw new IllegalStateException("Attempted to update list of patients for a non-clinician");
		if (null != mPatients) mPatients.clear();
	}
	
	/** returns true if the given username is the same as the username of a patient associated with this clinician */
	public boolean isAssociatedPatient(String username) {
		return (null != getIndexInPatientList(username));
	}
	
	/** gets index of username in associated patients - returns null if not in the list */
	private Integer getIndexInPatientList(String patientUsername) {
		if (null == mPatients) return null;
		int i = 0; 
		boolean foundIt = false;
		while ((i < mPatients.size()) && (foundIt == false)) {
			if (mPatients.get(i).getUsername().compareTo(patientUsername) == 0) foundIt = true;
			else i++;
		}
		if (true == foundIt) return i;
		else return null;
	}
	
	/** sets the current patient to a given user ID */
	public void setCurrentPatient(int userId) {
		
		if (null == mPatients) 
			throw new IllegalStateException("Attempted to update current patient for a non-clinician");

		int i = 0; 
		boolean foundIt = false;
		while ((i < mPatients.size()) && (foundIt == false)) {
			if (Integer.valueOf(mPatients.get(i).getPatientId()) == userId) foundIt = true;
			else i++;
		}
		
		if (false == foundIt)
			throw new IllegalArgumentException("tried to set current user ID to a patient that is not assoc with this clinician");
		else
			setCurrentPatient(mPatients.get(i).getUsername());
	}
	
	/** sets current patient to the given username */
	public void setCurrentPatient(String currentPatientUsername) {
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to update current patient for a non-clinician");
		if (null == currentPatientUsername) 
			throw new IllegalArgumentException("tried to set username of current patient to null - use clear");
		if (false == isAssociatedPatient(currentPatientUsername))
			throw new IllegalStateException("tried to set current patient for non-associated patient");
		
		mCurrentSelectedPatient = currentPatientUsername;
	}
	
	/** clears the current patient setting for this patent */
	public void clearCurrentPatient() { 
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to update current patient for a non-clinician");
		
		mCurrentSelectedPatient = null; 
	}

	/** returns the username of the currently selected patient - will return null if none is
	 * currently selected e.g. if clinician has no patients or if clinician has not selected one for current client ops */
	public String getCurrentPatientUsername() { 
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to get current patient for a non-clinician");
		
		return mCurrentSelectedPatient; 
	}

	/** gets the patient ID that we want to display ... helps with ClinicianActivity which wants
	 * to display the currently selected patient's info if the current user is a clinician, but wants to display
	 * the current user's own info if the current user is a patient */
	public String getPatientIdForDisplay() {
		if (isUserTypePatient()) return getUserId().toString();
		else return getCurrentPatient().getPatientId();
	}
	
	/** gets the patient username that we want to display ... helps with ClinicianActivity which wants
	 * to display the currently selected patient's info if the current user is a clinician, but wants to display
	 * the current user's own info if the current user is a patient */
	public String getPatientUsernameForDisplay() {
		if (isUserTypePatient()) return getUsername();
		else return getCurrentPatientUsername();
	}
	
	/** returns the PatientModel of the currently selected patient - will return null if none is currently selected */
	public PatientModel getCurrentPatient() {
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to get current patient for a non-clinician");

		if (null == mCurrentSelectedPatient) return null;
		
		return mPatients.get(getIndexInPatientList(mCurrentSelectedPatient));
	}
	
	/** returns true if the given user's username is the same as the username of a patient associated with this clinician */
	public boolean isAssociatedPatient(WebServiceUser theUser) {
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to update current patient for a non-clinician");

		return isAssociatedPatient(theUser.username);
	}

	public Integer getUserId() { return mUserId; }
	public void setUserId(Integer userId) { this.mUserId = userId; }

	public String getUsername() { return mUsername; }
	public void setUsername(String username) { this.mUsername = username; }

	public String getPassword() { return mPassword; }
	public void setPassword(String password) { this.mPassword = password; }

	public String getFirstName() { return mFirstName; }
	public void setFirstName(String firstName) { this.mFirstName = firstName; }
	
	public String getLastName() { return mLastName; }
	public void setLastName(String lastName) { this.mLastName = lastName; }

	public String getPhoneNumber() { return mPhoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.mPhoneNumber = phoneNumber; }

	public String getEmail() { return mEmail; }
	public void setEmail(String email) { this.mEmail = email; }

	public Boolean getIsDemoFlag() { return mIsDemoFlag; }
	public void setIsDemoFlag(Boolean isDemoFlag) { this.mIsDemoFlag = isDemoFlag; }

	public String getAccessToken() { return mAccessToken; }
	/** set access token of the current user - also stores it to shared prefs for later 
	 * reference after a force close ... note that if a null or empty value is passed
	 * in, nothing is done - we only throw away the current access token if 
	 * clearAccessToken() is called */
	public void setAccessToken(String accessToken) { 
		if ((null != accessToken) && (0 < accessToken.length())) {
			mAccessToken = accessToken; 
			saveAccessTokenToSharedPref();
		}
	}
	/** separate call to clear access token from shared preferences - necessary because
	 * we intentionally don't clear the shared pref when CTUser.clear() is called,
	 * because generally speaking clearing the access token is an extreme measure 
	 * that we only do when the user explicitly logs out */
	public void clearAccessToken() {
		mAccessToken = null;
		SharedPrefHelper.deleteAccessToken(mContext);
	}

	public String getUserType() { return mUserType; }
	public void setUserType(String userType) { this.mUserType = userType; }
	
	public boolean isUserTypeClinician() {return isUserTypeClinician(mUserType); }
	public boolean isUserTypePatient() {return isUserTypePatient(mUserType); }
	
	public Boolean getIsLoggedIn() { return mIsLoggedIn; }
	public void setIsLoggedIn(Boolean isLoggedIn) { mIsLoggedIn = isLoggedIn; }

	/** test for whether a given user type is clinician */
	public static boolean isUserTypeClinician(String typeString) {
		if (typeString.compareTo(USER_TYPE_CLINICIAN) == 0) return true;
		return false;
	}
	 
	/** test for whether a given user type is patient */
	public static boolean isUserTypePatient(String typeString) {
		if (typeString.compareTo(USER_TYPE_PATIENT) == 0) return true;
		return false;
	}
	
	/** update from provider ... if there is nothing in the provider we will be cleared out 
	 * (except of course for the access token which we'll keep) */
	public void refreshFromProvider() {
				
		boolean needToPurgeUsersAfterward = false;
		
		Cursor cursor = mContext.getContentResolver().query(Users.CONTENT_URI,
				null, null, null, Users.DEFAULT_SORT);

		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				
				// OK have new info so clear old info
				clear();
				needToPurgeUsersAfterward = true;

				int index = cursor.getColumnIndexOrThrow(Users.PATIENT_ID);
				String temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mUserId = Integer.valueOf(temp);
				
				index = cursor.getColumnIndexOrThrow(Users.USERNAME);
				temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mUsername = temp;
				
				index = cursor.getColumnIndexOrThrow(Users.ACCESSTOKEN);
				temp = cursor.getString(index);
				setAccessToken(temp);
				
				index = cursor.getColumnIndexOrThrow(Users.EMAIL);
				temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mEmail = temp;
				
				index = cursor.getColumnIndexOrThrow(Users.FIRSTNAME);
				temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mFirstName = temp;
				
				index = cursor.getColumnIndexOrThrow(Users.ISDEMO);
				temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mIsDemoFlag = Boolean.valueOf(temp);
				
				index = cursor.getColumnIndexOrThrow(Users.LASTNAME);
				temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mLastName = temp;

				index = cursor.getColumnIndexOrThrow(Users.USER_TYPE);
				temp = cursor.getString(index);
				if ((null != temp) && (temp.length() > 0))
					mUserType = temp;

			}
			
		}
		
		cursor.close();
		
		if (true == needToPurgeUsersAfterward) {
			// we got this info, so now it is by definition stale
			// ... wait until we close the other cursor on the content resolver 
			// before doing the purge
			mContext.getContentResolver().delete(Users.CONTENT_URI, null, null);
		}

		

	}

	/** gets the current list of patients of a clinician that is stored in the provider */
	public void refreshPatientsFromProvider(Context theContext) {
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to update patient list for a non-clinician");

		mPatients = new ArrayList<PatientModel>();
		PatientModel model = null;
		Cursor cursor = theContext.getContentResolver().query(Patients.CONTENT_URI,
				null, null, null, Patients.DEFAULT_SORT);

		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					String id = cursor.getString(cursor
							.getColumnIndex(PatientsList.PATIENT_ID));
					String name = cursor.getString(cursor
							.getColumnIndex(PatientsList.USERNAME));
					String imagePath = retrievePatientImagePath(theContext,cursor
							.getString(cursor
									.getColumnIndex(PatientsList.PATIENT_ID)));
					model = new PatientModel(name, imagePath, id, null, "");
					mPatients.add(model);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		
		// fix up the selected patient if necessary
		if ((null != mCurrentSelectedPatient) && (isAssociatedPatient(mCurrentSelectedPatient) == false))
			mCurrentSelectedPatient = null;

	}
	
	/** whether or not this user has associated patients */
	public Boolean hasPatients() { 
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to check patient list for a non-clinician");

		return ((null != mPatients) && (mPatients.size() > 0)); 
	}

	/** returns the list of patients of this user */
	public List<PatientModel> getPatientList() {
		
		if (!isUserTypeClinician()) 
			throw new IllegalStateException("Attempted to get patient list for a non-clinician");

		return mPatients;
	}
	
	/**Method for retrieving imagepath of selected patient from the database by
	 * passing the patient id... if only Eric understood why this is stored separately from the Patients themselves! */
	private String retrievePatientImagePath(Context theContext, String patientId) {
		String selection = PatientsList.PATIENT_ID + " = '" + (patientId) + "'";
		String imagePath = null;
		Cursor cursor = theContext.getContentResolver().query(
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

	/** get a string via a resource ID using our cached application-level context */
	@SuppressWarnings("unused")
	private String getString(int resourceId) { return mContext.getString(resourceId);}
	
	/** update the user account info e.g. email, phone, first & last name */
	public void updateAccountInfo(String email, String phone, String lname,
			String fname) {
		
		setEmail(email);
		setPhoneNumber(phone);
		setFirstName(fname);
		setLastName(lname);
		
	}

	/** put updated user info into the provider ... email, first and last name, phone number,
	 * user ID ... would perhaps have been better to do a system call that updated the provider 
	 * via the web service, but here we are */
	public void putAccountInfoIntoProvider() {
		mContext.getContentResolver().update(Users.CONTENT_URI, null, null, null); // values,
																				// where,
																				// selectionArgs);//delete(Users.CONTENT_URI,
																				// null,
																				// null);
		ContentValues insertValues = new ContentValues();
		insertValues.put(Users.EMAIL, mEmail);
		insertValues.put(Users.FIRSTNAME, mFirstName);
		insertValues.put(Users.LASTNAME, mLastName);
		insertValues.put(Users.PHONENUMBER, mPhoneNumber);
		insertValues.put(Users.PATIENT_ID, mUserId);
		mContext.getContentResolver().insert(Users.CONTENT_URI, insertValues);
	}



}
