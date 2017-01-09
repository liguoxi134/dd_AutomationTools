package com.dangdang.tools.atf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dangdang.tools.atf.entityenum.RequestType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "tbl_TestConfig")
public class TestConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(columnDefinition = "tinyint", nullable = false)
	private RequestType requestType;

	@Column(columnDefinition = "text")
	private String requestHeaders;

	@Column(columnDefinition = "text")
	private String resultVerify;

	@Column(columnDefinition = "text")
	private String requestBody;

	@JsonIgnore
	@Column(columnDefinition = "varchar(255) default 'root'", nullable = false)
	private String lastModifyUser;

	@JsonIgnore
	@Column(columnDefinition = "timestamp", nullable = false)
	private Date lastModifyDateTime;

	@JsonIgnore
	@Column(nullable = false, unique = true)
	private int testCaseId;
	
	public TestConfig(RequestType requestType, String resultVerify) {
		this(requestType, "", resultVerify, "");
	}

	public TestConfig(RequestType requestType, String requestHeaders, String resultVerify, String requestBody) {
		this();
		this.requestType = requestType;
		this.requestHeaders = requestHeaders;
		this.resultVerify = resultVerify;
		this.requestBody = requestBody;
	}

	public TestConfig() {
		this.lastModifyDateTime = new Date();
		this.lastModifyUser = "root";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public String getResultVerify() {
		return resultVerify;
	}

	public void setResultVerify(String resultVerify) {
		this.resultVerify = resultVerify;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
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

	public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}

}
