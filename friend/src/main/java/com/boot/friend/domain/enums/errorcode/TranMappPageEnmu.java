package com.boot.friend.domain.enums.errorcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description <p>
 *              交易码映射跳转页面
 *              </p>
 * @author 213li
 * @date 2017年8月6日 下午6:13:39
 * @update
 */
public enum TranMappPageEnmu{
	/** 甜点页面："000000","app.html" **/
	APP("100000","/myres/app.html") , ;

	public String trancode;
	public String pageurl;

	private TranMappPageEnmu(String trancode, String pageurl) {
		this.trancode = trancode;
		this.pageurl = pageurl;
	}

	public static Map<String, TranMappPageEnmu> getData() {
		Map<String, TranMappPageEnmu> map = new HashMap<String, TranMappPageEnmu>();
		for (TranMappPageEnmu single : TranMappPageEnmu.values()) {
			map.put(single.trancode, single);
		}
		return map;
	}
}
