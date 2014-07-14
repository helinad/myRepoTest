package com.constant_therapy.processor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.constant_therapy.dashboard.Patient;
import com.constant_therapy.provider.TherapyContract;
import com.constant_therapy.provider.TherapyContract.PatientsList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PatientImagepathProcessor extends Processor {

	/** not sure why this is "clinician alert", looks like "patient imagepath" */
	public PatientImagepathProcessor() {
		super(TherapyContract.CONTENT_AUTHORITY);
	}

	@Override
	public ArrayList<ContentProviderOperation> parse(String parser,
			ContentResolver resolver) throws IOException, JSONException {


		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

		ContentProviderOperation.Builder builder_delete = ContentProviderOperation.newDelete(PatientsList.CONTENT_URI);
		batch.add(builder_delete.build());

		if (parser != null) {
			Log.v("Tag", parser);
			Type mapType = new TypeToken<Map<String, Patient>>() {}.getType();

			Map<String, Patient> principalTranslation = new Gson().fromJson(parser, mapType);

			// we may not actually have any patients
			if (null != principalTranslation) {
				List<Patient> firstTranslationList = new ArrayList<Patient>(principalTranslation.values()); 



				for(int i = 0; i < principalTranslation.size(); i++){
					final ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(PatientsList.CONTENT_URI);
					builder.withValue(PatientsList.PATIENT_ID, firstTranslationList.get(i).getId());
					builder.withValue(PatientsList.USERNAME, firstTranslationList.get(i).getUsername());
					builder.withValue(PatientsList.IMAGEPATH, firstTranslationList.get(i).getImagePath());

					batch.add(builder.build());
				}
			}
		}
		// TODO Auto-generated method stub
		return batch;
	}
	
	
	boolean isTableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}
}