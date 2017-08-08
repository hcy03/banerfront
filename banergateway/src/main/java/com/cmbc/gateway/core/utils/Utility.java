package com.cmbc.gateway.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class Utility {

	public static String toNull(String str) {
		if (str == null || "null".equals(str) || str.equals(null)) {
			return "";
		}
		return str;
	}

	public static String formatString(String value) {
		if (isEmpty(value)) {
			return "";
		}
		return value;
	}

	public static Integer[] toInt(String... values) {
		if (null != values) {
			Integer[] is = new Integer[values.length];
			int i = 0;
			for (String s : values) {
				if (canToInt(s))
					is[i] = Integer.valueOf(s);
				i++;
			}
			return is;
		}
		return new Integer[0];
	}

	public static Long[] toLong(String... values) {
		if (null != values) {
			List<Long> list = new ArrayList<Long>();
			for (String s : values) {
				if (canToLong(s)) {
					list.add(Long.valueOf(s));
				}
			}
			return list.toArray(new Long[list.size()]);
		}
		return new Long[0];
	}

	public static Integer toInt(String value, Integer defaultValue) {
		try {
			if (canToInt(value)) {
				return Integer.valueOf(value);
			}
			return defaultValue;
		} catch (Exception e) {
			return defaultValue;
		}

	}

	public static boolean canToDouble(String value) {
		if (isEmpty(value)) {
			return false;
		}
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Double toDouble(String value, Double defaultValue) {
		try {
			if (canToDouble(value)) {
				return Double.valueOf(value);
			}
			return defaultValue;
		} catch (Exception e) {
			return defaultValue;
		}

	}

	public static Long toLong(String value, Long defaultValue) {
		if (canToLong(value)) {
			return Long.valueOf(value);
		}
		return defaultValue;
	}

	public static boolean isEmpty(String s) {
		return (s == null) || (s.trim().length() == 0);
	}

	public static boolean isNotEmpty(String s) {
		if ((StringUtils.isEmpty(s)) || StringUtils.isBlank(s))
			return false;
		return true;
	}

	public static String md5(String s) throws Exception {
		if (isEmpty(s))
			throw new Exception();

		return byte2hex(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")));
	}

	/**
	 * Md5加密, Mao+, 2015-07-30
	 * 
	 * @param s
	 *            (String) 要加密的串
	 * @param def
	 *            (String) 出错时的默认值
	 */
	public static String md5(String s, String def) {
		if (isEmpty(s))
			return def;
		try {
			def = md5(s);
		} catch (Exception e) {
		}
		return def;
	}

	public static Date parseDate(String date, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(date);
	}

	/**
	 * 将字符串转换成时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @param defaultDate
	 *            转换出错返回的值
	 * @return
	 */
	public static Date parseDate(String dateStr, String format, Date defaultDate) {
		try {
			if (!isStrNotNull(format)) {
				format = "yyyy-MM-dd HH:mm:ss";
			}
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (Exception e) {
			return defaultDate;
		}
	}

	/**
	 * 将date类型转成字符串
	 * 
	 * @param format
	 * @param date
	 * @param defaultStr
	 *            转换出错默认的字符串
	 * @return
	 */
	public static String toDate(String format, Date date, String defaultStr) {
		if (!isStrNotNull(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return defaultStr;
		}

	}

	public static String toDate(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 根据时间返回时但年的第几周
	 * 
	 * @param date
	 * @param defaultWeek
	 * @return
	 */
	public static int getDateWeek(Date date, int defaultWeek) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
			return calendar.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			return defaultWeek;
		}

	}

	public static String getNowDateLogger() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public static String getDateFormat(Date date, String format) {
		SimpleDateFormat datetimeFormat = (SimpleDateFormat) DateFormat.getDateTimeInstance();
		datetimeFormat.applyPattern(format);
		return datetimeFormat.format(date);
	}

	public static boolean canToInt(String value) {
		if (isEmpty(value)) {
			return false;
		}
		return value.matches("^[0-9]{1,9}$");
	}

	public static boolean canToLong(String value) {
		if (isEmpty(value)) {
			return false;
		}
		return value.matches("^[0-9]{1,20}$");
	}

	public static String formatMoney(int money) {
		String yuan = String.valueOf(money / 100);
		String fen = String.valueOf(money % 100);
		if (fen.length() == 1)
			fen = "0" + fen;
		return yuan + "." + fen;
	}

	public static String formatMoney(Long money) {
		String yuan = String.valueOf(money / 100);
		String fen = String.valueOf(money % 100);
		if (fen.length() == 1)
			fen = "0" + fen;
		return yuan + "." + fen;
	}

	public static boolean isForbitTime(String startTime, String endTime) {

		String temp = new SimpleDateFormat("HHmmss").format(new Date());
		int current = Integer.valueOf(temp);
		int start = Integer.valueOf(startTime.replace(":", ""));
		int end = Integer.valueOf(endTime.replace(":", ""));

		if (start >= end) {
			if (current >= start || current < end)
				return true;
			else
				return false;
		}

		else {
			if (current >= start && current < end)
				return true;
			else
				return false;
		}

	}

	public static String encodeWithHex(String s) {
		if (s == null)
			return null;
		try {
			return byte2hex(s.getBytes("UTF-8"));
		} catch (Throwable e) {
			return null;
		}
	}

	public static String decodeWithHex(String s) {
		if (s == null)
			return null;

		try {
			return new String(hex2byte(s), "UTF-8");
		} catch (Throwable e) {
			return null;
		}
	}

	public static String byte2hex(byte[] bytes) {
		StringBuffer result = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			String b = Integer.toHexString(bytes[i] & 0xFF);
			if (b.length() == 1)
				b = "0" + b;
			result.append(b);
		}
		return result.toString().toUpperCase();
	}

	public static byte[] hex2byte(String strings) {
		if (strings.length() % 2 != 0)
			strings += "0";
		byte[] bytes = new byte[strings.length() / 2];
		byte[] sByte = strings.getBytes();
		byte hi = 0;
		int lo = 0;
		for (int i = 0; i < sByte.length; i = i + 2) {
			hi = (byte) (Integer.parseInt(("" + strings.charAt(i)), 16) * 16);
			lo = (byte) (Integer.parseInt(("" + strings.charAt(i + 1)), 16));
			bytes[i / 2] = (byte) (hi + lo);
		}
		return bytes;
	}

	public static int toMoney(String money) {

		String[] temp = money.split("\\.");
		String str = "";
		int s = 0;
		s = Integer.parseInt(temp[0]) * 100;
		if (temp.length > 1) {
			if (temp[1].length() == 1) {
				str = temp[1] + "0";
				s += Integer.valueOf(str);
			} else if (temp[1].length() == 2) {
				str = temp[1];
				s += Integer.valueOf(str);
			} else if (temp[1].length() > 2) {
				str = String.valueOf(temp[1].substring(0, 2));
				s += Integer.valueOf(str);
			}
		}

		return s;
	}

	public static Object clone(Object formObject, Object toObject) {
		try {
			BeanUtils.copyProperties(toObject, formObject);
		} catch (Exception e) {
			throw new RuntimeException("clone异常", e);
		}
		return toObject;
	}

	// toString
	//	public static String toString(Object vo) {
	//		return ReflectionToStringBuilder.toString(vo);
	//	}

	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_note_tag;
		java.util.regex.Matcher m_note_tag;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{憿script>]*?>[\s\S]*?<\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{憿style>]*?>[\s\S]*?<\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			String regEx_note = "(<(xml)[^>]*>.*<\\/(xml)>)|(<!*[\\s\\S]*?-->)";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_note_tag = Pattern.compile(regEx_note, Pattern.CASE_INSENSITIVE);
			m_note_tag = p_note_tag.matcher(htmlStr);
			htmlStr = m_note_tag.replaceAll(""); // 过滤xml和注释

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;
	}

	public static String makeRandomNum(int length) {
		String result = "";
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			result = r.nextInt(10) + result;
		}
		return result;
	}

	public static Date getNowTime() {
		return Calendar.getInstance().getTime();
	}

	public static boolean isMobile(String mobile) {
		String regExp = "^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{9}$|17[0-9]{1}[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobile);
		return m.find();
	}

	public static boolean isEmail(String email) {
		String regExp = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(email);
		return m.find();
	}

	public static String makeRandomStr(int length) {
		String result = "";
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		char[] c = s.toCharArray();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			result = result + c[random.nextInt(c.length)];
		}
		return result;
	}

	public static boolean hasA_Z(String test) {
		if (isEmpty(test)) {
			return false;
		}
		String letter = "^*([A-Z]+)";
		Pattern p = Pattern.compile(letter);
		Matcher m = p.matcher(test);
		return m.find();
	}

	public static boolean hasa_z(String test) {
		if (isEmpty(test)) {
			return false;
		}
		String letter = "^*([a-z]+)";
		Pattern p = Pattern.compile(letter);
		Matcher m = p.matcher(test);
		return m.find();
	}

	public static boolean hasNum(String test) {
		if (isEmpty(test)) {
			return false;
		}
		String letter = "^*([\\d]+)";
		Pattern p = Pattern.compile(letter);
		Matcher m = p.matcher(test);
		return m.find();
	}

	public static boolean hasSymbol(String test) {
		if (isEmpty(test)) {
			return false;
		}
		String letter = "^*(\\p{Punct}+)";
		Pattern p = Pattern.compile(letter);
		Matcher m = p.matcher(test);
		return m.find();
	}

	/**
	 * 字符串是否不为空
	 * 
	 * @param str
	 * @return 不为空返回真
	 */
	public static boolean isStrNotNull(String str) {
		try {
			return (str != null && !"".equals(str));
		} catch (Exception e) {
			return false;
		}

	}

	public static Object clone(Object source) throws IOException, ClassNotFoundException {
		Object dc = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(source);
		oos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream bis = new ObjectInputStream(bais);
		dc = bis.readObject();
		return dc;
	}

	/**
	 * 用指定分割符分割字符串
	 * 
	 * @param str
	 *            待分割的字符串
	 * @param separator
	 *            分割符
	 * @return List
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List segmentationStr(String str, String separator) {
		List result = new ArrayList();
		StringTokenizer token = new StringTokenizer(str, separator);
		while (token.hasMoreElements()) {
			result.add(token.nextToken());
		}
		return result;
	}

	/**
	 * 取得距离当前时间day天的时间的字符串形式
	 * 
	 * @param day
	 * @return
	 */
	public static String getNow(int day) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar right = Calendar.getInstance();
		right.add(Calendar.DAY_OF_MONTH, -day);
		Date date = right.getTime();
		return formatter.format(date);
	}

	/**
	 * 取得距离当前时间month的时间的字符串形式
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonth(int month) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar right = Calendar.getInstance();
		right.add(Calendar.MONTH, -month);
		Date date = right.getTime();
		return formatter.format(date);
	}

	/**
	 * 取得距离当前时间minute的时间的字符串形式
	 * 
	 * @param month
	 * @return
	 */
	public static String getMinute(Date myDate, int minute) {
		// 60*60*24*3 添加三天以后的时间
		long myTime = (myDate.getTime() / 1000) + 60 * minute;
		myDate.setTime(myTime * 1000);
		return Utility.getDateFormat(myDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 取得距离当前时间second的时间的字符串形式
	 * 
	 * @param second
	 * @return
	 */
	public static Date getSecondDate(Date myDate, int second) {
		// 60*60*24*3 添加秒以后的时间
		long myTime = (myDate.getTime() / 1000) + second;
		myDate.setTime(myTime * 1000);
		return myDate;
	}

	/**
	 * 取得距离当前时间second的时间的字符串形式
	 * 
	 * @param second
	 * @return
	 */
	public static Date getSecondDate(long second) {
		Date myDate = new Date();
		long myTime = (myDate.getTime() / 1000) + second;
		myDate.setTime(myTime * 1000);
		return myDate;
	}

	/**
	 * 根据strdate生成时间(strdate为时间格式字符串 yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param strdate
	 * @return
	 */
	public static Date strtodate(String strdate, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getFileMD5(String filename) {
		String str = "";
		try {
			str = getHash(filename, "MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getFileSHA1(String filename) {
		String str = "";
		try {
			str = getHash(filename, "SHA1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getFileSHA256(String filename) {
		String str = "";
		try {
			str = getHash(filename, "SHA-256");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getFileSHA384(String filename) {
		String str = "";
		try {
			str = getHash(filename, "SHA-384");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getFileSHA512(String filename) {
		String str = "";
		try {
			str = getHash(filename, "SHA-512");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	private static String getHash(String fileName, String hashType) throws Exception {
		InputStream fis = new FileInputStream(fileName);
		byte[] buffer = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0)
			md5.update(buffer, 0, numRead);

		fis.close();
		return toHexString(md5.digest());
	}

	public static String getSHA1(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(value.getBytes());
			String result = toHexString(md.digest());
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; ++i) {
			sb.append(hexChar[((b[i] & 0xF0) >>> 4)]);
			sb.append(hexChar[(b[i] & 0xF)]);
		}
		return sb.toString();
	}

	public static String arrayToString(Long[] values) {
		String result = "";
		if (values == null) {
			return result;
		} else {
			for (Long val : values) {
				result += val + ",";
			}
		}
		if (result.length() > 1) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String removeSpace(String ss) {
		byte[] t = ss.getBytes();
		for (int i = 0; i < t.length; i++) {
			if (i + 1 >= t.length) {
				break;
			}
			if (t[i] == -95 && t[i + 1] == -95) {
				t[i] = 32;
				t[i + 1] = 32;
				i++;
			}
		}
		return (new String(t)).trim().replaceAll(" ", "");
	}

	public static String readProperties(String fileName, String propertyName) {

		Properties p = new Properties();

		InputStream is = Utility.class.getResourceAsStream("/" + fileName + ".properties");

		try {
			p.load(is);
			is.close();
		} catch (Exception e) {
			return "";
		}

		return p.getProperty(propertyName);
	}

	/**
	 * 获得一个6为随机验证码
	 * 
	 * @return String UUID
	 */
	public static String getAuth() {
		//字母+数字
		//String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		//return s.substring(0, 6);
		//6位数字
		Random random = new Random();
		int x = random.nextInt(8999);
		x = x + 1000;
		return x + "";
	}

	/**
	 * 根据日期计算年龄
	 * 
	 * @param birthDay
	 * @return
	 */
	public static Integer getAge(String birthday) {
		int age = 0;
		try {
			Calendar cal = Calendar.getInstance();
			Date birthDay = Utility.parseDate(birthday, "yyyy-MM-dd", null);
			if (birthDay == null) {
				return age;
			}
			if (cal.before(birthDay)) {
				return age;
			}

			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

			cal.setTime(birthDay);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

			age = yearNow - yearBirth;
			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {
					age--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return age;
	}

	/**
	 * 生成随机token
	 * 
	 * @param userStr
	 * @return
	 */
	public static String buildToken() {
		try {
			int seek = (int) ((Math.random() * 9 + 1) * 100000);
			String token = String.valueOf(System.currentTimeMillis()) + String.valueOf(seek);
			return token;
		} catch (Exception e) {
		}
		return "";
	}

	/***
	 * 格式化字节
	 * 
	 * @return
	 */
	public static String formatSize(String byteSize) {
		String m = new java.text.DecimalFormat("######0.0M").format((Double.valueOf(byteSize) / 1024 / 1024));
		return m;
	}

	/**
	 * 根据概率跳转
	 * 
	 * @param protal
	 * @param shop
	 * @return 0：portal 1：shop
	 */
	public static int redirectProb(int protal, int shop) {
		Random random = new Random();
		int x = random.nextInt(protal + shop);
		if (x < protal) {
			return 0;
		}
		return 1;
	}

	public static void main(String[] args) {
		//log.info(redirectProb(AuthConstant.PROTAL_PROB, AuthConstant.SHOP_PROB));
		//		Date d = Utility.getSecondDate(CacheConstant.INDEX_SWITCHABLE_PAST_TIME);
		//		String date = DateUtil.dateToString(d, "yyyy-MM-dd HH:mm:ss");
		//		log.info(date);
	}

}
