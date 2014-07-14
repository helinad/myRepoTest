package com.constant_therapy.dashboard;

public class SelectionList {
	public String key;
	public String value;
	public String start;
	public String end;
	
	
	public SelectionList(){
		this.key = "";
		this.value = "";
		this.start = "";
		this.end = "";
	}
	
	public void setKey(String id){
		key = id;
	}
	
	public String getKey(){
		return this.key;
	}
	
	
	public void setValue(String id){
		value = id;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setStart(String id){
		start = id;
	}
	
	public String getStart(){
		return this.start;
	}
	
	public void setEnd(String id){
		end = id;
	}
	
	public String getEnd(){
		return this.end;
	}

}
