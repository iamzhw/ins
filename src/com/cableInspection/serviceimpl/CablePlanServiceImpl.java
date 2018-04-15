package com.cableInspection.serviceimpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.dataSource.SwitchDataSourceUtil;
import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.ArrivalDao;
import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.CablePlanDao;
import com.cableInspection.dao.CableTaskDao;
import com.cableInspection.dao.PointManageDao;
import com.cableInspection.model.CableModel;
import com.cableInspection.model.PointModel;
import com.cableInspection.service.CablePlanService;
import com.cableInspection.service.CableService;
import com.system.constant.RoleNo;
import com.system.dao.StaffDao;
import com.util.DateUtil;
import com.util.Rule;
import com.util.StringUtil;
import com.util.sendMessage.SendMessageUtil;

@SuppressWarnings("all")
@Service
public class CablePlanServiceImpl implements CablePlanService {

	@Resource
	private CablePlanDao cablePlanDao;

	@Resource
	private CableDao cableDao;

	@Resource
	private StaffDao staffDao;

	@Resource
	private CableTaskDao cabletaskDao;
	@Resource
	private PointManageDao pointManageDao;
	@Resource
	private ArrivalDao arrivalDao;
	@Resource
	private CableService cableService;
	
	@Override
	public List<Map> getPoints(HttpServletRequest request) {
		return cablePlanDao.getPoints();
	}

	@Override
	public Map<String, Object> planQuery(HttpServletRequest request,
			UIPage pager) {
		String plan_no = request.getParameter("plan_no");
		String plan_name = request.getParameter("plan_name");
		String plan_start_time = request.getParameter("plan_start_time");
		String plan_end_time = request.getParameter("plan_end_time");
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		int ifGly = staffDao.getifGly(staffId);
		String son_area_id = request.getParameter("son_area_id");
		String plan_type = request.getParameter("plan_type");
		Map map = new HashMap();

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			// map.put("AREA_ID", session.getAttribute("areaId"));
			map.put("SON_AREA_ID", son_area_id);
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				map.put("SON_AREA_ID", son_area_id);
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
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
		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = cablePlanDao.planQuery(query);
		int nos=1;
		String no="";
		try{
		for (Map map2 : olists) {
			if(("0").equals(map2.get("INSPECTOR_TYPE").toString())
					&&("已分配").equals(map2.get("ISDISTRIBUTED").toString())
					&&null!=map2.get("INSPECTOR_NO")){
					SwitchDataSourceUtil.setCurrentDataSource("orcl153");
					no=map2.get("INSPECTOR_NO").toString();
					nos=cablePlanDao.ifStaffNocorrect(no);
				if(nos==0){
					map2.put("FLAG", 1);
				}else{
					map2.put("FLAG", 0);
				}
			}else{
				map2.put("FLAG", 0);
			}
		}
		SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			SwitchDataSourceUtil.clearDataSource();
		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public void save(HttpServletRequest request) {
		String points = request.getParameter("lines");
		String plan_name = request.getParameter("plan_name");
		String plan_no = request.getParameter("plan_no");
		String plan_type = request.getParameter("plan_type");//1
		String plan_start_time = request.getParameter("plan_start_time");
		String plan_end_time = request.getParameter("plan_end_time");
		String plan_circle = request.getParameter("plan_circle");//周期 
		String plan_frequency = request.getParameter("plan_frequency");//次数
		String custom_time = request.getParameter("custom_time");
		String dept_no= request.getParameter("dept_no");
		String sendType = request.getParameter("sendType");
		
		String staffId = (String) request.getSession().getAttribute("staffId");

		Map map = new HashMap();
		map.put("PLAN_NAME", plan_name);
		map.put("PLAN_NO", plan_name);
		map.put("PLAN_TYPE", plan_type);
		map.put("PLAN_START_TIME", plan_start_time);
		map.put("PLAN_END_TIME", plan_end_time);
		map.put("PLAN_CIRCLE", plan_circle);
		map.put("PLAN_FREQUENCY", plan_frequency);
		map.put("CREATE_STAFF", staffId);
		map.put("PLAN_KIND", 1);
		map.put("AREA_ID", request.getSession().getAttribute("areaId"));
		map.put("SON_AREA_ID", request.getSession().getAttribute("sonAreaId"));
		map.put("CUSTOM_TIME", custom_time);
		map.put("DEPT_NO", dept_no);
		map.put("SEND_TYPE", sendType);
		cablePlanDao.insertPlan(map);
		// 保存的计划id
		Integer plan_id = (Integer) map.get("plan_id");
		Map planDetail = null;
		String[] p = points.split(",");
		// String[] temp = null;
		for (int i = 0; i < p.length; i++) {
			planDetail = new HashMap();
			// temp = p[i].split(":");
			planDetail.put("PLAN_ID", plan_id);
			planDetail.put("INSPECT_OBJECT_ID", p[i]);
			planDetail.put("INSPECT_OBJECT_TYPE", 2);
			cablePlanDao.insertDetail(planDetail);
		}
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
		}
		if (plan_ids.endsWith(",")) {
			plan_ids = plan_ids.substring(0, plan_ids.length() - 1);
		}
		map.put("PLAN_ID", plan_ids);
		cablePlanDao.deletePlan(map);
	}

	@Override
	public Map<String, Object> editPlan(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Integer plan_id = Integer.valueOf(request.getParameter("plan_id"));
		Map map = new HashMap();
		map.put("PLAN_ID", plan_id);
		Map planInfo = cablePlanDao.getPlan(map);// 计划信息
		rs.put("planInfo", planInfo);
		return rs;
	}

	@Override
	public List<Map> getPlanDetail(HttpServletRequest request) {
		Integer plan_id = Integer.valueOf(request.getParameter("plan_id"));
		return cablePlanDao.getPlanDetail(plan_id);
	}

	@Override
	public Boolean updatePlan(HttpServletRequest request) {
		String lines = request.getParameter("lines");
		String plan_id = request.getParameter("plan_id");
		String plan_name = request.getParameter("plan_name");
		String plan_no = request.getParameter("plan_no");
		String plan_type = request.getParameter("plan_type");
		String plan_start_time = request.getParameter("plan_start_time");
		String plan_end_time = request.getParameter("plan_end_time");
		String plan_circle = request.getParameter("plan_circle");
		String plan_frequency = request.getParameter("plan_frequency");
		String custom_time = request.getParameter("custom_time");

		String staffId = (String) request.getSession().getAttribute("staffId");

		Map map = new HashMap();
		map.put("PLAN_ID", plan_id);
		map.put("PLAN_NAME", plan_name);
		map.put("PLAN_NO", plan_no);
		map.put("PLAN_TYPE", plan_type);
		map.put("PLAN_START_TIME", plan_start_time);
		map.put("PLAN_END_TIME", plan_end_time);
		map.put("PLAN_CIRCLE", plan_circle);
		map.put("PLAN_FREQUENCY", plan_frequency);
		map.put("MODIFY_STAFF", staffId);
		map.put("CUSTOM_TIME", custom_time);
		cablePlanDao.updatePlan(map);
		// 清除老的计划详细信息
		cablePlanDao.deletePlanDetail(map);
		// 保存新的计划详细信息
		Map planDetail = null;
		String[] p = lines.split(",");
		for (int i = 0; i < p.length; i++) {
			planDetail = new HashMap();
			// temp = p[i].split(":");
			planDetail.put("PLAN_ID", plan_id);
			planDetail.put("INSPECT_OBJECT_ID", p[i]);
			planDetail.put("INSPECT_OBJECT_TYPE", 2);
			cablePlanDao.insertDetail(planDetail);
		}
		return true;
	}

	@Override
	public Map<String, Object> getSpectors(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String staffNo = request.getParameter("staff_no");
		String staffName = request.getParameter("staff_name");
		String plan_ids=request.getParameter("plan_id");
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
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		List<Map> list1=new ArrayList();
		if(null==plan_ids){
			list1= cablePlanDao.getSpectors(query);
		}else{
			String[] plans = plan_ids.split(",");
			Map<String, Object> planInfo=cablePlanDao.getSonAreaIdByPlan(plans[0]);
			map.put("area_id", planInfo.get("SON_AREA_ID"));
			if(("1").equals(planInfo.get("TYPE").toString())&&!("1").equals(planInfo.get("SEND_TYPE"))){
				try{
				SwitchDataSourceUtil.setCurrentDataSource("orcl153");
				list1= cablePlanDao.getStaffFromJYH(map);
				SwitchDataSourceUtil.clearDataSource();
			} catch (Exception e) {
				SwitchDataSourceUtil.clearDataSource();
			}
				for (Map map2 : list1) {
					map2.put("STAFF_ID", cablePlanDao.getStaffIdByNo(map2));
				}
				List<Map> list2 = cablePlanDao.getStaffFromDEPT(map);
				list1.addAll(list2);
			}else{
				list1= cablePlanDao.getSpectors(query);
			}
		}
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list1);
		return pmap;
	}

	@Override
	public Boolean saveTask(HttpServletRequest request) {
		String inspector = request.getParameter("selected_staffId");
		String inspector_type = request.getParameter("inspector_type");
		String[] planSelected = request.getParameter("selected_planId")
				.replace(" ", "").split(",");
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前管理员

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
		Map planInfo = null;
		Map map = new HashMap();
		// 更新计划分配状态map
		Map updatePlanMap = new HashMap();
		// 收信人号码
		List phoneNumList = new ArrayList();
		// 发送内容
		List messageContentList = new ArrayList();
		for (int i = 0, j = planSelected.length; i < j; i++) {
			map.put("PLAN_ID", planSelected[i]);
			planInfo = cablePlanDao.getPlan(map);
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
			cablePlanDao.updatePlanIsDistributed(updatePlanMap);

			PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
			PLAN_NO = planInfo.get("PLAN_NO").toString();
			PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
			PLAN_FREQUENCY = planInfo.get("PLAN_FREQUENCY").toString();
			PLAN_CIRCLE = planInfo.get("PLAN_CIRCLE").toString();
			AREA_ID = planInfo.get("AREA_ID").toString();
			SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();

			List<Map> ruleData = null;// 生成的规则
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
			ruleMap.put("custom_time", StringUtil.objectToString(planInfo
					.get("CUSTOM_TIME")));
			ruleData = Rule.createTaskOrder(ruleMap, PLAN_CIRCLE);// 生成任务的开始和结束时间

			taskMap = new HashMap();
			taskMap.put("PLAN_ID", PLAN_ID);

			taskMap.put("PLAN_NO", PLAN_NO);
			taskMap.put("CREATE_STAFF", staffId);
			taskMap.put("INSPECTOR", inspector);
			taskMap.put("AREA_ID", AREA_ID);
			taskMap.put("SON_AREA_ID", SON_AREA_ID);
			for (Map rule : ruleData) {
				taskMap.put("COMPLETE_TIME", rule.get("endDate"));
				taskMap.put("START_TIME", rule.get("startDate"));
				cablePlanDao.saveTask(taskMap);// 保存任务信息
				TASK_ID = Integer.valueOf(taskMap.get("TASK_ID").toString());
				// 查询出计划对应的外力点信息
				planDetailList = cablePlanDao.getPlanDetail(PLAN_ID);
				// 保存任务详细信息
				taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", TASK_ID);
				taskDetailMap.put("INSPECTOR", inspector);
				for (Map planDetail : planDetailList) {
					INSPECT_OBJECT_ID = planDetail.get("LINE_ID").toString();
					INSPECT_OBJECT_TYPE = "2";
					taskDetailMap.put("INSPECT_OBJECT_ID", INSPECT_OBJECT_ID);
					taskDetailMap.put("INSPECT_OBJECT_TYPE",
							INSPECT_OBJECT_TYPE);
					cablePlanDao.saveTaskDetail(taskDetailMap);
				}
				try {
					// 获取人员的信息
					Map<String, Object> staffInfo = staffDao
							.getStaff(inspector);
					Map<String, Object> taskInfo = cabletaskDao
							.getTaskInfo(TASK_ID);
					String equipCode = taskInfo.get("TASK_NO").toString();
					// 发送短信内容
					String messageText = "";
					if (null != staffInfo &&
							staffInfo.get("STAFF_NAME") != null
							&& null != staffInfo.get("TEL")
							&& !"".equals(staffInfo.get("TEL"))) {
						messageText = staffInfo.get("STAFF_NAME") + "，您好。您于"
								+ DateUtil.getCurrentTime() + "收到" + equipCode
								+ "任务，请及时执行。【分公司缆线巡检】";
						phoneNumList.add(staffInfo.get("TEL").toString());
						messageContentList.add(messageText);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		// 发送短信
		//SendMessageUtil.sendMessageList(phoneNumList, messageContentList);
		return true;
	}

	@Override
	public String getPlanCable(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String planIdString = request.getParameter("planId");
		String parentId = request.getParameter("parentId");
		Integer plan_id = Integer.valueOf(planIdString);
		List<Map> list = null;
		if (!"".equals(parentId) && parentId != null) {
			rs.put("lineId", parentId);
			list = cablePlanDao.getLineByLineId(rs);
		} else {
			list = cablePlanDao.getPlanDetail(plan_id);// 计划信息
		}
		List<CableModel> list1 = new ArrayList<CableModel>();
		for (Map listMap : list) {
			CableModel cm = new CableModel();
			cm.setLineId(Integer.parseInt(listMap.get("LINE_ID").toString()));
			if ("".equals(parentId) || parentId == null) {
				if ((listMap.get("PARENT_LINE_ID")) != null) {
					cm.setParentId(Integer.parseInt(listMap.get(
							"PARENT_LINE_ID").toString()));
				}
			}
			cm.setLineNo(listMap.get("LINE_NO").toString());
			cm.setLineName(listMap.get("LINE_NAME").toString());
			cm.setCreateStaff(Long.parseLong(listMap.get("CREATE_STAFF")
					.toString()));
			cm.setCreateTime(listMap.get("CREATE_TIME").toString());
			cm.setLineType(Integer
					.parseInt(listMap.get("LINE_TYPE").toString()));

			List<Map> pointList = cableDao.queryPoint(listMap);
			List<PointModel> pointMode = new ArrayList<PointModel>();
			for (Map pointMap : pointList) {
				PointModel pm = new PointModel();
				pm.setLatitude(pointMap.get("LONGITUDE").toString());
				pm.setLongitude(pointMap.get("LATITUDE").toString());
				pm.setPoint_name(pointMap.get("POINT_NAME")==null?"":pointMap.get("POINT_NAME").toString());
				pm.setPoint_type(pointMap.get("POINT_TYPE").toString());
				pointMode.add(pm);
			}
			cm.setPointMode(pointMode);
			list1.add(cm);
		}
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(list1).toString();
		return jsonString;
	}

	@Override
	public String getPlanExitCable(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		Integer plan_id = Integer.valueOf(request.getParameter("PLAN_ID"));
		Map map = new HashMap();

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

		List<Map> list = cableDao.getCable(map);
		List<CableModel> list1 = new ArrayList<CableModel>();
		for (Map listMap : list) {
			CableModel cm = new CableModel();
			cm.setLineId(Integer.parseInt(listMap.get("LINE_ID").toString()));
			cm.setLineNo(listMap.get("LINE_NO").toString());
			cm.setLineName(listMap.get("LINE_NAME").toString());
			cm.setLineLevel(Integer.parseInt(listMap.get("LINE_LEVEL")
					.toString()));
			cm.setCreateStaff(Long.parseLong(listMap.get("CREATE_STAFF")
					.toString()));
			cm.setCreateTime(listMap.get("CREATE_TIME").toString());
			// cm.setModifyStaff(modifyStaff)
			// cm.setModifyTime(modifyTime);
			List<Map> pointList = cableDao.queryPoint(listMap);
			List<PointModel> pointMode = new ArrayList<PointModel>();
			for (Map pointMap : pointList) {
				PointModel pm = new PointModel();
				pm.setLatitude(pointMap.get("LONGITUDE").toString());
				pm.setLongitude(pointMap.get("LATITUDE").toString());
				pointMode.add(pm);
			}
			cm.setPointMode(pointMode);
			list1.add(cm);
		}
		JSONArray json = new JSONArray();
		String jsonString = json.fromObject(list1).toString();
		return jsonString;
	}

	@Override
	public List<Map> getLineByLineId(Map params) {
		return cablePlanDao.getLineByLineId(params);
	}
	/**
	 * 显示增加关键点计划页面
	 */
	@Override
	public Map<String, Object> showAddPointPlan() {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("pointTypeList", pointManageDao.getPointTypes());
		return rs;
	}
	/**
	 * 查询关键点
	 * @param request
	 * @param pager
	 * @return
	 */
	@Override
	public Map<String, Object> queryPoint(HttpServletRequest request, UIPage pager) {
		String point_no = request.getParameter("point_no");
		String point_name = request.getParameter("point_name");
		String address = request.getParameter("address");
		String point_type = request.getParameter("point_type");
		String dept_no = request.getParameter("dept_name");
		String is_distribute = request.getParameter("is_distribute");//是否已经派发任务
		String eqp_type = request.getParameter("eqp_type");//设备类型
		
		String[] levels=request.getParameterValues("point_level[]");
		String point_level="";
		if(null!=levels&&levels.length>0){
			for (int i = 0; i < levels.length; i++) {
				if(null != levels[i] && !"".equals(levels[i]))
					point_level+=","+levels[i];
			}
			point_level=point_level.substring(1,point_level.length());
		};
		// 管理员查询条件
		String son_area = request.getParameter("son_area_id");
		Map map = new HashMap();
		map.put("POINT_NO", point_no);
		map.put("POINT_NAME", point_name);
		map.put("ADDRESS", address);
		map.put("POINT_TYPE", point_type);
		map.put("POINT_LEVEL", point_level);
		map.put("DEPT_NO", dept_no);
		map.put("IS_DISTRIBUTE", is_distribute);
		map.put("EQP_TYPE", eqp_type);
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			// 判断查询条件是否有查询子区域
			if (!StringUtil.isEmpty(son_area)) {
				map.put("SON_AREA_ID", son_area);
			}
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				// 判断查询条件是否有查询子区域
				if (!StringUtil.isEmpty(son_area)) {
					map.put("SON_AREA_ID", son_area);
				}
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

		List<Map> olists = cablePlanDao.queryPoint(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	/**
	 * 保存关键点计划
	 */
	@Override
	public Map<String, Object> savePointPlan(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			String areaId = request.getParameter("areaId");
			String plan_name = request.getParameter("plan_name");
			String plan_type = request.getParameter("plan_type");
			String plan_circle = request.getParameter("plan_circle");
			String plan_frequency = request.getParameter("plan_frequency");
			String plan_start_time = request.getParameter("plan_start_time");
			String plan_end_time = request.getParameter("plan_end_time");
			String son_area_id = request.getParameter("son_area_id");
			String dept_no = request.getParameter("dept_name");//实际为网格编码
			String points = request.getParameter("points");
			String staffId = (String) request.getSession().getAttribute("staffId");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("areaId", areaId);
			param.put("plan_name", plan_name);
			param.put("plan_type", plan_type);
			param.put("plan_circle", plan_circle);
			param.put("plan_frequency", plan_frequency);
			param.put("plan_start_time", plan_start_time);
			param.put("plan_end_time", plan_end_time);
			param.put("son_area_id", son_area_id);
			param.put("points", points);
			param.put("staffId", staffId);
			param.put("dept_no", dept_no);
			//查询计划名称是否存在
			List<Map<String,Object>> planByNameList = cablePlanDao.getPointPlanByName(param);
			if(planByNameList.size()>0){
				rs.put("resultCode", "001");
				rs.put("resultDesc", "计划名称已存在!");
				return rs;
			}
			//添加线
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
			}
			//添加计划
			cablePlanDao.insertPointPlan(param);
			//添加计划详情
			cablePlanDao.insertPointPlanDetail(param);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put("resultCode", "001");
			rs.put("resultDesc", "关键点计划增加失败!");
			return rs;
		}
		rs.put("resultCode", "000");
		rs.put("resultDesc", "关键点计划增加成功!");
		return rs;
	}
	/**
	 * 显示修改关键点计划页面
	 */
	@Override
	public Map<String, Object> showEditPointPlan(HttpServletRequest request) {
		String plan_id = request.getParameter("plan_id");
		Map<String, Object> rs = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("plan_id", plan_id);
		rs =   cablePlanDao.getPointPlanById(param);
		rs.put("pointTypeList", pointManageDao.getPointTypes());
		return rs;
	}
	/**
	 * 查询已选择的关键点
	 * @param request
	 * @param response
	 * @param pager
	 * @throws IOException
	 */
	@Override
	public Map<String, Object> searchPointData(HttpServletRequest request,
			UIPage pager) {
		String PLAN_ID = request.getParameter("PLAN_ID");
		// 管理员查询条件
		Map map = new HashMap();
		map.put("PLAN_ID", PLAN_ID);

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = cablePlanDao.searchPointData(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}
	
	/**
	 * 保存关键点计划
	 */
	@Override
	public Map<String, Object> saveEditPointPlan(HttpServletRequest request) {
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
			String son_area_id = request.getParameter("son_area_id");
			String dept_no = request.getParameter("dept_name");//实际为光缆段
			String points = request.getParameter("points");
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
			param.put("son_area_id", son_area_id);
			param.put("points", points);
			param.put("staffId", staffId);
			param.put("dept_no", dept_no);
			//查询计划名称是否存在
			List<Map<String,Object>> planByNameList = cablePlanDao.getEditPointPlanByName(param);
			if(planByNameList.size()>0){
				rs.put("resultCode", "001");
				rs.put("resultDesc", "计划名称已存在!");
				return rs;
			}
			//编辑线
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
			}
			//编辑计划
			cablePlanDao.updatePointPlan(param);
		} catch (Exception e) {
			e.printStackTrace();
			rs.put("resultCode", "001");
			rs.put("resultDesc", "关键点计划编辑失败!");
			return rs;
		}
		rs.put("resultCode", "000");
		rs.put("resultDesc", "关键点计划编辑成功!");
		return rs;
	}

	@Override
	public Map addPlan(HttpServletRequest request) {
		Map params = new HashMap();
		params.put("area_id", request.getSession().getAttribute("areaId").toString());
		String areaName = cableService.getAreaName(request);
		params.put("AREA_NAME", areaName);
		List<Map> dept = cableService.queryDept(request);
		params.put("dept", dept);
		List<Map> area=arrivalDao.getSonAreaById(params);
		params.put("son_area", area);
		return params;
	}

	@Override
	public Map<String, Object> queryJYHStaff(HttpServletRequest request,
			UIPage pager) {
		Map map = new HashMap();
		String staffNo = request.getParameter("staff_no");
		String staffName = request.getParameter("staff_name");
		String plan_ids=request.getParameter("plan_id");
		String[] plans = plan_ids.split(",");
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
		List<Map> list1 = new ArrayList();
		Map<String, Object> planInfo=cablePlanDao.getSonAreaIdByPlan(plans[0]);
		map.put("area_id", cablePlanDao.getSonAreaIdByPlan(plans[0]).get("SON_AREA_ID"));
		if(("1").equals(planInfo.get("TYPE").toString())&&!("1").equals(planInfo.get("SEND_TYPE"))){
			try{
			SwitchDataSourceUtil.setCurrentDataSource("orcl153");
			list1= cablePlanDao.getStaffFromJYH(map);
			SwitchDataSourceUtil.clearDataSource();
		} catch (Exception e) {
			SwitchDataSourceUtil.clearDataSource();
		}
			for (Map map2 : list1) {
				map2.put("STAFF_ID", cablePlanDao.getStaffIdByNo(map2));
			}
			List<Map> list2 = cablePlanDao.getStaffFromDEPT(map);
			list1.addAll(list2);
		}else{
			list1= cablePlanDao.getSpectors(query);
		}
		
//		SwitchDataSourceUtil.setCurrentDataSource("orcl153");
//		List<Map> list1= cablePlanDao.getStaffFromJYH(map);
//		SwitchDataSourceUtil.clearDataSource();
//		for (Map map2 : list1) {
//			map2.put("STAFF_ID", cablePlanDao.getStaffIdByNo(map2));
//		}
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list1);
		return pmap;
	}

	@Override
	public Boolean editInspector(HttpServletRequest request) {
		String inspector = request.getParameter("selected_staffId");
		String inspector_type = request.getParameter("inspector_type");
		String[] planSelected = request.getParameter("selected_planId")
				.replace(" ", "").split(",");
		Map map = new HashMap();
		map.put("staff_id", inspector);
		map.put("inspector_type", inspector_type);
		for (String string : planSelected) {
			map.put("plan_id", string);
			cablePlanDao.editPlanInspector(map);
			cablePlanDao.editTaskInspector(map);
		}
		return true;
	}
}
