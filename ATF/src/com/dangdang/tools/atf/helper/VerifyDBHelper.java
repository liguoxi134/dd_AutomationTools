package com.dangdang.tools.atf.helper;

import java.util.List;

import com.dangdang.tools.atf.entity.VerifyDatabaseConfig;
import com.dangdang.tools.atf.models.LoggerObject;

public class VerifyDBHelper extends LoggerObject {

	public static List<VerifyDatabaseConfig> getList() {
		return HibernateHelper.findAll(VerifyDatabaseConfig.class, null, "vdbs");
	}
}
