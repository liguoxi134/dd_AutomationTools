package com.dangdang.tools.common.format.json;

import com.dangdang.tools.common.format.IFormater;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormater implements IFormater {

	@Override
	public String format(String text) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object json = mapper.readValue(text, Object.class);
		String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		return result;
	}
}
