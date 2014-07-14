package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SpokenWordTask implements Serializable{
	
	public String taskType;
	public ArrayList<String> resources = new ArrayList<String>();
	public ArrayList<String> instructionAudioPaths = new ArrayList<String>();
	public String resourceUrl;
	public String sessionId;
	public Boolean autoplayInstructions;
	public String instructions;
	public CorrectChoice correctChoice;
	public ArrayList<CorrectChoice> choices = new ArrayList<CorrectChoice>();
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
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
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public CorrectChoice getCorrectChoice() {
		return correctChoice;
	}
	public void setCorrectChoice(CorrectChoice correctChoice) {
		this.correctChoice = correctChoice;
	}
	public ArrayList<CorrectChoice> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<CorrectChoice> choices) {
		this.choices = choices;
	}
	
	
}

