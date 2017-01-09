package com.dangdang.tools.atf.utilities.data;

import java.util.*;

public class DataSet {
	private Map<String, DataTable> tables = new HashMap<String, DataTable>();

	public int getTableCount() {
		return tables.size();
	}

	public void addTable(String tableName, DataTable table) {
		this.tables.put(tableName, table);
	}

	public DataTable getTable(String tableName) {
		return tables.get(tableName);
	}
	public Map<String, DataTable> getTables() {
		return tables;
	}
}
