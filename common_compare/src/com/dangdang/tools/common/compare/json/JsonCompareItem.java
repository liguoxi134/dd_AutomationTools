package com.dangdang.tools.common.compare.json;

import com.dangdang.tools.common.compare.CompareState;
import com.dangdang.tools.common.compare.ICompareItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class JsonCompareItem implements ICompareItem {

	private String token;
	private JsonNodeType type;
	private JsonNode node;
	private CompareState state = CompareState.Ready;
	private String realToken;
	private JsonNodeType realType;
	private JsonNode realNode;
	private int group;
	private int realGroup;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JsonNodeType getType() {
		return type;
	}

	public void setType(JsonNodeType type) {
		this.type = type;
	}

	public JsonNode getNode() {
		return node;
	}

	public CompareState getState() {
		return state;
	}

	public void setState(CompareState state) {
		this.state = state;
	}

	public void setNode(JsonNode node) {
		this.node = node;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public JsonNode getRealNode() {
		return realNode;
	}

	public void setRealNode(JsonNode realNode) {
		this.realNode = realNode;
	}

	public String getRealToken() {
		return realToken;
	}

	public void setRealToken(String realToken) {
		this.realToken = realToken;
	}

	public JsonNodeType getRealType() {
		return realType;
	}

	public void setRealType(JsonNodeType realType) {
		this.realType = realType;
	}

	

	public int getRealGroup() {
		return realGroup;
	}

	public void setRealGroup(int realGroup) {
		this.realGroup = realGroup;
	}

	@Override
	public String toString() {
		return "&=" + token + "/" + type + "@=" + node + "!=" + state;
	}

}
