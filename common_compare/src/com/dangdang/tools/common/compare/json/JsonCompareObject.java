package com.dangdang.tools.common.compare.json;

import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class JsonCompareObject {

	private int group = 0;

	void calcAll(JsonNode root, String pref, String key) {
		if (root.isArray() && root.isContainerNode()) {
			// array has more nodes
			pref += key + "*";
			for (JsonNode _sub : root) {
				group++;
				calcAll(_sub, pref, "");
			}
			return;
		} else if (root.isObject() && root.isContainerNode()) {
			// object not null
			pref += key + "$";
			Iterator<Entry<String, JsonNode>> fields = root.fields();
			group++;
			while (fields.hasNext()) {
				Entry<String, JsonNode> entry = fields.next();
				calcAll(entry.getValue(), pref, entry.getKey());
			}
			return;
		} else {
			// null,number,string
			String path = pref + key;
			addItem(root, path, group);
			return;
		}
	}

	abstract void addItem(JsonNode root, String reversePath, int group);
}
