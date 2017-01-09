package com.dangdang.tools.atf.pages;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entityenum.TestInterfaceType;
import com.dangdang.tools.atf.factory.DataFactory;

public class TestInterfacePage extends BasePage {

	// Path: /Delete
	public static void Delete(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestInterfacePage.Delete()");
		try {
			int[] ids = getInts(request, "ids[]");
			String testSystemId = getString(request, "testSystemId");
			DEBUG("TestInterfacePage.Delete()： Get Parameter [testSystemId:" + testSystemId + ",delete ids:" + Arrays.toString(ids) + "]");
			for (int i = 0; i < ids.length; i++) {
				DataFactory.deleteTestInterface(ids[i], testSystemId);
			}
			writeMessage(response, "删除成功", true);
		} catch (Exception e) {
			ERROR("TestInterfacePage.Delete()： Delete case failed: " + e.getMessage());
			writeMessage(response, "删除失败：" + e.getMessage(), false);
		}
	}

	// Path: /Refresh
	public static void Refresh(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestInterfacePage.Refresh()");
		try {
			String testSystemId = getString(request, "testSystemId");
			DEBUG("TestInterfacePage.Refresh()： Get Parameter [testSystemId:" + testSystemId + "]");
			List<TestInterface> dataArray = DataFactory.getAllTestInterface(testSystemId);
			writeMessage(response, TestInterface.toJson(dataArray), true);
		} catch (Exception e) {
			ERROR("TestInterfacePage.Refresh()： Get all test interface failed, Exception Message: " + e.getMessage());
			writeMessage(response, "出现异常：" + e.getMessage(), false);
		}
	}

	// Path: /Add
	public static void Add(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestInterfacePage.Add()");
		try {
			String testSystemId = getString(request, "testSystemId");
			String name = getString(request, "name");
			TestInterfaceType type = getEnum(request, "type", TestInterfaceType.class, TestInterfaceType.GET);
			String url = getString(request, "url");
			String description = getString(request, "description");

			boolean isExist = DataFactory.checkTestInterface(url, name, testSystemId);
			if (isExist) {
				throw new Exception("测试接口的名称或者URL已经存在，请不要重复添加!!");
			}
			DEBUG("TestInterfacePage.Add()：Get Parameter [testSystemId:" + testSystemId + ",name:" + name + ",type:" + type + ",url:" + url + ",description:" + description + "]");
			TestInterface tmp = new TestInterface(type, url, name, description);
			DEBUG("TestInterfacePage.Add()：Package new test interface: " + tmp.toJson());
			DataFactory.updateTestInterface(tmp, testSystemId);
			DEBUG("TestInterfacePage.Add()：Save new test interface: " + tmp.toJson());
			writeMessage(response, "保存成功", true);
		} catch (Exception e) {
			ERROR("TestInterfacePage.Add()：Add new test interface failed: " + e.getMessage());
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}

	// Path: /Edit
	public static void Edit(HttpServletRequest request, HttpServletResponse response) {
		DEBUG("TestInterfacePage.Edit()");
		try {
			int id = getInt(request, "id");
			String testSystemId = getString(request, "testSystemId");
			String name = getString(request, "name");
			TestInterfaceType type = getEnum(request, "type", TestInterfaceType.class, TestInterfaceType.GET);
			String url = getString(request, "url");
			String description = getString(request, "description");
			DEBUG("TestInterfacePage.Edit(): Get Parameter [testSystemId:" + testSystemId + ",id:" + id + ",name:" + name + ",type:" + type + ",url:" + url + ",description:" + description + "]");
			TestInterface tmp = new TestInterface(type, url, name, description);
			tmp.setId(id);
			DEBUG("TestInterfacePage.Edit(): Package edit test interface: " + tmp.toJson());
			DataFactory.updateTestInterface(tmp, testSystemId);
			DEBUG("TestInterfacePage.Edit(): Update edit test interface: " + tmp.toJson());
			writeMessage(response, "保存成功", true);
		} catch (Exception e) {
			ERROR("TestInterfacePage.Edit(): Edit test interface failed: " + e.getMessage());
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}
}
