package com.dangdang.tools.atf.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.entity.TestCase;
import com.dangdang.tools.atf.entity.TestConfig;
import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entityenum.RequestType;
import com.dangdang.tools.atf.factory.DataFactory;
import com.dangdang.tools.atf.helper.HttpHelper;
import com.dangdang.tools.atf.helper.MySqlHelper;
import com.dangdang.tools.atf.models.VerifyDatabaseConfig;
import com.dangdang.tools.common.format.IFormater;
import com.dangdang.tools.common.format.json.JsonFormater;
import com.dangdang.tools.common.format.url.UrlFormater;
import com.dangdang.tools.common.format.xml.XmlFormater;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestConfigPage extends BasePage {

	public static void format(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestConfigPage.format()");
		try {
			String type = getString(request, "type");
			String text = getString(request, "text");
			DEBUG("TestConfigPage.format(): Format type:" + type + ", text:" + text);
			IFormater formater = null;
			if (type.equalsIgnoreCase("xml")) {
				formater = new XmlFormater();
			} else if (type.equalsIgnoreCase("json")) {
				formater = new JsonFormater();
			} else {
				formater = new UrlFormater();
			}
			String ftext = formater.format(text);
			DEBUG("TestConfigPage.format(): format result: " + ftext);
			writeMessage(response, ftext, true);
		} catch (Exception e) {
			ERROR("TestConfigPage.format(): Format failed, Exception Message:" + e.getMessage());
			writeMessage(response, e.getMessage(), false);
		}
	}

	// Path: /Refresh
	public static void Refresh(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestConfigPage.Refresh()");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("requestMethod", null);
			map.put("requestType", null);
			map.put("requestUrl", null);
			map.put("requestBody", null);
			map.put("headers", null);
			map.put("resultVerify", null);
			map.put("verifyDbs", null);
			int testCaseId = getInt(request, "rid");
			String testSystemId = getString(request, "testSystemId");
			DEBUG("TestConfigPage.Refresh(): Try to get test case[case id:" + testCaseId + ", test system:" + testSystemId + "]");
			TestCase testCase = DataFactory.getTestCaseById(testCaseId, testSystemId);

			TestInterface ti = DataFactory.getTestInterfaceById(testCase.getTestInterfaceId(), testSystemId);
			if (ti != null) {
				map.put("requestMethod", ti.getType());
				map.put("requestUrl", ti.getUrl());
				DEBUG("TestConfigPage.Refresh(): Test interface is not null: " + ti.toJson());
			}

			TestConfig testConfig = DataFactory.getTestConfigByTestCaseId(testCaseId, testSystemId);
			if (testConfig != null) {
				map.put("requestType", testConfig.getRequestType());
				map.put("requestBody", testConfig.getRequestBody());
				map.put("headers", testConfig.getRequestHeaders());
				map.put("resultVerify", testConfig.getResultVerify());
			}

			List<VerifyDatabaseConfig> verifyDbs = DataFactory.getVerifyDatabaseConfigs();
			if (verifyDbs != null) {
				map.put("verifyDbs", verifyDbs);
				DEBUG("TestConfigPage.Refresh(): Test verifyDbs is not null: " + VerifyDatabaseConfig.toJson(verifyDbs));
			}

			map.put("code", true);
			writeMessage(response, map);
		} catch (Exception e) {
			ERROR("TestConfigPage.Refresh(): Get test parameter and expect result failed, Exception Message:" + e.getMessage());
			writeMessage(response, "出现错误：" + e.getMessage(), false);
		}
	}

	public static void Save(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestConfigPage.Save()");
		try {
			int testCaseId = getInt(request, "rid");
			String testSystemId = getString(request, "testSystemId");
			String summary = getString(request, "summary");
			String inArgs = getString(request, "inArgs");
			String resultVerify = getString(request, "resultVerify");

			TestConfig testConfig = new TestConfig();
			ObjectMapper mapper = new ObjectMapper();
			if (summary != null && !summary.isEmpty()) {
				JsonNode summaryNode = mapper.readTree(summary);
				JsonNode headersNode = summaryNode.get("headers");
				String headers = headersNode != null ? headersNode.toString() : "[]";
				testConfig.setRequestHeaders(headers);
			}
			if (inArgs != null && !inArgs.isEmpty()) {
				JsonNode inArgsNode = mapper.readTree(inArgs);
				JsonNode resultType = inArgsNode.get("requestType");
				RequestType requestType = resultType != null ? RequestType.valueOf(resultType.textValue()) : RequestType.TEXT;
				testConfig.setRequestType(requestType);
				JsonNode requestBody = inArgsNode.get("requestBody");
				testConfig.setRequestBody(requestBody != null ? requestBody.textValue() : "");
			}
			testConfig.setResultVerify(resultVerify);

			boolean result = DataFactory.updateTestConfig(testConfig, testCaseId, testSystemId);
			if (result) {
				writeMessage(response, "保存成功", true);
			} else {
				writeMessage(response, "保存失败", false);
			}
		} catch (Exception e) {
			ERROR("TestConfigPage.Save(): Update test parameter and expect result failed, Exception Message:" + e.getMessage());
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}

	public static void Send(HttpServletRequest request, HttpServletResponse response) {
		try {
			String requestBody = getString(request, "requestBody");
			String[] requestHeaders = getStrings(request, "requestHeaders[]");
			String requestMethod = getString(request, "requestMethod");
			String requestUrl = getString(request, "requestUrl");
			String responseData = "";
			if (requestMethod.equalsIgnoreCase("get")) {
				responseData = HttpHelper.sendGet(requestUrl, requestBody, requestHeaders);
			} else {
				responseData = HttpHelper.doPost(requestUrl, requestBody, requestHeaders);
			}
			writeMessage(response, responseData, true);
		} catch (Exception e) {
			writeMessage(response, e.getMessage(), true);
		}
	}

	public static void Query(HttpServletRequest request, HttpServletResponse response) {
		String responseData = null;
		try {
			String server = getString(request, "server");
			String database = getString(request, "database");
			ObjectMapper map = new ObjectMapper();
			JsonNode serverNode = map.readTree(server);

			String serverType = serverNode.get("type").textValue().toLowerCase();
			String ip = serverNode.get("ip").textValue();
			String port = serverNode.get("port").textValue();
			String user = serverNode.get("uid").textValue();
			String pwd = serverNode.get("pwd").textValue();
			String connStr = "jdbc:" + serverType + "://" + ip + ":" + port + "/" + database + "?user=" + user + "&password=" + pwd;
			String sql = getString(request, "query");
			responseData = MySqlHelper.executeQuery(connStr, sql);
			writeMessage(response, responseData, true);
		} catch (Exception e) {
			responseData = e.getMessage();
			writeMessage(response, responseData, false);
		}
	}
}
