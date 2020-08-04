package com.example.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleException(AuthenticationException exc) {
		ErrorResponse errorResponse = new ErrorResponse(exc.getMessage(), HttpStatus.FORBIDDEN.value(),
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exc) {
		ErrorResponse errorResponse = new ErrorResponse(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
