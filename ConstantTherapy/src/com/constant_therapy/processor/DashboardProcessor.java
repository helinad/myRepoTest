package com.constant_therapy.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.constant_therapy.dashboard.Dashboard;
import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.Dashboards;
import com.google.gson.Gson;

public class DashboardProcessor extends Processor {
	Dashboard dashBoard;
	private boolean clean_db = false;
	public DashboardProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);

	}
	
	public DashboardProcessor(boolean clean) {
		super(TherapyContract.CONTENT_AUTHORITY);
		clean_db = clean;
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		Log.v("TAG", "ENTER INTO DASHBORED");
		
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
				
			ContentProviderOperation.Builder builder_delete = ContentProviderOperation.newDelete(Dashboards.CONTENT_URI);
			batch.add(builder_delete.build());
			
		
		
		if (parser != null) {
			Gson gson = new Gson();
			dashBoard = gson.fromJson(parser, Dashboard.class);
			final ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(Dashboards.CONTENT_URI);
			builder.withValue(Dashboards.PATIENT_ID, dashBoard.getPatientId());
			builder.withValue(Dashboards.JSON, parser);
			batch.add(builder.build());
			
				
			Log.v("dashboard", dashBoard.getPatientId());
			Log.v("TAG", "LOADED INTO DATABASE");
		}
		return batch;
	}
	
	
	
	private boolean checkDataBase(String myPath){

	    SQLiteDatabase checkDB = null;

	    try{
	        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    } catch(SQLiteException e){ 
	        //database doesn't exist yet
	    }

	    if(checkDB != null){
	        checkDB.close();
	    }

	    return checkDB != null ? true : false;
	}
	
}
