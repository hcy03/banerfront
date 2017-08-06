package com.boot.friend.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.friend.service.TranService;

@RestController
public class TranServiceController {

	private static final Logger log = LoggerFactory.getLogger(TranServiceController.class);
	@Autowired
	private TranService tranService;

	@RequestMapping("/service")
	public ModelAndView service(HttpServletRequest request) {
		log.info("this TranServiceController.");
		return tranService.tranService(request);
	}

}
