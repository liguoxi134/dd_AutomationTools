package com.dangdang.tools.atf.helper;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import com.dangdang.tools.atf.utilities.data.DataRow;
import com.dangdang.tools.atf.utilities.data.DataSet;
import com.dangdang.tools.atf.utilities.data.DataTable;

public class ExcelHelper {
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_XLSX = "xlsx";

	private static Workbook getWorkbook(byte[] bs, String type) throws Exception {
		Workbook workbook = null;
		ByteArrayInputStream is = new ByteArrayInputStream(bs);
		if (type.equals(EXTENSION_XLS)) {
			workbook = new HSSFWorkbook(is);
		}
		else if (type.equals(EXTENSION_XLSX)) {
			workbook = new XSSFWorkbook(is);
		}
		else {
			throw new Exception("无效的Excel(*.xls,*xlsx)文件,");
		}
		return workbook;
	}

	private static byte[] readBytes(InputStream is) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int readByteCount = is.read(buffer);
			while (readByteCount != -1) {
				output.write(buffer, 0, readByteCount);
				readByteCount = is.read(buffer);
			}
			byte[] bs = output.toByteArray();
			output.close();
			return bs;
		}
		catch (Exception e) {
			return null;
		}
	}

	public static DataSet getExcelData(InputStream is, String type) throws Exception {
		return getExcelData(readBytes(is), type);
	}

	public static DataSet getExcelData(byte[] bs, String type) throws Exception {
		Workbook workbook = getWorkbook(bs, type);

		if (workbook == null) return null;

		DataSet ds = new DataSet();
		for (Sheet sheet : workbook) {
			DataTable table = new DataTable();
			for (int rowIdx = sheet.getFirstRowNum(); rowIdx <= sheet.getLastRowNum(); rowIdx++) {
				Row row = sheet.getRow(rowIdx);
				DataRow dataRow = new DataRow();
				ArrayList<String> data = new ArrayList<String>();
				for (int colIdx = row.getFirstCellNum(); colIdx < row.getLastCellNum(); colIdx++) {
					Cell cell = row.getCell(colIdx);
					data.add(getCellValue(cell));
				}
				dataRow.setRowData(data);
				table.appendRow(dataRow);
			}
			String sheetName = sheet.getSheetName();
			ds.addTable(sheetName, table);
		}
		try {
			workbook.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return ds;
	}

	public static byte[] setExcelData(DataSet ds) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		for (Map.Entry<String, DataTable> entry : ds.getTables().entrySet()) {
			Sheet sheet = workbook.createSheet(entry.getKey());
			for (DataRow dataRow : entry.getValue().getRows()) {
				Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
				for (String data : dataRow.getRowData()) {
					int colIdx = row.getLastCellNum();
					Cell cell = row.createCell(colIdx <= -1 ? 0 : colIdx);
					cell.setCellValue(data);
				}
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		workbook.close();
		return os.toByteArray();
	}

	private static String getCellValue(Cell cell) {
		if (cell == null) { return ""; }
		cell.setCellType(CellType.STRING);
		return String.valueOf(cell.getStringCellValue());
	}
	//
	// public static void main(String args[]) {
	// File file_o = new File("C:\\Users\\liguoxi\\Desktop\\new.xlsx");
	// File file = new File("C:\\Users\\liguoxi\\Desktop\\new_o.xlsx");
	// FileInputStream fs;
	// try {
	// fs = new FileInputStream(file);
	//
	// getExcelData(fs, "xlsx");
	// }
	// catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
