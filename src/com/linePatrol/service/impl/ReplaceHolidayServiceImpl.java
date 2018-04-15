/**
 * @Description: TODO
 * @date 2015-2-6
 * @param
 */
package com.linePatrol.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linePatrol.dao.ReplaceHolidayDao;
import com.linePatrol.service.ReplaceHolidayService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class ReplaceHolidayServiceImpl implements ReplaceHolidayService {

	@Resource
	private ReplaceHolidayDao replaceHolidayDao;

	@Override
	public List<Map<String, Object>> query(Map<String, Object> para) {

		List<Map<String, Object>> olists = replaceHolidayDao.query(para);

		return olists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.replaceHolidayService#save(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> replaceHolidaySave(Map<String, Object> para) {
		// TODO Auto-generated method stub
		replaceHolidayDao.replaceHolidaySave(para);

		// 查询此人本月所有休假代巡情况
		// {replace_id=, =2015-04-21, user_id=, holiday_type=0}
		String[] hdArray = para.get("holiday_date").toString().split("-");
		String holiday_date = hdArray[0] + hdArray[1];
		para.put("holiday_date", holiday_date);
		List<Map<String, Object>> allHoliday = replaceHolidayDao
				.getAllHoliday(para);

		return allHoliday;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.replaceHolidayService#update(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> replaceHolidayUpdate(
			Map<String, Object> para) {
		// TODO Auto-generated method stub
		replaceHolidayDao.replaceHolidayUpdate(para);

		// 查询此人本月所有休假代巡情况
		String[] hdArray = para.get("holiday_date").toString().split("-");
		String holiday_date = hdArray[0] + hdArray[1];
		para.put("holiday_date", holiday_date);
		List<Map<String, Object>> allHoliday = replaceHolidayDao
				.getAllHoliday(para);
		return allHoliday;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.linePatrol.service.replaceHolidayService#delete(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> replaceHolidayDelete(String holiday_id) {
		// TODO Auto-generated method stub
		// 先查出此人本月休假情况
		List<Map<String, Object>> allHoliday = replaceHolidayDao
				.getAllHolidayByHid(holiday_id);
		replaceHolidayDao.replaceHolidayDelete(holiday_id);
		return allHoliday;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.linePatrol.service.replaceHolidayService#editUI(java.lang.String)
	 */
	@Override
	public Map<String, Object> findById(String id) {
		// TODO Auto-generated method stub
		return replaceHolidayDao.findById(id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		// TODO Auto-generated method stub
		return replaceHolidayDao.findAll();
	}

	@Override
	public List<Map<String, Object>> showDetailInfo(Map<String, Object> para) {
		return replaceHolidayDao.showDetailInfo(para);
	}

}
