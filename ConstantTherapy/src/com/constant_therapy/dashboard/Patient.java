package com.constant_therapy.dashboard;

public class Patient {
	public String patientId;
	public String username;
	public String imagePath;

	public Patient() {

		this.patientId = "";
		this.username = "";
		this.imagePath = "";
	}

	public void setId(String userId) {
		patientId = userId;
	}

	public String getId() {
		return this.patientId;
	}

	public void setUsername(String userName) {
		username = userName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setImagePath(String image) {
		imagePath = image;
	}

	public String getImagePath() {
		return this.imagePath;
	}
}
