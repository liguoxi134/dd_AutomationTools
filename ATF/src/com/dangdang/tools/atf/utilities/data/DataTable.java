package com.dangdang.tools.atf.utilities.data;

import java.util.*;

public class DataTable {

	private boolean firstRowAsHeader = true;
	private ArrayList<DataColumn> columns = new ArrayList<DataColumn>();
	private ArrayList<DataRow> rows = new ArrayList<DataRow>();

	public boolean isFirstRowAsHeader() {
		return firstRowAsHeader;
	}

	public void setFirstRowAsHeader(boolean firstRowAsHeader) {
		this.firstRowAsHeader = firstRowAsHeader;
	}

	public ArrayList<DataRow> getRows() {
		return rows;
	}

	public String GetCellValue(int rowIndex, int colIndex) {
		return this.rows.get(rowIndex).getRowData().get(colIndex);
	}

	public void appendRow(DataRow dataRow) {
		this.rows.add(dataRow);
	}

	public void appendRows(DataRow[] rows) {
		for (DataRow dataRow : rows) {
			this.rows.add(dataRow);
		}
	}

	public void clearRows() {
		this.rows.clear();
	}

	public ArrayList<DataColumn> getColumns() {
		return columns;
	}

	public void setColumns(DataColumn[] columns) {
		for (DataColumn dataColumn : columns) {
			this.columns.add(dataColumn);
		}
	}
}
