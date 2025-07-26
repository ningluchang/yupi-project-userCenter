package com.yupi.usercenter.common;

public enum ResultCode {
	SUCCESS(200, "操作成功",""),
	PARAMS_ERROR(40000, "参数错误",""),
	NOT_FOUND(404, "资源不存在",""),
	NULL_ERROR(40001, "请求参数为空",""),
	INTERNAL_SERVER_ERROR(500, "服务器错误",""),
	NO_AUTH(40100, "未授权",""),
	NOT_LOGIN(40101, "未登录","");

	ResultCode(Integer code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	private final Integer code;

	public String getDescription() {
		return description;
	}

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

	private final String message;
	private final String description;
}
