package com.distribute.order.exception;

import com.distribute.common.enums.ErrorCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wly on 2018/8/26.
 */
@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Map<String,String> exceptionHandler(){
		Map<String,String> map = new HashMap<String, String>(  );
		map.put( "result", ErrorCode.SYS_EXCEPTION.getCode() );
		return map;
	}
}
