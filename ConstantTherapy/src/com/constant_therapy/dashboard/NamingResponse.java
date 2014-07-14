package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class NamingResponse {
	
	public String id;
	public String type;
	public String patientId;
	public String taskTypeId;
	public int totalTaskCount;
	public long startTime;
	public int completedTaskCount;
	public List<NamingTasks> tasks = new ArrayList<NamingTasks>();
	public int taskLevel;
	public Parameters parameters = new Parameters();
	public List<String> completedResponses = new ArrayList<String>();
	
	
	
	public int getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(int taskLevel) {
		this.taskLevel = taskLevel;
	}
	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	public List<String> getCompletedResponses() {
		return completedResponses;
	}
	public void setCompletedResponses(List<String> completedResponses) {
		this.completedResponses = completedResponses;
	}
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
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public int getCompletedTaskCount() {
		return completedTaskCount;
	}
	public void setCompletedTaskCount(int completedTaskCount) {
		this.completedTaskCount = completedTaskCount;
	}
	public List<NamingTasks> getTasks() {
		return tasks;
	}
	public void setTasks(List<NamingTasks> tasks) {
		this.tasks = tasks;
	}

}
