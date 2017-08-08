package com.cmbc.gateway.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmbc.gateway.exception.GateWayException;


/**
 * 获取配置文件信息返回前端
 * @author chm
 *
 */


public interface ReadPropertisService {
	
	public String getPropertisInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, GateWayException;

}
