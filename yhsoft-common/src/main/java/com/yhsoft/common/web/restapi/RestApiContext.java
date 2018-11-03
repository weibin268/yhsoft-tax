package com.yhsoft.common.web.restapi;

import com.yhsoft.common.web.model.MyJsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestApiContext {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private MyJsonResult result;

	private String action;
	
	private String args;
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public MyJsonResult getResult() {
		return result;
	}

	public void setResult(MyJsonResult result) {
		this.result = result;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
	
	
}
