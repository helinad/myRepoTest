package com.constant_therapy.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;

import com.constant_therapy.provider.TherapyContract;

public class BaselineProcessor extends Processor {

	
	public BaselineProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
			
		// baseline process doesn't do anything with the response - so, for example,
		// if the response contains info that might have gone into the provider it is
		// not put into the provider - of course if the response from the web service
		// doesn't contain anything that belongs in a provider this is entirely 
		// appropriate
		
		return null;
	}
	
	
	
}

