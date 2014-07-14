package com.constant_therapy.service;



import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.network.ConnectionDetector;
import com.constant_therapy.processor.ProcessorFactory;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.util.Constants;
import com.constant_therapy.webservice.Executor;
import com.constant_therapy.webservice.RESTExecutor;
import com.constant_therapy.webservice.RestClient;
import com.constant_therapy.webservice.RestClient.RequestMethod;
import com.constant_therapy.webservice.WebServiceCTData;
import com.google.gson.Gson;

public class SyncService extends IntentService {

	private static final String TAG = "SyncService";

	public static final String EXTRA_STATUS_RECEIVER = "com.constant_therapy.constantTherapy.STATUS_RECEIVER";
	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;
	public static final int STATUS_NO_NETWORK = 0x4;

	private boolean abort = false;
	private boolean isSessionExpired = false;
	private Executor mExecutor;
	WebServiceCTData response;
	public SyncService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate called");
		// TODO Auto-generated method stub
		super.onCreate();
		abort = false;
		ContentResolver resolver = getContentResolver();
		mExecutor = new RESTExecutor(getApplicationContext(), resolver);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		abort = true;
		Log.d(TAG, "SyncService onDestroy Called");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent(intent=" + intent.toString() + ")");

		ResultReceiver receiver = intent
				.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (ConnectionDetector.isInternetAvailable(getApplicationContext())) {
			if (receiver != null) {
				Bundle bundle = new Bundle();
				bundle.putInt(Intent.EXTRA_TEXT, intent.getExtras().getInt("token"));
				receiver.send(STATUS_RUNNING, bundle);
			}

			final long startREST = System.currentTimeMillis();

			try {
				if(intent.getExtras().getInt("token") == Constants.LOGIN_TOKEN){
					RestClient client = new RestClient(intent.getExtras().getString("api_url"));

					client.AddParam("username", getUsername());
					client.AddParam("password", getPassword());
					Log.v(TAG, "LOGIN STARTS");
					try {
						client.Execute(RequestMethod.GET);
					} catch (Exception e) {
						e.printStackTrace();
					}

					String reader = client.getResponse();
					Log.v(TAG, "GOT LOGIN RESPONSE");
					Log.v(TAG, reader);
					Gson gson = new Gson();
					response = gson.fromJson(reader, WebServiceCTData.class);
					
					
					
					if(!response.getCtdata().getAccessToken().equalsIgnoreCase("")){
						Log.v(TAG, "USER CREDIT STARTS");
						saveAccessToken(response.getCtdata().getAccessToken());
						saveType(response.getCtdata().getType());
						String url = this.getString(R.string.remote_preurl) + this.getString(R.string.remote_user);
						mExecutor.sign(url, ProcessorFactory.createProcessor(intent.getExtras().getInt("token")));
					}else{
						Bundle bundle = new Bundle();
						bundle.putInt(Intent.EXTRA_TEXT, Constants.INVALID_ACCESSTOKEN);
						receiver.send(STATUS_ERROR, bundle);
						return;
					}
				} else if(intent.getExtras().getInt("token") == Constants.ACCESS_TOKEN_LOGIN_TOKEN){
					
					// already have the access token, don't have username/password yet - this is
					// a login after a new signup ... note that we expect the access token to have
					// already been saved in CTUser before making the executor call

					Log.v(TAG, "Access token login");
					mExecutor.sign(intent.getExtras().getString("api_url"), 
							ProcessorFactory.createProcessor(intent.getExtras().getInt("token")));

				}else if(intent.getExtras().getInt("token") == Constants.CLINICIAN_PLOT_ACCURACY_TOKEN || intent.getExtras().getInt("token") == Constants.CLINICIAN_PLOT_LATENCY_TOKEN){
					isSessionExpired = mExecutor.execute(intent.getExtras().getString("api_url"),
							ProcessorFactory.createProcessor(intent.getExtras()
									.getInt("token")),intent.getExtras().getString("taskType"), intent.getExtras().getString("taskLevel") );
				}else if(intent.getExtras().getInt("token") == Constants.CLINICIAN_TASKS_SAVE_TOKEN || intent.getExtras().getInt("token") == Constants.PREFERENCE_POST_TOKEN){
					isSessionExpired = mExecutor.execute(intent.getExtras().getString("api_url"), intent.getExtras().getString("json"), intent.getExtras().getBoolean("isSave"));
				}else if(intent.getExtras().getInt("token") == Constants.DOING_TASKS_TOKEN){
					isSessionExpired = mExecutor.execute(intent.getExtras().getString("api_url"), ProcessorFactory.createProcessor(intent.getExtras()
							.getInt("token")),intent.getExtras().getString("taskTypeId"), intent.getExtras().getString("taskTypeCount"), intent.getExtras().getString("taskLevel"),intent.getExtras().getString("parameters"));
				}else if(intent.getExtras().getInt("token") == Constants.TASK_BASELINE_TOKEN || intent.getExtras().getInt("token") == Constants.VALIDATE_EMAIL_TOKEN){
					isSessionExpired = mExecutor.execute(intent.getExtras().getString("api_url"), intent.getExtras().getString("baseline"), intent.getExtras().getBoolean("isBaseline"),ProcessorFactory.createProcessor(intent.getExtras()
							.getInt("token")));
				}else if(intent.getExtras().getInt("token") == Constants.CHANGE_PASSWORD_TOKEN){
					
					isSessionExpired = mExecutor.execute(intent.getExtras().getString("api_url"), ProcessorFactory.createProcessor(intent.getExtras()
							.getInt("token")), intent.getExtras().getString("username"), intent.getExtras().getString("oldPassword"), intent.getExtras().getString("newPassword"));
				}else{
					
					
					isSessionExpired = mExecutor.execute(intent.getExtras().getString("api_url"),ProcessorFactory.createProcessor(intent.getExtras()
								.getInt("token")));
				}
				final long stopREST = System.currentTimeMillis();

				Log.d(TAG, "Remote syncing took " + (stopREST - startREST)+ "ms");
				Log.v(TAG, "isSessionExpired: "+isSessionExpired);

			} catch (Exception e) {
				Log.e(TAG, "Problem while syncing", e);
				Log.v(TAG, "isSessionExpired: "+isSessionExpired);
				if (receiver != null && !abort) {
					if(isSessionExpired){
						Bundle bundle = new Bundle();
						bundle.putInt(Intent.EXTRA_TEXT, Constants.INVALID_ACCESSTOKEN);
						receiver.send(STATUS_ERROR, bundle);
						return;
					}else{
						//Bundle bundle = new Bundle();
						receiver.send(STATUS_ERROR, Bundle.EMPTY);
						return;
					}
					
				}
			}

			Log.d(TAG, "Syncing finished.");

			if (receiver != null && !abort) {
				if ((intent.getExtras().getInt("token") == Constants.LOGIN_TOKEN)
						|| (intent.getExtras().getInt("token") == Constants.ACCESS_TOKEN_LOGIN_TOKEN)){
					
					// was a login - special case - we intercepted the initial request to this service
					// and now we need to do special handling of the response as well
					
					// send empty bundle - will use CTUser to figure out what to do next
					Bundle theBundle = new Bundle();
					receiver.send(STATUS_FINISHED, theBundle);

				}else if(isSessionExpired){
					Bundle bundle = new Bundle();
					bundle.putInt(Intent.EXTRA_TEXT, Constants.INVALID_ACCESSTOKEN);
					receiver.send(STATUS_ERROR, bundle);
					return;
				}else{
					Bundle bundle = new Bundle();
					bundle.putInt(Intent.EXTRA_TEXT, intent.getExtras().getInt("token"));
					receiver.send(STATUS_FINISHED, bundle);
				}
			}

		} else {
			receiver.send(STATUS_NO_NETWORK, Bundle.EMPTY);
		}

	}
	
	
	public final String getUsername() {
		return CTUser.getInstance().getUsername();

	}

	

	public final String getPassword() {
		return CTUser.getInstance().getPassword();
	}
	
	public final void saveAccessToken(String accessToken) {
		CTUser.getInstance().setAccessToken(accessToken);
	}
	
	public final void saveType(String userType) {
		CTUser.getInstance().setUserType(userType);
	}
	
	public void postData(String result,JSONObject obj) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);

		String json = obj.toString();

		try {

		    HttpPost httppost = new HttpPost(result.toString());
		    httppost.setHeader("Content-type", "application/json");

		    StringEntity se = new StringEntity(obj.toString()); 
		    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    httppost.setEntity(se); 

		    HttpResponse response = httpclient.execute(httppost);
		    String temp = EntityUtils.toString(response.getEntity());
		    Log.i("tag", temp);


		} catch (ClientProtocolException e) {

		} catch (IOException e) {
		}
	}
	
}
