package com.util;

import java.util.Calendar;

public class MyDateDto {
	private int year;
	private int month;
	private int date;
	private int dayOfWeek;
	private int weekOfMoth;
	private int weekOfYear;
	private int dayOfYear;
	private long timeInMillis;
	private String acrossWeek;
	private String firstDateOfWeek;
	private String lastDateOfWeek;
	private String dateStr;

	private int dayOfMonth;

	public int getDayCount(MyDateDto anoDate) {
		int a = (int) ((getTimeInMillis() - anoDate.getTimeInMillis()) / 86400000 + 1);
		return a;
	}

	public int getWeekCount(MyDateDto anoDate) {
		return getWeekOfYear() - anoDate.getWeekOfYear() + 1;
	}

	public int getMonthCount(MyDateDto anoDate) {
		int yearCount = getYear() - anoDate.getYear();
		return yearCount * 12 - anoDate.getMonth() + getMonth() + 1;
	}

	public int getTwoMonthCount(MyDateDto anoDate) {
		int yearCount = getYear() - anoDate.getYear();
		int allM = yearCount * 12 - anoDate.getMonth() + getMonth() + 1;
		return allM / 2 + allM % 2;
	}

	/**
	 * 获得当前时间所在周中指定一天的日期
	 * 
	 * @param dayOfWeek
	 *            周几（1~7）
	 * @return
	 */
	public String getAppointedDateOfWeek(int dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(StringUtil.stringToDate(firstDateOfWeek, "yyyy-mm-dd"));
		cal.add(Calendar.DATE, dayOfWeek - 1);
		return StringUtil.dateToString(cal.getTime(), "yyyy-mm-dd");
	}
	
	public int getYearCount(MyDateDto anoDate) {
		return getYear() - anoDate.getYear() + 1;
	}

	public long getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public String getAcrossWeek() {
		return acrossWeek;
	}

	public void setAcrossWeek(String acrossWeek) {
		this.acrossWeek = acrossWeek;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getWeekOfMoth() {
		return weekOfMoth;
	}

	public void setWeekOfMoth(int weekOfMoth) {
		this.weekOfMoth = weekOfMoth;
	}

	public int getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(int weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getFirstDateOfWeek() {
		return firstDateOfWeek;
	}

	public void setFirstDateOfWeek(String firstDateOfWeek) {
		this.firstDateOfWeek = firstDateOfWeek;
	}

	public String getLastDateOfWeek() {
		return lastDateOfWeek;
	}

	public void setLastDateOfWeek(String lastDateOfWeek) {
		this.lastDateOfWeek = lastDateOfWeek;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

}
