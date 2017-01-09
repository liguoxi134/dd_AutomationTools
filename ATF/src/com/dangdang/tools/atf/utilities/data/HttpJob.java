package com.dangdang.tools.atf.utilities.data;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HttpJob implements Job {

//	private static String getCompareHtml(String str, String type) {
//		try {
//			return "<span class='" + type + "'>" + str + "</span>";
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		return "";
//	}

//	private static String compare(String expectData, String realData) {
//		StringBuffer sb = new StringBuffer();
//		KeyValueMap<String, String> splitExpectMap = new KeyValueMap<String, String>();
//		KeyValueMap<String, String> splitRealMap = new KeyValueMap<String, String>();
//		Pattern pattern = Pattern.compile("\"([a-zA-Z_0-9]+?)\"[ :=\"]+([^\\[\\{]*?)[\",\\]\\}]");
//		Matcher m = pattern.matcher(expectData);
//		while (m.find()) {
//			splitExpectMap.set(m.group(1), m.group(2));
//		}
//		m = pattern.matcher(realData);
//		while (m.find()) {
//			splitRealMap.set(m.group(1), m.group(2));
//		}
//		for (KeyValuePair<String, String> kvp : splitExpectMap.getData()) {
//			ArrayList<String> list = splitRealMap.get(kvp.getKey());
//			sb.append("<li><span class='kw'>" + kvp.getKey() + "</span>");
//			sb.append("<span class='kv'>" + kvp.getValue() + "</span>[");
//			for (String val : list) {
//				if (kvp.getValue().equals(val)) {
//					sb.append(getCompareHtml(val, "exact"));
//					continue;
//				} else if (kvp.getValue().equalsIgnoreCase(val)) {
//					sb.append(getCompareHtml(val, "part"));
//					continue;
//				} else {
//					sb.append(getCompareHtml(val, "diff"));
//				}
//			}
//			if (!list.isEmpty()) {
//				sb.substring(0, sb.length() - 1);
//			}
//			sb.append("]</li>");
//		}
//		return sb.toString();
//	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		System.out.println("现在运行的任务是:" + context.getJobDetail().getKey().getName());
//		String rid = context.getMergedJobDataMap().getString("rid");
//		String[] ids = context.getMergedJobDataMap().getString("ids").split(";");
//
//		TestInterface ti = DataFactory.getTestInterfaceById(Integer.parseInt(rid));
//		Map<String, String> compareMap = new HashMap<String, String>();
//		for (String id : ids) {
//			TestCase tc = DataFactory.getTestCaseById(Integer.parseInt(id));
//			Parameter pl = DataFactory.getParameterByTestCaseId(Integer.parseInt(id));
//			ExpectResult er = DataFactory.getExpectResultByTestCaseId(Integer.parseInt(id));
//			String responseText = null;
//			try {
//				if (ti.getType() == TestInterfaceType.POST) {
//					responseText = HttpHelper.doPost(ti.getUrl(), pl.getValue());
//				} else if (ti.getType() == TestInterfaceType.GET) {
//					responseText = HttpHelper.sendGet(ti.getUrl(), pl.getValue());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (er.getType() == ExpectResultType.JSON) {
//				responseText = compare(er.getValue(), responseText);
//				compareMap.put(tc.getName(), responseText);
//			} else if (er.getType() == ExpectResultType.XML) {
//
//			} else if (er.getType() == ExpectResultType.TEXT) {
//
//			}
//		}
	}
}
