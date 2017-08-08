package com.cmbc.gateway.executor;

import com.cmbc.gateway.exception.GateWayException;

/**
 * 业务执行接口
 * @author licd
 *
 */
public interface GwActionExecutor {

	/**
	 * 业务执行方法
	 * 
	 * @return
	 */
	public String execute() throws GateWayException;

}
