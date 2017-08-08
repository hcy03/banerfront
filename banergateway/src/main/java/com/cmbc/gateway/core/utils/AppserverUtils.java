/*
 * 功  能：简单说明该类的功能
 * 
 * 文件名：AppserverUtils.java
 * 
 * 描  述：
 * 
 * [变更履历]
 * Version   变更日         部署              作者           变更内容
 * -----------------------------------------------------------------------------
 * V1.00     2015年10月19日   Rmitec    ZhouJing     CREATE
 * -----------------------------------------------------------------------------
 *
 *
 * Copyright (c) 2015 Rmitec All Rights Reserved.
 *┌─────────────────────────────────────────────────—────┐
 *│ 版权声明                               Rmitec       │
 *└──────────────────────────────—————————————————————───┘
 */

package com.cmbc.gateway.core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.cmbc.gateway.exception.ExceptionContants;
import com.cmbc.gateway.exception.GateWayException;

/**
 *  DESCRIPTION:
 *
 * <p>
 * <a href="AppserverUtils.java"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:ZhouJing2@rmitec.com.cn">ZhouJing</a>
 *
 * @version Revision: 1.0  Date: 2015年10月19日 下午3:45:05 
 *
 */
public class AppserverUtils {
	/**
	 * 
	 * DESCRIPTION:获取yyyyMMddHHmmss格式日期
	 * @author ZhouJing
	 * @date 2015年10月19日
	 * getDate14 方法
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDate14(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String date14 = sdf.format(new Date());
		
		return date14;
	}
	/**
	 * 
	 * DESCRIPTION:获取yyyyMMdd格式日期
	 * @author ZhouJing
	 * @date 2015年10月28日
	 * getDate8方法
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDate8(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		String date8 = sdf.format(new Date());
		
		return date8;
	}
	/**
	 * 
	 * DESCRIPTION:获取HHmmssSSS格式日期
	 * @author ZhouJing
	 * @date 2015年10月28日
	 * getDate9 方法
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDate9(){
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		
		String date14 = sdf.format(new Date());
		
		return date14;
	}
	/**
	 * 
	 * DESCRIPTION:根据yyyyMMddHHmmss格式获取Date对象
	 * @author ZhouJing
	 * @date 2015年10月19日
	 * getDateByDate14 方法
	 * @param date14
	 * @return
	 * @param commonQueryVO
	 * @return
	 * @throws GateWayException 
	 */
	public static Date getDateByDate14(String date14) throws GateWayException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date date = new Date();
		try {
			date = sdf.parse(date14);
		} catch (ParseException e) {
			throw new GateWayException(ExceptionContants.APPS_EXCEPTION_CODE_0006, ExceptionContants.APPS_EXCEPTION_DESC_0006+e.getMessage());
		}
		
		return date;
	}
	
	/**
	 * 
	 * DESCRIPTION:根据data14获得yyyy-MM-dd HH:mm:ss 日期对象
	 * @author ZhouJing
	 * @date 2015年10月19日
	 * getDateStrByDate14 方法
	 * @param date14
	 * @return
	 * @param commonQueryVO
	 * @return
	 * @throws GateWayException 
	 */
	public static String getDateStrByDate14(String date14) throws GateWayException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date =getDateByDate14(date14);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 
	 * DESCRIPTION:根据Date日期yyyy-MM-dd HH:mm:ss 日期对象
	 * @author ZhouJing
	 * @date 2015年10月19日
	 * getDateStrByDate14 方法
	 * @param date
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDateStrByDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateStr = sdf.format(date);
		return dateStr;
	}
	/**
	 * 
	 * DESCRIPTION:获得当前yyyy-MM-dd HH:mm:ss 日期对象
	 * @author ZhouJing
	 * @date 2015年10月20日
	 * getDateStr 方法
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDateStr(){
		return getDateStrByDate(new Date());
	}
	
	/**
	 * 
	 * DESCRIPTION:获得yyyyMMddHHmmssSSS格式的字符串
	 * @author ZhouJing
	 * @date 2015年10月22日
	 * getDate17 方法
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDate17(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String date17 = sdf.format(new Date());
		
		return date17;
	}
	/**
	 * 
	 * DESCRIPTION:根据yyyyMMddHHmmssSSS格式字符串获得Date对象
	 * @author ZhouJing
	 * @date 2015年10月22日
	 * getDateByDate17 方法
	 * @param date17
	 * @return
	 * @param commonQueryVO
	 * @return
	 * @throws GateWayException 
	 */
	public static Date getDateByDate17(String date17) throws GateWayException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		Date date = new Date();
		try {
			date = sdf.parse(date17);
		} catch (ParseException e) {
			throw new GateWayException(ExceptionContants.APPS_EXCEPTION_CODE_0006, ExceptionContants.APPS_EXCEPTION_DESC_0006+e.getMessage());
		}
		
		return date;
	}
	/**
	 * 
	 * DESCRIPTION:根据yyyyMMddHHmmssSSS字符串获得yyyy-MM-dd HH:mm:ss格式日期串
	 * @author ZhouJing
	 * @date 2015年10月22日
	 * getDateStrByDate17 方法
	 * @param date17
	 * @return
	 * @param commonQueryVO
	 * @return
	 * @throws GateWayException 
	 */
	public static String getDateStrByDate17(String date17) throws GateWayException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date =getDateByDate17(date17);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 
	 * DESCRIPTION:校验字符串是否为空，为空返回true
	 * @author TongJie
	 * @date 2015年10月28日
	 * isEmpty 方法
	 * @param str
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static boolean isEmpty(String str)
	  {
	    return ((str == null) || (str.length() == 0));
	  }
	
	
	/**
	 * 
	 * DESCRIPTION:根据html模板的路径将map中的变量替换html模板定义的变量
	 * 				变量名称规则${***} 替换。
	 * @author ZhouJing
	 * @date 2015年11月20日
	 * getHtml5ByTemplate 方法
	 * @param path
	 * @param map
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getHtml5ByTemplate(String path,Map<String,String> map){
		String templateContent="";
		FileInputStream fileinputstream = null;
		try {
			
			fileinputstream = new FileInputStream(path);
		// 读取模板文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes,"UTF-8");
			
//			templateContent = HttpClientUtils.postHttp(path, "");
			map.put("random", UUID.randomUUID().toString());
			
			Iterator<String> iter = map.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next();
				String value = map.get(key);
				if(value == null){
					value = " ";
				}
				templateContent = templateContent.replace("${"+key+"}",value);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(null != fileinputstream){
				try {
					fileinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return templateContent;
	}
	
	public static Date getAddOrSubDate(int i){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, i);
		Date d = cal.getTime();
		return d ;
	}
	
	public static String getWeekByDate(Date date){
		String weekStr = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		
		switch (week) {
		case 1:
			weekStr = "星期天";
			break;
		case 2:
			weekStr = "星期一";
			break;
		case 3:
			weekStr = "星期二";
			break;
		case 4:
			weekStr = "星期三";
			break;
		case 5:
			weekStr = "星期四";
			break;
		case 6:
			weekStr = "星期五";
			break;
		case 7:
			weekStr = "星期六";
			break;
		default:
			break;
		}
		
		return weekStr;
	}
	
	/**
	 * 
	 * DESCRIPTION:
	 * @author ZhouJing
	 * @date 2015年12月4日
	 * getDateByStr10 方法
	 * @param str yyyy-MM-dd 格式
	 * @return
	 * @throws GateWayException
	 * @param commonQueryVO
	 * @return
	 */
	public static Date getDateByDate10(String str) throws GateWayException{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			throw new GateWayException(ExceptionContants.APPS_EXCEPTION_CODE_0006, ExceptionContants.APPS_EXCEPTION_DESC_0006+e.getMessage());
		}
		
		return date;
		
	}
	/**
	 * 
	 * DESCRIPTION:
	 * @author ZhouJing
	 * @date 2015年12月4日
	 * getDateByDate8 方法
	 * @param str yyyyMMdd
	 * @return
	 * @throws GateWayException
	 * @param commonQueryVO
	 * @return
	 */
	public static Date getDateByDate8(String str) throws GateWayException{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			throw new GateWayException(ExceptionContants.APPS_EXCEPTION_CODE_0006, ExceptionContants.APPS_EXCEPTION_DESC_0006+e.getMessage());
		}
		
		return date;
		
	}
	
	/**
	 * 
	 * DESCRIPTION:获取100,000格式金额
	 * @author ZhouJing
	 * @date 2015年12月4日
	 * getAmoutNum 方法
	 * @param amout
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getAmoutNum(String amout){
		char[] yuan = amout.split("\\.")[0].toCharArray();
		char[] yuanDao =new char[yuan.length];
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<yuan.length;i++){
			yuanDao[i]=yuan[yuan.length-i-1];
		}
		String fen = amout.split("\\.")[1];
		for(int i = 0;i<yuanDao.length;i++){
			if(i>0&&i%3==0){
				sb.append(",");
			}
			sb.append(yuanDao[i]);
		}
		char[] amoutNumDao = sb.toString().toCharArray();
		char[] amoutNum = new char[amoutNumDao.length];
		for(int i = 0;i<amoutNumDao.length;i++){
			amoutNum[i]=amoutNumDao[amoutNumDao.length-i-1];
		}
		
		return new String(amoutNum)+"."+fen;
	}
	
	public static String replaceStr(String oldStr){
		String newStr = oldStr.replaceAll("[^a-z^A-Z^0-9]", "");
		
		return newStr;
	}
	
	/**
	 * 
	 * DESCRIPTION:字段长度校验
	 * @author TongJie
	 * @date 2016年7月24日
	 * checkLenth 方法
	 * @param fieldName
	 *            字段中文描述
	 * @param fieldValue
	 *            字段值
	 * @param maxLength
	 *            字段最大长度(-1时不进行长度判断)
	 * @param allowNull
	 *            是否允许为空（true允许/false不允许）
	 * @throws GateWayException
	 * @param commonQueryVO
	 * @return
	 */
	public static String checkLenth(String fieldName, String fieldValue, int maxLength, boolean allowNull){
		String str = "-1";
		if (!allowNull && (fieldValue == null || "".equals(fieldValue))) {
			str =  "【" + fieldName + "】不允许为空";
		} else if (fieldValue != null && maxLength > 0 && fieldValue.length() > maxLength) {
			str = "【" + fieldName + "】长度不能超过" + maxLength +";实际长度【" + fieldValue.length() + "】";
		}
		return str;
	}
	
	/**
	 * 
	 * DESCRIPTION:获取dateTime后7位
	 * @author licundong
	 * @date 2016年12月11日
	 * getDate14 方法
	 * @return
	 */
	public static String getDateTime7(){
		long time = new Date().getTime();
		String last7 = (time+"").substring(6); 
		return last7;
	}
	
	/**
	 * 
	 * DESCRIPTION:获得yyyyMMddHHmmssSSS格式的字符串
	 * @author licundong
	 * @date 2015年10月22日
	 * getDate15 方法
	 * @return
	 * @param commonQueryVO
	 * @return
	 */
	public static String getDate15(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String date17 = sdf.format(new Date());
		if(date17.length() > 15){
			return date17.substring(0,15);
		}
		return date17;
	}
}
