package com.constant_therapy.dashboard;

public class Choices {

	public Boolean isCorrect = false;
	public PrimaryAspect primaryAspect;
	public OtherAspects otherAspects;

	public Choices(Boolean correct, PrimaryAspect aspec, OtherAspects othaspec) {
		this.isCorrect = correct;
		this.primaryAspect = aspec;
		this.otherAspects = othaspec;

	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public PrimaryAspect getPrimaryAspect() {
		return primaryAspect;
	}

	public void setPrimaryAspect(PrimaryAspect primaryAspect) {
		this.primaryAspect = primaryAspect;
	}

	public OtherAspects getOtherAspects() {
		return otherAspects;
	}

	public void setOtherAspects(OtherAspects otherAspects) {
		this.otherAspects = otherAspects;
	}
}