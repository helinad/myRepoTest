package com.constant_therapy.dashboard;


public class LatestTaskTypeResults {
	public String taskType;
	public int taskLevel;
	public int completedTaskCount;
	public Double accuracy;
	public String taskTypeSystemName;
	public Double avgLatency;
	public int percentSkipped;
	public int taskTypeId;
	public int percentCorrect;
	public int percentIncorrect;
	public Double minLatency;
	public Double maxLatency;
	public int totalTaskCount;
	
	
	
	public String getTaskTypeSystemName() {
		return taskTypeSystemName;
	}


	public void setTaskTypeSystemName(String taskTypeSystemName) {
		this.taskTypeSystemName = taskTypeSystemName;
	}


	public int getTaskTypeId() {
		return taskTypeId;
	}


	public void setTaskTypeId(int taskTypeId) {
		this.taskTypeId = taskTypeId;
	}


	public int getPercentCorrect() {
		return percentCorrect;
	}


	public void setPercentCorrect(int percentCorrect) {
		this.percentCorrect = percentCorrect;
	}


	public int getPercentIncorrect() {
		return percentIncorrect;
	}


	public void setPercentIncorrect(int percentIncorrect) {
		this.percentIncorrect = percentIncorrect;
	}


	public Double getMinLatency() {
		return minLatency;
	}


	public void setMinLatency(Double minLatency) {
		this.minLatency = minLatency;
	}


	public Double getMaxLatency() {
		return maxLatency;
	}


	public void setMaxLatency(Double maxLatency) {
		this.maxLatency = maxLatency;
	}


	public int getTotalTaskCount() {
		return totalTaskCount;
	}


	public void setTotalTaskCount(int totalTaskCount) {
		this.totalTaskCount = totalTaskCount;
	}


	public int getPercentSkipped() {
		return percentSkipped;
	}


	public void setPercentSkipped(int percentSkipped) {
		this.percentSkipped = percentSkipped;
	}

	public void setTaskType(String tast){
		taskType = tast;
	}
	
	public String getTaskType(){
		return this.taskType;
	}
	
	public void setTaskTypeSytemname(String tast){
		taskTypeSystemName = tast;
	}
	
	public String getTaskTypeSytemname(){
		return this.taskTypeSystemName;
	}
	
	public void setCompletedTaskCount(int count){
		completedTaskCount = count;
	}
	
	public int getCompletedTaskCount(){
		return this.completedTaskCount;
	}
	
	public void setTaskLevel(int level){
		taskLevel = level;
	}
	
	public int getTaskLevel(){
		return this.taskLevel;
	}
	
	public void setAccuracy(Double accur){
		accuracy = accur;
	}
	
	public Double getAccuracy(){
		return this.accuracy;
	}
	
	public void setAvgLatency(Double accur){
		avgLatency = accur;
	}
	
	public Double getAvgLatency(){
		return this.avgLatency;
	}

}
