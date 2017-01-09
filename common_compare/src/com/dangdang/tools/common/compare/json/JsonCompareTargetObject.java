package com.dangdang.tools.common.compare.json;

import java.util.ArrayList;
import java.util.List;

import com.dangdang.tools.common.compare.CompareState;
import com.dangdang.tools.common.compare.ICompareTargetObject;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonCompareTargetObject extends JsonCompareObject implements ICompareTargetObject<JsonCompareItem> {

	private List<JsonCompareItem> mapper = new ArrayList<JsonCompareItem>();

	public JsonCompareTargetObject(JsonNode node) {
		super.calcAll(node, "", "");
	}

	public List<JsonCompareItem> match(JsonCompareItem sourceItem) {
		List<JsonCompareItem> matchList = new ArrayList<JsonCompareItem>();
		for (JsonCompareItem item : this.mapper) {
			if (item.getState() == CompareState.Ready && item.getToken().endsWith(sourceItem.getToken())) {
				matchList.add(item);
				item.setState(CompareState.Match);
			}
		}
		return matchList;
	}

	@Override
	void addItem(JsonNode root, String reversePath, int group) {
		if (this.mapper == null) {
			this.mapper = new ArrayList<JsonCompareItem>();
		}
		JsonCompareItem item = new JsonCompareItem();
		item.setNode(root);
		item.setToken(reversePath);
		item.setType(root.getNodeType());
		item.setGroup(group);
		this.mapper.add(item);
	}

}
