package com.constant_therapy.processor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;

import com.constant_therapy.dashboard.Dashboard;
import com.constant_therapy.dashboard.Scores;
import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.ScoresList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CombinedPlotProcessor extends Processor {
	Dashboard dashBoard;
	private boolean clean_db = false;
	public CombinedPlotProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);

	}
	
	public CombinedPlotProcessor(boolean clean) {
		super(TherapyContract.CONTENT_AUTHORITY);
		clean_db = clean;
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
		// TODO Auto-generated method stub
		
		
		
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		
		ContentProviderOperation.Builder builder = ContentProviderOperation.newDelete(ScoresList.CONTENT_URI);
		batch.add(builder.build());
			
		
		if (parser != null) {
						
			
			Type type = new TypeToken<List<Scores>>(){}.getType();
			List<Scores> firstTranslationList = new ArrayList<Scores>(); 
			firstTranslationList = new Gson().fromJson(parser, type); 
			
			for(int i = 0; i < firstTranslationList.size(); i++){
				final ContentProviderOperation.Builder builder_score = ContentProviderOperation.newInsert(ScoresList.CONTENT_URI);
				builder_score.withValue(ScoresList.JSON, parser);
				builder_score.withValue(ScoresList.DATE, firstTranslationList.get(i).getDate());
				builder_score.withValue(ScoresList.ACCURACY, firstTranslationList.get(i).getAccuracy());
				builder_score.withValue(ScoresList.CUMULATIVEACCURACY, firstTranslationList.get(i).getCummulativeAccuracy());
				batch.add(builder_score.build());
			}
		}
		return batch;
	}
	
	
	
	
	
}

