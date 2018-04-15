package com.cableCheck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.page.Query;

public interface TeamUserDao {
	/**
	 * 任务管理-查询
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> listTeamUser(Query query);
}
