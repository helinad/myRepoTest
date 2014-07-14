package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ActiveTask implements Serializable{
	public List<Integer> filledSegmentIndices = new ArrayList<Integer>();
	public String taskType = null;
	public String sessionId = null;
	public String resourceUrl;
	public List<String> resources = new ArrayList<String>();
	public ArrayList<ActiveSegment> segments = new ArrayList<ActiveSegment>();
	public ArrayList<ActiveSegment> distractors = new ArrayList<ActiveSegment>();	
	public Boolean autoplayInstructions = false;
	
	public List<Integer> getFilledSegmentIndices() {
		return filledSegmentIndices;
	}
	public void setFilledSegmentIndices(List<Integer> filledSegmentIndices) {
		this.filledSegmentIndices = filledSegmentIndices;
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
	public ArrayList<ActiveSegment> getSegments() {
		return segments;
	}
	public void setSegments(ArrayList<ActiveSegment> segments) {
		this.segments = segments;
	}
	public ArrayList<ActiveSegment> getDistractors() {
		return distractors;
	}
	public void setDistractors(ArrayList<ActiveSegment> distractors) {
		this.distractors = distractors;
	}
	public Boolean getAutoplayInstructions() {
		return autoplayInstructions;
	}
	public void setAutoplayInstructions(Boolean autoplayInstructions) {
		this.autoplayInstructions = autoplayInstructions;
	}
	
}
