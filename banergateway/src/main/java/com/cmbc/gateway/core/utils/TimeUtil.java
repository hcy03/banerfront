package com.cmbc.gateway.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

public class TimeUtil {

	public static String get8LDate() {
		SimpleDateFormat format8 = new SimpleDateFormat("yyyyMMdd");
		return format8.format(new Date());
	}

	public static String get9LTime() {
		SimpleDateFormat format9 = new SimpleDateFormat("HHmmssSSS");
		return format9.format(new Date());
	}

	public static String get14LTime() {
		SimpleDateFormat format14 = new SimpleDateFormat("yyyyMMddHHmmss");
		return format14.format(new Date());
	}

	public static String get14LTime(long time) {
		SimpleDateFormat format14 = new SimpleDateFormat("yyyyMMddHHmmss");
		return format14.format(time);
	}

	public static String get17LTime() {
		SimpleDateFormat format17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format17.format(new Date());
	}

	public static Date getDateByDate14(String date14) throws GateWayException {
		SimpleDateFormat format14 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = format14.parse(date14);
		} catch (ParseException e) {
			throw new GateWayException(GWExceptionConstants.SYS1002_CODE, GWExceptionConstants.SYS1002_DESC);
		}
		return date;
	}
}
