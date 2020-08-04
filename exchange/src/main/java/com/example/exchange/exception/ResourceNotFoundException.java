package com.example.exchange.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String exception) {
		super(exception);
	}

	public ResourceNotFoundException() {
	}

}
