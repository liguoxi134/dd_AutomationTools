package com.dangdang.tools.common.compare.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dangdang.tools.common.compare.CompareState;
import com.dangdang.tools.common.compare.ICompareSourceObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCompareSourceObject extends JsonCompareObject implements ICompareSourceObject<JsonCompareTargetObject> {

	private Map<Integer, List<JsonCompareItem>> mapper = null;
	private List<JsonCompareItem> srcMap = new ArrayList<JsonCompareItem>();

	@Override
	public String CompareWithTarget(JsonCompareTargetObject target) {
		List<JsonCompareItem> allMatches = new ArrayList<JsonCompareItem>();
		for (Entry<Integer, List<JsonCompareItem>> entry : this.mapper.entrySet()) {
			List<JsonCompareItem> items = entry.getValue();
			for (JsonCompareItem item : items) {
				List<JsonCompareItem> matchList = target.match(item);
				allMatches.addAll(matchList);
			}
		}

		Map<Integer, List<JsonCompareItem>> groupedMatches = new HashMap<Integer, List<JsonCompareItem>>();
		for (JsonCompareItem item : allMatches) {
			if (!groupedMatches.containsKey(item.getGroup())) {
				groupedMatches.put(item.getGroup(), new ArrayList<JsonCompareItem>());
			}
			groupedMatches.get(item.getGroup()).add(item);
		}

		return compareWithTarget(groupedMatches);
	}

	public JsonCompareSourceObject(JsonNode node) {
		super.calcAll(node, "", "");
	}

	@Override
	void addItem(JsonNode root, String reversePath, int group) {
		if (this.mapper == null) {
			this.mapper = new HashMap<Integer, List<JsonCompareItem>>();
		}
		JsonCompareItem item = new JsonCompareItem();
		item.setNode(root);
		item.setToken(reversePath);
		item.setType(root.getNodeType());
		item.setGroup(group);
		if (!this.mapper.containsKey(group)) {
			this.mapper.put(group, new ArrayList<JsonCompareItem>());
		}
		this.mapper.get(group).add(item);
		this.srcMap.add(item);
	}

	private double compare(List<JsonCompareItem> src, List<JsonCompareItem> tgr) {
		double count = 0;
		for (JsonCompareItem _src : src) {
			for (JsonCompareItem _tgr : tgr) {
				if (_tgr.getToken().endsWith(_src.getToken())) {
					count += 0.5;
					String str_tgr = _tgr.getNode().toString();
					String str_src = _src.getNode().toString();
					if (_tgr.getNode().isNumber()) {
						str_tgr = String.valueOf(_tgr.getNode().asDouble());
					}
					if (_src.getNode().isNumber()) {
						str_src = String.valueOf(_src.getNode().asDouble());
					}
					if (str_tgr.equals(str_src)) {
						count += 0.5;
					}
					break;
				}
			}
		}
		if (count == src.size()) {
			return 1.0d;
		}
		return count / src.size();
	}

	private void mark(List<JsonCompareItem> src, List<JsonCompareItem> tgr) {
		for (int srcIndex = 0; srcIndex < src.size(); srcIndex++) {
			JsonCompareItem _src = src.get(srcIndex);
			_src.setState(CompareState.Miss);
			for (int tgrIndex = 0; tgrIndex < tgr.size(); tgrIndex++) {
				JsonCompareItem _tgr = tgr.get(tgrIndex);
				if (_tgr.getToken().endsWith(_src.getToken())) {
					_src.setRealNode(_tgr.getNode());
					_src.setRealToken(_tgr.getToken());
					_src.setRealType(_tgr.getType());
					_src.setRealGroup(_tgr.getGroup());

					String str_tgr = _tgr.getNode().toString();
					String str_src = _src.getNode().toString();
					if (_tgr.getNode().isNumber()) {
						str_tgr = String.valueOf(_tgr.getNode().asDouble());
					}
					if (_src.getNode().isNumber()) {
						str_src = String.valueOf(_src.getNode().asDouble());
					}

					if (str_tgr.equals(str_src)) {
						_src.setState(CompareState.Success);
						_tgr.setState(CompareState.Success);
					} else {
						_src.setState(CompareState.Fail);
						_tgr.setState(CompareState.Fail);
					}
				}
			}
		}
	}

	private String compareWithTarget(Map<Integer, List<JsonCompareItem>> target) {
		for (Entry<Integer, List<JsonCompareItem>> entrySource : this.mapper.entrySet()) {
			List<JsonCompareItem> src = entrySource.getValue();
			int maxMatchGroup = 0;
			double maxMatchPercent = 0d;
			for (Entry<Integer, List<JsonCompareItem>> entryTarget : target.entrySet()) {
				List<JsonCompareItem> tgr = entryTarget.getValue();
				double matachPercent = compare(src, tgr);
				if (matachPercent > maxMatchPercent) {
					maxMatchGroup = entryTarget.getKey();
					maxMatchPercent = matachPercent;
					if (maxMatchPercent >= 1.0d) {
						break;
					}
				}
			}
			List<JsonCompareItem> tgr = new ArrayList<JsonCompareItem>();
			if (maxMatchGroup > 0) {
				tgr = target.get(maxMatchGroup);
				target.remove(maxMatchGroup);
			}
			mark(src, tgr);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.srcMap);
			return result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String content = "{\"id\":[],\"name\":[1,2,3],\"description\":\"drf\"}";
		JsonNode node = mapper.readTree(content);
		JsonCompareSourceObject compareItem = new JsonCompareSourceObject(node);
	}
}
