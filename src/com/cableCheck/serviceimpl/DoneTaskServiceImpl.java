package com.cableCheck.serviceimpl;

import icom.util.Result;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableCheck.dao.CableMyTaskDao;
import com.cableCheck.dao.CheckProcessDao;
import com.cableCheck.dao.DoneTaskDao;
import com.cableCheck.service.DoneTaskService;
import com.linePatrol.util.DateUtil;
import com.system.constant.RoleNo;
import com.system.dao.StaffDao;

/** 
 * @author wangxy
 * @version 创建时间：2016年7月26日 下午5:57:21 
 * 
 */
@SuppressWarnings("all")
@Transactional
@Service
public class DoneTaskServiceImpl implements DoneTaskService {

	Logger logger = Logger.getLogger(DoneTaskServiceImpl.class);
	@Autowired
	private DoneTaskDao doneTaskDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private CheckProcessDao checkProcessDao;
	@Resource
	private CableMyTaskDao cableMyTaskDao;
	
	@Override
	public Map<String, Object> queryDoneTask(HttpServletRequest request,UIPage pager) {
		Map paramMap = new HashMap();
		String staffId = request.getSession().getAttribute("staffId").toString();
		/**
		 * 请求参数
		 */
		String statusId = request.getParameter("STATUS_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String C_START_TIME = request.getParameter("C_START_TIME");
		String C_COMPLETE_TIME = request.getParameter("C_COMPLETE_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String sblx = request.getParameter("sblx");
		String son_area_id = request.getParameter("son_area_id");
		String area_id = request.getParameter("area_id");
		String INSPECTOR = request.getParameter("INSPECTOR");
		String pos_eqpCode = request.getParameter("pos_eqpCode");
		/**
		 * 查询参数赋值
		 */
		int ifGly = staffDao.getifGly(staffId);
		HttpSession session = request.getSession();
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省管理员
			paramMap.put("AREA_ID", area_id);
			paramMap.put("SON_AREA_ID", son_area_id);
			paramMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 市管理员
			paramMap.put("AREA_ID", area_id);
			paramMap.put("SON_AREA_ID", son_area_id);
			paramMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){//区管理员
			paramMap.put("AREA_ID", area_id);
			paramMap.put("SON_AREA_ID", son_area_id);
			paramMap.put("isadmin", 1);
		}else {
			paramMap.put("SON_AREA_ID", son_area_id);//非管理员
			paramMap.put("isadmin", 0);
			if((Boolean) session.getAttribute(RoleNo.CABLE_WHY)){//维护员
				paramMap.put("why", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_XJY)){// 巡检员
				paramMap.put("xjy", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_SHY)){//审核员
				paramMap.put("shy", 1);
			}
		}
		paramMap.put("STATUS_ID", statusId);
		paramMap.put("TASK_NAME", taskName);
		paramMap.put("TASK_TYPE", taskType);
		paramMap.put("staff_id", staffId);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("C_START_TIME", C_START_TIME);
		paramMap.put("C_COMPLETE_TIME", C_COMPLETE_TIME);
		paramMap.put("WHWG", whwg);
		paramMap.put("EQUIPMENT_CODE", equipmentCode);
		paramMap.put("RES_TYPE_ID", sblx);
		paramMap.put("INSPECTOR", INSPECTOR);
		paramMap.put("pos_eqpCode", pos_eqpCode);
		
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
		List<Map<String, Object>> doneTaskList = doneTaskDao.queryDoneTask(query);
		/**
		 * 派发端子数，检查端子数
		 */
		/*List<Map<String, Object>> doneTaskList1 = new ArrayList<Map<String,Object>>();
		for(Map doneTask : doneTaskList){
			String task_id = doneTask.get("TASK_ID").toString();
			//派发端子数
			String BEFORE_CHECK = null==doneTaskDao.countBeforeCheck(task_id)?"0":doneTaskDao.countBeforeCheck(task_id);
			doneTask.put("BEFORE_CHECK", Integer.valueOf(BEFORE_CHECK));
			//派发H端子数
			String BEFORE_CHECK_H = null==doneTaskDao.countBeforeCheckH(task_id)?"0":doneTaskDao.countBeforeCheckH(task_id);
			doneTask.put("BEFORE_CHECK_H", Integer.valueOf(BEFORE_CHECK_H));
			//检查端子数
			String CHECKED = null==doneTaskDao.countChecked(task_id)?"0":doneTaskDao.countChecked(task_id);
			doneTask.put("CHECKED", Integer.valueOf(CHECKED));
			//检查H端子数
			String CHECKED_H = null==doneTaskDao.countCheckedH(task_id)?"0":doneTaskDao.countCheckedH(task_id);
			doneTask.put("CHECKED_H", Integer.valueOf(CHECKED_H));
			//错误端子数
			String ERROE_CHECK = null==doneTaskDao.countErrorCheck(task_id)?"0":doneTaskDao.countErrorCheck(task_id);
			doneTask.put("ERROE_CHECK", Integer.valueOf(ERROE_CHECK));
			//错误H端子数
			String ERROE_CHECK_H = null==doneTaskDao.countErrorCheckH(task_id)?"0":doneTaskDao.countErrorCheckH(task_id);
			doneTask.put("ERROE_CHECK_H", Integer.valueOf(ERROE_CHECK_H));
			
			doneTaskList1.add(doneTask);
		}*/
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", doneTaskList);
		return resultMap;
	}
	/**
	 * 获取任务类型
	 */
	public String getTaskByTaskId(String taskId){
		Map<String,Object> map = doneTaskDao.getTaskByTaskId(taskId);
		String taskType = map.get("TASK_TYPE").toString();
		return taskType;
	}
	
	/**
	 * 周期性检查详情(已办任务)
	 */
	@Override
	public Map<String, Object> getMyTaskEqpPhotoForZq(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String TASK_ID = request.getParameter("TASK_ID");
		map.put("TASK_ID", TASK_ID);
		//获取设备信息
		List<Map<String, Object>> eqpList =doneTaskDao.getMyTaskEqpPhotoForZq(map);
		//获取派发端子信息
		//List<Map<String, Object>> portList = doneTaskDao.queryPortDetailForZq(map);
		List<Map<String, Object>> portList = doneTaskDao.queryPortDetailNewForZq(map);
	    Map param = new HashMap();
        Map checked = new HashMap();
		/*for (int i = 0; i < portList.size(); i++) {
			Map<String, Object> port = portList.get(i); 
			param.put("PORT_ID", port.get("DZID"));
			param.put("TASK_ID", port.get("TASK_ID"));
			Map portChecked = doneTaskDao.portChecked(param); 
			if (portChecked!=null){
				port.putAll(portChecked);
	     	}
		}*/
		//获取工单流程
		//List<Map<String, Object>> processList = doneTaskDao.queryProcess(map);
		Map paramMap = new HashMap();
		paramMap.put("task_id", TASK_ID);
		List<Map<String, Object>> processList = new ArrayList<Map<String,Object>>();
		processList=cableMyTaskDao.queryProcess(map);
		
		map.put("taskEqpPhotoList", eqpList);
		map.put("portList", portList);
		map.put("processList", processList);
		return map;
	}

	/**
	 * 问题上报详情
	 */
	@Override
	public Map<String, Object> getMyTaskEqpPhoto(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String TASK_ID = request.getParameter("TASK_ID");
		map.put("TASK_ID", TASK_ID);
		//获取设备信息
		List<Map<String, Object>> list = doneTaskDao.queryTroubleTaskEqp(map);
		//获取端子信息
		List<Map<String, Object>> portList = cableMyTaskDao.queryTroubleTaskOrder(map);
		//对查询的端子信息进行处理
		Map<String,Object> orderMap=new HashMap<String,Object>();
		List<Map<String,Object>> orderCheckedList =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> orderZgList =new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<portList.size();i++){
			orderMap=portList.get(i);
			orderCheckedList=(List<Map<String, Object>>) orderMap.get("orderCheckedList");
			if(orderCheckedList!=null&&orderCheckedList.size()>0){
				orderZgList=(List<Map<String, Object>>) orderCheckedList.get(0).get("orderZgList");
				orderMap.put("orderZgList", orderZgList);
			}
		}
		/*int no_need_zg=doneTaskDao.getIfNeedZg(TASK_ID);
		List<Map<String, Object>> portList = null;
		if(no_need_zg==0){
			portList = doneTaskDao.queryNoTroubleTaskPort(map); 
		}else{
		//获取端子信息
	      portList = doneTaskDao.queryTroubleTaskPort(map); 
		}
        Map param = new HashMap();
        Map checked = new HashMap();
		for (int i = 0; i < portList.size(); i++) {
			Map<String, Object> port = portList.get(i); 
			param.put("PORT_ID", port.get("PORT_ID"));
			param.put("TASK_ID", port.get("TASK_ID"));
			Map portChecked = doneTaskDao.portChecked(param); 
			if (portChecked!=null){
				port.putAll(portChecked);
			}
		}*/
		//获取工单流程
		//List<Map<String, Object>> processList = doneTaskDao.queryProcess(map);
		Map paramMap = new HashMap();
		paramMap.put("task_id", TASK_ID);
		List<Map<String, Object>> processList = new ArrayList<Map<String,Object>>();
		processList=doneTaskDao.queryProcess(map);
		
		map.put("taskEqpPhotoList", list);
		map.put("portList", portList);
		map.put("processList", processList);
		return map;
	}
	@Override
	public List<Map<String, Object>> exportExcel(HttpServletRequest request) {
		
		//页面选择一条或多条的任务
		String taskIds = request.getParameter("taskIds");
		
		String staffId = request.getSession().getAttribute("staffId").toString();
		String statusId = request.getParameter("STATUS_ID");
		String area_id= request.getParameter("AREA_ID");
		String son_area_id = request.getParameter("SON_AREA_ID");
		String taskType1 = request.getParameter("TASK_TYPE");
		String sblx = request.getParameter("sblx");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String C_START_TIME = request.getParameter("C_START_TIME");
		String C_COMPLETE_TIME = request.getParameter("C_COMPLETE_TIME");
		String pos_eqpCode = request.getParameter("pos_eqpCode");
		String INSPECTOR =request.getParameter("INSPECTOR");
		String taskName =request.getParameter("TASK_NAME");

		int ifGly = staffDao.getifGly(staffId);
		Map doneParamMap = new HashMap();
		HttpSession session = request.getSession();
		
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
			doneParamMap.put("AREA_ID", area_id);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "266");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
			/*doneParamMap.put("AREA_ID", session.getAttribute("areaId"));*/
			doneParamMap.put("AREA_ID", area_id);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "288");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){
			doneParamMap.put("AREA_ID", area_id);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "346");
		}else {
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "");
		}
		doneParamMap.put("C_START_TIME", C_START_TIME);//检查开始时间
		doneParamMap.put("C_COMPLETE_TIME", C_COMPLETE_TIME);//检查结束时间
		doneParamMap.put("STATUS_ID", statusId);
		doneParamMap.put("TASK_TYPE", taskType1);
		doneParamMap.put("SBLX", sblx);
		doneParamMap.put("TASK_NAME", taskName);
		doneParamMap.put("staff_id", staffId);
		doneParamMap.put("START_TIME", startTime);//任务开始时间
		doneParamMap.put("COMPLETE_TIME", completeTime);//任务结束时间
		doneParamMap.put("WHWG", whwg);
		doneParamMap.put("EQUIPMENT_CODE", equipmentCode);
		doneParamMap.put("INSPECTOR", INSPECTOR);
		doneParamMap.put("pos_eqpCode", pos_eqpCode);
		doneParamMap.put("taskIds", taskIds);
		List<Map<String, Object>> exportInfoList = doneTaskDao.exportPortInfo(doneParamMap);
		
		/*if("".equals(taskIds) || taskIds==null){
			//如果没有选择任务，默认导出所有已办任务
			String staffId = request.getSession().getAttribute("staffId").toString();
			String taskName = request.getParameter("TASK_NAME");
			try {
				taskName = new String(taskName.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String son_area_id = request.getParameter("SON_AREA_ID");
			String taskType1 = request.getParameter("TASK_TYPE");
			String completeTime = request.getParameter("COMPLETE_TIME");
			String startTime = request.getParameter("START_TIME");
			int ifGly = staffDao.getifGly(staffId);
			Map doneParamMap = new HashMap();
			HttpSession session = request.getSession();
			if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
				doneParamMap.put("SON_AREA_ID", son_area_id);
				doneParamMap.put("ROLE_ID", "266");
			}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
				doneParamMap.put("AREA_ID", session.getAttribute("areaId"));
				doneParamMap.put("SON_AREA_ID", son_area_id);
				doneParamMap.put("ROLE_ID", "");
			}else{
				doneParamMap.put("SON_AREA_ID", son_area_id);
				doneParamMap.put("ROLE_ID", "");
			}
			doneParamMap.put("TASK_NAME", taskName);
			doneParamMap.put("TASK_TYPE", taskType1);
			doneParamMap.put("staff_id", staffId);
			doneParamMap.put("START_TIME", startTime);
			doneParamMap.put("COMPLETE_TIME", completeTime);
			List<Map<String,Object>> mapList = doneTaskDao.queryDoneTaskForExcel(doneParamMap);
			StringBuffer sb = new StringBuffer();
			for(Map map : mapList){
				sb.append(map.get("TASK_ID").toString() + ",");
			}
			taskIds = sb.substring(0, sb.length()-1);
		}
		Map paramMap = new HashMap();
		List<Map<String, Object>> exportInfoList = new ArrayList<Map<String,Object>>();
		String[] taskIdArr = taskIds.split(",");
		
		for(String TASK_ID : taskIdArr){
			paramMap.put("TASK_ID", TASK_ID);
			//获取任务信息
			Map map = doneTaskDao.exportInfo(paramMap).get(0);
			//获取设备和端子信息
			String taskType =this.getTaskByTaskId(TASK_ID);
			if("0".equals(taskType)){//0周期性检查
				List<Map<String, Object>> eqpList =doneTaskDao.getMyTaskEqpPhotoForZq(paramMap);
				String EQP_NO = null==eqpList.get(0).get("eqp_no")?"":eqpList.get(0).get("eqp_no").toString();
				String EQP_NAME = null==eqpList.get(0).get("eqp_name")?"":eqpList.get(0).get("eqp_name").toString();
				String EQPADDRESS = null==eqpList.get(0).get("eqpaddress")?"":eqpList.get(0).get("eqpaddress").toString();
				map.put("EQP_NO", EQP_NO);
				map.put("EQP_NAME", EQP_NAME);
				map.put("EQPADDRESS", EQPADDRESS);
				List<Map<String, Object>> portList = doneTaskDao.queryPortDetailForZq(paramMap);
				if(portList.size()>0 && portList != null){
					for(Map portMap : portList){
						String DZBM = null==portMap.get("DZBM")?"":portMap.get("DZBM").toString();
						String GLBH = null==portMap.get("GLBH")?"":portMap.get("GLBH").toString();
						String GLMC = null==portMap.get("GLMC")?"":portMap.get("GLMC").toString();
						String IS_CHECK_OK = null==portMap.get("ISCHECKOK")?"":portMap.get("ISCHECKOK").toString();
						IS_CHECK_OK = "1".equals(IS_CHECK_OK)?"不合格":"合格";
						String DESCRIPT = null==portMap.get("DESCRIPT")?"":portMap.get("DESCRIPT").toString();
						map.put("DZBM", DZBM);
						map.put("GLBH", GLBH);
						map.put("GLMC", GLMC);
						map.put("IS_CHECK_OK", IS_CHECK_OK);
						map.put("DESCRIPT", DESCRIPT);//问题描述
						Map newMap = new HashMap();
						newMap.putAll(map);
						exportInfoList.add(newMap);
					}
				}else{
					Map newMap = new HashMap();
					newMap.putAll(map);
					exportInfoList.add(newMap);
				}
				
			 }else{//问题上报
				List<Map<String, Object>> eqpList = doneTaskDao.getMyTaskEqpPhoto(paramMap);
				if(eqpList.size()>0 && eqpList != null){
					String EQP_NO = null==eqpList.get(0).get("eqp_no")?"":eqpList.get(0).get("eqp_no").toString();
					String EQP_NAME = null==eqpList.get(0).get("eqp_name")?"":eqpList.get(0).get("eqp_name").toString();
					String EQPADDRESS = null==eqpList.get(0).get("eqpaddress")?"":eqpList.get(0).get("eqpaddress").toString();
					map.put("EQP_NO", EQP_NO);
					map.put("EQP_NAME", EQP_NAME);
					map.put("EQPADDRESS", EQPADDRESS);
					List<Map<String, Object>> portList = doneTaskDao.queryPort(paramMap);
					if(portList.size()>0 && portList != null){
						for(Map portMap : portList){
							String DZBM = null==portMap.get("PORT_NO")?"":portMap.get("PORT_NO").toString();
							String IS_CHECK_OK = null==portMap.get("ISCHECKOK")?"":portMap.get("ISCHECKOK").toString();
							IS_CHECK_OK = "1".equals(IS_CHECK_OK)?"不合格":"合格";
							map.put("DZBM", DZBM);
							map.put("IS_CHECK_OK", IS_CHECK_OK);
							Map newMap = new HashMap();
							newMap.putAll(map);
							exportInfoList.add(newMap);
						}
					}else{
//						map.put("DZBM", "");
//						map.put("REMARK", "");
						Map newMap = new HashMap();
						newMap.putAll(map);
						exportInfoList.add(newMap);
					}
				}else{
					Map newMap = new HashMap();
					newMap.putAll(map);
					exportInfoList.add(newMap);
				}
			 }
		}*/
		return exportInfoList;
	}
	
	@Override
	public List<Map<String, Object>> exportExcelByEqp(HttpServletRequest request) {
		//页面选择一条或多条的任务
		String taskIds = request.getParameter("taskIds");
		String staffId = request.getSession().getAttribute("staffId").toString();
		String statusId = request.getParameter("STATUS_ID");
		String AREA_ID = request.getParameter("area_id");
		String son_area_id = request.getParameter("SON_AREA_ID");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String C_START_TIME = request.getParameter("C_START_TIME");
		String C_COMPLETE_TIME = request.getParameter("C_COMPLETE_TIME");
		int ifGly = staffDao.getifGly(staffId);
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String sblx = request.getParameter("sblx");
		String pos_eqpCode = request.getParameter("pos_eqpCode");
		String INSPECTOR=request.getParameter("INSPECTOR");
		String taskName = request.getParameter("TASK_NAME");	

		Map doneParamMap = new HashMap();
		HttpSession session = request.getSession();
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省管理员
			doneParamMap.put("AREA_ID", AREA_ID);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 市管理员
			/*doneParamMap.put("AREA_ID", session.getAttribute("areaId"));*/
			doneParamMap.put("AREA_ID", AREA_ID);			
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){//区管理员
			doneParamMap.put("AREA_ID", AREA_ID);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("isadmin", 1);
		}else {
			doneParamMap.put("SON_AREA_ID", son_area_id);//非管理员
			doneParamMap.put("isadmin", 0);
			if((Boolean) session.getAttribute(RoleNo.CABLE_WHY)){//维护员
				doneParamMap.put("why", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_XJY)){// 巡检员
				doneParamMap.put("xjy", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_SHY)){//审核员
				doneParamMap.put("shy", 1);
			}
		}
		doneParamMap.put("STATUS_ID", statusId);
		doneParamMap.put("TASK_TYPE", taskType);
		doneParamMap.put("TASK_NAME", taskName);
		doneParamMap.put("staff_id", staffId);
		doneParamMap.put("START_TIME", startTime);
		doneParamMap.put("COMPLETE_TIME", completeTime);
		doneParamMap.put("C_START_TIME", C_START_TIME);
		doneParamMap.put("C_COMPLETE_TIME", C_COMPLETE_TIME);
		doneParamMap.put("WHWG", whwg);
		doneParamMap.put("EQUIPMENT_CODE", equipmentCode);
		doneParamMap.put("RES_TYPE_ID", sblx);
		doneParamMap.put("INSPECTOR", INSPECTOR);
		doneParamMap.put("pos_eqpCode", pos_eqpCode);

		List<Map<String, Object>> exportInfoList = doneTaskDao.exportInfo(doneParamMap);
		/*List<Map<String, Object>> exportInfoList1 = new ArrayList<Map<String,Object>>();
		for(Map doneTask : exportInfoList){
			String task_id = doneTask.get("TASK_ID").toString();
			//派发端子数
			String BEFORE_CHECK = null==doneTaskDao.countBeforeCheck(task_id)?"0":doneTaskDao.countBeforeCheck(task_id);
			doneTask.put("BEFORE_CHECK", Integer.valueOf(BEFORE_CHECK));
			//派发H端子数
			String BEFORE_CHECK_H = null==doneTaskDao.countBeforeCheckH(task_id)?"0":doneTaskDao.countBeforeCheckH(task_id);
			doneTask.put("BEFORE_CHECK_H", Integer.valueOf(BEFORE_CHECK_H));
			//检查端子数
			String CHECKED = null==doneTaskDao.countChecked(task_id)?"0":doneTaskDao.countChecked(task_id);
			doneTask.put("CHECKED", Integer.valueOf(CHECKED));
			//检查H端子数
			String CHECKED_H = null==doneTaskDao.countCheckedH(task_id)?"0":doneTaskDao.countCheckedH(task_id);
			doneTask.put("CHECKED_H", Integer.valueOf(CHECKED_H));
			//错误端子数
			String ERROE_CHECK = null==doneTaskDao.countErrorCheck(task_id)?"0":doneTaskDao.countErrorCheck(task_id);
			doneTask.put("ERROE_CHECK", Integer.valueOf(ERROE_CHECK));
			//错误H端子数
			String ERROE_CHECK_H = null==doneTaskDao.countErrorCheckH(task_id)?"0":doneTaskDao.countErrorCheckH(task_id);
			doneTask.put("ERROE_CHECK_H", Integer.valueOf(ERROE_CHECK_H));
			
			exportInfoList1.add(doneTask);
		}*/
		
		return exportInfoList;
	}
	@Override
	public Map<String, Object> queryHandler(HttpServletRequest request,
			UIPage pager) {

		
		Map<String, Object> map = new HashMap<String, Object>();
		String staff_name = request.getParameter("staff_name");
		String staff_no = request.getParameter("staff_no");
		String areaId = request.getParameter("areaId");
		String sonAreaId = request.getParameter("sonAreaId");
		
		map.put("staff_name", staff_name);
		map.put("staff_no", staff_no);
		map.put("areaId", areaId);
		map.put("sonAreaId", sonAreaId);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> list = doneTaskDao.queryHandler(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", list);
		return pmap;
	
	}
	@Override
	public String saveTask(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String staffId = request.getParameter("staffId");  //检查人id
		String taskIds = request.getParameter("taskIds");
		
		String userId = request.getSession().getAttribute("staffId").toString();// 当前用户
		map.put("taskIds", taskIds);
		//获取任务各个信息
		List<Map<String, Object>> list = doneTaskDao.taskQuery(map);
		//新增任务
		for (Map<String, Object> sbMap : list) {
			Map taskMap = new HashMap();
			taskMap.put("taskId", sbMap.get("TASK_ID"));
			Map<String, Object>  info = doneTaskDao.queryTaskInfo(taskMap);
			//插入任务表
			Map troubleTaskMap = new HashMap();
			troubleTaskMap.put("TASK_NO", info.get("EQUIPMENT_CODE") + "_" + DateUtil.getDate("yyyyMMdd"));
			troubleTaskMap.put("TASK_NAME", info.get("EQUIPMENT_NAME")+ "_" + DateUtil.getDate("yyyyMMdd"));
			troubleTaskMap.put("TASK_TYPE", 4);//二次检查
			troubleTaskMap.put("STATUS_ID", 6);//二次检查
			troubleTaskMap.put("INSPECTOR", staffId);
			troubleTaskMap.put("CREATE_STAFF", userId);
			troubleTaskMap.put("SON_AREA_ID", sbMap.get("SON_AREA_ID"));
			troubleTaskMap.put("AREA_ID", sbMap.get("AREA_ID"));
			troubleTaskMap.put("ENABLE", "0");//如果不需要整改工单，则把此工单只为无效,0可用 1不可用（不显示在待办列表）
			troubleTaskMap.put("REMARK", "");
			troubleTaskMap.put("INFO", "");
			troubleTaskMap.put("SBID", info.get("EQUIPMENT_ID"));
			troubleTaskMap.put("AUDITOR", userId);
			troubleTaskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
			troubleTaskMap.put("OLD_TASK_ID", sbMap.get("TASK_ID"));//老的task_id			troubleTaskMap.put("SBID", eqpId);//设备id
			doneTaskDao.saveTroubleTask(troubleTaskMap);
			String newTaskId = troubleTaskMap.get("TASK_ID").toString();
			logger.info("【需要整改添加一张新的工单taskId】"+newTaskId);			
			//根据设备id获取端子
			List<Map<String, Object>> dzList = doneTaskDao.terminalQuery1(sbMap);
			//保存任务详细
			for (Map<String, Object> dzMap : dzList) {
				Map taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", newTaskId);
				taskDetailMap.put("INSPECT_OBJECT_ID", dzMap.get("ID"));//id
				taskDetailMap.put("DTSJ_ID", dzMap.get("ID"));
				taskDetailMap.put("INSPECT_OBJECT_NO", dzMap.get("DZBM")==null?"":dzMap.get("DZBM"));//端子编码
				taskDetailMap.put("DZID", dzMap.get("DZID")==null?"":dzMap.get("DZID"));//端子id
				taskDetailMap.put("SBID", dzMap.get("SBID")==null?"":dzMap.get("SBID"));//设备id
				taskDetailMap.put("SBBM", dzMap.get("SBBM")==null?"":dzMap.get("SBBM"));//设备编码
				taskDetailMap.put("SBMC", dzMap.get("SBMC")==null?"":dzMap.get("SBMC"));//设备名称
				taskDetailMap.put("XZ", dzMap.get("XZ")==null?"":dzMap.get("XZ"));//端子光路性质
				taskDetailMap.put("ACTION_TYPE", dzMap.get("ACTION_TYPE")==null?"":dzMap.get("ACTION_TYPE"));//端子光路性质
				taskDetailMap.put("BDSJ", dzMap.get("BDSJ")==null?"":dzMap.get("BDSJ"));//端子变动时间
				taskDetailMap.put("GLBH", dzMap.get("GLBH")==null?"":dzMap.get("GLBH"));
				taskDetailMap.put("GLMC", dzMap.get("GLMC")==null?"":dzMap.get("GLMC"));
				taskDetailMap.put("INSPECT_OBJECT_TYPE", 1);
				taskDetailMap.put("CHECK_FLAG", "0");//0派发的端子，1检查的端子
				doneTaskDao.doSaveTaskDetail(taskDetailMap);
				doneTaskDao.updateDtsj(dzMap);  //派发成功修改端子派发状态
			}
			//保存工单流程
			Map<String, Object> flowParams = new HashMap<String, Object>();
			flowParams.put("oper_staff", userId);
			flowParams.put("task_id", newTaskId);
			flowParams.put("status",  6); //待回单
			flowParams.put("remark", "二次派发工单");
			checkProcessDao.addProcess(flowParams);
		}
		return Result.returnSuccess("").toString();
	}
	@Override
	public Map<String, Object> querySecondTask(HttpServletRequest request,
			UIPage pager) {
		Map paramMap = new HashMap();
		String staffId = request.getSession().getAttribute("staffId").toString();
		/**
		 * 请求参数
		 */
		String statusId = request.getParameter("STATUS_ID");
		String taskName = request.getParameter("TASK_NAME");
		String taskType = request.getParameter("TASK_TYPE");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String C_START_TIME = request.getParameter("C_START_TIME");
		String C_COMPLETE_TIME = request.getParameter("C_COMPLETE_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String sblx = request.getParameter("sblx");
		String son_area_id = request.getParameter("son_area_id");
		String area_id = request.getParameter("area_id");
		String INSPECTOR = request.getParameter("INSPECTOR");
		String pos_eqpCode = request.getParameter("pos_eqpCode");
		String checkport = request.getParameter("checkport");
		String checkrate = request.getParameter("checkrate");
		String orderby  = request.getParameter("orderby");
		/**
		 * 查询参数赋值
		 */
		int ifGly = staffDao.getifGly(staffId);
		HttpSession session = request.getSession();
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省管理员
			paramMap.put("AREA_ID", area_id);
			paramMap.put("SON_AREA_ID", son_area_id);
			paramMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 市管理员
			paramMap.put("AREA_ID", area_id);
			paramMap.put("SON_AREA_ID", son_area_id);
			paramMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){//区管理员
			paramMap.put("AREA_ID", area_id);
			paramMap.put("SON_AREA_ID", son_area_id);
			paramMap.put("isadmin", 1);
		}else {
			paramMap.put("SON_AREA_ID", son_area_id);//非管理员
			paramMap.put("isadmin", 0);
			if((Boolean) session.getAttribute(RoleNo.CABLE_WHY)){//维护员
				paramMap.put("why", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_XJY)){// 巡检员
				paramMap.put("xjy", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_SHY)){//审核员
				paramMap.put("shy", 1);
			}
		}
		paramMap.put("STATUS_ID", statusId);
		paramMap.put("TASK_NAME", taskName);
		paramMap.put("TASK_TYPE", taskType);
		paramMap.put("staff_id", staffId);
		paramMap.put("START_TIME", startTime);
		paramMap.put("COMPLETE_TIME", completeTime);
		paramMap.put("C_START_TIME", C_START_TIME);
		paramMap.put("C_COMPLETE_TIME", C_COMPLETE_TIME);
		paramMap.put("WHWG", whwg);
		paramMap.put("EQUIPMENT_CODE", equipmentCode);
		paramMap.put("RES_TYPE_ID", sblx);
		paramMap.put("INSPECTOR", INSPECTOR);
		paramMap.put("pos_eqpCode", pos_eqpCode);
		paramMap.put("checkport", checkport);
		paramMap.put("checkrate", checkrate);
		paramMap.put("orderby", orderby);
		
		
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(paramMap);
		List<Map<String, Object>> doneTaskList = doneTaskDao.querySecondTask(query);
		/**
		 * 派发端子数，检查端子数
		 */
		
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		resultMap.put("total", query.getPager().getRowcount());
		resultMap.put("rows", doneTaskList);
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> exportSecondTaskExcelByEqp(HttpServletRequest request) {
		//页面选择一条或多条的任务
		String taskIds = request.getParameter("taskIds");
		String staffId = request.getSession().getAttribute("staffId").toString();
		String AREA_ID = request.getParameter("area_id");
		String son_area_id = request.getParameter("SON_AREA_ID");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String C_START_TIME = request.getParameter("C_START_TIME");
		String C_COMPLETE_TIME = request.getParameter("C_COMPLETE_TIME");
		int ifGly = staffDao.getifGly(staffId);
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String sblx = request.getParameter("sblx");
		String pos_eqpCode = request.getParameter("pos_eqpCode");
		String INSPECTOR=request.getParameter("INSPECTOR");
		String taskName = request.getParameter("TASK_NAME");
		String checkport = request.getParameter("checkport");
		String checkrate = request.getParameter("checkrate");
		String orderby  = request.getParameter("orderby");

		Map doneParamMap = new HashMap();
		HttpSession session = request.getSession();
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省管理员
			doneParamMap.put("AREA_ID", AREA_ID);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 市管理员
			/*doneParamMap.put("AREA_ID", session.getAttribute("areaId"));*/
			doneParamMap.put("AREA_ID", AREA_ID);			
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("isadmin", 1);
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){//区管理员
			doneParamMap.put("AREA_ID", AREA_ID);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("isadmin", 1);
		}else {
			doneParamMap.put("SON_AREA_ID", son_area_id);//非管理员
			doneParamMap.put("isadmin", 0);
			if((Boolean) session.getAttribute(RoleNo.CABLE_WHY)){//维护员
				doneParamMap.put("why", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_XJY)){// 巡检员
				doneParamMap.put("xjy", 1);
			} if((Boolean) session.getAttribute(RoleNo.CABLE_SHY)){//审核员
				doneParamMap.put("shy", 1);
			}
		}
		doneParamMap.put("STATUS_ID", "8");
		doneParamMap.put("TASK_NAME", taskName);
		doneParamMap.put("TASK_TYPE", "0");
		doneParamMap.put("staff_id", staffId);
		doneParamMap.put("START_TIME", startTime);
		doneParamMap.put("COMPLETE_TIME", completeTime);
		doneParamMap.put("C_START_TIME", C_START_TIME);
		doneParamMap.put("C_COMPLETE_TIME", C_COMPLETE_TIME);
		doneParamMap.put("WHWG", whwg);
		doneParamMap.put("EQUIPMENT_CODE", equipmentCode);
		doneParamMap.put("RES_TYPE_ID", sblx);
		doneParamMap.put("INSPECTOR", INSPECTOR);
		doneParamMap.put("pos_eqpCode", pos_eqpCode);
		doneParamMap.put("checkport", checkport);
		doneParamMap.put("checkrate", checkrate);
		doneParamMap.put("orderby", orderby);
		List<Map<String, Object>> exportInfoList = doneTaskDao.exportSecondTaskInfo(doneParamMap);
		
		
		return exportInfoList;
	}
	@Override
	public List<Map<String, Object>> exportExcelByPOrder(
			HttpServletRequest request) {
		//页面选择一条或多条的任务
		String taskIds = request.getParameter("taskIds");
		
		String staffId = request.getSession().getAttribute("staffId").toString();
		String statusId = request.getParameter("STATUS_ID");
		String area_id= request.getParameter("AREA_ID");
		String son_area_id = request.getParameter("SON_AREA_ID");
		String taskType1 = request.getParameter("TASK_TYPE");
		String sblx = request.getParameter("sblx");
		String completeTime = request.getParameter("COMPLETE_TIME");
		String startTime = request.getParameter("START_TIME");
		String whwg = request.getParameter("whwg");
		String equipmentCode = request.getParameter("equipmentCode");
		String C_START_TIME = request.getParameter("C_START_TIME");
		String C_COMPLETE_TIME = request.getParameter("C_COMPLETE_TIME");
		String pos_eqpCode = request.getParameter("pos_eqpCode");
		String INSPECTOR =request.getParameter("INSPECTOR");
		String taskName =request.getParameter("TASK_NAME");

		int ifGly = staffDao.getifGly(staffId);
		Map doneParamMap = new HashMap();
		HttpSession session = request.getSession();
		
		if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)){//省级管理员
			doneParamMap.put("AREA_ID", area_id);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "266");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)){// 是否是市级管理员
			/*doneParamMap.put("AREA_ID", session.getAttribute("areaId"));*/
			doneParamMap.put("AREA_ID", area_id);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "288");
		}else if((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)){
			doneParamMap.put("AREA_ID", area_id);
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "346");
		}else {
			doneParamMap.put("SON_AREA_ID", son_area_id);
			doneParamMap.put("ROLE_ID", "");
		}
		doneParamMap.put("C_START_TIME", C_START_TIME);//检查开始时间
		doneParamMap.put("C_COMPLETE_TIME", C_COMPLETE_TIME);//检查结束时间
		doneParamMap.put("STATUS_ID", statusId);
		doneParamMap.put("TASK_TYPE", taskType1);
		doneParamMap.put("SBLX", sblx);
		doneParamMap.put("TASK_NAME", taskName);
		doneParamMap.put("staff_id", staffId);
		doneParamMap.put("START_TIME", startTime);//任务开始时间
		doneParamMap.put("COMPLETE_TIME", completeTime);//任务结束时间
		doneParamMap.put("WHWG", whwg);
		doneParamMap.put("EQUIPMENT_CODE", equipmentCode);
		doneParamMap.put("INSPECTOR", INSPECTOR);
		doneParamMap.put("pos_eqpCode", pos_eqpCode);
		doneParamMap.put("taskIds", taskIds);
				
		List<Map<String, Object>> exportInfoList = doneTaskDao.exportExcelByPOrder(doneParamMap);
		String RECORD_TYPE="";
		String ISCHECKOK="";
		String PORT_INFO="";
		String INFO="";
		String ZG_MICRO_PHOTO_PATH="";
		List<Map<String, Object>> orderCheckedList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> orderMap:exportInfoList){
			orderCheckedList=(List<Map<String, Object>>) orderMap.get("orderCheckedList");
			if(orderCheckedList!=null&&orderCheckedList.size()>0){
				RECORD_TYPE=(String) orderCheckedList.get(0).get("RECORD_TYPE");
				ISCHECKOK=(String) orderCheckedList.get(0).get("ISCHECKOK");
				PORT_INFO=(String) orderCheckedList.get(0).get("PORT_INFO");
				INFO=(String) orderCheckedList.get(0).get("INFO");
				ZG_MICRO_PHOTO_PATH=(String) orderCheckedList.get(0).get("ZG_MICRO_PHOTO_PATH");
				orderMap.put("RECORD_TYPE", RECORD_TYPE);
				orderMap.put("ISCHECKOK", ISCHECKOK);
				orderMap.put("PORT_INFO", PORT_INFO);
				orderMap.put("INFO", INFO);
				orderMap.put("P_PATH", ZG_MICRO_PHOTO_PATH);
			}
		}
		
		return exportInfoList;
	}

}
