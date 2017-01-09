package com.dangdang.tools.atf.servlets;

import javax.servlet.annotation.WebServlet;
import com.dangdang.tools.atf.pages.TestConfigPage;

@WebServlet(name = "io", urlPatterns = { "/io/*" })
public class TestConfig extends BaseServlet {

	@Override
	public Class<?> getPageClass() {
		return TestConfigPage.class;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getBasePath() {
		return "/pages/io/";
	}
}
