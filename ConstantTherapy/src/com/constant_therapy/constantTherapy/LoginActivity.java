package com.constant_therapy.constantTherapy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.popup.AlertPopup;
import com.constant_therapy.service.ServiceHelper;
import com.constant_therapy.service.SyncService;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.Helper;
import com.constant_therapy.util.MyResultReceiver;
import com.constant_therapy.webservice.JSONParser;
import com.constant_therapy.widget.CTTextView;

public class LoginActivity extends CTActivity {

	private static final String TAG = "LoginActivity";
			
	ImageView imgCTIcon;
	
	private Button btnSignup;
	private Button btnLogin;

	private EditText etUsername;
	private EditText etPassword;

	
	Boolean isInternetPresent;
	Boolean isSession;
	
	String response;
	
	JSONParser jsonParser;
	
	Animation shake;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.login);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnSignup = (Button) findViewById(R.id.btnSignUp);
		btnLogin.setBackgroundColor(getResources().getColor(R.color.login_gray));

		etUsername = (EditText) findViewById(R.id.etusername);
		etPassword = (EditText) findViewById(R.id.etpassword);
		
		imgCTIcon = (ImageView)findViewById(R.id.ctIcon);
		registerReceiver();
		
		Helper.setIsRight(LoginActivity.this, true);
		
		btnSignup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// go to signup activity
			    Intent intent = new Intent(LoginActivity.this, WebSignupActivity.class);
			    intent.putExtra(WebSignupActivity.EXTRA_URL_KEY, WebSignupActivity.SIGNUP_URL);
			    startActivity(intent);

			}
		});
		
		
		imgCTIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertPopup.showVersionAlert(LoginActivity.this, getString(R.string.app_version), null);
			}
		});

		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
				btnLogin.setBackgroundColor(getResources().getColor(R.color.login_blue));
				
				if (ConnectionDetector.isInternetAvailable(LoginActivity.this)) {
					 String enteredUsername = etUsername.getText().toString().trim();
					 String enteredPassword = etPassword.getText().toString().trim();
					if (enteredUsername.length() != 0 && enteredPassword.length() != 0) {
						submitLogin(enteredUsername, enteredPassword);
					} else {
						btnLogin.setBackgroundColor(getResources().getColor(R.color.login_gray));
						ShowDialogWithSingleButton(getString(R.string.empty_login),null);
						
					}
					btnLogin.setClickable(false);

				} else {
					showInternetAlert("Oops!",
							getString(R.string.no_internet));
					
				}
				

			}
		});
		
		
        // isSession = true means that we got here due to trying to submit
        // a request with an invalid access token ... this alert is the only
        // way that isSession is used
		Intent myIntent = getIntent();
		isSession = myIntent.getBooleanExtra("isSession", false);
		if(isSession){
			showInternetAlert("Your session has ended", getString(R.string.end_session));
		}

		if (!ConnectionDetector.isInternetAvailable(LoginActivity.this)) {
		
			showInternetAlert("Oops!",
					getString(R.string.no_internet));

		}
		
		// try to log in with our known access token ... if we have one
		if (null != CTUser.getInstance().getAccessToken()) {
			submitLoginUsingExistingAccessToken();
		} else {
			playPleaseGiveUsernamePasswordAudio();
		}

	}
	
	private void playPleaseGiveUsernamePasswordAudio() {
		MediaPlayer mPlayer = MediaPlayer.create(LoginActivity.this, R.raw.please_login_with_your_username_and_password);
        mPlayer.start();
	}
			
	private void submitLogin(String username, String password) {

		Log.d(TAG, "submitLogin()");

		// Save login data
		CTUser.getInstance().setUsername(username);
		CTUser.getInstance().setPassword(password);
		String url = getString(R.string.remote_preurl) + this.getString(R.string.remote_login);
		ServiceHelper.execute(LoginActivity.this, mReceiver, Constants.LOGIN_TOKEN, url);
	}
	
	private void submitLoginUsingExistingAccessToken() {

		Log.d(TAG, "Auto login with known access token");
		
		// use the URL for "user login with access token" instead of the "login with username & password"
		// ... assumes someone else e.g. WebSignupActivity has already set the access token of CTUser instance
		String url = getString(R.string.remote_preurl) + this.getString(R.string.remote_user);
		ServiceHelper.execute(LoginActivity.this, mReceiver, Constants.ACCESS_TOKEN_LOGIN_TOKEN, url);
	}

	@Override
	protected void onResume() {
		// animateIn this activity
		//ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
		super.onResume();
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiveResult(resultCode=" + resultCode + ", resultData="
				+ resultData.toString());
		
		switch (resultCode) {
		
		case SyncService.STATUS_RUNNING:
			
			// let it run
			break;
			
		case SyncService.STATUS_FINISHED:
			
			// successful login, of some type or the other, or retrieval of patient list
									
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.GET_PATIENT_LIST_TOKEN) {
				
				// this was actually kicked off by getPatientList(), not by a login,
				// which means we already have our patient list and are ready to go to
				// the clinician dashboard
				
				Log.d(TAG, "Got patient list");

				animatedStartActivity(ClinicianActivity.class);
				
			} else {
								
				// was a login
				
				Log.d(TAG, "Login was OK!");
				
				// get who we logged in as ... note that the provider doesn't contain an 
				// access token, but CTUser will keep the one we used to get info into the
				// provider so, no problem there
				CTUser.getInstance().refreshFromProvider();

				// user is in "logged in" state
				CTUser.getInstance().setIsLoggedIn(true);


				if(CTUser.getInstance().isUserTypePatient()) {
			
					// logged in as patient
					animatedStartActivity(PatientDashboardActivity.class);
				}else{
					// logged in as clinician, extra will be Constants.CLINICIAN - need patient list
					// before we can move to clinician dashboard
					getPatientListFromWebService();
				}
			}
			
			break;
		case SyncService.STATUS_ERROR:
			
			Log.d(TAG, "Login did not succeed.");
			
			if(resultData.isEmpty()){
				
				// no internet means no data
				showInternetAlert("Oops!", getString(R.string.no_internet));
				
			}else{
				
				// whether we shake the screen or not depends on whether they entered a username
				// and password or not - if we just tried to login using a cached access token,
				// then we don't shake
				String theUsername = CTUser.getInstance().getUsername();
				if ((null != theUsername) && (0 < theUsername.length())) {
					
					 shake = AnimationUtils.loadAnimation(this, R.anim.shake);
					 findViewById(R.id.container).startAnimation(shake);
					showInternetAlert("Oops!", getString(R.string.error_login));
				} else {
					
					// they haven't actually typed in username and password ... ask them for it
					playPleaseGiveUsernamePasswordAudio();
				}
				
			}
			
			// let them try again
			btnLogin.setClickable(true);
			
			break;
		case SyncService.STATUS_NO_NETWORK:
			Log.d(TAG, "Login did not succeed.");
			
			showInternetAlert("Oops!", getString(R.string.no_internet));
			btnLogin.setClickable(true);
			break;
		}
	}

	/*
	 * Method for showing internet alert.
	 */
	
	private void showInternetAlert(String title, String message){
    	final Dialog dialog = new Dialog(LoginActivity.this);
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
		// if button is clicked, close the custom dialog
		
		if(title.equalsIgnoreCase(getResources().getString(R.string.app_version))){
			dialogButton.setText("Ok");
		}else{
			dialogButton.setText("Okay");
		}
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				btnLogin.setBackgroundColor(getResources().getColor(R.color.login_gray));
			}
		});

		
       
		dialog.show();

    }
	
	
	public void ShowDialogWithSingleButton(String title, String message){
    	final Dialog dialog = new Dialog(LoginActivity.this);
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
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnLogin.setClickable(true);
				dialog.dismiss();
			}
		});

		
       
		dialog.show();

    }
	
	@Override
	public void onBackPressed() {
	    // do nothing.
	   
	       return;
	}

}
