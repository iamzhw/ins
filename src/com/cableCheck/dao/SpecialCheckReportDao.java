package com.cableCheck.dao;

import java.util.List;
import java.util.Map;

import util.page.Query;

public interface SpecialCheckReportDao {

	List<Map<String, Object>> query(Query query);

	List<Map<String, Object>> queryDownByCity(Map<String, Object> paras);

	List<Map<String, Object>> queryByCity(Query query);

	List<Map<String, Object>> queryDown(Map<String, Object> paras);

	List<Map<String, Object>> queryZYByCity(Query query);

	List<Map<String, Object>> queryFTTHByCity(Query query);

	List<Map<String, Object>> queryIOMByCity(Query query);

	List<Map<String, Object>> queryKXByCity(Query query);

	List<Map<String, Object>> queryZY(Query query);

	List<Map<String, Object>> queryFTTH(Query query);

	List<Map<String, Object>> queryIOM(Query query);

	List<Map<String, Object>> queryKX(Query query);

	List<Map<String, Object>> queryCheckPortByCity(Query query);

	List<Map<String, Object>> queryPromblePortByCity(Query query);

	List<Map<String, Object>> queryRightPortByCity(Query query);

	List<Map<String, Object>> queryCheckPort(Query query);

	List<Map<String, Object>> queryPromblePort(Query query);

	List<Map<String, Object>> queryRightPort(Query query);

	List<Map<String, Object>> downCheckPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downPromblePortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downRightPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downCheckPort(Map<String, Object> paras);

	List<Map<String, Object>> downPromblePort(Map<String, Object> paras);

	List<Map<String, Object>> downRightPort(Map<String, Object> paras);

	List<Map<String, Object>> downZYByCity(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHByCity(Map<String, Object> paras);

	List<Map<String, Object>> downIOMEqpByCity(Map<String, Object> paras);

	List<Map<String, Object>> downKXByCity(Map<String, Object> paras);

	List<Map<String, Object>> downZY(Map<String, Object> paras);

	List<Map<String, Object>> downFTTH(Map<String, Object> paras);

	List<Map<String, Object>> downIOM(Map<String, Object> paras);

	List<Map<String, Object>> downKX(Map<String, Object> paras);

	List<Map<String, Object>> queryZYW(Query query);

	List<Map<String, Object>> queryZYALL(Query query);

	List<Map<String, Object>> queryFTTHW(Query query);

	List<Map<String, Object>> queryIOMW(Query query);

	List<Map<String, Object>> queryKXW(Query query);

	List<Map<String, Object>> queryKXW1(Query query);

	List<Map<String, Object>> queryKXW2(Query query);

	List<Map<String, Object>> queryKXW3(Query query);
	List<Map<String, Object>> queryKXW4(Query query);
	List<Map<String, Object>> queryKXW5(Query query);
	List<Map<String, Object>> queryKXW6(Query query);
	List<Map<String, Object>> queryKXW7(Query query);

	List<Map<String, Object>> queryZYWByCity(Query query);

	List<Map<String, Object>> queryZYALLByCity(Query query);

	List<Map<String, Object>> queryFTTHWByCity(Query query);

	List<Map<String, Object>> queryIOMWByCity(Query query);

	List<Map<String, Object>> queryIOMALLByCity(Query query);

	List<Map<String, Object>> queryKXWByCity(Query query);

	List<Map<String, Object>> queryKXW1ByCity(Query query);
	List<Map<String, Object>> queryKXW2ByCity(Query query);
	List<Map<String, Object>> queryKXW3ByCity(Query query);
	List<Map<String, Object>> queryKXW4ByCity(Query query);
	List<Map<String, Object>> queryKXW5ByCity(Query query);
	List<Map<String, Object>> queryKXW6ByCity(Query query);
	List<Map<String, Object>> queryKXW7ByCity(Query query);

	List<Map<String, Object>> queryKXALLByCity(Query query);
	
	
	
	
	
	
	
	
	List<Map<String, Object>> downZYW(Map<String, Object> paras);

	List<Map<String, Object>> downZYALL(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHW(Map<String, Object> paras);

	List<Map<String, Object>> downIOMW(Map<String, Object> paras);

	List<Map<String, Object>> downKXW(Map<String, Object> paras);

	List<Map<String, Object>> downKXW1(Map<String, Object> paras);

	List<Map<String, Object>> downKXW2(Map<String, Object> paras);

	List<Map<String, Object>> downKXW3(Map<String, Object> paras);
	List<Map<String, Object>> downKXW4(Map<String, Object> paras);
	List<Map<String, Object>> downKXW5(Map<String, Object> paras);
	List<Map<String, Object>> downKXW6(Map<String, Object> paras);
	List<Map<String, Object>> downKXW7(Map<String, Object> paras);

	List<Map<String, Object>> downZYWByCity(Map<String, Object> paras);

	List<Map<String, Object>> downZYALLByCity(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHWByCity(Map<String, Object> paras);

	List<Map<String, Object>> downIOMWByCity(Map<String, Object> paras);

	List<Map<String, Object>> downIOMALLByCity(Map<String, Object> paras);

	List<Map<String, Object>> downKXWByCity(Map<String, Object> paras);

	List<Map<String, Object>> downKXW1ByCity(Map<String, Object> paras);
	List<Map<String, Object>> downKXW2ByCity(Map<String, Object> paras);
	List<Map<String, Object>> downKXW3ByCity(Map<String, Object> paras);
	List<Map<String, Object>> downKXW4ByCity(Map<String, Object> paras);
	List<Map<String, Object>> downKXW5ByCity(Map<String, Object> paras);
	List<Map<String, Object>> downKXW6ByCity(Map<String, Object> paras);
	List<Map<String, Object>> downKXW7ByCity(Map<String, Object> paras);

	List<Map<String, Object>> downKXALLByCity(Map<String, Object> paras);

	List<Map<String, Object>> queryZyPortByCity(Query query);

	List<Map<String, Object>> queryZyWrongPortByCity(Query query);

	List<Map<String, Object>> queryFTTHPortByCity(Query query);

	List<Map<String, Object>> queryFTTHWrongPortByCity(Query query);

	List<Map<String, Object>> queryIOMPortByCity(Query query);

	List<Map<String, Object>> queryIOMWrongPortByCity(Query query);

	List<Map<String, Object>> queryZyCheckPort(Query query);

	List<Map<String, Object>> queryZyWrongPort(Query query);

	List<Map<String, Object>> queryFTTHCheckPort(Query query);

	List<Map<String, Object>> queryFTTHWrongPort(Query query);

	List<Map<String, Object>> queryIOMCheckPort(Query query);

	List<Map<String, Object>> queryIOMWrongPort(Query query);

	
	List<Map<String, Object>> downZyPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downZyWrongPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHWrongPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downIOMPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downIOMWrongPortByCity(Map<String, Object> paras);

	List<Map<String, Object>> downZyCheckPort(Map<String, Object> paras);

	List<Map<String, Object>> downZyWrongPort(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHCheckPort(Map<String, Object> paras);

	List<Map<String, Object>> downFTTHWrongPort(Map<String, Object> paras);

	List<Map<String, Object>> downIOMCheckPort(Map<String, Object> paras);

	List<Map<String, Object>> downIOMWrongPort(Map<String, Object> paras);



}
