package com.dangdang.tools.atf.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultCDATA;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test {

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) throws Exception {
		ObjectMapper map = new ObjectMapper();
		Document document = getDocument();
		OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
		xmlFormat.setEncoding("UTF-8");
		xmlFormat.setNewlines(true);
		xmlFormat.setIndent(true);
		xmlFormat.setIndent("    ");
		Writer writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, xmlFormat);
		xmlWriter.write(document);
		String result = writer.toString();
		xmlWriter.close();
		writer.close();
		System.out.println(result);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonGenerator generator = map.getFactory().createGenerator(out);
		List<Element> nodes = document.content();
		generator.writeStartObject();
		writeElement(document.getRootElement(), generator, true);
		generator.writeEndObject();
		generator.flush();
		out.flush();

		String value = new String(out.toByteArray());

		System.out.println(value);
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

	private static Document getDocument() {
		Element root = DocumentHelper.createElement("Person");
		Document document = DocumentHelper.createDocument(root);
		// 给根节点添加属性
		root.addAttribute("学校", "南大").addAttribute("位置", "江西");

		// 给根节点添加孩子节点
		Element element1 = root.addElement("学生");
		element1.addText("21");

		Element element2 = root.addElement("学生");
		element2.addText("212");
		Element element3 = root.addElement("老师");
		element3.addCDATA("<a>terst</a>");
		element3.addComment("sadfsgfs");
		return document;
	}

}
