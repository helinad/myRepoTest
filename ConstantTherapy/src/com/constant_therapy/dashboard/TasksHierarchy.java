package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TasksHierarchy {
	public int level;
	public String systemName;
	public String displayName;
	
	public String typeId;
	
	public String sampleImagePath = null;
	public String resourceUrl = null;
	public boolean visibility = true;
	public String description = null;
	
	

	@SerializedName("children")
	public List<TasksHierarchy> tasksHierarchyLevel1 = new ArrayList<TasksHierarchy>();
	
	public int maxLevel;
	
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getVisibility() {
		return visibility;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

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
	
	public List<TasksHierarchy> getTasksHierarchyLevel1(){
		return this.tasksHierarchyLevel1;
	}
	
	public void setTasksHierarchyLevel1(List<TasksHierarchy> selection){
		tasksHierarchyLevel1 = selection;
	}
	
	
}
