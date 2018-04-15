package icom.cableCheck.dao;

import java.util.Map;
@SuppressWarnings("all")
public interface CheckMyworkDao {

	int countEqpNum(String staffId);

	int countzgEqpNum(String staffId);

	int countPortNum(String staffId);

	int countzgPortNum(String staffId);
	/**
	 * 查询当天完成情况
	 */
	int countTodayEqpNum(String staffId);

	int countTodayzgEqpNum(String staffId);

	int countTodayPortNum(String staffId);

	int countTodayzgPortNum(String staffId);
	
	String getIdentify(String staffId);
	
}
