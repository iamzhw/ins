package com.roomInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;
@SuppressWarnings("all")
@Repository
public interface CompanyDao {
	
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
	
	public Map getCompany(int id);
	
	/**
	 * @Title: getSonAreaListByStaffId
	 * @Description:
	 * @param: @param valueOf
	 * @param: @return
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getSonAreaListByCompanyId(int companyId);

}
