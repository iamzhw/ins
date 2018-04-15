package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class KeepRule {

	public static List<Map> createTaskOrder(Map ruleMap) {
		String startDate = (String) ruleMap.get("startDate");
		String endDate = (String) ruleMap.get("endDate");
		List<Map<String, String>> timeList = (List<Map<String, String>>) ruleMap.get("timeList");
		
		List<Map> res = new ArrayList<Map>();

		MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
		MyDateDto mEDate = MyCalendar.getDayInfo(endDate);

		int dCount = 0;
		dCount = mEDate.getDayCount(mSDate);
		String mSDateStr = mSDate.getDateStr();
		String sDate = null;
		String eDate = null;
		Map<String, String> param = null;
		for (int i = 0; i < dCount; i++) {
			for(Map<String, String> times : timeList){
				sDate = mSDateStr + " " + times.get("START_TIME");
				eDate = mSDateStr + " " + times.get("END_TIME");
				param = new HashMap<String, String>();
				param.put("startDate", sDate);
				param.put("endDate", eDate);
				res.add(param);
			}
			mSDateStr = MyCalendar.getAddDate(mSDateStr, 5, 1).getDateStr();
		}
		return res;
	}

}
