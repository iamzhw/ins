package com.cableCheck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.page.Query;

public interface TeamManagerDao {
	/**
	 * 任务管理-查询
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> listTeam(Query query);
	
	public List<Map<String,Object>> listUserRole(Query query);
	
	public List<Map<String,Object>> exportTeam(Query query);
	
	public List<Map<String,Object>> exportTeamUser(Query query);
	
	public List<Map> queryStaff(Query query);
	
	public List<Map> queryImportLog(Query query);
	
	public void updateTeam(Map map);
	
	public int selectRelationCount(Map map);
	
	public void updateCompany(Map map);
	
	public int selectStaffCount(Map map);
	public void updateShenhe(Map map);
	
	public void resetTeamStaff(Map map);
	public void updateTeamStaff(Map map);
	public void addShenhe(Map map);
	
	public void updateDoudi(Map map);
	public void addDoudi(Map map);
	
	public int isExistCableRole(Map map);
	public void insertCableRole(Map map);
	
	public List<Map> queryRolePermission(Map map);
	
	/**
	 * 批量更新装维班组和人员的areaid
	 * @param map
	 */
	public void calBatchUpdateBanzu(Map map);
	public void calBatchUpdateZwAreaId(Map map);
}
