package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@SuppressWarnings("all")
@Repository
public interface PersonalWorkDao {
	public List<Map> query(Query query);
	
	public List<Map> queryAreaPoints(Map m);
}
