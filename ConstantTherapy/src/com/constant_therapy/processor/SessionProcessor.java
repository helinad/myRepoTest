package com.constant_therapy.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;

import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.Session;

public class SessionProcessor extends Processor {

	
	public SessionProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
			
			
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		
		ContentProviderOperation.Builder builder_delete = ContentProviderOperation.newDelete(Session.CONTENT_URI);
		batch.add(builder_delete.build());
		
		if (parser != null) {
			final ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(Session.CONTENT_URI);
			
			builder.withValue(Session.JSON, parser);
			batch.add(builder.build());
			
		}
		// TODO Auto-generated method stub
		return batch;
	}
	
	
	
}
