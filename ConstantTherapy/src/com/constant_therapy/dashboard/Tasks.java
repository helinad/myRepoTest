package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

public class Tasks implements Serializable{
	public int rows;
	public int columns;
	public float itemTimeout;

	
	public String instruction;
	
	public Boolean showConfirmationDialog;
	public String taskType;
	
	public ArrayList<String> items = new ArrayList<String>();
	public ArrayList<String> resources = new ArrayList<String>();
	public ArrayList<String> instructionAudioPaths = new ArrayList<String>();
	public String resourceUrl;
	public String sessionId;
	public Boolean autoplayInstructions;
	
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
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instructions) {
		this.instruction = instructions;
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
	public ArrayList<String> getItems() {
		return items;
	}
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	public ArrayList<String> getResources() {
		return resources;
	}
	public void setResources(ArrayList<String> resources) {
		this.resources = resources;
	}
	public ArrayList<String> getInstructionAudioPaths() {
		return instructionAudioPaths;
	}
	public void setInstructionAudioPaths(ArrayList<String> instructionAudioPaths) {
		this.instructionAudioPaths = instructionAudioPaths;
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
}
