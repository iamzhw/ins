package com.cableInspection.serviceimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cableInspection.dao.TaskStateDao;
import com.cableInspection.service.TaskStateService;
import com.util.StringUtil;

/**
 * 任务状态service
 * 
 * @author fengjl
 * 
 */
@Service
public class TaskStateServiceImpl implements TaskStateService {

	@Resource
	private TaskStateDao taskStateDao;

	@Override
	public void changeTaskState() {
		// 当前时间
		Calendar cal = Calendar.getInstance();
		Date endDate = cal.getTime();
		cal.set(Calendar.MINUTE, -30);
		Date startDate = cal.getTime();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate",
				StringUtil.dateToString(startDate, "yyyy-MM-dd HH:mm") + ":00");
		params.put("endDate", StringUtil.dateToString(endDate, "yyyy-MM-dd HH:mm")
				+ ":00");
		taskStateDao.updateFinish(params);
		taskStateDao.updateRun(params);
	}
}
