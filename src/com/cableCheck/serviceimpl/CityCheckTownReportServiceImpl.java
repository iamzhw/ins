package com.cableCheck.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.DownloadUtil;
import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CityCheckTownReportDao;
import com.cableCheck.dao.WrongPortReportDao;
import com.cableCheck.service.CityCheckTownReportService;
import com.linePatrol.util.DateUtil;
import com.system.dao.StaffDao;


@SuppressWarnings("all")
@Transactional
@Service
public class CityCheckTownReportServiceImpl implements CityCheckTownReportService {

	@Resource
	private CityCheckTownReportDao cityCheckTownReportDao;
	@Autowired
	private StaffDao staffDao;
	@Override
	public Map<String, Object> query(HttpServletRequest request,
			UIPage pager) {
		Map paramMap = new HashMap();
		String staffId = request.getSession().getAttribute("staffId").toString();
		/**
		 * 请求参数
		 */
		
		String area_id = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String static_month = request.getParameter("static_month");
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
		String pstart_time = request.getParameter("PSTART_TIME");
		int ifGly = staffDao.getifGly(staffId);
		/**
		 * 查询参数赋值
		 */
		
	
		paramMap.put("staff_id", staffId);
		paramMap.put("AREA_ID", area_id);
		paramMap.put("son_area", son_area);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("static_month", static_month);
		
		/*List<Map<String,Object>> list = staffDao.getRoleList(staffId);
		String flag = "";
		for(Map map : list){
			String role_id = map.get("ROLE_ID").toString();
			if("266".equals(role_id)){
				flag = "266";
				break;
			}
		}
		paramMap.put("ROLE_ID", flag);*/
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> cityCheckTownReportList = cityCheckTownReportDao.query(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", cityCheckTownReportList);
		return resultMap;
	}
	@Override
	public void cityCheckTownReportDownload(HttpServletRequest request,
	HttpServletResponse response) {
		
		Map<String, Object> paras = new HashMap<String, Object>();
		String AREA_ID = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String START_TIME = request.getParameter("START_TIME");
		String COMPLETE_TIME = request.getParameter("COMPLETE_TIME");
		String static_month = request.getParameter("static_month");
		String PSTART_TIME = request.getParameter("PSTART_TIME");
		String PCOMPLETE_TIME = request.getParameter("PCOMPLETE_TIME");
		paras.put("AREA_ID",AREA_ID );
		paras.put("son_area",son_area );
		paras.put("START_TIME",START_TIME );
		paras.put("COMPLETE_TIME", COMPLETE_TIME);
		paras.put("static_month",static_month);
		paras.put("PSTART_TIME",PSTART_TIME);
		paras.put("PCOMPLETE_TIME",PCOMPLETE_TIME );
	
		List<Map<String, Object>> data = cityCheckTownReportDao.queryDown(paras);

		String fileName = "市对县检查报告";
		String firstLine = "市对县检查报告";
		XSSFWorkbook wb = null;
		InputStream inp = null;
		OutputStream out = null;
		List<String> code = null;
			code = Arrays.asList(new String[] { "NAME", "NUM1",
					"NUM2", "NUM3", "NUM4", "NUM5", "NUM6", "NUM7", "NUM8","NUM9",  "NUM10", "N1",
					"NUM11", "NUM12", "NUM13", "NUM14", "NUM15", "NUM16" });

		try {

			
			// 读取样板
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			String modalPath = null;
		
				modalPath = rootPath + File.separator + "excelFiles"
						+ File.separator + "cityCheckTown.xlsx";
			

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
	@Override
    public List<Map<String, String>> getSonAreaList(String area_id) {

    	return cityCheckTownReportDao.getSonAreaList(area_id);
    }

}
