package com.dangdang.tools.atf.helper;

import java.util.*;
import java.io.*;

import org.dom4j.*;
import org.dom4j.io.*;

import com.dangdang.tools.atf.models.LoggerObject;

public class XmlHelper extends LoggerObject {

	private Document document;

	private static XmlHelper xmlHelper;

	public static XmlHelper getXmlHelper() {
		if (xmlHelper == null) {
			xmlHelper = new XmlHelper();
		}
		return xmlHelper;
	}

	private XmlHelper() {
		// String path = "D:\\atf_db.xml";
		String path = "./atf_db.xml";
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(path);
		} catch (DocumentException e) {
			document = initConfig();
		}
	}

	/**
	 * 根据根结点
	 * 
	 * @param id
	 * @return
	 */
	public Element getRootElement() {
		Element root = document.getRootElement();
		return root;
	}

	/**
	 * 根据节点id属性值获取节点 Returns the element of the given ID attribute value. If
	 * this tree is capable of understanding which attribute value should be
	 * used for the ID then it should be used, otherwise this method should
	 * return null.
	 * 
	 * @param id
	 * @return
	 */
	public Element getElementById(String id) {
		// xml文件中的id属性名称必须为大写 ID，否则不能用此方法
		return document.elementByID(id);
	}

	/**
	 * 根据节点id属性值获取节点
	 * 
	 * @param id
	 * @return
	 */
	public Element getElementById(UUID id) {
		return document.elementByID(id.toString());
	}

	/**
	 * 根据节点名称获取节点
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getElementsByTagName(String tagName) {
		return document.selectNodes("//" + tagName);
	}

	/**
	 * 根据父节点获取所有下一层子结点
	 * 
	 * @param id
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Element> getChidrenByParentId(String id) {
		Element ele = document.elementByID(id);
		if (ele == null)
			return new ArrayList<Element>();
		return ele.elements();
	}

	/**
	 * 删除指定结点
	 * 
	 * @param id
	 * @return
	 */
	public synchronized boolean deleteNodeById(UUID id) {
		Element ele = getElementById(id);
		if (ele != null) {
			boolean hasRemove = ele.getParent().remove(ele);
			return hasRemove;
		} else {
			return false;
		}

	}

	/**
	 * 修改给定结点的指定属性的值
	 * 
	 * @param id
	 * @return
	 */

	public synchronized boolean updateNodeAttribute(Element updateNode, String attributeName, String attributeValue) {
		if (updateNode != null) {
			updateNode.addAttribute(attributeName, attributeValue);
			return updateNode.attributeValue(attributeName).equals(attributeValue);
		} else {
			return false;
		}
	}

	/**
	 * 修改给定结点的text值
	 * 
	 * @param id
	 * @return
	 */
	public boolean updateNodeValue(Element updateNode, String valueOfCDATA) {
		if (updateNode != null) {
			updateNode.clearContent();
			updateNode.addCDATA(valueOfCDATA);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 在指定的id的父节点后增加给定的子节点
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public boolean insertNode(Element insertNode, String parentId) {
		if (parentId != null) {
			Element parentElement = getElementById(parentId);
			List<Element> contents = parentElement.content();
			contents.add(0, insertNode);
			parentElement.setContent(contents);
			return true;
		}

		// 如果为空，在根结点下插入该结点
		else {
			Element root = getRootElement();
			root.content().add(0, insertNode);
			return true;
		}
	}

	private Document initConfig() {
		Element root = DocumentHelper.createElement("config");
		Document doc = DocumentHelper.createDocument(root);
		try {
			writeFile(doc);
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private void writeFile(Document doc) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		// XMLWriter writer = new XMLWriter(new
		// FileOutputStream("D:\\atf_db.xml"), format);
		XMLWriter writer = new XMLWriter(new FileOutputStream("./atf_db.xml"), format);
		writer.write(doc);
		writer.close();
	}

	public void saveChanges() {
		try {
			writeFile(document);
		} catch (IOException e) {
			System.out.println("保存XML出错：" + e.getMessage());
		}
	}
}
