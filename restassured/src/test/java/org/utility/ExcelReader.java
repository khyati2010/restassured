package org.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader extends MainClass {

	static FileInputStream fis;
	static FileOutputStream fileOut;
	static XSSFWorkbook wb;
	static XSSFSheet sh;
	static XSSFCell cell;
	static XSSFRow row;
	static XSSFCellStyle cellstyle;
	static XSSFColor mycolor;

	//Method will create new file and read sheets with XSSF workbook library
	public static void setExcelFile(String ExcelPath, String SheetName) throws Exception {
		try {
			File f = new File(ExcelPath);
			if (!f.exists()) {
				f.createNewFile();
				System.out.println("File doesn't exist, so created!");
			}
			fis = new FileInputStream(ExcelPath);
			wb = new XSSFWorkbook(fis);
			sh = wb.getSheet(SheetName);
			// sh = wb.getSheetAt(0); //0 - index of 1st sheet
			if (sh == null) {
				sh = wb.createSheet(SheetName);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Method to read rows and columns from the sheet from above method
	public static String getCellData(int rowNumber, int columnNumber) throws Exception {
		try {
			cell = sh.getRow(rowNumber).getCell(columnNumber);
			String CellData = null;
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				CellData = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					CellData = cell.getDateCellValue().toString();
				} else {
					CellData = Double.toString(cell.getNumericCellValue());
					if (CellData.contains(".0"))// removing the extra .0
					{
						CellData = CellData.substring(0, CellData.length() - 2);
					}
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				CellData = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				CellData = Boolean.toString(cell.getBooleanCellValue());
				break;
			}
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}
}
