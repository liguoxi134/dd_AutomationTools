package com.dangdang.tools.atf.entity;

import java.text.Collator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dangdang.tools.atf.entityenum.TestInterfaceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "tbl_TestInterface")
public class TestInterface implements SoftDeleteObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(columnDefinition = "tinyint", nullable = false)
	private TestInterfaceType type;

	@Column(columnDefinition = "varchar(2048)", nullable = false)
	private String url;

	@Column(columnDefinition = "varchar(255)", nullable = false)
	private String name;

	@Column(columnDefinition = "text", nullable = false)
	private String description;

	@JsonIgnore
	@Column(columnDefinition = "varchar(255) default 'root'", nullable = false)
	private String lastModifyUser;

	@JsonIgnore
	@Column(columnDefinition = "timestamp", nullable = false)
	private Date lastModifyDateTime;

	@JsonIgnore
	@Column(nullable = false, columnDefinition = "bit default 1")
	private boolean state;

	public TestInterface() {
		super();
	}

	public TestInterface(String url, String name) {
		this(url, name, name);
	}

	public TestInterface(String url, String name, String description) {
		this(TestInterfaceType.GET, url, name, description);
	}

	public TestInterface(TestInterfaceType type, String url, String name) {
		this(type, url, name, name);
	}

	public TestInterface(TestInterfaceType type, String url, String name, String description) {
		super();
		this.type = type;
		this.url = url;
		this.name = name;
		this.description = description;
		this.lastModifyDateTime = new Date();
		this.lastModifyUser = "root";
		this.state = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestInterfaceType getType() {
		return type;
	}

	public void setType(TestInterfaceType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}

	public Date getLastModifyDateTime() {
		return lastModifyDateTime;
	}

	public void setLastModifyDateTime(Date lastModifyDateTime) {
		this.lastModifyDateTime = lastModifyDateTime;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
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

	public static String toJson(List<TestInterface> list) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public int compareTo(Object o) {
		TestInterface another = (TestInterface) o;
		return Collator.getInstance(Locale.CHINESE).compare(this.name, another.name);
	}
}
