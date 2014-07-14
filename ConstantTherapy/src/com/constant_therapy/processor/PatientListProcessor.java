package com.constant_therapy.processor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;

import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.Patients;
import com.constant_therapy.webservice.WebServiceUser;
import com.google.gson.Gson;

public class PatientListProcessor extends Processor {

	WebServiceUser[] user;
	public PatientListProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		if (parser != null) {
			Gson gson = new Gson();
			user = gson.fromJson(parser, WebServiceUser[].class);
		for(int i = 0; i < user.length; i++){
			final ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(Patients.CONTENT_URI);
			builder.withValue(Patients.PATIENT_ID, user[i].getId());
			builder.withValue(Patients.USERNAME, user[i].getUsername());
			builder.withValue(Patients.FIRSTNAME, user[i].getFirstname());
			builder.withValue(Patients.LASTNAME, user[i].getLastname());
			builder.withValue(Patients.EMAIL, user[i].getEmail());
			builder.withValue(Patients.USER_TYPE, user[i].getType());
			builder.withValue(Patients.ACCESSTOKEN, user[i].getAccessToken());
			builder.withValue(Patients.ISDEMO, user[i].isDemo());
			
			batch.add(builder.build());
		}
		}
		// TODO Auto-generated method stub
		return batch;
	}
}