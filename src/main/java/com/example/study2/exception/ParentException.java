package com.example.study2.exception;

import org.springframework.http.HttpStatus;

public abstract class ParentException extends RuntimeException {

	public abstract ErrorResponseDto getErrorResponse();
}
