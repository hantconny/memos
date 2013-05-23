package com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public void execute(ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
