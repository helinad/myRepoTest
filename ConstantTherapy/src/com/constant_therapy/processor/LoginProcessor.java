package com.constant_therapy.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;

import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.Users;
import com.constant_therapy.webservice.WebServiceUser;
import com.google.gson.Gson;

public class LoginProcessor extends Processor {

	WebServiceUser user;
	public LoginProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		if (parser != null) {
			Gson gson = new Gson();
			user = gson.fromJson(parser, WebServiceUser.class);
		
			final ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(Users.CONTENT_URI);
			builder.withValue(Users.PATIENT_ID, user.getId());
			builder.withValue(Users.USERNAME, user.getUsername());
			builder.withValue(Users.FIRSTNAME, user.getFirstname());
			builder.withValue(Users.LASTNAME, user.getLastname());
			builder.withValue(Users.EMAIL, user.getEmail());
			builder.withValue(Users.USER_TYPE, user.getType());
			builder.withValue(Users.ACCESSTOKEN, user.getAccessToken());
			builder.withValue(Users.ISDEMO, user.isDemo());
			
			batch.add(builder.build());
		}
		// TODO Auto-generated method stub
		return batch;
	}
	
	

}
