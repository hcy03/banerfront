package com.cmbc.gateway.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.cmbc.gateway.exception.GateWayException;

@Slf4j
@Service("wxPageJumpService")
public class WxPageJumpService {

	public void sendWxRequest(HttpServletRequest request, HttpServletResponse response) throws GateWayException {

		try {
			response.sendRedirect("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jumpPage(HttpServletRequest request, HttpServletResponse response) throws GateWayException {
		try {
			response.sendRedirect("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
