package com.boot.friend.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @description <p>
 *              外部访问入口
 *              </p>
 * @author 213li
 * @date 2017年8月6日 下午5:27:59
 * @update
 */
public interface TranService {

	public ModelAndView tranService(HttpServletRequest request);
}
