package com.constant_therapy.webservice;

import com.constant_therapy.processor.Processor;
import com.constant_therapy.processor.Processor.ProcessorException;


/**
 * Interface to define how webservices can be called on. All whats needed is the URL and {@link Processor} to process the callback.
 * @author Sam
 *
 */
public interface Executor {

	/**
	 * Executes a HTTP call to the given URL, the results will be processed by the {@link Processor}
	 * @param url
	 * @param processor
	 * @throws ProcessorException
	 */
	public Boolean execute(String url, Processor processor) throws ProcessorException;
	public Boolean execute(String url, Processor processor, String taskType, String taskLevel) throws ProcessorException;
	public Boolean execute(String url, Processor processor, String taskTypeId, String taskTypeCount, String taskLevel, String parameters) throws ProcessorException;
	public Boolean execute(String url, Processor processor, String username, String oldPassword, String newPassword)throws ProcessorException;
	
	
	public Boolean execute(String url, String json, Boolean isSave);
	public Boolean execute(String url, String baseline, Boolean isBaseline, Processor processor)throws ProcessorException;
	
	public void sign(String url, Processor processor) throws ProcessorException;
}
