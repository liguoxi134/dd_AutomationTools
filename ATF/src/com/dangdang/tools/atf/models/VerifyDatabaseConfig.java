package com.dangdang.tools.atf.models;

import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VerifyDatabaseConfig {
	private String name;
	private String ip;
	private String uid;
	private String pwd;
	private String port;
	private String more;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBaseConnectionString() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append("jdbc:").append(this.type.toLowerCase()).append("://").append(this.ip).append(this.port != null && !this.port.isEmpty() ? ":" + this.port : "").append("/");
		return sb.toString();
	}

	public Properties getProperties() {
		Properties info = new Properties();
		info.put("user", this.uid);
		info.put("password", this.pwd);
		if (this.more != null && !this.more.isEmpty()) {
			String[] kvpList = more.split("&");
			for (String kvp : kvpList) {
				String[] kv = kvp.split("=", 2);
				info.put(kv[0], kv.length == 2 ? kv[1] : "");
			}
		}
		return info;
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static String toJson(List<VerifyDatabaseConfig> list) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}
}
