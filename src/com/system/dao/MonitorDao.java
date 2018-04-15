package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public interface MonitorDao {
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
    
    public void clearLogs();

}
