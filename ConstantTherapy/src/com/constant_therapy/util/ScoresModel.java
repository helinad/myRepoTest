package com.constant_therapy.util;




public class ScoresModel {
	public Long  date;
	public Double accuracy;
	public Double cumulativeAccuracy;
	
	public ScoresModel(){
		this.date = 0L;
		this.accuracy = 0.0;
		this.cumulativeAccuracy = 0.0;
		
	}
	
	
	public ScoresModel(Long date, Double accuracy, Double cumulative){
		this.date = date;
		this.accuracy = accuracy;
		this.cumulativeAccuracy = cumulative;
		
	}
	public void setDate(Long dateLong){
		date = dateLong;
	}
	
	public Long getDate(){
		return this.date;
	}
	
	public void setAccuracy(Double accur){
		accuracy = accur;
	}
	
	public Double getAccuracy(){
		return this.accuracy;
	}
	
	public void setCummulativeAccuracy(Double cummuAccur){
		cumulativeAccuracy = cummuAccur;
	}
	
	public Double getCummulativeAccuracy(){
		return this.cumulativeAccuracy;
	}

}

