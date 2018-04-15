package com.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.dao.MonitorDao;
import com.system.service.MonitorService;

@Service
@SuppressWarnings("all")
public class MonitorServiceImpl implements MonitorService {
	
	@Autowired
	MonitorDao monitorDao;
	
	@Override
	public List<Map<String, String>> getServerConfig(String hostName) {
		return monitorDao.getServerConfig(hostName);
	}
	
	@Override
	public List<Map<String, String>> getDBStatus(){
		return monitorDao.getDBStatus();
	}

	@Override
	public void clearLogs() {
		monitorDao.clearLogs();
	}

	
	
}
