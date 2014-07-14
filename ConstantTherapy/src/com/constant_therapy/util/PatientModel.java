package com.constant_therapy.util;

/** patient model isn't really just a patient ... the "key" is a schedule ID or a session ID,
 * so really what this is used for is to store the info for a row in a selector that selects
 * a schedule or session for a particular patient */
public class PatientModel {
	
	private String username;
	private String imagePath;
	private String id;
	/** the session ID or schedule ID for this patient+session or patient+schedule item in a list */
	private String key;
	private String systemName;
	
	public PatientModel(String name, String image, String id, String key, String sys) {
		this.username = name;
		this.imagePath = image;
		this.id = id;
		this.key = key;
		this.systemName = sys;
	}
	
	
	public void setUsername(String name) {
		this.username = name;
	}

	public String getUsername() {
		return username;
	}
	
	public void setSystemname(String name) {
		this.systemName = name;
	}

	public String getSystemname() {
		return systemName;
	}

	public void setImagePath(String image) {

		this.imagePath = image;
	}

	public String getImagePath() {

		return imagePath;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getPatientId() {

		return this.id;
	}
	
	public void setKey(String id) {

		this.key = id;
	}

	public String getKey() {

		return this.key;
	}

}
