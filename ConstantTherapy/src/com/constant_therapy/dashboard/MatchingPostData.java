package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class MatchingPostData {
	public List<MatchingData> clickPath = new ArrayList<MatchingData>();
	public int touchCount;
	
	public List<MatchingData> getClickPath() {
		return clickPath;
	}
	public void setClickPath(List<MatchingData> clickPath) {
		this.clickPath = clickPath;
	}
	public int getTouchCount() {
		return touchCount;
	}
	public void setTouchCount(int touchCount) {
		this.touchCount = touchCount;
	}

}
