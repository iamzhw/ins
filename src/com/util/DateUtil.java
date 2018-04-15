package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getDate(int num) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, num);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * 获取下个星期的星期日
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextWeekSunday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		if (!(cal.get(Calendar.DAY_OF_WEEK) == 1)) {
			int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			cal.add(Calendar.DAY_OF_WEEK, 8 - dayWeek);
		}
		return cal.getTime();
	}

	/**
	 * 获取下个星期的星期一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			cal.add(Calendar.DAY_OF_WEEK, 1);
		} else {
			int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			cal.add(Calendar.DAY_OF_WEEK, 9 - dayWeek);
		}
		return cal.getTime();
	}
	
	/**
	 * 获取当前时间所在周的第一天
	 * 
	 * @return
	 */
	public static Date getMondayOfCurrWeek() {
		return getMondayOfWeek(new Date());
	}
	
	/**
	 * 获取指定时间所在周的第一天
	 * 
	 * @return
	 */
	public static Date getMondayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.add(Calendar.WEEK_OF_YEAR, -1);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		return cal.getTime();
	}

	/**
	 * 获取当前时间所在周的最后一天
	 * 
	 * @return
	 */
	public static Date getSundayOfCurrWeek() {
		return getSundayOfWeek(new Date());
	}
	
	/**
	 * 获取指定时间所在周的最后一天
	 * 
	 * @return
	 */
	public static Date getSundayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return cal.getTime();
	}
	
	/**
	 * 获取当前时间所在月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfCurrMonth() {
		return getFirstDayOfMonth(new Date());
	}
	
	/**
	 * 获取指定时间所在月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 获取当前时间所在月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfCurrMonth() {
		return getLastDayOfMonth(new Date());
	}

	/**
	 * 获取指定时间所在月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取指定时间是一周当中的周几
	 * 
	 * @param cal
	 * @return
	 */
	public static int getDayOfWeek(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (day == 0) {
			day = 7;
		}
		return day;
	}
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		Date date =new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * 获得当前日期
	 */
	public static Date getCurrentDate(){
		Date date =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		Date d = null;
		try {
			 d=sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 获得上月第一天
	 * 
	 */
	public static String getFirstDayOfLastMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return StringUtil.dateToString(
				cal.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获得上月最后一天
	 */
	public static String  getLastDayOfLastMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return StringUtil.dateToString(
				cal.getTime(), "yyyy-MM-dd");
	}
}
