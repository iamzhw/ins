package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.lang.annotation.SuppressAjWarnings;

@SuppressWarnings("all")
public class MyExcelTool {

	private Workbook workbook;

	public MyExcelTool(File f) {
		// try {
		// workbook = new HSSFWorkbook(new FileInputStream(f));
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		try {
			workbook = new XSSFWorkbook(f.getAbsolutePath());
		} catch (Exception ex) {
			try {
				workbook = new HSSFWorkbook(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List getDataInSheet(int sheetNumber) {
		List<List> result = new ArrayList<List>();
		// 获得指定的sheet
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得sheet的总行数
		int rowCount = sheet.getLastRowNum();
		if (rowCount < 1) {
			return result;
		}
		// 遍历行row
		for (int i = 0; i <= rowCount; i++) {
			// 获得行对象
			Row row = sheet.getRow(i);
			if (row != null) {
				List<Object> rowData = new ArrayList<Object>();
				// 获得本行中单元格的个数
				int cellCount = row.getLastCellNum();
				// 遍历列cell
				for (int j = 0; j <= cellCount; j++) {
					Cell cell = row.getCell(j);
					// 获得指定单元格中的数据
					Object cellStr = this.getCellString(cell);
					rowData.add(cellStr);
				}
				result.add(rowData);
			}
		}
		return result;
	}

	private Object getCellString(Cell cell) {
		Object result = null;
		if (cell != null) {
			int cellType = cell.getCellType();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(d);
					result = date.toLocaleString();
				} else {
					DecimalFormat df = new DecimalFormat("0");
					result = df.format(cell.getNumericCellValue());
				}
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		File file = new File(
				"C:\\Users\\Monkey.D.Luffy\\Desktop\\柱形图\\成端动态q.xls");
		MyExcelTool parser = new MyExcelTool(file);

		List<List> datas = parser.getDataInSheet(1);
		for (int i = 0; i < datas.size(); i++) {
			List row = datas.get(i);
			// }
			if (i > 0 && row.size() > 8) {
				String o = (String) (row.get(7) == null ? "" : row.get(7));
				if (o.equals("已整改")) {
					//MyDebug.info("框号：" + row.get(0) + ",光路编码：" + row.get(4)
					//		+ ",现场情况：" + row.get(6));
					//				
					// // for(short j=0;j<row.size();j++){
					// // Object value = row.get(j);
					// // String data = String.valueOf(value);
					// // System.out.print("j:"+j+","+data+"\t");
					// }
				}
			}
			//MyDebug.info(i + "行结束");
		}

	}

}
