package com.ideassoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class DateUtil {
	
	private static HashMap<String, SimpleDateFormat> dfMap = new HashMap<String, SimpleDateFormat>();

	private static SimpleDateFormat getDf(String pattern) {
		SimpleDateFormat df = dfMap.get(pattern);
		if (df == null) {
			df = new SimpleDateFormat(pattern);
			dfMap.put(pattern, df);
		}

		return df;
	}

	public static String d2s(Date date, String pattern) {
		SimpleDateFormat sdf = getDf(pattern);
		return sdf.format(date);
	}

	public static String d2s(Date date) {
		SimpleDateFormat sdf = getDf("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String t2s(Date date) {
		SimpleDateFormat sdf = getDf("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String utilDate2Str(java.util.Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		return df.format(date);

	}

	public static String currentDate() {
		return currentDate("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String currentDate(String pattern) {
		return utilDate2Str(new java.util.Date(), pattern);
	}
	
	public static String nextDate(String pattern) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return utilDate2Str(date, pattern);
	}
	
	public static Date s2d(String strdate, String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		Date date = null;
		try {
			 date = df.parse(strdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static boolean isSameDay(Calendar day1, Calendar day2){
		return day1.get(Calendar.DAY_OF_YEAR) == day2.get(Calendar.DAY_OF_YEAR)?true:false;
	}
	
	public static long subHour(Calendar hour1, Calendar hour2){
		long subhour = (hour1.getTimeInMillis() - hour2.getTimeInMillis())/(60*60*1000) + 1;
		return Math.abs(subhour);
	}
	
	public static void onlysetMonthYear(Calendar calendar){
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
	}
	
}
