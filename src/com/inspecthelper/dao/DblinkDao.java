package com.inspecthelper.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface DblinkDao {
	
	public List<Map<String,Object>> getDbLinkByAreaId(HashMap<String,Object> hm); 
	
	public List<Map<String,Object>> getDbLinkByStaffNo(HashMap<String,Object> hm) ;
	
	public List<Map<String,Object>> getDblinkContainsPro(HashMap<String,Object> hm) ;

}
