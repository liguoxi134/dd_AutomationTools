package com.dangdang.tools.atf.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.factory.DataFactory;

public class AdminPage extends BasePage {
	public static void reset(HttpServletRequest request, HttpServletResponse response) {
		DataFactory.reset();
	}

	public static void message(HttpServletRequest request, HttpServletResponse response) {
		try {
			String message = getString(request, "msg");
			WARN("set alert message as [" + message + "]");
			DataFactory.setMessage(message);
		} catch (Exception e) {
			WARN("Cannot set alert message, Exception Message: " + e.getMessage());
		}
	}

	public static void alert(HttpServletRequest request, HttpServletResponse response) {
		try {
			setContentType(response, ContentType.Text);
			String message = DataFactory.getMessage();
			response.getOutputStream().write(message.getBytes("UTF-8"));
		} catch (Exception e) {
			WARN("Cannot get alert message, Exception Message: " + e.getMessage());
		}
	}
}
