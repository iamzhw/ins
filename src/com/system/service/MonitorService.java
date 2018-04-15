package com.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public interface MonitorService {
	
	/**
	 * 获取服务器配置信息
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getServerConfig(String hostName);
	
	/**
	 * 获取爱运维数据库表空间
	 * @return
	 */
	public List<Map<String, String>> getDBStatus();
	
	/**
	 * 清理日志表
	 */
	public void clearLogs();
	
	
	

}
