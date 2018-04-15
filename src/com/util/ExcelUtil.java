package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {

	private HSSFWorkbook workBook;

	public ExcelUtil(InputStream input) throws IOException {
		POIFSFileSystem inputs = new POIFSFileSystem(input);
		workBook = new HSSFWorkbook(inputs);
	}

	public List<List<String>> getDatas(int num){
		HSSFSheet sheet = workBook.getSheetAt(0);
		int size = sheet.getLastRowNum();
		if(size == 0){
			return null;
		}
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> data = null;
		HSSFRow row = null;
		HSSFCell cell = null;
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
		HSSFSheet sheet = workBook.getSheetAt(0);
		int size = sheet.getLastRowNum();
		if(size == 0){
			return null;
		}
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> data = null;
		HSSFRow row = null;
		HSSFCell cell = null;
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
