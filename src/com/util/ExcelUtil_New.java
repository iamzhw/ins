package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil_New {

	private Workbook workBook;

	public ExcelUtil_New(InputStream input,String fileName) throws IOException {
        try {  
            InputStream is = input;
            if(fileName.endsWith(".xls")){  
            	workBook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(".xlsx")){  
            	workBook = new XSSFWorkbook(is);  
            }else{  
            	workBook=null;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}

	public List<List<String>> getDatas(int num){
		Sheet sheet = workBook.getSheetAt(0);
		int size = sheet.getLastRowNum();
		if(size == 0){
			return null;
		}
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> data = null;
		Row row = null;
		Cell cell = null;
		for(int i = 1; i <= size; i ++){
			 row = sheet.getRow(i);
			 if(null!=row){
				 data = new ArrayList<String>();
				 for(int j = 0; j < num; j ++){
					 cell = row.getCell(j);
					 try {
						if(cell != null){
							data.add(cell.getStringCellValue());
						} else {
							data.add("");
						}
					} catch (Exception e) {
						//e.printStackTrace();
						data.add(new DecimalFormat("#").format(cell.getNumericCellValue()));
					}
				 }
				 datas.add(data);
			 }
		}
		return datas;
	}
	/**
	 *获取excel数据(含小数) 
	 */
	public List<List<String>> getDecimalDatas(int num){
		Sheet sheet = workBook.getSheetAt(0);
		int size = sheet.getLastRowNum();
		if(size == 0){
			return null;
		}
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> data = null;
		Row row = null;
		Cell cell = null;
		for(int i = 1; i <= size; i ++){
			 row = sheet.getRow(i);
			 if(null!=row){
				 data = new ArrayList<String>();
				 for(int j = 0; j < num; j ++){
					 cell = row.getCell(j);
					 try {
						if(cell != null){
							data.add(cell.getStringCellValue());
						} else {
							data.add("");
						}
					} catch (Exception e) {
						//e.printStackTrace();
						data.add(new DecimalFormat("####.##########").format(cell.getNumericCellValue()));
					}
				 }
				 datas.add(data);
			 }
		}
		return datas;
	}
}
