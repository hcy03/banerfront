package com.boot.friend.domain.enums.errorcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description <p>
 *              公共错误码
 *              </p>
 * @author 213li
 * @date 2017年8月6日 下午5:48:11
 * @update
 */
public enum CommonErrorCode{

	/** "S000000000","交易成功" **/
	SUCCESS("S000000000","交易成功") ,
	/** "E000000001","交易码为空" **/
	E000000001("E000000001","交易码为空") ,
	/** "E000000002","交易数据为空" **/
	E000000002("E000000002","交易数据为空") ,
	/** "E000000003","交易数据格式不正确" **/
	E000000003("E000000003","交易数据格式不正确") ,
	/** "E000000004","交易码不存在" **/
	E000000004("E000000004","交易码不存在") ,
	/** "E999999999","交易失败" **/
	ERROR("E999999999","交易失败") , ;

	public String errorcode;
	public String errormsg;

	private CommonErrorCode(String errorcode, String errormsg) {
		this.errorcode = errorcode;
		this.errormsg = errormsg;
	}

	public static Map<String, CommonErrorCode> getData() {
		Map<String, CommonErrorCode> map = new HashMap<String, CommonErrorCode>();
		for (CommonErrorCode single : CommonErrorCode.values()) {
			map.put(single.errorcode, single);
		}
		return map;
	}

}
