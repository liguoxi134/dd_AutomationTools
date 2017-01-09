package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;
import com.dangdang.tools.atf.pages.TestInterfacePage;

/**
 * Servlet implementation class testinterface
 */
@WebServlet(name = "ti", urlPatterns = { "/ti/*" })
public class TestInterface extends BaseServlet {

	@Override
	public Class<?> getPageClass() {
		return TestInterfacePage.class;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getBasePath() {
		return "/pages/ti/";
	}
}
