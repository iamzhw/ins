package com.cableInspection.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cableInspection.dao.CableTaskDao;
import com.cableInspection.dao.PointDao;
import com.cableInspection.dao.TaskCreateDao;
import com.cableInspection.service.TaskCreateService;
import com.system.dao.StaffDao;
import com.util.DateUtil;
import com.util.KeepRule;
import com.util.Rule;
import com.util.StringUtil;
import com.util.sendMessage.SendMessageUtil;

/**
 * 任务生成service
 * 
 * @author fengjl
 * 
 */
@Service
@Transactional(rollbackFor = { Exception.class })
@SuppressWarnings("all")
public class TaskCreateServiceImpl implements TaskCreateService {

	@Resource
	private PointDao pointDao;

	@Resource
	private TaskCreateDao taskCreateDao;
	
	@Resource
	private StaffDao staffDao;
	
	@Resource
	private CableTaskDao cabletaskDao;

	/**
	 * 
	 * @param type
	 *            1：日计划和周计划；2：月计划
	 */
	@Override
	public void createTask(int type) {
		// 本周的第一天
		Date firstDateOfCurrWeek = DateUtil.getMondayOfCurrWeek();
		String str_firstDateOfCurrWeek = StringUtil.dateToString(
				firstDateOfCurrWeek, "yyyy-MM-dd");
		// 本周的最后一天
		Date lastDateOfCurrWeek = DateUtil.getSundayOfCurrWeek();
		String str_lastDateOfCurrWeek = StringUtil.dateToString(
				lastDateOfCurrWeek, "yyyy-MM-dd");
		// 本月的第一天
		Date firstDateOfCurrMonth = DateUtil.getFirstDayOfCurrMonth();
		String str_firstDateOfCurrMonth = StringUtil.dateToString(
				firstDateOfCurrMonth, "yyyy-MM-dd");
		// 本月的最后一天
		Date lastDateOfCurrMonth = DateUtil.getLastDayOfCurrMonth();
		String str_lastDateOfCurrMonth = StringUtil.dateToString(
				lastDateOfCurrMonth, "yyyy-MM-dd");

		// 获取所有需要生成任务的计划
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		if (type == 1) {
			params.put("MIN_END_DATE", str_firstDateOfCurrWeek);
			params.put("MAX_BEGIN_DATE", str_lastDateOfCurrWeek);
		} else if (type == 2) {
			params.put("MIN_END_DATE", str_firstDateOfCurrMonth);
			params.put("MAX_BEGIN_DATE", str_lastDateOfCurrMonth);
		}
		List<Map<String, Object>> planList = taskCreateDao
				.getPlansForCreate(params);
		if (planList == null || planList.size() < 1) {
			return;
		}

		Integer PLAN_ID = null;
		String PLAN_NO = null;
		Date PLAN_START_TIME = null;
		Date PLAN_END_TIME = null;
		String PLAN_FREQUENCY = null;
		String PLAN_CIRCLE = null;
		String PLAN_KIND = null;
		String AREA_ID = null;
		String SON_AREA_ID = null;
		String TASK_INSPECTOR = null;
		String TASK_CREATOR = null;
		String CUSTOM_TIME = null;

		List<Map> ruleData = null;// 生成的时间段
		Map<String, Object> taskMap = null;// 任务参数map
		Integer TASK_ID = null;// 任务id
		List<Map> planDetailList = null;// 计划对应外力点信息集合

		String INSPECT_OBJECT_ID = null;// 巡检对象id
		String INSPECT_OBJECT_TYPE = null;// 巡检对象类型
		Map taskDetailMap = null;
		for (Map<String, Object> plan : planList) 
		{
			PLAN_ID = Integer.valueOf(plan.get("PLAN_ID").toString());
			PLAN_NO = plan.get("PLAN_NO").toString();
			PLAN_START_TIME = (Date) plan.get("PLAN_START_TIME");
			PLAN_END_TIME = (Date) plan.get("PLAN_END_TIME");
			PLAN_FREQUENCY = StringUtil.objectToString(plan
					.get("PLAN_FREQUENCY"));
			PLAN_CIRCLE = StringUtil.objectToString(plan.get("PLAN_CIRCLE"));
			PLAN_KIND = plan.get("PLAN_KIND").toString();
			AREA_ID = plan.get("AREA_ID").toString();
			SON_AREA_ID = plan.get("SON_AREA_ID").toString();
			TASK_INSPECTOR = plan.get("TASK_INSPECTOR").toString();
			TASK_CREATOR = plan.get("TASK_CREATOR").toString();
			CUSTOM_TIME = StringUtil.objectToString(plan.get("CUSTOM_TIME"));

			Map ruleMap = new HashMap();// 查询任务生成规则参数map

			if (type == 2) 
			{
				if (PLAN_START_TIME.after(firstDateOfCurrMonth)) {// 计划开始时间在本月第一天后
					ruleMap.put("startDate", StringUtil.dateToString(
							PLAN_START_TIME, "yyyy-MM-dd"));
				} else {
					ruleMap.put("startDate", StringUtil.dateToString(
							firstDateOfCurrMonth, "yyyy-MM-dd"));
				}
				if (PLAN_END_TIME.before(lastDateOfCurrMonth)) {// 计划结束时间在本月最后一天前
					ruleMap.put("endDate", StringUtil.dateToString(
							PLAN_END_TIME, "yyyy-MM-dd"));
				} else {
					ruleMap.put("endDate", StringUtil.dateToString(
							lastDateOfCurrMonth, "yyyy-MM-dd"));
				}
				
				ruleMap.put("frequency", PLAN_FREQUENCY);
				ruleMap.put("custom_time", CUSTOM_TIME);
				ruleData = Rule.createTaskOrder(ruleMap, PLAN_CIRCLE);
			} 
			else if (type == 1) 
			{
				if (PLAN_START_TIME.after(firstDateOfCurrWeek)) {// 计划开始时间在本周第一天后
					ruleMap.put("startDate", StringUtil.dateToString(
							PLAN_START_TIME, "yyyy-MM-dd"));
				} else {
					ruleMap.put("startDate", StringUtil.dateToString(
							firstDateOfCurrWeek, "yyyy-MM-dd"));
				}
				if (PLAN_END_TIME.before(lastDateOfCurrWeek)) {// 计划结束时间在本周最后一天前
					ruleMap.put("endDate", StringUtil.dateToString(
							PLAN_END_TIME, "yyyy-MM-dd"));
				} else {
					ruleMap.put("endDate", StringUtil.dateToString(
							lastDateOfCurrWeek, "yyyy-MM-dd"));
				}
				
				if ("3".equals(PLAN_KIND)) {// 看护计划
					// 获取看护任务的时间段
					List<Map<String, String>> timeList = pointDao
							.getTimeList(PLAN_ID);
					ruleMap.put("timeList", timeList);
					ruleData = KeepRule.createTaskOrder(ruleMap);// 生成任务的开始和结束时间
				} else {
					ruleMap.put("frequency", PLAN_FREQUENCY);
					ruleMap.put("custom_time", CUSTOM_TIME);
					ruleData = Rule.createTaskOrder(ruleMap, PLAN_CIRCLE);
				}
			}
			
			taskMap = new HashMap();
			taskMap.put("PLAN_ID", PLAN_ID);
			taskMap.put("PLAN_NO", PLAN_NO);
			taskMap.put("CREATE_STAFF", TASK_CREATOR);
			taskMap.put("INSPECTOR", TASK_INSPECTOR);
			taskMap.put("AREA_ID", AREA_ID);
			taskMap.put("SON_AREA_ID", SON_AREA_ID);
			// 收信人号码
			List phoneNumList = new ArrayList();
			// 发送内容
			List messageContentList = new ArrayList();
			for (Map rule : ruleData) {
				taskMap.put("COMPLETE_TIME", rule.get("endDate"));
				taskMap.put("START_TIME", rule.get("startDate"));
				try {
					//判断任务是否存在
					if(pointDao.ifTaskExists(taskMap)>0){
						continue;
					}
					pointDao.saveTask(taskMap);// 保存任务信息
				} catch (Exception e) {
					System.out.println(taskMap);
				}
				
				TASK_ID = Integer.valueOf(taskMap.get("TASK_ID").toString());
				// 查询出计划对应的计划详细
				planDetailList = taskCreateDao.getCablePlanDetail(PLAN_ID);
//				planDetailList = pointDao.getPlanDetail(PLAN_ID);
				// 保存任务详细信息
				taskDetailMap = new HashMap();
				taskDetailMap.put("TASK_ID", TASK_ID);
				taskDetailMap.put("INSPECTOR", TASK_INSPECTOR);
				for (Map planDetail : planDetailList) {
					taskDetailMap.put("INSPECT_OBJECT_ID",
							planDetail.get("INSPECT_OBJECT_ID").toString());
					taskDetailMap.put("INSPECT_OBJECT_TYPE",
							PLAN_KIND);
					pointDao.saveTaskDetail(taskDetailMap);
				}
				try{
					// 获取人员的信息
					Map<String, Object> staffInfo = staffDao.getStaff(TASK_INSPECTOR);
					Map<String, Object> taskInfo = cabletaskDao.getTaskInfo(TASK_ID);
					String equipCode = taskInfo.get("TASK_NO").toString();
					// 发送短信内容
					String messageText ="";
					
					if ( null != staffInfo
							&& null != staffInfo.get("STAFF_NAME")
							&& null != staffInfo.get("TEL")
							&& !"".equals(staffInfo.get("TEL")))
					{
						messageText = staffInfo.get("STAFF_NAME") + "，您好。您于"
								+ DateUtil.getCurrentTime() + "收到"
								+ equipCode
								+ "任务，请及时执行。【分公司缆线巡检】";
						phoneNumList.add(staffInfo.get("TEL"));
						messageContentList.add(messageText);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 发送短信
			//SendMessageUtil.sendMessageList(phoneNumList,messageContentList);
		}
	}
}
