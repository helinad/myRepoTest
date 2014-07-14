package com.constant_therapy.dashboard;

public class TasksType {
	public String taxonomy = null;
	public Double accuracyMean = 0.0d;
	public Double latencyMean = 0.0d;
	public Double accuracyBaseline = 0.0d;
	public Double accuracyPercentile = 0.0d;
	public String taskTypeId = null;
	public Double accuracyAverage = 0.0d;
	
	public Double getAccuracyBaseline() {
		return accuracyBaseline;
	}

	public void setAccuracyBaseline(Double accuracyBaseline) {
		this.accuracyBaseline = accuracyBaseline;
	}

	public Double getAccuracyPercentile() {
		return accuracyPercentile;
	}

	public void setAccuracyPercentile(Double accuracyPercentile) {
		this.accuracyPercentile = accuracyPercentile;
	}

	public String getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Double getAccuracyAverage() {
		return accuracyAverage;
	}

	public void setAccuracyAverage(Double accuracyAverage) {
		this.accuracyAverage = accuracyAverage;
	}

	
	
	
	public void setTaxonomy(String taxo){
		taxonomy = taxo;
	}
	
	public String getTaxonomy(){
		return this.taxonomy;
	}
	
	
	public void setAccuracyMean(Double taxo){
		accuracyMean = taxo;
	}
	
	public Double getAccuracyMean(){
		return this.accuracyMean;
	}
	
	public void setLatencyMean(Double taxo){
		latencyMean = taxo;
	}
	
	public Double getLatencyMean(){
		return this.latencyMean;
	}

}
