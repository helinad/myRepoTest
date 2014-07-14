package com.constant_therapy.webservice;

/* POJO class that is used to parse JSON responses from the web service that contain authentication info */
public class WebServiceUserCredentials {
	public  String accessToken;
	public  String type;
	
	public WebServiceUserCredentials() {
		
		this.accessToken = "";
		this.type = "";
	}
	
	public  void setAccessToken(String token){
		this.accessToken = token;
	}
	
	public  String getAccessToken(){
		return accessToken;
	}
	
	public  void setType(String userType){
		this.type = userType;
	}
	
	public  String getType(){
		return this.type;
	}

}
