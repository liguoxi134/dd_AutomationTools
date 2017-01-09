package com.dangdang.tools.atf.servlets;

import static com.dangdang.tools.atf.models.LoggerObject.DEBUG;
import static com.dangdang.tools.atf.models.LoggerObject.ERROR;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {

	public abstract String getBasePath();

	public abstract Class<?> getPageClass();

	private static final long serialVersionUID = 1L;

	private static final Pattern pattern = Pattern.compile("^/([a-z]*)");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DEBUG("GET: " + request.getPathInfo());
		String _path = request.getPathInfo().toLowerCase();
		Matcher m = pattern.matcher(_path);
		if (m.find()) {
			String path = m.group(1);
			DEBUG("Find request view page name:" + path);
			try {
				String uri = getBasePath() + path + ".jsp";
				DEBUG("Getting jsp file: " + uri);
				request.getRequestDispatcher(uri).forward(request, response);
				DEBUG("Forward to jsp file: " + uri);
			} catch (Exception e) {
				ERROR("Message: " + e.getMessage());
				ERROR("Class Name: " + e.getClass().getName());
				ERROR("Cause Message: " + e.getCause().getMessage());

				response.getOutputStream().write(("!!!Error!!!:" + e.getMessage()).getBytes());
			}
		} else {
			DEBUG("Cannot find request view page name in path :" + _path);
			response.getOutputStream().write("Error".getBytes());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DEBUG("POST: " + request.getPathInfo());
		String _path = request.getPathInfo().toLowerCase();
		Matcher m = pattern.matcher(_path);
		if (m.find()) {
			String path = m.group(1);
			DEBUG("Find request view page name:" + path);
			try {
				Method[] ms = getPageClass().getMethods();
				for (Method method : ms) {
					if (method.getName().equalsIgnoreCase(path)) {
						DEBUG("Getting method: " + method.getName());
						method.invoke(null, request, response);
						DEBUG("Invoke method: " + method.getName());
						return;
					}
				}
				ERROR("Cannot get method for path: " + path);
				response.getOutputStream().write(("!!!View Missmatch!!!:" + path).getBytes());
			} catch (Exception e) {
				ERROR(e.toString());
				response.getOutputStream().write(("!!!Error!!!:" + e.getMessage()).getBytes());
			}
		} else {
			ERROR("Cannot find request view page name in path :" + _path);
			response.getOutputStream().write("Error".getBytes());
		}
	}

}
