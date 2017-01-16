package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;

import com.dangdang.tools.atf.pages.DatabaseServerPage;

@WebServlet(name = "dbs", urlPatterns = { "/dbs/*" })
public class DatabaseServer extends BaseServlet {

	private static final long serialVersionUID = 5700780392203359293L;

	@Override
	public String getBasePath() {
		return "/pages/mgmt/dbs/";
	}

	@Override
	public Class<?> getPageClass() {
		return DatabaseServerPage.class;
	}

}
