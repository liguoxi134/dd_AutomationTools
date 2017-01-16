package com.dangdang.tools.atf.entity;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "tbl_VerifyDatabase")
public class VerifyDatabaseConfig implements SoftDeleteObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(columnDefinition = "varchar(255)", nullable = false)
	private String name;
	@Column(columnDefinition = "varchar(255)", nullable = false)
	private String ip;
	@Column(columnDefinition = "varchar(255)")
	private String uid;
	@Column(columnDefinition = "varchar(255)")
	private String pwd;
	@Column(columnDefinition = "varchar(255)")
	private String port;
	@Column(columnDefinition = "varchar(1024)")
	private String more;
	@Column(columnDefinition = "varchar(255)", nullable = false)
	private String type;
	@JsonIgnore
	private boolean state;

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

	@JsonIgnore
	public String getBaseConnectionString() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append("jdbc:").append(this.type.toLowerCase()).append("://").append(this.ip).append(this.port != null && !this.port.isEmpty() ? ":" + this.port : "").append("/");
		return sb.toString();
	}

	@JsonIgnore
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setState(boolean value) {
		this.state = value;
	}

	@Override
	public boolean isState() {
		return this.state;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof VerifyDatabaseConfig) {
			VerifyDatabaseConfig another = (VerifyDatabaseConfig) o;
			return Collator.getInstance(Locale.CHINESE).compare(this.name, another.name);
		} else {
			return -1;
		}
	}
}
