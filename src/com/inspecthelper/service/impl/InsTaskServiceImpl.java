package com.inspecthelper.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.inspecthelper.dao.EquipDao;
import com.inspecthelper.dao.InsTaskDao;
import com.inspecthelper.service.InsTaskService;
import com.util.MyCalendar;
import com.util.MyDateDto;

/**
 * 日常巡检任务业务层实现类
 *
 * @author lbhu
 * @since 2014-9-9
 *
 */
@SuppressWarnings("all")
@Service
public class InsTaskServiceImpl implements InsTaskService {

	public static final String MONTH_PARAM = "1,10,20,30";
	
	public static final String WEEK_PARAM = "1,3,5,8";
	
	public static final String YEAR_PARAM = "1,5,9,13";
	
	@Resource
	private InsTaskDao insTaskDao;
	
	@Resource
	private EquipDao equipDao;
	
	
	
	@Override
	public Map<String, Object> getTaskList(HttpServletRequest request,
			UIPage pager) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", request.getParameter("taskId"));
		map.put("taskCode", request.getParameter("taskCode"));
		map.put("equCode", request.getParameter("equCode"));
		map.put("ownCompany", request.getParameter("ownCompany"));
		map.put("staffNo", request.getParameter("staffNo"));
		map.put("staffName", request.getParameter("staffName"));
		map.put("stateId", request.getParameter("stateId"));
		map.put("areaName", request.getParameter("areaName"));
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map> taskList = insTaskDao.query(query);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("rows", taskList);
		paramMap.put("total", query.getPager().getRowcount());
		
		return paramMap;
	}
	
	@Override
	public void allotTask(HttpServletRequest request){
		Map params = new HashMap();//新建查询和存储对象
		String flag = request.getParameter("flag");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cycle = request.getParameter("cycle");
		String frequency = request.getParameter("frequency");
		String taskCode = request.getParameter("taskCode");
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("cycle", cycle);
		params.put("frequency", frequency);
		params.put("taskCode", taskCode);
		
		if("0".equals(flag)){//flag=0表示指定分配，否则为智能分配
			String staffId = request.getParameter("staffId");//巡检人ID
			String equipIds = request.getParameter("equipIds");
			String[] equipIdArry = equipIds.split(",");
			String taskId = String.valueOf(insTaskDao.getTaskId());//获取新的任务ID
			params.put("taskId", taskId);
			params.put("staffId", staffId);
			insTaskDao.saveTask(params);//存储任务
			
			for(int i=0;i<equipIdArry.length;i++){//插入人员和资源的关系
				params.put("equipment_id", equipIdArry[i]);
				insTaskDao.saveTaskEqu(params);
			}
			
			this.createOrder(params);//创建任务
		}else{
			Object areaObject = request.getSession().getAttribute("sonAreaId");//登录用户所在区域
			String sonAreaId = areaObject == null?null:areaObject.toString();
			String equpCode = request.getParameter("equpCode");//设备编码
			String state = request.getParameter("state");
			String ownCompany = request.getParameter("ownCompany");
			String equpAddress = request.getParameter("equpAddress");
			String manaArea = request.getParameter("manaArea");//电信管理区
			String staffName = request.getParameter("staffName");//巡检人员
			String manaType = request.getParameter("manaType");//管理模式
			String areaName = request.getParameter("areaName");
			String resTypeId = request.getParameter("resTypeId");
			String staffType = request.getParameter("staffType");
			
			params.put("equpCode", equpCode);
			params.put("state", state);
			params.put("ownCompany", ownCompany);
			params.put("equpAddress", equpAddress);
			params.put("manaArea", manaArea);
			params.put("staffName", staffName);
			params.put("manaType", manaType);
			params.put("areaName", areaName);
			params.put("resTypeId", resTypeId);
			params.put("staffType",staffType);
			params.put("area", sonAreaId);
			
			if("0".equals(staffType)){//巡检人员
				
				List staffIds = equipDao.getDisStaffList(params);
				saveTask(params,staffIds,"0");		
				
			}else if("1".equals(staffType)){//管控人员
				
				List staffIds = equipDao.getControlStaffList(params);
				saveTask(params,staffIds,"1");
			}else{//巡检和管控人员
				List staffIds = equipDao.getDisStaffList(params);
				saveTask(params,staffIds,"0");
				
				List staffIds_ = equipDao.getControlStaffList(params);
				saveTask(params,staffIds_,"1");
			}
			
		}
		
	}
	
	
	@Override
	public Map<String, Object> getTaskDetailList(HttpServletRequest request,
			UIPage pager) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", request.getParameter("taskId"));
		map.put("equipment_code", request.getParameter("equipment_code"));
		map.put("equipment_name", request.getParameter("equipment_name"));
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map> taskList = insTaskDao.getTaskDetailList(query);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("rows", taskList);
		paramMap.put("total", query.getPager().getRowcount());
		
		return paramMap;
	}
	
	/**
	 * 创建巡检人任务
	 * 
	 * @param map
	 */
	private void saveTask(Map map,List staffIds,String type){
		
		for(int i=0;i<staffIds.size();i++){//共需要生成计划数量为人员数量
			
			String taskId = String.valueOf(insTaskDao.getTaskId());//获取新的计划ID
			map.put("taskId", taskId);
			
			List equipList = null;
			if("0".equals(type)){
				String staffId = (String) ((Map) staffIds.get(i)).get("STAFF_ID");
				map.put("staffId", staffId);
				
				equipList = equipDao.getAllResList(map);//根据巡检人员ID获取所有设备
			}else{
				String staffId_ = (String) ((Map) staffIds.get(i)).get("CHECK_STAFF_ID");
				map.put("staffId", staffId_);
				
				equipList = equipDao.getControlStaffList(map);//根据管控人员ID获取所有设备
			}
			
			insTaskDao.saveTask(map);//存储计划
	
			for(int j=0;j<equipList.size();j++){//插入人员和资源的关系
				String equipmentId = (String) ((Map) equipList.get(j)).get("EQUIPMENT_ID");//获取设备ID
				map.put("equipment_id", equipmentId);
				insTaskDao.saveTaskEqu(map);
			}
			
			this.createOrder(map);//创建任务
		}
	}
	
	
	@Override
	@Transactional
	public void modifyTask(HttpServletRequest request){
		Map map = new HashMap();
		String state = request.getParameter("state");//获取计划状态
		String staffId = request.getParameter("staffId");//获取巡检人员ID
		
		Map paramMap = new HashMap();
		paramMap.put("taskId",request.getParameter("taskId"));
		Map taskInfo = insTaskDao.getTaskById(paramMap);//根据计划ID查询任务信息
		
		String taskId = String.valueOf(insTaskDao.getTaskId());//获取新的计划ID
		paramMap.put("newTaskId", taskId);//设置新的计划ID
		
		if("1".equals(state)){//计划已开始执行
			
			map.put("taskId", taskId);
			map.put("staffId", staffId);
			map.put("groupId", taskInfo.get("GROUP_ID"));
			map.put("taskCode", taskInfo.get("TASK_CODE"));
			map.put("startDate", taskInfo.get("START_DATE"));
			map.put("endDate", taskInfo.get("END_DATE"));
			map.put("cycle", taskInfo.get("CYCLE_ID"));
			map.put("frequency", taskInfo.get("FREQUENCY"));
			
			insTaskDao.deleteTask(paramMap); //1、将原计划状态置为失效
			insTaskDao.saveTask(map); //2、添加一条新的计划记录
			insTaskDao.updateTaskEqu(paramMap); //3、将原计划的资源关系更新为新计划
			insTaskDao.updateTaskOrder(paramMap);//4、将未执行的任务关联到新计划
			
		}else{//计划未执行
			
			if(null != staffId && !"".equals(staffId.trim())){
				map.put("staffId", staffId);
			}else{
				map.put("staffId", taskInfo.get("CHECK_STAFF_ID"));
			}
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String cycle = request.getParameter("cycle");
			String frequency = request.getParameter("frequency");
			map.put("taskId", taskId);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("cycle", cycle);
			map.put("frequency", frequency);
			map.put("groupId", taskInfo.get("GROUP_ID"));
			map.put("taskCode", taskInfo.get("TASK_CODE"));
			
			insTaskDao.deleteTaskOrder(paramMap);//1、删除未执行的任务
			insTaskDao.deleteTask(paramMap); //2、将原计划状态置为失效
			insTaskDao.saveTask(map); //3、添加一条新的计划记录
			insTaskDao.updateTaskEqu(paramMap); //4、将原计划的资源关联关系更新为新计划
			this.createOrder(map);//5、插入新的任务
		}
	}
	
	@Override
	@Transactional
	public void deleteTask(HttpServletRequest request){
		String taskIds = request.getParameter("taskIds");
		Map map = new HashMap();
		map.put("taskId", taskIds);
		insTaskDao.deleteTask(map);//删除计划
		insTaskDao.deleteTaskEqu(map);//删除计划和资源关联关系
		insTaskDao.deleteTaskOrder(map);//删除任务
	}
	
	public boolean createOrder(Map map){
		if(map.get("cycle").equals("5")){//周期是一天
			this.createDayTaskOrder(map);
		}else if(map.get("cycle").equals("4")){//周期是一周
			this.createWeekTaskOrder(map);
		}else if(map.get("cycle").equals("3")){//周期是一月
			this.createMonthTaskOrder(map);
		}else if(map.get("cycle").equals("1")){//周期是一年
			this.createYearTaskOrder(map);
		}else if(map.get("cycle").equals("6")){//周期是两月
			this.createTwoMonthTaskOrder(map);
		}else if(map.get("cycle").equals("7")){//周期是半年
			this.createSixMonthTaskOrder(map);
		}
		return true;
	}
	
	public boolean createSixMonthTaskOrder(Map map){
		try{
			/*获取开始时间和结束时间*/
			String startDate = (String) map.get("startDate");
			String endDate = (String) map.get("endDate");
			String frequency = (String) map.get("frequency");
			String taskId = (String) map.get("taskId");
			Map param = new HashMap();
			param.put("taskId", taskId);
			param.put("startDate", startDate);
			param.put("endDate", endDate+" 23:59:59");
			/*获取签到点和工单主键ID*/
			String orderId = String.valueOf(insTaskDao.getTaskOrderId());
			param.put("orderId", orderId);
			insTaskDao.createTaskOrder(param);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createTwoMonthTaskOrder(Map map){
		try{
			/*获取开始时间和结束时间*/
			String startDate = (String) map.get("startDate");
			String endDate = (String) map.get("endDate");
			String frequency = (String) map.get("frequency");
			String taskId = (String) map.get("taskId");
			Map param = new HashMap();
			param.put("taskId", taskId);
			/*生成开始结束时间*/
			MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
			MyDateDto mEDate = MyCalendar.getDayInfo(endDate);
			/*定义日期相差多少月*/
			int monthCount = 0;
			monthCount = mEDate.getTwoMonthCount(mSDate);
			String orderId = "";
			for(int i = 0;i < monthCount;i++){
				String mSDateStr = mSDate.getDateStr();
				/*获取签到点和工单主键ID*/
				orderId = String.valueOf(insTaskDao.getTaskOrderId());
				param.put("orderId", orderId);
				String sDate = "";
				String eDate = "";
				if(i == 0){
					sDate = mSDateStr+" 00:00:00";
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 2, 1).getDateStr());
					eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
				}else if(i == monthCount-1){
					sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-1"+" 00:00:00";
					eDate = mEDate.getDateStr()+" 23:59:59";
				}else{
					sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-1"+" 00:00:00";
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 2, 1).getDateStr());
					eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
				}
				param.put("startDate", sDate);
				param.put("endDate", eDate);
				insTaskDao.createTaskOrder(param);
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 2, 2).getDateStr());
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createDayTaskOrder(Map map){
		try{
			/*获取开始时间和结束时间*/
			String startDate = (String) map.get("startDate");
			String endDate = (String) map.get("endDate");
			String frequency = (String) map.get("frequency");
			String taskId = (String) map.get("taskId");
			Map param = new HashMap();
			param.put("taskId", taskId);
			/*生成开始结束时间*/
			MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
			MyDateDto mEDate = MyCalendar.getDayInfo(endDate);
			/*定义相隔天数*/
			int dCount = 0;
			dCount = mEDate.getDayCount(mSDate);
			String mSDateStr = mSDate.getDateStr();
			String orderId = "";
			for(int i = 0;i<dCount;i++){
				if(Integer.parseInt(frequency)==1){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					String sDate = mSDateStr+" 00:00:00";
					String eDate = mSDateStr+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					param.put("orderId", orderId);
					/*插入一张工单*/
					insTaskDao.createTaskOrder(param);
				}else if(Integer.parseInt(frequency)==2){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					String sDate = mSDateStr+" 00:00:00";
					String eDate = mSDateStr+" 11:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					param.put("orderId", orderId);
					/*插入第一张工单*/
					insTaskDao.createTaskOrder(param);
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					sDate = mSDateStr+" 12:00:00";
					eDate = mSDateStr+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					param.put("orderId", orderId);
					/*插入第二张工单*/
					insTaskDao.createTaskOrder(param);
				}else if(Integer.parseInt(frequency)==3){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					String sDate = mSDateStr+" 00:00:00";
					String eDate = mSDateStr+" 09:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					param.put("orderId", orderId);
					/*插入第一张工单*/
					insTaskDao.createTaskOrder(param);
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					sDate = mSDateStr+" 10:00:00";
					eDate = mSDateStr+" 14:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					param.put("orderId", orderId);
					/*插入第二张工单*/
					insTaskDao.createTaskOrder(param);
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					sDate = mSDateStr+" 15:00:00";
					eDate = mSDateStr+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					param.put("orderId", orderId);
					/*插入第二张工单*/
					insTaskDao.createTaskOrder(param);
				}
				mSDateStr = MyCalendar.getAddDate(mSDateStr, 5, 1).getDateStr();
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createWeekTaskOrder(Map map){
		try{
			/*获取开始时间和结束时间*/
			String startDate = (String) map.get("startDate");
			String endDate = (String) map.get("endDate");
			String frequency = (String) map.get("frequency");
			String taskId = (String) map.get("taskId");
			Map param = new HashMap();
			param.put("taskId", taskId);
			/*生成开始结束时间*/
			MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
			MyDateDto mEDate = MyCalendar.getDayInfo(endDate);
			/*定义日期相差多少星期*/
			int weekCount = 0;
			weekCount = MyCalendar.getDayInfo(mEDate.getLastDateOfWeek()).getWeekCount(MyCalendar.getDayInfo(mSDate.getLastDateOfWeek()));
			String orderId = "";
			for(int i = 0;i < weekCount;i++){
				String mSDateStr = mSDate.getDateStr();
				if(Integer.parseInt(frequency)==1){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					String sDate = "";
					String eDate = "";
					if(i == 0){
						sDate = mSDateStr+" 00:00:00";
						eDate = mSDate.getLastDateOfWeek()+" 23:59:59";
					}else if(i == weekCount-1){
						sDate = mSDate.getFirstDateOfWeek()+" 00:00:00";
						eDate = mEDate.getDateStr()+" 23:59:59";
					}else{
						sDate = mSDate.getFirstDateOfWeek()+" 00:00:00";
						eDate = mSDate.getLastDateOfWeek()+" 23:59:59";
					}
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 3, 1).getDateStr());
				}else if(Integer.parseInt(frequency)==2){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					String sDate = "";
					String eDate = "";
					sDate = mSDate.getFirstDateOfWeek()+" 00:00:00";
					eDate =MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),5,3).getDateStr()+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					sDate =MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),5,4).getDateStr()+" 00:00:00";
					eDate = mSDate.getLastDateOfWeek()+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 3, 1).getDateStr());
					
				}else if(Integer.parseInt(frequency)==3){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					String sDate = "";
					String eDate = "";
					sDate = mSDate.getFirstDateOfWeek()+" 00:00:00";
					eDate =MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),5,1).getDateStr()+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					sDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),5,2).getDateStr()+" 00:00:00";
					eDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),5,3).getDateStr()+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					sDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),5,4).getDateStr()+" 00:00:00";
					eDate = mSDate.getLastDateOfWeek()+" 23:59:59";
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 3, 1).getDateStr());
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean createMonthTaskOrder(Map map){
		try{
			/*获取开始时间和结束时间*/
			String startDate = (String) map.get("startDate");
			String endDate = (String) map.get("endDate");
			String frequency = (String) map.get("frequency");
			String taskId = (String) map.get("taskId");
			Map param = new HashMap();
			param.put("taskId", taskId);
			/*生成开始结束时间*/
			MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
			MyDateDto mEDate = MyCalendar.getDayInfo(endDate);
			/*定义日期相差多少月*/
			int monthCount = 0;
			monthCount = mEDate.getMonthCount(mSDate);
			String orderId = "";
			for(int i = 0;i < monthCount;i++){
				String mSDateStr = mSDate.getDateStr();
				if(Integer.parseInt(frequency)==1){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					param.put("orderId", orderId);
					String sDate = "";
					String eDate = "";
					if(i == 0){
						sDate = mSDateStr+" 00:00:00";
						eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
					}else if(i == monthCount-1){
						sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-1"+" 00:00:00";
						eDate = mEDate.getDateStr()+" 23:59:59";
					}else{
						sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-1"+" 00:00:00";
						eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
					}
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 2, 1).getDateStr());
				}else if(Integer.parseInt(frequency)==2){
					String sDate = "";
					String eDate = "";
					if(i == 0){
						if(mSDate.getDate()>=15){
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDateStr+" 00:00:00";
							eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}else if(mSDate.getDate()<15){
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDateStr+" 00:00:00";
							eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-15"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-16"+" 00:00:00";
							eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}
					}else if(i == monthCount-1){
						if(mEDate.getDate()<=15){
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-01"+" 00:00:00";
							eDate = mEDate.getDateStr()+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}else if(mEDate.getDate()>15){
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-01"+" 00:00:00";
							eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-15"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-16"+" 00:00:00";
							eDate = mEDate.getDateStr()+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}
					}else{
						/*获取签到点和工单主键ID*/
						orderId = String.valueOf(insTaskDao.getTaskOrderId());
						param.put("orderId", orderId);
						sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-01"+" 00:00:00";
						eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-15"+" 23:59:59";
						param.put("startDate", sDate);
						param.put("endDate", eDate);
						insTaskDao.createTaskOrder(param);
						/*获取签到点和工单主键ID*/
						orderId = String.valueOf(insTaskDao.getTaskOrderId());
						param.put("orderId", orderId);
						sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-16"+" 00:00:00";
						eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+" 23:59:59";
						param.put("startDate", sDate);
						param.put("endDate", eDate);
						insTaskDao.createTaskOrder(param);
						}
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 2, 1).getDateStr());
				}else if (Integer.parseInt(frequency)==3){
					String sDate = "";
					String eDate = "";
					String[] monthParam = MONTH_PARAM.split(",");
					monthParam[3] = MyCalendar.getMonthDays(mSDate.getYear(), mSDate.getMonth())+1+"";
					if(i == 0){
						if(mSDate.getDate()<10){
							monthParam[0] = mSDate.getDate()+"";
							for(int j = 0;j<monthParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[j]+" 00:00:00";
								eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[j+1])-1)+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}else if(mSDate.getDate()<20){
							monthParam[1] = mSDate.getDate()+""; 
							for(int j = 1;j<monthParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[j]+" 00:00:00";
								eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[j+1])-1)+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}else{
							monthParam[2] = mSDate.getDate()+""; 
							for(int j = 2;j<monthParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[j]+" 00:00:00";
								eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[j+1])-1)+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}
					}else if(i == monthCount-1){
						if(mEDate.getDate()<10){
							monthParam[1] = mEDate.getDate()+1+"";
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[0]+" 00:00:00";
							eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[1])-1)+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}else if(mEDate.getDate()<20){
							monthParam[2] = mEDate.getDate()+1+"";
							for(int j = 0; j< 2; j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[j]+" 00:00:00";
								eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[j+1])-1)+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}else{
							monthParam[3] = mEDate.getDate()+1+"";
							for(int j = 0;j<monthParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[j]+" 00:00:00";
								eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[j+1])-1)+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}
					}else{
						for(int j = 0;j<monthParam.length-1;j++){
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+monthParam[j]+" 00:00:00";
							eDate = mSDate.getYear()+"-"+mSDate.getMonth()+"-"+(Integer.parseInt(monthParam[j+1])-1)+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}
					}
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 2, 1).getDateStr());
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createYearTaskOrder(Map map){
		try{
			/*获取开始时间和结束时间*/
			String startDate = (String) map.get("startDate");
			String endDate = (String) map.get("endDate");
			String frequency = (String) map.get("frequency");
			String taskId = (String) map.get("taskId");
			Map param = new HashMap();
			param.put("taskId", taskId);
			/*生成开始结束时间*/
			MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
			MyDateDto mEDate = MyCalendar.getDayInfo(endDate);
			/*定义相隔年数*/
			int yCount = 0;
			yCount = mEDate.getYearCount(mSDate);
			String mSDateStr = mSDate.getDateStr();
			String orderId = "";
			String sDate = "";
			String eDate = "";
			for(int i = 0;i<yCount;i++){
				if(Integer.parseInt(frequency)==1){
					/*获取签到点和工单主键ID*/
					orderId = String.valueOf(insTaskDao.getTaskOrderId());
					if(i == 0){
						sDate = mSDateStr+" 00:00:00";
						eDate = mSDate.getYear()+"-12-31"+" 23:59:59";
					}else if(i == yCount-1){
						sDate = mSDate.getYear()+"-01-01"+" 00:00:00";
						eDate = mEDate.getDateStr()+" 23:59:59";
					}else{
						sDate = mSDate.getYear()+"-01-01"+" 00:00:00";
						eDate = mSDate.getYear()+"-12-31"+" 23:59:59";
					}
					param.put("orderId", orderId);
					param.put("startDate", sDate);
					param.put("endDate", eDate);
					insTaskDao.createTaskOrder(param);
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 1, 1).getDateStr());
				}else if(Integer.parseInt(frequency)==2){
					if(i==0){
						if(mSDate.getMonth()>6){
							/*获取工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getDateStr()+" 00:00:00";
							eDate = mSDate.getYear()+"-12-31"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}else{
							/*获取工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getDateStr()+" 00:00:00";
							eDate = mSDate.getYear()+"-06-30"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							/*获取工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							sDate = mSDate.getDateStr()+" 00:00:00";
							eDate = mSDate.getYear()+"-12-31"+" 23:59:59";
							param.put("orderId", orderId);
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}
					}else if(i==yCount-1){
						if(mEDate.getMonth()<6){
							/*获取工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-01-01"+" 00:00:00";
							eDate = mEDate.getDateStr()+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}else{
							/*获取工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-01-01"+" 00:00:00";
							eDate = mSDate.getYear()+"-06-30"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							/*获取工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							sDate = mSDate.getYear()+"-07-01"+" 00:00:00";
							eDate = mEDate.getDateStr()+" 23:59:59";
							param.put("orderId", orderId);
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							} 
						}else{
							sDate = mSDate.getYear()+"-01-01"+" 00:00:00";
							eDate = mSDate.getYear()+"-06-30"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							sDate = mSDate.getYear()+"-07-01"+" 00:00:00";
							eDate = mSDate.getYear()+"-12-31"+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
					}
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 1, 1).getDateStr());
				}else if(Integer.parseInt(frequency)==3){
					String[] yearParam = YEAR_PARAM.split(",");
					if(i == 0){
						yearParam[0] = mSDate.getMonth()+1+"";
						if(mSDate.getDate()<5){
							yearParam[0] = mSDate.getMonth()+"";
							for(int j = 0;j<yearParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+yearParam[j]+"-01 00:00:00";
								eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[j+1])-1)+"-"+MyCalendar.getMonthDays(mSDate.getYear(), (Integer.parseInt(yearParam[j+1])-1))+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}else if(mSDate.getDate()<9){
							yearParam[1] = mSDate.getMonth()+""; 
							for(int j = 1;j<yearParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+yearParam[j]+"-01 00:00:00";
								eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[j+1])-1)+"-"+MyCalendar.getMonthDays(mSDate.getYear(), (Integer.parseInt(yearParam[j+1])-1))+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}else{
							yearParam[2] = mSDate.getMonth()+""; 
							for(int j = 2;j<yearParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+yearParam[j]+"-01 00:00:00";
								eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[j+1])-1)+"-"+MyCalendar.getMonthDays(mSDate.getYear(), (Integer.parseInt(yearParam[j+1])-1))+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}
					}else if(i == yCount-1){
						if(mEDate.getMonth()<5){
							yearParam[1] = mEDate.getMonth()+1+"";
							/*获取签到点和工单主键ID*/
							for(int j = 2; j< 3; j++){
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+yearParam[0]+"-01 00:00:00";
							eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[1])-1)+"-"+mEDate.getDate()+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
							}
						}else if(mEDate.getMonth()<9){
							yearParam[2] = mEDate.getDate()+1+"";
							for(int j = 0; j< 2; j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+yearParam[j]+"-"+yearParam[j]+" 00:00:00";
								eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[j+1])-1)+"-"+MyCalendar.getMonthDays(mSDate.getYear(), (Integer.parseInt(yearParam[j+1])-1))+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}else{
							yearParam[3] = mEDate.getDate()+1+"";
							for(int j = 0;j<yearParam.length-1;j++){
								/*获取签到点和工单主键ID*/
								orderId = String.valueOf(insTaskDao.getTaskOrderId());
								param.put("orderId", orderId);
								sDate = mSDate.getYear()+"-"+yearParam[j]+"-01 00:00:00";
								eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[j+1])-1)+"-"+MyCalendar.getMonthDays(mSDate.getYear(), (Integer.parseInt(yearParam[j+1])-1))+" 23:59:59";
								param.put("startDate", sDate);
								param.put("endDate", eDate);
								insTaskDao.createTaskOrder(param);
							}
						}
					}else{
						for(int j = 0;j<yearParam.length-1;j++){
							/*获取签到点和工单主键ID*/
							orderId = String.valueOf(insTaskDao.getTaskOrderId());
							param.put("orderId", orderId);
							sDate = mSDate.getYear()+"-"+yearParam[j]+"-01 00:00:00";
							eDate = mSDate.getYear()+"-"+(Integer.parseInt(yearParam[j+1])-1)+"-"+MyCalendar.getMonthDays(mSDate.getYear(), (Integer.parseInt(yearParam[j+1])-1))+" 23:59:59";
							param.put("startDate", sDate);
							param.put("endDate", eDate);
							insTaskDao.createTaskOrder(param);
						}
					}
					mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr, 1, 1).getDateStr());
				}
			}
			return true;
		}catch(Exception e){
		e.printStackTrace();
		return false;
		}
	}
	
	private String getURLDecoderStr(String name){
		try {
			name = name==null?null:URLDecoder.decode(name,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return name;
	}

}
