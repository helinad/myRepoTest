package com.constant_therapy.constantTherapy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.constant_therapy.animation.ActivitySwitcher;
import com.constant_therapy.provider.TherapyContract.Dashboards;
import com.constant_therapy.provider.TherapyContract.Patients;
import com.constant_therapy.provider.TherapyContract.PatientsList;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.widget.CTTextView;

public abstract class CTActivity extends Activity implements MyResultReceiver.Receiver {

	protected MyResultReceiver mReceiver;
	
	/** go to the login page - if isSession is true, notify user that their session has ended */
	protected void animatedStartActivity(Boolean isSession) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to
		// implement it.
		final Intent intent = new Intent(getApplicationContext(),
				LoginActivity.class);
		if (null != isSession)
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
	
	/** go to LoginActivity, but do not tell user that session has ended */
	protected void animatedStartActivity() {

		Boolean isSession = false;
		animatedStartActivity(isSession);
	}


	@SuppressWarnings("rawtypes")
	protected void animatedStartActivity(Class activity) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to implement it.
		final Intent intent = new Intent(getApplicationContext(), activity);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		ActivitySwitcher.animationOut(findViewById(R.id.container), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				startActivity(intent);
				finish();
			}
		});
	}
	
	/** clear out the shared prefs and the provider data relevant to this
	 * user session ... note that some of this info is redundantly stored
	 * e.g. access token, user type ... we will want to encapsulate this
	 * later but for now it's dual-maintained */
	protected void clearDbAndSession() {

		CTUser.getInstance().clear();
		CTUser.getInstance().clearAccessToken();
		
		// TO DO: purge all this once CTUser has taken full control of persistence
		getApplicationContext().getSharedPreferences("logininfo", 0).edit()
				.clear().commit();
		getApplicationContext().getSharedPreferences("accessTokeninfo", 0)
				.edit().clear().commit();
		getApplicationContext().getSharedPreferences("typeinfo", 0).edit()
				.clear().commit();
		getApplicationContext().getSharedPreferences("alertinfo", 0).edit()
				.clear().commit();
		getContentResolver().delete(Users.CONTENT_URI, null, null);
		getContentResolver().delete(Dashboards.CONTENT_URI, null, null);

		getContentResolver().delete(PatientsList.CONTENT_URI, null, null);
		getContentResolver().delete(Patients.CONTENT_URI, null, null);
	}
	
	/** fetch the user type of the currently logged in user from the provider */
	protected String getUserTypeFromProvider() {
		
		String[] projectionUserType = new String[] { Users.USER_TYPE };

		Cursor cursor = this.getContentResolver().query(Users.CONTENT_URI,
				projectionUserType, null, null, Users.DEFAULT_SORT);

		String userType = null;
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(Users.USER_TYPE);
				userType = cursor.getString(index);
			}
		}
		cursor.close();
		return userType;

	}

	/** fetch the username of the currently logged in user from the provider */
	protected String getUsernameFromProvider() {
		
		String[] projectionUsername = new String[] { Users.USERNAME };

		Cursor cursor = this.getContentResolver().query(Users.CONTENT_URI,
				projectionUsername, null, null, Users.DEFAULT_SORT);

		String username = null;
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(Users.USERNAME);
				username = cursor.getString(index);
			}
		}
		cursor.close();
		return username;

	}
		
	/*
	 * Method for retrieving patientId from database.
	 */

	/** get user ID from provider */
	protected String getUserIdFromProvider() {

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
	
	
	/** get patient list from web service */
	protected void getPatientListFromWebService() {

		String url = getString(R.string.remote_preurl)
				+ getString(R.string.remote_clinician) + CTUser.getInstance().getUserId()
				+ getString(R.string.remote_patients);
		ServiceHelper.execute(getApplicationContext(), mReceiver,
				Constants.GET_PATIENT_LIST_TOKEN, url);

	}

	@Override
	public void registerReceiver() {
		
		mReceiver = new MyResultReceiver(new Handler());
		mReceiver.setReceiver(this);
	}
	
	/*
	 * Method for showing internet alert.
	 */
	
	protected void showGenericOkDialog(String title, String message){
    	final Dialog dialog = new Dialog(this);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		dialog.setCancelable(true);
		CTTextView text = (CTTextView) dialog.findViewById(R.id.tv);
		text.setText(title);
		CTTextView mess = (CTTextView) dialog.findViewById(R.id.etsearch);
		if(message != null){
		
			mess.setText(message);
			mess.setVisibility(View.VISIBLE);
		}else{
			mess.setVisibility(View.GONE);
		}
		Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
		
		dialogButton.setText("OK");

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
       
		dialog.show();

    }
	
}
