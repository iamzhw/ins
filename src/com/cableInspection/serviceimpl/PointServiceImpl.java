package com.cableInspection.serviceimpl;

import icom.util.Result;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import util.page.Query;
import util.page.UIPage;

import com.cableInspection.dao.CableDao;
import com.cableInspection.dao.CableTaskDao;
import com.cableInspection.dao.PointDao;
import com.cableInspection.service.PointService;
import com.cableInspection.webservice.Wfworkitemflow;
import com.cableInspection.webservice.WfworkitemflowLocator;
import com.cableInspection.webservice.WfworkitemflowSoap11BindingStub;
import com.system.constant.RoleNo;
import com.system.dao.GeneralDao;
import com.system.dao.RoleDao;
import com.system.dao.StaffDao;
import com.util.DateUtil;
import com.util.KeepRule;
import com.util.Rule;
import com.util.StringUtil;
import com.util.sendMessage.PropertiesUtil;
import com.util.sendMessage.SendMessageUtil;

@SuppressWarnings("all")
@Transactional(rollbackFor = { Exception.class })
@Service
public class PointServiceImpl implements PointService {

	@Resource
	private PointDao pointDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private StaffDao staffDao;

	@Resource
	private CableTaskDao cabletaskDao;

	@Resource
	private GeneralDao generalDao;
	
	@Resource
	private CableDao cableDao;

	@Override
	public List<Map> getPoints(HttpServletRequest request) {
		Map<String, Object> params = new HashMap();
		params.put("POINT_TYPE", request.getParameter("type"));
		params.put("POINT_NO", request.getParameter("pointNo"));// 点编码
		params.put("POINT_NAME", request.getParameter("pointName"));// 点名称
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				params.put("AREA_ID", session.getAttribute("areaId"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					params.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					params.put("AREA_ID", -1);
				}
			}
		}
		return pointDao.getPoints(params);
	}

	@Override
	public Map<String, Object> planQuery(HttpServletRequest request,
			UIPage pager) {
		String plan_no = request.getParameter("plan_no");
		String plan_name = request.getParameter("plan_name");
		String plan_start_time = request.getParameter("plan_start_time");
		String plan_end_time = request.getParameter("plan_end_time");
		String point_type = request.getParameter("point_type");
		String trouble = request.getParameter("trouble");
		String if_complete = request.getParameter("if_complete");

		Map map = new HashMap();
		map.put("PLAN_NO", plan_no);
		map.put("PLAN_NAME", plan_name);
		map.put("PLAN_START_TIME", plan_start_time);
		map.put("PLAN_END_TIME", plan_end_time);
		map.put("point_type", point_type);
		map.put("trouble", trouble);
		map.put("if_complete", if_complete);

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				map.put("SON_AREA_ID", request.getParameter("son_area_id"));
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else if ((Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 是否是审核员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
				} else {
					map.put("AREA_ID", -1);
				}
			}
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = pointDao.planQuery(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	@Transactional
	public Boolean save(HttpServletRequest request) {
		try {
			String points = request.getParameter("points");
			String keepLine=request.getParameter("keepLine");
			String plan_name = request.getParameter("plan_name");
			String plan_no = request.getParameter("plan_no");
			String plan_type = request.getParameter("plan_type");
			String plan_start_time = request.getParameter("plan_start_time");
			String plan_end_time = request.getParameter("plan_end_time");
			String plan_circle = request.getParameter("plan_circle");
			String plan_frequency = request.getParameter("plan_frequency");
			String plan_kind = request.getParameter("plan_kind");
			String custom_time = request.getParameter("custom_time");
			String inaccuracy = request.getParameter("inaccuracy");

			String staffId = (String) request.getSession().getAttribute(
					"staffId");

			Map map = new HashMap();
			map.put("PLAN_NAME", plan_name);
			map.put("PLAN_NO", plan_name);
			map.put("PLAN_TYPE", plan_type);
			map.put("PLAN_START_TIME", plan_start_time);
			map.put("PLAN_END_TIME", plan_end_time);
			map.put("PLAN_CIRCLE", plan_circle);
			map.put("PLAN_FREQUENCY", plan_frequency);
			map.put("CREATE_STAFF", staffId);
			map.put("PLAN_KIND", plan_kind);
			map.put("AREA_ID", request.getSession().getAttribute("areaId"));
			map.put("SON_AREA_ID",
					request.getSession().getAttribute("sonAreaId"));
			map.put("CUSTOM_TIME", custom_time);
			map.put("INACCURACY", inaccuracy);
			pointDao.insertPlan(map);
			// 保存的计划id
			Integer plan_id = (Integer) map.get("plan_id");
			Map planDetail = null;
			String[] p = points.split(",");
			String[] temp = null;
			for (int i = 0, j = p.length; i < j; i++) {
				planDetail = new HashMap();
				temp = p[i].split(":");
				planDetail.put("PLAN_ID", plan_id);
				planDetail.put("INSPECT_OBJECT_ID", temp[0]);
				planDetail.put("INSPECT_OBJECT_TYPE", temp[1]);
				pointDao.insertDetail(planDetail);
			}
			// 看护计划单独保存时间段
			if ("3".equals(plan_kind)) {
				/**
				 * 新增看护范围保存
				 */
				String[] LinePoints = keepLine.split(",");
				String[] cache = null;
				Map pointMap = new HashMap();
				pointMap.put("CREATE_STAFF", staffId);
				pointMap.put("staff_id", staffId);
				pointMap.put("plan_id", plan_id);
				for (int i = 0, j = LinePoints.length; i < j; i++) {
					// planDetail = new HashMap();
					// 判断坐标点是否具有ID
					cache = LinePoints[i].split(":");
					pointMap.put("LONGITUDE", cache[0]);
					pointMap.put("LATITUDE", cache[1]);	
					cableDao.insertPoint(pointMap);
					pointMap.put("point_seq", i+1);
					pointDao.addKeepLinePoints(pointMap);
				}
				String[] start_time = request.getParameter("start_time").split(
						",");
				String[] end_time = request.getParameter("end_time").split(",");
				Map<String, Object> keepTimeParams = new HashMap<String, Object>();
				keepTimeParams.put("PLAN_ID", plan_id);
				for (int i = 0, j = start_time.length; i < j; i++) {
					if ("".equals(start_time[i])) {
						continue;
					}
					keepTimeParams.put("START_TIME", start_time[i]);
					keepTimeParams.put("END_TIME", end_time[i]);
					pointDao.saveKeepTime(keepTimeParams);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
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
			plan_ids += "," + plans[i];
		}
		map.put("PLAN_ID", plan_ids.substring(1));
		pointDao.deletePlan(map);
	}

	@Override
	public Map<String, Object> editPlan(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户的区域信息
		Map params = new HashMap();
		params.put("STAFF_ID", staffId);
		Map areaInfo = pointDao.getAreaInfo(params);
		rs.put("area_id", areaInfo.get("AREA_ID"));
		Integer plan_id = Integer.valueOf(request.getParameter("plan_id"));
		Map map = new HashMap();
		map.put("PLAN_ID", plan_id);
		Map planInfo = pointDao.getPlan(map);// 计划信息
		rs.put("planInfo", planInfo);
		rs.put("AREA_NAME", areaInfo.get("NAME"));
		return rs;
	}

	@Override
	public Map<String, Object> getPlanDetail(HttpServletRequest request) {
		Integer plan_id = Integer.valueOf(request.getParameter("plan_id"));
		Map<String, Object> params =  new HashMap();
		params.put("plan_id", plan_id);
		List<Map> list = pointDao.queryKeepLinePoints(params);
		List<Map> datas= pointDao.getPlanDetail(plan_id);
		params.put("list", list);
		params.put("datas", datas);
		return params;
	}

	@Override
	public Boolean updatePlan(HttpServletRequest request) {
		String points = request.getParameter("points");
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
		map.put("INACCURACY", "");
		pointDao.updatePlan(map);
		// 清除老的计划详细信息
		pointDao.deletePlanDetail(map);
		// 保存新的计划详细信息
		Map planDetail = null;
		String[] p = points.split(",");
		String[] temp = null;
		for (int i = 0, j = p.length; i < j; i++) {
			planDetail = new HashMap();
			temp = p[i].split(":");
			planDetail.put("PLAN_ID", plan_id);
			planDetail.put("INSPECT_OBJECT_ID", temp[0]);
			planDetail.put("INSPECT_OBJECT_TYPE", temp[1]);
			pointDao.insertDetail(planDetail);
		}
		return true;
		// //获取任务执行人
		// List<Map> inspectorInfo = pointDao.getInspector(map);
		// if(inspectorInfo != null && inspectorInfo.size() > 0){
		// String inspector = inspectorInfo.get(0).get("INSPECTOR").toString();
		// //删除老的任务（如果存在），已完成和进行中的任务不删除
		// pointDao.deleteOldTask(map);
		// //重新生成任务
		// return createTask(plan_id, inspector, staffId);
		// } else {
		// return true;
		// }
	}

	@Override
	public List<Map<String, String>> getSpectors(HttpServletRequest request) {
		Map map = new HashMap();
		return pointDao.getSpectors(map);
	}

	@Override
	public Boolean saveTask(HttpServletRequest request) {
		String inspector = request.getParameter("inspector");
		String planSelected = request.getParameter("planSelected");
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前管理员
		return createTask(planSelected, inspector, staffId);
	}

	public boolean createTask(String ids, String inspector, String staffId) {
		try {
			String[] planSelected = ids.replace(" ", "").split(",");
			Integer PLAN_ID = null;
			String PLAN_NO = null;
			String PLAN_START_TIME = null;
			String PLAN_END_TIME = null;
			String PLAN_FREQUENCY = null;
			String PLAN_CIRCLE = null;
			String PLAN_KIND = null;
			String AREA_ID = null;
			String SON_AREA_ID = null;

			List<Map> ruleData = null;// 生成的规则
			Map taskMap = null;// 任务参数map
			Integer TASK_ID = null;// 任务id
			List<Map> planDetailList = null;// 计划对应外力点信息集合

			String INSPECT_OBJECT_ID = null;// 巡检对象id
			String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
			Map taskDetailMap = null;

			// 获取任务详细信息
			Map planInfo = null;
			Map map = new HashMap();
			for (int i = 0, j = planSelected.length; i < j; i++) {
				map.put("PLAN_ID", planSelected[i]);
				map.put("TASK_INSPECTOR", inspector);
				map.put("TASK_CREATOR", staffId);
				planInfo = pointDao.getPlan(map);
				// 将计划设置为已分配，保存巡检人员和创建者
				pointDao.updatePlanDistributed(map);

				PLAN_ID = Integer.valueOf(planInfo.get("PLAN_ID").toString());
				PLAN_NO = planInfo.get("PLAN_NO").toString();
				PLAN_START_TIME = planInfo.get("PLAN_START_TIME").toString();
				PLAN_END_TIME = planInfo.get("PLAN_END_TIME").toString();
				PLAN_FREQUENCY = StringUtil.objectToString(planInfo
						.get("PLAN_FREQUENCY"));
				PLAN_CIRCLE = StringUtil.objectToString(planInfo
						.get("PLAN_CIRCLE"));
				PLAN_KIND = planInfo.get("PLAN_KIND").toString();
				AREA_ID = planInfo.get("AREA_ID").toString();
				SON_AREA_ID = planInfo.get("SON_AREA_ID").toString();
				Map ruleMap = new HashMap();// 查询任务生成规则参数map

				if ("3".equals(PLAN_KIND)) {
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
					// 获取看护任务的时间段
					List<Map<String, String>> timeList = pointDao
							.getTimeList(PLAN_ID);
					ruleMap.put("timeList", timeList);
					ruleData = KeepRule.createTaskOrder(ruleMap);// 生成任务的开始和结束时间
				} else {
					if (Rule.DAY.equals(PLAN_CIRCLE)
							|| Rule.WEEK.equals(PLAN_CIRCLE)) {
						// 获取本周的最后一天
						Date sundayOfCurrWeek = DateUtil.getSundayOfCurrWeek();
						if (StringUtil.stringToDate(PLAN_START_TIME,
								"yyyy-MM-dd").after(sundayOfCurrWeek)) {// 计划开始时间在本周日后
							continue;
						} else {
							ruleMap.put("startDate", PLAN_START_TIME);
						}
						if (StringUtil
								.stringToDate(PLAN_END_TIME, "yyyy-MM-dd")
								.before(sundayOfCurrWeek)) {// 计划结束时间在本周日前
							ruleMap.put("endDate", PLAN_END_TIME);
						} else {
							ruleMap.put("endDate", StringUtil.dateToString(
									sundayOfCurrWeek, "yyyy-MM-dd"));
						}
					} else if (Rule.MONTH.equals(PLAN_CIRCLE)) {
						// 获取本月的最后一天
						Date lastDayOfCurrMonth = DateUtil
								.getLastDayOfCurrMonth();
						if (StringUtil.stringToDate(PLAN_START_TIME,
								"yyyy-MM-dd").after(lastDayOfCurrMonth)) {// 计划开始时间在本月最后一天后
							continue;
						} else {
							ruleMap.put("startDate", PLAN_START_TIME);
						}
						if (StringUtil
								.stringToDate(PLAN_END_TIME, "yyyy-MM-dd")
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
					ruleMap.put("custom_time", StringUtil
							.objectToString(planInfo.get("CUSTOM_TIME")));

					ruleData = Rule.createTaskOrder(ruleMap, PLAN_CIRCLE);// 生成任务的开始和结束时间
				}
				taskMap = new HashMap();
				taskMap.put("PLAN_ID", PLAN_ID);
				taskMap.put("PLAN_NO", PLAN_NO);
				taskMap.put("CREATE_STAFF", staffId);
				taskMap.put("INSPECTOR", inspector);
				taskMap.put("AREA_ID", AREA_ID);
				taskMap.put("SON_AREA_ID", SON_AREA_ID);
				List phoneNumList = new ArrayList();
				List messageContentList = new ArrayList();
				for (Map rule : ruleData) {
					taskMap.put("COMPLETE_TIME", rule.get("endDate"));
					taskMap.put("START_TIME", rule.get("startDate"));
					pointDao.saveTask(taskMap);// 保存任务信息
					TASK_ID = Integer
							.valueOf(taskMap.get("TASK_ID").toString());
					// 查询出计划对应的外力点信息
					planDetailList = pointDao.getPlanDetail(PLAN_ID);
					// 保存任务详细信息
					taskDetailMap = new HashMap();
					taskDetailMap.put("TASK_ID", TASK_ID);
					taskDetailMap.put("INSPECTOR", inspector);
					for (Map planDetail : planDetailList) {
						INSPECT_OBJECT_ID = planDetail.get("POINT_ID")
								.toString();
						INSPECT_OBJECT_TYPE = planDetail.get("POINT_TYPE")
								.toString();
						taskDetailMap.put("INSPECT_OBJECT_ID",
								INSPECT_OBJECT_ID);
						taskDetailMap.put("INSPECT_OBJECT_TYPE",
								INSPECT_OBJECT_TYPE);
						pointDao.saveTaskDetail(taskDetailMap);
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
						// 发短信开关开启

						if (staffInfo.get("STAFF_NAME") != null
								&& null != staffInfo.get("TEL")
								&& !"".equals(staffInfo.get("TEL"))) {
							messageText = staffInfo.get("STAFF_NAME")
									+ "，您好。您于" + DateUtil.getCurrentTime()
									+ "收到" + equipCode + "任务，请及时执行。【分公司缆线巡检】";
							phoneNumList.add(staffInfo.get("TEL").toString());
							messageContentList.add(messageText);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 发送短信
				//SendMessageUtil.sendMessageList(phoneNumList,
				//		messageContentList);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
	}

	@Override
	public Map<String, Object> taskQuery(HttpServletRequest request,
			UIPage pager) {
		String plan_name = request.getParameter("plan_name");
		String inspector = request.getParameter("inspector");
		String start_time = request.getParameter("start_time");
		String complete_time = request.getParameter("complete_time");
		String plan_id = request.getParameter("plan_id");
		if (start_time != null && !"".equals(start_time)) {
			start_time += " 00:00:00";
		}
		if (complete_time != null && !"".equals(complete_time)) {
			complete_time += " 23:59:59";
		}
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户

		Map map = new HashMap();
		map.put("STAFF_ID", staffId);
		map.put("START_TIME", start_time);
		map.put("COMPLETE_TIME", complete_time);
		if (plan_id != null && !"".equals(plan_id)) {
			map.put("PLAN_ID", plan_id);
		} else {
			map.put("PLAN_NAME", plan_name);
		}

		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			map.put("SON_AREA_ID", request.getParameter("son_area_id"));
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				map.put("SON_AREA_ID", request.getParameter("son_area_id"));
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

		List<Map> olists = pointDao.taskQuery(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map<String, Object> getUserArea(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String plan_kind = request.getParameter("plan_kind");// 计划类别
		rs.put("plan_kind", plan_kind);
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户的区域信息
		Map params = new HashMap();
		params.put("STAFF_ID", staffId);
		Map areaInfo = pointDao.getAreaInfo(params);
		rs.put("area_id", areaInfo.get("AREA_ID"));
		rs.put("area_name", areaInfo.get("NAME"));
		return rs;
	}

	@Override
	public Boolean checkPlan(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		Map params = new HashMap();
		params.put("PLAN_ID", ids);
		int rows = pointDao.checkPlan(params);
		if (rows > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Map<String, Object> billQuery(HttpServletRequest request,
			UIPage pager) {
		String point_no = request.getParameter("point_no");
		String bill_status = request.getParameter("bill_status");
		String create_time = request.getParameter("create_time");
		String end_time = request.getParameter("end_time");
		String complete_time = request.getParameter("complete_time");
		String grid_name = request.getParameter("grid_name");
		HttpSession session = request.getSession();
		String staffId = session.getAttribute("staffId").toString();// 当前用户

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("POINT_NO", point_no);
		map.put("BILL_STATUS", bill_status);
		map.put("CREATE_TIME", create_time);
		map.put("STAFF_ID", staffId);
		map.put("COMPLETE_TIME", end_time);
		map.put("end_time", end_time);
		map.put("GRID_NAME", grid_name);
		if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
			map.put("SON_AREA_ID",request.getParameter("son_area_id")==""?null:request.getParameter("son_area_id"));
			map.put("ADMIN", true);
		} else {
			if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
				map.put("AREA_ID", session.getAttribute("areaId"));
				//map.put("SON_AREA_ID", request.getParameter("son_area_id"));
				map.put("SON_AREA_ID",request.getParameter("son_area_id")==""?null:request.getParameter("son_area_id"));
				map.put("ADMIN", true);
			} else {
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
					map.put("ADMIN", true);
				} else if (session.getAttribute(RoleNo.LXXJ_AUDITOR)==null?false:(Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 如果是审核员只能查到本区域的
					map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
					map.put("ADMIN", true);
				}
			}
		}

		Query query = new Query();
		query.setPager(pager);
		query.setQueryParams(map);

		List<Map> olists = pointDao.billQuery(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		pmap.put("rows", olists);
		return pmap;
	}

	@Override
	public Map<String, Object> indexBill(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		// 获取所有工单状态
		List<Map<String, Object>> statusList = pointDao.getAllBillStatus();
		rs.put("statusList", statusList);
		String roleNo = null;
		for (Map<String, String> map : roleList) {
			roleNo = map.get("ROLE_NO");
			if (RoleNo.GLY.equals(roleNo) || RoleNo.LXXJ_ADMIN.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("admin", true);
				continue;
			}
			if (RoleNo.LXXJ_MAINTOR.equals(roleNo)) {
				rs.put("maintor", true);
				continue;
			}
			if (RoleNo.LXXJ_AUDITOR.equals(roleNo)) {
				rs.put("auditor", true);
				continue;
			}
		}
		return rs;
	}

	@Override
	public String handleBill(HttpServletRequest request) {
		String operate = request.getParameter("operate");
		String ids = request.getParameter("ids");
		String receiver = request.getParameter("receiver");
		
		String remarks = request.getParameter("remark")==null?"":request.getParameter("remark");
		String complete_time = request.getParameter("complete_time");
		receiver = receiver == null ? "" : receiver;
		Map<String, Object> params = new HashMap<String, Object>();
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		boolean isAdmin = false;
		params.put("BILL_IDS", ids);
		// params.put("OPERATE", operate);
		params.put("STAFF_ID", staffId);
		// 判断是否为系统管理员或者缆线管理员
		if (pointDao.isAdmin(params) > 0) {
			isAdmin = true;
		}
		// 流程信息
		Map<String, Object> flowParams = new HashMap<String, Object>();
		flowParams.put("OPERATOR", staffId);
		flowParams.put("RECEIVOR", receiver);
		if ("distributeBill".equals(operate)) {
			String name="";
			try {
				name = java.net.URLDecoder.decode(request.getParameter("staff_name"),"UTF-8");
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (!isStatusRight(ids, "1, 5")) {
				return Result.returnError("请选择未派发或已退单的工单!").toString();
			} else {
				if (!isAllowed(ids, "AUDITOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				if("".equals(complete_time))
				{
					return Result.returnError("请选择整治结束时间").toString();
				}

				//集约化隐患工单派发
				Map<String,String> workMap = new HashMap<String,String>();
				String[] billId=ids.split(",");
				Map staffinfo = new HashMap();
				if(null!=receiver&&!("").equals(receiver)){
					staffinfo=pointDao.workStaffInfo(receiver);
				}
				WfworkitemflowSoap11BindingStub  wfworkitemflowSoap11BindingStub;
				Wfworkitemflow wfworkitemflowLocator = new WfworkitemflowLocator();
				String eqpId="";
				try {
				wfworkitemflowSoap11BindingStub=(WfworkitemflowSoap11BindingStub) wfworkitemflowLocator.getWfworkitemflowHttpSoap11Endpoint();
				wfworkitemflowSoap11BindingStub.setTimeout(30 * 1000);
				String result="";
				String param="";
				Document doc;
				Element root = null;
				Map post = new HashMap();
				Map<String,String> pointCood = new HashMap<String,String>();
				for (int i = 0; i < billId.length; i++) {
					eqpId=pointDao.decodeEqyTypeByBill(billId[i]);
					if(!eqpId.equals("0")){
						pointCood = pointDao.getCoodByBillId(billId[i]);
						remarks=pointDao.getRemarksById(billId[i])==null?"":pointDao.getRemarksById(billId[i]);
						params.put("url", "61.160.128.47");
						params.put("BILL_ID", billId[i]);
						// 获取工单关联隐患上报记录id
						params.put("RECORD_TYPE", 1);
						List<Map<String, Object>> recordList = pointDao
						.getRecordByBillId(params);
						params.put("RECORD_TYPE", 4);
						recordList.addAll(pointDao.getRecordByBillId(params));
						params.put("RECORD_ID", StringUtil.objectToString(recordList
								.get(0).get("RECORD_ID")));
						List<Map<String, String>> photoList = pointDao.billPhoto(params);
						String photo_in="";
						String photo_out="";
						if(photoList!=null&&photoList.size()!=0){
							for (Map<String, String> map : photoList) {
								photo_out+=","+map.get("PHOTO_PATH");
							}
							photo_in=photo_out.replace("61.160.128.47", "132.228.237.107");
							photo_in=photo_in.substring(1,photo_in.length());
							photo_out=photo_out.substring(1,photo_out.length());
						}
						param="<?xml version=\"1.0\" encoding=\"gb2312\"?>"
							   +"<IfInfo>"
							   +"<sysRoute>XJXT</sysRoute>"
							   +"<taskType>4</taskType>"
							   +"<xjMan>"+name+"</xjMan>"
							   +"<taskId>"+billId[i]+"</taskId>"
							   +"<TaskType>"+eqpId+"</TaskType>"
							   +"<xjManAccount>"+receiver+"</xjManAccount>"
							   +"<XjContent>"+remarks+"</XjContent>"
							   +"<picture_outSys>"+photo_out+"</picture_outSys>"
							   +"<picture_inSys>"+photo_in+"</picture_inSys>"
							   +"<longitude>"+pointCood.get("LONGITUDE")+"</longitude>"
							   +"<latitude>"+pointCood.get("LATITUDE")+"</latitude>"
							   +"</IfInfo>";
						result=wfworkitemflowSoap11BindingStub.outSysDispatchTask(param);
						post.put("get", result);
						post.put("post", param);
						post.put("type", 1);
						if(result.length()>1){
							result=result.replace("<?xml version=\"1.0\" encoding=\"gb2312\"?>", "");
							doc= DocumentHelper.parseText(result);
							root = doc.getRootElement();
							post.put("flag", root.element("IfResult").getText());
						}else{
							post.put("flag", 1);
						}
						pointDao.add_a_post(post);
						//修改工单状态
							params.put("STATUS_ID", 9);
							params.put("MAINTOR", receiver);
							params.put("AUDITOR", staffId);
							params.put("COMPLETE_TIME", complete_time);
							pointDao.updateBillHandle(params);
					}
//					else{//正常工单派发
//						params.put("STATUS_ID", 2);
//						params.put("MAINTOR", receiver);
//						params.put("AUDITOR", staffId);
//						params.put("COMPLETE_TIME", complete_time);
//						pointDao.updateBillHandle(params);
//					}
				}
				} catch (RemoteException e) {
					e.printStackTrace();
				}catch (ServiceException e1) {
					e1.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}

				List phoneNumList = new ArrayList();
				List messageContentList = new ArrayList();
				for (String id : ids.split(",")) {
					if(eqpId.equals("0")){
						flowParams.put("BILL_ID", id);
						flowParams.put("STATUS", 2);
						flowParams.put("OPERATE_INFO", "派发工单");
					}else{
						flowParams.put("BILL_ID", id);
						flowParams.put("STATUS", 9);
						flowParams.put("OPERATE_INFO", "集约工单");
					}
					pointDao.insertBillFlow(flowParams);

					// 隐患工单派发发送短信
					try {
						// 获取人员的信息
						Map<String, Object> staffInfo = staffDao
								.getStaff(receiver);
						Map<String, String> taskInfo = pointDao.billInfo(id);
						String equipCode = taskInfo.get("POINT_NO").toString();
						// 发送短信内容
						String messageText = "";
						if (staffInfo.get("STAFF_NAME") != null
								&& null != staffInfo.get("TEL")
								&& !"".equals(staffInfo.get("TEL"))) {
							messageText = staffInfo.get("STAFF_NAME")
									+ "，您好。您于" + DateUtil.getCurrentTime()
									+ "收到" + equipCode + "任务，任务截止时间:"+complete_time+"，请及时执行。【分公司缆线巡检】";
							phoneNumList.add(staffInfo.get("TEL"));
							messageContentList.add(messageText);
						}
					}

					catch (Exception e) {
						//e.printStackTrace();
					}
				}
				// 发送短信
				//SendMessageUtil.sendMessageList(phoneNumList,
				//		messageContentList);
			}
		} else if ("audit".equals(operate)) {
			if (!isStatusRight(ids, "3")) {
				return Result.returnError("请选择待审核的工单!").toString();
			} else {
				if (!isAllowed(ids, "AUDITOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				params.put("STATUS_ID", 4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				params.put("COMPLETE_TIME", sdf.format(new Date()));
				pointDao.updateBillHandle(params);
				
				Map<String, Object> type = pointDao.getPointTypeByids(ids);
				String eqpType = String.valueOf(type.get("eqp_type_id"));    
				if(eqpType!=null&&!"".equals(eqpType))
				{
					pointDao.updatePointType(type);
				}
				for (String id : ids.split(",")) {
					flowParams.put("BILL_ID", id);
					flowParams.put("STATUS", 4);
					flowParams.put("OPERATE_INFO", "审核通过，归档");
					pointDao.insertBillFlow(flowParams);
				}
			}
		} else if ("audit_error".equals(operate)) {
			if (!isStatusRight(ids, "3")) {
				return Result.returnError("请选择待审核的工单!").toString();
			} else {
				if (!isAllowed(ids, "AUDITOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				params.put("STATUS_ID", 2);
				pointDao.updateBillHandle(params);
				for (String id : ids.split(",")) {
					flowParams.put("BILL_ID", id);
					flowParams.put("STATUS", 2);
					flowParams.put("OPERATE_INFO", "审核不通过，重新维护");
					flowParams.put("RECEIVOR", pointDao.getMaintorByBillId(id));
					pointDao.insertBillFlow(flowParams);
				}
			}
		} else if ("return".equals(operate)) {
			if (!isStatusRight(ids, "2")) {
				return Result.returnError("请选择待回单的工单!").toString();
			} else {
				if (!isAllowed(ids, "MAINTOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				if("".equals(remarks.toString()))
				{
					return Result.returnError("请填写退单说明！").toString();
				}
				params.put("STATUS_ID", 5);
				pointDao.updateBillHandle(params);
				for (String id : ids.split(",")) {
					flowParams.put("BILL_ID", id);
					flowParams.put("STATUS", 5);
					flowParams.put("OPERATE_INFO", "退单操作，原因："+remarks);
					//(new String(remarks.getBytes("iso-8859-1"),"gb2312"))
					flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(id));
					pointDao.insertBillFlow(flowParams);
				}
			}
		} else if ("receipt".equals(operate)) {
			if (!isStatusRight(ids, "2")) {
				return Result.returnError("请选择待回单的工单!").toString();
			} else {
				if (!isAllowed(ids, "MAINTOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				params.put("STATUS_ID", 3);
				pointDao.updateBillHandle(params);
				for (String id : ids.split(",")) {
					flowParams.put("BILL_ID", id);
					flowParams.put("STATUS", 3);
					flowParams.put("OPERATE_INFO", "回单操作");
					flowParams.put("RECEIVOR", pointDao.getAuditorByBillId(id));
					pointDao.insertBillFlow(flowParams);
				}
			}
		} else if ("forward".equals(operate)) {
			if (!isStatusRight(ids, "1, 5")) {
				return Result.returnError("请选择未派发或已退单的工单!").toString();
			} else {
				if (!isAdmin && !isAllowed(ids, "AUDITOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				params.put("STATUS_ID", 1);
				params.put("AUDITOR", receiver);
				pointDao.updateBillHandle(params);
				for (String id : ids.split(",")) {
					flowParams.put("BILL_ID", id);
					flowParams.put("STATUS", 1);
					flowParams.put("OPERATE_INFO", "转发工单");
					pointDao.insertBillFlow(flowParams);
				}
			}
		} else if ("archive".equals(operate)) {
			if (!isStatusRight(ids, "1, 5")) {
				return Result.returnError("请选择未派发或已退单的工单!").toString();
			} else {
				if (!isAllowed(ids, "AUDITOR", staffId, operate)) {// 判断审核员是否是当前用户
					return Result.returnError("操作失败，请处理自己的工单!").toString();
				}
				//判断是否有整改后的照片
				params.put("STATUS_ID", 4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				params.put("COMPLETE_TIME", sdf.format(new Date()));
				pointDao.updateBillHandle(params);
				for (String id : ids.split(",")) {
					flowParams.put("BILL_ID", id);
					flowParams.put("STATUS", 4);
					flowParams.put("OPERATE_INFO", "归档");
					pointDao.insertBillFlow(flowParams);
					pointDao.updatePointTypeByBillId(id);
				}
			}
		}
		return Result.returnSuccess("").toString();
	}

	private boolean isAllowed(String ids, String column, String currStaff,
			String operate) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("IDS", ids);
		params.put("COLUMN", column);
		if("AUDITOR".equals(column)){
			List<Map<String, Object>> userList = pointDao.getUserList(params);
			if(userList.get(0) == null){
				return true;
			}else if(currStaff.equals(userList.get(0).get(column).toString())){
				return true;
			}else{
				return false;
			}
		}
		List<Map<String, Object>> userList = pointDao.getUserList(params);
		if (userList == null || userList.size() > 1 || userList.get(0) == null
				|| !currStaff.equals(userList.get(0).get(column).toString())
				) {
			return false;
		}
		return true;
	}

	public boolean isStatusRight(String ids, String allowedStatus) {
		List<Map<String, Object>> statusList = pointDao
				.getStatusIdsByBillIds(ids);
		if (statusList == null || statusList.size() < 1) {
			return false;
		}
		String status = "";
		for (Map<String, Object> map : statusList) {
			status += "," + map.get("STATUS_ID").toString();
		}
		status = status.substring(1);
		if (allowedStatus.indexOf(status) > -1) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> selectBillStaff(HttpServletRequest request) {
		String operate = request.getParameter("operate");
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("billIds", request.getParameter("billIds"));
		rs.put("operate", operate);
		if ("forward".equals(operate)) {
			List<Map<String, String>> sonAreaList = generalDao.getSonAreaList(request.getSession()
					.getAttribute("areaId").toString());
			rs.put("sonAreaList", sonAreaList);
		}
		return rs;
	}

	@Override
	public Map<String, Object> billInfo(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String billId = request.getParameter("billId");
		// 获取工单详细信息
		Map<String, String> billMap = pointDao.billInfo(billId);
		rs.put("billMap", billMap);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("BILL_ID", billId);
		String url = request.getServerName();
		params.put("url", getUrl(url));
		// 获取工单关联隐患上报记录id
		params.put("RECORD_TYPE", 1);
		List<Map<String, Object>> recordList = pointDao
				.getRecordByBillId(params);
		//临时上报也带出来
		params.put("RECORD_TYPE", 4);
		recordList.addAll(pointDao.getRecordByBillId(params));
		List<Map<String, String>> photoList = new ArrayList();
		int num = recordList.size();
		if(num!=0){
			params.put("RECORD_ID", StringUtil.objectToString(recordList
					.get(0).get("RECORD_ID")));
			photoList = pointDao.billPhoto(params);
		}
		rs.put("photoBefore", photoList);
		// 获取隐患政治的上报记录id
		params.put("RECORD_TYPE", 2);
		recordList = pointDao.getRecordByBillId(params);
		if (recordList != null && recordList.size() > 0
				&& recordList.get(0) != null
				&& recordList.get(0).get("RECORD_ID") != null) {
			params.put("RECORD_ID", StringUtil.objectToString(recordList
					.get(0).get("RECORD_ID")));
			List<Map<String, String>> photoList1 = pointDao
					.billPhoto(params);
			rs.put("photoAfter", photoList1);
		}
		return rs;
	}

	@Override
	public Map<String, Object> billFlow(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String billId = request.getParameter("billId");
		// 获取流程信息
		List<Map<String, String>> flowList = pointDao.billFlow(billId);
		rs.put("flowList", flowList);
		return rs;
	}

	@Override
	public Map<String, Object> editKeepPlan(HttpServletRequest request) {

		Map<String, Object> rs = new HashMap<String, Object>();

		Integer plan_id = Integer.valueOf(request.getParameter("plan_id"));
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户的区域信息
		Map params = new HashMap();
		params.put("STAFF_ID", staffId);
		// 获取当前用户的区域信息
		Map areaInfo = pointDao.getAreaInfo(params);
		rs.put("area_id", areaInfo.get("AREA_ID"));
		rs.put("area_name", areaInfo.get("NAME"));

		// 获取时间列表
		List<Map<String, String>> timeList = pointDao.getTimeList(plan_id);
		rs.put("timeList", timeList);

		Map map = new HashMap();
		map.put("PLAN_ID", plan_id);

		Map planInfo = pointDao.getPlan(map);// 计划信息
		rs.put("planInfo", planInfo);
		return rs;
	}

	public Boolean updatePointKeep(HttpServletRequest request) {
		try {
			String points = request.getParameter("points");
			String plan_id = request.getParameter("plan_id");
			String plan_name = request.getParameter("plan_name");
			String plan_no = request.getParameter("plan_no");
			String plan_type = request.getParameter("plan_type");
			String plan_start_time = request.getParameter("plan_start_time");
			String plan_end_time = request.getParameter("plan_end_time");
			String plan_circle = request.getParameter("plan_circle");
			String plan_frequency = request.getParameter("plan_frequency");
			String inaccuracy = request.getParameter("inaccuracy");
			String staffId = (String) request.getSession().getAttribute(
					"staffId");

			Map map = new HashMap();
			map.put("PLAN_ID", plan_id);
			map.put("PLAN_NAME", plan_name);
			map.put("PLAN_NO", plan_no);
			map.put("PLAN_TYPE", plan_type);
			map.put("PLAN_START_TIME", plan_start_time);
			map.put("PLAN_END_TIME", plan_end_time);
			map.put("PLAN_CIRCLE", 0);
			map.put("PLAN_FREQUENCY", "");
			map.put("CUSTOM_TIME", "");
			map.put("INACCURACY", inaccuracy);
			map.put("MODIFY_STAFF", staffId);
			pointDao.updatePlan(map);

			pointDao.deletePlanDetail(map);
			// 保存新的计划详细信息
			Map planDetail = null;
			String[] p = points.split(",");
			String[] temp = null;
			for (int i = 0, j = p.length; i < j; i++) {
				planDetail = new HashMap();
				temp = p[i].split(":");
				planDetail.put("PLAN_ID", plan_id);
				planDetail.put("INSPECT_OBJECT_ID", temp[0]);
				planDetail.put("INSPECT_OBJECT_TYPE", temp[1]);
				pointDao.insertDetail(planDetail);
			}

			String[] start_time = request.getParameter("start_time").split(",");
			String[] end_time = request.getParameter("end_time").split(",");
			Map<String, Object> keepTimeParams = new HashMap<String, Object>();
			// 时间段

			keepTimeParams.put("PLAN_ID", plan_id);
			pointDao.deleteKeepTime(keepTimeParams);
			for (int i = 0, j = start_time.length; i < j; i++) {
				if ("".equals(start_time[i])) {
					continue;
				}
				keepTimeParams.put("START_TIME", start_time[i]);
				keepTimeParams.put("END_TIME", end_time[i]);
				pointDao.saveKeepTime(keepTimeParams);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String queryPlans(HttpServletRequest request) {
		String keyword = request.getParameter("q");
		if (keyword != null) {
			try {
				keyword = new String(keyword.getBytes("ISO8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("KEYWORD", keyword);
		List<Map<String, Object>> planList = pointDao.queryPlans(params);
		return parseMapForAutoComplete(planList);
	}

	private String parseMapForAutoComplete(List<Map<String, Object>> list) {
		if (list == null || list.size() < 1) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		for (Map<String, Object> map : list) {
			for (String key : map.keySet()) {
				result.append(map.get(key).toString()).append("|");
			}
			result.deleteCharAt(result.length() - 1);
			result.append("\n");
		}
		result.deleteCharAt(result.length() - 1);
		return result.toString();
	}

	@Override
	public JSONObject deleteTaskById(HttpServletRequest request) {
		// 要删除的任务ID
		String ids = request.getParameter("selected");
		pointDao.deleteTaskDetailByTaskId(ids);
		pointDao.deleteTaskById(ids);
		return Result.returnSuccess("");
	}

	@Override
	public Map<String, Object> getRole(HttpServletRequest request) {
		Map<String, Object> rs = new HashMap<String, Object>();
		String roleNo = null;
		String staffId = request.getSession().getAttribute("staffId")
				.toString();// 当前用户
		// 获取当前用户所有角色
		List<Map<String, String>> roleList = roleDao.getAllByStaffId(staffId);
		for (Map<String, String> map : roleList) {
			roleNo = map.get("ROLE_NO");
			if (RoleNo.GLY.equals(roleNo) || RoleNo.LXXJ_ADMIN.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_AREA.equals(roleNo)
					|| RoleNo.LXXJ_ADMIN_SON_AREA.equals(roleNo)) {
				rs.put("admin", true);
				continue;
			}
			if (RoleNo.LXXJ_MAINTOR.equals(roleNo)) {
				rs.put("maintor", true);
				continue;
			}
			if (RoleNo.LXXJ_AUDITOR.equals(roleNo)) {
				rs.put("auditor", true);
				continue;
			}
		}
		return rs;
	}

	@Override
	public Map<String, Object> getKeepSpectors(HttpServletRequest request,
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
		List<Map> list = pointDao.getKeepSpectors(query);
		Map<String, Object> pmap = new HashMap<String, Object>(0);
		pmap.put("total", query.getPager().getRowcount());
		// pmap.put("total", list.size());
		pmap.put("rows", list);
		return pmap;
	}

	@Override
	public void billExport(HttpServletRequest request,HttpServletResponse response){
		 try {
				String point_no = "";
				try {
					point_no = point_no = new String(request.getParameter("point_no").getBytes("ISO8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				String bill_status = request.getParameter("bill_status");
				String create_time = request.getParameter("create_time");
				String complete_time = request.getParameter("complete_time");
				String pic = request.getParameter("pic");
				HttpSession session = request.getSession();
				String staffId = session.getAttribute("staffId").toString();// 当前用户
		
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("POINT_NO", point_no);
				map.put("BILL_STATUS", bill_status);
				map.put("CREATE_TIME", create_time);
				map.put("STAFF_ID", staffId);
				map.put("COMPLETE_TIME", complete_time);
				if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN)) {// 省级管理员
					map.put("SON_AREA_ID",request.getParameter("son_area_id")==""?null:request.getParameter("son_area_id"));
					map.put("ADMIN", true);
				} else {
					if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_AREA)) {// 是否是市级管理员
						map.put("AREA_ID", session.getAttribute("areaId"));
						//map.put("SON_AREA_ID", request.getParameter("son_area_id"));
						map.put("SON_AREA_ID",request.getParameter("son_area_id")==""?null:request.getParameter("son_area_id"));
						map.put("ADMIN", true);
					} else {
						if ((Boolean) session.getAttribute(RoleNo.LXXJ_ADMIN_SON_AREA)) {// 是否是区级管理员
							map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
							map.put("ADMIN", true);
						} else if (session.getAttribute(RoleNo.LXXJ_AUDITOR)==null?false:(Boolean) session.getAttribute(RoleNo.LXXJ_AUDITOR)) {// 如果是审核员只能查到本区域的
							map.put("SON_AREA_ID", session.getAttribute("sonAreaId"));
							map.put("ADMIN", true);
						}
					}
				}
				List<Map> olists = pointDao.billExport(map);
				XSSFWorkbook book = new XSSFWorkbook();
				XSSFSheet sheet = book.createSheet("隐患工单导出");
				sheet.setDefaultColumnWidth((short)12);
				XSSFRow topic = sheet.createRow(0);
				XSSFCell cell = topic.createCell(0);
				XSSFCellStyle columnStyle = book.createCellStyle();
				columnStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 左右居中   
				columnStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 上下居中
				cell.setCellValue(new XSSFRichTextString("工单ID"));
				cell.setCellStyle(columnStyle);
				 cell = topic.createCell(1);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("隐患点编码"));
				 cell = topic.createCell(2);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("隐患点名称"));
				 cell = topic.createCell(3);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("工单状态"));
				 cell = topic.createCell(4);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("工单类型"));
				 cell = topic.createCell(5);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("问题描述"));
				 cell = topic.createCell(6);
				cell.setCellValue(new XSSFRichTextString("巡检人员"));
				 cell = topic.createCell(7);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("维护人员"));
				 cell = topic.createCell(8);
				 cell.setCellStyle(columnStyle);
				cell.setCellValue(new XSSFRichTextString("整改结束时间"));
				cell = topic.createCell(9);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("审核人员"));
				 cell = topic.createCell(10);
				 cell.setCellValue(new XSSFRichTextString("所属网格"));
				 cell = topic.createCell(11);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("区县"));
				 cell = topic.createCell(12);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("上报日期"));
				 cell = topic.createCell(13);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("光缆名称"));
				 cell = topic.createCell(14);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("光缆等级"));
				 cell = topic.createCell(15);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("施工单位"));
				 cell = topic.createCell(16);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("施工内容"));
				 if(!("undefined").equals(pic)){
				 cell = topic.createCell(17);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("整改前照片"));
				 cell = topic.createCell(20);
				 cell.setCellStyle(columnStyle);
				 cell.setCellValue(new XSSFRichTextString("整改后照片"));
				 }
				Map<String, String> billMap =new HashMap();
				for (int i = 0; i < olists.size(); i++) {
					// 获取工单详细信息
					billMap = pointDao.billInfo(olists.get(i).get("BILL_ID").toString());
					XSSFRow row = sheet.createRow(1+i);
					XSSFCell cel = row.createCell(0);
					if(!"".equals(olists.get(i).get("BILL_ID"))&&null!=olists.get(i).get("BILL_ID")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("BILL_ID").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(1);
					if(!"".equals(olists.get(i).get("POINT_NO"))&&null!=olists.get(i).get("POINT_NO")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("POINT_NO").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(2);
					if(!"".equals(olists.get(i).get("POINT_NAME"))&&null!=olists.get(i).get("POINT_NAME")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("POINT_NAME").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(3);
					if(!"".equals(olists.get(i).get("STATUS_NAME"))&&null!=olists.get(i).get("STATUS_NAME")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("STATUS_NAME").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(4);
					if(!"".equals(olists.get(i).get("BILL_TYPE"))&&null!=olists.get(i).get("BILL_TYPE")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("BILL_TYPE").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(5);
					if(!"".equals(olists.get(i).get("DESCRP"))&&null!=olists.get(i).get("DESCRP")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("DESCRP").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(6);
					if(!"".equals(olists.get(i).get("INSPECTOR"))&&null!=olists.get(i).get("INSPECTOR")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("INSPECTOR").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(7);
					if(!"".equals(olists.get(i).get("MAINTOR"))&&null!=olists.get(i).get("MAINTOR")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("MAINTOR").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(8);
					if(!"".equals(olists.get(i).get("COMPLETE_TIME"))&&null!=olists.get(i).get("COMPLETE_TIME")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("COMPLETE_TIME").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(9);
					if(!"".equals(olists.get(i).get("AUDITOR"))&&null!=olists.get(i).get("AUDITOR")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("AUDITOR").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(10);
					if(!"".equals(olists.get(i).get("DEPT_NAME"))&&null!=olists.get(i).get("DEPT_NAME")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("DEPT_NAME").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(11);
					if(!"".equals(olists.get(i).get("SON_AREA"))&&null!=olists.get(i).get("SON_AREA")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("SON_AREA").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(12);
					if(!"".equals(olists.get(i).get("CREATE_TIME"))&&null!=olists.get(i).get("CREATE_TIME")){
						cel.setCellValue(new XSSFRichTextString(olists.get(i).get("CREATE_TIME").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(13);
					if(!"".equals(billMap.get("CABLE_NAME"))&&null!=billMap.get("CABLE_NAME")){
						cel.setCellValue(new XSSFRichTextString(billMap.get("CABLE_NAME").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(14);
					if(!"".equals(billMap.get("CABLE_LEVEL"))&&null!=billMap.get("CABLE_LEVEL")){
						cel.setCellValue(new XSSFRichTextString(billMap.get("CABLE_LEVEL").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(15);
					if(!"".equals(billMap.get("CONS_UNIT"))&&null!=billMap.get("CONS_UNIT")){
						cel.setCellValue(new XSSFRichTextString(billMap.get("CONS_UNIT").toString()));
					}
					cel.setCellStyle(columnStyle);
					cel = row.createCell(16);
					if(!"".equals(billMap.get("CONS_CONTENT"))&&null!=billMap.get("CONS_CONTENT")){
						cel.setCellValue(new XSSFRichTextString(billMap.get("CONS_CONTENT").toString()));
					}
					cel.setCellStyle(columnStyle);
		
					//图片处理
					if(!("undefined").equals(pic)){
						row.setHeight((short) (800));
						
		//				cel = row.createCell(16);
		//			if(!"".equals(olists.get(i).get("POINT_NO"))&&null!=olists.get(i).get("POINT_NO")){
		//				cel.setCellValue(new XSSFRichTextString(olists.get(i).get("POINT_NO").toString()));
		//			}
		//			cel.setCellStyle(columnStyle);
		//			cel = row.createCell(17);
		//			if(!"".equals(olists.get(i).get("POINT_NO"))&&null!=olists.get(i).get("POINT_NO")){
		//				cel.setCellValue(new XSSFRichTextString(olists.get(i).get("POINT_NO").toString()));
		//			}
		//			cel.setCellStyle(columnStyle);
			
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("BILL_ID", olists.get(i).get("BILL_ID").toString());
					// 获取工单关联隐患上报记录id
					params.put("RECORD_TYPE", 1);
					List<Map<String, Object>> recordList = pointDao
							.getRecordByBillId(params);
					//临时上报也带出来
					params.put("RECORD_TYPE", 4);
					recordList.addAll(pointDao.getRecordByBillId(params));
					List<Map<String, String>> photoList = null;
					int num = recordList.size();
					CreationHelper helper = book.getCreationHelper();
					Drawing drawing=sheet.createDrawingPatriarch();
					int co=0;
					params.put("url", "61.160.128.47");
					if(num!=0){
						params.put("RECORD_ID", StringUtil.objectToString(recordList
								.get(0).get("RECORD_ID")));
					photoList = pointDao.billPhoto(params);
					ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
					InputStream is = null;
					try {
						for (Map<String, String> map2 : photoList) {
							if(null!=map2.get("PHOTO_PATH")&&!"".equals(map2.get("PHOTO_PATH"))){
								URL url;
								url = new URL(map2.get("PHOTO_PATH").replace("61.160.128.47", "132.228.237.107"));
								URLConnection con = url.openConnection();
								is = con.getInputStream();
								BufferedImage bufferImg=ImageIO.read(is);
								ImageIO.write(bufferImg, "jpg", arrayOut);
								cel = row.createCell(16+co);
								ClientAnchor anchor = helper.createClientAnchor();
								int picByte = book.addPicture(arrayOut.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
								anchor.setCol1(17+co);
								anchor.setRow1(1+i);
								anchor.setCol2(18+co);
								anchor.setRow2(2+i);
								anchor.setDx1(0);
								anchor.setDy1(0);
								anchor.setDx2(100);
								anchor.setDy2(100);
								
								Picture pict = drawing.createPicture(anchor,picByte);
								co+=1;
								if(co==3){
									break;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally{
						try {
							if(arrayOut != null){
								arrayOut.close();
							}
							if(is != null){
								is.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					co=0;
					//pict.resize();
					}
					// 获取隐患政治的上报记录id
					params.put("RECORD_TYPE", 2);
					recordList = pointDao.getRecordByBillId(params);
					ByteArrayOutputStream arrayOut2 = null;
					InputStream is = null;
					try {
						  if (recordList != null && recordList.size() > 0
								&& recordList.get(0) != null
								&& recordList.get(0).get("RECORD_ID") != null) {
							  params.put("RECORD_ID", StringUtil.objectToString(recordList
										.get(0).get("RECORD_ID")));
							List<Map<String, String>> photoList1 = pointDao
									.billPhoto(params);
							arrayOut2 = new ByteArrayOutputStream();
							for (Map<String, String> map2 : photoList1) {
								if(null!=map2.get("PHOTO_PATH")&&!"".equals(map2.get("PHOTO_PATH"))){
									URL url = new URL(map2.get("PHOTO_PATH").replace("61.160.128.47", "132.228.237.107"));
									URLConnection con = url.openConnection();
									is = con.getInputStream();
									BufferedImage bufferImg=ImageIO.read(is);
									ImageIO.write(bufferImg, "jpg", arrayOut2);
									ClientAnchor anchor2 = helper.createClientAnchor();
									int picByte2 = book.addPicture(arrayOut2.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
									anchor2.setCol1(20+co);
									anchor2.setRow1(1+i);
									anchor2.setCol2(21+co);
									anchor2.setRow2(2+i);
									anchor2.setDx1(0);
									anchor2.setDy1(0);
									anchor2.setDx2(100);
									anchor2.setDy2(100);
									Picture pict2 = drawing.createPicture(anchor2,picByte2);
									co+=1;
									if(co==3){
										break;
									}
								}
							}
							//pict2.resize();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally{
						try {
							if(arrayOut2 != null){
								arrayOut2.close();
							}
							if(is != null){
								is.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				  }
				}
				response.reset();
			    response.setContentType("application/octet-stream; charset=utf-8"); 
			    OutputStream os = null;
			    try {
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(("隐患工单导出.xlsx").getBytes("gb2312"), "iso8859-1"));
					os = response.getOutputStream();
					book.write(os);
					os.flush();
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(os != null){
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
       } catch (Exception e) {
    	   e.printStackTrace();
       }			    
	}

	@Override
	public List<Map> getTroubleType() {
		return pointDao.getTroubleType();
	}
	
	private String getUrl(String url){
		List outside=PropertiesUtil.getPropertyToList("outside");
		List inside=PropertiesUtil.getPropertyToList("inside");
		String trueUrl="";
		for (int i = 0; i < inside.size(); i++) {
			if(url.indexOf(inside.get(i).toString())>=0){
				trueUrl= inside.get(i).toString();
				break;
			}else{
				trueUrl=outside.get(0).toString();
				break;
			}
		}
		return trueUrl;
	}
}
