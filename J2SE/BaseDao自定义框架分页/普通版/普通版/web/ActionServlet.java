package com.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String key = uri.substring(uri.lastIndexOf("/") + 1, uri.length() - 3);
		String actionName = "com.web.action." + key + "Action";
		String formName = "com.web.form." + key + "Form";
		
		try {
			ActionForm form = null;
			try {
				form = (ActionForm)Class.forName(formName).newInstance();
				form.setRequest(request);
			} catch (Exception e) {}
			
			Action action = (Action)Class.forName(actionName).newInstance();
			action.execute(form, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
