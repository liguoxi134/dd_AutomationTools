package com.dangdang.tools.atf.pages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.entity.TestSystem;
import com.dangdang.tools.atf.factory.DataFactory;

public class TestSystemPage extends BasePage {

	// Path: /List
	public static void List(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestSystemPage.List()");
		try {
			DEBUG("TestSystemPage.List()： Getting all test system");
			List<TestSystem> tsList = DataFactory.getAllTestSystem();
			DEBUG("TestSystemPage.List()： Test system count:" + tsList.size());
			String tsJson = TestSystem.toJson(tsList);
			DEBUG("TestSystemPage.List()： Test System List" + tsJson);
			writeMessage(response, tsJson, true);
		} catch (Exception e) {
			ERROR("TestSystemPage.List()： Get test system failed, Exception Message: " + e.getMessage());
			writeMessage(response, "出现异常："+e.getMessage(), false);}
	}
}
