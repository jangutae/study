package com.example.study2.basic1.exception;

public abstract class ParentException extends RuntimeException {

	public abstract ErrorResponseDto getErrorResponse();
}
