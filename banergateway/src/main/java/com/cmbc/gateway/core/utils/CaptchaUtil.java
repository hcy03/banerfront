package com.cmbc.gateway.core.utils;

import java.awt.Font;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @author fairy
 * 
 */
public class CaptchaUtil {
	public static final Font mFont = new Font("Arial Black", Font.PLAIN, 20);
	public static final int IMG_WIDTH = 80;
	public static final int IMG_HEIGTH = 25;
	public static final int CODE_LENGTH = 6;
	public static final String SIMPLE_CAPCHA_SESSION_KEY = "SIMPLE_CAPCHA_SESSION_KEY";
	public static char captchars[] = { 'a', 'b', 'c', 'd', 'e', '2', '3', '4',
			'5', '6', '7', '8', 'g', 'f', 'y', 'n', 'm', 'n', 'p', 'w', 'x' };

	public static boolean check(String vcode, Map<?, ?> session) {
		if (null != session) {
			String captchaText = ((String) session
					.get(SIMPLE_CAPCHA_SESSION_KEY));
			if (null != captchaText && null != vcode
					&& (captchaText.toLowerCase()).equals(vcode.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean check(String vcode, HttpSession session) {

		String captchaText = (String) session
				.getAttribute(SIMPLE_CAPCHA_SESSION_KEY);
		if (session != null) {
			if (null != captchaText && null != vcode
					&& (captchaText.toLowerCase()).equals(vcode.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
