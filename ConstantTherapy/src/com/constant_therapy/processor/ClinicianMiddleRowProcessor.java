package com.constant_therapy.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.util.Log;

import com.constant_therapy.dashboard.Dashboard;
import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.LatestTypeResultsList;
import com.google.gson.Gson;

public class ClinicianMiddleRowProcessor extends Processor {
	Dashboard dashBoard;
	private boolean clean_db = false;
	public ClinicianMiddleRowProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);

	}
	
	public ClinicianMiddleRowProcessor(boolean clean) {
		super(TherapyContract.CONTENT_AUTHORITY);
		clean_db = clean;
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		
		
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		
			
			ContentProviderOperation.Builder builder = ContentProviderOperation.newDelete(LatestTypeResultsList.CONTENT_URI);
			batch.add(builder.build());
			
			
		
		
		if (parser != null) {
			Gson gson = new Gson();
			dashBoard = gson.fromJson(parser, Dashboard.class);
			for(int i = 0; i < dashBoard.getTaskResults().size(); i++){
				final ContentProviderOperation.Builder builder_lastest = ContentProviderOperation.newInsert(LatestTypeResultsList.CONTENT_URI);
				builder_lastest.withValue(LatestTypeResultsList.PATIENT_ID, dashBoard.getPatientId());
				builder_lastest.withValue(LatestTypeResultsList.JSON, parser);
				batch.add(builder_lastest.build());
			}
			
			Log.v("dashboard", dashBoard.getPatientId());
		}
		return batch;
	}
	
	
	
	
	
}

