package com.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

@SuppressWarnings("all")
public class Rule {
	public static final String DAY = "1";
	public static final String WEEK = "2";
	public static final String MONTH = "3";
	public static final String YEAR = "4";
	public static final String HALF_MONTH = "5";

	public static final String MONTH_PARAM = "1,10,20,30";
	// public static final String WEEK_PARAM = "1,3,5,8";
	public static final String YEAR_PARAM = "1,5,9,13";

	public static final String CLOCK1 = " 00:00:00";
	public static final String CLOCK2 = " 23:59:59";

	public static List<Map> createTaskOrder(Map map, String cycle) {
		if (cycle.equals(DAY)) {
			return Rule.createDayTaskOrder(map);
		} else if (cycle.equals(WEEK)) {
			return Rule.createWeekTaskOrder(map);

		} else if (HALF_MONTH.equals(cycle)) {
			return Rule.createHalfMonthTaskOrder(map);

		} else if (cycle.equals(MONTH)) {
			return Rule.createMonthTaskOrder(map);

		} else if (cycle.equals(YEAR)) {
			return Rule.createYearTaskOrder(map);
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		String startDate = "";
		String endDate = "";
		String custom_time = "";
		Map map = new HashMap();
		map.put("startDate", "2015-11-01");
		map.put("endDate", "2015-11-12");
		map.put("custom_time", "");
		map.put("frequency", "1");

		String cycle = HALF_MONTH;
		if (cycle.equals(DAY)) {
			List<Map> res = Rule.createDayTaskOrder(map);

		} else if (cycle.equals(WEEK)) {
			List<Map> res = Rule.createWeekTaskOrder(map);

		} else if (cycle.equals(HALF_MONTH)) {
			List<Map> res = Rule.createHalfMonthTaskOrder(map);

		} else if (cycle.equals(MONTH)) {
			List<Map> res = Rule.createMonthTaskOrder(map);
			System.out.println(res.size());
		} else if (cycle.equals(YEAR)) {
			List<Map> res = Rule.createYearTaskOrder(map);
		}

	}

	public static List createDayTaskOrder(Map map) {
		List<Map> res = new ArrayList();

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String frequency = (String) map.get("frequency");
		// Map param = new HashMap();

		MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
		MyDateDto mEDate = MyCalendar.getDayInfo(endDate);

		int dCount = 0;
		dCount = mEDate.getDayCount(mSDate);
		String mSDateStr = mSDate.getDateStr();
		for (int i = 0; i < dCount; i++) {
			Map param = new HashMap();

			if (Integer.parseInt(frequency) == 1) {
				String sDate = mSDateStr + CLOCK1;
				String eDate = mSDateStr + CLOCK2;

				res.add(addMap(sDate, eDate));

			} else if (Integer.parseInt(frequency) == 2) {

				String sDate = mSDateStr + CLOCK1;
				String eDate = mSDateStr + " 11:59:59";

				res.add(addMap(sDate, eDate));

				sDate = mSDateStr + " 12:00:00";
				eDate = mSDateStr + CLOCK2;

				res.add(addMap(sDate, eDate));

			} else if (Integer.parseInt(frequency) == 3) {

				String sDate = mSDateStr + CLOCK1;
				String eDate = mSDateStr + " 09:59:59";

				res.add(addMap(sDate, eDate));

				sDate = mSDateStr + " 10:00:00";
				eDate = mSDateStr + " 14:59:59";

				res.add(addMap(sDate, eDate));

				sDate = mSDateStr + " 15:00:00";
				eDate = mSDateStr + CLOCK2;

				res.add(addMap(sDate, eDate));

			}
			mSDateStr = MyCalendar.getAddDate(mSDateStr, 5, 1).getDateStr();
		}
		return res;
	}

	public static Map addMap(String startDate, String endDate) {
		Map param = new HashMap<String, String>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		return param;
	}

	/**
	 * 创建周计划时间列表
	 * 
	 * @param map
	 * @return
	 */
	public static List createWeekTaskOrder(Map map) {
		List<Map> res = new ArrayList();

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		int frequency = NumberUtils.toInt(StringUtil.objectToString(map
				.get("frequency")));
		String custom_time = (String) map.get("custom_time");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map param = new HashMap();

		MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
		MyDateDto mEDate = MyCalendar.getDayInfo(endDate);

		boolean isCustomTime = StringUtils.isNotBlank(custom_time);// 任务是否自定义执行时间
		List<Integer> customTimeList = null;
		if (isCustomTime) {
			customTimeList = StringUtil.toIntegerList(custom_time, ",");
			if (customTimeList.size() == 0) {
				return null;
			}
		}

		String sDate = null;
		String eDate = null;
		String appointedDate = null;

		int weekCount = 0;// 周数，不足一周算一周
		weekCount = MyCalendar
				.getDayInfo(mEDate.getLastDateOfWeek())
				.getWeekCount(MyCalendar.getDayInfo(mSDate.getLastDateOfWeek()));
		for (int i = 0; i < weekCount; i++) {
			String mSDateStr = mSDate.getDateStr();
			if (frequency == 1) {
				if (isCustomTime) {
					appointedDate = mSDate
							.getAppointedDateOfWeek(customTimeList.get(0));
					sDate = appointedDate + CLOCK1;
					eDate = appointedDate + CLOCK2;
				} else {
					sDate = mSDate.getFirstDateOfWeek() + CLOCK1;
					eDate = mSDate.getLastDateOfWeek() + CLOCK2;
				}
				res.add(addMap(sDate, eDate));
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.WEEK_OF_YEAR, 1).getDateStr());
			} else if (frequency == 2) {
				if (isCustomTime) {
					for (int j = 0; j < 2; j++) {
						appointedDate = mSDate
								.getAppointedDateOfWeek(customTimeList.get(j));
						sDate = appointedDate + CLOCK1;
						eDate = appointedDate + CLOCK2;
						res.add(addMap(sDate, eDate));
					}
				} else {
					sDate = mSDate.getFirstDateOfWeek() + CLOCK1;
					eDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),
							5, 3).getDateStr()
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),
							5, 4).getDateStr()
							+ CLOCK1;
					eDate = mSDate.getLastDateOfWeek() + CLOCK2;
					res.add(addMap(sDate, eDate));
				}
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.WEEK_OF_YEAR, 1).getDateStr());

			} else if (frequency == 3) {
				if (isCustomTime) {
					for (int j = 0; j < 3; j++) {
						appointedDate = mSDate
								.getAppointedDateOfWeek(customTimeList.get(j));
						sDate = appointedDate + CLOCK1;
						eDate = appointedDate + CLOCK2;
						res.add(addMap(sDate, eDate));
					}
				} else {
					sDate = mSDate.getFirstDateOfWeek() + CLOCK1;
					eDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),
							5, 1).getDateStr()
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),
							5, 2).getDateStr()
							+ CLOCK1;
					eDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),
							5, 3).getDateStr()
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = MyCalendar.getAddDate(mSDate.getFirstDateOfWeek(),
							5, 4).getDateStr()
							+ CLOCK1;
					eDate = mSDate.getLastDateOfWeek() + CLOCK2;
					res.add(addMap(sDate, eDate));
				}

				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.WEEK_OF_YEAR, 1).getDateStr());
			}
		}
		return res;
	}

	public static List createMonthTaskOrder(Map map) {
		List<Map> res = new ArrayList();

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		int frequency = NumberUtils.toInt(StringUtil.objectToString(map
				.get("frequency")));
		String custom_time = (String) map.get("custom_time");
		Map param = new HashMap();

		MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
		MyDateDto mEDate = MyCalendar.getDayInfo(endDate);

		boolean isCustomTime = StringUtils.isNotBlank(custom_time);// 任务是否自定义执行时间
		List<Integer> customTimeList = null;
		if (isCustomTime) {
			customTimeList = StringUtil.toIntegerList(custom_time, ",");
			if (customTimeList.size() == 0) {
				return null;
			}
		}

		String sDate = null;
		String eDate = null;
		String appointedDate = null;

		int monthCount = 0;
		monthCount = mEDate.getMonthCount(mSDate);

		for (int i = 0; i < monthCount; i++) {
			String mSDateStr = mSDate.getDateStr();
			if (frequency == 1) {
				if (isCustomTime) {
					appointedDate = eDate = mSDate.getYear() + "-"
							+ mSDate.getMonth() + "-" + customTimeList.get(0);
					sDate = appointedDate + CLOCK1;
					eDate = appointedDate + CLOCK2;
				} else {
					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mSDate.getYear()
							+ "-"
							+ mSDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
									.getMonth()) + CLOCK2;
				}
				res.add(addMap(sDate, eDate));
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.MONTH, 1).getDateStr());
			} else if (frequency == 2) {
				if (isCustomTime) {
					for (int j = 0; j < 2; j++) {
						appointedDate = eDate = mSDate.getYear() + "-"
								+ mSDate.getMonth() + "-"
								+ customTimeList.get(j);
						sDate = appointedDate + CLOCK1;
						eDate = appointedDate + CLOCK2;
						res.add(addMap(sDate, eDate));
					}
				} else {
					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-16"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-17"
							+ CLOCK1;
					eDate = mSDate.getYear()
							+ "-"
							+ mSDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
									.getMonth()) + CLOCK2;
					res.add(addMap(sDate, eDate));
				}
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.MONTH, 1).getDateStr());
			} else if (frequency == 3) {
				if (isCustomTime) {
					for (int j = 0; j < 3; j++) {
						appointedDate = eDate = mSDate.getYear() + "-"
								+ mSDate.getMonth() + "-"
								+ customTimeList.get(j);
						sDate = appointedDate + CLOCK1;
						eDate = appointedDate + CLOCK2;
						res.add(addMap(sDate, eDate));
					}
				} else {
					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-10"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-11"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-20"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-21"
							+ CLOCK1;
					eDate = mSDate.getYear()
							+ "-"
							+ mSDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
									.getMonth()) + CLOCK2;
					res.add(addMap(sDate, eDate));
				}
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.MONTH, 1).getDateStr());
			}
			else if (frequency == 4) {
				if (isCustomTime) {
					for (int j = 0; j < 4; j++) {
						appointedDate = eDate = mSDate.getYear() + "-"
								+ mSDate.getMonth() + "-"
								+ customTimeList.get(j);
						sDate = appointedDate + CLOCK1;
						eDate = appointedDate + CLOCK2;
						res.add(addMap(sDate, eDate));
					}
				} else {
					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-10"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-11"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-16"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));
					
					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-17"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-22"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-23"
							+ CLOCK1;
					eDate = mSDate.getYear()
							+ "-"
							+ mSDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
									.getMonth()) + CLOCK2;
					res.add(addMap(sDate, eDate));
				}
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						Calendar.MONTH, 1).getDateStr());
			}
		}
		return res;
	}

	public static List createYearTaskOrder(Map map) {
		List<Map> res = new ArrayList();

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		String frequency = (String) map.get("frequency");

		Map param = new HashMap();

		MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
		MyDateDto mEDate = MyCalendar.getDayInfo(endDate);

		int yCount = 0;
		yCount = mEDate.getYearCount(mSDate);
		String mSDateStr = mSDate.getDateStr();

		String sDate = "";
		String eDate = "";
		for (int i = 0; i < yCount; i++) {
			if (Integer.parseInt(frequency) == 1) {

				if (i == 0) {
					sDate = mSDateStr + CLOCK1;
					eDate = mSDate.getYear() + "-12-31" + CLOCK2;
					res.add(addMap(sDate, eDate));

				} else if (i == yCount - 1) {
					sDate = mSDate.getYear() + "-01-01" + CLOCK1;
					eDate = mEDate.getDateStr() + CLOCK2;
					res.add(addMap(sDate, eDate));

				} else {
					sDate = mSDate.getYear() + "-01-01" + CLOCK1;
					eDate = mSDate.getYear() + "-12-31" + CLOCK2;
					res.add(addMap(sDate, eDate));

				}

				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						1, 1).getDateStr());
			} else if (Integer.parseInt(frequency) == 2) {
				if (i == 0) {
					if (mSDate.getMonth() > 6) {

						sDate = mSDate.getDateStr() + CLOCK1;
						eDate = mSDate.getYear() + "-12-31" + CLOCK2;
						res.add(addMap(sDate, eDate));

					} else {

						sDate = mSDate.getDateStr() + CLOCK1;
						eDate = mSDate.getYear() + "-06-30" + CLOCK2;
						res.add(addMap(sDate, eDate));

						sDate = mSDate.getDateStr() + CLOCK1;
						eDate = mSDate.getYear() + "-12-31" + CLOCK2;
						res.add(addMap(sDate, eDate));

					}
				} else if (i == yCount - 1) {
					if (mEDate.getMonth() < 6) {

						sDate = mSDate.getYear() + "-01-01" + CLOCK1;
						eDate = mEDate.getDateStr() + CLOCK2;
						res.add(addMap(sDate, eDate));

					} else {

						sDate = mSDate.getYear() + "-01-01" + CLOCK1;
						eDate = mSDate.getYear() + "-06-30" + CLOCK2;
						res.add(addMap(sDate, eDate));

						sDate = mSDate.getYear() + "-07-01" + CLOCK1;
						eDate = mEDate.getDateStr() + CLOCK2;
						res.add(addMap(sDate, eDate));

					}
				} else {
					sDate = mSDate.getYear() + "-01-01" + CLOCK1;
					eDate = mSDate.getYear() + "-06-30" + CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-07-01" + CLOCK1;
					eDate = mSDate.getYear() + "-12-31" + CLOCK2;
					res.add(addMap(sDate, eDate));

				}
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						1, 1).getDateStr());
			} else if (Integer.parseInt(frequency) == 3) {
				String[] yearParam = YEAR_PARAM.split(",");
				if (i == 0) {
					yearParam[0] = mSDate.getMonth() + 1 + "";
					if (mSDate.getDate() < 5) {
						yearParam[0] = mSDate.getMonth() + "";
						for (int j = 0; j < yearParam.length - 1; j++) {

							sDate = mSDate.getYear() + "-" + yearParam[j]
									+ "-01" + CLOCK1;
							eDate = mSDate.getYear()
									+ "-"
									+ (Integer.parseInt(yearParam[j + 1]) - 1)
									+ "-"
									+ MyCalendar
											.getMonthDays(
													mSDate.getYear(),
													(Integer
															.parseInt(yearParam[j + 1]) - 1))
									+ CLOCK2;
							res.add(addMap(sDate, eDate));

						}
					} else if (mSDate.getDate() < 9) {
						yearParam[1] = mSDate.getMonth() + "";
						for (int j = 1; j < yearParam.length - 1; j++) {

							sDate = mSDate.getYear() + "-" + yearParam[j]
									+ "-01" + CLOCK1;
							eDate = mSDate.getYear()
									+ "-"
									+ (Integer.parseInt(yearParam[j + 1]) - 1)
									+ "-"
									+ MyCalendar
											.getMonthDays(
													mSDate.getYear(),
													(Integer
															.parseInt(yearParam[j + 1]) - 1))
									+ CLOCK2;
							res.add(addMap(sDate, eDate));

						}
					} else {
						yearParam[2] = mSDate.getMonth() + "";
						for (int j = 2; j < yearParam.length - 1; j++) {

							sDate = mSDate.getYear() + "-" + yearParam[j]
									+ "-01" + CLOCK1;
							eDate = mSDate.getYear()
									+ "-"
									+ (Integer.parseInt(yearParam[j + 1]) - 1)
									+ "-"
									+ MyCalendar
											.getMonthDays(
													mSDate.getYear(),
													(Integer
															.parseInt(yearParam[j + 1]) - 1))
									+ CLOCK2;
							res.add(addMap(sDate, eDate));

						}
					}
				} else if (i == yCount - 1) {
					if (mEDate.getMonth() < 5) {
						yearParam[1] = mEDate.getMonth() + 1 + "";

						for (int j = 2; j < 3; j++) {

							sDate = mSDate.getYear() + "-" + yearParam[0]
									+ "-01" + CLOCK1;
							eDate = mSDate.getYear() + "-"
									+ (Integer.parseInt(yearParam[1]) - 1)
									+ "-" + mEDate.getDate() + CLOCK2;
							res.add(addMap(sDate, eDate));

						}
					} else if (mEDate.getMonth() < 9) {
						yearParam[2] = mEDate.getDate() + 1 + "";
						for (int j = 0; j < 2; j++) {

							sDate = mSDate.getYear() + "-" + yearParam[j] + "-"
									+ yearParam[j] + CLOCK1;
							eDate = mSDate.getYear()
									+ "-"
									+ (Integer.parseInt(yearParam[j + 1]) - 1)
									+ "-"
									+ MyCalendar
											.getMonthDays(
													mSDate.getYear(),
													(Integer
															.parseInt(yearParam[j + 1]) - 1))
									+ CLOCK2;
							res.add(addMap(sDate, eDate));

						}
					} else {
						yearParam[3] = mEDate.getDate() + 1 + "";
						for (int j = 0; j < yearParam.length - 1; j++) {

							sDate = mSDate.getYear() + "-" + yearParam[j]
									+ "-01" + CLOCK1;
							eDate = mSDate.getYear()
									+ "-"
									+ (Integer.parseInt(yearParam[j + 1]) - 1)
									+ "-"
									+ MyCalendar
											.getMonthDays(
													mSDate.getYear(),
													(Integer
															.parseInt(yearParam[j + 1]) - 1))
									+ CLOCK2;
							res.add(addMap(sDate, eDate));

						}
					}
				} else {
					for (int j = 0; j < yearParam.length - 1; j++) {

						sDate = mSDate.getYear() + "-" + yearParam[j] + "-01"
								+ CLOCK1;
						eDate = mSDate.getYear()
								+ "-"
								+ (Integer.parseInt(yearParam[j + 1]) - 1)
								+ "-"
								+ MyCalendar
										.getMonthDays(
												mSDate.getYear(),
												(Integer
														.parseInt(yearParam[j + 1]) - 1))
								+ CLOCK2;
						res.add(addMap(sDate, eDate));

					}
				}
				mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
						1, 1).getDateStr());
			}
		}
		return res;

	}

	public static List createHalfMonthTaskOrder(Map map) {
		List<Map> res = new ArrayList();

		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");

		MyDateDto mSDate = MyCalendar.getDayInfo(startDate);
		MyDateDto mEDate = MyCalendar.getDayInfo(endDate);

		String sDate = null;
		String eDate = null;
		String appointedDate = null;

		int monthCount = 0;
		monthCount = mEDate.getMonthCount(mSDate);

		for (int i = 0; i < monthCount; i++) {
			String mSDateStr = mSDate.getDateStr();

			// 根据i判断开始日期/结束日期所在月的时间段,其余月份按上下半月生成
			if (i == 0) {

				// 第一个月,判断mSDate日范围
				if (mSDate.getDate() <= 16) {

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-16"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-17"
							+ CLOCK1;
					eDate = mSDate.getYear()
							+ "-"
							+ mSDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
									.getMonth()) + CLOCK2;
					res.add(addMap(sDate, eDate));

				} else {

					sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-17"
							+ CLOCK1;
					eDate = mSDate.getYear()
							+ "-"
							+ mSDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
									.getMonth()) + CLOCK2;
					res.add(addMap(sDate, eDate));
				}

			} else if (i == monthCount - 1) {

				// 最后一个月,判断mEDate日范围
				if (mEDate.getDate() <= 16) {

					sDate = mEDate.getYear() + "-" + mEDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mEDate.getYear() + "-" + mEDate.getMonth() + "-16"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));
				} else {

					sDate = mEDate.getYear() + "-" + mEDate.getMonth() + "-01"
							+ CLOCK1;
					eDate = mEDate.getYear() + "-" + mEDate.getMonth() + "-16"
							+ CLOCK2;
					res.add(addMap(sDate, eDate));

					sDate = mEDate.getYear() + "-" + mEDate.getMonth() + "-17"
							+ CLOCK1;
					eDate = mEDate.getYear()
							+ "-"
							+ mEDate.getMonth()
							+ "-"
							+ MyCalendar.getMonthDays(mEDate.getYear(), mEDate
									.getMonth()) + CLOCK2;
					res.add(addMap(sDate, eDate));
				}

			} else {

				// 其余中间月份固定按上半月、下半月生成日期时间段
				sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-01"
						+ CLOCK1;
				eDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-16"
						+ CLOCK2;
				res.add(addMap(sDate, eDate));

				sDate = mSDate.getYear() + "-" + mSDate.getMonth() + "-17"
						+ CLOCK1;
				eDate = mSDate.getYear()
						+ "-"
						+ mSDate.getMonth()
						+ "-"
						+ MyCalendar.getMonthDays(mSDate.getYear(), mSDate
								.getMonth()) + CLOCK2;
				res.add(addMap(sDate, eDate));
			}

			// res.add(addMap(sDate, eDate));
			mSDate = MyCalendar.getDayInfo(MyCalendar.getAddDate(mSDateStr,
					Calendar.MONTH, 1).getDateStr());
		}
		return res;
	}

}
