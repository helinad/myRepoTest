package com.constant_therapy.dashboard;


public class PostValues {
	public boolean skipped;
	public String clientVersionNumber;
	public String clientHardwareType;
	public long timestamp;
	public double longitude;
	public String clientOSVersion;
	public double latitude;
	public int accuracy;
	public long startTime;
	public String taskType;
	public String additionalDataJson;
	public double latency;
	public String taskJson;
	public int patientId;
	public int sessionId;
	public boolean isSkipped() {
		return skipped;
	}
	public void setSkipped(boolean skipped) {
		this.skipped = skipped;
	}
	public String getClientVersionNumber() {
		return clientVersionNumber;
	}
	public void setClientVersionNumber(String clientVersionNumber) {
		this.clientVersionNumber = clientVersionNumber;
	}
	public String getClientHardwareType() {
		return clientHardwareType;
	}
	public void setClientHardwareType(String clientHardwareType) {
		this.clientHardwareType = clientHardwareType;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getClientOSVersion() {
		return clientOSVersion;
	}
	public void setClientOSVersion(String sdkInt) {
		this.clientOSVersion = sdkInt;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude2) {
		this.latitude = latitude2;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getAdditionalDataJson() {
		return additionalDataJson;
	}
	public void setAdditionalDataJson(String additionalDataJson) {
		this.additionalDataJson = additionalDataJson;
	}
	public double getLatency() {
		return latency;
	}
	public void setLatency(double latency) {
		this.latency = latency;
	}
	public String getTaskJson() {
		return taskJson;
	}
	public void setTaskJson(String taskJson) {
		this.taskJson = taskJson;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
	

}
