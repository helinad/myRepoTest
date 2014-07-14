package com.constant_therapy.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActiveSegment implements Serializable{
	public String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
