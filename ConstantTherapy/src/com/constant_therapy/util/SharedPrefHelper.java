package com.constant_therapy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefHelper {
    
    public static final String LOGIN_STATE_SHARED_PREF = "logininfo";
    public static final String LOGIN_STATE_SHARED_PREF_KEY = "login";
    public static final String USERNAME_SHARED_PREF_KEY = "username";
    public static final String PASSWORD_SHARED_PREF_KEY = "password";
    public static final String USER_TYPE_SHARED_PREF = "typeinfo";
    public static final String USER_TYPE_SHARED_PREF_KEY = "type";
    public static final String ACCESS_TOKEN_INFO_SHARED_PREF = "accessTokeninfo";
    public static final String ACCESS_TOKEN_SHARED_PREF_KEY = "accessToken";

	public static void saveSharedPrefString(Context theContext, int prefId, int keyId, String valueString) {
		saveSharedPrefString(theContext, theContext.getString(prefId), 
				theContext.getString(keyId), valueString);
	}
	
	public static void saveSharedPrefString(Context theContext, String prefString, String keyString, String valueString) {
		Editor e = theContext.getSharedPreferences(prefString, Context.MODE_PRIVATE).edit();
		e.putString(keyString, valueString);
		e.commit();

	}
	
	public static void saveSharedPrefBoolean(Context theContext, int prefId, int keyId, boolean valueBoolean) {
		saveSharedPrefBoolean(theContext, theContext.getString(prefId), 
				theContext.getString(keyId), valueBoolean);
	}
	
	public static void saveSharedPrefBoolean(Context theContext, String prefString, String keyString, boolean valueBoolean) {
		Editor e = theContext.getSharedPreferences(prefString, Context.MODE_PRIVATE).edit();
		e.putBoolean(keyString, valueBoolean);
		e.commit();

	}
	
	public static String getSharedPrefString(Context theContext, int prefId, int keyString) {
		return getSharedPrefString(theContext, theContext.getString(prefId), 
				theContext.getString(keyString));
	}
	
	public static String getSharedPrefString(Context theContext, String prefString, String keyString) {
		
		SharedPreferences prefs = theContext.getSharedPreferences(
				prefString, Context.MODE_PRIVATE);
		String theResult = prefs.getString(keyString, null);
		return theResult;
	}
	
	public static boolean getSharedPrefBoolean(Context theContext, int prefId, int keyString) {
		return getSharedPrefBoolean(theContext, theContext.getString(prefId), 
				theContext.getString(keyString));
	}
	
	public static boolean getSharedPrefBoolean(Context theContext, String prefString, String keyString) {
		
		SharedPreferences prefs = theContext.getSharedPreferences(
				prefString, Context.MODE_PRIVATE);
		boolean theResult = prefs.getBoolean(keyString, false);
		return theResult;
	}
	
	public static String getAccessToken(Context theContext) {
		return SharedPrefHelper.getSharedPrefString(theContext, 
				ACCESS_TOKEN_INFO_SHARED_PREF, ACCESS_TOKEN_SHARED_PREF_KEY);
	}

	public static void saveAccessToken(Context theContext, String theValue) {
		SharedPrefHelper.saveSharedPrefString(theContext, 
				ACCESS_TOKEN_INFO_SHARED_PREF, ACCESS_TOKEN_SHARED_PREF_KEY, theValue);
	}
	
	public static void deleteAccessToken(Context theContext) {
		theContext.getSharedPreferences(ACCESS_TOKEN_INFO_SHARED_PREF, 0)
		.edit().clear().commit();
	}

	public static void saveUserType(Context theContext, String theValue) {
		SharedPrefHelper.saveSharedPrefString(theContext, 
				USER_TYPE_SHARED_PREF, USER_TYPE_SHARED_PREF_KEY, theValue);
	}

	public static void saveUsername(Context theContext, String theValue) {
		SharedPrefHelper.saveSharedPrefString(theContext, 
				LOGIN_STATE_SHARED_PREF, USERNAME_SHARED_PREF_KEY, theValue);
	}

	public static void savePassword(Context theContext, String theValue) {
		SharedPrefHelper.saveSharedPrefString(theContext, 
				LOGIN_STATE_SHARED_PREF, PASSWORD_SHARED_PREF_KEY, theValue);
	}

	public static String getUserType(Context theContext) {
		return SharedPrefHelper.getSharedPrefString(theContext, 
				USER_TYPE_SHARED_PREF, USER_TYPE_SHARED_PREF_KEY);
	}
	
	public static boolean getLoginState(Context theContext) {
		return SharedPrefHelper.getSharedPrefBoolean(theContext,
				LOGIN_STATE_SHARED_PREF, LOGIN_STATE_SHARED_PREF_KEY);
	}
	
	public static void saveLoginState(Context theContext, boolean theValue) {
		SharedPrefHelper.saveSharedPrefBoolean(theContext, 
				LOGIN_STATE_SHARED_PREF, LOGIN_STATE_SHARED_PREF_KEY, theValue);
	}


}
