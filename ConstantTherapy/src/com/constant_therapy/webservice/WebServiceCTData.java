package com.constant_therapy.webservice;


/** POJO class that is used to unpack JSON in service response to login request */
public class WebServiceCTData {
	public WebServiceUserCredentials ctdata;

	public WebServiceUserCredentials getCtdata() {
		return ctdata;
	}

	public void setCtdata(WebServiceUserCredentials ctdata) {
		this.ctdata = ctdata;
	}
}
