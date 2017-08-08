package com.cmbc.gateway.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cmbc.gateway.core.utils.Utility;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.ReadPropertisService;

/**
 * 处理前端页面请求返回配置信息
 * @author chm
 *
 */
@Slf4j
@Controller
public class ReadPropertisController {
	
	@Resource
	private ReadPropertisService readPropertisService;
	
	@RequestMapping(value = "/readPropertis.do", method = RequestMethod.POST)
	public void getPropertisInfo(HttpServletRequest request, HttpServletResponse response)throws IOException,GateWayException{
		log.info("---------readPropertis.do  start---------->");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String respMsg = null;
		try {
			 respMsg = readPropertisService.getPropertisInfo(request, response);
		} catch (GateWayException e) {
			log.info("获取配置信息失败");
			log.info(e.getCode(), e.getMessage());
		}catch (IOException e){
			e.printStackTrace();
			log.info(e.getMessage());
			log.error("respWriter 111 error !!!", e);
		}finally{			
			if(respMsg ==  null){
				out.print("获取配置信息失败");
				out.flush();
				out.close();
			}else{	
				log.info("PrintWriter out str : " + respMsg);
				out.print(respMsg);
				out.flush();
				out.close();
			}
		}
		log.info("---------readPropertis.do  end---------->");
	}

}
