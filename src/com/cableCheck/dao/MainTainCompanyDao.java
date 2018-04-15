package com.cableCheck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.page.Query;

public interface MainTainCompanyDao {
	/**
	 * 任务管理-查询
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> listCompany(Query query);
	
	
	public List<Map> selectCompany(Map map);
	
	public void addCompany(Map map);
	
	public void updateCompany(Map map);
	
	public void deleteCompany(Map map);
	
	public void saveRelation(Map map);
	
	public void updateTreeStatus(Map map);
	
	public String selectTeamName(Map map);
	
	public void updateTreeStaffStatus(Map map);
	
	public void deleteRelation(Map map);
	
	public List<Map> listGridOrder(Query query);
	
	public List<Map> getDeptTree(Map map);
	
	public List<Map> getUserTree(Map map);

	public int isExistDept(Map map);
	
	public int lookStatus(Map map);
	
	public List<Map> selectArea(Map map);
}
