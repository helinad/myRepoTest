package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class WrittenTasks implements Serializable {
	public List<Integer> filledLetterIndices = new ArrayList<Integer>();
	public NamingItems item = new NamingItems();
	public List<String> itemLetterSoundPaths = new ArrayList<String>();
	public List<String> distractorLetters = new ArrayList<String>();
	public List<String> distractorLetterSoundPaths = new ArrayList<String>();
	public String taskType = null;
	public String sessionId = null;
	public List<String> resources = new ArrayList<String>();
	public Boolean autoplayInstructions = false;
	
	public List<Integer> getFilledLetterIndices() {
		return filledLetterIndices;
	}
	public void setFilledLetterIndices(List<Integer> filledLetterIndices) {
		this.filledLetterIndices = filledLetterIndices;
	}
	public NamingItems getItem() {
		return item;
	}
	public void setItem(NamingItems item) {
		this.item = item;
	}
	public List<String> getItemLetterSoundPaths() {
		return itemLetterSoundPaths;
	}
	public void setItemLetterSoundPaths(List<String> itemLetterSoundPaths) {
		this.itemLetterSoundPaths = itemLetterSoundPaths;
	}
	public List<String> getDistractorLetters() {
		return distractorLetters;
	}
	public void setDistractorLetters(List<String> distractorLetters) {
		this.distractorLetters = distractorLetters;
	}
	public List<String> getDistractorLetterSoundPaths() {
		return distractorLetterSoundPaths;
	}
	public void setDistractorLetterSoundPaths(
			List<String> distractorLetterSoundPaths) {
		this.distractorLetterSoundPaths = distractorLetterSoundPaths;
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

}
