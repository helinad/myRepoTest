package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class PlayTasks implements Serializable{

	public float timeInterval;
	public int numCorrect;
	@SerializedName("instructions")
	public String instructions;
	public Boolean showConfirmationDialog;
	public String taskType;
	public ArrayList<String> items = new ArrayList<String>();
	public ArrayList<String> resources = new ArrayList<String>();
	public ArrayList<String> instructionAudioPaths = new ArrayList<String>();
	public String resourceUrl;
	public String sessionId;
	public Boolean autoplayInstructions;
	

	public float getItemTimeout() {
		return timeInterval;
	}
	public void setItemTimeout(float itemTimeout) {
		this.timeInterval = itemTimeout;
	}
	public String getInstruction() {
		return instructions;
	}
	public void setInstruction(String instructions) {
		this.instructions = instructions;
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
