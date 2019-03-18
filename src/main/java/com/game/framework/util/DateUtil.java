package com.game.framework.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * @Description 日期工具
 */
public final class DateUtil {

	private DateUtil() {

	}
	
	public static final int[] WEEK_DAY = new int[8];
	static {

		WEEK_DAY[Calendar.MONDAY] = 1;
		WEEK_DAY[Calendar.TUESDAY] = 2;
		WEEK_DAY[Calendar.WEDNESDAY] = 3;
		WEEK_DAY[Calendar.THURSDAY] = 4;
		WEEK_DAY[Calendar.FRIDAY] = 5;
		WEEK_DAY[Calendar.SATURDAY] = 6;
		WEEK_DAY[Calendar.SUNDAY] = 7;

	}
	/** 一秒的毫秒数 */
	public static final long SECOND_MILLISECOND = 1000;
	/** 一分钟的毫秒数 */
	public static final long MINUTE_MILLISECOND = 60 * SECOND_MILLISECOND;
	/** 一小时的毫秒数 */
	public static final long HOUR_MILLISECOND = 60 * MINUTE_MILLISECOND;
	/** 一天的毫秒数 */
	public static final long DAY_MILLISECOND = 24 * HOUR_MILLISECOND;

	// -----------------------------

	/**
	 * 
	 * @Description 返回指定今天小时的日期. 并将毫秒数设为0.
	 * @author chen.su
	 * @date 2015-11-18 下午06:54:50
	 * @param hour24
	 * @return Date
	 */
	public Date getTodayDate(int hour24) {
		return getTodayDate(hour24, 0, 0);
	}

	/**
	 * 
	 * @Description 返回指定今天小时&分钟的日期. 并将毫秒数设为0.
	 * @author chen.su
	 * @date 2015-11-18 下午06:54:50
	 * @param hour24
	 * @return Date
	 */
	public Date getTodayDate(int hour24, int minute) {
		return getTodayDate(hour24, minute, 0);
	}

	/**
	 * 
	 * @Description 返回指定今天小时&分钟&秒的日期. 并将毫秒数设为0.
	 * @author chen.su
	 * @date 2015-11-18 下午06:45:24
	 * @return Date
	 */
	public Date getTodayDate(int hour24, int minute, int second) {
		Date date = null;

		Calendar calendar = Calendar.getInstance();

		date = getDayDateByMillisecond(calendar, date, 0);
		date = getDayDateBySecond(calendar, date, second);
		date = getDayDateByMinute(calendar, date, minute);
		date = getDayDateByHour(calendar, date, hour24);

		return date;
	}

	// -----------------------------

	/**
	 * 
	 * @Description 在指定的日期增加毫秒. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:47:13
	 * @param calendar
	 * @param date
	 * @param millisecond
	 * @return Date
	 */
	public static Date getDateByAddToMillisecond(Calendar calendar, Date date, int millisecond) {

		if (date != null) {
			calendar.setTime(date);
		}

		calendar.set(Calendar.MILLISECOND, millisecond);

		return calendar.getTime();
	}

	/**
	 * 
	 * @Description 在指定的日期上增加秒数. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:23:07
	 * @param calendar
	 * @param date
	 * @param minute
	 * @return Date
	 */
	public static Date getDateByAddToSecond(Calendar calendar, Date date, int second) {
		if (date != null) {
			calendar.setTime(date);
		}

		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Description 在指定的日期增加分钟. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:08:46
	 * @param calendar
	 * @param date
	 * @param minute
	 * @return Date
	 */
	public static Date getDateByAddToMinute(Calendar calendar, Date date, int minute) {
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Description 在指定的日期增加小时. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:08:53
	 * @param calendar
	 * @param date
	 * @param hour
	 * @return Date
	 */
	public static Date getDateByAddToHour(Calendar calendar, Date date, int hour) {
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.add(Calendar.MILLISECOND, hour);
		return calendar.getTime();
	}

	// ----------------------------

	/**
	 * 
	 * @Description 在指定的日期设置毫秒. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:47:13
	 * @param calendar
	 * @param date
	 * @param millisecond
	 * @return Date
	 */
	public static Date getDayDateByMillisecond(Calendar calendar, Date date, int millisecond) {

		if (date != null) {
			calendar.setTime(date);
		}

		calendar.set(Calendar.MILLISECOND, millisecond);

		return calendar.getTime();
	}

	/**
	 * 
	 * @Description 在指定的日期设置秒数. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:09:04
	 * @param calendar
	 * @param date
	 * @param second
	 * @return Date
	 */
	public static Date getDayDateBySecond(Calendar calendar, Date date, int second) {

		if (date != null) {
			calendar.setTime(date);
		}

		calendar.set(Calendar.SECOND, second);

		return calendar.getTime();
	}

	/**
	 * 
	 * @Description 在指定的日期设置分钟数. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:09:10
	 * @param calendar
	 * @param date
	 * @param minute
	 * @return Date
	 */
	public static Date getDayDateByMinute(Calendar calendar, Date date, int minute) {

		if (date != null) {
			calendar.setTime(date);
		}

		calendar.set(Calendar.MINUTE, minute);

		return calendar.getTime();
	}

	/**
	 * 
	 * @Description 在指定的日期设置小时数. 如果date为null, 则使用calendar.getTime()作为指定的日期.
	 * @author chen.su
	 * @date 2015-11-18 下午06:08:59
	 * @param calendar
	 * @param date
	 * @param hour
	 * @return Date
	 */
	public static Date getDayDateByHour(Calendar calendar, Date date, int hour) {

		if (date != null) {
			calendar.setTime(date);
		}

		calendar.set(Calendar.HOUR_OF_DAY, hour);

		return calendar.getTime();
	}

	// ----------------------------

	/**
	 * 
	 * @Description 判断两个时间值是否为同一天
	 * @author chen.su
	 * @date 2015-11-18 下午06:15:17
	 * @param time1
	 * @param time2
	 * @return boolean
	 */
	public static boolean isOneDay(long time1, long time2) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(time1);

		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);

		calendar.setTimeInMillis(time2);

		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);

		if (year1 == year2 && month1 == month2 && day1 == day2) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @Description 返回两个日期之间差的天数
	 * @author chen.su
	 * @date 2015-11-18 下午07:01:50
	 * @param time1
	 * @param time2
	 * @return int time1 与 time2 为同一天返回0. time1 > time2 返回正相差数. time1 < time2
	 *         返回负的相差数.
	 */
	public static int dayDifference(long time1, long time2) {
		return (int) (time1 / DAY_MILLISECOND) - (int) (time2 / DAY_MILLISECOND);
	}

	/**
	 * 
	 * @param birthDate
	 * @param currentDate
	 * @return
	 */
	public static int calculateAge(String birthDate) {
		if ((birthDate != null) ) {
			return Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears();
		} 

		return 1;
	}
	
	public static String[] getEarDay(int earliestday) {
		String[] time = new String[7];
		Calendar date = Calendar.getInstance();
		int earliest = -earliestday;

		for (int i = 0; i < earliest; i++) {
			date.add(Calendar.DATE, -1);
			time[i] = DateUtil.yyyyMMdd(date.getTimeInMillis());
		}

		return time;

	}

	public static Date getAfterDay(Date date, int day) {

		return null;
	}

	public static final String getDefaultFormatDate(long time) {
		// TODO ThreadLocal 优化
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return dateFormat.format(new Date(time));
	}

	public static final String yyyyMMdd(long time) {
		// TODO ThreadLocal 优化
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return dateFormat.format(new Date(time));
	}
	public static String iSOneWeek(long time ) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTimeInMillis(time);

		int day = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(day != 1) {
			calendar.set(Calendar.DAY_OF_WEEK, 1);
		}

		String weekTime = yyyyMMdd(calendar.getTimeInMillis());
		return weekTime;
	}
	/**
	  * 
	 * @Description 返回和上周末相差的具体时间数组
	 * @param time
	 * @return
	 */
	public static String[] dayToLastWeek(long time) {
		Calendar calendar = Calendar.getInstance();		

		calendar.setTimeInMillis(time);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		String[] dateToLastWeek = new String[day];
		for (int i = day; i >= 1; i--) {
			calendar.set(Calendar.DAY_OF_WEEK, i);
			dateToLastWeek[i - 1] = yyyyMMdd(calendar.getTimeInMillis());
		}
		return dateToLastWeek;
	}
	
}