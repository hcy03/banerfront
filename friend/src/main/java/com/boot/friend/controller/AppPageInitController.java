package com.boot.friend.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.friend.domain.TestDataConfig;

/**
 * @description <p>
 *              甜点主页初始数据
 *              </p>
 * @author 213li
 * @date 2017年8月5日 下午8:40:25
 * @update
 */
@RestController
public class AppPageInitController {

	private static final Logger log = LoggerFactory.getLogger(AppPageInitController.class);
	// TODO 测试数据
	@Autowired
	private TestDataConfig data;

	@RequestMapping("/initdata")
	public JsonData initdata(HttpServletRequest request) {
		JsonData jsonData = new JsonData();
		try {
			// TODO 这里应该是请求后台接口数据的，现在先做成挡板形式
			String initdata = null;
			if (null != this.data) {
				initdata = data.getInitdata();
				log.info("initdata ={}.", initdata);
			} else {
				log.info(" TestDataConfig is NULL");
			}
			// TODO 这里应该是请求后台接口数据的，现在先做成挡板形式
			jsonData.setInitdata(initdata);
		} catch (Exception e) {
			log.error("JSONException", e);
		}
		return jsonData;
	}

	class JsonData {

		private String initdata;

		public String getInitdata() {
			return initdata;
		}

		public void setInitdata(String initdata) {
			this.initdata = initdata;
		}

		@Override
		public String toString() {
			return "JsonData [initdata=" + initdata + "]";
		}
	}
}
