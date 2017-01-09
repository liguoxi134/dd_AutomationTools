package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;

import com.dangdang.tools.atf.pages.LogPage;

@WebServlet(name = "log", urlPatterns = { "/log/*" })
public class Log extends BaseServlet {

	private static final long serialVersionUID = 6510277067882324537L;

	@Override
	public String getBasePath() {
		return "/pages/mgmt/log/";
	}

	@Override
	public Class<?> getPageClass() {
		return LogPage.class;
	}

}
