package com.cmbc.gateway.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.http.HttpClientTransport;
import com.cmbc.gateway.http.HttpPropertiesUtil;

@Slf4j
public class InGwNotifExecutor implements GwActionExecutor {
	
	/** 线程池 */
	private static ExecutorService executors = Executors.newSingleThreadExecutor();
	
	private String jsonParam;
	private HttpClientTransport httpClientTransport;// 发送http请求
	private HttpPropertiesUtil httpPropertiesUtil;
	
	public InGwNotifExecutor(String jsonParam,HttpPropertiesUtil httpPropertiesUtil,HttpClientTransport httpClientTransport) {
		this.jsonParam = jsonParam;
		this.httpPropertiesUtil = httpPropertiesUtil;
		this.httpClientTransport = httpClientTransport;
	}
	
	public InGwNotifExecutor(){
		super();
	}

	@Override
	public String execute() throws GateWayException {
		
		// 2. 异步发送消息
		executors.execute(new Runnable() {
			@Override
			public void run() {
				try {

					String param = httpPropertiesUtil.getParam() + jsonParam;
					log.info("请求发送数据param==========" + param);
					try {
						httpClientTransport.submit(httpPropertiesUtil.getUrl(), param);
						log.info("向业务系统传输数据完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					} catch (GateWayException ge) {
						log.error(ExceptionUtils.getFullStackTrace(ge));
					}
				} catch (Exception e) {
					log.error("异步发送交易系统通知失败", e);
				}
			}
		});

		// 3.返回同步的ACK响应
		return "";
	}

}
