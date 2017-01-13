package com.dangdang.tools.atf.pages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.entity.TestCase;
import com.dangdang.tools.atf.entity.TestConfig;
import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entityenum.TestInterfaceType;
import com.dangdang.tools.atf.factory.DataFactory;
import com.dangdang.tools.atf.helper.HttpHelper;
import com.dangdang.tools.atf.helper.MySqlHelper;
import com.dangdang.tools.atf.models.VerifyDatabaseConfig;
import com.dangdang.tools.common.compare.IComparer;
import com.dangdang.tools.common.compare.json.JsonComparer;
import com.dangdang.tools.common.compare.text.TextComparer;
import com.dangdang.tools.common.compare.xml.XmlComparer;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCasePage extends BasePage {

	public static void Add(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestCasePage.Add()");
		try {
			int rid = getInt(request, "rid");
			String testSystemId = getString(request, "testSystemId");
			String name = getString(request, "name");
			String description = getString(request, "description");

			boolean isExist = DataFactory.checkTestCase(name, testSystemId);
			if (isExist) {
				throw new Exception("测试用例的名称已经存在，请不要重复添加!!");
			}

			DEBUG("TestCasePage.Add(): Get Parameter [rid:" + rid + ",testSystemId:" + testSystemId + ",name:" + name + ",description:" + description + "]");
			TestCase tmp = new TestCase(name, description);
			DEBUG("TestCasePage.Add(): Package new test case: " + tmp.toJson());
			DataFactory.updateTestCase(tmp, rid, testSystemId);
			DEBUG("TestCasePage.Add(): Saved test case: " + tmp.toJson());
			writeMessage(response, "保存成功", true);
		} catch (Exception e) {
			ERROR("TestCasePage.Add(): Add test case failed, Exception Result: " + e.getMessage());
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}

	public static void Edit(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestCasePage.Edit()");
		try {
			String testSystemId = getString(request, "testSystemId");
			int id = getInt(request, "id");
			int rid = getInt(request, "rid");
			String name = getString(request, "name");
			String description = getString(request, "description");
			DEBUG("TestCasePage.Edit(): Get Parameter [id:" + id + ",rid:" + rid + ",testSystemId:" + testSystemId + ",name:" + name + ",description:" + description + "]");
			TestCase tmp = new TestCase(name, description);
			tmp.setId(id);
			DEBUG("TestCasePage.Edit(): Package edit test case: " + tmp.toJson());
			DataFactory.updateTestCase(tmp, rid, testSystemId);
			DEBUG("TestCasePage.Edit(): Update test case: " + tmp.toJson());
			writeMessage(response, "保存成功", true);
		} catch (Exception e) {
			ERROR("TestCasePage.Edit(): Edit test case failed, Exception Result: " + e.getMessage());
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}

	public static void Copy(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestCasePage.Copy()");
		try {
			String testSystemId = getString(request, "testSystemId");
			int rid = getInt(request, "rid");
			int[] ids = getInts(request, "ids[]");
			DEBUG("TestCasePage.Copy()： Get Parameter [rid:" + rid + ",testSystemId:" + testSystemId + ",copy ids:" + Arrays.toString(ids) + "]");
			DataFactory.copyTestCase(ids, rid, testSystemId);
			writeMessage(response, "克隆成功", true);
		} catch (Exception e) {
			ERROR("TestCasePage.Copy()： Copy test case failed, Exception Result: " + e.getMessage());
			writeMessage(response, "克隆失败：" + e.getMessage(), false);
		}
	}

	// Path: /Delete
	public static void Delete(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestCasePage.Delete()");
		try {
			String testSystemId = getString(request, "testSystemId");
			int[] ids = getInts(request, "ids[]");
			DEBUG("TestCasePage.Delete(): Get Parameter [testSystemId:" + testSystemId + ",delete ids:" + Arrays.toString(ids) + "]");
			for (int i = 0; i < ids.length; i++) {
				DataFactory.deleteTestCase(ids[i], testSystemId);
			}
			writeMessage(response, "删除成功", true);
		} catch (Exception e) {
			ERROR("TestCasePage.Delete(): Delete test case failed, Exception Result: " + e.getMessage());
			writeMessage(response, "删除失败：" + e.getMessage(), false);
		}
	}

	public static void Execute(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestCasePage.Execute()");
		ObjectMapper mapper = new ObjectMapper();
		String testSystemId = "";
		String[] ids = null;
		try {
			testSystemId = getString(request, "testSystemId");
			DEBUG("TestCasePage.Execute()： Get System Id: " + testSystemId);
			ids = getString(request, "ids").split(",");
			DEBUG("TestCasePage.Execute()： Get Execute Case Ids: " + Arrays.toString(ids));
		} catch (Exception e) {
			ids = new String[0];
			ERROR("TestCasePage.Execute(): Cannot some of parameters, Exception Message: " + e.getMessage());
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonGenerator generator = null;
		try {
			DEBUG("TestCasePage.Execute()： Create JSON Generator, Encode:" + JsonEncoding.UTF8);
			generator = mapper.getFactory().createGenerator(out, JsonEncoding.UTF8);
			generator.writeStartArray();// [
		} catch (IOException ex) {
			ERROR("TestCasePage.Execute(): Cannot create JSON Generator, Exception Message: " + ex.getMessage());
			writeMessage(response, "出现异常：" + ex.getMessage(), false);
		}
		for (String id : ids) {
			if (id.trim().isEmpty()) {
				WARN("TestCasePage.Execute(): Test Case Id is empty, skip execute!");
				continue;
			}
			DEBUG("TestCasePage.Execute()： Ready to parse case id to int: " + id);
			int testCaseId = Integer.parseInt(id);
			DEBUG("TestCasePage.Execute()： Getting test case by id:" + testCaseId);
			TestCase testCase = DataFactory.getTestCaseById(testCaseId, testSystemId);
			DEBUG("TestCasePage.Execute()： Getting test interface...");
			TestInterface testInterface = DataFactory.getTestInterfaceById(testCase.getTestInterfaceId(), testSystemId);
			DEBUG("TestCasePage.Execute()： Getting testConfig...");
			TestConfig testConfig = DataFactory.getTestConfigByTestCaseId(testCaseId, testSystemId);
			String headers = testConfig.getRequestHeaders();
			JsonNode headerNodes = null;
			try {
				headerNodes = mapper.readTree(headers);
			} catch (Exception e) {
				headerNodes = null;
				ERROR("Map request Header Exception: " + e.getMessage());
			}
			List<String> headerList = new ArrayList<String>();
			if (headerNodes != null && headerNodes.isArray()) {
				for (JsonNode node : headerNodes) {
					headerList.add(node.textValue());
				}
			}
			String real = "";
			if (testInterface.getType() == TestInterfaceType.POST) {
				real = HttpHelper.doPost(testInterface.getUrl(), testConfig.getRequestBody(), headerList.toArray(new String[0]));
			} else if (testInterface.getType() == TestInterfaceType.GET) {
				real = HttpHelper.sendGet(testInterface.getUrl(), testConfig.getRequestBody(), headerList.toArray(new String[0]));
			}
			JsonNode configNode = null;
			try {
				configNode = mapper.readTree(testConfig.getResultVerify());
			} catch (Exception e) {
				configNode = null;
				ERROR("Map Result Verify Exception: " + e.getMessage());
			}
			try {
				generator.writeStartObject();// {
				generator.writeObjectField("testCase", testCase);// testCase:{}
				generator.writeObjectField("testInterface", testInterface);// testInterface:{}
			} catch (IOException e) {
				try {
					if (generator != null) {
						generator.close();
					}
					if (out != null) {
						out.close();
					}
				} catch (IOException ex) {
					ERROR("Close json generator Exception: " + ex.getMessage());
				}
				ERROR("Map test case and test interface Exception: " + e.getMessage());
				writeMessage(response, e.getMessage(), false);
			}
			try {
				executeConfig(configNode, "returnConfig", generator, real);
			} catch (Exception e) {
				ERROR("Map returnConfig Exception: " + e.getMessage());
				writeMessage(response, e.getMessage(), false);
			}
			try {
				executeConfig(configNode, "databaseConfig", generator, real);
			} catch (Exception e) {
				ERROR("Map databaseConfig Exception: " + e.getMessage());
				writeMessage(response, e.getMessage(), false);
			}
			try {
				executeConfig(configNode, "logConfig", generator, real);
			} catch (Exception e) {
				ERROR("Map logConfig Exception: " + e.getMessage());
				writeMessage(response, e.getMessage(), false);
			}
			try {
				generator.writeEndObject();// }
			} catch (IOException e) {
				ERROR("Write end object Exception: " + e.getMessage());
				writeMessage(response, e.getMessage(), false);
			}
		}
		setContentType(response, ContentType.Json);
		try {
			generator.writeEndArray();// ]
			generator.flush();
			out.flush();
			String result = new String(out.toByteArray(), "UTF-8");
			out.close();
			generator.close();
			System.out.println(result);
			writeMessage(response, result, true);
		} catch (IOException e) {
			writeMessage(response, e.getMessage(), false);
		}
	}

	private static void executeConfig(JsonNode configNode, String verifyConfigNodeName, JsonGenerator generator, String apiResponseData) throws Exception {
		IComparer compare = null;
		String returnTypeNodeName = "returnType";
		String returnResultNodeName = "returnResult";
		JsonNode verifyConfigNode = configNode != null && configNode.has(verifyConfigNodeName) ? configNode.get(verifyConfigNodeName) : null;
		String expectReturnTypeNode = configNode != null && configNode.has(returnTypeNodeName) ? configNode.get(returnTypeNodeName).textValue() : "JSON";
		JsonNode returnResultNodeList = verifyConfigNode != null && verifyConfigNode.has(returnResultNodeName) ? verifyConfigNode.get(returnResultNodeName) : null;

		int resultCount = returnResultNodeList != null && returnResultNodeList.isArray() ? returnResultNodeList.size() : 0;
		generator.writeArrayFieldStart(verifyConfigNodeName);// verifyConfigNodeName:[
		for (int i = 0; i < resultCount; i++) {
			JsonNode returnResultNode = returnResultNodeList.get(i);
			String expectReturnResult = returnResultNode != null && returnResultNode.has("text") ? returnResultNode.get("text").textValue() : "";

			String realReturnResult = "";

			switch (verifyConfigNodeName) {
			case "returnConfig":
				realReturnResult = apiResponseData;
				break;
			case "databaseConfig":
				JsonNode serverNode = returnResultNode.get("server");
				String ip = serverNode.get("ip").textValue();
				String uid = serverNode.get("uid").textValue();
				String pwd = serverNode.get("pwd").textValue();
				String port = serverNode.get("port").textValue();
				String type = serverNode.get("type").textValue();
				String name = serverNode.get("name").textValue();

				List<VerifyDatabaseConfig> vdbList = DataFactory.getVerifyDatabaseConfigs();
				VerifyDatabaseConfig server = null;
				for (VerifyDatabaseConfig config : vdbList) {
					if (config.getName().equalsIgnoreCase(name)) {
						server = config;
						break;
					}
				}
				if (server == null) {
					server = new VerifyDatabaseConfig();
					server.setIp(ip);
					server.setName(name);
					server.setPort(port);
					server.setPwd(pwd);
					server.setType(type);
					server.setUid(uid);
				}
				String database = returnResultNode.get("database").textValue();
				String sql = returnResultNode.get("query").textValue();
				realReturnResult = MySqlHelper.executeQuery(server.getBaseConnectionString() + database, sql, server.getProperties());
				break;

			default:
				break;
			}

			if (expectReturnTypeNode.equalsIgnoreCase("JSON")) {
				compare = new JsonComparer();
			} else if (expectReturnTypeNode.equalsIgnoreCase("XML")) {
				compare = new XmlComparer();
			} else {
				compare = new TextComparer();
			}
			generator.writeStartObject();// {
			generator.writeObjectField("name", "比较结果 - " + (i + 1));
			generator.writeObjectField("source", expectReturnResult);// source:""
			generator.writeObjectField("target", realReturnResult);// target:""
			String compareResult = compare.compare(expectReturnResult, realReturnResult);
			generator.writeObjectField("compare", compareResult);// compare:""
			generator.writeEndObject();// }
		}
		generator.writeEndArray();// ]
	}

	// Path: /Refresh
	public static void Refresh(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestCasePage.Refresh()");
		try {
			String testSystemId = getString(request, "testSystemId");
			int rid = Integer.parseInt(getString(request, "rid"));
			DEBUG("TestCasePage.Refresh(): Get Parameter [testSystemId:" + testSystemId + ",rid:" + rid + "]");
			List<TestCase> dataArray = DataFactory.getAllTestCase(rid, testSystemId);
			writeMessage(response, TestCase.toJson(dataArray), true);
		} catch (Exception e) {
			ERROR("TestCasePage.Refresh(): Get all test case failed, Exception Message: " + e.getMessage());
			writeMessage(response, "出现异常：" + e.getMessage(), false);
		}
	}
}
