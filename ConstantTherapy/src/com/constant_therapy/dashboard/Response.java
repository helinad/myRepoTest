package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class Response {

	public int itemTimeout = 0;
	public List<Choices> choices = new ArrayList<Choices>();

	public int getItemTimeout() {
		return itemTimeout;
	}

	public void setItemTimeout(int itemTimeout) {
		this.itemTimeout = itemTimeout;
	}

	public List<Choices> getChoices() {
		return choices;
	}

	public void setChoices(List<Choices> choices) {
		this.choices = choices;
	}

}




