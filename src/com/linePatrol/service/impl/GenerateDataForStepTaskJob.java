package com.linePatrol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.linePatrol.dao.StepPartTaskDao;
import com.linePatrol.util.DateUtil;
import com.linePatrol.util.StepPartTaskUtil;

/**
 * 生成步巡任务的最终数据，每个月统计一次
 * 
 * @author sunmi
 * @author 2017-1-10
 */
public class GenerateDataForStepTaskJob {

	@Resource
	private StepPartTaskDao StepPartTaskDao;

	public void generateData() {
		createDate();// 生成上一周期步巡统计表数据
		createCurrentDate();//生成当前周期的步巡统计表数据
	}

	private void createDate() {
		int currentMonth = DateUtil.getCurrentMonth();// 获取当前月
		/**
		 * 1.按地标、一干、二干 分别统计 2.获取当前时间，并且查询出地标、一干、二干的上一个周期
		 * 3.查询出步巡统计表中上一个周期存不存在，如果存在就不管，继续下一个 4.不存在就去生成统计数据和对应错误类型数量的数据
		 */
		// 获取一干数组的上一个周期开始日期与结束日期
		Map<String, Object> oneMap = new HashMap<String, Object>();
		int[] OneLineMonths = StepPartTaskUtil.oneLevelGroundline;// 一干月份数组
		Map<String, Object> oneLineMap = DateUtil.getLastStartMOnth(OneLineMonths, currentMonth, 2);
		String oneCircleBeginMonth = oneLineMap.get("beginMonth").toString();// 获取一干上周期开始月的1号
		String oneCircleEndMonth = oneLineMap.get("endMonth").toString();// 获取结束时间
		oneMap.put("beginMonth", oneCircleBeginMonth);
		oneMap.put("endMonth", oneCircleEndMonth);
		oneMap.put("circle", 2);
		List<Map<String, Object>> oneLineList = StepPartTaskDao.selStepStatisticsInLastCircle(oneMap);
		if (oneLineList.size() > 0) {
			// 不管了，存在该干线上周期数据就不生成
		}else{
			// 生成统计数据
			oneMap.put("task_type", 1);// 统计表中统计类型1.一干2.二干3.地标
			int one =  StepPartTaskDao.intoStepStatistics(oneMap);// 插入统计表
			System.out.println("一干插入统计表数:"+one);
			int oneType = StepPartTaskDao.intoStepStatisticsType(oneMap);// 插入统计表关联任务
			System.out.println("一干插入统计表类型数:"+oneType);
		}

		// 获取二干数组上一个周期开始时间和结束时间
		Map<String, Object> twoMap = new HashMap<String, Object>();
		int[] TwoLineMonths = StepPartTaskUtil.twoLevelGroundline;// 二干月份数组
		Map<String, Object> twoLineMap = DateUtil.getLastStartMOnth(TwoLineMonths, currentMonth, 3);
		String twoCircleBeginMonth = twoLineMap.get("beginMonth").toString();// 获取二干上周期开始月的1号
		String twoCircleEndMonth = twoLineMap.get("endMonth").toString();// 获取结束时间
		twoMap.put("beginMonth", twoCircleBeginMonth);
		twoMap.put("endMonth", twoCircleEndMonth);
		twoMap.put("circle", 3);
		List<Map<String, Object>> twoLineList = StepPartTaskDao.selStepStatisticsInLastCircle(twoMap);
		if (twoLineList.size() > 0) {
			// 不管了，存在该干线上周期数据就不生成
		}else{
			// 生成统计数据
			twoMap.put("task_type", 2);// 统计表中统计类型1.一干2.二干3.地标
			int two = StepPartTaskDao.intoStepStatistics(twoMap);// 插入统计表
			System.out.println("二干插入统计表数:"+two);
			int twoType = StepPartTaskDao.intoStepStatisticsType(twoMap);// 插入统计表关联任务
			System.out.println("二干插入统计表类型数:"+twoType);
		}

		// 获取地标数组上一个周期开始日期和结束日期
		Map<String, Object> landMarkMap = new HashMap<String, Object>();
		int[] LandMarkLineMonths = StepPartTaskUtil.landMarkGroundline;
		Map<String, Object> LandMarkLineMap = DateUtil.getLastStartMOnth(LandMarkLineMonths, currentMonth, 6);
		String landMarkBeginMonth = LandMarkLineMap.get("beginMonth").toString();// 获取地标上周期开始月的1号
		String landMarkEndMonth = LandMarkLineMap.get("endMonth").toString();// 获取结束时间
		landMarkMap.put("beginMonth", landMarkBeginMonth);
		landMarkMap.put("endMonth", landMarkEndMonth);
		landMarkMap.put("circle", 6);
		List<Map<String, Object>> landMarkLineList = StepPartTaskDao.selStepStatisticsInLastCircle(landMarkMap);
		if(landMarkLineList.size() > 0) {
			// 不管了，存在该干线上周期数据就不生成
		}else{
			// 生成统计数据
			landMarkMap.put("task_type", 3);// 统计表中统计类型1.一干2.二干3.地标
			int land = StepPartTaskDao.intoStepStatistics(landMarkMap);// 插入统计表
			System.out.println("地标插入统计表数:"+land);
			int landType =StepPartTaskDao.intoStepStatisticsType(landMarkMap);// 插入统计表关联任务
			System.out.println("地标插入统计类型表数:"+landType);
		}

	}
	
	private void createCurrentDate(){
		/**
		 * 1.获取当前周期已经一干二干地标的开始周期、结束周期
		 * 2.删除当前周期一干、二干、地标统计数据
		 * 3.生成当前周期数据
		 */
		int currentMonth = DateUtil.getCurrentMonth();//获取当前月
		//获取一干数组的当前开始日期与结束日期
		int[] OneLineMonths = StepPartTaskUtil.oneLevelGroundline;//一干月份数组
		int oneBeginMonth= DateUtil.getBeginMOnth(OneLineMonths, currentMonth);//根据当前月份,和当前月份数组获取开始月份
	    String oneCircleBeginMonth=DateUtil.getFirstDayOfMonth(oneBeginMonth);//获取一干开始月的1号
	    String oneCircleEndMonth=DateUtil.getTimeByCycle(oneBeginMonth,2);//获取结束时间
	    //删除一干当前周期统计数据，统计表和错误统计表
	    Map<String, Object> oneMap = new HashMap<String, Object>();
	    oneMap.put("beginMonth", oneCircleBeginMonth);
		oneMap.put("endMonth", oneCircleEndMonth);
		oneMap.put("circle", 2);
		int delOneType = StepPartTaskDao.delStepStatisticsType(oneMap);
		System.out.println("删除一干步巡错误统计数据表"+delOneType+"个");
		
		int delOne = StepPartTaskDao.delTaskStatistics(oneMap);
		System.out.println("删除一干步巡统计数据表"+delOne+"个");
		
		oneMap.put("task_type", 1);// 统计表中统计类型1.一干2.二干3.地标
		int one =  StepPartTaskDao.intoStepStatistics(oneMap);// 插入统计表
		System.out.println("一干插入统计表数:"+one);
		int oneType = StepPartTaskDao.intoStepStatisticsType(oneMap);// 插入统计表关联任务
		System.out.println("一干插入统计表类型数:"+oneType);
	    
		//获取二干数组的当前开始日期与结束日期
		int[] TwoLineMonths = StepPartTaskUtil.twoLevelGroundline;//二干月份数组
		int twoBeginMonth= DateUtil.getBeginMOnth(TwoLineMonths, currentMonth);//根据当前月份,和当前月份数组获取开始月份
		String twoCircleBeginMonth=DateUtil.getFirstDayOfMonth(twoBeginMonth);//获取二干开始月的1号
		String twoCircleEndMonth=DateUtil.getTimeByCycle(twoBeginMonth,3);//获取结束时间
		Map<String, Object> twoMap = new HashMap<String, Object>();
		twoMap.put("beginMonth", twoCircleBeginMonth);
		twoMap.put("endMonth", twoCircleEndMonth);
		twoMap.put("circle", 3);
		int delTwoType = StepPartTaskDao.delStepStatisticsType(twoMap);
		System.out.println("删除一干步巡错误统计数据表"+delTwoType+"个");
		
		int delTwo = StepPartTaskDao.delTaskStatistics(twoMap);
		System.out.println("删除一干步巡统计数据表"+delTwo+"个");
		
		twoMap.put("task_type", 2);// 统计表中统计类型1.一干2.二干3.地标
		int two = StepPartTaskDao.intoStepStatistics(twoMap);// 插入统计表
		System.out.println("二干插入统计表数:"+two);
		int twoType = StepPartTaskDao.intoStepStatisticsType(twoMap);// 插入统计表关联任务
		System.out.println("二干插入统计表类型数:"+twoType);
		
		
	    //获取地标数组开始日期和结束日期
		int[] LandMarkLineMonths = StepPartTaskUtil.landMarkGroundline;//地标月份数组
		int landMarkBeginMonth= DateUtil.getBeginMOnth(LandMarkLineMonths, currentMonth);//根据当前月份,和当前月份数组获取开始月份
		String landMarkCircleBeginMonth=DateUtil.getFirstDayOfMonth(landMarkBeginMonth);//获取二干开始月的1号
		String landMarkCircleEndMonth=DateUtil.getTimeByCycle(landMarkBeginMonth,6);//获取结束时间
		Map<String, Object> landMarkMap = new HashMap<String, Object>();
		landMarkMap.put("beginMonth", landMarkCircleBeginMonth);
		landMarkMap.put("endMonth", landMarkCircleEndMonth);
		landMarkMap.put("circle", 6);
		int delLandMarkType = StepPartTaskDao.delStepStatisticsType(landMarkMap);
		System.out.println("删除一干步巡错误统计数据表"+delLandMarkType+"个");
		
		int delLandMark = StepPartTaskDao.delTaskStatistics(landMarkMap);
		System.out.println("删除一干步巡统计数据表"+delLandMark+"个");
		
		landMarkMap.put("task_type", 3);// 统计表中统计类型1.一干2.二干3.地标
		int land = StepPartTaskDao.intoStepStatistics(landMarkMap);// 插入统计表
		System.out.println("地标插入统计表数:"+land);
		int landType =StepPartTaskDao.intoStepStatisticsType(landMarkMap);// 插入统计表关联任务
		System.out.println("地标插入统计类型表数:"+landType);
		
	}
	
}
