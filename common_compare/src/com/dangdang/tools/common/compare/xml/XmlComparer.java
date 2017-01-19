package com.dangdang.tools.common.compare.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import com.dangdang.tools.common.compare.IComparer;
import com.dangdang.tools.common.compare.json.JsonComparer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class XmlComparer implements IComparer {

	@Override
	public String compare(String expect, String real) throws Exception {
		String _expect = convertToJsonString(expect);
		String _real = convertToJsonString(real);

		JsonComparer _comparer = new JsonComparer();
		String result = _comparer.compare(_expect, _real);

		return result;
	}

	private String convertToJsonString(String xml) throws Exception {
		ObjectMapper map = new ObjectMapper();
		Document document = DocumentHelper.parseText(xml);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonGenerator generator = map.getFactory().createGenerator(out);
		generator.writeStartObject();
		writeElement(document.getRootElement(), generator, true);
		generator.writeEndObject();
		generator.flush();
		out.flush();
		String value = new String(out.toByteArray());
		out.close();
		generator.close();
		return value;
	}

	private static void writeElement(Element root, JsonGenerator generator, boolean isWriteName) throws Exception {
		if (root != null) {
			if (isWriteName) {
				generator.writeObjectFieldStart(root.getName());
			} else {
				generator.writeStartObject();
			}
			writeAttributes(root, generator);
			if (root.hasContent()) {
				if (root.isTextOnly()) {
					generator.writeStringField("#text", root.getText());
				} else {
					writeSubElement(root, generator);
				}
			}
			generator.writeEndObject();
		}

	}

	private static void writeAttributes(Element root, JsonGenerator generator) throws IOException {
		if (root instanceof DefaultElement) {
			int attrCount = root.attributeCount();
			for (int i = 0; i < attrCount; i++) {
				Attribute attr = root.attribute(i);
				generator.writeObjectField("@" + attr.getName(), attr.getValue());
			}
		}
	}

	private static void writeSubElement(Element root, JsonGenerator generator) throws IOException, Exception {
		@SuppressWarnings("unchecked")
		List<Element> nodes = root.elements();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (Element el : nodes) {
			if (map.containsKey(el.getName())) {
				map.put(el.getName(), map.get(el.getName()) + 1);
			} else {
				map.put(el.getName(), 1);
			}
			// writeJson((Element.class.cast(el)), generator);
		}
		for (Entry<String, Integer> entry : map.entrySet()) {
			int count = entry.getValue();
			if (count > 1) {
				writeElementArray(root, generator, entry);
			} else {
				Element el = root.element(entry.getKey());
				if (el.hasContent()) {
					if (el.isTextOnly()) {
						generator.writeObjectField(el.getName(), el.getText());
					} else {
						writeElement(el, generator, true);
					}
				}
			}
		}
	}

	private static void writeElementArray(Element root, JsonGenerator generator, Entry<String, Integer> entry) throws IOException, Exception {
		@SuppressWarnings("unchecked")
		List<Element> els = root.elements(entry.getKey());
		generator.writeArrayFieldStart(entry.getKey());
		for (Element el : els) {
			writeAttributes(el, generator);
			if (el.hasContent()) {
				if (el.isTextOnly()) {
					generator.writeObject(el.getText());
				} else {
					writeElement(el, generator, false);
				}
			}
		}
		generator.writeEndArray();
	}
}
