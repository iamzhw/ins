package icom.cableCheck.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import icom.cableCheck.dao.SensitiveLogDao;
import icom.cableCheck.service.SensitiveLogService;

@Service
@SuppressWarnings("all")
public class SensitiveLogServiceImpl implements SensitiveLogService{

	@Resource
	private SensitiveLogDao sensitiveLogDao;
	
	@Override
	public void insertSensitiveLog(Map map) {
		sensitiveLogDao.insertSensitiveLog(map);
	}
}
