package com.constant_therapy.util;

import com.constant_therapy.dashboard.TasksHierarchy;

import android.view.View;

public class NLevelItem implements NLevelListItem {
	
	private TasksHierarchy wrappedObject;
	private NLevelItem parent;
	private NLevelView nLevelView;
	private boolean isExpanded = false;
	
	public NLevelItem(TasksHierarchy wrappedObject, NLevelItem parent, NLevelView nLevelView) {
		this.wrappedObject = wrappedObject;
		this.parent = parent;
		this.nLevelView = nLevelView;
	}
	
	public NLevelItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TasksHierarchy getWrappedObject() {
		return wrappedObject;
	}
	
	@Override
	public boolean isExpanded() {
		return isExpanded;
	}
	@Override
	public NLevelListItem getParent() {
		return parent;
	}
	@Override
	public View getView() {
		return nLevelView.getView(this);
	}
	@Override
	public void toggle() {
		isExpanded = !isExpanded;
	}
}
