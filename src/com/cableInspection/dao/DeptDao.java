package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface DeptDao {

public List<Map> query(Query query);
	
	/**
	 * @Title: save
	 * @Description:
	 * @param: @param map
	 * @return: void
	 * @throws
	 */
	public void insert(Map map);
	
	/**
	 * @Title: update
	 * @Description:
	 * @param: @param map
	 * @return: void
	 * @throws
	 */
	public void update(Map map);

	public void delete(int id);
	
	public Map getDeptInfo(int id);
	
	/**
	 * @Title: getSonAreaListByStaffId
	 * @Description:
	 * @param: @param valueOf
	 * @param: @return
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getSonAreaListByClassId(int classId);

	public List<Map> getStaffs(Query query);

	public void deleteStaffByDeptId(String dept_id);

	public void insertStaffByDeptId(Map map);
	
	public int checkDeptExist(String s);
	
	public String getAreaIdByName(String s);
	
	public String getMinAreaIdByParentName(String s);
	
	public List<Map> getDeptSelection(Map map);
	
	public List<Map> getDeptSelectionForCable(Map m);
	
	public List<Map> getDeptByAreaId(Map map);

}
