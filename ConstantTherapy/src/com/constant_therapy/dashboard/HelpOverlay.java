package com.constant_therapy.dashboard;

/*
 * @Developed By: Arumugam
 */

import java.util.ArrayList;

public class HelpOverlay {


	ArrayList<String> images = new ArrayList<String>();
	public String duration;

	public void setHelpImage(ArrayList<String> helpimage) {
		this.images = helpimage;
	}

	public ArrayList<String> getHelpImage() {
		return images;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}
}
