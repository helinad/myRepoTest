package com.constant_therapy.dashboard;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
	public Instruction instrucIons;
	public List<Simuli> sImuli = new ArrayList<Simuli>();
	public Response response;
	
	public Instruction getInstrucIons() {
		return instrucIons;
	}
	public void setInstrucIons(Instruction instrucIons) {
		this.instrucIons = instrucIons;
	}
	public List<Simuli> getsImuli() {
		return sImuli;
	}
	public void setsImuli(List<Simuli> sImuli) {
		this.sImuli = sImuli;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
}
