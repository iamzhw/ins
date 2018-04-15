package com.system.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * 我的工单批量导出处理类
 *
 * @author lbhu
 * @since 2014-10-28
 *
 */
@SuppressWarnings("all")
public class InsOrderDataListExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		String name = (String) model.get("name");
		response.setHeader("Content-Disposition", "inline; filename="
				+ new String((name + "列表.xls").getBytes("gb2312"), "iso8859-1"));
		List<Map> dataList = (List<Map>) model.get("dataList");//获取数据列表对象
		String[] cols = (String[]) model.get("cols");//获取所有列标题
		String[] columnMethods = (String[]) model.get("columnMethods");//获取列对应字段名称
		HSSFSheet sheet = workbook.createSheet(name);//创建sheet页
		
		//字体样式 - 标题样式 加粗居中 
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBoldweight((short) 2000);
		titleStyle.setFont(font);

		//生成excel标题
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		for (int i = 0; i < cols.length; i++) {
			cell = row.createCell(i); // 创建第i列
			cell.setCellStyle(titleStyle);
			cell.setCellValue(new HSSFRichTextString(cols[i]));
		}

		//生成Excel中的数据
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> record = dataList.get(i);
			row = sheet.createRow(i + 1);//创建单元格

			//生成字段数据
			for (int j = 0; j < columnMethods.length; j++) {
				Object obj = record.get(columnMethods[j]);
				cell = row.createCell(j);
				cell.setCellValue(new HSSFRichTextString(obj == null ? "" : obj.toString()));

			}
		}
	}

}
