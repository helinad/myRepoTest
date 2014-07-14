package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class SoundToLetterTask {
	public String correctLetter;
	public String phonemeSoundPath;
	public String distractorLetter;
	public String taskId;
	public String taskType;
	public String sessionId;
	public Boolean autoplayInstructions;
	
	List<String> resources = new ArrayList<String>();
	public String getCorrectLetter() {
		return correctLetter;
	}
	public void setCorrectLetter(String correctLetter) {
		this.correctLetter = correctLetter;
	}
	public String getPhonemeSoundPath() {
		return phonemeSoundPath;
	}
	public void setPhonemeSoundPath(String phonemeSoundPath) {
		this.phonemeSoundPath = phonemeSoundPath;
	}
	public String getDistractorLetter() {
		return distractorLetter;
	}
	public void setDistractorLetter(String distractorLetter) {
		this.distractorLetter = distractorLetter;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public Boolean getAutoplayInstructions() {
		return autoplayInstructions;
	}
	public void setAutoplayInstructions(Boolean autoplayInstructions) {
		this.autoplayInstructions = autoplayInstructions;
	}
	public List<String> getResources() {
		return resources;
	}
	public void setResources(List<String> resources) {
		this.resources = resources;
	}
}
