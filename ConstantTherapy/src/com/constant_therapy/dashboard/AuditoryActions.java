package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AuditoryActions implements Serializable{
	public NamingItems  item;
	public int location;
	ArrayList<String> instructionAudioPaths = new ArrayList<String>();
	
	public NamingItems getItem() {
		return item;
	}
	public void setItem(NamingItems item) {
		this.item = item;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public ArrayList<String> getInstructionAudioPaths() {
		return instructionAudioPaths;
	}
	public void setInstructionAudioPaths(ArrayList<String> instructionAudioPaths) {
		this.instructionAudioPaths = instructionAudioPaths;
	}
}
