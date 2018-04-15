package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.ReviewDao;
import com.cableCheck.service.ReviewService;

@SuppressWarnings("all")
@Service
public class ReviewServiceImpl implements ReviewService {
	Logger logger = Logger.getLogger(ReviewServiceImpl.class);
	
	@Resource
	private ReviewDao reviewDao;
	@Override
	public Map<String, Object> queryReviewRecords(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String area_id = request.getParameter("AREA_ID");//地市
		String son_area_id = request.getParameter("SON_AREA_ID");//区域
		String review_start_time = request.getParameter("REVIEW_START_TIME");//复查开始时间
		String review_complete_time = request.getParameter("REVIEW_COMPLETE_TIME");//复查结束时间
		String INSPECTOR = request.getParameter("INSPECTOR");//检查员
		
		map.put("area_id", area_id);
		map.put("son_area_id", son_area_id);
		map.put("review_start_time", review_start_time);	
		map.put("review_complete_time", review_complete_time);
		map.put("INSPECTOR", INSPECTOR);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String,Object>> reviewrecord=reviewDao.query(query);
		
		logger.info("【现场复查统计】："+reviewrecord);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", reviewrecord);
		
		return pmap;
		
	}
	@Override
	public Map<String, Object> queryReviewDetailRecords(
			HttpServletRequest request, UIPage pager) {
		Map map = new HashMap();
		String area_id = request.getParameter("AREA_ID");//地市
		String son_area_id = request.getParameter("SON_AREA_ID");//区域
		String review_start_time = request.getParameter("REVIEW_START_TIME");//复查开始时间
		String review_complete_time = request.getParameter("REVIEW_COMPLETE_TIME");//复查结束时间
		String INSPECTOR = request.getParameter("INSPECTOR");//检查员
		
		map.put("area_id", area_id);
		map.put("son_area_id", son_area_id);
		map.put("review_start_time", review_start_time);	
		map.put("review_complete_time", review_complete_time);
		map.put("INSPECTOR", INSPECTOR);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String,Object>> reviewrecord=reviewDao.queryDetailRecords(query);
		
		logger.info("【现场复查清单列表】："+reviewrecord);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", reviewrecord);
		
		return pmap;
	}
	@Override
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		String area_id = request.getParameter("AREA_ID");//地市
		String son_area_id = request.getParameter("SON_AREA_ID");//区域
		String review_start_time = request.getParameter("REVIEW_START_TIME");//复查开始时间
		String review_complete_time = request.getParameter("REVIEW_COMPLETE_TIME");//复查结束时间
		String INSPECTOR = request.getParameter("INSPECTOR");//复查结束时间
		
		map.put("area_id", area_id);
		map.put("son_area_id", son_area_id);
		map.put("review_start_time", review_start_time);	
		map.put("review_complete_time", review_complete_time);
		map.put("INSPECTOR", INSPECTOR);
		
		List<Map<String,Object>> reviewrecord=reviewDao.exportExcel(map);
		
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","检查员","复查端子数","复查一致端子数 ", "自查准确率"});
		List<String> code = Arrays.asList(new String[] { "CITYNAME","NAME","STAFF_NAME",
				"REVIEWRECORDS", "SAMERECORDS", "CHECK_REVIEW"});
	
		String fileName = "现场复查统计";
		String firstLine = "现场复查统计";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, reviewrecord, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void exportDetailExcel(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		String area_id = request.getParameter("AREA_ID");//地市
		String son_area_id = request.getParameter("SON_AREA_ID");//区域
		String review_start_time = request.getParameter("REVIEW_START_TIME");//复查开始时间
		String review_complete_time = request.getParameter("REVIEW_COMPLETE_TIME");//复查结束时间
		String INSPECTOR = request.getParameter("INSPECTOR");//复查结束时间
		map.put("area_id", area_id);
		map.put("son_area_id", son_area_id);
		map.put("review_start_time", review_start_time);	
		map.put("review_complete_time", review_complete_time);
		map.put("INSPECTOR", INSPECTOR);
		
		List<Map<String,Object>> reviewrecord=reviewDao.exportDetailExcel(map);
		
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","设备名称","设备编码","设备类型 ", "端子编码","检查人员", "检查时间 ","检查结果","复查人员","复查时间", "复查结果","是否一致 "});
		List<String> code = Arrays.asList(new String[] { "CITYNAME","NAME","EQPNAME","EQPNO", "SBLX", "DZNO","CHECKSTAFF", "CHECKTIME", 
				"CHECK_ISCHECKOK","REVIEWSTAFF", "REVIEWTIME", "REVIEW_ISCHECKOK", "CHECK_REVIEW_RESULT"});
	
		String fileName = "现场复查清单";
		String firstLine = "现场复查清单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, reviewrecord, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


}
