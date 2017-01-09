package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;
import com.dangdang.tools.atf.pages.TestSystemPage;

@WebServlet(name = "ts", urlPatterns = { "/ts/*" })
public class TestSystem extends BaseServlet {

	@Override
	public Class<?> getPageClass() {
		return TestSystemPage.class;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getBasePath() {
		return "/pages/ts/";
	}
}
