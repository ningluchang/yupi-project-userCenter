package com.yupi.usercenter.common;

public enum ResultCode {
	SUCCESS(200, "操作成功"),
	BAD_REQUEST(400, "参数错误"),
	NOT_FOUND(404, "资源不存在"),
	INTERNAL_SERVER_ERROR(500, "服务器错误"),
	UNAUTHORIZED(401, "未授权"),
	FORBIDDEN(403, "禁止访问");

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	private final Integer code;

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

	private final String message;
}
