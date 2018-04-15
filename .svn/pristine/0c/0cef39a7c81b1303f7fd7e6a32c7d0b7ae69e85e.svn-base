package com.cableCheck.serviceimpl;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.axxreport.util.ExcelUtil;
import com.cableCheck.dao.CheckQualityReportDao;
import com.cableCheck.service.CheckQualityReportService;
import com.system.constant.RoleNo;
import com.system.dao.StaffDao;

import util.page.Query;
import util.page.UIPage;



@SuppressWarnings("all")
@Transactional
@Service
public class CheckQualityReportServiceimpl implements CheckQualityReportService {

	
	
	@Resource
	private CheckQualityReportDao checkQualityReportDao;
	@Autowired
	private StaffDao staffDao;
	@Override
	public Map<String, Object> query(HttpServletRequest request, UIPage pager) {
		// TODO Auto-generated method stub
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
		String WLJB_ID = request.getParameter("WLJB_ID");
		int ifGly = staffDao.getifGly(staffId);
		/**
		 * 查询参数赋值
		 */
		paramMap.put("WLJB_ID", WLJB_ID);
	
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
		List<Map<String, Object>> checkQualityReportList = checkQualityReportDao.query(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", checkQualityReportList);
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selArea() {
		// TODO Auto-generated method stub
		return checkQualityReportDao.selArea();
	}

	@Override
	public void checkQualityReportDownload(
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市 ", "区域 ","变动端子数","变动设备量",
				"只包含H光路的设备数 ", "派发设备数", "派发设备中只包含H光路的设备数", "周期性任务检查设备数 ","二次检查设备数","一键反馈检查设备数","不预告抽查设备数","检查端子数","检查端子错误数" ,"端子检查准确率","设备检查完成率"});
		List<String> code = Arrays.asList(new String[] { "CITYNAME","NAME","变动端子数",
				"CHANGEEQPNUM", "EQPISHNUM", "BILLEQPNUM", "BILLEQPISHNUM", "CHECKEQPNUM" ,"二次检查设备数","一键反馈检查设备数","不预告抽查设备数","检查端子数","检查端子错误数" ,"端子检查准确率","设备检查完成率"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String AREA_ID = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String START_TIME = request.getParameter("START_TIME");
		String COMPLETE_TIME = request.getParameter("COMPLETE_TIME");
		String static_month = request.getParameter("static_month");
		String PSTART_TIME = request.getParameter("PSTART_TIME");
		String PCOMPLETE_TIME = request.getParameter("PCOMPLETE_TIME");
		String WLJB_ID = request.getParameter("WLJB_ID");
		paras.put("WLJB_ID", WLJB_ID);
		paras.put("AREA_ID",AREA_ID );
		paras.put("son_area",son_area );
		paras.put("START_TIME",START_TIME );
		paras.put("COMPLETE_TIME", COMPLETE_TIME);
		paras.put("static_month",static_month);
		paras.put("PSTART_TIME",PSTART_TIME);
		paras.put("PCOMPLETE_TIME",PCOMPLETE_TIME );
	
		List<Map<String, Object>> data = checkQualityReportDao.queryDown(paras);
		int CHANGEEQPNUM=0 ;
		int EQPISHNUM=0 ;
		int BILLEQPNUM=0 ;
		int BILLEQPISHNUM=0 ;
		int CHECKEQPNUM=0 ;
		int sd=0 ;
		int yj=0 ;
		int bk=0 ;
		int dz=0 ;
		int dzw=0 ;
		int dtsj=0 ;
		String dzwcl;
		String sbwcl;
				for (int i = 0; i < data.size(); i++) {
					CHANGEEQPNUM += Integer.valueOf(data.get(i).get("CHANGEEQPNUM").toString());
					EQPISHNUM += Integer.valueOf(data.get(i).get("EQPISHNUM").toString());
					BILLEQPNUM += Integer.valueOf(data.get(i).get("BILLEQPNUM").toString());
					BILLEQPISHNUM += Integer.valueOf(data.get(i).get("BILLEQPISHNUM").toString());
					CHECKEQPNUM += Integer.valueOf(data.get(i).get("CHECKEQPNUM").toString());
					sd += Integer.valueOf(data.get(i).get("二次检查设备数").toString());
					yj += Integer.valueOf(data.get(i).get("一键反馈检查设备数").toString());
					bk += Integer.valueOf(data.get(i).get("不预告抽查设备数").toString());
					dz += Integer.valueOf(data.get(i).get("检查端子数").toString());
					dzw += Integer.valueOf(data.get(i).get("检查端子错误数").toString());
					dtsj += Integer.valueOf(data.get(i).get("变动端子数").toString());
		}
				float dzwc=(float)(dz-dzw)/dz*100;
				float sbwc=(float)(CHECKEQPNUM+sd+yj+bk)/CHANGEEQPNUM*100;

				DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		        String  f1 = df.format(dzwc);
		        String  f2 = df.format(sbwc);//返回的是String类型的
				dzwcl= f1+"%";
				sbwcl= f2+"%";
				paras.put("CHANGEEQPNUM", CHANGEEQPNUM);		
				paras.put("EQPISHNUM", EQPISHNUM);
				paras.put("BILLEQPNUM", BILLEQPNUM);
				paras.put("BILLEQPISHNUM", BILLEQPISHNUM);
				paras.put("CHECKEQPNUM", CHECKEQPNUM);
				paras.put("二次检查设备数", sd);
				paras.put("一键反馈检查设备数", yj);
				paras.put("变动端子数", dtsj);
		paras.put("不预告抽查设备数", bk);
		paras.put("检查端子数", dz);
		paras.put("检查端子错误数", dzw);
		paras.put("CITYNAME", "总计");
		paras.put("端子检查准确率", dzwcl);
		paras.put("设备检查完成率", sbwcl);
		data.add(paras);
		
		String fileName = "检查质量报告";
		String firstLine = "检查质量报告";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String, Object> equipmentQuery(HttpServletRequest request, UIPage pager) {
		String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");
		String parent_area_id = request.getParameter("parent_area_id");
		String static_month = request.getParameter("static_month");
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
		String pstart_time = request.getParameter("PSTART_TIME");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String RES_TYPE_ID = request.getParameter("RES_TYPE_ID");
		String WLJB_ID = request.getParameter("WLJB_ID");
		Map paramMap = new HashMap();
		paramMap.put("areaId", areaId);
		paramMap.put("WLJB_ID", WLJB_ID);

		paramMap.put("parent_area_id", parent_area_id);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("static_month", static_month);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("RES_TYPE_ID", RES_TYPE_ID);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> list = null;
		if(parent_area_id!=null&&!"".equals(parent_area_id)){
			if("0".equals(type)){
	        	list = checkQualityReportDao.queryChangeEqpByCity(query);
	        	
	        }else if ("1".equals(type)){
	        	 list = checkQualityReportDao.queryPfEqpByCity(query);
	        	
	        }
	        else if ("2".equals(type)){
	        	 list = checkQualityReportDao.queryCheckEqpByCity(query);
	        	
	        }
	        else if ("3".equals(type)){
	         list = checkQualityReportDao.queryYjEqpByCity(query);
	        	
	        }
	        else if("4".equals(type)){
	        	 list = checkQualityReportDao.queryBkEqpByCity(query);
	        	
	        }else if("5".equals(type)){
	        	list = checkQualityReportDao.queryHEqpByCity(query);
	        }else if("6".equals(type)){
	        	list = checkQualityReportDao.queryPfHEqpByCity(query);
	        }else {
	        	list = checkQualityReportDao.querySdEqpByCity(query);
	        }		
		}else{
        if("0".equals(type)){
        	list = checkQualityReportDao.queryChangeEqp(query);
        	
        }else if ("1".equals(type)){
        	 list = checkQualityReportDao.queryPfEqp(query);
        	
        }
        else if ("2".equals(type)){
        	 list = checkQualityReportDao.queryCheckEqp(query);
        	
        }
        else if ("3".equals(type)){
         list = checkQualityReportDao.queryYjEqp(query);
        	
        }
        else if("4".equals(type)){
        	 list = checkQualityReportDao.queryBkEqp(query);
        	
        }else if("5".equals(type)){
        	list = checkQualityReportDao.queryHEqp(query);
        }else if("6".equals(type)){
        	list = checkQualityReportDao.queryPfHEqp(query);
        }else {
        	list = checkQualityReportDao.querySdEqp(query);
        }
        }
        Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
		
		
	}

	@Override
	public Map<String, Object> portQuery(HttpServletRequest request,
			UIPage pager) {
		String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String static_month = request.getParameter("static_month");
		String parent_area_id = request.getParameter("parent_area_id");
		String WLJB_ID = request.getParameter("WLJB_ID");
		Map paramMap = new HashMap();
		paramMap.put("areaId", areaId);
		paramMap.put("WLJB_ID", WLJB_ID);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("parent_area_id", parent_area_id);
		paramMap.put("static_month", static_month);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> list = null;
		if(parent_area_id!=null&&!"".equals(parent_area_id)){
			if ("0".equals(type)) {
				list = checkQualityReportDao.queryCheckPortByCity(query);

			} else if ("1".equals(type)){
				list = checkQualityReportDao.queryPromblePortByCity(query);
			}else {
				list = checkQualityReportDao.queryDtsjPortByCity(query);
			}
		} else {
			if ("0".equals(type)) {
				list = checkQualityReportDao.queryCheckPort(query);

			} else if ("1".equals(type)){
				list = checkQualityReportDao.queryPromblePort(query);
			}else {
				list = checkQualityReportDao.queryDtsjPort(query);
			}
		}
        Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public void equipmentDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] {  "区域 ","设备名称","设备编码 ","设备地址","设备类型" ,"管理编码 "});
		List<String> code = Arrays.asList(new String[] { "NAME", "EQUIPMENT_NAME" ,"EQUIPMENT_CODE","ADDRESS","RES_TYPE","MANAGE_AREA_ID"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = request.getParameter("type");
		paras.put("areaId", para.get("areaId"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));

		paras.put("parent_area_id", para.get("parent_area_id"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("static_month", para.get("static_month"));
		paras.put("PSTART_TIME", para.get("PSTART_TIME"));
		paras.put("PCOMPLETE_TIME", para.get("PCOMPLETE_TIME"));
		List<Map<String, Object>> data = null;
		if(para.get("parent_area_id")!=null&&!"".equals(para.get("parent_area_id"))){
			if("0".equals(type)){
				data = checkQualityReportDao.downChangeEqpByCity(paras);
	        	
	        }else if ("1".equals(type)){
	        	data = checkQualityReportDao.downPfEqpByCity(paras);
	        	
	        }
	        else if ("2".equals(type)){
	        	data = checkQualityReportDao.downCheckEqpByCity(paras);
	        	
	        }
	        else if ("3".equals(type)){
	        	data = checkQualityReportDao.downYjEqpByCity(paras);
	        	
	        }
	        else if("4".equals(type)){
	        	data = checkQualityReportDao.downBkEqpByCity(paras);
	        	
	        }else if("5".equals(type)){
	        	data = checkQualityReportDao.downHEqpByCity(paras);
	        }else if("6".equals(type)){
	        	data = checkQualityReportDao.downPfHEqpByCity(paras);
	        }else {
	        	data = checkQualityReportDao.downSdEqpByCity(paras);
	        }	}else{	
		if("0".equals(type)){
        	data = checkQualityReportDao.downChangeEqp(paras);
        	
        }else if ("1".equals(type)){
        	 data = checkQualityReportDao.downPfEqp(paras);
        	
        }
        else if ("2".equals(type)){
        	data = checkQualityReportDao.downCheckEqp(paras);
        	
        }
        else if ("3".equals(type)){
        	data = checkQualityReportDao.downYjEqp(paras);
        	
        }
        else if ("4".equals(type)){
        	data = checkQualityReportDao.downBkEqp(paras);
        	
        }
        else if ("5".equals(type)){
        	data = checkQualityReportDao.downHEqp(paras);
        	
        }else if ("6".equals(type)){
        	data = checkQualityReportDao.downPfHEqp(paras);
        }else {
        	data = checkQualityReportDao.downSdEqp(paras);
        }
		
	        }
	   
		String fileName = "设备清单";
		String firstLine = "设备清单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void portDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] {  "区域 ","端子ID","端子编码 ","设备名称","设备编码" ,"任务名称 ","检查人 ","检查时间 ","问题描述 ","是否合格 ","光路名称","光路名编码"});
		List<String> code = Arrays.asList(new String[] { "NAME", "PORT_ID" ,"PORT_NO","EQP_NAME","EQP_NO","TASK_NAME","STAFF_NAME","CHECK_TIME","DESCRIPT","ISCHECKOK","GLMC","GLBM"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = request.getParameter("type");
		paras.put("parent_area_id", para.get("parent_area_id"));
		paras.put("areaId", para.get("areaId"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));

		List<Map<String, Object>> data = null;
		if(para.get("parent_area_id")!=null&&!"".equals(para.get("parent_area_id"))){
			
			if ("0".equals(type)) {
				data = checkQualityReportDao.downCheckPortByCity(paras);

			} else {
				data = checkQualityReportDao.downPromblePortByCity(paras);

			}
			
		} else {
			if ("0".equals(type)) {
				data = checkQualityReportDao.downCheckPort(paras);

			} else {
				data = checkQualityReportDao.downPromblePort(paras);

			}
			
		}
		
	   
		String fileName = "端子清单";
		String firstLine = "端子清单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Map<String, Object> getMyTaskEqpPhotoForZq(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String TASK_ID = request.getParameter("TASK_ID");
		map.put("TASK_ID", TASK_ID);
		//获取第一次检查设备信息
		List<Map<String, Object>> eqpList =checkQualityReportDao.getZqEqpInfo(map);
		
		//获取二次检查设备信息
		List<Map<String, Object>> sdEqpList =checkQualityReportDao.getZqSdEqpInfo(map);
		
		//获取第一次派发端子信息
		List<Map<String, Object>> portList = checkQualityReportDao.getZqPortInfo(map);
		//获取二次派发端子信息
		List<Map<String, Object>> sdPortList = checkQualityReportDao.getZqSdPortInfo(map);

        Map param = new HashMap();
        Map checked = new HashMap();
		for (int i = 0; i < sdPortList.size(); i++) {
			Map<String, Object> port = sdPortList.get(i); 
			param.put("PORT_ID", port.get("DZID"));
			param.put("TASK_ID", port.get("TASK_ID"));
			Map portChecked = checkQualityReportDao.portChecked(param); 
			if (portChecked!=null){
				port.putAll(portChecked);
			}
		}
		
		//获取工单流程
		List<Map<String, Object>> processList = checkQualityReportDao.queryProcess(map);
		
		map.put("eqpList", eqpList);
		map.put("sdEqpList", sdEqpList);
		map.put("portList", portList);
		map.put("sdPortList", sdPortList);
		map.put("processList", processList);
		return map;
	}

	@Override
	public Map<String, Object> queryByCity(HttpServletRequest request,
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
		
		String WLJB_ID = request.getParameter("WLJB_ID");
		paramMap.put("WLJB_ID", WLJB_ID);

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
		List<Map<String, Object>> checkQualityReportList = checkQualityReportDao.queryByCity(query);
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", checkQualityReportList);
		return resultMap;
	}

	@Override
	public void checkQualityReportDownloadByCity(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] { "地市 ","变动端子数","变动设备量",
				"只包含H光路的设备数 ", "派发设备数", "派发设备中只包含H光路的设备数", "周期性任务检查设备数 ","二次检查设备数","一键反馈检查设备数","不预告抽查设备数","检查端子数","检查端子错误数" ,"端子检查准确率","设备检查完成率"});
		List<String> code = Arrays.asList(new String[] { "NAME","变动端子数",
				"CHANGEEQPNUM", "EQPISHNUM", "BILLEQPNUM", "BILLEQPISHNUM", "CHECKEQPNUM" ,"二次检查设备数","一键反馈检查设备数","不预告抽查设备数","检查端子数","检查端子错误数" ,"端子检查准确率","设备检查完成率"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String AREA_ID = request.getParameter("AREA_ID");
		String son_area = request.getParameter("son_area");
		String START_TIME = request.getParameter("START_TIME");
		String COMPLETE_TIME = request.getParameter("COMPLETE_TIME");
		String static_month = request.getParameter("static_month");
		String PSTART_TIME = request.getParameter("PSTART_TIME");
		String PCOMPLETE_TIME = request.getParameter("PCOMPLETE_TIME");
		String WLJB_ID = request.getParameter("WLJB_ID");
		paras.put("WLJB_ID", WLJB_ID);

		paras.put("AREA_ID",AREA_ID );
		paras.put("son_area",son_area );
		paras.put("START_TIME",START_TIME );
		paras.put("COMPLETE_TIME", COMPLETE_TIME);
		paras.put("static_month",static_month);
		paras.put("PSTART_TIME",PSTART_TIME);
		paras.put("PCOMPLETE_TIME",PCOMPLETE_TIME );
		List<Map<String, Object>> data = checkQualityReportDao.queryDownByCity(paras);
		int CHANGEEQPNUM=0 ;
		int EQPISHNUM=0 ;
		int BILLEQPNUM=0 ;
		int BILLEQPISHNUM=0 ;
		int CHECKEQPNUM=0 ;
		int sd=0 ;
		int yj=0 ;
		int bk=0 ;
		int dz=0 ;
		int dzw=0 ;
		int dtsj=0 ;
		String dzwcl;
		String sbwcl;
				for (int i = 0; i < data.size(); i++) {
					CHANGEEQPNUM += Integer.valueOf(data.get(i).get("CHANGEEQPNUM").toString());
					EQPISHNUM += Integer.valueOf(data.get(i).get("EQPISHNUM").toString());
					BILLEQPNUM += Integer.valueOf(data.get(i).get("BILLEQPNUM").toString());
					BILLEQPISHNUM += Integer.valueOf(data.get(i).get("BILLEQPISHNUM").toString());
					CHECKEQPNUM += Integer.valueOf(data.get(i).get("CHECKEQPNUM").toString());
					sd += Integer.valueOf(data.get(i).get("二次检查设备数").toString());
					yj += Integer.valueOf(data.get(i).get("一键反馈检查设备数").toString());
					bk += Integer.valueOf(data.get(i).get("不预告抽查设备数").toString());
					dz += Integer.valueOf(data.get(i).get("检查端子数").toString());
					dzw += Integer.valueOf(data.get(i).get("检查端子错误数").toString());
					dtsj += Integer.valueOf(data.get(i).get("变动端子数").toString());
		}
				float dzwc=(float)(dz-dzw)/dz*100;
				float sbwc=(float)(CHECKEQPNUM+sd+yj+bk)/CHANGEEQPNUM*100;

				DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		        String  f1 = df.format(dzwc);
		        String  f2 = df.format(sbwc);//返回的是String类型的
				dzwcl= f1+"%";
				sbwcl= f2+"%";
				paras.put("CHANGEEQPNUM", CHANGEEQPNUM);		
				paras.put("EQPISHNUM", EQPISHNUM);
				paras.put("BILLEQPNUM", BILLEQPNUM);
				paras.put("BILLEQPISHNUM", BILLEQPISHNUM);
				paras.put("CHECKEQPNUM", CHECKEQPNUM);
				paras.put("二次检查设备数", sd);
				paras.put("一键反馈检查设备数", yj);
				paras.put("变动端子数", dtsj);
		paras.put("不预告抽查设备数", bk);
		paras.put("检查端子数", dz);
		paras.put("检查端子错误数", dzw);
		paras.put("NAME", "总计");
		paras.put("端子检查准确率", dzwcl);
		paras.put("设备检查完成率", sbwcl);
		data.add(paras);
		String fileName = "检查质量报告";
		String firstLine = "检查质量报告";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> equipmentNumQuery(HttpServletRequest request,
			UIPage pager) {
		String type = request.getParameter("type");
		String areaId = request.getParameter("AREAID");
		String parent_area_id = request.getParameter("parent_area_id");
		String static_month = request.getParameter("static_month");
		String pcomplete_time = request.getParameter("PCOMPLETE_TIME");
		String pstart_time = request.getParameter("PSTART_TIME");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		Map paramMap = new HashMap();
		String WLJB_ID = request.getParameter("WLJB_ID");
		paramMap.put("WLJB_ID", WLJB_ID);
		paramMap.put("areaId", areaId);
		paramMap.put("parent_area_id", parent_area_id);
		paramMap.put("PCOMPLETE_TIME", pcomplete_time);
		paramMap.put("PSTART_TIME", pstart_time);
		paramMap.put("static_month", static_month);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> list = null;
		if(parent_area_id!=null&&!"".equals(parent_area_id)){
			if("0".equals(type)){
	        	list = checkQualityReportDao.queryChangeNumEqpByCity(query);
	        	
	        }else if ("1".equals(type)){
	        	 list = checkQualityReportDao.queryPfEqpNumByCity(query);
	        	
	        }
	        else if ("2".equals(type)){
	        	 list = checkQualityReportDao.queryCheckEqpNumByCity(query);
	        	
	        }
	        else if ("3".equals(type)){
	         list = checkQualityReportDao.queryYjEqpNumByCity(query);
	        	
	        }
	        else if("4".equals(type)){
	        	 list = checkQualityReportDao.queryBkEqpNumByCity(query);
	        	
	        }else if("5".equals(type)){
	        	list = checkQualityReportDao.queryHEqpByCity(query);
	        }else if("6".equals(type)){
	        	list = checkQualityReportDao.queryPfHEqpByCity(query);
	        }else {
	        	list = checkQualityReportDao.querySdEqpByCity(query);
	        }		
		}else{
        if("0".equals(type)){
        	list = checkQualityReportDao.queryChangeEqpNum(query);
        	
        }else if ("1".equals(type)){
        	 list = checkQualityReportDao.queryPfEqpNum(query);
        	
        }
        else if ("2".equals(type)){
        	 list = checkQualityReportDao.queryCheckEqpNum(query);
        	
        }
        else if ("3".equals(type)){
         list = checkQualityReportDao.queryYjEqpNum(query);
        	
        }
        else if("4".equals(type)){
        	 list = checkQualityReportDao.queryBkEqpNum(query);
        	
        }else if("5".equals(type)){
        	list = checkQualityReportDao.queryHEqp(query);
        }else if("6".equals(type)){
        	list = checkQualityReportDao.queryPfHEqp(query);
        }else {
        	list = checkQualityReportDao.querySdEqp(query);
        }
        }
        Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
		
	}

	@Override
	public void dtsjDownload(Map<String, Object> para,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> title = Arrays.asList(new String[] {  "地市","区域","端子ID","端子编码 ","设备名称","设备编码","设备类型","变动时间","工单竣工时间","性质 ","光路编号 ","光路名称","配置工号","施工工号","更纤工号","箱子编码"});
		List<String> code = Arrays.asList(new String[] { "CITY","TOWN","DZID","DZBM","SBMC","SBBM","SBLX","BDSJ","GDJGSJ","XZ","GLBH","GLMC","PZGH","SGGH","GQGH","INSTALL_SBBM"});
		Map<String, Object> paras = new HashMap<String, Object>();
		String type = request.getParameter("type");
		paras.put("parent_area_id", para.get("parent_area_id"));
		paras.put("static_month", para.get("static_month"));
		paras.put("areaId", para.get("areaId"));
		paras.put("START_TIME", para.get("START_TIME"));
		paras.put("COMPLETE_TIME", para.get("COMPLETE_TIME"));
		paras.put("WLJB_ID", para.get("WLJB_ID"));
		List<Map<String, Object>> data = null;
		if(para.get("parent_area_id")!=null&&!"".equals(para.get("parent_area_id"))){
		
				data = checkQualityReportDao.downDtsjPortByCity(paras);

			
		} else {
				data = checkQualityReportDao.downDtsjPort(paras);

		}
		
	   
		String fileName = "端子清单";
		String firstLine = "端子清单";

		try {
			ExcelUtil.generateExcelAndDownload(title, code, data, request,
					response, fileName, firstLine);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
