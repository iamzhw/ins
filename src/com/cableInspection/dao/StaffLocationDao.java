package com.cableInspection.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public interface StaffLocationDao {
	List<Map> queryStaff(Map m);
	List<Map> queryEqp(Map m);
}
