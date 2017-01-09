package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;

import com.dangdang.tools.atf.pages.JobPage;

/**
 * Servlet implementation class testinterface
 */
@WebServlet(name = "job", urlPatterns = { "/job/*" })
public class Job extends BaseServlet {

	@Override
	public Class<?> getPageClass() {
		return JobPage.class;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getBasePath() {
		return "/pages/mgmt/job/";
	}
}
