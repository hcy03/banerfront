package com.boot.friend.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranServiceController {

	private static final Logger log = LoggerFactory.getLogger(TranServiceController.class);

	@RequestMapping("/service")
	public String service(HttpServletRequest request) {
		log.info("this service");
		return "this service";
	}

}
