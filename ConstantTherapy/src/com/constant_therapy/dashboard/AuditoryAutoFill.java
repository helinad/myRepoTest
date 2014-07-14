package com.constant_therapy.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuditoryAutoFill implements Serializable{
	public NamingItems  item;
	public int location;
	
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

}
