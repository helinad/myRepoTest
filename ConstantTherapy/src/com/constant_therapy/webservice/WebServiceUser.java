package com.constant_therapy.webservice;


/* POJO used to parse JSON responses from the web service that contain user information */
public class WebServiceUser extends WebServiceUserCredentials{
	public  String id;
	public  String username;
	public  String password;
	public  String firstName;
	public  String lastName;
	public  String email;
	public  Boolean isDemo; 
		
	public WebServiceUser() {
		
		this.id = "";
		this.username = "";
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.type = "";
		this.accessToken = "";
		this.isDemo = false;
	}
	
	public  void setId(String userId){
		id = userId;
	}
	
	public  String getId(){
		return this.id;
	}
	
	public  void setIsDemo(Boolean isdemo){
		isDemo = isdemo;
	}
	
	public  Boolean isDemo(){
		return this.isDemo;
	}
	
	public  void setUsername(String userName){
		username = userName;
	}
	
	public  String getUsername(){
		return this.username;
	}
	
	public  void setPassword(String userName){
		password = userName;
	}
	
	public  String getPassword(){
		return password;
	}
	
	public  void setFirstname(String firstname){
		firstName = firstname;
	}
	
	public  String getFirstname(){
		return this.firstName;
	}
	
	public  void setLastname(String lastname){
		lastName = lastname;
	}
	
	public  String getLastname(){
		return this.lastName;
	}
	
	public  void setEmail(String emailId){
		email = emailId;
	}
	
	public  String getEmail(){
		return this.email;
	}	 

}
