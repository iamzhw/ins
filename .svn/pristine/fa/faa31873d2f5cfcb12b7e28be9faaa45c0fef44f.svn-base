package com.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface RoleDao {

	/**
	 * @Title: query
	 * @Description:
	 * @param: @param map
	 * @param: @param integer
	 * @param: @param integer2
	 * @param: @return
	 * @return: Page
	 * @throws
	 */
	public List<Map> query(Query query);

	/**
	 * @Title: proveUniqueness
	 * @Description:
	 * @param: @param map
	 * @param: @return
	 * @return: Integer
	 * @throws
	 */
	public Integer proveUniqueness(Map map);

	/**
	 * @Title: insert
	 * @Description:
	 * @param: @param map
	 * @return: void
	 * @throws
	 */
	public void insert(Map map);

	/**
	 * @Title: getRole
	 * @Description:
	 * @param: @param roleId
	 * @param: @return
	 * @return: Map
	 * @throws
	 */
	public Map getRole(String roleId);

	/**
	 * @Title: update
	 * @Description:
	 * @param: @param map
	 * @return: void
	 * @throws
	 */
	public void update(Map map);

	/**
	 * @Title: delete
	 * @Description:
	 * @param: @param map
	 * @return: void
	 * @throws
	 */
	public void delete(Map map);
	
	/**
	 * @Title: delete
	 * @Description:
	 * @param: @param map
	 * @return: void
	 * @throws
	 */
	public void deleteRole(Map map);
	
	/**
	 * @Title: getAllGns
	 * @Description:
	 * @param: @param hm
	 * @param: @return
	 * @return: List<HashMap>
	 * @throws
	 */
	public List<HashMap> getAllGns(Map hm);

	/**
	 * @Title: getResources
	 * @Description:
	 * @param: @param hm
	 * @param: @return
	 * @return: List<HashMap>
	 * @throws
	 */
	public List<HashMap> getResources(Map hm);

	/**
	 * @Title: delResource
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param roleId
	 * @return: void
	 * @throws
	 */
	public void delResource(String roleId);

	/**
	 * @Title: giveResource
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param roleId
	 * @param: @param string
	 * @return: void
	 * @throws
	 */
	public void giveResource(Map map);

	public List<Map<String, String>> getAllByStaffId(String staffId);

	public List<String> getRoleList(Map<String, Object> params);
	
	public List<Map> getRoleListForApk(String s);

}
