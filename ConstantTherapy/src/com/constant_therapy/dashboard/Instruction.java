package com.constant_therapy.dashboard;

public class Instruction {
	public Boolean autoPlayInstrucIons = false;
	public PrimaryAspect primaryAspect;
	public OtherAspects otherAspects;
	
	public OtherAspects getOtherAspects() {
		return otherAspects;
	}
	public void setOtherAspects(OtherAspects otherAspects) {
		this.otherAspects = otherAspects;
	}
	public Boolean getAutoPlayInstrucIons() {
		return autoPlayInstrucIons;
	}
	public void setAutoPlayInstrucIons(Boolean autoPlayInstrucIons) {
		this.autoPlayInstrucIons = autoPlayInstrucIons;
	}
	public PrimaryAspect getPrimaryAspect() {
		return primaryAspect;
	}
	public void setPrimaryAspect(PrimaryAspect primaryAspect) {
		this.primaryAspect = primaryAspect;
	}
}
