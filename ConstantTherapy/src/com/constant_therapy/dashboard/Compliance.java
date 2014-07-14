package com.constant_therapy.dashboard;

public class Compliance {
	public Long  date;
	public String duration;
	public int completedTaskCount;
	public boolean inClinic;
	
	public Compliance(){
		this.date = 0L;
		this.duration = "";
		this.completedTaskCount = 0;
		this.inClinic = false;
		
	}
		
	public Compliance(Long date, String duration, int taskCount, boolean clinic){
		this.date = date;
		this.duration = duration;
		this.completedTaskCount = taskCount;
		this.inClinic = clinic;
		
	}
	
	
	public void setDate(Long dateLong){
		date = dateLong;
	}
	
	public Long getDate(){
		return this.date;
	}
	
	public void setDuration(String duratio){
		duration = duratio;
	}
	
	public String getDuration(){
		return this.duration;
	}
	
	public void setCompletedTaskCount(int cummuAccur){
		completedTaskCount = cummuAccur;
	}
	
	public int getCompletedTaskCount(){
		return this.completedTaskCount;
	}
	
	public void setInClinic(boolean clinic){
		inClinic = clinic;
	}
	
	public boolean getInClinic(){
		return this.inClinic;
	}
}
