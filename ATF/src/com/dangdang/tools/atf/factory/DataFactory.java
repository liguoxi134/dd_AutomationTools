package com.dangdang.tools.atf.factory;

import static com.dangdang.tools.atf.helper.HibernateHelper.delete;
import static com.dangdang.tools.atf.helper.HibernateHelper.exist;
import static com.dangdang.tools.atf.helper.HibernateHelper.find;
import static com.dangdang.tools.atf.helper.HibernateHelper.findAll;
import static com.dangdang.tools.atf.helper.HibernateHelper.save;
import static com.dangdang.tools.atf.helper.HibernateHelper.update;
import static com.dangdang.tools.atf.models.LoggerObject.DEBUG;
import static com.dangdang.tools.atf.models.LoggerObject.ERROR;
import static com.dangdang.tools.atf.models.LoggerObject.WARN;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.dangdang.tools.atf.entity.TestCase;
import com.dangdang.tools.atf.entity.TestConfig;
import com.dangdang.tools.atf.entity.TestInterface;
import com.dangdang.tools.atf.entity.TestSystem;
import com.dangdang.tools.atf.entity.VerifyDatabaseConfig;
import com.dangdang.tools.atf.helper.GBKOrder;
import com.dangdang.tools.atf.helper.HibernateHelper;

public class DataFactory {

	public static List<VerifyDatabaseConfig> checkVerifyDatabase(String ip, String port, String pwd, String uid, String type, String name) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("ip", ip);
		args.put("port", port);
		args.put("pwd", pwd);
		args.put("uid", uid);
		args.put("type", type);
		Conjunction junction = Restrictions.conjunction();
		Conjunction conjunction = Restrictions.conjunction();
		Disjunction disjunction = Restrictions.disjunction();
		junction.add(Restrictions.eq("state", true));
		conjunction.add(Restrictions.allEq(args));
		disjunction.add(Restrictions.eq("name", name));
		disjunction.add(conjunction);
		junction.add(disjunction);
		return exist(VerifyDatabaseConfig.class, junction, "vdbs");
	}

	public static List<TestInterface> checkTestInterface(String url, String name, String testSystemId) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("url", url);
		args.put("name", name);
		Junction conjunction = Restrictions.conjunction();
		Junction disjunction = Restrictions.disjunction();
		conjunction.add(Restrictions.eq("state", true));
		disjunction.add(Restrictions.eq("url", url));
		disjunction.add(Restrictions.eq("name", name));
		conjunction.add(disjunction);

		return exist(TestInterface.class, conjunction, testSystemId);
	}

	public static List<TestCase> checkTestCase(String name, String testSystemId) {
		Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("name", name));
		junction.add(Restrictions.eq("state", true));
		return exist(TestCase.class, junction, testSystemId);
	}

	public static VerifyDatabaseConfig getVerifyDatabaseConfigById(int configId) {
		DEBUG("DataFactory.getVerifyDatabaseConfigById()");
		return find(VerifyDatabaseConfig.class, configId, "vdbs");
	}

	public static TestInterface getTestInterfaceById(int testInterfaceId, String testSystemId) {
		DEBUG("DataFactory.getTestInterfaceById()");
		return find(TestInterface.class, testInterfaceId, testSystemId);
	}

	public static TestCase getTestCaseById(int testCaseId, String testSystemId) {
		DEBUG("DataFactory.getTestCaseById()");
		return find(TestCase.class, testCaseId, testSystemId);
	}

	public static TestConfig getTestConfigByTestCaseId(int testCaseId, String testSystemId) {
		DEBUG("DataFactory.getParameterByTestCaseId()");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testCaseId", testCaseId);
		Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("testCaseId", testCaseId));
		List<TestConfig> result = findAll(TestConfig.class, junction, testSystemId, null);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public static List<VerifyDatabaseConfig> getVerifyDatabaseConfigs() {
		Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("state", true));
		List<VerifyDatabaseConfig> list = findAll(VerifyDatabaseConfig.class, junction, "vdbs", GBKOrder.asc("name"));
		if (list == null) {
			return new ArrayList<VerifyDatabaseConfig>();
		}
		return list;
	}

	public static List<TestSystem> getAllTestSystem() {
		DEBUG("DataFactory.getAllTestSystem()");
		return HibernateHelper.getAllTestSystem();
	}

	public static List<TestInterface> getAllTestInterface(String testSystemId) {
		DEBUG("DataFactory.getAllTestInterface()");
		Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("state", true));
		List<TestInterface> result = findAll(TestInterface.class, junction, testSystemId, GBKOrder.asc("name"));
		return result;
	}

	public static List<TestCase> getAllTestCase(int testInterfaceId, String testSystemId) {
		DEBUG("DataFactory.getAllTestCase()");
		Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("testInterfaceId", testInterfaceId));
		junction.add(Restrictions.eq("state", true));
		List<TestCase> result = findAll(TestCase.class, junction, testSystemId, GBKOrder.asc("name"));
		return result;
	}

	public static boolean deleteVerifyDatabaseConfig(int configId) {
		VerifyDatabaseConfig testInterface = find(VerifyDatabaseConfig.class, configId, "vdbs");
		boolean result = delete(VerifyDatabaseConfig.class, testInterface, "vdbs");
		return result;
	}

	public static boolean deleteTestInterface(int testInterfaceId, String testSystemId) {
		DEBUG("DataFactory.deleteTestInterface()");
		DEBUG("Find test interface:" + testInterfaceId + "==>" + testSystemId);
		TestInterface testInterface = find(TestInterface.class, testInterfaceId, testSystemId);
		DEBUG("Delete test interface:" + testInterface.toJson());
		boolean result = delete(TestInterface.class, testInterface, testSystemId);
		return result;
	}

	public static boolean deleteTestCase(int testCaseId, String testSystemId) {
		DEBUG("DataFactory.deleteTestCase()");
		DEBUG("Find test case:" + testCaseId + "==>" + testSystemId);
		TestCase testCase = find(TestCase.class, testCaseId, testSystemId);
		DEBUG("Delete test case:" + testCase.toJson());
		boolean result = delete(TestCase.class, testCase, testSystemId);
		return result;
	}

	public static boolean updateVerifyDatabaseConfig(VerifyDatabaseConfig config) {
		String testSystemId = "vdbs";
		if (config.getId() > 0) {
			VerifyDatabaseConfig dbConfig = find(VerifyDatabaseConfig.class, config.getId(), testSystemId);
			if (dbConfig != null) {
				dbConfig.setIp(config.getIp());
				dbConfig.setMore(config.getMore());
				dbConfig.setPort(config.getPort());
				dbConfig.setName(config.getName());
				dbConfig.setType(config.getType());
				dbConfig.setUid(config.getUid());
				dbConfig.setPwd(config.getPwd());
				config = dbConfig;
				return update(VerifyDatabaseConfig.class, dbConfig, testSystemId);
			}
		}
		return save(VerifyDatabaseConfig.class, config, testSystemId);
	}

	public static boolean updateTestInterface(TestInterface testInterface, String testSystemId) {
		DEBUG("DataFactory.updateTestInterface()");
		if (testInterface.getId() > 0) {
			DEBUG("Find exist test interface by id:" + testInterface.getId());
			TestInterface dbInterface = find(TestInterface.class, testInterface.getId(), testSystemId);
			if (dbInterface != null) {
				DEBUG("Find exist test interface:" + dbInterface.toJson());
				dbInterface.setDescription(testInterface.getDescription());
				dbInterface.setLastModifyDateTime(testInterface.getLastModifyDateTime());
				dbInterface.setLastModifyUser(testInterface.getLastModifyUser());
				dbInterface.setName(testInterface.getName());
				dbInterface.setType(testInterface.getType());
				dbInterface.setUrl(testInterface.getUrl());
				testInterface = dbInterface;
				DEBUG("Update exist test interface as:" + dbInterface.toJson());
				return update(TestInterface.class, dbInterface, testSystemId);
			} else {
				WARN("Cannot find test interface by id:" + testInterface.getId() + ", will create new one");
			}
		}
		DEBUG("Add new test interface:" + testInterface.toJson());
		return save(TestInterface.class, testInterface, testSystemId);
	}

	public static boolean updateTestCase(TestCase testCase, int testInterfaceId, String testSystemId) {
		DEBUG("DataFactory.updateTestCase()");
		DEBUG("Find test interface by id: " + testInterfaceId);
		TestInterface testInterface = find(TestInterface.class, testInterfaceId, testSystemId);
		if (testInterface != null) {
			DEBUG("Find test interface: " + testInterface.toJson());
			if (testCase.getId() > 0) {
				DEBUG("Find exist test case by id:" + testCase.getId());
				TestCase dbCase = find(TestCase.class, testCase.getId(), testSystemId);
				if (dbCase != null && dbCase.getTestInterfaceId() == testInterfaceId) {
					DEBUG("Find exist test case:" + dbCase.toJson());
					dbCase.setDescription(testCase.getDescription());
					dbCase.setLastModifyDateTime(testCase.getLastModifyDateTime());
					dbCase.setLastModifyUser(testCase.getLastModifyUser());
					dbCase.setName(testCase.getName());
					testCase = dbCase;
					DEBUG("Update exist test case as:" + dbCase.toJson());
					return update(TestCase.class, dbCase, testSystemId);
				}
			} else {
				WARN("Cannot find test case by id:" + testCase.getId() + ", will create new one");
			}
			DEBUG("Add new test case:" + testCase.toJson());
			testCase.setTestInterfaceId(testInterface.getId());
			return save(TestCase.class, testCase, testSystemId);
		} else {
			ERROR("Cannot find test interface by id: " + testInterfaceId + ", operation cancel!");
			return false;
		}

	}

	public static boolean updateTestConfig(TestConfig testConfig, int testCaseId, String testSystemId) {
		DEBUG("DataFactory.updateTestConfig()");
		DEBUG("Find test case by id: " + testCaseId);
		TestCase testCase = find(TestCase.class, testCaseId, testSystemId);
		if (testCase != null) {
			DEBUG("Find test case: " + testCase.toJson());
			Junction junction = Restrictions.conjunction();
			junction.add(Restrictions.eq("testCaseId", testCase.getId()));
			List<TestConfig> list = findAll(TestConfig.class, junction, testSystemId, null);
			TestConfig dbTestConfig = list.size() > 0 ? list.get(0) : null;
			if (dbTestConfig != null) {
				DEBUG("TestConfig is not null, will update...=> " + testConfig.toJson());
				dbTestConfig.setLastModifyDateTime(testConfig.getLastModifyDateTime());
				dbTestConfig.setLastModifyUser(testConfig.getLastModifyUser());
				dbTestConfig.setRequestBody(testConfig.getRequestBody());
				dbTestConfig.setRequestHeaders(testConfig.getRequestHeaders());
				dbTestConfig.setRequestType(testConfig.getRequestType());
				dbTestConfig.setResultVerify(testConfig.getResultVerify());
				DEBUG("Update TestConfig into database");
				return update(TestConfig.class, dbTestConfig, testSystemId);
			} else {
				DEBUG("TestConfig is null, will create new one...=> " + testConfig.toJson());
				dbTestConfig = testConfig;
				dbTestConfig.setTestCaseId(testCase.getId());
				DEBUG("Save TestConfig into database");
				return save(TestConfig.class, dbTestConfig, testSystemId);
			}
		}
		ERROR("Cannot find test case by id: " + testCaseId + ", operation cancel!");
		return false;
	}

	public static void copyVerifyDatabaseConfig(int[] configIds) {
		String testSystemId = "vdbs";
		for (int testCaseId : configIds) {
			VerifyDatabaseConfig config = find(VerifyDatabaseConfig.class, testCaseId, testSystemId);
			if (config == null) {
				return;
			}
			VerifyDatabaseConfig cloneTestConfig = new VerifyDatabaseConfig();
			cloneTestConfig.setIp(config.getIp());
			cloneTestConfig.setMore(config.getMore());
			cloneTestConfig.setPort(config.getPort());
			cloneTestConfig.setName(config.getName());
			cloneTestConfig.setType(config.getType());
			cloneTestConfig.setUid(config.getUid());
			cloneTestConfig.setPwd(config.getPwd());
			save(VerifyDatabaseConfig.class, cloneTestConfig, testSystemId);
		}
	}

	public static void copyTestCase(int[] testCaseIds, int testInterfaceId, String testSystemId) {
		DEBUG("DataFactory.copyTestCase()");
		List<TestCase> clonedTestCases = new ArrayList<TestCase>();
		DEBUG("Find test interface by id:" + testInterfaceId);
		for (int testCaseId : testCaseIds) {
			TestInterface testInterface = find(TestInterface.class, testInterfaceId, testSystemId);
			if (testInterface == null) {
				ERROR("Cannot find test interface: " + testInterfaceId + ", operation will cancel");
				return;
			}
			DEBUG("Find test case by id:" + testCaseId);
			TestCase testCase = find(TestCase.class, testCaseId, testSystemId);
			if (testCase == null) {
				WARN("Cannot find test case: " + testCaseId + ", skip copy...");
				continue;
			}

			TestCase cloneCase = new TestCase(testCase.getName() + "_copyAt" + (new Date().getTime()), testCase.getDescription());
			DEBUG("Cloned Case:" + cloneCase.toJson());
			DEBUG("Set test interface");
			cloneCase.setTestInterfaceId(testInterface.getId());
			clonedTestCases.add(cloneCase);
			DEBUG("Save cloned case");
			save(TestCase.class, cloneCase, testSystemId);
			TestConfig testConfig = find(TestConfig.class, testCase.getId(), testSystemId);
			DEBUG("Getting testConfig...");
			if (testConfig != null) {
				TestConfig cloneTestConfig = new TestConfig();
				cloneTestConfig.setRequestBody(testConfig.getRequestBody());
				cloneTestConfig.setRequestHeaders(testConfig.getRequestHeaders());
				cloneTestConfig.setRequestType(testConfig.getRequestType());
				cloneTestConfig.setResultVerify(testConfig.getResultVerify());
				DEBUG("Clone testConfig:" + cloneTestConfig.toJson());
				cloneTestConfig.setTestCaseId(cloneCase.getId());
				DEBUG("Save cloned testConfig");
				save(TestConfig.class, cloneTestConfig, testSystemId);
			} else {
				DEBUG("TestConfig is null, skip clone");
			}
		}
	}

	public static void reset() {
		HibernateHelper.reset();
	}

	private static String AlertMessage = "";

	public static void setMessage(String message) {
		AlertMessage = message;
	}

	public static String getMessage() {
		return AlertMessage;
	}
}