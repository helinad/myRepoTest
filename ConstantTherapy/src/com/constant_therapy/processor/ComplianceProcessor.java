package com.constant_therapy.processor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;


import com.constant_therapy.dashboard.Compliance;
import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.ComplianceList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ComplianceProcessor extends Processor {
	
	private boolean clean_db = false;
	public ComplianceProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);

	}
	
	public ComplianceProcessor(boolean clean) {
		super(TherapyContract.CONTENT_AUTHORITY);
		clean_db = clean;
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		
		
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		ContentProviderOperation.Builder builder = ContentProviderOperation.newDelete(ComplianceList.CONTENT_URI);
		batch.add(builder.build());
			
		if (parser != null) {
			
			Type type = new TypeToken<List<Compliance>>(){}.getType();
			List<Compliance> firstTranslationList = new ArrayList<Compliance>(); 
			firstTranslationList = new Gson().fromJson(parser, type); 
			
			for(int i = 0; i < firstTranslationList.size(); i++){
				final ContentProviderOperation.Builder builder_score = ContentProviderOperation.newInsert(ComplianceList.CONTENT_URI);
				builder_score.withValue(ComplianceList.JSON, parser);
				builder_score.withValue(ComplianceList.DATE, firstTranslationList.get(i).getDate());
				builder_score.withValue(ComplianceList.DURATION, firstTranslationList.get(i).getDuration());
				builder_score.withValue(ComplianceList.COMPLETEDTASKCOUNT, firstTranslationList.get(i).getCompletedTaskCount());
				batch.add(builder_score.build());
			}
			
		}
		return batch;
	}
	
	
	
	
	
}

