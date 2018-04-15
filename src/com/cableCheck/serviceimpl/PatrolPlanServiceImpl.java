package com.cableCheck.serviceimpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.page.Query;
import util.page.UIPage;

import com.cableCheck.dao.CheckProcessDao;
import com.cableCheck.dao.DoneTaskDao;
import com.cableCheck.dao.PatrolPlanDao;
import com.cableCheck.dao.TaskMangerDao;
import com.cableCheck.service.PatrolPlanService;
import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.CablePlanDao;
import com.cableInspection.dao.CableTaskDao;
import com.cableInspection.dao.PointManageDao;
import com.cableInspection.model.CableModel;
import com.cableInspection.model.PointModel;
import com.cableInspection.service.CablePlanService;
import com.system.constant.RoleNo;
import com.system.dao.StaffDao;
import com.util.DateUtil;
import com.util.Rule;
import com.util.StringUtil;
import com.util.sendMessage.SendMessageUtil;

import icom.util.Result;

/**
 * 任务计实现类 * @author linzhengcheng
 *
 */
@SuppressWarnings("all")
@Transactional
@Service
public class PatrolPlanServiceImpl implements PatrolPlanService {
	@Resource
	private StaffDao staffDao;

	@Resource
	private CableTaskDao cabletaskDao;
	
	@Resource
	private PatrolPlanDao patrolPlanDao;

	@Resource
	private DoneTaskDao doneTaskDao;
	
	@Resource
	private CheckProcessDao checkProcessDao;
	
	@Resource
	private TaskMangerDao taskMangerDao;

	@Override
	public Map<String, Object> planQuery(HttpServletRequest request,
			UIPage pager) {
		String plan_no = request.getParameter("plan_no");
		String plan_name = request.getParameter("plan_name");
		String plan_start_time = request.getParameter("plan_start_time");
		String plan_end_time = request.getParameter("plan_end_time");
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前用户
		int ifGly = staffDao.getifGly(staffId);
		/*String son_area_id = request.getParameter("son_area_id");*/
		String plan_type = request.getParameter("plan_type");
//		String contractor = request.getParameter("contractor");
		Map map = new HashMap();

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN)) {// 省级管理员
			// map.put("AREA_ID", session.getAttribute("areaId"));
//			map.put("SON_AREA_ID", son_area_id);
		} else {
			if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
//				map.put("SON_AREA_ID", son_area_id);
			} else {
				if ((Boolean) session.getAttribute(RoleNo.CABLE_ADMIN_SONAREA)) {// 是否是区级管理员
//					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		map.put("PLAN_NO", plan_no);
		map.put("PLAN_NAME", plan_name);
		map.put("PLAN_START_TIME", plan_start_time);
		map.put("PLAN_END_TIME", plan_end_time);
		map.put("PLAN_TYPE", plan_type);
//		map.put("CONTRACTOR", contractor);
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = patrolPlanDao.planQuery(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void deletePlan(HttpServletRequest request) {
		String selected = request.getParameter("selected");
		HttpSession session = request.getSession();
		Map map = new HashMap();
		map.put("MODIFY_STAFF", session.getAttribute("staffId"));
		String[] plans = selected.split(",");
		String plan_ids = "";
		for (int i = 0; i < plans.length; i++) {
			plan_ids += plans[i] + ",";
			patrolPlanDao.deleteForPlanByRule(plans[i]);
		}
		if (plan_ids.endsWith(",")) {
			plan_ids = plan_ids.substring(0, plan_ids.length() - 1);
		}
		map.put("PLAN_ID", plan_ids);
		patrolPlanDao.deletePlan(map);
		
	}
	
	@Override
	public Boolean saveTask(HttpServletRequest request) {
		String inspector = request.getParameter("selected_staffId");
		String inspector_type = request.getParameter("inspector_type");
		String[] planSelected = request.getParameter("selected_planId").replace(" ", "").split(",");
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前管理员

		Integer PLAN_ID = null;
		String PLAN_NO = null;
		String PLAN_START_TIME = null;
		String PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;

		Map taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合

		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;

		// 获取任务详细信息
		Map<String,Object> planInfo = null;
		Map map = new HashMap();
		// 更新计划分配状态map
		Map updatePlanMap = new HashMap();
		// 收信人号码
		List phoneNumList = new ArrayList();
		// 发送内容
		List messageContentList = new ArrayList();
		for (int i = 0, j = planSelected.length; i < j; i++) {
			map.put("PLAN_ID", planSelected[i]);

			//计划信息
			planInfo = patrolPlanDao.getPlan(map);

			PLAN_END_TIME = planInfo.get("PLAN_END_TIME").toString();

			//判断计划是否过期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date end_date = sdf.parse(PLAN_END_TIME);
				if(end_date.before(new Date())){
					continue;
				}
			} catch (ParseException e1) {

			} catch(Exception e){

			}

			updatePlanMap.put("PLAN_ID", planSelected[i]);
			updatePlanMap.put("TASK_INSPECTOR", inspector);
			updatePlanMap.put("TASK_CREATOR", staffId);
			updatePlanMap.put("ISDISTRIBUTED", 1);
			updatePlanMap.put("INSPECTOR_TYPE", inspector_type);
			// 更新计划分配状态
			int updateResult = patrolPlanDao.updatePlanIsDistributed(updatePlanMap);

			PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
			PLAN_NO = planInfo.get("PLAN_NO").toString();
			PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
			PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
			PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
			AREA_ID = planInfo.get("AREA_ID").toString();
			SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();

			/*List<Map> ruleData = null;// 生成的规则
			Map ruleMap = new HashMap();// 查询任务生成规则参数map

			if (Rule.DAY.equals(PLAN_CIRCLE) || Rule.WEEK.equals(PLAN_CIRCLE)) {
				// 获取本周的最后一天
				Date sundayOfCurrWeek = DateUtil.getSundayOfCurrWeek();
				if (StringUtil.stringToDate(PLAN_START_TIME, "yyyy-MM-dd")
						.after(sundayOfCurrWeek)) {// 计划开始时间在本周日后
					continue;
				} else {
					ruleMap.put("startDate", PLAN_START_TIME);
				}
				if (StringUtil.stringToDate(PLAN_END_TIME, "yyyy-MM-dd")
						.before(sundayOfCurrWeek)) {// 计划结束时间在本周日前
					ruleMap.put("endDate", PLAN_END_TIME);
				} else {
					ruleMap.put("endDate", StringUtil.dateToString(
							sundayOfCurrWeek, "yyyy-MM-dd"));
				}
			} else if (Rule.MONTH.equals(PLAN_CIRCLE)) {
				// 获取本月的最后一天
				Date lastDayOfCurrMonth = DateUtil.getLastDayOfCurrMonth();
				if (StringUtil.stringToDate(PLAN_START_TIME, "yyyy-MM-dd")
						.after(lastDayOfCurrMonth)) {// 计划开始时间在本月最后一天后
					continue;
				} else {
					ruleMap.put("startDate", PLAN_START_TIME);
				}
				if (StringUtil.stringToDate(PLAN_END_TIME, "yyyy-MM-dd")
						.before(lastDayOfCurrMonth)) {// 计划结束时间在本月最后一天前
					ruleMap.put("endDate", PLAN_END_TIME);
				} else {
					ruleMap.put("endDate", StringUtil.dateToString(
							lastDayOfCurrMonth, "yyyy-MM-dd"));
				}
			} else {
				ruleMap.put("startDate", PLAN_START_TIME);
				ruleMap.put("endDate", PLAN_END_TIME);
			}

			ruleMap.put("frequency", PLAN_FREQUENCY);
			ruleMap.put("custom_time", StringUtil.objectToString(planInfo.get("CUSTOM_TIME")));
			ruleData = Rule.createTaskOrder(ruleMap, PLAN_CIRCLE);// 生成任务的开始和结束时间
*/
			Map parm = new HashMap(); 
			parm.put("CONTRACTOR", planInfo.get("CONTRACTOR"));
			
			String contractor = planInfo.get("CONTRACTOR").toString();
			
			//设备列表
			List<Map<String,Object>> equipmentList = patrolPlanDao.getUseFullEquipmentByPlanInfo(planInfo);

			taskMap = new HashMap();

			taskMap.put("CREATE_STAFF", staffId);//创建人
			taskMap.put("INSPECTOR", inspector);//检查人
			taskMap.put("AREA_ID", AREA_ID);//地市
			taskMap.put("SON_AREA_ID", SON_AREA_ID);//区县
			taskMap.put("AUDITOR", staffId);//审核人
			taskMap.put("REMARK", "");
			taskMap.put("INFO", "");
			taskMap.put("TASK_TYPE", 0);//周期性检查
			taskMap.put("STATUS_ID", 6);//待回单
			taskMap.put("ENABLE", "0");//可用
			taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
			taskMap.put("OLD_TASK_ID", "");//老的task_id

			for (Map<String, Object> eqpInfo : equipmentList) {
				taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
				int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息

				/*//发送信息通知，暂时
				try {
					// 获取人员的信息
					Map<String, Object> staffInfo = staffDao.getStaff(inspector);
					Map<String, Object> taskInfo = cabletaskDao.getTaskInfo(TASK_ID);
					String equipCode = taskInfo.get("TASK_NO").toString();
					// 发送短信内容
					String messageText = "";
					if (null != staffInfo &&
							staffInfo.get("STAFF_NAME") != null
							&& null != staffInfo.get("TEL")
							&& !"".equals(staffInfo.get("TEL"))) {
						messageText = staffInfo.get("STAFF_NAME") + "，您好。您于"
								+ DateUtil.getCurrentTime() + "收到" + equipCode
								+ "任务，请及时执行。【光网助手，巡检计划】";
						phoneNumList.add(staffInfo.get("TEL").toString());
						messageContentList.add(messageText);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		}
		// 发送短信
		//SendMessageUtil.sendMessageList(phoneNumList, messageContentList);
		return true;
	}
	
	/**
	 * 任务计划
	 */
	@Override
	public Map<String, Object> savePatrolPlan(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			String plan_name = request.getParameter("plan_name");
			String areaId = request.getParameter("areaId");
			String sonAreaId = request.getParameter("sonAreaId");
			String whwg = request.getParameter("whwg");
			String staffId = request.getParameter("staffId");
			String plan_type = request.getParameter("plan_type");
			String plan_circle = request.getParameter("plan_circle");
			String plan_frequency = request.getParameter("plan_frequency");
			String plan_start_time = request.getParameter("plan_start_time");
			String plan_end_time = request.getParameter("plan_end_time");

			String createStaff = (String) request.getSession().getAttribute("staffId");

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("plan_name", plan_name);
			param.put("areaId", areaId);
			param.put("sonAreaId", sonAreaId);
//			param.put("whwg", whwg);
			param.put("whwg", 0);
			
//			param.put("staffId", staffId);
			
			//根据派单关系查询人员
			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("AREA_ID", areaId);
			param2.put("SON_AREA_ID", sonAreaId);
			param2.put("PLAN_TYPE", plan_type);
			String name = patrolPlanDao.getStaffIdByRelation(param2);
			param.put("staffId", name);
			
			param.put("plan_type", plan_type);
			param.put("plan_circle", plan_circle);
			param.put("plan_frequency", plan_frequency);
			param.put("plan_start_time", plan_start_time);
			param.put("plan_end_time", plan_end_time);
			param.put("CREATE_STAFF", createStaff);
			
			//查询计划名称是否存在
			List<Map<String,Object>> planByNameList = patrolPlanDao.getPatrolPlanByName(param);
			if(planByNameList.size()>0){
				rs.put("resultCode", "001");
				rs.put("resultDesc", "计划名称已存在!");
				return rs;
			}

			/*//添加线
			cablePlanDao.insertPointLine(param);
			//添加线和点对应关系
			String [] pointArr = points.split(",");
			Map<String, Object> pointParam;
			for (int i = 0; i < pointArr.length; i++) {
				pointParam = new HashMap<String, Object>();
				pointParam.put("lineId", String.valueOf(param.get("lineId")));
				pointParam.put("pointId", pointArr[i]);
				pointParam.put("pointSeq", i+1);
				cablePlanDao.insertPointLineDetail(pointParam);
			}*/

			//生成plan_id
			String plan_id = patrolPlanDao.queryPlanId();
			param.put("plan_id", plan_id);
			    
			//添加计划
			patrolPlanDao.insertPatrolPlan(param);
			
			//添加计划相对应的设备
			param.put("staffId", 0);
			patrolPlanDao.insertRuleByTem(param);
			
			//将临时表中的设备删除
			patrolPlanDao.deleteEqTem(param);
			
			//添加计划详情
			//cablePlanDao.insertPointPlanDetail(param);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put("resultCode", "001");
			rs.put("resultDesc", "任务计划增加失败!");
			return rs;
		}
		rs.put("resultCode", "000");
		rs.put("resultDesc", "任务计划增加成功!");
		return rs;
	}
	
	/**
	 * 显示修改关键点计划页面
	 */
	@Override
	public Map<String, Object> showEditPatrolPlan(HttpServletRequest request) {
		String plan_id = request.getParameter("plan_id");
		Map<String, Object> rs = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("plan_id", plan_id);
		rs =   patrolPlanDao.getPatrolPlanById(param);
		return rs;
	}
	
	/**
	 * 保存关键点计划
	 */
	@Override
	public Map<String, Object> saveEditPatrolPlan(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			String plan_name = request.getParameter("plan_name");
			String areaId = request.getParameter("areaId");
			String sonAreaId = request.getParameter("sonAreaId");
			String whwg = request.getParameter("whwg");
			String staffId = request.getParameter("staffId");
			String plan_type = request.getParameter("plan_type");
			String plan_circle = request.getParameter("plan_circle");
			String plan_frequency = request.getParameter("plan_frequency");
			String plan_start_time = request.getParameter("plan_start_time");
			String plan_end_time = request.getParameter("plan_end_time");
			String plan_id = request.getParameter("plan_id");
			String createStaff = (String) request.getSession().getAttribute("staffId");

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("plan_name", plan_name);
			param.put("areaId", areaId);
			param.put("sonAreaId", sonAreaId);
			param.put("whwg", whwg);
			param.put("staffId", staffId);
			param.put("plan_type", plan_type);
			param.put("plan_circle", plan_circle);
			param.put("plan_frequency", plan_frequency);
			param.put("plan_start_time", plan_start_time);
			param.put("plan_end_time", plan_end_time);
			param.put("CREATE_STAFF", createStaff);
			param.put("plan_id", plan_id);
			
			/*//查询计划名称是否存在
			List<Map<String,Object>> planByNameList = patrolPlanDao.getPatrolPlanByName(param);
			if(planByNameList.size()>0){
				rs.put("resultCode", "001");
				rs.put("resultDesc", "计划名称已存在!");
				return rs;
			}*/

			//添加计划
			patrolPlanDao.updatePatrolPlan(param);
			//添加计划相对应的设备
			patrolPlanDao.insertRuleByTem(param);
			//将临时表中的设备删除
			patrolPlanDao.deleteEqTem(param);
			
			//添加计划详情
			//cablePlanDao.insertPointPlanDetail(param);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put("resultCode", "001");
			rs.put("resultDesc", "任务计划修改失败!");
			return rs;
		}
		rs.put("resultCode", "000");
		rs.put("resultDesc", "任务计划修改成功!");
		return rs;
	}
	
	@Override
	public List<Map<String, String>> getContractorListBySonAreaId(String parameter) {
		return patrolPlanDao.getContractorListBySonAreaId(parameter);
	}
	
	@Override
	public Map<String, Object> getSpectors(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String staffNo = request.getParameter("staff_no");
		String staffName = request.getParameter("staff_name");
		map.put("STAFF_NAME", staffName);
		map.put("STAFF_NO", staffNo);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		List<Map> list = patrolPlanDao.getSpectors(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list);
		return pmap;
	}
	
	@Override
	public Boolean createTask(HttpServletRequest request) {
		String inspector = request.getParameter("selected_staffId");
		String inspector_type = request.getParameter("inspector_type");
		String[] planSelected = request.getParameter("selected_planId").replace(" ", "").split(",");
		String staffId = request.getSession().getAttribute("staffId").toString();// 当前管理员

		Integer PLAN_ID = null;
		String PLAN_NO = null;
		String PLAN_START_TIME = null;
		String PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;
		
		String planType = null;

		Map taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合

		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;

		// 获取任务详细信息
		Map<String,Object> planInfo = null;
		Map map = new HashMap();
		// 更新计划分配状态map
		Map updatePlanMap = new HashMap();
		// 收信人号码
		List phoneNumList = new ArrayList();
		// 发送内容
		//List messageContentList = new ArrayList();
		
		//循环计划
		for (int i = 0, j = planSelected.length; i < j; i++) {
			map.put("PLAN_ID", planSelected[i]);

			//计划信息
			planInfo = patrolPlanDao.getPlan(map);
			
			planType = null!=planInfo.get("PLAN_TYPE")?planInfo.get("PLAN_TYPE").toString():"";
			
			PLAN_END_TIME = planInfo.get("PLAN_END_TIME").toString();

			//判断计划是否过期（现在无效，不做限制）
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date end_date = sdf.parse(PLAN_END_TIME);
				if(end_date.before(new Date())){
					continue;
				}
			} catch (ParseException e1) {

			} catch(Exception e){

			}*/

			updatePlanMap.put("PLAN_ID", planSelected[i]);
			updatePlanMap.put("TASK_INSPECTOR", staffId);
			updatePlanMap.put("TASK_CREATOR", staffId);
			updatePlanMap.put("ISDISTRIBUTED", 1);
			updatePlanMap.put("INSPECTOR_TYPE", 1);
			// 更新计划分配状态
			int updateResult = patrolPlanDao.updatePlanIsDistributed(updatePlanMap);
			
			if("1".equals(planType)){
				createContactorTask(staffId,planInfo);
			}else if("2".equals(planType)){
				createGridTask(staffId,planInfo);
			}else if("3".equals(planType)){
				createThirdTask(staffId,planInfo);
			}else if("4".equals(planType)){
				createFourthTask(staffId,planInfo);
			}else{
				System.out.println("作业计划类型异常,无法生成任务");
			}
			
			/*try {
				
			} catch (Exception e) {
				System.out.println(e);
			}*/
			
//			PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
//			PLAN_NO = planInfo.get("PLAN_NO").toString();
//			PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
//			PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
//			PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
//			AREA_ID = planInfo.get("AREA_ID").toString();
//			//SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();
//			
//			//获取承包人列表
//			List<Map<String, String>> contractorList = patrolPlanDao.getContractorListByAreaId(AREA_ID);
//			for (Map<String, String> contractorInfo : contractorList) {
//				Map searchMap = new HashMap();
//				searchMap.put("CONTRACTOR", contractorInfo.get("CONTRACT_PERSION_NO"));
//				//SON_AREA_ID = contractorInfo.get("SON_AREA_ID").toString();
//				
//				//设备列表
//				List<Map<String,Object>> equipmentList = patrolPlanDao.getUseFullEquipmentForTaskByPlanInfo(searchMap);
//				taskMap = new HashMap();
//				
//				taskMap.put("CREATE_STAFF", staffId);//创建人
//				taskMap.put("INSPECTOR", contractorInfo.get("STAFF_ID"));//检查人
//				taskMap.put("AREA_ID", AREA_ID);//地市
//				taskMap.put("SON_AREA_ID", contractorInfo.get("SON_AREA_ID"));//区县
//				taskMap.put("AUDITOR", staffId);//审核人
//				taskMap.put("REMARK", "");
//				taskMap.put("INFO", "");
//				taskMap.put("TASK_TYPE", 0);//周期性检查
//				taskMap.put("STATUS_ID", 6);//待回单
//				taskMap.put("ENABLE", "0");//可用
//				taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
//				taskMap.put("OLD_TASK_ID", "");//老的task_id
//				taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));
//				
//				//循环设备,生成任务
//				for (Map<String, Object> eqpInfo : equipmentList) {
//					taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
//					taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
//					taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
//					int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
//					
//					/*//发送信息通知，暂时
//					try {
//						// 获取人员的信息
//						Map<String, Object> staffInfo = staffDao.getStaff(inspector);
//						Map<String, Object> taskInfo = cabletaskDao.getTaskInfo(TASK_ID);
//						String equipCode = taskInfo.get("TASK_NO").toString();
//						// 发送短信内容
//						String messageText = "";
//						if (null != staffInfo &&
//								staffInfo.get("STAFF_NAME") != null
//								&& null != staffInfo.get("TEL")
//								&& !"".equals(staffInfo.get("TEL"))) {
//							messageText = staffInfo.get("STAFF_NAME") + "，您好。您于"
//									+ DateUtil.getCurrentTime() + "收到" + equipCode
//									+ "任务，请及时执行。【光网助手，巡检计划】";
//							phoneNumList.add(staffInfo.get("TEL").toString());
//							messageContentList.add(messageText);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}*/
//				}
//			}
			
		}
		// 发送短信
		//SendMessageUtil.sendMessageList(phoneNumList, messageContentList);
		return true;
	}

	/**
	 * 市公司资源中心
	 * @param staffId
	 * @param planInfo
	 */
	private void createFourthTask(String staffId, Map<String, Object> planInfo){
		
		Integer PLAN_ID = null;
		String PLAN_NO = null;
		String PLAN_START_TIME = null;
		String PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;
		
		String planType = null;

		Map taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合

		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;
		
		PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
		PLAN_NO = planInfo.get("PLAN_NO").toString();
		PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
		PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
		PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
		AREA_ID = planInfo.get("AREA_ID").toString();
		//SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();
		
		//检查员
		Map inspectorSearchMap = new HashMap();
		inspectorSearchMap.put("CHECK_TYPE", 4);
		inspectorSearchMap.put("AREA_ID", AREA_ID);
		Map<String,Object> inspector = patrolPlanDao.getInspectorsByMap(inspectorSearchMap);		
//		Map<String,Object> inspector = patrolPlanDao.getFourthInspectorByAreaId(AREA_ID);

		if(null==inspector||null==inspector.get("STAFF_ID")||"".equals(inspector.get("STAFF_ID"))){
			return;
		}else{
			//一、每月检查每个区县公司1名承包人承包的设备至少1个;
			//1、获取市下的所有区县
			List<Map<String,Object>> contractorSonAreaIdList = patrolPlanDao.getContractorSonAreaIDListByAreaId(AREA_ID);
			//2、循环获取各个区县下的一个承包人和设备
			for (Map<String, Object> sonArea : contractorSonAreaIdList) {
				String contractorSonAreaId = sonArea.get("SON_AREA_ID").toString();
				
				Map<String,Object> contractorEquipment = patrolPlanDao.getOneEquipmentOfOneContractorOfSonAreaBySonAreaId(contractorSonAreaId);
				
	            taskMap = new HashMap();
				
				taskMap.put("CREATE_STAFF", staffId);//创建人
				taskMap.put("INSPECTOR", inspector.get("STAFF_ID"));//检查人
				taskMap.put("AREA_ID", AREA_ID);//地市
				taskMap.put("SON_AREA_ID", contractorEquipment.get("SON_AREA_ID"));//区县
				taskMap.put("AUDITOR", staffId);//审核人
				taskMap.put("REMARK", "");
				taskMap.put("INFO", "");
				taskMap.put("TASK_TYPE", 0);//周期性检查
				taskMap.put("STATUS_ID", 6);//待回单
				taskMap.put("ENABLE", "0");//可用
				taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
				taskMap.put("OLD_TASK_ID", "");//老的task_id
				taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));
				
				taskMap.put("TASK_NO", contractorEquipment.get("EQUIPMENT_NO") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("TASK_NAME", contractorEquipment.get("EQUIPMENT_NO") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("SBID", contractorEquipment.get("EQUIPMENT_ID"));
				int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
			}
			
			
			//二:每月复核每个区县公司已检查的光交、ODF至少各2个；
			List<Map<String,Object>> sonAreaList = patrolPlanDao.getSonAreaIDListByAreaId(AREA_ID);
			for (Map<String, Object> map : sonAreaList) {
				List<Map<String,Object>> checkedEquipmentList = patrolPlanDao.getCheckedEquipmentBySonAreaId(map.get("AREA_ID").toString());

				taskMap = new HashMap();

				taskMap.put("CREATE_STAFF", staffId);//创建人
				taskMap.put("INSPECTOR", inspector.get("STAFF_ID"));//检查人
				taskMap.put("AREA_ID", AREA_ID);//地市
				taskMap.put("SON_AREA_ID", map.get("AREA_ID").toString());//区县
				taskMap.put("AUDITOR", staffId);//审核人
				taskMap.put("REMARK", "");
				taskMap.put("INFO", "");
				taskMap.put("TASK_TYPE", 0);//周期性检查
				taskMap.put("STATUS_ID", 6);//待回单
				taskMap.put("ENABLE", "0");//可用
				taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
				taskMap.put("OLD_TASK_ID", "");//老的task_id
				taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));

				//循环设备,生成任务
				for (Map<String, Object> eqpInfo : checkedEquipmentList) {
					taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
					int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
				}
			}
		}
		
		/*List<Map<String,Object>> contractorList = patrolPlanDao.getThirdContractorListByAreaId(AREA_ID);
		
		for (Map<String, Object> contractorInfo : contractorList) {
			planInfo.put("CONTRACTOR", contractorInfo.get("CONTRACT_PERSION_NO"));
			//设备列表
			List<Map<String,Object>> equipmentList = patrolPlanDao.getUseFullEquipmentByPlanInfo(planInfo);
			taskMap = new HashMap();
			
			taskMap.put("CREATE_STAFF", staffId);//创建人
			taskMap.put("INSPECTOR", contractorInfo.get("STAFF_ID"));//检查人
			taskMap.put("AREA_ID", AREA_ID);//地市
			taskMap.put("SON_AREA_ID", contractorInfo.get("SON_AREA_ID"));//区县
			taskMap.put("AUDITOR", staffId);//审核人
			taskMap.put("REMARK", "");
			taskMap.put("INFO", "");
			taskMap.put("TASK_TYPE", 0);//周期性检查
			taskMap.put("STATUS_ID", 6);//待回单
			taskMap.put("ENABLE", "0");//可用
			taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
			taskMap.put("OLD_TASK_ID", "");//老的task_id
			taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));
			
			//循环设备,生成任务
			for (Map<String, Object> eqpInfo : equipmentList) {
				taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
				int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
			}
		}*/
	}
	
	/**
	 * 市县公司专业中心
	 * @param staffId
	 * @param planInfo
	 */
	private void createThirdTask(String staffId, Map<String, Object> planInfo) {
		Integer PLAN_ID = null;
		String PLAN_NO = null;
		String PLAN_START_TIME = null;
		String PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;

		String planType = null;

		Map taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合

		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;

		PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
		PLAN_NO = planInfo.get("PLAN_NO").toString();
		PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
		PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
		PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
		AREA_ID = planInfo.get("AREA_ID").toString();
		//SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();
		
		Map inspectorSearchMap = new HashMap();
		inspectorSearchMap.put("CHECK_TYPE", 3);
		inspectorSearchMap.put("AREA_ID", AREA_ID);
		Map<String,Object> inspector = patrolPlanDao.getInspectorsByMap(inspectorSearchMap);
//		Map<String,Object> inspector = patrolPlanDao.getFourthInspectorByAreaId(AREA_ID);
		
		//检查员
		if(null==inspector||null==inspector.get("STAFF_ID")||"".equals(inspector.get("STAFF_ID"))){
			return;
		}else{
			List<Map<String,Object>> contractorList = patrolPlanDao.getThirdContractorListByAreaId(AREA_ID);

			for (Map<String, Object> contractorInfo : contractorList) {
				
				planInfo.put("CONTRACTOR", contractorInfo.get("CONTRACT_PERSION_NO"));
				//设备列表
				List<Map<String,Object>> equipmentList = patrolPlanDao.getUseFullEquipmentByPlanInfo(planInfo);
				taskMap = new HashMap();

				taskMap.put("CREATE_STAFF", staffId);//创建人
				taskMap.put("INSPECTOR", inspector.get("STAFF_ID"));//检查人
				taskMap.put("AREA_ID", AREA_ID);//地市
				taskMap.put("SON_AREA_ID", contractorInfo.get("SON_AREA_ID"));//区县
				taskMap.put("AUDITOR", staffId);//审核人
				taskMap.put("REMARK", "");
				taskMap.put("INFO", "");
				taskMap.put("TASK_TYPE", 0);//周期性检查
				taskMap.put("STATUS_ID", 6);//待回单
				taskMap.put("ENABLE", "0");//可用
				taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
				taskMap.put("OLD_TASK_ID", "");//老的task_id
				taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));

				//循环设备,生成任务
				for (Map<String, Object> eqpInfo : equipmentList) {
					taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
					int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
				}
			}
		}
	}
	
	/**
	 * 网格检查
	 * @param staffId
	 * @param planInfo
	 */
	private void createGridTask(String staffId, Map<String, Object> planInfo) {
		Integer PLAN_ID = null;
		String PLAN_NO = null;
		String PLAN_START_TIME = null;
		String PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;
		
		String planType = null;
		
		Map taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合
		
		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;
		
		PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
		PLAN_NO = planInfo.get("PLAN_NO").toString();
		PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
		PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
		PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
		AREA_ID = planInfo.get("AREA_ID").toString();
		//SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();
		
		Map gridSearchMap = new HashMap();
		gridSearchMap.put("AREA_ID", AREA_ID);
		//根据地区ID获取网格列表
		List<Map<String, Object>> gridList = patrolPlanDao.getGridListByAreaId(gridSearchMap);
		
		for (Map<String, Object> gridInfo : gridList) {
			Integer GRID_ID = Integer.valueOf(gridInfo.get("GRID_ID").toString());
			Map inspectorSearchMap = new HashMap();
			inspectorSearchMap.put("CHECK_TYPE", 2);
			inspectorSearchMap.put("GRID_ID", GRID_ID);
			Map<String,Object> inspector = patrolPlanDao.getInspectorsByMap(inspectorSearchMap);
			
			if(null==inspector||null==inspector.get("STAFF_ID")||"".equals(inspector.get("STAFF_ID"))){
				continue;
			}
			
			//获取网格下1/3承包人(暂时一个网格最多两个承包人,1/3取不到数据,所以先没加1/3)
			List<Map<String, String>> contractorList = patrolPlanDao.getContractorListByGridId(GRID_ID);
			for (Map<String, String> contractorInfo : contractorList) {
				Map searchMap = new HashMap();
				searchMap.put("CONTRACTOR", contractorInfo.get("STAFF_NO"));
				//设备列表
				List<Map<String,Object>> equipmentList = patrolPlanDao.getGridEquipmentForTaskByContractor(searchMap);
				taskMap = new HashMap();
				
				taskMap.put("CREATE_STAFF", staffId);//创建人
				taskMap.put("INSPECTOR", inspector.get("STAFF_ID"));//检查人
				taskMap.put("AREA_ID", AREA_ID);//地市
				// taskMap.put("SON_AREA_ID", contractorInfo.get("SON_AREA_ID"));//区县
				taskMap.put("AUDITOR", "");//审核人
				taskMap.put("REMARK", "");
				taskMap.put("INFO", "");
				taskMap.put("TASK_TYPE", 0);//周期性检查
				taskMap.put("STATUS_ID", 6);//待回单
				taskMap.put("ENABLE", "0");//可用
				taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
				
				taskMap.put("OLD_TASK_ID", "");//老的task_id
				taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));
				
				//循环设备,生成任务
				for (Map<String, Object> eqpInfo : equipmentList) {
					taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
					int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
				}
				
			}
		}
		
	}

	/**
	 * 承包人检查
	 * @param staffId
	 * @param planInfo
	 */
	private void createContactorTask(String staffId, Map<String, Object> planInfo) {
		Integer PLAN_ID = null;
		String PLAN_NO = null;
		String PLAN_START_TIME = null;
		String PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;
		
		String planType = null;

		Map taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合

		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;
		
		PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
		PLAN_NO = planInfo.get("PLAN_NO").toString();
		PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
		PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
		PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
		AREA_ID = planInfo.get("AREA_ID").toString();
		//SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();
		
		//根据地区ID获取承包人列表
		List<Map<String, String>> contractorList = patrolPlanDao.getContractorListByAreaId(AREA_ID);
		for (Map<String, String> contractorInfo : contractorList) {
			Map searchMap = new HashMap();
			searchMap.put("CONTRACTOR", contractorInfo.get("CONTRACT_PERSION_NO"));
			//SON_AREA_ID = contractorInfo.get("SON_AREA_ID").toString();
			
			//根据承包人获取承包人下离最近一个月端口变动最大设备最近的1/6未被检查设备  getUseFullEquipmentForTaskByPlanMap
//			List<Map<String,Object>> equipmentList = patrolPlanDao.getUseFullEquipmentForTaskByPlanInfo(searchMap);
			
			List<Map<String,Object>> equipmentList = patrolPlanDao.getUseFullEquipmentForTaskByPlanMap(searchMap);
			
			taskMap = new HashMap();
			
			taskMap.put("CREATE_STAFF", staffId);//创建人
			taskMap.put("INSPECTOR", contractorInfo.get("STAFF_ID"));//检查人
			taskMap.put("AREA_ID", AREA_ID);//地市
			taskMap.put("SON_AREA_ID", contractorInfo.get("SON_AREA_ID"));//区县
			taskMap.put("AUDITOR", staffId);//审核人
			taskMap.put("REMARK", "");
			taskMap.put("INFO", "");
			taskMap.put("TASK_TYPE", 0);//周期性检查
			taskMap.put("STATUS_ID", 6);//待回单
			taskMap.put("ENABLE", "0");//可用
			taskMap.put("NO_EQPNO_FLAG", "0");//无编码上报
			taskMap.put("OLD_TASK_ID", "");//老的task_id
			taskMap.put("PATROL_PLAN_ID", planInfo.get("PLAN_ID"));
			
			//循环设备,生成任务
			for (Map<String, Object> eqpInfo : equipmentList) {
				taskMap.put("TASK_NO", eqpInfo.get("EQUIPMENT_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("TASK_NAME", eqpInfo.get("EQUIPMENT_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
				taskMap.put("SBID", eqpInfo.get("EQUIPMENT_ID"));
				int result = patrolPlanDao.saveTask(taskMap);// 保存任务信息
			}
		}
	}

	@Override
	public Map<String, Object> saveEditPatrolPlanRule(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			String areaId = request.getParameter("areaId");
			String plan_id = request.getParameter("plan_id");
			String plan_name = request.getParameter("plan_name");
			String plan_type = request.getParameter("plan_type");
			String plan_circle = request.getParameter("plan_circle");
			String plan_frequency = request.getParameter("plan_frequency");
			String plan_start_time = request.getParameter("plan_start_time");
			String plan_end_time = request.getParameter("plan_end_time");
			/*String son_area_id = request.getParameter("son_area_id");*/

//			String contractor = request.getParameter("contractor");
			String distribution_amount = request.getParameter("distribution_amount");
			
			String FIRST_AB_CYCLE = request.getParameter("FIRST_AB_CYCLE");
			String FIRST_CD_CYCLE = request.getParameter("FIRST_CD_CYCLE");
			String FIRST_OTHER_CYCLE = request.getParameter("FIRST_OTHER_CYCLE");

			//			String points = request.getParameter("points");

			String staffId = (String) request.getSession().getAttribute("staffId");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("areaId", areaId);
			param.put("plan_id", plan_id);
			param.put("plan_name", plan_name);
			param.put("plan_type", plan_type);
			param.put("plan_circle", plan_circle);
			param.put("plan_frequency", plan_frequency);
			param.put("plan_start_time", plan_start_time);
			param.put("plan_end_time", plan_end_time);
			/*param.put("son_area_id", son_area_id);*/
			//			param.put("points", points);

//			param.put("contractor", contractor);
			param.put("distribution_amount", distribution_amount);


			param.put("FIRST_AB_CYCLE", FIRST_AB_CYCLE);
			param.put("FIRST_CD_CYCLE", FIRST_CD_CYCLE);
			param.put("FIRST_OTHER_CYCLE", FIRST_OTHER_CYCLE);
			
			param.put("staffId", staffId);
			//查询计划名称是否存在
			List<Map<String,Object>> planByNameList = patrolPlanDao.getEditPatrolPlanByName(param);
			if(planByNameList.size()>0){
				rs.put("resultCode", "001");
				rs.put("resultDesc", "计划名称已存在!");
				return rs;
			}
			/*//编辑线
			cablePlanDao.updatePointLine(param);
			List<Map<String,Object>> lineList = cablePlanDao.getPointLineId(param);
			if(lineList.size()>0){
				param.put("lineId", lineList.get(0).get("LINEID"));
			}else{
				rs.put("resultCode", "001");
				rs.put("resultDesc", "缆线不存在!");
				return rs;
			}
			//删除线和点对应关系
			cablePlanDao.deletePointLineDetail(param);
			//添加线和点对应关系
			String [] pointArr = points.split(",");
			Map<String, Object> pointParam;
			for (int i = 0; i < pointArr.length; i++) {
				pointParam = new HashMap<String, Object>();
				pointParam.put("lineId", String.valueOf( lineList.get(0).get("LINEID")));
				pointParam.put("pointId", pointArr[i]);
				pointParam.put("pointSeq", i+1);
				cablePlanDao.insertPointLineDetail(pointParam);
			}*/
			//编辑计划
			int updateResult = patrolPlanDao.saveEditPatrolPlanRule(param);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put("resultCode", "001");
			rs.put("resultDesc", "巡检计划编辑失败!");
			return rs;
		}
		rs.put("resultCode", "000");
		rs.put("resultDesc", "巡检计划编辑成功!");
		return rs;
	}

	@Override
	public List<Map<String, Object>> getStaffListByWHWGId(String whwgId) {
		
		return patrolPlanDao.getStaffListByWHWGId(whwgId);
	}

	@Override
	public List<Map<String, Object>> getRule(Map<String, Object> map) {
		
		return patrolPlanDao.getRule(map);
	}

	@Override
	public void createNewTask(HttpServletRequest request) {
		
		//将规则放入map
		Map<String, Object> param = new HashMap<String, Object>();
		String planId = request.getParameter("plan_id");
		String planType = null;  //计划类型
		//根据planId获取计划
		param.put("PLAN_ID", planId);
		
		//判断计划是否已生成任务
		List<Map<String, Object>> taskList = patrolPlanDao.queryEqListByPlanId(param);
		if(null != taskList && taskList.size() > 0){
			//已生成任务，无需重复生成
			return;
		}else{
			//无任务，开始生成任务
					
			//根据任务计划id获取到该计划的规则
			List<Map<String, Object>> ruleList = patrolPlanDao.getRuleByPlanId(planId);

			//计划信息
			param = patrolPlanDao.getPlan(param);
			planType = param.get("PLAN_TYPE").toString();
			
			if(null != ruleList && ruleList.size() >0 ){
				for (int i = 0; i < ruleList.size(); i++) {
					param.put(ruleList.get(i).get("KEY_NO").toString(), ruleList.get(i).get("KEY_VALUE"));
				}
			}
			
			//根据规则获取需要派发设备
			List<Map<String, Object>> eqList = null;
			
			if("1".equals(planType)){
				String longitude = null;   //经度
				String latitude = null;    //纬度
				//承包人检查
				//查询变动量最大的设备
				Map<String, Object> maxMap = patrolPlanDao.queryMaxBDNum(param);
				
				longitude = null == maxMap.get("LONGITUDE_INSPECT") ? maxMap.get("LONGITUDE").toString():maxMap.get("LONGITUDE_INSPECT").toString();
				latitude = null == maxMap.get("LATITUDE_INSPECT") ? maxMap.get("LATITUDE").toString():maxMap.get("LATITUDE_INSPECT").toString();
				
				param.put("LONGITUDE", longitude);
				param.put("LATITUDE", latitude);
				
				eqList = patrolPlanDao.geteqListByContactorRule(param);
			}
			
			//除了承包人检查之外的情况都统一
			else{
				eqList = patrolPlanDao.geteqListDead(param);
			}
			
//			else if("2".equals(planType)){
//				//网格检查
//				eqList = patrolPlanDao.geteqListByGridRule(param);
//			}else if("3".equals(planType)){
//				//市县公司专业中心检查
//				eqList = patrolPlanDao.geteqListByCountiesRule(param);
//			}else if("4".equals(planType)){
//				//市公司资源中心检查
//				eqList = patrolPlanDao.geteqListByCityRule(param);
//			}
			
			//先将此plan_id的设备删除
//			patrolPlanDao.deleteForPlanByRule(planId);
			
			
			//将获取到的设备插入表中
			if(null != eqList && eqList.size() > 0){
				for (Map<String, Object> map : eqList) {
					map.put("PLAN_ID", planId);
					if("1".equals(planType)){   //承包人检查时，检查人，为每个设备的承包人
						//根据身份证号获取staff_id
						if(null != map.get("IDENTIFY") && !"".equals(map.get("IDENTIFY"))){
							String identify = map.get("IDENTIFY").toString();
							Map<String, Object> staffMap = patrolPlanDao.getStaffIdByIdentify(identify);
							if(null != staffMap){
								map.put("CHECK_STAFF", staffMap.get("STAFF_ID")==null ?"":staffMap.get("STAFF_ID"));
							}else{
								map.put("CHECK_STAFF", "");
							}
						}else{
							map.put("CHECK_STAFF", "");
						}
						
						
					}else{
						map.put("CHECK_STAFF", param.get("CHECK_STAFF"));  //其他计划类型检查人为创建计划时的检查人
					}
					map.put("PLAN_TYPE", planType);
					
					patrolPlanDao.inserteqForPlanByRule(map);
				}
			}		
		}
	}

	@Override
	public Map<String, Object> getEqByPlanAndRuleForNewTask(
			HttpServletRequest request, UIPage pager) {
		
		String plan_id = request.getParameter("plan_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PLAN_ID", plan_id);
		
		map = patrolPlanDao.getPlan(map);
		String planType = map.get("PLAN_TYPE").toString();
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map<String, Object>> taskList = null;
		
		if("1".equals(planType)){  //承包人检查单独查询sql
			taskList = patrolPlanDao.getEqByPlanAndRuleForNewTask2(query);
		}else{
			taskList = patrolPlanDao.getEqByPlanAndRuleForNewTask(query);
			
			
			//查询人员名称
			for (Map<String, Object> map2 : taskList) {
				String planId = map2.get("PLAN_ID")==null?"":map2.get("PLAN_ID").toString();
//				String staff = map2.get("CHECK_STAFF")==null?"":map2.get("CHECK_STAFF").toString();
				
				
				Map map_pa2 = new HashMap();
				map_pa2.put("planId", planId);
				String staff = patrolPlanDao.getStaffId(map_pa2);
//				String[] str = staff.split(",");
//				List list = new ArrayList();
				Map map_pa = new HashMap();
//				for (int i = 0; i < str.length; i++) {
//					list.add(str[i]);
//				}
				map_pa.put("staffList", staff);
				String staffName = patrolPlanDao.getStaffName(map_pa);
				map2.put("STAFF_NAME", staffName);
				
				String gridName = patrolPlanDao.getGridName(map_pa);
				map2.put("GRID_NAME", gridName);
			}
			
			
			
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", taskList);
		return pmap;
	}

	@Override
	public String distributeTask(HttpServletRequest request) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		
		String result = "fail";
		String plan_id = request.getParameter("plan_id");
		String check_staff = request.getParameter("check_staff");
		
		String check_staffs [] = check_staff.split(",");
		
		//判断是否已派发过
		//根据plan_id查询任务表中是否存在此计划的任务
//		List<Map<String, Object>> eqList = patrolPlanDao.queryTaskByPlanId(plan_id);
//		if(null != eqList && eqList.size() > 0){
//			result = "completed";
//			return result;
//		}
		
		
		// 派发任务
		Map<String, Object> param = new HashMap();
		param.put("PLAN_ID", plan_id);
		param = patrolPlanDao.getPlan(param);
		
		String planType = param.get("PLAN_TYPE").toString();  //计划类型

		Map<String, Object> taskMap = new HashMap();
		taskMap.put("CREATE_STAFF", staffId);// 创建人
		taskMap.put("AREA_ID",param.get("AREA_ID") == null ? "" : param.get("AREA_ID"));// 地市
		taskMap.put("SON_AREA_ID", param.get("SON_AREA_ID") == null ? "": param.get("SON_AREA_ID"));// 区县
		taskMap.put("AUDITOR", "");// 审核人
		taskMap.put("REMARK", "");
		taskMap.put("INFO", "");
		
		Map<String, Object> flowParams = new HashMap<String, Object>();
		flowParams.put("oper_staff", staffId);
		flowParams.put("status", 6);
		
		// 10：三级派发-承包人检查   11：三级派发-网格检查   12：三级派发-市县专业中心检查   13：三级派发-市资源中心检查
		if("1".equals(planType)){
			taskMap.put("TASK_TYPE", 10);
			flowParams.put("remark", "派发工单-三级派发-承包人检查");
		}else if("2".equals(planType)){
			taskMap.put("TASK_TYPE", 11);
			flowParams.put("remark", "派发工单-三级派发-网格检查");
		}else if("3".equals(planType)){
			taskMap.put("TASK_TYPE", 12);
			flowParams.put("remark", "派发工单-三级派发-市县专业中心检查");
		}else if("4".equals(planType)){
			taskMap.put("TASK_TYPE", 13);
			flowParams.put("remark", "派发工单-三级派发-市资源中心检查");
		}
		
		taskMap.put("STATUS_ID", 6);// 待回单
		taskMap.put("ENABLE", "0");// 可用
		taskMap.put("NO_EQPNO_FLAG", "0");// 无编码上报
		taskMap.put("OLD_TASK_ID", "");// 老的task_id
		taskMap.put("PATROL_PLAN_ID", plan_id);
		
		Map<String, Object> sbMap = new HashMap<String, Object>();
		Date date = new Date();
		String dz_start_time = DateUtil.getFirstDayOfLastMonth(date);
		String dz_end_time = DateUtil.getLastDayOfLastMonth(date);
		
		sbMap.put("DZ_START_TIME", dz_start_time);
		sbMap.put("DZ_END_TIME", dz_end_time);
		
		for (int i = 0; i < check_staffs.length; i++) {
			param.put("CHECK_STAFF", check_staffs[i]);
			flowParams.put("receiver", check_staffs[i]);
			//获取该计划下符合规则的设备
			List<Map<String,Object>> equipmentList = patrolPlanDao.queryEqListByPlanId(param);
			String task_id = null;
			try {
				//循环设备,生成任务
				for (Map<String, Object> eqpInfo : equipmentList) {
					task_id = patrolPlanDao.queryTaskId();//生成task_id
					
					taskMap.put("TASK_ID", task_id);
					taskMap.put("TASK_NO", eqpInfo.get("EQ_CODE") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("TASK_NAME", eqpInfo.get("EQ_NAME") + "_" + com.linePatrol.util.DateUtil.getDate("yyyyMMdd"));
					taskMap.put("SBID", eqpInfo.get("EQ_ID"));
					taskMap.put("INSPECTOR",check_staffs[i]);// 检查人
					patrolPlanDao.saveTask(taskMap);// 保存任务信息
					
					sbMap.put("EQUIPMENT_ID", eqpInfo.get("EQ_ID"));
					//根据设备id获取端子
			    	List<Map<String, Object>> dzList = taskMangerDao.terminalQuery1(sbMap);
			    	
			    	//保存任务详细
					for (Map<String, Object> dzMap : dzList) {
						Map taskDetailMap = new HashMap();
						taskDetailMap.put("TASK_ID", task_id);
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
						taskMangerDao.doSaveTaskDetail(taskDetailMap);
						taskMangerDao.updateDtsj(dzMap);  //派发成功修改端子派发状态
					}
					
					//修改设备状态
					patrolPlanDao.updateEqState(eqpInfo);
					
					//添加流程记录
					flowParams.put("task_id", task_id);
					String content = "管理员派发工单，生成任务";
					flowParams.put("content", content);
					checkProcessDao.addProcessNew(flowParams);

				}
				result = "success";
				
			} catch (Exception e) {
				e.printStackTrace();
				result = "fail";
			}
		}
		Map<String, Object> updatePlanMap = new HashMap<String, Object>();
		updatePlanMap.put("PLAN_ID", plan_id);
		updatePlanMap.put("TASK_INSPECTOR", staffId);
		updatePlanMap.put("TASK_CREATOR", staffId);
		updatePlanMap.put("ISDISTRIBUTED", 1);
		updatePlanMap.put("INSPECTOR_TYPE", 1);
		// 更新计划分配状态
		patrolPlanDao.updatePlanIsDistributed(updatePlanMap);
		
		return result;
	}

	@Override
	public Map<String, Object> queryEq(HttpServletRequest request, UIPage pager) {
		String plan_id = request.getParameter("plan_id");
		String check_staff = request.getParameter("check_staff");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PLAN_ID", plan_id);
		map.put("CHECK_STAFF", check_staff);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> eqList = patrolPlanDao.queryEq(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", eqList);
		return pmap;
	}

	@Override
	public String deleteEq(HttpServletRequest request) {
		String result="fail";
		String ids = request.getParameter("ids");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		try {
			patrolPlanDao.deleteEq(map);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> saveEqByPlanRule(HttpServletRequest request) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		
		String result = "success"; 
		
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		String plan_type = request.getParameter("plan_type");
		String whwg = request.getParameter("whwg");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AREA_ID", area_id);
		map.put("SON_AREA_ID", son_area_id);
		map.put("PLAN_TYPE", plan_type);
		map.put("WHWG", whwg);
		map.put("CHECK_STAFF", staffId);
		
		try {
			
			map.put("CREATE_STAFF", staffId);
			//先将staffId下的设备删除
			patrolPlanDao.deleteEqTem(map);
			
			//根据规则获取符合要求的设备，新增入临时表
			//获取规则
			List<Map<String, Object>> ruleList = patrolPlanDao.getRule(map);

			if(null != ruleList && ruleList.size() >0 ){
				for (int i = 0; i < ruleList.size(); i++) {
					map.put(ruleList.get(i).get("KEY_NO").toString(), ruleList.get(i).get("KEY_VALUE"));
				}
			}
			//根据规则获取预览设备
			List<Map<String, Object>> eqList = null;
			
			
			//
			List<Map<String,Object>> gridListAll = patrolPlanDao.getGridIdByRelation(map);
			
			map.put("gridListAll", gridListAll);//GRID_ID
			
			if("1".equals(plan_type)){
				//承包人检查暂时无预览
			}
				//除了承包人检查之外的情况都统一
			else{
				eqList = patrolPlanDao.geteqListDead(map);
			}
			
//			else if("2".equals(plan_type)){
//				//网格检查
////				eqList = patrolPlanDao.geteqListByGridRule(map);
//				eqList = patrolPlanDao.geteqListDead(map);
//			}else if("3".equals(plan_type)){
//				//市县公司专业中心检查
//				eqList = patrolPlanDao.geteqListByCountiesRule(map);
//			}else if("4".equals(plan_type)){
//				//市公司资源中心检查
//				eqList = patrolPlanDao.geteqListByCityRule(map);
//			}	
			
			//将获取到的设备插入表中
			if(null != eqList && eqList.size() > 0){
				for (Map<String, Object> eqMap : eqList) {
					
					if("1".equals(plan_type)){
						
					}else{
						eqMap.put("CREATE_STAFF", staffId);  //创建人
					}
					eqMap.put("PLAN_TYPE", plan_type);
					
					patrolPlanDao.inserteqForPlanByRuleTem(eqMap);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("result", result);
		return pMap;
	}

	@Override
	public Map<String, Object> queryEqByPlanRule(HttpServletRequest request,
			UIPage pager) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CREATE_STAFF", staffId);
		
		//从临时表中获取预览的设备
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map<String, Object>> olists = patrolPlanDao.queryEqByPlanRuleTem(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	@Override
	public Map<String, Object> getGridNum(HttpServletRequest request,
			UIPage pager) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CREATE_STAFF", staffId);
		
		//从临时表中获取预览的设备
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map<String, Object>> olists = patrolPlanDao.getGridNum(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	

	@Override
	public String deleteEqForPreview(HttpServletRequest request) {
		String result="fail";
		String ids = request.getParameter("ids");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		try {
			patrolPlanDao.deleteEqForPreview(map);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String addSureSelect(HttpServletRequest request){
		String result="fail";
		String ids = request.getParameter("ids");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		try {
			patrolPlanDao.addSureSelect(map);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	@Override
	public Map<String, Object> queryEqForPreview(HttpServletRequest request,
			UIPage pager) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		String whwg = request.getParameter("whwg");
		String res_type_id = request.getParameter("res_type_id");
		String eq_name = request.getParameter("eq_name");
		String eq_code = request.getParameter("eq_code");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AREA_ID", area_id);
		map.put("SON_AREA_ID", son_area_id);
		map.put("WHWG", whwg);
		map.put("CREATE_STAFF",staffId);
		map.put("RES_TYPE_ID", res_type_id);
		map.put("EQ_NAME", eq_name);
		map.put("EQ_CODE", eq_code);
		
		
		String plan_type = request.getParameter("plan_type");
		map.put("PLAN_TYPE", plan_type);
		//根据选择的地市和区县以及计划类型，去派单关系查询相关的网格id
		List<Map<String,Object>> gridListAll = patrolPlanDao.getGridIdByRelation(map);
		
		map.put("gridListAll", gridListAll);//GRID_ID
		
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> eqList = patrolPlanDao.queryEqForPreview(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", eqList);
		return pmap;
	}

	@Override
	public Map<String, Object> queryRule(HttpServletRequest request,
			UIPage pager) {
		String plan_type = request.getParameter("plan_type");
		String area_id = request.getParameter("area_id");
		String son_area_id = request.getParameter("son_area_id");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PLAN_TYPE", plan_type);
		map.put("AREA_ID", area_id);
		map.put("SON_AREA_ID", son_area_id);
		
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);
		
		List<Map<String, Object>> ruleList = patrolPlanDao.queryRule(query);
		
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", ruleList);
		return pmap;
	}

	@Override
	public String addEqForPreview(HttpServletRequest request) {
		String staffId = request.getSession().getAttribute("staffId").toString();
		
		String result="fail";
		String ids = request.getParameter("ids");
		String plan_type = request.getParameter("plan_type");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("PLAN_TYPE", plan_type);
		map.put("CREATE_STAFF", staffId);
		
		try {
			patrolPlanDao.addEqForPreview(map);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getRuleForAdd(Map<String, Object> map) {
	
		return patrolPlanDao.getRuleForAdd(map);
	}

	@Override
	public String updateRule(HttpServletRequest request) {
		

		String staffId = request.getSession().getAttribute("staffId").toString();
		
		String result="fail";
		String plan_ids = request.getParameter("plan_ids");
		String key_values = request.getParameter("key_values");
		
		String planIds[] = plan_ids.split(",");
		String keyValues[] = key_values.split(",");
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(planIds.length !=keyValues.length){
			return result;
		}
		try {
			map.put("CREATE_STAFF", staffId);
			for (int i = 0; i < planIds.length; i++) {
				map.put("PLAN_ID", planIds[i]);
				map.put("KEY_VALUE", keyValues[i]);
				patrolPlanDao.updateRule(map);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String queryIfHavaTask(HttpServletRequest request) {
		
		String result="fail";
		
		String plan_id = request.getParameter("plan_id");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PLAN_ID", plan_id);
		
		//判断是否已生成任务
		List<Map<String, Object>> taskList = patrolPlanDao.queryEqListByPlanId(param);
		if(null != taskList && taskList.size()>0){
			result="success";
		}
		
		return result;
	}
	
	
	@Override
	public Map<String, Object> queryEqpGroupGrid(HttpServletRequest request,UIPage pager){
		String staffId = request.getSession().getAttribute("staffId").toString();
		
		String areaId = request.getParameter("area_id")==null?"3":request.getParameter("area_id");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CREATE_STAFF", staffId);
		
		map.put("areaId", areaId);
		map.put("startTime", getTime("start"));
		map.put("endTime", getTime("end"));
		
		//从临时表中获取预览的设备
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map<String, Object>> olists = patrolPlanDao.queryEqpGroupGrid(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	
	public String getTime(String type){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result="";
		if("end".equals(type)){
			calendar.add(Calendar.MONTH, -2);
			calendar.set(Calendar.DAY_OF_MONTH, 1); 
			calendar.add(Calendar.DATE, -1);
			result = sdf.format(calendar.getTime())+" 23:59:59";
		}else{
			calendar.add(Calendar.MONTH, -3);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			result = sdf.format(calendar.getTime())+" 00:00:00";
		}
		return result;
	}
}
