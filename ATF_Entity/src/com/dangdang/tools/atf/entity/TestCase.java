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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "tbl_TestCase")
public class TestCase implements SoftDeleteObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(columnDefinition = "varchar(2048)", nullable = false)
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

	@JsonIgnore
	@Column(nullable = false)
	private int testInterfaceId;

	public TestCase() {
		this.lastModifyDateTime = new Date();
		this.lastModifyUser = "root";
		this.state = true;
	}

	public TestCase(String name) {
		this(name, name);
	}

	public TestCase(String name, String description) {
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

	public static String toJson(List<TestCase> list) {
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
		TestCase another = (TestCase) o;
		return Collator.getInstance(Locale.CHINESE).compare(this.name, another.name);
	}

	public int getTestInterfaceId() {
		return testInterfaceId;
	}

	public void setTestInterfaceId(int testInterfaceId) {
		this.testInterfaceId = testInterfaceId;
	}
}
