package com.dangdang.tools.common.compare.json;

import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class JsonCompareObject {

	private int group = 0;

	void calcAll(JsonNode root, String pref, String key) {
		if (root.isContainerNode()) {
			if (root.isArray()) {
				// array has more nodes
				pref += key + "*";
				if (root.size() > 0) {
					for (JsonNode _sub : root) {
						group++;
						calcAll(_sub, pref, "");
					}
				} else {
					addItem(root, pref, group);
				}
				return;
			} else if (root.isObject()) {
				// object not null
				pref += key + "$";
				Iterator<Entry<String, JsonNode>> fields = root.fields();
				if (root.size() > 0) {
					group++;
					while (fields.hasNext()) {
						Entry<String, JsonNode> entry = fields.next();
						calcAll(entry.getValue(), pref, entry.getKey());
					}
				} else {
					addItem(root, pref, group);
				}
				return;
			}
		} else {
			// null,number,string
			String path = pref + key;
			addItem(root, path, group);
			return;
		}
	}

	abstract void addItem(JsonNode root, String reversePath, int group);
}
