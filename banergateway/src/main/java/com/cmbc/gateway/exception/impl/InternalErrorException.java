package com.cmbc.gateway.exception.impl;

import com.cmbc.gateway.exception.DataConsistencyException;


/**
 * 内部异常
 * 
 * <pre>
 * 划分标准：
 *         1.所有没有划归到其他部分的异常
 *         2.JDK自带异常的转义
 * </pre>
 */
@SuppressWarnings("serial")
public class InternalErrorException extends DataConsistencyException {

    /**
     * 内部异常的异常错误码
     */
    public static final String ERROR_CODE = "E999999";

    public InternalErrorException(String errorDetail) {
        super(ERROR_CODE, errorDetail);
    }

    public InternalErrorException(String errorDetail, Throwable cause) {
        super(ERROR_CODE, errorDetail, cause);
    }

}
