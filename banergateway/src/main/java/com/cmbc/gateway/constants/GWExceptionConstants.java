package com.cmbc.gateway.constants;

public class GWExceptionConstants {

	// 安全异常
	public static final String SET0001_CODE = "SET0001";
	public static final String SET0001_DESC = "安全异常: 请求数据安全校验未通过";
	
	// WebService异常
	public static final String WS0001_CODE = "WS0001";
	public static final String WS0001_DESC = "后台服务不可用, 错误码0001";
//	public static final String WS0001_DESC = "服务端返回无法被解析的xml/html数据格式";
	public static final String WS0002_CODE = "WS0002";
	public static final String WS0002_DESC = "无法向后台服务器发送数据(服务器不可用)，请稍后再试";
	public static final String WS0003_CODE = "WS0003";
	public static final String WS0003_DESC = "发送WebService请求异常: ";
	// 系统异常
	public static final String HTTP_DESC = "http请求异常";
	public static final String SYS0001_CODE = "SYS0001";
	public static final String SYS0001_DESC = "系统异常: ";
	public static final String SYS0002_CODE = "SYS0002";
	public static final String SYS0002_DESC = "HttpPost请求异常: ";
	public static final String SYS1001_CODE = "SYS1001";
	public static final String SYS1001_DESC = "获取图形验证码失败!";
	public static final String SYS1002_CODE = "SYS1002";
	public static final String SYS1002_DESC = "日期字符串格式错误!";
	public static final String SYS1003_CODE = "SYS1002";
	public static final String SYS1003_DESC = "获取随机数失败!";

	// 文件上传相关的错误
	public static final String UPLD0000_CODE = "UPLD0000";
	public static final String UPLD0000_DESC = "文件上传异常: ";
	public static final String UPLD0001_CODE = "UPLD0001";
	public static final String UPLD0001_DESC = "文件上传大小超出限制";
	// 请求相关的错误
	public static final String REQ0000_CODE = "REQ0000";
	public static final String REQ0000_DESC = "请求错误: ";
	public static final String REQ0001_CODE = "REQ0001";
	public static final String REQ0001_DESC = "报文转换错误: ";
	public static final String REQ0002_CODE = "REQ0002";
	public static final String REQ0002_DESC = "图形验证码验证未通过";
	public static final String REQ0003_CODE = "REQ0003";
	public static final String REQ0003_DESC = "form请求编码格式错误";
	public static final String REQ0010_CODE = "REQ0010";
	public static final String REQ0010_DESC = "请求参数[{0}]不合法!";
	public static final String REQ0011_CODE = "REQ0011";
	public static final String REQ0011_DESC = "请求参数[{0}]格式不正确!";
	public static final String REQ1001_CODE = "REQ1001";
	public static final String REQ1001_DESC = "图形验证码不存在!";
	public static final String REQ1002_CODE = "REQ1002";
	public static final String REQ1002_DESC = "随机数不存在!";
	public static final String REQ1003_CODE = "REQ1003";
	public static final String REQ1003_DESC = "随机数不正确!";
	public static final String REQ1004_CODE = "REQ1004";
	public static final String REQ1004_DESC = "密钥不存在!";
	public static final String REQ1005_CODE = "REQ1005";
	public static final String REQ1005_DESC = "会话信息不存在!";
	public static final String REQ1011_CODE = "REQ1011";
	public static final String REQ1011_DESC = "图形验证码已失效!";
	public static final String REQ1012_CODE = "REQ1012";
	public static final String REQ1012_DESC = "随机数已失效!";
	public static final String REQ1013_CODE = "REQ1013";
	public static final String REQ1013_DESC = "图形验证码已过期!";
	public static final String REQ1014_CODE = "REQ1014";
	public static final String REQ1014_DESC = "随机数已过期!";
	public static final String REQ1015_CODE = "REQ1015";
	public static final String REQ1015_DESC = "sessionId已过期!";

	public static final String REQ1000_CODE = "REQ1000";
	public static final String REQ1000_DESC = "参数[{0}]不能为空";
	public static final String REQ1100_CODE = "REQ1001";
	public static final String REQ1100_DESC = "请求未包含字段[{0}]";
	// 关联参数不正确
	public static final String REQ2000_CODE = "REQ2000";
	public static final String REQ2000_DESC = "[{0}]为[{1}]时[{2}]不能为空";

	public static final String GW0000_CODE = "GW0000";
	public static final String GW0000_DESC = "网关服务器忙";

	// 业务错误
	public static final String GW0001_CODE = "GW0001";
	public static final String GW0001_DESC = "解析多层报文异常";
	public static final String GW0002_CODE = "GW0002";
	public static final String GW0002_DESC = "token不存在";
	public static final String GW0003_CODE = "GW0003";
	public static final String GW0003_DESC = "token校验未通过";
	public static final String GW0004_CODE = "GW0004";
	public static final String GW0004_DESC = "token已失效";
	public static final String GW0005_CODE = "GW0005";
	public static final String GW0005_DESC = "token更新失败";
	public static final String GW0006_CODE = "GW0006";
	public static final String GW0006_DESC = "token未生效";
	public static final String GW0007_CODE = "GW0007";
	public static final String GW0007_DESC = "未找到最新版本信息";
	public static final String GW0008_CODE = "GW0008";
	public static final String GW0008_DESC = "token异常, 请重新登录";
	public static final String GW1000_CODE = "GW1000";
	public static final String GW1000_DESC = "加解密错误";
	
	// APP扫码错误
	public static final String APPWEB0000_CODE = "APPWEB0000";
	public static final String APPWEB0000_DESC = "下单失败";
	
	public static final String APPS_EXCEPTION_CODE_0002 = "APPWEB0002";
	public static final String APPS_EXCEPTION_CODE_0002_DES = "mkey为空";
	
	public static final String APPS_EXCEPTION_CODE_0003 = "APPWEB0003";
	public static final String APPS_EXCEPTION_CODE_0003_DES = "mkey格式类型错误";

}
