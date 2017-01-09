package com.dangdang.tools.atf.helper;

import java.util.Date;

import com.dangdang.tools.atf.utilities.data.KeyValueMap;

public class EmptyChecker {

	public static boolean checkEmptyWithDefaultValue(String checkString) {
		return checkString == null || checkString.trim().isEmpty();
	}

	public static String checkEmptyWithDefaultValue(String checkString, String defaultValue) {
		return checkEmptyWithDefaultValue(checkString) ? defaultValue : checkString;
	}

	public static boolean checkEmptyWithDefaultValue(Date checkDate) {
		return checkDate == null;
	}

	public static Date checkEmptyWithDefaultValue(Date checkDate, Date defaultValue) {
		return checkEmptyWithDefaultValue(checkDate) ? defaultValue : checkDate;
	}

	public static <K, V> boolean checkEmptyWithDefaultValue(KeyValueMap<K, V> checkKVP) {
		return checkKVP == null;
	}

	public static <K, V> KeyValueMap<K, V> checkEmptyWithDefaultValue(KeyValueMap<K, V> checkKVP, KeyValueMap<K, V> defaultValue) {
		return checkEmptyWithDefaultValue(checkKVP) ? defaultValue : checkKVP;
	}

}
