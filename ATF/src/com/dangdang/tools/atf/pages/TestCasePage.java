package com.dangdang.tools.atf.pages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.entity.TestCase;
import com.dangdang.tools.atf.entity.TestConfig;
import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entityenum.TestInterfaceType;
import com.dangdang.tools.atf.factory.DataFactory;
import com.dangdang.tools.atf.helper.HttpHelper;
import com.dangdang.tools.common.compare.IComparer;
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
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonGenerator generator = null;
		try {
			DEBUG("TestCasePage.Execute()： Create JSON Generator, Encode:" + JsonEncoding.UTF8);
			generator = mapper.getFactory().createGenerator(out, JsonEncoding.UTF8);
			generator.writeStartArray();
		} catch (IOException ex) {
			ERROR("TestCasePage.Execute(): Cannot create JSON Generator, Exception Message: " + ex.getMessage());
			writeMessage(response, "出现异常：" + ex.getMessage(), false);
		}
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
		IComparer compare = null;
		Map<String, Object> map = new HashMap<String, Object>();
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
				e.printStackTrace();
			}
			List<String> headerList = new ArrayList<String>();
			if (headerNodes != null && headerNodes.isArray()) {
				for (JsonNode node : headerNodes) {
					headerList.add(node.textValue());
				}
			}
			String real = "";
			if (testInterface.getType() == TestInterfaceType.POST) {
				real = HttpHelper.doPost(testInterface.getUrl(), testConfig.getRequestBody(), headerList);
			} else if (testInterface.getType() == TestInterfaceType.GET) {
				real = HttpHelper.sendGet(testInterface.getUrl(), testConfig.getRequestBody(), headerList);
			}
			JsonNode configNode = null;
			try {
				configNode = mapper.readTree(testConfig.getResultVerify());
			} catch (Exception e) {
				e.printStackTrace();
			}
			configNode.findPath("");
		}
	}
	//
	// if (testInterface.getType() == TestInterfaceType.POST) {
	// DEBUG("TestCasePage.Execute()： test interface type: " +
	// testInterface.getType());
	// DEBUG("TestCasePage.Execute()： Parameter type: " + testConfig.getType());
	// try {
	// DEBUG("TestCasePage.Execute()： Start send post request...");
	// String real = HttpHelper.doPost(testInterface.getUrl(),
	// testConfig.getValue(), testConfig.getType());
	// String expect = "";
	// if (expectResult.getVerifyType() == ExpectResultVerifyType.RETURN) {
	// DEBUG("TestCasePage.Execute()： Expect result verify type: " +
	// expectResult.getVerifyType());
	// expect = expectResult.getValue();
	// } else {
	// ERROR("TestCasePage.Execute(): Expect result verify type error:
	// 仅支持URL返回结果的用例");
	// throw new Exception("仅支持URL返回结果的用例");
	// }
	// if (expectResult.getType() == ExpectResultType.JSON) {
	// DEBUG("TestCasePage.Execute()： Expect result type: " +
	// expectResult.getType());
	// compare = new JsonComparer();
	// String result = compare.compare(expect, real);
	// map.put("testcase", testCase);
	// map.put("testinterface", testInterface);
	// map.put("source", expect);
	// map.put("target", real);
	// map.put("compare", result);
	// generator.writeObject(map);
	// } else {
	// ERROR("TestCasePage.Execute(): Expect result type error:
	// 仅支持比较预期结果类型是JSON格式的用例");
	// throw new Exception("仅支持比较预期结果类型是JSON格式的用例");
	// }
	// } catch (Exception e) {
	// ERROR("TestCasePage.Execute(): Execute test case failed: " +
	// testCase.toJson() + "\r\nException Message: " + e.getMessage());
	// try {
	// map.put("testcase", testCase);
	// map.put("testinterface", testInterface);
	// map.put("source", null);
	// map.put("target", null);
	// map.put("compare", e.getMessage());
	// generator.writeObject(map);
	// } catch (Exception ex) {
	// ERROR("TestCasePage.Execute(): Package return map failed, Exception
	// Message: " + ex.getMessage());
	// }
	// }
	// } else if (testInterface.getType() == TestInterfaceType.GET) {
	// DEBUG("TestCasePage.Execute()： Parameter type: " + testConfig.getType());
	// try {
	// DEBUG("TestCasePage.Execute()： Start send get request...");
	// String real = HttpHelper.sendGet(testInterface.getUrl(),
	// testConfig.getValue());
	// String expect = "";
	// if (expectResult.getVerifyType() == ExpectResultVerifyType.RETURN) {
	// DEBUG("TestCasePage.Execute()： Expect result verify type: " +
	// expectResult.getVerifyType());
	// expect = expectResult.getValue();
	// } else {
	// ERROR("TestCasePage.Execute(): Expect result verify type error:
	// 仅支持URL返回结果的用例");
	// throw new Exception("仅支持URL返回结果的用例");
	// }
	// if (expectResult.getType() == ExpectResultType.JSON) {
	// DEBUG("TestCasePage.Execute()： Expect result type: " +
	// expectResult.getType());
	// compare = new JsonComparer();
	// map.put("testcase", testCase);
	// map.put("testinterface", testInterface);
	// map.put("source", expect);
	// map.put("target", real);
	// String result = compare.compare(expect, real);
	// map.put("compare", result);
	// generator.writeObject(map);
	// } else {
	// ERROR("TestCasePage.Execute(): Expect result type error:
	// 仅支持比较预期结果类型是JSON格式的用例");
	// throw new Exception("仅支持比较预期结果类型是JSON格式的用例");
	// }
	// } catch (Exception e) {
	// ERROR("TestCasePage.Execute(): Execute test case failed: " +
	// testCase.toJson() + "\r\nException Message: " + e.getMessage());
	// try {
	// if (!map.containsKey("testcase")) {
	// map.put("testcase", testCase);
	// }
	// if (!map.containsKey("testinterface")) {
	// map.put("testinterface", testInterface);
	// }
	// if (!map.containsKey("source")) {
	// map.put("source", null);
	// }
	// if (!map.containsKey("target")) {
	// map.put("target", null);
	// }
	// if (!map.containsKey("compare")) {
	// map.put("compare", e.getMessage());
	// }
	// generator.writeObject(map);
	// } catch (Exception ex) {
	// ERROR("TestCasePage.Execute(): Package return map failed, Exception
	// Message: " + ex.getMessage());
	// writeMessage(response, "出现异常：" + e.getMessage(), false);
	// }
	// }
	// }
	// }
	// try {
	// generator.writeEndArray();
	// generator.flush();
	// byte bs[] = out.toByteArray();
	// generator.close();
	// out.close();
	// writeMessage(response, new String(bs, "UTF-8"), true);
	// } catch (Exception e) {
	// ERROR("TestCasePage.Execute(): Generate JSON String failed, Exception
	// Message: " + e.getMessage());
	// writeMessage(response, "出现异常：" + e.getMessage(), false);
	// }
	// }

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
