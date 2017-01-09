package com.dangdang.tools.atf.pages;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.models.LoggerObject;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasePage extends LoggerObject {

	public enum ContentType {

		Json("text/json; charset=UTF-8"), Text("text/plain; charset=UTF-8"), Html("text/html; charset=UTF-8"), xlsx("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");

		private String name;

		ContentType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void writeMessage(HttpServletResponse response, String message, boolean code) {
		setContentType(response, ContentType.Json);
		ObjectMapper map = new ObjectMapper();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			JsonGenerator gen = map.getFactory().createGenerator(out, JsonEncoding.UTF8);
			gen.writeStartObject();
			gen.writeBooleanField("code", code);
			gen.writeStringField("message", message);
			gen.writeEndObject();
			gen.flush();
			byte[] bs = out.toByteArray();
			gen.close();
			out.close();
			response.getOutputStream().write(bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeMessage(HttpServletResponse response, Map<String, Object> list) {
		setContentType(response, ContentType.Json);
		ObjectMapper map = new ObjectMapper();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			JsonGenerator gen = map.getFactory().createGenerator(out, JsonEncoding.UTF8);
			gen.writeStartObject();
			if (list != null && !list.isEmpty()) {
				Set<Entry<String, Object>> entrySet = list.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					gen.writeObjectField(entry.getKey(), entry.getValue());
				}
			}
			gen.writeEndObject();
			gen.flush();
			byte[] bs = out.toByteArray();
			gen.close();
			out.close();
			response.getOutputStream().write(bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setContentType(HttpServletResponse response, ContentType ct) {
		response.setContentType(ct.getName());
	}

	public static String[] getStrings(HttpServletRequest request, String name) throws Exception {
		String[] result = request.getParameterValues(name);
		return result;
	}

	public static int[] getInts(HttpServletRequest request, String name) throws Exception {
		String[] _result = getStrings(request, name);
		int[] result = new int[_result.length];
		for (int i = 0; i < _result.length; i++) {
			result[i] = Integer.parseInt(_result[i]);
		}
		return result;
	}

	public static String getString(HttpServletRequest request, String name) throws Exception {
		String result = request.getParameter(name).trim();
		return result;
	}

	public static int getInt(HttpServletRequest request, String name) throws Exception {
		String result = getString(request, name);
		return Integer.parseInt(result);
	}

	public static String getString(Map<String, byte[]> map, String name) throws Exception {
		String result = new String(map.get(name), "UTF-8").trim();
		return result;
	}

	public static int getInt(Map<String, byte[]> map, String name) throws Exception {
		String result = getString(map, name);
		return Integer.parseInt(result);
	}

	public static <T extends Enum<T>> T getEnum(Map<String, byte[]> map, String name, Class<T> tclass) throws Exception {
		String result = getString(map, name);
		return (T) Enum.valueOf(tclass, result);
	}

	public static <T extends Enum<T>> T getEnum(HttpServletRequest request, String name, Class<T> tclass,T defaultValue) throws Exception {
		String result = getString(request, name);
		if(result==null||result.isEmpty()){
			return defaultValue;
		}
		return (T) Enum.valueOf(tclass, result);
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

	public static Map<String, byte[]> getRequestData(HttpServletRequest request) throws Exception {
		int totalBytes = request.getContentLength();
		byte[] dataOrigin = new byte[totalBytes];
		String contentType = request.getContentType(), boundary = null;

		int pos = contentType.indexOf("boundary=");
		if (pos != -1) {
			pos += "boundary=".length();
			boundary = "--" + contentType.substring(pos); // 解析出分界符
		}

		dataOrigin = readBytes(request.getInputStream());
		String requestContent = new String(dataOrigin, "ISO-8859-1");
		byte[] back = requestContent.getBytes("ISO-8859-1");
		for (int i = 0; i < back.length; i++) {
			if (dataOrigin[i] != back[i]) {
				System.out.println("NONONONO");
			}
		}
		String[] partArray = requestContent.split(boundary);

		Pattern pattern = Pattern.compile("[a-zA-Z- ;]*?name=\"([\\s\\S]+?)\"[\\s\\S]*?\r\n\r\n([\\s\\S]+)\r\n");
		Map<String, byte[]> map = new HashMap<String, byte[]>();

		for (String part : partArray) {
			if (part.isEmpty())
				continue;
			Matcher m = pattern.matcher(part);
			if (m.find()) {
				String key = m.group(1);
				byte[] value = m.group(2).getBytes("ISO-8859-1");
				map.put(key, value);
			}
		}
		return map;
	}

	public static void Redirect(HttpServletRequest request, HttpServletResponse response, String url) {
		try {
			setContentType(response, ContentType.Html);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
