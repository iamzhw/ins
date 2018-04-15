package com.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface GridDao {

	List<Map> query(Query query);


	List<Map> queryAduits(Query query);


	void delAudits(String grid_id);


	void saveAduits(Map map);


	List getAduits(String grid_id);

}
