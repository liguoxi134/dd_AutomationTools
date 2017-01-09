package com.dangdang.tools.atf.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerObject {
	private static Logger _log = null;

	public static void INFO(String info) {
		if (_log == null) {
			_log = LoggerFactory.getLogger(LoggerObject.class);
		}
		if (_log.isInfoEnabled()) {
			_log.info(info);
		}
	}
	public static void DEBUG(String info) {
		if (_log == null) {
			_log = LoggerFactory.getLogger(LoggerObject.class);
		}
		if (_log.isDebugEnabled()) {
			_log.debug(info);
		}
	}

	public static void ERROR(String info) {
		if (_log == null) {
			_log = LoggerFactory.getLogger(LoggerObject.class);
		}
		if (_log.isErrorEnabled()) {
			_log.error(info);
		}
	}

	public static void TRACE(String info) {
		if (_log == null) {
			_log = LoggerFactory.getLogger(LoggerObject.class);
		}
		if (_log.isTraceEnabled()) {
			_log.trace(info);
		}
	}

	public static void WARN(String info) {
		if (_log == null) {
			_log = LoggerFactory.getLogger(LoggerObject.class);
		}
		if (_log.isWarnEnabled()) {
			_log.warn(info);
		}
	}
}
