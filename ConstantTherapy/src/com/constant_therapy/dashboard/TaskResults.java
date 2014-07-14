package com.constant_therapy.dashboard;

public class TaskResults {
	public String taskTypeSystemName;
	public String taskTypeId;
	public String taskType;
	public int taskLevel;
	public int totalTaskCount;
	public int completedTaskCount;
	public double percentCorrect;
	public double percentIncorrect;
	public double percentSkipped;
	public double accuracy;
	public double avgLatency = 0.000d;
	public double minLatency = 0.000d;
	public double maxLatency = 0.000d;
	
	public String getTaskTypeSystemName() {
		return taskTypeSystemName;
	}
	public void setTaskTypeSystemName(String taskTypeSystemName) {
		this.taskTypeSystemName = taskTypeSystemName;
	}
	public String getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public int getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(int taskLevel) {
		this.taskLevel = taskLevel;
	}
	public int getTotalTaskCount() {
		return totalTaskCount;
	}
	public void setTotalTaskCount(int totalTaskCount) {
		this.totalTaskCount = totalTaskCount;
	}
	public int getCompletedTaskCount() {
		return completedTaskCount;
	}
	public void setCompletedTaskCount(int completedTaskCount) {
		this.completedTaskCount = completedTaskCount;
	}
	public double getPercentCorrect() {
		return percentCorrect;
	}
	public void setPercentCorrect(double percentCorrect) {
		this.percentCorrect = percentCorrect;
	}
	public double getPercentIncorrect() {
		return percentIncorrect;
	}
	public void setPercentIncorrect(double percentIncorrect) {
		this.percentIncorrect = percentIncorrect;
	}
	public double getPercentSkipped() {
		return percentSkipped;
	}
	public void setPercentSkipped(double percentSkipped) {
		this.percentSkipped = percentSkipped;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public double getAvgLatency() {
		return avgLatency;
	}
	public void setAvgLatency(double avgLatency) {
		this.avgLatency = avgLatency;
	}
	public double getMinLatency() {
		return minLatency;
	}
	public void setMinLatency(double minLatency) {
		this.minLatency = minLatency;
	}
	public double getMaxLatency() {
		return maxLatency;
	}
	public void setMaxLatency(double maxLatency) {
		this.maxLatency = maxLatency;
	}

}
