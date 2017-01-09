package com.dangdang.tools.atf.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.usertype.DynamicParameterizedType.ParameterType;

import com.dangdang.tools.atf.entity.TestCase;
import com.dangdang.tools.atf.entity.TestConfig;
import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entityenum.RequestType;
import com.dangdang.tools.atf.factory.DataFactory;
import com.dangdang.tools.atf.helper.HttpHelper;
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

	public static void Run(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestConfigPage.Run()");
		try {
			String url = getString(request, "url");
			DEBUG("TestConfigPage.Run(): Run URL:" + url);
			String itype = getString(request, "itype");
			ParameterType ptype = getEnum(request, "ptype", ParameterType.class, ParameterType.JSON);
			DEBUG("TestConfigPage.Run(): Run METHOD:" + itype);
			String args = getString(request, "args");
			DEBUG("TestConfigPage.Run(): Run ARGS:" + args);
			response.setContentType("text/plain; charset=UTF-8");
			if (itype.equalsIgnoreCase("post")) {
				String result = HttpHelper.doPost(url, args, ptype);
				writeMessage(response, result, true);
			} else if (itype.equalsIgnoreCase("get")) {
				String result = HttpHelper.sendGet(url, args);
				writeMessage(response, result, true);
			} else {
				WARN("TestConfigPage.Run(): 接口类型为非标准类型");
				writeMessage(response, "接口类型为非标准类型", false);
			}
		} catch (Exception e) {
			ERROR("TestConfigPage.Run(): Run test case failed, Exception Message:" + e.getMessage());
			writeMessage(response, "异常:" + e.getMessage(), false);
		}
	}

}
