package com.example.excel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exc) {
		ErrorResponse errorResponse = new ErrorResponse(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
