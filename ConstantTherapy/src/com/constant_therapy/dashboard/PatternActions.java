package com.constant_therapy.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PatternActions  implements Serializable{
	public int location;
	public PatternItem item;
	
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public PatternItem getItem() {
		return item;
	}
	public void setItem(PatternItem item) {
		this.item = item;
	}

}
