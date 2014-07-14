package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TasksHierarchyLevel2 {
	public int level;
	public String systemName;
	public String displayName;
	public String typeId;
	
	public String sampleImagePath = null;
	public String resourceUrl = null;
	
	@SerializedName("children")
	public List<TasksHierarchyLevel2> tasksHierarchyLevel3 = new ArrayList<TasksHierarchyLevel2>();
	
	public String getSampleImagePath() {
		return sampleImagePath;
	}
	public void setSampleImagePath(String sampleImagePath) {
		this.sampleImagePath = sampleImagePath;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	
	
	public void setTypeId(String id){
		typeId = id;
	}
		
	public String getTypeId(){
		return this.typeId;
	}
	
	public void setSystemName(String system){
		systemName = system;
	}
	
	
	public String getSystemName(){
		return this.systemName;
	}
	
	public void setDisplayName(String display){
		displayName = display;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
	
	public void setLevel(int leve){
		level = leve;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public List<TasksHierarchyLevel2> getTasksHierarchyLevel3(){
		return this.tasksHierarchyLevel3;
	}
	
	public void setTasksHierarchyLevel3(List<TasksHierarchyLevel2> selection){
		tasksHierarchyLevel3 = selection;
	}
	
	
}
