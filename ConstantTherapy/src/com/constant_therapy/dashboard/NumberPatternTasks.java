package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class NumberPatternTasks {

	public String taskType;
	public ArrayList<String> resources = new ArrayList<String>();
	public String resourceUrl;
	public String sessionId;
	public Boolean autoplayInstructions;
	public String pattern;
	public String answer;
	public String hint;
	public int level;
	public List<String> sequence = new ArrayList<String>();
	public List<String> instructionAudioPaths = new ArrayList<String>();
	public int answerIndex;
	public String instructions;
	
	
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
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<String> getSequence() {
		return sequence;
	}
	public void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}
	public List<String> getInstructionAudioPaths() {
		return instructionAudioPaths;
	}
	public void setInstructionAudioPaths(List<String> instructionAudioPaths) {
		this.instructionAudioPaths = instructionAudioPaths;
	}
	public int getAnswerIndex() {
		return answerIndex;
	}
	public void setAnswerIndex(int answerIndex) {
		this.answerIndex = answerIndex;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	
}
