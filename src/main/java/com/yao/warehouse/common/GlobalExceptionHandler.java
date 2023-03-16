package com.yao.warehouse.common;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	//

	// 实体校验异常捕获
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public R handler(MethodArgumentNotValidException e) {

		BindingResult result = e.getBindingResult();
		ObjectError objectError = result.getAllErrors().stream().findFirst().get();

		log.error("实体校验异常：----------------{}", objectError.getDefaultMessage());
		return R.error(objectError.getDefaultMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = IllegalArgumentException.class)
	public R handler(IllegalArgumentException e) {
		log.error("Assert异常：----------------{}", e.getMessage());
		return R.error(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = RuntimeException.class)
	public R handler(RuntimeException e) {
		log.error("运行时异常：----------------{}", e.getMessage());
		return R.error(e.getMessage());
	}

}
