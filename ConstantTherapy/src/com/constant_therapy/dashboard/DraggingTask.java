package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DraggingTask implements Serializable {
	public String id;
	public String type;
	public String patientId;
	public String taskTypeId;
	public int totalTaskCount;
	public int completedTaskCount;
	public ArrayList<DragandDropTask> tasks = new ArrayList<DragandDropTask>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public int getTotalTaskCount() {
		return totalTaskCount;
	}
	public void setTotalTaskCount(int totalTaskCount) {
		this.totalTaskCount = totalTaskCount;
	}
	public int getCompletedTaskCount() {
		return completedTaskCount;
	}
	public void setCompletedTaskCount(int completedTaskCount) {
		this.completedTaskCount = completedTaskCount;
	}
	public ArrayList<DragandDropTask> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<DragandDropTask> tasks) {
		this.tasks = tasks;
	}
	

}


	
	
