package com.system.view;

import java.util.HashMap;
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
public class ArrivalRateDataListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = (String) model.get("name");
		response.setHeader("Content-Disposition", "inline; filename="
				+ new String((name + "列表.xls").getBytes("gb2312"), "iso8859-1"));
		List<Map> dataList = (List<Map>) model.get("dataList");
		String[] cols = (String[]) model.get("cols");
		HSSFSheet sheet = workbook.createSheet(name);
		HSSFRow header1 = sheet.createRow(0);
		HSSFRow header = sheet.createRow(1);

		// 整体样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		HSSFFont f = workbook.createFont();
		f.setFontHeightInPoints((short) 16);// 字号
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		cellStyle.setFont(f);

		// style.setFont(f);
		// 合并单元格
		header1.createCell((short) 0).setCellValue("日常巡检到位率报表");
		header1.getCell(0).setCellStyle(cellStyle);
		header1.createCell((short) 1).setCellStyle(cellStyle);
		header1.createCell((short) 2).setCellStyle(cellStyle);
		header1.createCell((short) 3).setCellStyle(cellStyle);
		header1.createCell((short) 4).setCellStyle(cellStyle);
		header1.createCell((short) 5).setCellStyle(cellStyle);
		header1.createCell((short) 6).setCellStyle(cellStyle);
		header1.createCell((short) 7).setCellStyle(cellStyle);
		header1.createCell((short) 8).setCellStyle(cellStyle);
		header1.createCell((short) 9).setCellStyle(cellStyle);
		header1.createCell((short) 10).setCellStyle(cellStyle);
		header1.createCell((short) 11).setCellStyle(cellStyle);
		header1.createCell((short) 12).setCellStyle(cellStyle);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 10));
		// header.createCell((short) 0).setCellValue("序号");
		// 标题行文字格式
		cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		HSSFFont f2 = workbook.createFont();
		f2.setFontHeightInPoints((short) 12);// 字号
		f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		cellStyle.setFont(f2);
		for (int i = 0; i < cols.length; i++) {
			header.createCell((short) i).setCellValue(cols[i]);
			header.getCell(i).setCellStyle(cellStyle);
		}
		// 标题行文字格式
		cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		for (int i = 0; i < dataList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 2);
			// row.setRowStyle(cellStyle);
			row.createCell((short) 0).setCellValue(
					dataList.get(i).get("STAFFNAME").toString());
			row.createCell((short) 1).setCellValue(
					dataList.get(i).get("AREANAME").toString());
			row.createCell((short) 2).setCellValue(
					dataList.get(i).get("TASKNAME").toString());
			row.createCell((short) 3).setCellValue(
					dataList.get(i).get("TIMES").toString());
			row.createCell((short) 4).setCellValue(
					dataList.get(i).get("PLANPOINTS").toString());
			row.createCell((short) 5).setCellValue(
					dataList.get(i).get("REALPOINTS").toString());
			row.createCell((short) 6).setCellValue(
					dataList.get(i).get("ARRIVALRATE").toString());
			row.createCell((short) 7).setCellValue(
					dataList.get(i).get("PLANPOINTS2").toString());
			row.createCell((short) 8).setCellValue(
					dataList.get(i).get("REALPOINTS2").toString());
			row.createCell((short) 9).setCellValue(
					dataList.get(i).get("ARRIVALRATE2").toString());
			row.createCell((short) 10).setCellValue(
					dataList.get(i).get("KILOMETRE").toString());
			row.createCell((short) 11).setCellValue(
					dataList.get(i).get("TIMECOUNT").toString());
			row.createCell((short) 12).setCellValue(
					dataList.get(i).get("TROUBLEPOINTS").toString());
			row.getCell(0).setCellStyle(cellStyle);
			row.getCell(1).setCellStyle(cellStyle);
			row.getCell(2).setCellStyle(cellStyle);
			row.getCell(3).setCellStyle(cellStyle);
			row.getCell(4).setCellStyle(cellStyle);
			row.getCell(5).setCellStyle(cellStyle);
			row.getCell(6).setCellStyle(cellStyle);
			row.getCell(7).setCellStyle(cellStyle);
			row.getCell(8).setCellStyle(cellStyle);
			row.getCell(9).setCellStyle(cellStyle);
			row.getCell(10).setCellStyle(cellStyle);
			row.getCell(11).setCellStyle(cellStyle);
			row.getCell(12).setCellStyle(cellStyle);
			/*
			 * for (int j = 0; j < cols.length; j++) { //row.createCell((short)
			 * j ).setCellValue( //
			 * String.valueOf(dataList.get(i).get(cols[j]))); }
			 */
		}
		sheet.setColumnWidth(0, 2500);
		sheet.setColumnWidth(1, 2500);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 5500);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 4500);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 4500);
		sheet.setColumnWidth(9, 3500);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(11, 2500);
		sheet.setColumnWidth(12, 2500);
		/*
		 * 
		 * sheet.autoSizeColumn((short) 4); sheet.autoSizeColumn((short) 5);
		 * sheet.autoSizeColumn((short) 6); sheet.autoSizeColumn((short) 7);
		 */
		/*
		 * //首行文字格式 cellStyle= workbook.createCellStyle(); HSSFFont f =
		 * workbook.createFont(); f.setFontHeightInPoints((short) 16);// 字号
		 * f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗 cellStyle.setFont(f);
		 * header1.getCell(0).setCellStyle(cellStyle);
		 * 
		 * cellStyle= workbook.createCellStyle(); f = workbook.createFont();
		 * f.setFontHeightInPoints((short) 12);// 字号
		 * f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		 * cellStyle.setFont(f); header.setRowStyle(cellStyle);
		 */
	}

}
