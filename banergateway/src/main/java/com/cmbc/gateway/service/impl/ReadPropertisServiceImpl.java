package com.cmbc.gateway.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.core.utils.PropertiesUtil;
import com.cmbc.gateway.core.utils.Utility;
import com.cmbc.gateway.exception.GateWayException;
import com.cmbc.gateway.service.ReadPropertisService;
@Slf4j
@Service
public class ReadPropertisServiceImpl implements ReadPropertisService {
	
	private static String alipayPropPath = "/alipay/";
	private static String wxPropPath = "/weixin/";
	private static String msfPropPath = "/msfpay/";
	private static String gatewayPropPath = "/gateway/conf/";
	@Override
	public String getPropertisInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, GateWayException {
		log.info("开始获取配置文件信息");
		//1.获取请求参数
		String fileName = request.getParameter("fileName");//获配置文件夹的名字
		String valueName = request.getParameter("valueName");//获取配置文件里面的参数名字
		
		log.info("===========fileName: "+fileName);
		log.info("===========valueName: "+valueName);
		if(fileName == null  || fileName.length()== 0 || valueName == null  || valueName.length() == 0){
			throw new GateWayException(GWExceptionConstants.REQ1000_CODE, GWExceptionConstants.REQ1000_DESC);
		}
		//2.解析参数名：
		JSONObject valueObj = JSONObject.parseObject(valueName);
		String  ios_agreement = valueObj.get("ios_agreement").toString();
		String  ios_download_uri = valueObj.get("ios_download_uri").toString();
		String  android_agreement = valueObj.get("android_agreement").toString();
		String  android_download_uri = valueObj.get("android_download_uri").toString();
		
		//3.配置读取路径
		String path = buildReadPropertisPath(fileName);
		
		//4.重新赋值(获取对应的参数值)
		ios_agreement = PropertiesUtil.readProperty(path,ios_agreement);
		ios_download_uri = PropertiesUtil.readProperty(path,ios_download_uri);
		android_agreement = PropertiesUtil.readProperty(path,android_agreement);
		android_download_uri = PropertiesUtil.readProperty(path,android_download_uri);
		if(ios_agreement == null || ios_download_uri == null || android_agreement == null||android_download_uri == null){
			throw new GateWayException("E999999999", "获取配置参数失败");
		}
		
		log.info("================ios_agreement: "+ ios_agreement);
		log.info("================ios_download_uri: "+ ios_download_uri);
		log.info("================android_agreement: "+ android_agreement);
		log.info("================android_download_uri: "+ android_download_uri);
		valueObj.put("ios_agreement", ios_agreement);
		valueObj.put("ios_download_uri", ios_download_uri);
		valueObj.put("android_agreement", "");
		valueObj.put("android_download_uri", android_download_uri);
		JSONObject value = new JSONObject();
		value.put("valueName", valueObj);
		//5.组建响应字符串
		String respMsg = JSONObject.toJSONString(value);
		log.info("配置文件获取的信息: "+respMsg);
		if(respMsg  == null){
			throw new GateWayException("E999999999","没有找到配置信息");
		}
		log.info("获取配置文件信息结束");
		return respMsg;
	}
	//构造读取配置文件路径
	public String buildReadPropertisPath(String fileName ){
		log.info("开始构建读取路径");
		StringBuffer path = null ;
		if(fileName.equals("alipay_per") || fileName.equals("alipay")){
			path =  new StringBuffer(alipayPropPath);
			path.append(fileName).append(".properties");
		}else if(fileName.equals("http") || fileName.equals("unhttp")){
			path =  new StringBuffer(gatewayPropPath);
			path.append(fileName).append(".properties");
		}else if(fileName.equals("msfpay")){
			path =  new StringBuffer(msfPropPath);
			path.append(fileName).append(".properties");
		}else{
			path =  new StringBuffer(wxPropPath);
			path.append(fileName).append(".properties");
		}
		log.info("被读取文件的路径："+path );
		log.info("构建读取路径结束");
		return path.toString();
		
	}

}
