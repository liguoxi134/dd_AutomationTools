package com.dangdang.tools.common.compare.json;

import com.dangdang.tools.common.compare.IComparer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonComparer implements IComparer {
	@Override
	public String compare(String expect, String real) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (expect == null || expect.trim().isEmpty()) {
			throw new Exception("Ԥ�ڽ��Ϊ�գ��Ա�û���κ�����");
//			expect = "{\"code\":false,\"message\":\"Ԥ�ڽ��Ϊ��,�޷����бȽ�\"}";
		}
		if (real == null || real.trim().isEmpty()) {
			real="{}";
//			real = "{\"code\":false,\"message\":\"ʵ�ʽ��Ϊ��,�޷����бȽ�\"}";
		}
		JsonNode expectNode = mapper.readTree(expect);
		JsonNode realNode = mapper.readTree(real);

		JsonCompareSourceObject expectCompareObject = new JsonCompareSourceObject(expectNode);
		JsonCompareTargetObject realCompareObject = new JsonCompareTargetObject(realNode);
		String returnVal = expectCompareObject.CompareWithTarget(realCompareObject);
		return returnVal;
	}
}
