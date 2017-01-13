package com.dangdang.tools.atf.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.dangdang.tools.atf.entity.SoftDeleteObject;
import com.dangdang.tools.atf.entity.TestSystem;
import com.dangdang.tools.atf.models.LoggerObject;

public class HibernateHelper extends LoggerObject {

	private static Map<String, TestSystem> TESTSYSTEMMAP;

	private static Map<String, Configuration> connectionPool = new HashMap<String, Configuration>();
	private static Map<String, SessionFactory> sessionFactoryPool = new HashMap<String, SessionFactory>();
	private static Map<String, Session> sessionPool = new HashMap<String, Session>();

	static {
		loadConfig();
	}

	private static void loadConfig() {
		DEBUG("Setting ATF configuration base path: " + System.getProperty("user.dir") + "/ATF_Config/");
		System.setProperty("atf.config.dirs", System.getProperty("user.dir") + "/ATF_Config/");
		TESTSYSTEMMAP = new HashMap<String, TestSystem>();
		String path = System.getProperty("atf.config.dirs") + "database.hibernate.cfg.xml";
		DEBUG("Loading config file: " + path);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(path);
			@SuppressWarnings("unchecked")
			List<Element> databases = document.selectNodes("//database");
			DEBUG("Find " + databases.size() + " DATABASE configed");
			for (Element db : databases) {
				TestSystem config = new TestSystem();
				config.setId(db.attributeValue("id"));
				config.setExternalId(db.attributeValue("externalid"));
				config.setName(db.attributeValue("name"));
				config.setState(Boolean.parseBoolean(db.attributeValue("state")));
				TESTSYSTEMMAP.put(config.getId(), config);
				//System.out.println("create database atf_dbs_" + config.getId() + " DEFAULT CHARACTER SET utf8;");
				// insert into atf_dbs_lipingka.tbl_TestInterface select * from
				// atf_db_lipingka.ti;
				// insert into atf_dbs_lipingka.tbl_TestCase select * from
				// atf_db_lipingka.tc;
//				System.out.println("insert into atf_dbs_" + config.getId() + ".tbl_TestInterface select * from atf_db_" + config.getId() + ".ti");
//				System.out.println("insert into atf_dbs_" + config.getId() + ".tbl_TestCase select * from atf_db_" + config.getId() + ".tc");
				DEBUG("Add new DATABASE: " + config.toJson());
			}
		} catch (DocumentException e) {
			ERROR("Load config file failed: " + e.getMessage());
		}
	}

	public static List<TestSystem> getAllTestSystem() {
		DEBUG("HibernateHelper.getAllTestSystem(): get all test systems");
		return new ArrayList<>(TESTSYSTEMMAP.values());
	}

	private static Configuration configConnection(String testSystemId) {
		DEBUG("Lock connectionPool");
		synchronized (connectionPool) {
			try {
				DEBUG("Find connection by test system id: " + testSystemId);
				if (connectionPool.containsKey(testSystemId)) {
					DEBUG("Find connection for " + testSystemId + ", will delete it and build new one");
					connectionPool.remove(testSystemId);
				}
				DEBUG("Build new connection for " + testSystemId + ", config path: " + System.getProperty("atf.config.dirs") + testSystemId + ".hibernate.cfg.xml");
				Configuration configuration = new Configuration().configure(new File(System.getProperty("atf.config.dirs") + testSystemId + ".hibernate.cfg.xml"));
				DEBUG("Build new connection for " + testSystemId + " successfully!!");
				connectionPool.put(testSystemId, configuration);
				return configuration;
			} catch (Exception ex) {
				ERROR("Build session failed, test system id: " + testSystemId + ", Exception Message: " + ex.getMessage());
				return null;
			}
		}
	}

	private static Configuration getConnection(String testSystemId) {
		Configuration configuration = null;
		if (sessionPool.containsKey(testSystemId)) {
			DEBUG("Session pool contains key: " + testSystemId + ", will check is valid or not");
			configuration = connectionPool.get(testSystemId);
			if (configuration != null) {
				return configuration;
			} else {
				DEBUG(testSystemId + ": The session is not init,will create new one...");
				configuration = configConnection(testSystemId);
			}
		} else {
			DEBUG(testSystemId + ": Cannot find session in session pool, will create new one...");
			configuration = configConnection(testSystemId);
		}
		return configuration;
	}

	private static SessionFactory buildSessionFactory(String testSystemId) {
		DEBUG("Lock sessionFactoryPool");
		synchronized (sessionFactoryPool) {
			try {
				DEBUG("Find session by test system id: " + testSystemId);
				if (sessionFactoryPool.containsKey(testSystemId)) {
					DEBUG("Find sessionFactory for " + testSystemId + ", will delete it and build new one");
					sessionFactoryPool.remove(testSystemId);
				}
				DEBUG("Build new sessionFactory for " + testSystemId + ", config path: " + System.getProperty("atf.config.dirs") + testSystemId + ".hibernate.cfg.xml");
				SessionFactory sessionFactory = getConnection(testSystemId).buildSessionFactory();
				DEBUG("Build new sessionFactory for " + testSystemId + " successfully!!");
				sessionFactoryPool.put(testSystemId, sessionFactory);
				return sessionFactory;
			} catch (Exception ex) {
				ERROR("Build session failed, test system id: " + testSystemId + ", Exception Message: " + ex.getMessage());
				return null;
			}
		}
	}

	private static SessionFactory getSessionFactory(String testSystemId) {
		SessionFactory sessionFactory = null;
		if (sessionFactoryPool.containsKey(testSystemId)) {
			DEBUG("Session pool contains key: " + testSystemId + ", will check is valid or not");
			sessionFactory = sessionFactoryPool.get(testSystemId);
			if (sessionFactory != null) {
				if (sessionFactory.isClosed()) {
					sessionFactory = buildSessionFactory(testSystemId);
				} else {
					DEBUG(testSystemId + ": Find connected session for you");
				}
			} else {
				DEBUG(testSystemId + ": The session is not init,will create new one...");
				sessionFactory = buildSessionFactory(testSystemId);
			}
		} else {
			DEBUG(testSystemId + ": Cannot find session in session pool, will create new one...");
			sessionFactory = buildSessionFactory(testSystemId);
		}
		return sessionFactory;
	}

	private static Session buildSession(String testSystemId) {
		DEBUG("Lock sessionPool");
		synchronized (sessionPool) {
			try {
				DEBUG("Find session by test system id: " + testSystemId);
				if (sessionPool.containsKey(testSystemId)) {
					DEBUG("Find session for " + testSystemId + ", will delete it and build new one");
					sessionPool.remove(testSystemId);
				}
				DEBUG("Build new session for " + testSystemId + ", config path: " + System.getProperty("atf.config.dirs") + testSystemId + ".hibernate.cfg.xml");
				Session session = getSessionFactory(testSystemId).getCurrentSession();
				DEBUG("Build new session for " + testSystemId + " successfully!!");
				sessionPool.put(testSystemId, session);
				return session;
			} catch (Exception ex) {
				ERROR("Build session failed, test system id: " + testSystemId + ", Exception Message: " + ex.getMessage());
				return null;
			}
		}
	}

	private static Session getSession(String testSystemId) {
		return buildSession(testSystemId);
	}

	private static Transaction beginTransaction(Session session) {
		try {
			DEBUG("Begin new transaction");
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			Transaction transaction = session.beginTransaction();
			DEBUG("Create new transaction");
			return transaction;
		} catch (Exception e) {
			ERROR("Build transaction failed, Exception Message: " + e.getMessage());
			return null;
		}
	}

	private static <T> T clean(T t) {
		DEBUG("Check the soft delete object is delete or not");
		if (t != null) {
			if (t instanceof SoftDeleteObject) {
				SoftDeleteObject _t = (SoftDeleteObject) t;
				if (_t.isState()) {
					DEBUG("The SDO is not deleted:" + _t.toJson());
					return t;
				}
			} else {
				return t;
			}
		}
		return null;
	}

	private static <T> List<T> clean(List<T> list) {
		List<T> newList = new ArrayList<T>();
		for (T t : list) {
			T _t = clean(t);
			if (_t != null) {
				newList.add(_t);
			}
		}
		return newList;
	}

	public static <T> boolean exist(Class<T> entityClass, Map<String, Object> args, String testSystemId) {
		DEBUG("Get session by id:" + testSystemId);
		Session session = getSession(testSystemId);
		if (session == null) {
			ERROR("HibernateHelper.exist(): Cannot get session for " + testSystemId + ", will return an empty list");
			return true;
		}
		DEBUG("Begin new transaction");
		Transaction transaction = beginTransaction(session);
		if (transaction == null) {
			ERROR("HibernateHelper.exist(): Cannot create transaction, will return an empty list");
			return true;
		}
		DEBUG("Build query...");
		StringBuilder query = new StringBuilder();
		query = query.append("from ").append(entityClass.getSimpleName());
		if (args != null && !args.isEmpty() && args.size() > 0) {
			query = query.append(" where ");
			for (String key : args.keySet()) {
				query = query.append(" ").append(key).append("=:" + key + " or");
			}
			query = query.replace(query.length() - " or".length(), query.length(), "");
			query = query.append(" and state=:state");
		}
		DEBUG("Select SDO by Query:" + query.toString());
		Query<T> result = session.createQuery(query.toString(), entityClass);
		if (args != null && !args.isEmpty() && args.size() > 0) {
			Set<Entry<String, Object>> entrySet = args.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				result.setParameter(entry.getKey(), entry.getValue());
			}
			result.setParameter("state", true);
		}
		List<T> queryResult = result.getResultList();
		try {
			DEBUG("transaction.commit();");
			transaction.commit();
			return queryResult.size() > 0;
		} catch (Exception ex) {
			ERROR("HibernateHelper.exist(): cannot query SDO, Exception Message: " + ex.getMessage());
			ex.printStackTrace();
			if (transaction != null) {
				DEBUG("HibernateHelper.exist(): transaction is not null, will rollback");
				transaction.rollback();
				DEBUG("HibernateHelper.exist(): transaction.rollback()------OK");
			}
			WARN("HibernateHelper.findAll(): Stop this connection, remove from session pool, then return an empty list");
			StopConnect(session);
			sessionPool.remove(testSystemId);
			return false;
		}
	}

	public static <T> T find(Class<T> entityClass, Object primaryKey, String testSystemId) {
		DEBUG("Get session by id:" + testSystemId);
		Session session = getSession(testSystemId);
		if (session == null) {
			ERROR("HibernateHelper.find(): Cannot get session for " + testSystemId + ", will return null");
			return null;
		}
		DEBUG("Begin new transaction");
		Transaction transaction = beginTransaction(session);
		if (transaction == null) {
			ERROR("HibernateHelper.find(): Cannot create transaction, will return null");
			return null;
		}
		try {
			DEBUG("Select SDO: " + entityClass + " by id:" + primaryKey);
			T t = session.find(entityClass, primaryKey);
			DEBUG("transaction.commit()");
			transaction.commit();
			return clean(t);
		} catch (Exception ex) {
			ERROR("HibernateHelper.find(): Select SDO failed, Exception Message: " + ex.getMessage());
			ex.printStackTrace();
			if (transaction != null) {
				DEBUG("HibernateHelper.find(): transaction is not null, will rollback");
				transaction.rollback();
				DEBUG("HibernateHelper.find(): transaction.rollback()------OK");
			}
			WARN("HibernateHelper.find(): Stop this connection, remove from session pool, then return null");
			StopConnect(session);
			sessionPool.remove(testSystemId);
			return null;
		}
	}

	public static <T> List<T> findAll(Class<T> entityClass, Map<String, Object> args, String testSystemId) {
		DEBUG("Get session by id:" + testSystemId);
		Session session = getSession(testSystemId);
		if (session == null) {
			ERROR("HibernateHelper.findAll(): Cannot get session for " + testSystemId + ", will return an empty list");
			return new ArrayList<T>();
		}
		DEBUG("Begin new transaction");
		Transaction transaction = beginTransaction(session);
		if (transaction == null) {
			ERROR("HibernateHelper.findAll(): Cannot create transaction, will return an empty list");
			return new ArrayList<T>();
		}
		DEBUG("Build query...");
		StringBuilder query = new StringBuilder();
		query = query.append("from ").append(entityClass.getSimpleName());
		if (args != null && !args.isEmpty() && args.size() > 0) {
			query = query.append(" where ");
			for (String key : args.keySet()) {
				query = query.append(" ").append(key).append("=:" + key + " and");
			}
			query.replace(query.length() - " and".length(), query.length(), "");
		}
		DEBUG("Select SDO by Query:" + query.toString());
		Query<T> result = session.createQuery(query.toString(), entityClass);
		if (args != null && !args.isEmpty() && args.size() > 0) {
			Set<Entry<String, Object>> entrySet = args.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				result.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List<T> queryResult = result.getResultList();
		try {
			DEBUG("transaction.commit();");
			transaction.commit();
			DEBUG("Clean deleted SDO");
			List<T> ret = clean(queryResult);
			session.close();
			return ret;
		} catch (Exception ex) {
			ERROR("HibernateHelper.findAll(): cannot query SDO, Exception Message: " + ex.getMessage());
			ex.printStackTrace();
			if (transaction != null) {
				DEBUG("HibernateHelper.findAll(): transaction is not null, will rollback");
				transaction.rollback();
				DEBUG("HibernateHelper.findAll(): transaction.rollback()------OK");
			}
			WARN("HibernateHelper.findAll(): Stop this connection, remove from session pool, then return an empty list");
			StopConnect(session);
			sessionPool.remove(testSystemId);
			return new ArrayList<T>();
		}
	}

	public static <T> boolean delete(Class<T> entityClass, T t, String testSystemId) {
		DEBUG("Get session by id:" + testSystemId);
		Session session = getSession(testSystemId);
		if (session == null) {
			ERROR("HibernateHelper.delete(): Cannot get session for " + testSystemId + ", will return false");
			return false;
		}
		DEBUG("Begin new transaction");
		Transaction transaction = beginTransaction(session);
		if (transaction == null) {
			ERROR("HibernateHelper.delete(): Cannot create transaction, will return false");
			return false;
		}
		if (t instanceof SoftDeleteObject) {
			SoftDeleteObject _t = (SoftDeleteObject) t;
			DEBUG("Set SDO state to false");
			_t.setState(false);
			DEBUG("The SDO state: " + _t.isState());
			session.update(t);
		} else {
			session.remove(t);
		}
		try {
			DEBUG("transaction.commit()");
			transaction.commit();
			return true;
		} catch (Exception ex) {
			ERROR("HibernateHelper.delete(): cannot set SDO state, Exception Message: " + ex.getMessage());
			ex.printStackTrace();
			if (transaction != null) {
				DEBUG("HibernateHelper.delete(): transaction is not null, will rollback");
				transaction.rollback();
				DEBUG("HibernateHelper.delete(): transaction.rollback()------OK");
			}
			WARN("HibernateHelper.delete(): Stop this connection, remove from session pool, then return false");
			StopConnect(session);
			sessionPool.remove(testSystemId);
			return false;
		}
	}

	public static <T> boolean update(Class<T> entityClass, T t, String testSystemId) {
		DEBUG("Get session by id:" + testSystemId);
		Session session = getSession(testSystemId);
		if (session == null) {
			ERROR("HibernateHelper.update(): Cannot get session for " + testSystemId + ", will return false");
			return false;
		}
		DEBUG("Begin new transaction");
		Transaction transaction = beginTransaction(session);
		if (transaction == null) {
			ERROR("HibernateHelper.update(): Cannot create transaction, will return false");
			return false;
		}
		try {
			DEBUG("Update " + entityClass + ", test system id: " + testSystemId);
			session.update(t);
			DEBUG("transaction.commit()");
			transaction.commit();
			DEBUG("Update ----OK");
			return true;
		} catch (Exception ex) {
			ERROR("HibernateHelper.update(): cannot update SDO, Exception Message: " + ex.getMessage());
			ex.printStackTrace();
			if (transaction != null) {
				DEBUG("HibernateHelper.update(): transaction is not null, will rollback");
				transaction.rollback();
				DEBUG("HibernateHelper.update(): transaction.rollback()------OK");
			}
			WARN("HibernateHelper.update(): Stop this connection, remove from session pool, then return false");
			StopConnect(session);
			sessionPool.remove(testSystemId);
			return false;
		}
	}

	public static <T> boolean save(Class<T> entityClass, T t, String testSystemId) {
		DEBUG("Get session by id:" + testSystemId);
		Session session = getSession(testSystemId);
		if (session == null) {
			ERROR("HibernateHelper.save(): Cannot get session for " + testSystemId + ", will return false");
			return false;
		}
		DEBUG("Begin new transaction");
		Transaction transaction = beginTransaction(session);
		if (transaction == null) {
			ERROR("HibernateHelper.save(): Cannot create transaction, will return false");
			return false;
		}
		try {
			DEBUG("Save " + entityClass + ", test system id: " + testSystemId);
			session.save(t);
			DEBUG("transaction.commit()");
			transaction.commit();
			DEBUG("Save ----OK");
			return true;
		} catch (Exception ex) {
			ERROR("HibernateHelper.save(): cannot save SDO, Exception Message: " + ex.getMessage());
			ex.printStackTrace();
			if (transaction != null) {
				DEBUG("HibernateHelper.save(): transaction is not null, will rollback");
				transaction.rollback();
				DEBUG("HibernateHelper.save(): transaction.rollback()------OK");
			}
			WARN("HibernateHelper.update(): Stop this connection, remove from session pool, then return false");
			StopConnect(session);
			sessionPool.remove(testSystemId);
			return false;
		}
	}

	public static void StopConnect(Session session) {
		WARN("Stoping SessionFactory and Session...");
		SessionFactory factory = null;
		if (session != null) {
			factory = session.getSessionFactory();
			WARN("Session is not null, stoping...");
			session.close();
			WARN("Session is not null, stop----OK");
		} else {
			WARN("Session is null, no need to stop");
		}
		if (factory != null) {
			WARN("SessionFactory is not null, stoping...");
			factory.close();
			WARN("SessionFactory is not null, stop----OK");
		} else {
			WARN("SessionFactory is null, no need to stop");
		}
	}

	public static void reset() {
		Set<Entry<String, Session>> entrySet = sessionPool.entrySet();
		for (Entry<String, Session> entry : entrySet) {
			StopConnect(entry.getValue());
		}
		sessionPool.clear();
		TESTSYSTEMMAP.clear();
		loadConfig();
	}
}
