package com.dangdang.tools.atf.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.dangdang.tools.atf.models.LoggerObject;

public class HttpHelper extends LoggerObject {
	public static String sendGet(String url, String param, String[] headers) {
		if (headers == null) {
			headers = new String[0];
		}
		if (param == null) {
			param = "";
		}
		String result = "";
		url = url.trim().replaceAll("[\r\n]+?", "");
		param = param.trim().replaceAll("[\r\n]+?", "");
		String urlNameString = "";
		if (url.contains("?")) {
			if (param.contains("?")) {
				return "URL异常：请不要再接口和输入参数信息中都加入'?'";
			} else {
				urlNameString = url + param;
			}
		} else if (param.contains("?")) {
			urlNameString = url + param;
		} else {
			urlNameString = url + "?" + param;
		}
		urlNameString = urlNameString.endsWith("?") ? urlNameString.substring(0, urlNameString.length() - 1) : urlNameString;
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
		// connection.setDoInput(true);
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
		try {
			connection.setDoInput(true);
			if (param != null && !param.isEmpty()) {
				connection.setDoOutput(true);
				writer = new PrintWriter(connection.getOutputStream());
				writer.print(param);
				writer.flush();
			}
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
