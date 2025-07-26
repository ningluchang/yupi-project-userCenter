package com.yupi.usercenter.exception;

import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultCode;
import com.yupi.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public <T> BaseResponse<T> businessExceptionHandler(BusinessException e){
		log.error("businessException:{}", e.getMessage(), e);
		return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
	}

	@ExceptionHandler(RuntimeException.class)
	public <T> BaseResponse<T> runtimeExceptionHandler(BusinessException e){
		log.error("runtimeException",e);
		return ResultUtils.error(ResultCode.INTERNAL_SERVER_ERROR,e.getMessage(),"");

	}
}
