package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderTasks implements Serializable{
	
	public String taskType = null;
	public String sessionId = null;
	public String resourceUrl;
	public List<String> resources = new ArrayList<String>();
	public Boolean autoplayInstructions = false;
	public ArrayList<NamingItems> items = new ArrayList<NamingItems>();
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public List<String> getResources() {
		return resources;
	}
	public void setResources(List<String> resources) {
		this.resources = resources;
	}
	public Boolean getAutoplayInstructions() {
		return autoplayInstructions;
	}
	public void setAutoplayInstructions(Boolean autoplayInstructions) {
		this.autoplayInstructions = autoplayInstructions;
	}
	public ArrayList<NamingItems> getItems() {
		return items;
	}
	public void setItems(ArrayList<NamingItems> items) {
		this.items = items;
	} 
	
}
