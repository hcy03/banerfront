package com.cmbc.gateway.exception;

/**
 * 平台异常
 */
@SuppressWarnings("serial")
public abstract class PlatformException extends RuntimeException {

    /**
     * 错误码
     */
    protected String errorCode;

    /**
     * 错误详情
     */
    protected String errorDetail;

    /**
     * 异常错误码
     */
    public static final String ERROR_CODE = "E999999";

    /**
     * 异常错误信息
     */
    public static final String ERROR_MSG = "\u64CD\u4F5C\u5931\u8D25!";

    public PlatformException(String errorCode, String errorDetail) {
        super(errorDetail);
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    public PlatformException(String errorCode, String errorDetail, Throwable cause) {
        super(errorDetail, cause);
        this.errorCode = errorCode;
        this.errorDetail = errorDetail;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

}
