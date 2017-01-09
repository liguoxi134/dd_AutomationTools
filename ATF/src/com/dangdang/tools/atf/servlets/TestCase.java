package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;
import com.dangdang.tools.atf.pages.TestCasePage;

/**
 * Servlet implementation class testcase
 */
@WebServlet(name = "tc", urlPatterns = { "/tc/*" })
public class TestCase extends BaseServlet {

	@Override
	public Class<?> getPageClass() {
		return TestCasePage.class;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getBasePath() {
		return "/pages/tc/";
	}

}
