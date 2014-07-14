package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class SummaryResponse {
	public String completedPracticeTime = null;
	public String expectedPracticeTime = null;
	public int percentCompliance = 0;
	public int completedTaskCount = 0;
	public int totalTaskCount = 0;
	public float percentTaskCompleted = 0;
	public double percentCorrect = 0.00d;
	public double percentIncorrect = 0.00d;
	public double percentSkipped = 0.00d;
	public double accuracy = 0.00d;
	public List<TaskResults> taskResults = new ArrayList<TaskResults>();
	
	
	public String getCompletedPracticeTime() {
		return completedPracticeTime;
	}
	public void setCompletedPracticeTime(String completedPracticeTime) {
		this.completedPracticeTime = completedPracticeTime;
	}
	public String getExpectedPracticeTime() {
		return expectedPracticeTime;
	}
	public void setExpectedPracticeTime(String expectedPracticeTime) {
		this.expectedPracticeTime = expectedPracticeTime;
	}
	public int getPercentCompliance() {
		return percentCompliance;
	}
	public void setPercentCompliance(int percentCompliance) {
		this.percentCompliance = percentCompliance;
	}
	public int getCompletedTaskCount() {
		return completedTaskCount;
	}
	public void setCompletedTaskCount(int completedTaskCount) {
		this.completedTaskCount = completedTaskCount;
	}
	public int getTotalTaskCount() {
		return totalTaskCount;
	}
	public void setTotalTaskCount(int totalTaskCount) {
		this.totalTaskCount = totalTaskCount;
	}
	public float getPercentTaskCompleted() {
		return percentTaskCompleted;
	}
	public void setPercentTaskCompleted(float percentTaskCompleted) {
		this.percentTaskCompleted = percentTaskCompleted;
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
	public List<TaskResults> getTaskResults() {
		return taskResults;
	}
	public void setTaskResults(List<TaskResults> taskResults) {
		this.taskResults = taskResults;
	}

}
