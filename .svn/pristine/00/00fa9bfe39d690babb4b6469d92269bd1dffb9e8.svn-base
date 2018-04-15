/**
 * @Description: TODO
 * @date 2015-3-26
 * @param
 */
package com.axxreport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.linePatrol.util.DateUtil;

/**
 * @author Administrator
 * 
 */
public class ExcelUtil {

	
	public static void generateExcelAndDownload(List<List<String>> title, List<List<String>> code,
		    List<List<Map<String, Object>>> data, HttpServletRequest request,
		    HttpServletResponse response, String fName, String firstLine,int sheetNum)
		    throws FileNotFoundException {

		String filePath = request.getSession().getServletContext().getRealPath("/excelFiles")
			+ File.separator + DateUtil.getNameAccordDate() + ".xlsx";
		File file = new File(filePath);
		OutputStream out = new FileOutputStream(new File(filePath));

		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		for (int i = 0; i < sheetNum; i++) {
			createSheet(title.get(i), code.get(i), 
					data.get(i), firstLine, workbook);	
		}

		try {
		    workbook.write(out);
		    out.close();

		    String fileName = fName + DateUtil.getNameAccordDate();

		    DownloadUtil.download(filePath, fileName, request, response);

		    // 删除文件
		    file.delete();

		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    if (out != null) {
			try {
			    out.close();
			} catch (IOException e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
		    }
		}

	 }



	private static void createSheet(List<String> title, List<String> code, List<Map<String, Object>> data,
			String firstLine, XSSFWorkbook workbook) {
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);

		// 第一行样式
		XSSFCellStyle firstlineStyle = workbook.createCellStyle();
		// 设置这些样式
		firstlineStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		firstlineStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		firstlineStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		firstlineStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.RED.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		firstlineStyle.setFont(font);
		// 标题样式
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		// 设置这些样式
		titleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

		// 生成一个字体
		XSSFFont font2 = workbook.createFont();
		// font2.setColor(HSSFColor.RED.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		titleStyle.setFont(font2);

		// 数据样式
		XSSFCellStyle dataStyle = workbook.createCellStyle();

		dataStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

		// 合并第一行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		XSSFRow row = sheet.createRow(0);
		row.setHeight((short) 450);
		XSSFCell cell = null;
		cell = row.createCell(0);
		firstlineStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		cell.setCellStyle(firstlineStyle);

		XSSFRichTextString text = new XSSFRichTextString(firstLine);
		cell.setCellValue(text);

		// 产生表格标题行
		int startRow = 1;

		row = sheet.createRow(startRow);

		for (short i = 0; i < title.size(); i++) {
		    cell = row.createCell(i);

		    cell.setCellStyle(titleStyle);

		    text = new XSSFRichTextString(title.get(i));
		    cell.setCellValue(text);
		}
		startRow++;
		// 数据行
		String value = null;
		Double val= null;
		for (int i = 0; i < data.size(); i++) {

		    row = sheet.createRow(startRow);

		    Map<String, Object> map = data.get(i);
		    for (int j = 0; j < code.size(); j++) {
			cell = row.createCell(j);
			cell.setCellStyle(dataStyle);
			value = map.get(code.get(j)) == null ? "" : map.get(code.get(j)).toString();
//			text = new XSSFRichTextString(value);
				try {
//					val = Double.parseDouble(value);
//					cell.setCellValue(val);
					cell.setCellValue(value);
				} catch (NumberFormatException e) {
					cell.setCellValue(value);
				}
		    }
		    startRow++;
		}
	}
	
	
	
    public static void generateExcelAndDownload(List<String> title, List<String> code,
	    List<Map<String, Object>> data, HttpServletRequest request,
	    HttpServletResponse response, String fName, String firstLine)
	    throws FileNotFoundException {
	// TODO Auto-generated method stub

	String filePath = request.getSession().getServletContext().getRealPath("/excelFiles")
		+ File.separator + DateUtil.getNameAccordDate() + ".xlsx";
	File file = new File(filePath);
	OutputStream out = new FileOutputStream(new File(filePath));

	// 声明一个工作薄
	XSSFWorkbook workbook = new XSSFWorkbook();
	createSheet(title, code, data, firstLine, workbook);

	try {
	    workbook.write(out);
	    out.close();

	    String fileName = fName + DateUtil.getNameAccordDate();

	    DownloadUtil.download(filePath, fileName, request, response);

	    // 删除文件
	    file.delete();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    if (out != null) {
		try {
		    out.close();
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	}

    }

    public static void writeToModalAndDown(List<String> code, List<Map<String, Object>> data,
	    HttpServletRequest request, HttpServletResponse response, String fileName,
	    String modalPath, int startRow, String firstline) {

	XSSFWorkbook wb = null;
	InputStream inp = null;
	OutputStream out = null;
	try {

	    // 读取样板
	    inp = new FileInputStream(modalPath);
	    wb = new XSSFWorkbook(inp);
	    XSSFSheet sheet = wb.getSheetAt(0);

	    if (data != null && !data.isEmpty()) {

		// 第一行样式
		XSSFCellStyle firstlineStyle = wb.createCellStyle();
		// 设置这些样式
		firstlineStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		firstlineStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		firstlineStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		firstlineStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

		// 生成一个字体
		XSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		firstlineStyle.setFont(font);

		// 标题样式
		XSSFCellStyle titleStyle = wb.createCellStyle();
		// 设置这些样式
		titleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

		// 生成一个字体
		XSSFFont font2 = wb.createFont();
		// font2.setColor(HSSFColor.RED.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		titleStyle.setFont(font2);

		XSSFCellStyle dataStyle = wb.createCellStyle();

		dataStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

		String value = null;
		XSSFRow row = null;
		XSSFCell cell = null;

		// 第一行
		row = sheet.getRow(0);
		cell = row.getCell(0);
		firstlineStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		cell.setCellStyle(firstlineStyle);

		cell.setCellValue(firstline);

		// 数据
		for (short i = 0; i < data.size(); i++) {
		    row = sheet.createRow(startRow);

		    Map<String, Object> map = data.get(i);
					for (int j = 0; j < code.size(); j++) {
						cell = row.createCell(j);
						cell.setCellStyle(dataStyle);
						value = map.get(code.get(j)) == null ? null : map.get(
								code.get(j)).toString();
						// text = new XSSFRichTextString(value);
						int val;
						try {
							val = Integer.valueOf(value);
							cell.setCellValue(val);
						} catch (Exception e) {
							cell.setCellValue(value);
						}
					}
		    startRow++;
		}

		//
		String tempFilePath = request.getSession().getServletContext()
			.getRealPath("/excelFiles")
			+ File.separator + DateUtil.getNameAccordDate() + ".xlsx";
		File file = new File(tempFilePath);
		out = new FileOutputStream(new File(tempFilePath));

		wb.write(out);

		DownloadUtil.download(tempFilePath, fileName, request, response);

		// 删除文件
		file.delete();

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (null != inp) {
		    inp.close();
		}

	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    try {
		if (null != out) {
		    out.close();
		}

	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

    }
    
}
