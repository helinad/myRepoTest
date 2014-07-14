package com.constant_therapy.dashboard;

import java.util.ArrayList;

public class Dashboard {
	public String patientId;
	public int totalTaskCount = 0;
	public Double accuracyAverage;
	
	public ArrayList<Scores> scores = new ArrayList<Scores>();
	public ArrayList<LatestTaskTypeResults> latestTaskTypeResults = new ArrayList<LatestTaskTypeResults>();
	public ArrayList<SelectionList> selectionList = new ArrayList<SelectionList>();
	public ArrayList<LatestTaskTypeResults> taskResults = new ArrayList<LatestTaskTypeResults>();
	public ArrayList<Compliance> compliance = new ArrayList<Compliance>();
	public Dashboard(){
		this.patientId = "";
		this.totalTaskCount = 0;
		this.accuracyAverage = 0.0;
	}
	
	public Dashboard(String id, int count, Double accuracy){
		this.patientId = id;
		this.totalTaskCount = count;
		this.accuracyAverage = accuracy;
	}
	
	
	public void setTotalTaskCount(int count){
		totalTaskCount = count;
	}
	
	public int getTotalTaskCount(){
		return this.totalTaskCount;
	}
	
	public void setAccuacyAverage(double accuracy){
		accuracyAverage = accuracy;
	}
	
	public double getAccuacyAverage(){
		return this.accuracyAverage;
	}
	
	public void setPatientId(String id){
		patientId = id;
	}
	
	public String getPatientId(){
		return this.patientId;
	}
	
	public void setScores(ArrayList<Scores> score){
		scores = score;
	}
	
	public ArrayList<Scores> getScores(){
		return this.scores;
	}
	
	
	public void setLatestTaskTypeResults(ArrayList<LatestTaskTypeResults> latestResults){
		latestTaskTypeResults = latestResults;
	}
	
	public ArrayList<LatestTaskTypeResults> getLatestTaskTypeResults(){
		return this.latestTaskTypeResults;
	}
	
	public void setSelectionList(ArrayList<SelectionList> selection){
		selectionList = selection;
	}
	
	public ArrayList<SelectionList> getSelectionList(){
		return this.selectionList;
	}
	
	public void setTaskResults(ArrayList<LatestTaskTypeResults> selection){
		taskResults = selection;
	}
	
	public ArrayList<LatestTaskTypeResults> getTaskResults(){
		return this.taskResults;
	}
	
	public void setCompliance(ArrayList<Compliance> selection){
		compliance = selection;
	}
	
	public ArrayList<Compliance> getCompliance(){
		return this.compliance;
	}

}
