package com.dangdang.tools.atf.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entity.VerifyDatabaseConfig;
import com.dangdang.tools.atf.factory.DataFactory;

public class DatabaseServerPage extends BasePage {
	public static void Add(HttpServletRequest request, HttpServletResponse response) {
		try {
			String ip = getString(request, "ip");
			String more = getString(request, "more");
			String name = getString(request, "name");
			String port = getString(request, "port");
			String pwd = getString(request, "pwd");
			String type = getString(request, "type");
			String uid = getString(request, "uid");

			List<VerifyDatabaseConfig> exist = DataFactory.checkVerifyDatabase(ip, port, pwd, uid, type, name);

			if (!exist.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (VerifyDatabaseConfig config : exist) {
					buffer.append("已经存在重复的校验服务器，服务器名称为:" + config.getName() + "\r\n");
				}
				if (buffer.length() > 0) {
					writeMessage(response, buffer.replace(buffer.length() - "\r\n".length() - 1, buffer.length(), "").toString(), false);
					return;
				} else {
					writeMessage(response, "保存成功", true);
					return;
				}
			}

			VerifyDatabaseConfig tmp = new VerifyDatabaseConfig();
			tmp.setIp(ip);
			tmp.setMore(more);
			tmp.setPort(port);
			tmp.setName(name);
			tmp.setType(type);
			tmp.setUid(uid);
			tmp.setPwd(pwd);
			tmp.setState(true);
			DataFactory.updateVerifyDatabaseConfig(tmp);
			writeMessage(response, "保存成功", true);
		} catch (Exception e) {
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}

	public static void Edit(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = getInt(request, "id");
			String ip = getString(request, "ip");
			String more = getString(request, "more");
			String name = getString(request, "name");
			String port = getString(request, "port");
			String pwd = getString(request, "pwd");
			String type = getString(request, "type");
			String uid = getString(request, "uid");

			List<VerifyDatabaseConfig> exist = DataFactory.checkVerifyDatabase(ip, port, pwd, uid, type, name);

			if (!exist.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (VerifyDatabaseConfig config : exist) {
					if (config.getId() != id) {
						buffer.append("已经存在重复的校验服务器，服务器名称为:" + config.getName() + "\r\n");
					}
				}
				if (buffer.length() > 0) {
					writeMessage(response, buffer.replace(buffer.length() - "\r\n".length() - 1, buffer.length(), "").toString(), false);
					return;
				} else {
					writeMessage(response, "保存成功", true);
					return;
				}
			}

			VerifyDatabaseConfig tmp = new VerifyDatabaseConfig();
			tmp.setIp(ip);
			tmp.setMore(more);
			tmp.setPort(port);
			tmp.setName(name);
			tmp.setType(type);
			tmp.setUid(uid);
			tmp.setPwd(pwd);

			tmp.setId(id);

			DataFactory.updateVerifyDatabaseConfig(tmp);
			writeMessage(response, "保存成功", true);
		} catch (Exception e) {
			writeMessage(response, "保存失败：" + e.getMessage(), false);
		}
	}

	public static void Copy(HttpServletRequest request, HttpServletResponse response) {
		try {
			int[] ids = getInts(request, "ids[]");
			DataFactory.copyVerifyDatabaseConfig(ids);
			writeMessage(response, "克隆成功", true);
		} catch (Exception e) {
			writeMessage(response, "克隆失败：" + e.getMessage(), false);
		}
	}

	// Path: /Delete
	public static void Delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			int rid = getInt(request, "rid");
			DataFactory.deleteVerifyDatabaseConfig(rid);
			writeMessage(response, "删除成功", true);
		} catch (Exception e) {
			writeMessage(response, "删除失败：" + e.getMessage(), false);
		}
	}

	public static void List(HttpServletRequest request, HttpServletResponse response) {
		List<VerifyDatabaseConfig> vbds = DataFactory.getVerifyDatabaseConfigs();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vdbs", vbds);
		map.put("code", true);
		writeMessage(response, map);
	}

}
