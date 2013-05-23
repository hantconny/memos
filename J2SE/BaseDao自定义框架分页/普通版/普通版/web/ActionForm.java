package com.web;

import javax.servlet.http.HttpServletRequest;

public abstract class ActionForm {
	HttpServletRequest request;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public abstract Object getEntity();
}
