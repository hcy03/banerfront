package com.boot.friend.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.friend.domain.TestDataConfig;

@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	@Value(value = "${customs.static-locations}")
	private String locations;

	@Autowired
	private TestDataConfig data;

	@RequestMapping("/test")
	public String service(HttpServletRequest request) {

		String bcd = request.getParameter("bcd");
		log.info("bcd:" + bcd);

		JSONObject obj = new JSONObject();
		try {
			obj.put("message", "msg");
			if (null != this.data)
				obj.put("testdata", data.getInitdata());
			else
				log.info(" TestDataConfig is NULL");
		} catch (JSONException e) {
			log.error("JSONException", e);
		}
		return obj.toString();
	}
	
}
