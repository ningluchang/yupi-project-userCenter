package com.yupi.usercenter.exception;

import com.yupi.usercenter.common.ResultCode;

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException{
	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	private final Integer code;
	private final String description;

	public BusinessException(String message,Integer code, String description) {
		super(message);
		this.code = code;
		this.description = description;
	}
	public BusinessException(ResultCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.description = errorCode.getDescription();
	}
	public BusinessException(ResultCode errorCode,String description) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.description = description;
	}
}
