package com.cmbc.gateway.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * 日期工具
 */
@Slf4j
public final class DateUtils {

	private DateUtils() {
	}
	
	/**
	 * ORA标准时间格式yyyy-MM-dd HH:mm:ss
	 */
	private static final SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat standardFormat_US = new SimpleDateFormat("MMM yyyy dd HH:mm:ss", Locale.US);
	
	/**
	 * 标准时间格式yyyy-MM-dd
	 */
	private static final SimpleDateFormat standardSimpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat standardSimpleFormat_US = new SimpleDateFormat("MMM yyyy dd", Locale.US);

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return date == null ? null : new DateTime(date).toString(pattern);
	}

	/**
	 * 转换成时间对象
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date covertDate(String date, String pattern) {
		if (StringUtils.isBlank(date) || !isDate(date, pattern)) {
			return null;
		}
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern(pattern).toFormatter();
		return DateTime.parse(date, formatter).toDate();
	}

	/**
	 * 是否是可以格式化的日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static boolean isDate(String date, String pattern) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern(pattern).toFormatter();
		try {
			formatter.parseDateTime(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 最小化当前日期（年月日不变，时间为0点）
	 * 
	 * @param date
	 * @return
	 */
	public static Date minCurrentDay(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.millisOfDay().withMinimumValue().toDate();
	}

	/**
	 * 最小化当前日期（年月日不变，时间为0点）
	 * 
	 * @param date
	 * @return
	 */
	public static Date maxCurrentDay(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.millisOfDay().withMaximumValue().toDate();
	}

	/**
	 * 增加天数
	 * 
	 * @param date
	 *            日期
	 * @param addDays
	 *            增加的天数
	 * @return
	 */
	public static Date addDays(Date date, int addDays) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusDays(addDays).toDate();
	}

	/**
	 * 增加间隔时间
	 * 
	 * @param date
	 *            日期
	 * @param duration
	 *            间隔时间
	 * @return
	 */
	public static Date addDays(Date date, long duration) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plus(duration).toDate();
	}

	/**
	 * 和当前时间比较
	 * 
	 * <pre>
	 * 如果>0，则当前时间比传入时间大
	 * 如果=0，则当前时间和传入时间相等
	 * 如果<0，则当前时间和传入时间小
	 * </pre>
	 * 
	 * @param overdueTime
	 * @return
	 */
	public static int compareCurrent(Date overdueTime) {
		DateTime now = DateTime.now();
		return now.compareTo(new DateTime(overdueTime));
	}
	
	/**
	 * 获得指定分钟之前/之后的时间，格式化成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param minute
	 *            负数代表之前,正数代表之后
	 * @return
	 */
	public static String getCurrentTimeByMinute(int minute) {
		String oneHoursAgoTime = "";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minute);
		oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		return oneHoursAgoTime;
	}
	
	/**
	 * 字符串转换成Date
	 * 
	 * @param String
	 *            dateStr(格式:yyyy-MM-dd)
	 * @return Date
	 */
	public static Date simpleStringToDate(String dateStr) {
		Date date = null;
		try {
			Pattern p = Pattern.compile("[a-zA-Z]+");
			Matcher m = p.matcher(dateStr);
			if (m.find()) {
				date = standardSimpleFormat_US.parse(dateStr);
			} else {
				date = standardSimpleFormat.parse(dateStr);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	
	/**
	 * 字符串转换成Date
	 * 
	 * @param String
	 *            dateStr(格式:yyyy-MM-dd hh:mm:ss)
	 * @return Date
	 */
	public static Date stringToDate(String dateStr) {
		Date date = null;
		try {
			Pattern p = Pattern.compile("[a-zA-Z]+");
			Matcher m = p.matcher(dateStr);
			if (m.find()) {
				date = standardFormat_US.parse(dateStr);
			} else {
				date = standardFormat.parse(dateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date == null) {
			date = simpleStringToDate(dateStr);
		}
		return date;
	}
	
	/**
	 * 比较两个String日期的大小 大于返回>0,小于返回<0,等于返回=0
	 * 
	 * @param String
	 *            ,String 格式为yyyy-MM-dd HH:mm:ss
	 * @return int
	 */
	public static synchronized int compareStringDate(String date1, String date2) {
		Date dates = stringToDate(date1);
		Date datee = stringToDate(date2);
		return dates.compareTo(datee);
	}
	
	/** 当前时间 */
	public static Date now(){
		return new Date();
	}
	
	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowDateLogger() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	/**
	 * 比较两个日期的大小 大于返回>0,小于返回<0,等于返回=0 默认返回-1
	 * 
	 * @param Date
	 * @return int
	 */
	public static synchronized int compareDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return -1;
		}
		return date1.compareTo(date2);
	}
	
	/**
	 * 和当前时间比较，大于返回>0,小于返回<0,等于返回=0 默认返回-1
	 * @param date1
	 * @return
	 */
	public static int compareNowDate(String date1) {
		String date2 = getNowDateLogger();
		log.info("当前时间=================|" + date2);
		log.info("过期时间=================|" + date1);
		log.info("比较结果=================|" + (compareStringDate(date1 ,date2)));
		return compareStringDate(date1 ,date2);
	}
	

}
