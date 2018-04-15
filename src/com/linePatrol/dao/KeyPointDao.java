package com.linePatrol.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.page.Query;

@Repository
public interface KeyPointDao {
	 List<Map<String,Object>> query(Map<String,Object> map);
}
