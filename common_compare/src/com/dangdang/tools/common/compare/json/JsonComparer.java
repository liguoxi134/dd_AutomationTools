package com.dangdang.tools.common.compare.json;

import com.dangdang.tools.common.compare.IComparer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonComparer implements IComparer {
	@Override
	public String compare(String expect, String real) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (expect == null || expect.trim().isEmpty()) {
			expect = "{\"code\":false,\"message\":\"预期结果为空,无法进行比较\"}";
		}
		if (real == null || real.trim().isEmpty()) {
			real = "{\"code\":false,\"message\":\"实际结果为空,无法进行比较\"}";
		}
		JsonNode expectNode = mapper.readTree(expect);
		JsonNode realNode = mapper.readTree(real);

		JsonCompareSourceObject expectCompareObject = new JsonCompareSourceObject(expectNode);
		JsonCompareTargetObject realCompareObject = new JsonCompareTargetObject(realNode);
		String returnVal = expectCompareObject.CompareWithTarget(realCompareObject);
		return returnVal;
	}
}
