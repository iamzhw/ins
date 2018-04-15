package com.cableCheck.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cableCheck.service.SyncOssDataTaskService;
import com.util.sendMessage.PropertiesUtil;

/**
 * @desc 同步OSS数据的定时器
 * @author wangxiangyu
 *
 */
@Component
public class SyncOssDataTask {

	@Resource
	private SyncOssDataTaskService syncOssDataTaskService;
	
	/**
	 * 同步OSS数据库中不含分光器且小于24芯（不含24芯）的光分纤箱
	 * 每日凌晨3点
	 * @author wangxiangyu  
	 * 该数据源的存储过程没了 暂时不考虑
	 */
	/*@Scheduled(cron = "0 17 16 * * ?")
	public void syncEqpNoOBDLess24() {
		if (PropertiesUtil.getPropertyBoolean("quartzSwitch", true)) {
			syncOssDataTaskService.syncEqpNoOBDLess24();
		}
	}*/
}
