package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CheckQualityReportDao;
import com.cableCheck.dao.WrongPortReportDao;
import com.cableCheck.service.CheckQualityReportService;
import com.cableCheck.service.WrongPortReportService;
import com.system.dao.StaffDao;


	@SuppressWarnings("all")
	@Transactional
	@Service
	
	public class WrongPortReportServiceImpl implements WrongPortReportService {

		@Resource
		private WrongPortReportDao wrongPortReportDao;
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
			List<Map<String, Object>> wrongPortReportList = wrongPortReportDao.query(query);
			
			Map<String, Object> resultMap = new HashMap<String, Object>(0);
			resultMap.put("total", query.getPager().getRowcount());
			resultMap.put("rows", wrongPortReportList);
			return resultMap;
		}
		
		@Override
		public List<Map<String, Object>> selArea() {
			// TODO Auto-generated method stub
			return  wrongPortReportDao.selArea();
		}

		@Override
		public void wrongPortReportDownload(HttpServletRequest request,
				HttpServletResponse response) {
			List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","设备编码",
					"端子编码 ", "上报描述", "检查时间", "端子变动时间","光路编号","光路名称","施工工号","光路性质"});
			List<String> code = Arrays.asList(new String[] { "CITY","TOWN","EQP_NO",
					"PORT_NO", "DESCRIPT", "CREATE_TIME", "BDSJ" ,"GLBH ","GLMC","SGGH","NAME"});
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
		
			List<Map<String, Object>> data = wrongPortReportDao.queryDown(paras);

			String fileName = "错误单子检查报告";
			String firstLine = "错误端子检查报告";

			try {
				ExcelUtil.generateExcelAndDownload(title, code, data, request,
						response, fileName, firstLine);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public Map<String, Object> queryPersonalQuality(
				HttpServletRequest request, UIPage pager) {
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
			String ISCHECK= request.getParameter("ISCHECK");
			String STAFFNAME= request.getParameter("STAFFNAME");
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
			paramMap.put("ISCHECK", ISCHECK);
			paramMap.put("STAFFNAME", STAFFNAME);
			
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
			List<Map<String, Object>> queryPersonalQualityList = wrongPortReportDao.queryPersonalQuality(query);
			
			Map<String, Object> resultMap = new HashMap<String, Object>(0);
			resultMap.put("total", query.getPager().getRowcount());
			resultMap.put("rows", queryPersonalQualityList);
			return resultMap;
		}

		@Override
		public void exportExcelPersonalCheckDownload(
				HttpServletRequest request, HttpServletResponse response) {
			List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","姓名",
					 "检查正确端子数", "检查端子数", "检查准确率","整改成功端子数（通过审核）","整改端子数","整改成功率"});
			List<String> code = Arrays.asList(new String[] { "CITY","TOWN","STAFF_NAME",
					 "NUM", "NUM1", "PERCENT1" ,"NUM2","NUM3","PERCENT2"});
			Map<String, Object> paras = new HashMap<String, Object>();
			String AREA_ID = request.getParameter("AREA_ID");
			String son_area = request.getParameter("son_area");
			String START_TIME = request.getParameter("START_TIME");
			String COMPLETE_TIME = request.getParameter("COMPLETE_TIME");
			String static_month = request.getParameter("static_month");
			String PSTART_TIME = request.getParameter("PSTART_TIME");
			String PCOMPLETE_TIME = request.getParameter("PCOMPLETE_TIME");
			String ISCHECK= request.getParameter("ISCHECK");
			String STAFFNAME= request.getParameter("STAFFNAME");
			paras.put("AREA_ID",AREA_ID );
			paras.put("son_area",son_area );
			paras.put("START_TIME",START_TIME );
			paras.put("COMPLETE_TIME", COMPLETE_TIME);
			paras.put("static_month",static_month);
			paras.put("PSTART_TIME",PSTART_TIME);
			paras.put("PCOMPLETE_TIME",PCOMPLETE_TIME );
			paras.put("ISCHECK", ISCHECK);
			paras.put("STAFFNAME", STAFFNAME);
			List<Map<String, Object>> data = wrongPortReportDao.personalCheckDown(paras);

			String fileName = "个人检查报告";
			String firstLine = "个人检查报告";

			try {
				ExcelUtil.generateExcelAndDownload(title, code, data, request,
						response, fileName, firstLine);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		

	}


