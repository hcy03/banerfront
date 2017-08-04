package com.boot.friend.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	@Value(value = "${customs.static-locations}")
	private String locations;

	@RequestMapping("/test")
	public String service(HttpServletRequest request, @RequestParam("abc") String abc) {
		log.info("abc:" + abc);

		String bcd = request.getParameter("bcd");
		log.info("bcd:" + bcd);

		JSONObject obj = new JSONObject();
		try {
			obj.put("code", -1002);
			obj.put("message", "msg");
			obj.put("customs.static-locations", locations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
}
