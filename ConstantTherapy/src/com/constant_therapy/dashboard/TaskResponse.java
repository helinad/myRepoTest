package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class TaskResponse {

	public String systemName = null;
	public int level;
	public String sessionId = null;
	public String urlPrex = null;
	
	public List<TaskList> tasks = new ArrayList<TaskList>();

	public List<TaskList> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskList> tasks) {
		this.tasks = tasks;
	}

	public String getUrlPrex() {
		return urlPrex;
	}

	public void setUrlPrex(String urlPrex) {
		this.urlPrex = urlPrex;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
