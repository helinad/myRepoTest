package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class LetterToSoundTask {
	public String letter;
	public String correctLetterSoundPath;
	public String distractorLetterSoundPath;
	public String taskId;
	public String taskType;
	public String sessionId;
	public Boolean autoplayInstructions;
	ArrayList<String> resources = new ArrayList<String>();
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getCorrectLetterSoundPath() {
		return correctLetterSoundPath;
	}
	public void setCorrectLetterSoundPath(String correctLetterSoundPath) {
		this.correctLetterSoundPath = correctLetterSoundPath;
	}
	public String getDistractorLetterSoundPath() {
		return distractorLetterSoundPath;
	}
	public void setDistractorLetterSoundPath(String distractorLetterSoundPath) {
		this.distractorLetterSoundPath = distractorLetterSoundPath;
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
	public ArrayList<String> getResources() {
		return resources;
	}
	public void setResources(ArrayList<String> resources) {
		this.resources = resources;
	}
	
	

}
