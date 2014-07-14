package com.constant_therapy.util;

import com.constant_therapy.dashboard.TasksHierarchy;

import android.view.View;

public interface NLevelListItem {

	public boolean isExpanded();
	public void toggle();
	public NLevelListItem getParent();
	public View getView();
	public TasksHierarchy getWrappedObject();
}
