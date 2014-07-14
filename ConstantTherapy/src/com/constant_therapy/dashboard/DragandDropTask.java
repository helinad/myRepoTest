package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

public class DragandDropTask implements Serializable {


	public String task;
	public String taskType;
	public ArrayList<String> sequence = new ArrayList<String>();
	public ArrayList<String> resources = new ArrayList<String>();
	public String sessionId;
	public Boolean autoplayInstructions;
	

     public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public ArrayList<String> getItems() {
		return sequence;
	}
	public void setItems(ArrayList<String> sequence) {
		this.sequence = sequence;
	}
	public ArrayList<String> getResources() {
		return resources;
	}
	public void setResources(ArrayList<String> resources) {
		this.resources = resources;
	}


	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Boolean getAutoplayInstructions() {
		return autoplayInstructions;
	}
	public void setAutoplayInstructions(Boolean autoplayInstructions) {
		this.autoplayInstructions = autoplayInstructions;
	}
}
