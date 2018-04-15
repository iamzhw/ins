package com.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface LoginDao {
	public List<Map> login(Map map);

	public List<Map> getGns(Map map);
	public List<Map> singleLogin(Map map);

}
