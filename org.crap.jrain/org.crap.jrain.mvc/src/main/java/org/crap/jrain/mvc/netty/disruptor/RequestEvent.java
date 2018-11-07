package org.crap.jrain.mvc.netty.disruptor;

import org.crap.jrain.mvc.Treatment;

public class RequestEvent<TRequest, TResponse> {
	
	private String mapping;
	
	private TRequest request;
	
	private TResponse response;
	
	private Treatment<TRequest, TResponse> treatment;

	public Treatment<TRequest, TResponse> getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment<TRequest, TResponse> treatment) {
		this.treatment = treatment;
	}

	public TRequest getRequest() {
		return request;
	}

	public void setRequest(TRequest request) {
		this.request = request;
	}

	public TResponse getResponse() {
		return response;
	}

	public void setResponse(TResponse response) {
		this.response = response;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	
	public void clear() {
		mapping = null;
		request = null;
		response = null;
		treatment = null;
	}
}
