package com.cableCheck.task;

import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.cableCheck.service.QuartzJobService;
import com.linePatrol.util.DateUtil;
import com.util.StringUtil;
import com.util.sendMessage.PropertiesUtil;

/**
 * 光网助手定时任务
 * 
 * @author ningruofan
 * 
 */
@Component
public class CableCheckTask {

	@Resource
	private QuartzJobService quartzJob;
	
	//private static Logger logger=Logger.getLogger(FtpResolveServiceImpl.class);

//	/**
//	 * 同步十三个地市所有的变动端子
//	 */
//	@Scheduled(cron = "0 10 1 * * ?")
//	public void syncOSSChangePorts() {
//		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
//		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
//			quartzJob.syncOSSChangePorts();
//		}
//	}
	
	/**
	 * 同步十三个地市末级分光器
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	public void syncOSSChangePorts() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用末级分光器任务");
			quartzJob.syncOSSOBD();
		}
	}
	/**
	 * 同步南京所有的变动端子
	 */
	@Scheduled(cron = "0 32 1 * * ?")
	public void syncOSSChangePortsNJ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用南京动态端子任务");

			quartzJob.syncOSSChangePortsNJ();
		}
	}
	
	/**
	 * 同步镇江所有的变动端子
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	public void syncOSSChangePortsZJ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用镇江动态端子任务");

			quartzJob.syncOSSChangePortsZJ();
		}
	}
	/**
	 * 同步无锡所有的变动端子
	 */
	@Scheduled(cron = "0 30 2 * * ?")
	public void syncOSSChangePortsWX() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用无锡动态端子任务");
			quartzJob.syncOSSChangePortsWX();
		}
	}
	/**
	 * 同步南通所有的变动端子
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	public void syncOSSChangePortsNT() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用南通动态端子任务");
			quartzJob.syncOSSChangePortsNT();
		}
	}
	/**
	 * 同步扬州所有的变动端子
	 */
	@Scheduled(cron = "0 28 3 * * ?")
	public void syncOSSChangePortsYZ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用扬州动态端子任务");
			quartzJob.syncOSSChangePortsYZ();
		}
	}
	/**
	 * 同步盐城所有的变动端子
	 */
	@Scheduled(cron = "0 0 4 * * ?")
	public void syncOSSChangePortsYC() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用盐城动态端子任务");
			quartzJob.syncOSSChangePortsYC();
		}
	}
	/**
	 * 同步徐州所有的变动端子
	 */
	@Scheduled(cron = "0 28 4 * * ?")
	public void syncOSSChangePortsXZ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用徐州动态端子任务");
			quartzJob.syncOSSChangePortsXZ();
		}
	}
	/**
	 * 同步淮安所有的变动端子
	 */
	@Scheduled(cron = "0 0 5 * * ?")
	public void syncOSSChangePortsHA() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用淮安动态端子任务");
			quartzJob.syncOSSChangePortsHA();
		}
	}
	/**
	 * 同步连云港所有的变动端子
	 */
	@Scheduled(cron = "0 24 5 * * ?")
	public void syncOSSChangePortsLYG() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用连云港动态端子任务");
			quartzJob.syncOSSChangePortsLYG();
		}
	}
	/**
	 * 同步常州所有的变动端子
	 */
	@Scheduled(cron = "0 0 6 * * ?")
	public void syncOSSChangePortsCZ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用常州动态端子任务");
			quartzJob.syncOSSChangePortsCZ();
		}
	}
	/**
	 * 同步泰州所有的变动端子
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void syncOSSChangePortsTZ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用泰州动态端子任务");
			quartzJob.syncOSSChangePortsTZ();
		}
	}
	/**
	 * 同步宿迁所有的变动端子
	 */
	@Scheduled(cron = "0 40 0 * * ?")
	public void syncOSSChangePortsSQ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用宿迁动态端子任务");
			quartzJob.syncOSSChangePortsSQ();
		}
	}
	/**
	 * 同步苏州所有的变动端子
	 */
	@Scheduled(cron = "0 20 0 * * ?")
	public void syncOSSChangePortsSZ() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用苏州动态端子任务");
			quartzJob.syncOSSChangePortsSZ();
		}
	}
	
	
	/**
	 * 同步OSS中设备点数据到系统
	 */
/*	@Scheduled(cron = "0 12 2 * * ?")
	public void synchronizePoint() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", false)) {
			quartzJob.synchronizeEquipmentInfo();
		}
	}*/
	
	/**
	 * 新的同步OSS中设备数据到系统
	 */
	@Scheduled(cron = "0 19 22 * * ?")
	public void synchronizeEquipment() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			quartzJob.synchronizeEquipment();
		}
	}
	
	
	/**
	 * 同步电子档案库信息到设备表
	 */
	@Scheduled(cron = "0 00 20 * * ?")
	public void synchronizeElectronArchives() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用电子档案库任务");
			quartzJob.synchronizeElectronArchives();
		}
	}
	
	/**
	 * 同步承包人到设备表
	 */
	@Scheduled(cron = "30 0 22 * * ?")
	public void synchronizeEqpContract() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":同步承包人");
			quartzJob.synchronizeEqpContract();
		}
	}
	
	/**
	 * 同步网络级别到设备表
	 */
	@Scheduled(cron = "30 5 22 * * ?")
	public void synchronizeEqpWljb() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":同步网络级别");
			quartzJob.synchronizeEqpWljb();
		}
	}
	
	/**
	 * 同步OSS更纤率数据
	 */
	@Scheduled(cron = "0 00 21 * * ?")
	public void synchronizeOSSGXL() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用OSS更纤率任务");
			quartzJob.synchronizeOSSGXL();
		}
	}
	
	/**
	 * 同步无资源响应单
	 */
	@Scheduled(cron = "0 30 21 * * ?")
	public void synchronizeNoRes() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用无资源任务");
			quartzJob.synchronizeNoRes();
		}
	}
	
	/**
	 * 月度资源报表

	 */
	@Scheduled(cron = "0 15 1 1 * ?")
	public void syncMONTH() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用月度资源报表任务");
			quartzJob.syncMONTH();
		}
	}
	/**
	 * 全景视图

	 */
	@Scheduled(cron = "0 20 1 1 * ?")
	public void syncQJST() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用全景视图任务");
			quartzJob.syncQJST();
		}
	}
	
	/**
	 * odso

	 */
	@Scheduled(cron = "0 5 5 * * ?")
	public void syncODSO() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用ODSO任务");
			quartzJob.syncODSO();
		}
	}
	
	/**
	 * 定时资源准确率(每天晚上23点)

	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void syncResRat() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用定时资源准确率任务");
			quartzJob.syncResRat();
		}
	}
	
	/**
	 * 定时综调13地市数据(每天晚上23点)

	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void syncCvs() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用定时综调13地市数据");
			quartzJob.syncCvs();
		}
	}
	
	/**
	 * 定时Iom13地市数据(每天晚上23点)

	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void syncIom() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":调用定时Iom13地市数据");
			quartzJob.syncIom();
		}
	}
	
	
	/**
	 * 定时超时预警，催促执行(每天上午9点执行)
	 */
	@Scheduled(cron = "0 0 9 * * ?")
	public void reviewTaskToStaff() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":超时预警，催促执行");
			quartzJob.reviewTaskToStaff();
		}else{
			System.out.println("定时任务开关是false");
		}
	}
	
	/**
	 * 定时计算工单数，便于智检查工单占比的页面展示
	 * 每天凌晨计算前一天的数据
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void calOrderNum() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":定时计算工单数");
			
//			quartzJob.calOrderNum();//计算现场检查工单数
			quartzJob.calOrderChangeNum();//计算更纤率
			
			quartzJob.calCheckError();//计算数据错误责任判定报表
			quartzJob.calTeamOrder();//计算班组归属工单查询
			
			quartzJob.calGridOrder();//计算网格归属工单
		}else{
			System.out.println("定时任务开关是false");
		}
	}
	/**
	 * 定时统计每天变动端子数量短信通知
	 */
	@Scheduled(cron = "30 57 7 * * ?")
	public void queryAreaPortNum() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			System.out.println(DateUtil.getDateAndTime()+":定时统计每天变动端子数量短信通知");	
			quartzJob.queryAreaPortNum();//计算现场检查工单数
		}else{
			System.out.println("定时任务开关是false");
		}
	}
	
	/**
	 * 定时生成ftp目录
	 */
	//etl平台搭建好后不再在ftp目录下动态生成目录
	/*@Scheduled(cron = "50 46 4 * * ?")
	public void makeDirectory() {
		// 增加定时任务开关(代码保护,未配置时默认为开关打开)
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", false)) {
			System.out.println(DateUtil.getDateAndTime()+":ftp目录自动生成");	
			quartzJob.queryFtpDir();
			System.out.println(DateUtil.getDateAndTime()+":ftp目录自动生成完毕");
		}else{
			System.out.println("定时任务开关是false");
		}
	}*/
	
	
	
}
