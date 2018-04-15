package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface TroubleReportDao {
	List<Map> query(Query query);

	List<Map> queryExl(Map map);
}
