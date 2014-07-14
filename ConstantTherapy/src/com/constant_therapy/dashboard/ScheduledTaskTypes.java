package com.constant_therapy.dashboard;

public class ScheduledTaskTypes {
	public String scheduleId = null;
	public String id = null;
	public String taskTypeId = null;
	public int taskCount = 0;
	public int taskLevel = 0;
	public int taskOrder = 0;
	public String displayName = null;
	
	public Parameters parameters;
	
	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public int getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	public int getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(int taskLevel) {
		this.taskLevel = taskLevel;
	}
	public int getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(int taskOrder) {
		this.taskOrder = taskOrder;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	

}


 class Parameters{
	public int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}