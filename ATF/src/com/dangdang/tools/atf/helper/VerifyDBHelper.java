package com.dangdang.tools.atf.helper;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dangdang.tools.atf.models.LoggerObject;
import com.dangdang.tools.atf.models.VerifyDatabaseConfig;

public class VerifyDBHelper extends LoggerObject {
	private static List<VerifyDatabaseConfig> dbList = null;

	public static void reset() {
		DEBUG("Setting ATF configuration base path: " + System.getProperty("user.dir") + "/ATF_v2/");
		System.setProperty("atf.config.dir", System.getProperty("user.dir") + "/ATF_v2/");
		dbList = new ArrayList<VerifyDatabaseConfig>();
		SAXReader saxReader = new SAXReader();
		Document document = null;
		String path = System.getProperty("atf.config.dir") + "verifyDBServer.xml";
		try {
			document = saxReader.read(path);
			@SuppressWarnings("unchecked")
			List<Element> servers = document.selectNodes("//server");
			for (Element server : servers) {
				VerifyDatabaseConfig config = new VerifyDatabaseConfig();
				config.setIp(server.attributeValue("ip"));
				config.setName(server.attributeValue("name"));
				config.setPort(server.attributeValue("port"));
				config.setUid(server.attributeValue("uid"));
				config.setPwd(server.attributeValue("pwd"));
				config.setType(server.attributeValue("type"));
				config.setMore(server.attributeValue("more"));
				dbList.add(config);
			}
		} catch (Exception e) {
			ERROR("Load verify database exception: " + e.getMessage());
			dbList = null;
		}
	}

	public static List<VerifyDatabaseConfig> getList() {
		if (dbList == null) {
			reset();
		}
		return dbList;
	}
}
