package com.dangdang.tools.atf.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.dangdang.tools.atf.models.LoggerObject;

public class HttpHelper extends LoggerObject {
	public static String sendGet(String url, String param, String[] headers) {
		String result = "";
		url = url.trim().replaceAll("[\r\n]+?", "");
		param = param.trim().replaceAll("[\r\n]+?", "");
		param = param.startsWith("?") ? param.substring(1) : param;
		String urlNameString = "";
		int idx1 = url.indexOf("?");
		int idx2 = url.lastIndexOf("?");

		if (idx1 != -1) {
			if (idx1 == idx2) {
				urlNameString = url + "&" + param;
			} else {
				return "接口地址不正确";
			}
		} else {
			urlNameString = url + "?" + param;
		}
		URL realUrl = null;
		try {
			realUrl = new URL(urlNameString);
		} catch (MalformedURLException e) {
			return e.getMessage();
		}
		URLConnection connection = null;
		try {
			connection = realUrl.openConnection();
		} catch (IOException e) {
			return e.getMessage();
		}
		for (String header : headers) {
			String[] kvp = header.split("=", 2);
			if (kvp.length > 0) {
				connection.setRequestProperty(kvp[0], kvp.length == 2 ? kvp[1] : "");
			}
		}
		try {
			connection.connect();
			byte[] responseBytes = readBytes(connection.getInputStream());
			result = new String(responseBytes, "UTF-8");
		} catch (IOException e) {
			return e.getMessage();
		}

		return result;
	}

	public static String doPost(String url, String param, String[] headers) {
		PrintWriter writer = null;
		String result = "";
		url = url.replaceAll("[\r\n]+?", "");
		URL realUrl;
		try {
			realUrl = new URL(url);
		} catch (MalformedURLException e) {
			return e.getMessage();
		}

		URLConnection connection;
		try {
			connection = realUrl.openConnection();
		} catch (IOException e) {
			return e.getMessage();
		}
		for (String header : headers) {
			String[] kvp = header.split("=", 2);
			if (kvp.length > 0) {
				connection.setRequestProperty(kvp[0], kvp.length == 2 ? kvp[1] : "");
			}
		}
		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		try {
			writer = new PrintWriter(connection.getOutputStream());
			writer.print(param);
			writer.flush();
			byte[] responseBytes = readBytes(connection.getInputStream());
			result = new String(responseBytes, "UTF-8");
		} catch (IOException e) {
			return e.getMessage();
		}

		return result;
	}

	private static byte[] readBytes(InputStream is) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int readByteCount = is.read(buffer);
			while (readByteCount != -1) {
				output.write(buffer, 0, readByteCount);
				readByteCount = is.read(buffer);
			}
			byte[] bs = output.toByteArray();
			output.close();
			return bs;
		} catch (Exception e) {
			return null;
		}
	}
}
