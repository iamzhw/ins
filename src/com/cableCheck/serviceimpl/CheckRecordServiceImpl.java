package com.cableCheck.serviceimpl;

import icom.cableCheck.dao.CheckPortDao;
import icom.cableCheck.serviceimpl.CheckTaskServiceImpl;
import icom.system.dao.CableInterfaceDao;
import icom.util.Result;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CheckProcessDao;
import com.cableCheck.dao.CheckRecordDao;
import com.cableCheck.dao.TaskMangerDao;
import com.cableCheck.service.CheckRecordService;
import com.cableCheck.service.TaskMangerService;
import com.linePatrol.util.DateUtil;
import com.webservice.client.ElectronArchivesService;

@SuppressWarnings("all")
@Service
public class CheckRecordServiceImpl implements CheckRecordService {
	Logger logger = Logger.getLogger(CheckRecordServiceImpl.class);
	
	@Resource
	private CheckRecordDao checkRecordDao;
	
	/**
	 * 查询端子所在设备记录列表
	 */
	@Override
	public Map<String, Object> queryCheckRecord(HttpServletRequest request, UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();

		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String static_month = request.getParameter("static_month");//端子变动月份
		String dz_start_time = request.getParameter("dz_start_time");//端子动态开始时间
		String dz_end_time = request.getParameter("dz_end_time");//端子动态结束时间
		String check_start_time = request.getParameter("check_start_time");
		String check_end_time = request.getParameter("check_end_time");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("whwg", whwg);
		map.put("static_month", static_month);
		map.put("dz_start_time", dz_start_time);
		map.put("dz_end_time", dz_end_time);
		map.put("check_start_time", check_start_time);
		map.put("check_end_time", check_end_time);
		map.put("RES_TYPE_ID", RES_TYPE_ID);
			
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = checkRecordDao.queryCheckRecord(query);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	
	/**
	 * 查询同种端子检查记录情况
	 */
	@Override
	public Map<String, Object> querySamePortRecord(HttpServletRequest request,
			UIPage pager) {
		Map<String, Object> map = new HashMap<String, Object>();

		String area = request.getParameter("area");
		String sonArea = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String dz_start_time = request.getParameter("dz_start_time");
		String dz_end_time = request.getParameter("dz_end_time");
		String static_month = request.getParameter("static_month");
		String check_start_time = request.getParameter("check_start_time");
		String check_end_time = request.getParameter("check_end_time");
		String PORT_ID = request.getParameter("PORT_ID");

		map.put("AREA", area);
		map.put("SON_AREA", sonArea);
		map.put("whwg", whwg);
		map.put("dz_start_time", dz_start_time);
		map.put("dz_end_time", dz_end_time);
		map.put("check_start_time", check_start_time);
		map.put("check_end_time", check_end_time);
		map.put("static_month", static_month);
		map.put("PORT_ID", PORT_ID);
			
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = checkRecordDao.querySamePortRecord(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}


	@Override
	public List<Map<String, Object>> exportPortsRecord(
			HttpServletRequest request) {
		String area = request.getParameter("area");
		String son_area = request.getParameter("son_area");
		String whwg = request.getParameter("whwg");
		String static_month = request.getParameter("static_month");//端子变动月份
		String dz_start_time = request.getParameter("dz_start_time");//端子动态开始时间
		String dz_end_time = request.getParameter("dz_end_time");//端子动态结束时间
		String check_start_time = request.getParameter("check_start_time");
		String check_end_time = request.getParameter("check_end_time");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		
		Map map =new HashMap();
		map.put("area", area);
		map.put("son_area", son_area);
		map.put("whwg", whwg);
		map.put("static_month", static_month);
		map.put("dz_start_time", dz_start_time);
		map.put("dz_end_time", dz_end_time);
		map.put("check_start_time", check_start_time);
		map.put("check_end_time", check_end_time);
		map.put("RES_TYPE_ID", RES_TYPE_ID);
		
		List<Map<String, Object>> exportInfoList = checkRecordDao.exportPortsRecord(map);
		
		return exportInfoList;
	}
	
}
