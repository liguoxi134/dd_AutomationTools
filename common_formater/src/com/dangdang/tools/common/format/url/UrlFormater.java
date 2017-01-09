package com.dangdang.tools.common.format.url;

import com.dangdang.tools.common.format.IFormater;

public class UrlFormater implements IFormater {

	@Override
	public String format(String text) throws Exception {
		System.out.println("UrlFormater.format()");
		// 取出URL中的Query部分
		int idxOfQuery = text.indexOf("?");
		if (0 <= idxOfQuery && idxOfQuery <= text.length() - 1) {
			text = text.substring(idxOfQuery + 1);
		}
		// 1. 干掉回车，换行，Tab，问号
		text = text.replaceAll("[\r\n\t\\?]+", "");
		// 2. 干掉多个=
		text = text.replaceAll("=+", "=");
		// 3. 干掉多个&
		text = text.replaceAll("&+", "&");
		// 4. 干掉多个连起来的&=
		text = text.replaceAll("&=", "");

		String[] kvpArray = text.split("&");
		StringBuilder stringBuilder = new StringBuilder();
		for (String kvp : kvpArray) {
			if (kvp.indexOf("=") > 0) {
				String[] kv = kvp.split("=");
				if (kv.length == 2) {
					stringBuilder.append("&" + kv[0] + "=" + kv[1]);
				} else if (kv.length == 1) {
					stringBuilder.append("&" + kv[0] + "=");
				} else {
					throw new Exception("URL传递的参数不合法：" + kvp);
				}
			} else {
				throw new Exception("URL参数非法，参数格式必须遵从a=b或者a=格式,而" + kvp + "并不符合这两种格式");
			}
		}
		if (stringBuilder.length() > 0) {
			return stringBuilder.substring(1);
		}
		return stringBuilder.toString();
	}
}
