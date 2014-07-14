package com.constant_therapy.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.constant_therapy.util.MyResultReceiver;

public class ServiceHelper {
	
	/**
	 * Executes the service with the given parameters. 
	 * @param context		see {@link Context}
	 * @param receiver		In order to let the activity know the syncing is complete it needs to register itself as a Receiver.
	 * @param match			For example Order.PATH_TOKEN, this is the identifier to create a Processor to process the data.
	 * @param parameters	This is the actual URL wich the REST service has to call to get the data.
	 */
	public static void execute(Context context, MyResultReceiver receiver, int match, String parameters) {
		
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		Log.v("TAG", "PASSING VIA INTENT");
		syncIntent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		
		syncIntent.putExtra("api_url", parameters);
		syncIntent.putExtra("token", match);
		context.startService(syncIntent);
	}
	
	
public static void execute(Context context, MyResultReceiver receiver, int match, String parameters, String username, String oldPassword, String newPassword) {
		
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		
		syncIntent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		
		syncIntent.putExtra("api_url", parameters);
		syncIntent.putExtra("token", match);
		syncIntent.putExtra("username", username);
		syncIntent.putExtra("oldPassword", oldPassword);
		syncIntent.putExtra("newPassword", newPassword);
		context.startService(syncIntent);
	}
	
	
	public static void execute(Context context, MyResultReceiver receiver, int match, String parameters, String baseline, Boolean isBaseline, String method) {
		
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		Log.v("TAG", "PASSING VIA INTENT");
		syncIntent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		
		syncIntent.putExtra("api_url", parameters);
		syncIntent.putExtra("token", match);
		syncIntent.putExtra("baseline", baseline);
		syncIntent.putExtra("isBaseline", isBaseline);
		syncIntent.putExtra("method", method);
		context.startService(syncIntent);
	}
	
	public static void execute(Context context, MyResultReceiver receiver, int match, String parameters, String taskType, String taskLevel) {
		
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		
		syncIntent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		
		syncIntent.putExtra("api_url", parameters);
		syncIntent.putExtra("token", match);
		syncIntent.putExtra("taskType", taskType);
		syncIntent.putExtra("taskLevel", taskLevel);
		context.startService(syncIntent);
	}
	
	
public static void execute(Context context, MyResultReceiver receiver, int match, String url, String taskTypeId, String taskTypeCount, String taskLevel, String parameters) {
		
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		
		syncIntent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		
		syncIntent.putExtra("api_url", url);
		syncIntent.putExtra("token", match);
		syncIntent.putExtra("taskTypeId", taskTypeId);
		syncIntent.putExtra("taskTypeCount", taskTypeCount);
		syncIntent.putExtra("taskLevel", taskLevel);
		syncIntent.putExtra("parameters", parameters);
		context.startService(syncIntent);
	}
	
	
public static void execute(Context context, MyResultReceiver receiver, int match, String parameters, String json, Boolean isSave) {
		
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		Log.v("TAG", "PASSING VIA INTENT");
		syncIntent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		
		syncIntent.putExtra("api_url", parameters);
		syncIntent.putExtra("token", match);
		syncIntent.putExtra("json", json);
		syncIntent.putExtra("isSave", isSave);
		context.startService(syncIntent);
	}
	
	public static void abort(Context context) {
		final Intent syncIntent = new Intent(
				Intent.ACTION_SYNC, 
				null, 
				context,
				SyncService.class);
		
		context.stopService(syncIntent);
	}

}
