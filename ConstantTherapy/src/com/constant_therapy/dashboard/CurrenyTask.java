package com.constant_therapy.dashboard;

import java.util.ArrayList;

public class CurrenyTask {
	
	public String taskType;
	public ArrayList<String> resources = new ArrayList<String>();
	public ArrayList<String> instructionAudioPaths = new ArrayList<String>();
	public String resourceUrl;
	public String sessionId;
	public String taskId;
	public Boolean autoplayInstructions;
	public String text;
	public String answer;
	public String question;
	public String filePath;
	public String instructions;
	public int level;
	
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Boolean getAutoplayInstructions() {
		return autoplayInstructions;
	}
	public void setAutoplayInstructions(Boolean autoplayInstructions) {
		this.autoplayInstructions = autoplayInstructions;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
