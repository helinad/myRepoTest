package com.constant_therapy.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.constant_therapy.network.MySSLSocketFactory;

public class JSONParser {
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	public HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
	        HttpConnectionParams.setSoTimeout(params, 20 * 1000);
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){
	    try{
	        if(method == "POST"){
	            //DefaultHttpClient httpClient = new DefaultHttpClient();
	        	
	            HttpPost httpPost = new HttpPost(url);
	            httpPost.setEntity(new UrlEncodedFormEntity(params));
	            Log.e("[TEST]",params.toString());
	            HttpResponse httpResponse = getNewHttpClient().execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	        }
	        else if(method == "GET"){
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            String paramString = URLEncodedUtils.format(params, "iso-8859-1");
	            url+="?" + paramString;
	            HttpGet httpGet = new HttpGet(url);

	            HttpResponse httpResponse = httpClient.execute(httpGet);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	        }
	    } catch(UnsupportedEncodingException e){
	        e.printStackTrace();
	    } catch(ClientProtocolException e){
	        e.printStackTrace();
	    } catch(IOException e){
	        e.printStackTrace();
	    }

	    try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while((line = reader.readLine()) != null){
	            sb.append(line+"\n");
	        }
	        is.close();
	        json = sb.toString();
	    } catch(Exception e){
	        Log.e("Buffer Error", "Error converting result " + e.toString());
	    }

	    try{
	        jObj = new JSONObject(json);
	    } catch(JSONException e){
	        Log.e("JSON Parser", "Error parsing data " + e.toString() + " DATA: "+json);
	    }

	    return jObj;
	}
	}