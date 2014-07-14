package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class DragTask {
	public int autoplayInstructions;
	public List<Choices> choices = new ArrayList<Choices>();
	public List<String> instructionAudioPaths = new ArrayList<String>();
	public String instructions = null;
	public String resourceUrl = null;
	public List<String> resources = new ArrayList<String>();
	public String taskType = null;
	public String sessionId = null;
	
	
	public int getAutoplayInstructions() {
		return autoplayInstructions;
	}
	public void setAutoplayInstructions(int autoplayInstructions) {
		this.autoplayInstructions = autoplayInstructions;
	}
	public List<Choices> getChoices() {
		return choices;
	}
	public void setChoices(List<Choices> choices) {
		this.choices = choices;
	}
	public List<String> getInstructionAudioPaths() {
		return instructionAudioPaths;
	}
	public void setInstructionAudioPaths(List<String> instructionAudioPaths) {
		this.instructionAudioPaths = instructionAudioPaths;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
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
	
	

}
