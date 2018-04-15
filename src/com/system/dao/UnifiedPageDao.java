package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface UnifiedPageDao {

	public List<Map> query(Query query);
	
	public List getRoles(String staffId);
	
	public List getSofts(String staffId);
	
	public Map<String, Object> getOutSitePermissions(String staffId);
	
	public void insert(Map map);
	
	public Map getStaff(String staffId);
	
	public void delete(Map map);
	
	public void update(Map map);
	
}
