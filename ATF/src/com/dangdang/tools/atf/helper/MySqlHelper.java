package com.dangdang.tools.atf.helper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class MySqlHelper {
	public static List<String> showDatabases() throws Exception {
		String connStr = "jdbc:mysql://10.255.242.58:3306?useUnicode=true&characterEncoding=UTF-8&user=atf_root&password=Pa$$word~1234";
		Connection connection = DriverManager.getConnection(connStr);
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getCatalogs();
		List<String> dbs = new ArrayList<String>();
		while (rs.next()) {
			dbs.add(rs.getString("TABLE_CAT"));
		}
		connection.close();
		return dbs;
	}

	public static List<String> showTables(String db) throws Exception {
		String connStr = "jdbc:mysql://10.255.242.58:3306?useUnicode=true&characterEncoding=UTF-8&user=atf_root&password=Pa$$word~1234";
		Connection connection = DriverManager.getConnection(connStr);
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getTables(db, null, "%", null);
		List<String> tbs = new ArrayList<String>();
		while (rs.next()) {
			tbs.add(rs.getString("TABLE_NAME"));
		}
		connection.close();
		return tbs;
	}

	public static List<String> showColumns(String db, String table) throws Exception {
		String connStr = "jdbc:mysql://10.255.242.58:3306?useUnicode=true&characterEncoding=UTF-8&user=atf_root&password=Pa$$word~1234";
		Connection connection = DriverManager.getConnection(connStr);
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getColumns(db, null, table, "%");
		List<String> cols = new ArrayList<String>();
		while (rs.next()) {
			cols.add(rs.getString("COLUMN_NAME"));
		}
		connection.close();
		return cols;
	}

	// SELECT SCHEMA_NAME FROM information_schema.SCHEMATA
	public static void main(String[] args) throws Exception {
		String connStr = "jdbc:mysql://10.255.242.58:3306?useUnicode=true&characterEncoding=UTF-8&user=atf_root&password=Pa$$word~1234";
		String sql = "select * from atf_log.log limit 10;";
		Connection connection = DriverManager.getConnection(connStr);
		CallableStatement call = connection.prepareCall(sql);
		ResultSet rs = call.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int colCount = metaData.getColumnCount();
		for (int i = 0; i < colCount; i++) {
			System.out.println(metaData.getCatalogName(i + 1));
		}
		connection.close();
	}
}
