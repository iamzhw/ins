package com.cableInspection.task;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cableInspection.service.ArrivalService;
import com.cableInspection.service.CableService;
import com.cableInspection.service.FtpResolveService;
import com.cableInspection.service.SynchronizePointService;
import com.cableInspection.service.TaskCreateService;
import com.cableInspection.service.TaskStateService;
import com.cableInspection.serviceimpl.FtpResolveServiceImpl;
import com.util.sendMessage.PropertiesUtil;

/**
 * 定时任务
 * 
 * @author fengjl
 * 定时执行顺序
 * 1、每天先统计每个人的工作量 addKeyPoints ,由于ODSO时间安排，提前到每天晚上11点
 * 2、同步OSS设备点数据到系统
 * 3、定时生成日常巡检到位率静态表（与2平级）
 * 4、对于片区计划，每月月初更新计划片区内的关键点
 * 5、同步人员和网格，时间：晚上22点（智能网格13点开始提供）
 * 6、同步设备所在网格，时间：晚上22点50分（等待“同步人员和网格”完成对网格的同步）
 * 
 */
@Component
public class CableTask {

	@Resource
	private ArrivalService arrivalService;

	@Resource
	private TaskCreateService taskCreateService;

	@Resource
	private TaskStateService taskStateService;

	@Resource
	private SynchronizePointService synchronizePointService;
	
	@Resource
	private FtpResolveService ftpResolveService;
	
	@Resource
	private CableService cableService;
	
	private static Logger logger=Logger.getLogger(FtpResolveServiceImpl.class);

	/**
	 * 看护任务坐标验证task: 每天凌晨验证昨日上传的看护坐标 与看护点在误差范围内则置为1
	 */
	@Scheduled(cron = "0 30 0 * * ?")
	public void keepArrivalCheck() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			arrivalService.keepArrivalCheckTask();
		}
	}

	/**"0 20 0 ? * MON"
	 * 生成任务：每周一凌晨执行（日计划和周计划）
	 */
	@Scheduled(cron = "0 40 3 ? * MON")
	public void createTask() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			taskCreateService.createTask(1);
		}
	}

	/**
	 * 生成任务：每月1号凌晨执行（月计划）
	 */
	@Scheduled(cron = "0 20 3 1 * ?")
	public void createPointTask2() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//		System.out.println("执行");
			taskCreateService.createTask(2);
		}
			//"0 20 0 1 * ?"
	}

	/**
	 * 改变任务状态：每30分钟跑一次
	 * 12月7日关闭，
	 */
	@Scheduled(cron = "0 0/30 * * * *")
	public void changeTaskState() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			//taskStateService.changeTaskState();
		}
	}

	
	/**
	 * 定时生成到位率写入静态表
	 */
	@Scheduled(cron = "30 35 0 * * ?")
	public void addNewArrivalRates() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//			System.out.println("开始生成");
			arrivalService.addNewArrivalRates();
//			System.out.println("结束");
		}	
	}
	
	/**
	 * 同步OSS设备点数据到系统
	 * "0 30 0 1 9 ?"
	 */
	@Scheduled(cron = "0 23 0 * * ?")
	public void synchronizePoint() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			synchronizePointService.synchronizeEquipmentInfo();
		}
	}
	
	/**
	 * 同步人员和网格
	 * 由于智能网格在中午13点生成数据文件，故把此定时任务推迟到晚上22点20分
	 */
	@Scheduled(cron = "30 0 22 * * *")
	public void addNewStaffAndDept() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//			System.out.println("staff_start");
			ftpResolveService.solveFTP();
//			System.out.println("starff_completed");
		}	
	}
	
	/**
	 * 同步光交所在网格
	 * 由于统一网元库在7点30生成数据文件，故把此定时任务推迟到晚上22点50分
	 */
	@Scheduled(cron = "30 50 22 * * *")
	public void addNewPhyEqp() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println("eqp_start");
			ftpResolveService.solveDatFile();
			System.out.println("eqp_completed");
		}	
	}
	
	/**
	 * 
	 * @Function: com.cableInspection.task.CableTask.addKeyPoints
	 * @Description:统计每个人的工作量
	 *
	 *
	 * @date:2016-9-21 下午2:10:12
	 *
	 * @Modification History:
	 * @date:2016-9-21     @author:Administrator     create
	 */
	@Scheduled(cron = "0 50 23 * * ?")
	public void addKeyPoints() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//			System.out.println("开始生成");
			arrivalService.addKeyPoints();
//			System.out.println("结束");
		}	
	}
	
	/**
	 * 更新计划的关键点
	 * "0 10 0 28 * ?"
	 */
	@Scheduled(cron = "0 10 1 1 * ?")
	public void updateCablePoint() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//		System.out.println("**start**");
//		long cur = System.currentTimeMillis();
		cableService.updateCablePoint();
		}
//		long end = System.currentTimeMillis();
//		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//		System.out.println("**end**用时:"+sdf.format(end-cur));
	}
	
	
	/**"0 0 2 1 * ?"
	 * 生成分公司报表：每月1号凌晨执行（月计划）
	 */
	@Scheduled(cron = "0 0 2 1 * ?")
	public void saveArrivalRateTable() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//		System.out.println("执行");
			arrivalService.saveArrivalRateTable();
//			System.out.println("结束");
		}
			//"0 20 0 1 * ?"
	}
	
	/**"0 10 22 * * ?"
	 * 生成集约化数据：每天晚上10点执行
	 */
	@Scheduled(cron = "0 10 22 * * ?")
	public void saveLxxjCity() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//		System.out.println("执行");
			arrivalService.saveLxxjCity();
//			System.out.println("结束");
		}
	}
	
	/**"0 10 22 * * ?"
	 * 推送线路工单给集约化：每天晚上10点执行
	 */
	@Scheduled(cron = "0 10 22 * * ?")
	public void callWfworkitemflowServiceForTask() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//		System.out.println("执行");
			arrivalService.callWfworkitemflowServiceForTask("0");
//			System.out.println("结束");
		}
	}
	
	/**"0 10 22 * * ?"
	 * 推送缆线巡检到位率给集约化的承包指标
	 */
	@Scheduled(cron = "0 40 21 * * ?")
	public void personKPI() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//		System.out.println("执行");
			ftpResolveService.personKPI();
//			System.out.println("结束");
		}
	}
}
