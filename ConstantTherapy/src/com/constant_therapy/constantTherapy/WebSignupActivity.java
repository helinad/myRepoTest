package com.constant_therapy.constantTherapy;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.constant_therapy.service.SyncService;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.util.MyResultReceiver;

public class WebSignupActivity extends CTActivity {

	public static final String EXTRA_URL_KEY = "extra_url";

	private static final String LOG_TAG = "WebSignupActivity";

    private static final String ACCESS_TOKEN_PATH_SUBSTR = "ct_signup_v2_access_token";

    private static final String LOGIN_FAIL_PATH_SUBSTR = "ct_signup_v2_message";
    
    private static final String CLINICIAN_ADD_PATIENT_SUCCESS = "success";
    
    public static final String CLINICIAN_ADD_PATIENT_URL = "http://dev.constanttherapy.com/clinician-add-patient/ipad/";
    
    public static final String SIGNUP_URL = "http://dev.constanttherapy.com/ct_signupform_v2/ipad";
    
    private int mNewPatientUserId = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_signup);

		// Get the message from the intent
		Intent intent = getIntent();
		String theUrl = intent.getStringExtra(EXTRA_URL_KEY);

		// workaround for fullscreen mode vs. keyboard overlapping webview edit fields,
		// which works but has wiggy behavior
		//AndroidBug5497Workaround.assistActivity(this);

		registerReceiver();

		PlaceholderFragment.theUrl = theUrl;

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private static String theUrl = null;

		public PlaceholderFragment() {
		}

		private class MyWebViewClient extends WebViewClient {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				Log.d(LOG_TAG,"Got URI :" + url);

				if (Uri.parse(url).getHost().contains(getString(R.string.remote_ct_domain))) {

					Log.d(LOG_TAG,"URI in CT domain");


					// return value of false means we are staying in our own in-app WebView browser
					// and not opening an external Chrome or other browser
					boolean returnValue = false;
					
					// check to see if this is a successful login that got me an access token
					// e.g. http://dev.constanttherapy.com/v1/ct_signup_v2_access_token/3c8d1628fad0463795efe218d496a22d
					if (url.contains(ACCESS_TOKEN_PATH_SUBSTR)) {
						
						// they gave us a token ... use the token ... send login intent to login activity

						Log.d(LOG_TAG,"URI is access token fake landing path");

						// the token is actually the last element of the path - it's not a query parameter
						String theValue = Uri.parse(url).getLastPathSegment();

						if (null != theValue) {

							Log.d(LOG_TAG,"Have new access token from signup");

							// have access token, so store it
							CTUser.getInstance().clear();
							CTUser.getInstance().setAccessToken(theValue);

							// now that access token has been stored, go to login as if we were opening app
							((CTActivity)getActivity()).animatedStartActivity(false);
						} else {
							// go to login anyhow even without access token, since we're stuck either way at this point
							((CTActivity)getActivity()).animatedStartActivity(true);
						}
						
					} else if (url.contains(CLINICIAN_ADD_PATIENT_SUCCESS)) {
						
						// they added a patient ... we don't get a new token in this case, and in fact we don't have
						// to go back to the login page at all ... we need to go to the 
						// activity from whence we came, whatever that was, could be ClinicianActivity,
						// could be TasksActivity ...
						
						Log.d(LOG_TAG,"URI is success for clinician adding patient");

						// the new patient's user ID is the last segment of the URL
						int newId = Integer.valueOf(Uri.parse(url).getLastPathSegment());

						if (0 != newId) {

							Log.d(LOG_TAG,"Have new patient ID");
							
							// we're gonna use this later to select this new patient in the patient
							// list ... after we update the patient list
							((WebSignupActivity)getActivity()).mNewPatientUserId = newId;
							
							// update our patient list
							((WebSignupActivity)getActivity()).getPatientListFromWebService();
														
						} 
						
					} else if (url.contains(LOGIN_FAIL_PATH_SUBSTR)) {
						
						// oops, login failed ... show a dialog that displays the message from the server
						// which is the last part of the path

						AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
						myAlertDialog.setTitle(R.string.thank_you);
						String theMsg = Uri.parse(url).getLastPathSegment();
						myAlertDialog.setMessage(theMsg);
						
						myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								// do something when the OK button is clicked
							}});
						
						myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								// do something when the Cancel button is clicked
							}});
						
						myAlertDialog.show();

						// back from dialog ... just go to login page now and hope they try again
						((CTActivity)getActivity()).animatedStartActivity(true);
					} 

					return returnValue;

				} else {

					// Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
					return true;

				}

			}
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_web_signup,
					container, false);

			//Dialog dialog = new Dialog(getActivity());
			//dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			//dialog.setContentView(R.layout.fragment_web_signup);
			//WebView forumView = (WebView) dialog.findViewById(R.id.signupWebView);

			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

			if (null != theUrl) {

				// get the WebView from my layout
				WebView forumView=(WebView)rootView.findViewById(R.id.signupWebView);

				// my own URL handler
				forumView.setWebViewClient( new MyWebViewClient());

				// avoid the wacky hardware accelerated canvas rendering error "nativeOnDraw failed"
				forumView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

				// setLoadWithOverviewMode(true) causes images to not be shown!
				forumView.getSettings().setLoadWithOverviewMode(true);

				forumView.getSettings().setUseWideViewPort(true);

				forumView.getSettings().setJavaScriptEnabled(true);

				//forumView.getSettings().setUserAgentString("Mozilla/5.0");

				// pretend to be iPad
				forumView.getSettings().setUserAgentString(
						"Mozilla/5.0(iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10"
						);

				//forumView.reload();
				// 100% is really small...
				//forumView.setInitialScale(100);
				//forumView.getSettings().setSupportZoom(true);
				//forumView.getSettings().setDisplayZoomControls(true);
				//forumView.getSettings().setBuiltInZoomControls(true);
				//forumView.setInitialScale(1);
				//forumView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
				// load the website
				//forumView.loadDataWithBaseURL(null,
				//		"<!DOCTYPE html><html><body style = \"text-align:center\"><img style=\"border-style:dotted;border-width:5px;border-color:black;width:99%;\" src=\""  + 
				//			theUrl + "\" alt=\"page Not Found\"></body></html>","text/html", "UTF-8", null);
				//forumView.loadDataWithBaseURL(null,
				//		"<!DOCTYPE html><html><body style = \"text-align:center\"><object style=\"border-style:dotted;border-width:5px;border-color:black;width:99%;\"" +
				//			"id=\"foo\" name=\"foo\" type=\"text/html\" data=\""  + 
				//			theUrl + "\" alt=\"page Not Found\"></object></body></html>","text/html", "UTF-8", null);
				//Map<String, String> addl = new HashMap<String, String>();
				//addl.put("viewport",  "<meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\">");
				//forumView.loadUrl(theUrl, addl);


				forumView.loadUrl(theUrl);
			}

			return rootView;
		}
	}
	
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "onReceiveResult(resultCode=" + resultCode + ", resultData="
				+ resultData.toString());
		
		switch (resultCode) {
		
		case SyncService.STATUS_RUNNING:
			
			// let it run
			break;
			
		case SyncService.STATUS_FINISHED:
			
						
			if (resultData.getInt(Intent.EXTRA_TEXT) == Constants.GET_PATIENT_LIST_TOKEN) {
				
				// this was kicked off by getPatientListFromWebService(), so we did
				// add patient and then went and got our patient list successfully
				
				Log.d(LOG_TAG, "Got patient list after adding new patient");
								
				// get new patient list (automatically retains current patient selection)
				CTUser.getInstance().refreshPatientsFromProvider(this);

				// show a "SWITCH TO NEW PATIENT?" yes/no dialog here!!!!!!!!!
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

			    builder.setTitle("New patient created");
			    builder.setMessage("Would you like to switch to the new patient?");

			    // YES YES YES YES
			    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			            Log.d(LOG_TAG, "YES");

						// if they switched to a new patient, alter our CTUser accordingly 
						// ... unfortunately the new user is specified as an ID

			            CTUser.getInstance().setCurrentPatient(mNewPatientUserId);

			            dialog.dismiss();
			            
						// go to back to whoever called us, and tell them there's a new
			            // patient they should switch to by saying RESULT_OK
			            setResult(RESULT_OK);
			            finish();
			        }

			    });

			    // NO NO NO NO
			    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			        	
			            Log.d(LOG_TAG, "NO");
			            dialog.dismiss();
			            
						// go straight to Clinician activity, do not modify selected patient
			            // ... we send back OK because some updates still need to be done
			            // perhaps by our caller since a patient was added
			            setResult(RESULT_OK);
			            finish();
			        }
			    });

			    AlertDialog alert = builder.create();
			    alert.show();
				
			} else {
						
				// DUNNO WHAT THIS COULD HAVE BEEN
				
				Log.e(LOG_TAG, "Error ... received unknown sync service result.");

				// just go back to where we started before signup
	            setResult(RESULT_CANCELED);
	            finish();

			}
			
			break;
		case SyncService.STATUS_ERROR:
			
			Log.d(LOG_TAG, "Sync service request failed");
			
			if(resultData.isEmpty()){
				
				// no internet means no data
				Log.d(LOG_TAG, "Internet not available");
				showGenericOkDialog("Oops!", getString(R.string.no_internet));
				
			}else{
				
				// just bail
				Log.d(LOG_TAG, "Unknown sync service failure");

			}
			
			// just go back to where we started before signup
            setResult(RESULT_CANCELED);
            finish();
			
			break;
		case SyncService.STATUS_NO_NETWORK:
			Log.d(LOG_TAG, "Sync service request failed - no Internet");
			
			showGenericOkDialog("Oops!", getString(R.string.no_internet));
			
			// just go back to where we started before signup
            setResult(RESULT_CANCELED);
            finish();

			break;
		}
	}

}

