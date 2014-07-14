package com.constant_therapy.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.format.DateUtils;
import android.util.Log;

import com.constant_therapy.processor.Processor;
import com.constant_therapy.processor.Processor.ProcessorException;
import com.constant_therapy.user.CTUser;
import com.constant_therapy.webservice.RestClient.RequestMethod;

public class RESTExecutor implements Executor {

	private static final String TAG = "RESTExecutor";
	private final ContentResolver mResolver;
	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";
	private static final int SECOND_IN_MILLIS = (int) DateUtils.SECOND_IN_MILLIS;


	public RESTExecutor(Context context, ContentResolver resolver) {
		mResolver = resolver;
	}

	public Boolean execute(String url, Processor processor)
			throws ProcessorException {

		Log.d(TAG, "execute(" + url + ")");

		return executes(url, processor);

	}
	
	public Boolean execute(String url, Processor processor, String taskType, String taskLevel)
			throws ProcessorException {

		Log.d(TAG, "execute(" + url + ")");
		Log.v(TAG, "EXECUTES URL");
		return executes(url, processor, taskType, taskLevel);

	}

	public final String getAccessToken() {
		return CTUser.getInstance().getAccessToken();

	}

	public void sign(String url, Processor processor) throws ProcessorException {

		RestClient client = new RestClient(url);
		client.AddParam("token", getAccessToken());
		try {
			client.Execute(RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reader = client.getResponse();
		processor.parseAndApply(reader, mResolver);

	}
	
	
	@Override
	public Boolean execute(String url, Processor processor, String taskTypeId,
			String taskTypeCount, String taskLevel, String parameters)
			throws ProcessorException {
		RestClient client = new RestClient(url);
		client.AddParam("token", getAccessToken());
		client.AddParam("taskTypeId", taskTypeId);
		client.AddParam("taskTypeCount", taskTypeCount);
		client.AddParam("taskLevel", taskLevel);
		client.AddParam("parameters", parameters);

		try {
			client.Execute(RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String reader = client.getResponse();
		if(isSessionExpried(reader)){
			return true;
		}else{
			processor.parseAndApply(reader, mResolver);
			return false;
		}
	}
	
	private Boolean executes(String url, Processor processor)
			throws ProcessorException {
		RestClient client = new RestClient(url);
		client.AddParam("token", getAccessToken());

		try {
			client.Execute(RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String reader = client.getResponse();
		Log.v(TAG, "GOT JSON");
		if(isSessionExpried(reader)){
			return true;
		}else{
			processor.parseAndApply(reader, mResolver);
			return false;
		}
	}
	
	
	
	private Boolean executes(String url, Processor processor, String taskType, String taskLevel)
			throws ProcessorException {
		RestClient client = new RestClient(url);
		client.AddParam("token", getAccessToken());
		client.AddParam("taskType", taskType);
		client.AddParam("level", taskLevel);

		try {
			client.Execute(RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String reader = client.getResponse();
		if(isSessionExpried(reader)){
			return true;
		}else{
			processor.parseAndApply(reader, mResolver);
			return false;
		}
	}
	
	
	public static boolean isSessionExpried(String result){
		try {
			JSONObject jsonObj = new JSONObject(result);
			if(jsonObj.has("authError")){
				return true;
			}
		
		}catch (JSONException e) {
			//e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Generate and return a {@link HttpClient} configured for general use,
	 * including setting an application-specific user-agent string.
	 */
	public static HttpClient getHttpClient(Context context) {
		final HttpParams params = new BasicHttpParams();

		// Use generous timeouts for slow mobile networks
		HttpConnectionParams
				.setConnectionTimeout(params, 20 * SECOND_IN_MILLIS);
		HttpConnectionParams.setSoTimeout(params, 20 * SECOND_IN_MILLIS);

		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpProtocolParams.setUserAgent(params, buildUserAgent(context));

		final DefaultHttpClient client = new DefaultHttpClient(params);

		client.addRequestInterceptor(new HttpRequestInterceptor() {
			public void process(HttpRequest request, HttpContext context) {
				// Add header to accept gzip content
				if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
					request.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
				}
			}
		});

		client.addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(HttpResponse response, HttpContext context) {
				// Inflate any responses compressed with gzip
				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					final Header encoding = entity.getContentEncoding();
					if (encoding != null) {
						for (HeaderElement element : encoding.getElements()) {
							if (element.getName().equalsIgnoreCase(
									ENCODING_GZIP)) {
								response.setEntity(new InflatingEntity(response
										.getEntity()));
								break;
							}
						}
					}
				}
			}
		});

		return client;
	}

	/**
	 * Build and return a user-agent string that can identify this application
	 * to remote servers. Contains the package name and version code.
	 */
	private static String buildUserAgent(Context context) {
		try {
			final PackageManager manager = context.getPackageManager();
			final PackageInfo info = manager.getPackageInfo(
					context.getPackageName(), 0);

			// Some APIs require "(gzip)" in the user-agent string.
			return info.packageName + "/" + info.versionName + " ("
					+ info.versionCode + ") (gzip)";
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	/**
	 * Simple {@link HttpEntityWrapper} that inflates the wrapped
	 * {@link HttpEntity} by passing it through {@link GZIPInputStream}.
	 */
	private static class InflatingEntity extends HttpEntityWrapper {
		public InflatingEntity(HttpEntity wrapped) {
			super(wrapped);
		}

		@Override
		public InputStream getContent() throws IOException {
			return new GZIPInputStream(wrappedEntity.getContent());
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}
	
	
	

	@Override
	public Boolean execute(String url, String json, Boolean isSave) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		String uri = url+"?token="+getAccessToken();
		String res = postData(uri, json);
		//Log.i("POSTJsonResponse", res);
		if(!isSave){
			try {
				JSONObject jsonObj = new JSONObject(res);
				if(!jsonObj.has("error")){
					String uri2 = url+"/"+jsonObj.getJSONArray("summaries").getJSONObject(0).getString("responseId")+"/upload";
					Log.v("tag", uri2+"?token="+getAccessToken()+"&uploadType=SCREENSHOT");
					RestClient client = new RestClient(uri2);
					client.AddParam("token", getAccessToken());
					client.AddParam("uploadType", "SCREENSHOT");
					
					try {
						client.Execute(RequestMethod.POST);
					} catch (Exception e) {
						e.printStackTrace();
					}

					String reader = client.getResponse();
					Log.i("responsePostJson", ""+reader);
				}
			
			}catch (JSONException e) {
				//e.printStackTrace();
				return false;
			}
		}
		return result;
	}
	
	
		
	public String postData(String result,String json) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);

		

		try {

		    HttpPost httppost = new HttpPost(result);
		    httppost.setHeader("Content-type", "application/json");

		    StringEntity se = new StringEntity(json); 
		    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		    httppost.setEntity(se); 

		    HttpResponse response = httpclient.execute(httppost);
		    String temp = EntityUtils.toString(response.getEntity());
		    Log.i("tag", temp);

		    return temp;
		} catch (ClientProtocolException e) {

		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public Boolean execute(String url, String baseline, Boolean isBaseline,
			Processor processor) throws ProcessorException {
		// TODO Auto-generated method stub
		RestClient client = new RestClient(url);
		if(isBaseline){
			client.AddParam("token", getAccessToken());
			client.AddParam("isBaseline", baseline);
		}else{
			client.AddParam("email", baseline);
		}

		try {
			client.Execute(RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String reader = client.getResponse();
		Log.v(TAG, "GOT JSON" + reader);
		if(isSessionExpried(reader)){
			return true;
		}else{
			processor.parseAndApply(reader, mResolver);
			return false;
		}
	}
	
	@Override
	public Boolean execute(String url, Processor processor, String username,
			String oldPassword, String newPassword) throws ProcessorException {
		RestClient client = new RestClient(url);
		client.AddParam("username", username);
		client.AddParam("oldPassword", oldPassword);
		client.AddParam("newPassword", newPassword);

		try {
			client.Execute(RequestMethod.GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String reader = client.getResponse();
		if(isSessionExpried(reader)){
			return true;
		}else{
			processor.parseAndApply(reader, mResolver);
			return false;
		}

	}
	

	
}
