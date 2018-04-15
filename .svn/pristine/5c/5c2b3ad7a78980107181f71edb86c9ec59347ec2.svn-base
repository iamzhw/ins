package com.linePatrol.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.linePatrol.dao.StepPartTaskDao;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.StepPartTaskUtil;

/**
 * 步巡计划任务生成
 * 
 * @author sunmin
 * @author 2016-5-24
 */
@SuppressWarnings("all")

public class GeneratePartTaskJob {
	
	@Resource
	private StepPartTaskDao StepPartTaskDao;
	
	//一干开始日期和结束日期
	private String OneCircleBeginMonth;
	private String OneCircleEndMonth;
	
	//二干开始日期和结束日期
	private String TwoCircleBeginMonth;
	private String TwoCircleEndMonth;
	
	//地标开始日期和结束日期
	private String LandMarkCircleBeginMonth;
	private String LandMarkCircleEndMonth;
	
	//定义一个Map集合存放数据
	private Map<String,Object> paramMap = new HashMap<String,Object>();
	
	private String lmid;//地标id
	private String landMarkCircle;//地标周期
	
	public void execute() {
		getCircles();//获取开始日期与结束日期
		createTask();//生成任务
		createStepTaskEquip();// 生成步巡任务路由任务点
		createNoRouteTask();//查询非路由有没有对应未生成的任务
		createNoRouteTaskEquip();//生成非路由任务点
	}
	
	private void getCircles(){
		//先把当前周期给找出来,查找步巡段时候可以把当前周期给过滤掉
		int currentMonth = DateUtil.getCurrentMonth();//获取当前月
		
		//获取一干数组的当前开始日期与结束日期
		int[] OneLineMonths = StepPartTaskUtil.oneLevelGroundline;//一干月份数组
		int oneBeginMonth= DateUtil.getBeginMOnth(OneLineMonths, currentMonth);//根据当前月份,和当前月份数组获取开始月份
	    OneCircleBeginMonth=DateUtil.getFirstDayOfMonth(oneBeginMonth);//获取一干开始月的1号
		OneCircleEndMonth=DateUtil.getTimeByCycle(oneBeginMonth,2);//获取结束时间
		
		//获取二干数组的当前开始日期与结束日期
		int[] TwoLineMonths = StepPartTaskUtil.twoLevelGroundline;//二干月份数组
		int twoBeginMonth= DateUtil.getBeginMOnth(TwoLineMonths, currentMonth);//根据当前月份,和当前月份数组获取开始月份
	    TwoCircleBeginMonth=DateUtil.getFirstDayOfMonth(twoBeginMonth);//获取二干开始月的1号
	    TwoCircleEndMonth=DateUtil.getTimeByCycle(twoBeginMonth,3);//获取结束时间
		
	    //获取地标数组开始日期和结束日期
		int[] LandMarkLineMonths = StepPartTaskUtil.landMarkGroundline;//地标月份数组
		int landMarkBeginMonth= DateUtil.getBeginMOnth(LandMarkLineMonths, currentMonth);//根据当前月份,和当前月份数组获取开始月份
		LandMarkCircleBeginMonth=DateUtil.getFirstDayOfMonth(landMarkBeginMonth);//获取二干开始月的1号
		LandMarkCircleEndMonth=DateUtil.getTimeByCycle(landMarkBeginMonth,6);//获取结束时间
		
		lmid=StepPartTaskDao.selLMTypeId();
		paramMap.put("equip_type", lmid);
		paramMap.put("OneCircleBeginMonth",OneCircleBeginMonth);
		paramMap.put("OneCircleEndMonth",OneCircleEndMonth);
		paramMap.put("TwoCircleBeginMonth",TwoCircleBeginMonth);
		paramMap.put("TwoCircleEndMonth",TwoCircleEndMonth);
		paramMap.put("LandMarkCircleBeginMonth",LandMarkCircleBeginMonth);
		paramMap.put("LandMarkCircleEndMonth",LandMarkCircleEndMonth);
		landMarkCircle=StepPartTaskDao.selCircleByLandMark();//获取地标周期
		paramMap.put("circle_id",landMarkCircle);
		
	}
	
	private void createTask(){
		//查询出未生成路由非地标任务的人员频次去生成任务,过滤本周期之内生成的步巡段
		List<Map<String,Object>> manCircles=StepPartTaskDao.selNoLMTaskPeople(paramMap);
		if(manCircles.size()>0){
			for(Map<String,Object> manCircle:manCircles){
				String task_id = StepPartTaskDao.selTaskId();
				String inspect_id=manCircle.get("INSPECT_ID").toString();
				String staff_name=manCircle.get("STAFF_NAME").toString();
				int circle_id=Integer.parseInt(manCircle.get("CIRCLE_ID").toString());
				String start_time=null;
				String end_time = null;
				if(circle_id == 2){//一干赋值开始月份和结束月份
					start_time=OneCircleBeginMonth;
					end_time=OneCircleEndMonth;
				}else if(circle_id == 3){//二干赋值开始月份和结束月份
					start_time=TwoCircleBeginMonth;
					end_time=TwoCircleEndMonth;
				}
				manCircle.put("START_TIME", start_time);
				String task_name=staff_name+"_编号:"+inspect_id+": "+String.valueOf(circle_id)+"月任务"+
						   start_time+"至"+end_time;
				manCircle.put("END_TIME", end_time);
				manCircle.put("TASK_NAME", task_name);
				manCircle.put("TASK_ID", task_id);
				StepPartTaskDao.insertStepPartTask(manCircle);
			}
		}
		
		//根据地标频次去查询未分配地标任务人员,过滤本周期之内生成的步巡段
		List<Map<String,Object>> manLMCircles=StepPartTaskDao.selLMTaskPeople(paramMap);
		if(manLMCircles.size()>0){
			for(Map<String,Object> manLmCircle:manLMCircles){
				String task_id = StepPartTaskDao.selTaskId();
				String inspect_id=manLmCircle.get("INSPECT_ID").toString();
				String staff_name=manLmCircle.get("STAFF_NAME").toString();
				int circle_id=Integer.parseInt(landMarkCircle);
				manLmCircle.put("CIRCLE_ID", landMarkCircle);
				String start_time = LandMarkCircleBeginMonth;
				String end_time=LandMarkCircleEndMonth;
				manLmCircle.put("START_TIME", start_time);
				String task_name=staff_name+"_编号:"+inspect_id+": "+String.valueOf(circle_id)+"月地标任务"+
						   start_time+"至"+end_time;
				manLmCircle.put("END_TIME", end_time);
				manLmCircle.put("TASK_NAME", task_name);
				manLmCircle.put("EQUIP_TYPE", lmid);
				manLmCircle.put("TASK_ID", task_id);
				StepPartTaskDao.insertLMStepPartTask(manLmCircle);
			}
		}
		System.out.println("路由任务生成完成。。。");
	}
	
	private void createStepTaskEquip() {

		//根据未过期的任务去查找普通类型步巡段把相应的设施点插入到任务设施关系表中
		List<Map<String,Object>> noLMParts =StepPartTaskDao.selNoLMStepPartBysTask(paramMap);
		for(Map<String,Object> noLMPart:noLMParts){
			//判断有没有非地标的步巡点
			noLMPart.put("EQUIP_TYPE", lmid);
			List<Map<String,Object>> isNoLandMark=StepPartTaskDao.selIsContainNoLandMark(noLMPart);
			List<Map<String,Object>> isNoLandMarkInEquip=StepPartTaskDao.selIsContainNoLangMarkInEquip(noLMPart);
			if(isNoLandMark.size()>0 || isNoLandMarkInEquip.size()>0 ){
				//先将多余的点给移除到历史表中,然后删除多余点。再新增新的设施点...
				StepPartTaskDao.intoTaskHisNoLM(noLMPart);
				StepPartTaskDao.delTaskEquipNoLM(noLMPart);
				StepPartTaskDao.intoTaskEquipAddNoLM(noLMPart);
			}
		}
		
		//根据地标周期,id去查询未被分配所有地标类型的步巡段
		List<Map<String,Object>> LMparts=StepPartTaskDao.selLMStepPartTask(paramMap);
		for(Map<String,Object> LMPart:LMparts){
			LMPart.put("CIRCLE_ID", landMarkCircle);
 			LMPart.put("EQUIP_TYPE",lmid);
			//判断当前步巡段中是否包含地标 
			List<Map<String,Object>> isLandMark=StepPartTaskDao.selIsContainLandMark(LMPart);
			List<Map<String,Object>> isLandMarkInEquip=StepPartTaskDao.selIsContainLankMarkInEquip(LMPart);
			if(isLandMark.size()>0 || isLandMarkInEquip.size()>0){
				//先删除后增加，删除时先把信息保存到历史表中去
				StepPartTaskDao.insertToTaskHis(LMPart);//保存要删除的记录到历史表
				StepPartTaskDao.delTaskEquipLM(LMPart);//删除设施任务表多余的记录
				StepPartTaskDao.intoTaskEquipAddLM(LMPart);//插入步巡段新加的设施点
			}
			
		}
		//从任务点表中删除已经不存在的步巡点
		StepPartTaskDao.delLostEquip();
		System.out.println("路由任务点生成完成。。。");
	}
	
	private void createNoRouteTask() {
		//查询哪些非路由底下的步巡段没有被分配非地标任务,去除本周期内生成的步巡段
		List<Map<String,Object>> manCircles = StepPartTaskDao.selNoLMNoRouteTaskEquip(paramMap);
		if(manCircles.size()>0){//遍历取值，然后插入到任务表当中去
			for(Map<String,Object> manCircle:manCircles){
				String task_id = StepPartTaskDao.selTaskId();
				String inspect_id=manCircle.get("INSPECT_ID").toString();
				String staff_name=manCircle.get("STAFF_NAME").toString();
				int circle_id=Integer.parseInt(manCircle.get("CIRCLE_ID").toString());
				String start_time=null;
				String end_time = null;
				if(circle_id == 2){//一干赋值开始月份和结束月份
					start_time=OneCircleBeginMonth;
					end_time=OneCircleEndMonth;
				}else if(circle_id == 3){//二干赋值开始月份和结束月份
					start_time=TwoCircleBeginMonth;
					end_time=TwoCircleEndMonth;
				}
				manCircle.put("START_TIME", start_time);
				String task_name=staff_name+"_编号:"+inspect_id+": "+String.valueOf(circle_id)+"月任务"+
						   start_time+"至"+end_time;
				manCircle.put("END_TIME", end_time);
				manCircle.put("TASK_NAME", task_name);
				manCircle.put("TASK_ID", task_id);
				StepPartTaskDao.insertStepPartTask(manCircle);
			}
		}
		
		//根据地标频次去查询未分配地标任务人员,过滤本周期内生成的步巡段
		List<Map<String,Object>> manLMCircles=StepPartTaskDao.selLMNoRouteTaskEquip(paramMap);
		if(manLMCircles.size()>0){
			for(Map<String,Object> manLmCircle:manLMCircles){
				String task_id = StepPartTaskDao.selTaskId();
				String inspect_id=manLmCircle.get("INSPECT_ID").toString();
				String staff_name=manLmCircle.get("STAFF_NAME").toString();
				int circle_id=Integer.parseInt(landMarkCircle);
				manLmCircle.put("CIRCLE_ID", landMarkCircle);
				String start_time = LandMarkCircleBeginMonth;
				String end_time=LandMarkCircleEndMonth;
				manLmCircle.put("START_TIME", start_time);
				String task_name=staff_name+"_编号:"+inspect_id+": "+String.valueOf(circle_id)+"月地标任务"+
						   start_time+"至"+end_time;
				manLmCircle.put("END_TIME", end_time);
				manLmCircle.put("TASK_NAME", task_name);
				manLmCircle.put("EQUIP_TYPE", lmid);
				manLmCircle.put("TASK_ID", task_id);
				StepPartTaskDao.insertLMStepPartTask(manLmCircle);
			}
		}
		
		System.out.println("非路由任务生成完成。。。");
	}

	private void createNoRouteTaskEquip() {

		//根据未过期的任务去查找普通类型步巡段把相应的设施点插入到任务设施关系表中,针对非路由非地标任务点
		List<Map<String,Object>> noLMParts =StepPartTaskDao.selNoLMStepPartBysTask(paramMap);
		for(Map<String,Object> noLMPart:noLMParts){
			//判断有没有非地标的步巡点
			noLMPart.put("EQUIP_TYPE", lmid);
			//插入新增的非地标非路由非地标设施点
			StepPartTaskDao.intoHisTaskEquipNoRouthLM(noLMPart);//先将多余的非路由非地标任务点给备份到任务表中
			StepPartTaskDao.delHisTaskEquipNoRouthLM(noLMPart);//将刚刚备份到历史表的非路由非地标任务点全部删除
			StepPartTaskDao.intoTaskHisNoLMRoute(noLMPart);//插入新增的非路由非地标任务点
		}
		
		//根据地标周期,id去查询未被分配所有地标类型的步巡段,针对非路由地标任务点
		List<Map<String,Object>> LMparts=StepPartTaskDao.selLMStepPartTask(paramMap);
		for(Map<String,Object> LMPart:LMparts){
			LMPart.put("CIRCLE_ID", landMarkCircle);
			LMPart.put("EQUIP_TYPE",lmid);
			//处理非路由地标任务点
			StepPartTaskDao.intoHisTaskLMEquipNoRouth(LMPart);//先将多余的非路由地标任务点给备份到任务表中
			StepPartTaskDao.delHisTaskLMEquipNoRouth(LMPart);//将刚刚备份到历史表的非路由地标任务点全部删除
			StepPartTaskDao.intoTaskEquipAddLMRoute(LMPart);//插入新增的非路由地标点
			
		}
		
		StepPartTaskDao.delLostEquipRouteEquip();//删除未挂上路由点的非路由点
		StepPartTaskDao.delLostEquip();//从任务点表中删除已经不存在的步巡点
		StepPartTaskDao.IntoHisTaskEquipDelAllot();//先将要删除的任务点给移动到历史表中去
		StepPartTaskDao.removeStepPartRecord();//设施任务关联表删除不存在的步巡段记录
		System.out.println("非路由任务点生成完成。。。");
	}
	
}
