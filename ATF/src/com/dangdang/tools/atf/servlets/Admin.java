package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;

import com.dangdang.tools.atf.pages.AdminPage;

@WebServlet(name = "admin", urlPatterns = { "/admin/*" })
public class Admin extends BaseServlet {

	@Override
	public Class<?> getPageClass() {
		return AdminPage.class;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getBasePath() {
		return "/pages/admin/";
	}
}
