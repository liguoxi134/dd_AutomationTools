package com.dangdang.tools.common.format.xml;

import java.io.StringWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dangdang.tools.common.format.IFormater;

public class XmlFormater implements IFormater {

	@Override
	public String format(String text) throws Exception {
		System.out.println("XmlFormater.format()");
		Document document = DocumentHelper.parseText(text);
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
		return result;
	}
}
