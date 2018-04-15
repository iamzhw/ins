/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.DownloadUtil;
import com.axxreport.util.ExcelUtil;
import com.linePatrol.dao.DangerOrderDao;
import com.linePatrol.service.DangerOrderService;
import com.linePatrol.util.DateUtil;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class DangerOrderServiceImpl implements DangerOrderService {

	@Resource
	private DangerOrderDao dangerOrderDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.dangerOrderService#query(java.util.Map,
	 * util.page.UIPage)
	 */
	@Override
	public List<Map<String, Object>> query(Query query) {

/*		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_status", para.get("order_status"));
		map.put("org_id", para.get("org_id"));
		map.put("danger_question", para.get("danger_question"));
		map.put("area_id", para.get("areaId"));
		map.put("start_time", para.get("start_time"));
		map.put("end_time", para.get("end_time"));
		map.put("user_id", para.get("user_id"));*/

		List<Map<String, Object>> olists = dangerOrderDao.query(query);

		return olists;
		

	}

	@Override
	public List<Map<String, Object>> getScopeList(Map<String, Object> para) {
		return dangerOrderDao.getScopeList(para);
	}

	/*
	 * 
	 * 隐患单管理页面数据导出
	 */
	@Override
	public void dataDownload(Map<String, Object> para2,
			HttpServletRequest request, HttpServletResponse response) {
		// String order_status=para2.get("order_status").toString();
		// String danger_question=para2.get("danger_question").toString();

		List<String> title = Arrays.asList(new String[] { "隐患描述 ", "发现人",
				"发现时间 ", "隐患单状态 " });
		List<String> code = Arrays.asList(new String[] { "DANGER_QUESTION",
				"FOUNDER_NAME", "FOUND_TIME", "ORDER_STATUS" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_status", para2.get("order_status"));
		map.put("org_id", para2.get("org_id"));
		map.put("danger_question", para2.get("danger_question"));
		map.put("area_id", para2.get("areaId"));
		map.put("start_time", para2.get("start_time"));
		map.put("end_time", para2.get("end_time"));
		map.put("user_id", para2.get("user_id"));
		
		List<Map<String, Object>> data = dangerOrderDao.dataDownload(map);

		String fileName = "隐患单管理报表";
		String firstLine = "隐患单管理报表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 隐患单删除
	 */

	public void deleteDanger(int ids) {

		dangerOrderDao.deleteDanger(ids);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.dangerOrderService#save(java.util.Map)
	 */
	@Override
	public void dangerOrderSave(Map<String, Object> para) {
		// TODO Auto-generated method stub
		// 保存隐患点
		String creation_time = DateUtil.getDateAndTime();
		para.put("creation_time", creation_time);

		String danger_id = dangerOrderDao.getDangerId();
		para.put("danger_id", danger_id);
		dangerOrderDao.saveDanger(para);

		// 保存隐患单
		dangerOrderDao.dangerOrderSave(para);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.dangerOrderService#update(java.util.Map)
	 */
	@Override
	public void dangerOrderUpdate(Map<String, Object> para) {
		// TODO Auto-generated method stub
		dangerOrderDao.dangerOrderUpdate(para);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.dangerOrderService#delete(java.util.Map)
	 */
	@Override
	public void dangerOrderDelete(String ids) {
		// TODO Auto-generated method stub

		String[] idsArray = ids.split(",");
		for (int i = 0; i < idsArray.length; i++) {
			dangerOrderDao.dangerOrderDelete(idsArray[i]);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.dangerOrderService#editUI(java.lang.String)
	 */
	@Override
	public Map<String, Object> findById(String id) {
		// TODO Auto-generated method stub
		return dangerOrderDao.findById(id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		// TODO Auto-generated method stub
		return dangerOrderDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.DangerOrderService#getAllDangerOrderState()
	 */
	@Override
	public List<Map<String, Object>> getAllDangerOrderState() {
		// TODO Auto-generated method stub
		return dangerOrderDao.getAllDangerOrderState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.DangerOrderService#getSeq()
	 */
	@Override
	public String getDangerId() {
		// TODO Auto-generated method stub

		return "GZ" + dangerOrderDao.getDangerId();
	}

	public static void main(String[] args) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.DangerOrderService#findHandlePerson()
	 */
	@Override
	public Map<String, Object> findHandlePerson() {
		// TODO Auto-generated method stub

		List<Map> handlePerson = dangerOrderDao.findHandlePerson();
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", handlePerson.size());
		pmap.put("rows", handlePerson);
		return pmap;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.linePatrol.service.DangerOrderService#dangerOrderDistribute(java.
	 * util.Map)
	 */
	@Override
	public void dangerOrderDistribute(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String ids = para.get("ids").toString();
		String handlePersonId = para.get("handlePersonId").toString();

		String[] idsArray = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("handle_person", handlePersonId);
		map.put("distribute_time", DateUtil.getDateAndTime());
		map.put("distribute_person", para.get("distribute_person"));

		map.put("order_status", 2);// 已派发 待回执

		for (int i = 0; i < idsArray.length; i++) {
			String dangerOrderId = idsArray[i];
			map.put("order_id", dangerOrderId);
			dangerOrderDao.dangerOrderUpdate(map);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.DangerOrderService#receipt(java.lang.String)
	 */
	@Override
	public void receipt(String ids) {
		// TODO Auto-generated method stub
		String[] idsArray = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_status", 2);// 2已处理 待审核
		for (int i = 0; i < idsArray.length; i++) {
			map.put("order_id", idsArray[i]);
			dangerOrderDao.dangerOrderUpdate(map);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.DangerOrderService#audit(java.util.Map)
	 */
	@Override
	public void audit(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String audit_time = para.get("audit_time").toString();
		if (audit_time != null && audit_time.length() != 0) {
			para.put("audit_time", para.get("audit_time"));
		} else {
			para.put("audit_time", DateUtil.getDateAndTime());
		}
		// para.put("audit_time", DateUtil.getDateAndTime());
		para.put("order_status", 3);// 3已审核
		dangerOrderDao.dangerOrderUpdate(para);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.linePatrol.service.DangerOrderService#findDetail(java.lang.String)
	 */
	@Override
	public Map<String, Object> findDetail(String order_id) {
		// TODO Auto-generated method stub

		Map<String, Object> map = dangerOrderDao.findDetail(order_id);

		return map;

	}

	@Override
	public Map<String, Object> doStatistic(Map<String, Object> map, UIPage pager) {
		// TODO Auto-generated method stub

		String static_month = map.get("static_month").toString();
		String[] static_monthArr = static_month.split("-");
		int year = Integer.parseInt(static_monthArr[0]);
		int month = Integer.parseInt(static_monthArr[1]);

		String firstSaturday = getFirstSaturdayOfMonth(year, month);
		String lastSaturday = getLastSaturdayOfMonth(year, month);

		String[] fa = firstSaturday.split("-");
		String[] la = lastSaturday.split("-");

		int fai = Integer.parseInt(fa[2]);
		int lai = Integer.parseInt(la[2]);

		int count = (lai - fai) / 7 + 1;

		Date fsDate = null;
		try {
			fsDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstSaturday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.put("count", count);

		List<String> title = new ArrayList<String>();
		title.add("发现人");

		for (int i = 0; i < count; i++) {
			Date startDate = fsDate;
			Date endDate = new Date(fsDate.getTime() + 6L * 24 * 60 * 60 * 1000);
			map.put("startDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(startDate));
			map.put("endDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(endDate));

			fsDate = new Date(fsDate.getTime() + 7L * 24 * 60 * 60 * 1000);

			String tt = new SimpleDateFormat("MM月dd日").format(startDate) + "-"
					+ new SimpleDateFormat("MM月dd日").format(endDate);
			title.add(tt);
		}

		List<Map<String, Object>> olists = null;
		if (count == 4) {
			olists = dangerOrderDao.doStatistic4(map);
		} else {
			olists = dangerOrderDao.doStatistic5(map);
		}

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("title", title);
		pmap.put("data", olists);
		pmap.put("count", count);
		return pmap;

	}

	public static String getFirstSaturdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1); // 设为第一天

		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			cal.add(Calendar.DATE, 1);
		}
		// return cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static String getLastSaturdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1); // 设为第一天

		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			cal.add(Calendar.DATE, 1);
		}
		cal.add(Calendar.DATE, -7);
		// return cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	@Override
	public void paidan(Map<String, Object> para) {

		para.put("distribute_time", DateUtil.getDateAndTime());
		para.put("order_status", 1);// 1为已派单 待处理

		dangerOrderDao.dangerOrderUpdate(para);

	}

	@Override
	public String getDangerIdByOrder(String order_id) {
		// TODO Auto-generated method stub
		return dangerOrderDao.getDangerIdByOrder(order_id);
	}

	@Override
	public void doDownload(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {

		String static_month = map.get("static_month").toString();
		String[] static_monthArr = static_month.split("-");
		int year = Integer.parseInt(static_monthArr[0]);
		int month = Integer.parseInt(static_monthArr[1]);

		String firstSaturday = getFirstSaturdayOfMonth(year, month);
		String lastSaturday = getLastSaturdayOfMonth(year, month);

		String[] fa = firstSaturday.split("-");
		String[] la = lastSaturday.split("-");

		int fai = Integer.parseInt(fa[2]);
		int lai = Integer.parseInt(la[2]);

		int count = (lai - fai) / 7 + 1;

		Date fsDate = null;
		try {
			fsDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstSaturday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.put("count", count);

		List<String> title = new ArrayList<String>();
		title.add("发现人");

		for (int i = 0; i < count; i++) {
			Date startDate = fsDate;
			Date endDate = new Date(fsDate.getTime() + 6L * 24 * 60 * 60 * 1000);
			map.put("startDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(startDate));
			map.put("endDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(endDate));

			fsDate = new Date(fsDate.getTime() + 7L * 24 * 60 * 60 * 1000);

			String tt = new SimpleDateFormat("MM月dd日").format(startDate) + "-"
					+ new SimpleDateFormat("MM月dd日").format(endDate);
			title.add(tt);
		}

		List<Map<String, Object>> data = null;
		if (count == 4) {
			data = dangerOrderDao.doStatistic4(map);
		} else {
			data = dangerOrderDao.doStatistic5(map);
		}

		List<String> code = null;
		if (count == 4) {
			code = Arrays.asList(new String[] { "FOUNDER_NAME", "W1", "W2",
					"W3", "W4" });
		} else {
			code = Arrays.asList(new String[] { "FOUNDER_NAME", "W1", "W2",
					"W3", "W4", "W5" });
		}

		String fileName = static_month + "隐患单报表";
		String firstLine = "隐患单报表";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> searchByAdmin(Map<String, Object> para) {
		String static_month = para.get("static_month").toString();
		String[] static_monthArr = static_month.split("-");
		int year = Integer.parseInt(static_monthArr[0]);
		int month = Integer.parseInt(static_monthArr[1]);

		String firstSaturday = getFirstSaturdayOfMonth(year, month);
		String lastSaturday = getLastSaturdayOfMonth(year, month);

		String[] fa = firstSaturday.split("-");
		String[] la = lastSaturday.split("-");

		int fai = Integer.parseInt(fa[2]);
		int lai = Integer.parseInt(la[2]);

		int count = (lai - fai) / 7 + 1;

		Date fsDate = null;
		try {
			fsDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstSaturday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		para.put("count", count);

		List<String> title = new ArrayList<String>();
		title.add("发现人");

		for (int i = 0; i < count; i++) {
			Date startDate = fsDate;
			Date endDate = new Date(fsDate.getTime() + 6L * 24 * 60 * 60 * 1000);
			para.put("startDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(startDate));
			para.put("endDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(endDate));

			fsDate = new Date(fsDate.getTime() + 7L * 24 * 60 * 60 * 1000);

			String tt = new SimpleDateFormat("MM月dd日").format(startDate) + "-"
					+ new SimpleDateFormat("MM月dd日").format(endDate);
			title.add(tt);
		}

		List<Map<String, Object>> olists = null;
		if (count == 4) {
			olists = dangerOrderDao.searchByAdmin4(para);
		} else {
			olists = dangerOrderDao.searchByAdmin5(para);
		}

		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("title", title);
		pmap.put("data", olists);
		pmap.put("count", count);
		return pmap;
	}

	@Override
	public void downloadByAdmin(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		String static_month = para.get("static_month").toString();
		String[] static_monthArr = static_month.split("-");
		int year = Integer.parseInt(static_monthArr[0]);
		int month = Integer.parseInt(static_monthArr[1]);

		String firstSaturday = getFirstSaturdayOfMonth(year, month);
		String lastSaturday = getLastSaturdayOfMonth(year, month);

		String[] fa = firstSaturday.split("-");
		String[] la = lastSaturday.split("-");

		int fai = Integer.parseInt(fa[2]);
		int lai = Integer.parseInt(la[2]);

		int count = (lai - fai) / 7 + 1;

		Date fsDate = null;
		try {
			fsDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstSaturday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		para.put("count", count);
		// List<String> title = new ArrayList<String>();
		String[] title = new String[5];

		for (int i = 0; i < count; i++) {
			Date startDate = fsDate;
			Date endDate = new Date(fsDate.getTime() + 6L * 24 * 60 * 60 * 1000);
			para.put("startDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(startDate));
			para.put("endDate" + i, new SimpleDateFormat("yyyy-MM-dd")
					.format(endDate));

			fsDate = new Date(fsDate.getTime() + 7L * 24 * 60 * 60 * 1000);

			String tt = new SimpleDateFormat("MM月dd日").format(startDate) + "-"
					+ new SimpleDateFormat("MM月dd日").format(endDate);
			title[i] = tt;
		}

		List<Map<String, Object>> data = null;
		if (count == 4) {
			data = dangerOrderDao.searchByAdmin4(para);
		} else {
			data = dangerOrderDao.searchByAdmin5(para);
		}

		List<String> code = null;
		if (count == 4) {
			code = Arrays.asList(new String[] { "AREA_NAME", "INSPACTOR_COUNT",
					"W1", "W11", "kf", "W2", "W22", "kf", "W3", "W33", "kf",
					"W4", "W44", "kf" });
		} else {
			code = Arrays.asList(new String[] { "AREA_NAME", "INSPACTOR_COUNT",
					"W1", "W11", "kf", "W2", "W22", "kf", "W3", "W33", "kf",
					"W4", "W44", "kf", "W5", "W55", "kf" });
		}

		String fileName = static_month + "全省隐患单报表";
		String firstLine = "隐患单报表";

		// ====生成excel===///
		// Workbook wb=null;
		InputStream inp = null;
		XSSFWorkbook wb = null;
		OutputStream out = null;
		try {

			// 读取样板
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			String modalPath = null;
			if (count == 4) {
				modalPath = rootPath + File.separator + "excelFiles"
						+ File.separator + "dangerOrder4.xlsx";
			} else {
				modalPath = rootPath + File.separator + "excelFiles"
						+ File.separator + "dangerOrder5.xlsx";
			}

			inp = new FileInputStream(modalPath);
			wb = new XSSFWorkbook(inp);
			XSSFSheet sheet = wb.getSheetAt(0);

			if (data != null && !data.isEmpty()) {

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

				// 标题
				XSSFRow titleRow = sheet.getRow(1);

				titleRow.getCell(2).setCellValue(title[0]);
				titleRow.getCell(5).setCellValue(title[1]);
				titleRow.getCell(8).setCellValue(title[2]);
				titleRow.getCell(11).setCellValue(title[3]);
				if (count == 5) {
					titleRow.getCell(14).setCellValue(title[4]);
				}

				// 数据
				String value = null;
				int startRow = 3;
				XSSFRow row = null;
				XSSFCell cell = null;

				for (short i = 0; i < data.size(); i++) {
					row = sheet.createRow(startRow);

					Map<String, Object> map = data.get(i);
					for (int j = 0; j < code.size(); j++) {
						cell = row.createCell(j);
						cell.setCellStyle(dataStyle);

						value = map.get(code.get(j)) == null ? null : map.get(
								code.get(j)).toString();
						// text = new XSSFRichTextString(value);

						cell.setCellValue(value);
					}
					startRow++;
				}

				//
				String tempFilePath = request.getSession().getServletContext()
						.getRealPath("/excelFiles")
						+ File.separator
						+ DateUtil.getNameAccordDate()
						+ ".xlsx";
				File file = new File(tempFilePath);
				out = new FileOutputStream(new File(tempFilePath));

				wb.write(out);

				DownloadUtil
						.download(tempFilePath, fileName, request, response);

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
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
