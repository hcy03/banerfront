package com.boot.friend.untils;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || str.equals("") || str.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}
