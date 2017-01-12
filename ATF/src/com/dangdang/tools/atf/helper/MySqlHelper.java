package com.dangdang.tools.atf.helper;

import java.io.ByteArrayOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	public static String executeQuery(String connStr, String sql, Properties info) throws Exception {
		Connection connection = DriverManager.getConnection(connStr, info);
		CallableStatement call = connection.prepareCall(sql);
		boolean hasResultSet = call.execute();
		ObjectMapper map = new ObjectMapper();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonGenerator generator = map.getFactory().createGenerator(baos);
		generator.writeStartArray();// [
		do {
			generator.writeStartArray();// [
			if (hasResultSet) {
				ResultSet rs = call.getResultSet();
				String[] cols = new String[rs.getMetaData().getColumnCount()];
				for (int i = 0; i < cols.length; i++) {
					cols[i] = rs.getMetaData().getColumnName(i + 1);
				}
				while (rs.next()) {
					generator.writeStartObject();// {
					for (int i = 0; i < cols.length; i++) {
						generator.writeObjectField(cols[i], rs.getObject(i + 1)); // colName:cellValue
					}
					generator.writeEndObject();// }
				}
			}
			generator.writeEndArray();// ]
		} while (hasResultSet = call.getMoreResults() == true);
		generator.writeEndArray();// ]
		generator.flush();
		baos.flush();
		String result = new String(baos.toByteArray(), "UTF-8");
		baos.close();
		generator.close();
		connection.close();
		return map.writerWithDefaultPrettyPrinter().writeValueAsString(map.readValue(result, Object.class));
	};

	// SELECT SCHEMA_NAME FROM information_schema.SCHEMATA
	public static void main(String[] args) throws Exception {
		String connStr = "jdbc:mysql://10.255.242.58:3306?useUnicode=true&characterEncoding=UTF-8&user=atf_root&password=Pa$$word~1234";
		String sql = "select * from atf_log.log limit 10;";
		//executeQuery(connStr, sql);
		// String connStr =
		// "jdbc:mysql://10.255.242.58:3306?useUnicode=true&characterEncoding=UTF-8&user=atf_root&password=Pa$$word~1234";
		// String sql = "select * from atf_log.log limit 10;";
		// Connection connection = DriverManager.getConnection(connStr);
		// CallableStatement call = connection.prepareCall(sql);
		// ResultSet rs = call.executeQuery();
		// ResultSetMetaData metaData = rs.getMetaData();
		// int colCount = metaData.getColumnCount();
		// for (int i = 0; i < colCount; i++) {
		// System.out.println(metaData.getCatalogName(i + 1));
		// }
		// connection.close();
	}
}
