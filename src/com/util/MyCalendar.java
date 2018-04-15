package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 
 * 
 * @author <a href="zhou.huicai1@zte.com.cn">zhou.huicai1</a>
 * @version 1.0 
 * @since 2012-3-18 上午11:26:42
 * 
 */

public  class MyCalendar {

	public static  MyDateDto getDayInfo(){
		Calendar cal=Calendar.getInstance(); 
		cal.setTime(new Date());		
		return getDayInfo(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DATE));
	}
	public static MyDateDto getDayInfo(String s){
		String[] date = s.split("-");
		return getDayInfo(date[0],date[1],date[2]);
	}
	public static  MyDateDto getDayInfo(String y,String m,String d){
				
		return getDayInfo(Integer.parseInt(y),Integer.parseInt(m),Integer.parseInt(d));
	}
	public static MyDateDto getDayInfo(int y,int m,int d){
		Calendar cal=Calendar.getInstance(); 
		cal.set(y, m-1,d);
		MyDateDto mydate = new MyDateDto();
		mydate.setYear(cal.get(Calendar.YEAR));
		mydate.setMonth(cal.get(Calendar.MONTH)+1);
		mydate.setDate(cal.get(Calendar.DATE));
		mydate.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
		mydate.setTimeInMillis(cal.getTimeInMillis());
		mydate.setWeekOfMoth(cal.get(Calendar.WEEK_OF_MONTH));
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		mydate.setDateStr(sf2.format(cal.getTime()));
		mydate.setDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
		
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		if(cal.get(Calendar.MONTH)>0 && week==1){
			cal.add(Calendar.DATE, -7);
			week = cal.get(Calendar.WEEK_OF_YEAR)+1;
			cal.add(Calendar.DATE, 7);
			mydate.setAcrossWeek((cal.get(Calendar.YEAR)+1)+"1");
			
		}else{
			mydate.setAcrossWeek("");
		}
		
		int wd = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(wd==0){
			wd = 7;
			week = week-1;
		}
		mydate.setWeekOfYear(week);
		mydate.setDayOfWeek(wd);
		cal.add(Calendar.DATE,1-wd);
		
		mydate.setFirstDateOfWeek(sf2.format(cal.getTime()));
		cal.add(Calendar.DATE,wd-1+7-wd);
		mydate.setLastDateOfWeek(sf2.format(cal.getTime()));
		return mydate;
	}
	public static MyDateDto getAddDate(int addType,int ad){
		 Calendar cal=Calendar.getInstance();
		 cal.setTime(new Date());
		 SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 return getAddDate(sf2.format(cal.getTime()), addType, ad);
	}
	
	public static MyDateDto getAddDate(String dateStr,int addType,int ad){
		 Calendar cal=Calendar.getInstance(); 
		 String str[]=dateStr.split("-");
		 cal.set(Integer.parseInt(str[0]), Integer.parseInt(str[1])-1,Integer.parseInt(str[2]));
		 cal.add(addType, ad);
		 int wd = cal.get(Calendar.DAY_OF_WEEK)-1;
		 if(wd==0)wd = 7;
		 MyDateDto mydate = new MyDateDto();
		 
		  int week = cal.get(Calendar.WEEK_OF_YEAR);
		  if(cal.get(Calendar.MONTH)>0 && week==1){
				cal.add(Calendar.DATE, -7);
				week = cal.get(Calendar.WEEK_OF_YEAR)+1;
				cal.add(Calendar.DATE, 7);
				mydate.setAcrossWeek((cal.get(Calendar.YEAR)+1)+"1");
			}else{
				mydate.setAcrossWeek("");
			}
		 mydate.setWeekOfYear(week);
		 mydate.setYear(cal.get(Calendar.YEAR));
		 mydate.setMonth(cal.get(Calendar.MONTH)+1);
		 mydate.setDate(cal.get(Calendar.DATE));
		 SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		 mydate.setDateStr(sf2.format(cal.getTime()));
		 return mydate;
		
	}
	 public static MyDateDto getWeekFirtDate(int addWeeks){
		 Calendar cal=Calendar.getInstance(); 
		 cal.setTime(new Date());
		 cal.add(Calendar.DATE, addWeeks * 7);
		 int wd = cal.get(Calendar.DAY_OF_WEEK)-1;
		 if(wd==0)wd = 7;
		 cal.add(Calendar.DATE,1-wd);
		
		 MyDateDto mydate = new MyDateDto();
		 
		 int week = cal.get(Calendar.WEEK_OF_YEAR);
		  if(cal.get(Calendar.MONTH)>0 && week==1){
				cal.add(Calendar.DATE, -7);
				week = cal.get(Calendar.WEEK_OF_YEAR)+1;
				cal.add(Calendar.DATE, 7);
				mydate.setAcrossWeek((cal.get(Calendar.YEAR)+1)+"1");
			}else{
				mydate.setAcrossWeek("");
			}
		 mydate.setWeekOfYear(week);
		 
		 mydate.setYear(cal.get(Calendar.YEAR));
		 mydate.setMonth(cal.get(Calendar.MONTH)+1);
		 mydate.setDate(cal.get(Calendar.DATE));
		 SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		 mydate.setFirstDateOfWeek(sf2.format(cal.getTime()));
		 cal.add(Calendar.DATE,6);
		 mydate.setLastDateOfWeek(sf2.format(cal.getTime()));
		 return mydate;
	 }
	 public static MyDateDto getWeekLastDate(int addWeeks){
		 Calendar cal=Calendar.getInstance(); 
		 cal.setTime(new Date());
		 cal.add(Calendar.DATE, addWeeks * 7);
		 int wd = cal.get(Calendar.DAY_OF_WEEK)-1;
		 if(wd==0)wd = 7;
		 cal.add(Calendar.DATE,7-wd);
		 
		
		 MyDateDto mydate = new MyDateDto();
		 int week = cal.get(Calendar.WEEK_OF_YEAR);
		  if(cal.get(Calendar.MONTH)>0 && week==1){
				cal.add(Calendar.DATE, -7);
				week = cal.get(Calendar.WEEK_OF_YEAR)+1;
				cal.add(Calendar.DATE, 7);
				mydate.setAcrossWeek((cal.get(Calendar.YEAR)+1)+"1");
			}else{
				mydate.setAcrossWeek("");
			}
		 mydate.setWeekOfYear(week);
		 mydate.setYear(cal.get(Calendar.YEAR));
		 mydate.setMonth(cal.get(Calendar.MONTH)+1);
		 mydate.setDate(cal.get(Calendar.DATE));
		 SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		 mydate.setLastDateOfWeek(sf2.format(cal.getTime()));
		 cal.add(Calendar.DATE,-6);
		 mydate.setFirstDateOfWeek(sf2.format(cal.getTime()));
		 return mydate;
	 }
	 public static MyDateDto getWeekFirtDate(int y,int m,int d,int addWeeks){
		 Calendar cal=Calendar.getInstance(); 
		 cal.set(y, m-1,d);
		 cal.add(Calendar.DATE, addWeeks * 7);
		 int wd = cal.get(Calendar.DAY_OF_WEEK)-1;
		 if(wd==0)wd = 7;
		 cal.add(Calendar.DATE,1-wd);
		
		 MyDateDto mydate = new MyDateDto();
		 int week = cal.get(Calendar.WEEK_OF_YEAR);
		  if(cal.get(Calendar.MONTH)>0 && week==1){
				cal.add(Calendar.DATE, -7);
				week = cal.get(Calendar.WEEK_OF_YEAR)+1;
				cal.add(Calendar.DATE, 7);
				mydate.setAcrossWeek((cal.get(Calendar.YEAR)+1)+"1");
			}else{
				mydate.setAcrossWeek("");
			}
		 mydate.setWeekOfYear(week);
		 mydate.setYear(cal.get(Calendar.YEAR));
		 mydate.setMonth(cal.get(Calendar.MONTH)+1);
		 mydate.setDate(cal.get(Calendar.DATE));
		 SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		 mydate.setFirstDateOfWeek(sf2.format(cal.getTime()));
		 return mydate;
	 }
	 public static MyDateDto getWeekLastDate(int y,int m,int d,int addWeeks){
		 Calendar cal=Calendar.getInstance(); 
		 cal.set(y, m-1,d);
		 cal.add(Calendar.DATE, addWeeks * 7);
		 int wd = cal.get(Calendar.DAY_OF_WEEK)-1;
		 if(wd==0)wd = 7;
		 cal.add(Calendar.DATE,7-wd);
		 
		
		 MyDateDto mydate = new MyDateDto();
		 int week = cal.get(Calendar.WEEK_OF_YEAR);
		  if(cal.get(Calendar.MONTH)>0 && week==1){
				cal.add(Calendar.DATE, -7);
				week = cal.get(Calendar.WEEK_OF_YEAR)+1;
				cal.add(Calendar.DATE, 7);
				mydate.setAcrossWeek((cal.get(Calendar.YEAR)+1)+"1");
			}else{
				mydate.setAcrossWeek("");
			}
		 mydate.setWeekOfYear(week);
		 mydate.setYear(cal.get(Calendar.YEAR));
		 mydate.setMonth(cal.get(Calendar.MONTH)+1);
		 mydate.setDate(cal.get(Calendar.DATE));
		 SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		 mydate.setLastDateOfWeek(sf2.format(cal.getTime()));
		 return mydate;
	 }
	 public static int getMonthDays(String year,String month){
		 return getMonthDays(Integer.parseInt(year),Integer.parseInt(month));
		}
		public static int getMonthDays(String year,int month){
			 return getMonthDays(Integer.parseInt(year),month);
			}
		public static int getMonthDays(int year,String month){
			 return getMonthDays(year,Integer.parseInt(month));
			}
		public static int getMonthDays(int year,int month){
		    boolean bool = false;	
		    int num=0;
			if((year%4==0&&year%100!=0)||(year%400==0)){
			   bool = true;
			  }
			
			  switch(month)
			  {
			  case 1:
			  case 3:
			  case 5:
			  case 7:
			  case 8:
			  case 10:
			  case 12:
			   num =31;
			   break;
			  case 4:
			  case 6:
			  case 9:
			  case 11:
			   num = 30;
			   break;
			  case 2:
			   if(bool==true)
			   {
			    num=29;
			   }
			   else 
			   {
			    num =28;
			   }
			   break;
			  }
			  return num;
		    }

}
