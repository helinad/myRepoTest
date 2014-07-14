package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class Homework {
	public String id = null;
	public String patientId = null;
	public Boolean active = true;
	public List<ScheduledTaskTypes> scheduledTaskTypes = new ArrayList<ScheduledTaskTypes>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<ScheduledTaskTypes> getScheduledTaskTypes() {
		return scheduledTaskTypes;
	}
	public void setScheduledTaskTypes(
			List<ScheduledTaskTypes> scheduledTaskTypes) {
		this.scheduledTaskTypes = scheduledTaskTypes;
	}

}
