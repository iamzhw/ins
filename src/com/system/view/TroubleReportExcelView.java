package com.system.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

@SuppressWarnings("all")
public class TroubleReportExcelView extends AbstractExcelView{
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = (String) model.get("name");
		response.setHeader("Content-Disposition", "inline; filename="
				+ new String((name + "报表.xls").getBytes("gb2312"), "iso8859-1"));
		List<Map> dataList = (List<Map>) model.get("dataList");
		String[] cols = (String[]) model.get("cols");
		HSSFSheet sheet = workbook.createSheet(name);
		HSSFRow header = sheet.createRow(0);
		HSSFRow header1 = sheet.createRow(1);
		HSSFRow header2 = sheet.createRow(2);
		//整体样式
		HSSFCellStyle cellStyle= workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框   
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框   
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框   
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框   
		HSSFFont f  = workbook.createFont();      
		f.setFontHeightInPoints((short) 16);// 字号   
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗   
		cellStyle.setFont(f);
		
		//style.setFont(f);  
		//标题行
		for (int i = 0; i < cols.length; i++) {
			if(i==0){
				 header.createCell((short)i).setCellValue(name);
			}else{
			header.createCell((short) i ).setCellValue("");
			}
			header.getCell(i).setCellStyle(cellStyle);
		}
		sheet.addMergedRegion(new Region(0,(short)0,0,(short)(cols.length-1)));
		
		cellStyle= workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框   
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框   
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框   
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框 
		HSSFFont f2  = workbook.createFont();      
		f2.setFontHeightInPoints((short) 12);// 字号   
		f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 
		cellStyle.setFont(f2);
		//第二列
		for (int i = 0; i < cols.length; i++) {
			if(i==3){
			header1.createCell((short) i ).setCellValue("隐患上报情况");
			}else{
				header1.createCell((short) i ).setCellValue(cols[i]);
			}
			header1.getCell(i).setCellStyle(cellStyle);
		}
		sheet.addMergedRegion(new Region(1,(short)3,1,(short)5));
		sheet.addMergedRegion(new Region(1,(short)0,2,(short)0));
		sheet.addMergedRegion(new Region(1,(short)1,2,(short)1));
		sheet.addMergedRegion(new Region(1,(short)2,2,(short)2));
		sheet.addMergedRegion(new Region(1,(short)6,2,(short)6));
		//第三列
		for (int i = 0; i < cols.length; i++) {
			header2.createCell((short) i ).setCellValue(cols[i]);
			header2.getCell(i).setCellStyle(cellStyle);
		}
		
		//文字格式
		cellStyle= workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框   
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框   
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框   
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框 
		
		for (int i = 0; i < dataList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 3);
			//row.setRowStyle(cellStyle);
			row.createCell((short)0).setCellValue(dataList.get(i).get("AREA_NAME").toString());
			row.createCell((short)1).setCellValue(dataList.get(i).get("SUM0").toString());
			row.createCell((short)2).setCellValue(dataList.get(i).get("SUM1").toString());
			row.createCell((short)3).setCellValue(dataList.get(i).get("SUM2").toString());
			row.createCell((short)4).setCellValue(dataList.get(i).get("SUM3").toString());
			row.createCell((short)5).setCellValue(dataList.get(i).get("SUM4").toString());
			row.createCell((short)6).setCellValue(dataList.get(i).get("SUM5").toString());
			
			row.getCell(0).setCellStyle(cellStyle);
			row.getCell(1).setCellStyle(cellStyle);
			row.getCell(2).setCellStyle(cellStyle);
			row.getCell(3).setCellStyle(cellStyle);
			row.getCell(4).setCellStyle(cellStyle);
			row.getCell(5).setCellStyle(cellStyle);
			row.getCell(6).setCellStyle(cellStyle);
		}
		
		sheet.setColumnWidth(0,2500);
		sheet.setColumnWidth(1,5500);
		sheet.setColumnWidth(2,3000);
		sheet.setColumnWidth(3,5500);
		sheet.setColumnWidth(4,5000);
		sheet.setColumnWidth(5,4500);
		sheet.setColumnWidth(6,20000);
	
	}

}
