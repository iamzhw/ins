package com.linePatrol.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具包
 * 
 * @author wangyan
 * 
 */
public class DateUtil {
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 以默认格式取当前时间
	 * 
	 * @return
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	public static String getDate(String format) {
		return getDate(format, new Date());
	}

	public static String getDateAndTime() {
		sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdfDate.format(new Date());
	}

	public static String getDate(String format, Date date) {
		sdfDate = new SimpleDateFormat(format);
		return sdfDate.format(date);
	}

	public static String getDateOfRegular(Object date) {
		return sdfDate.format(date);
	}

	// 获取时间戳
	public static long getTime() {
		Date date = new Date();
		return date.getTime();
	}

	// 日期作为名字 20140911134511
	public static String getNameAccordDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return sdf.format(date);
	}

	public static String getNextDate() {
		Date date = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
		return getDate("yyyy-MM-dd", date);
	}

	public static String getYesterDay() {
		Date date = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
		return getDate("yyyy-MM-dd", date);
	}

	/**
	 * 计算两个时间差
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 * @param baseTime
	 *            时间基数:1秒，60分钟，1440小时
	 * @return
	 */
	public static long getDifferTime(String time1, String time2, int baseTime) {

		long diff = 0;
		long min = 0;

		Date d1;
		Date d2;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			d1 = df.parse(time1);
			d2 = df.parse(time2);
			diff = d2.getTime() - d1.getTime();
			min = (diff / (1000 * baseTime));
		} catch (ParseException e) {
			e.printStackTrace();
			min = 0;
		}

		return min;
	}

	/**
	 * 获取当前月份
	 */
	public static int getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取指定月份的第一天
	 * 
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int month) {
		Calendar cal = Calendar.getInstance();

		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 设置日历中月份的第1天
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());

		return firstDayOfMonth;
	}

	/**
	 * 根据开始月和频次获取结束月份最后一天
	 * 
	 * @param cycle
	 */
	public static String getTimeByCycle(int start_month, int cycle) {
		Calendar cal = Calendar.getInstance();
		// 设置月份
		cal.set(Calendar.MONTH, start_month - 1);
		cal.add(Calendar.MONTH, cycle - 1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-" + maxDay);
		String end_date = formatter.format(cal.getTime());
		return end_date;

	}

	/**
	 * 根据开始日期数组与当前月份获取当前所在周期开始月份
	 * 
	 * @param lineMonths
	 * @param current_month
	 * @return
	 */
	public static int getBeginMOnth(int[] lineMonths, int current_month) {
		int index = 0;
		for (int month : lineMonths) {
			if (month == current_month) {
				return month;
			}

			int delta = month - current_month;

			if (delta > 0) {
				return lineMonths[index - 1];
			}
			index++;

		}
		return lineMonths[index - 1];
	}
	
	// 获取数组和当前月获取上一个周期
		public static Map<String, Object> getLastStartMOnth(int[] lineMonths,
				int current_month, int circle) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String beginMonth = "";
			String endMonth   = "";
			int lastBeginMonth = 0;
			// 1 先判断当前周期开始月等不等于数组第一位，等于就是开始月: 去年年份+数组最后的数字 + 1号。结束月份按周期推算
			int begin_month = getBeginMOnth(lineMonths, current_month);// 获取当前开始月份
			for (int i = 0; i < lineMonths.length; i++) {
				if (begin_month == lineMonths[0]) {// 去年的
					lastBeginMonth =  lineMonths[lineMonths.length - 1];
					beginMonth = getLastYearBeginMonth(lastBeginMonth);//获取上一年开始月份1号
					endMonth = getLastYearEndMonth(lastBeginMonth, circle);// 获取结束时间
					paramMap.put("beginMonth", beginMonth);
					paramMap.put("endMonth", endMonth);
					return paramMap;
				} else if (begin_month == lineMonths[i]) {
					// 2 如果不等于获取数组当前周期的上一位
					lastBeginMonth = lineMonths[i - 1];
					// 获取月份1号和结束日期
					beginMonth = getFirstDayOfMonth(lastBeginMonth);
					endMonth = getTimeByCycle(lastBeginMonth, circle);// 获取结束时间
					paramMap.put("beginMonth", beginMonth);
					paramMap.put("endMonth", endMonth);
				}
			}
			return paramMap;
		}
	
	
	/**
	 * 根据开始月份和周期获取上一年结束月份
	 * @param start_month
	 * @param cycle
	 * @return
	 */
	public static String getLastYearEndMonth(int start_month,int cycle){
		Calendar cal = Calendar.getInstance();    
		//设置月份
		cal.add(Calendar.YEAR, -1);
	    cal.set(Calendar.MONTH, start_month-1);
		cal.add(Calendar.MONTH,cycle -1);
		int maxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);    
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-"+maxDay);    
		String end_date=formatter.format(cal.getTime());
		System.out.println(end_date);
		return end_date;
	}
	
	
	/**
	 * 根据月份获取上一年所在月份1号
	 * @param month
	 * @return
	 */
	public static String getLastYearBeginMonth(int month){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		// 设置月份
		c.set(Calendar.MONTH, month - 1);
		// 设置日历中月份的第1天
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastYearDayOfMonth = sdf.format(c.getTime());
		System.out.println(lastYearDayOfMonth);
		return lastYearDayOfMonth;
	}
}
