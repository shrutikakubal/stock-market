package com.example.sector.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String exception) {
		super(exception);
	}

	public ResourceNotFoundException() {
	}
}
