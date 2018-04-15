package com.system.view;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.output.Format;
import org.springframework.web.servlet.view.document.AbstractExcelView;

@SuppressWarnings("all")
public class DataListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = (String) model.get("name");
		response.setHeader("Content-Disposition", "inline; filename=" + new String((name + "列表.xls").getBytes("gb2312"), "iso8859-1"));
		//获取数据
		List<Map> dataList = (List<Map>) model.get("dataList");
		String[] cols = (String[]) model.get("cols");
		String[] colsName = (String[]) model.get("colsName");
		//创建Excel
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFRow header = sheet.createRow(0);
		header.createCell((short) 0).setCellValue("序号");
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		for (int i = 0; i < colsName.length; i++) {
			header.createCell((short) i + 1).setCellValue(colsName[i]);
		}
		if(name.equals("分公司缆线巡检使用情况")){
			String s="";
			for (int i = 0; i < dataList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell((short) 0).setCellValue(i + 1);
				for (int j = 0; j < cols.length; j++) {
					s= String.valueOf(dataList.get(i).get(cols[j]) == null ? "0" : dataList.get(i).get(cols[j]));
					HSSFCell cell;
					if(s.indexOf("%")>0&&j!=0){
						cell=row.createCell((short) j + 1);
						style.setDataFormat(format.getFormat("0.00%"));
						cell.setCellStyle(style);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(s.replace("%", ""))/100);
					}else if(j!=0){
						cell=row.createCell((short) j + 1);
						cell.setCellValue(Double.parseDouble(s));
					}else{
						cell=row.createCell((short) j + 1);
						cell.setCellValue(s);
					}
				}
			}
		}else{
			for (int i = 0; i < dataList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell((short) 0).setCellValue(i + 1);
				for (int j = 0; j < cols.length; j++) {
						row.createCell((short) j + 1).setCellValue(
								String.valueOf(dataList.get(i).get(cols[j]) == null ? "" : dataList.get(i).get(cols[j])));
						
				}
			}
		}
	}

}
