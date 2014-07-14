package com.constant_therapy.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PatternItem implements Serializable{
	public int itemId;
	public String name;
	public String category;
	public String imagePath;
	public String soundPath;
	public List<String> synonyms = new ArrayList<String>();
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSoundPath() {
		return soundPath;
	}
	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}
	public List<String> getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}
	
	
}
