package com.yupi.usercenter.common;

public class ResultUtils {

	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>(0, data, "ok", "");
	}

	public static BaseResponse error(ResultCode errorCode) {
		return new BaseResponse<>(errorCode);
	}
}
