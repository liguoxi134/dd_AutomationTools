package com.dangdang.tools.atf.helper;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class LogHelper {

	private static Session buildSession() {
		try {
			Session session = new Configuration().configure(new File(System.getProperty("atf.config.dir") + "log.hibernate.cfg.xml")).buildSessionFactory().openSession();
			return session;
		} catch (Exception ex) {
			return null;
		}
	}

	private static Session session = null;

	private static Session getSession() {
		if (session != null) {
			if (!session.isConnected() || session.isDirty()) {
				session.clear();
				session.close();
				session = buildSession();
			}
		} else {
			session = buildSession();
		}
		return session;
	}

	private static Transaction beginTransaction(Session session) {
		try {
			Transaction transaction = session.beginTransaction();
			return transaction;
		} catch (Exception e) {
			return null;
		}
	}

}
