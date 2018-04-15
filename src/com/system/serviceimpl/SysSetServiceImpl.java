package com.system.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.dao.SysSetDao;
import com.system.service.SysSetService;

@Service
@Transactional(rollbackFor = { Exception.class }) 
public class SysSetServiceImpl implements SysSetService{
	
	@Resource
	private SysSetDao sysSetDao;

	@Override
	public List<Map<String, Object>> getSysSet() {
		return sysSetDao.getSysSet();
	}

}
