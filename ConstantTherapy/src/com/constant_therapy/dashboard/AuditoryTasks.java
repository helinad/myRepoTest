package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AuditoryTasks implements Serializable{
	public int rows;
	public int columns;
	public float itemTimeout;
	public Boolean showConfirmationDialog;
	public String taskType;
	public ArrayList<NamingItems> distractors = new ArrayList<NamingItems>();
	public ArrayList<AuditoryAutoFill> autofill = new ArrayList<AuditoryAutoFill>();
	public ArrayList<AuditoryActions> actions = new ArrayList<AuditoryActions>();
	public ArrayList<String> resources = new ArrayList<String>();
	public String resourceUrl;
	public String sessionId;
	public Boolean autoplayInstructions;
	public Boolean ordered;
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public float getItemTimeout() {
		return itemTimeout;
	}
	public void setItemTimeout(float itemTimeout) {
		this.itemTimeout = itemTimeout;
	}
	public Boolean getShowConfirmationDialog() {
		return showConfirmationDialog;
	}
	public void setShowConfirmationDialog(Boolean showConfirmationDialog) {
		this.showConfirmationDialog = showConfirmationDialog;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public ArrayList<NamingItems> getDistractors() {
		return distractors;
	}
	public void setDistractors(ArrayList<NamingItems> distractors) {
		this.distractors = distractors;
	}
	public ArrayList<AuditoryAutoFill> getAutofill() {
		return autofill;
	}
	public void setAutofill(ArrayList<AuditoryAutoFill> autofill) {
		this.autofill = autofill;
	}
	public ArrayList<AuditoryActions> getActions() {
		return actions;
	}
	public void setActions(ArrayList<AuditoryActions> actions) {
		this.actions = actions;
	}
	public ArrayList<String> getResources() {
		return resources;
	}
	public void setResources(ArrayList<String> resources) {
		this.resources = resources;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
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
	public Boolean getOrdered() {
		return ordered;
	}
	public void setOrdered(Boolean ordered) {
		this.ordered = ordered;
	}
}
