package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class NumberPattern {
	public String id;
	public String type;
	public String patientId;
	public long startTime;
	public String taskTypeId;
	public int totalTaskCount;
	public int completedTaskCount;
	public List<NumberPatternTasks> tasks = new ArrayList<NumberPatternTasks>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public String getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
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
	public List<NumberPatternTasks> getTasks() {
		return tasks;
	}
	public void setTasks(List<NumberPatternTasks> tasks) {
		this.tasks = tasks;
	}
}

