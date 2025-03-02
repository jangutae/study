package com.example.study2.basic1.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = {UserException.class})
	public ResponseEntity<ErrorResponseDto> handlerCustomException(UserException exception) {

		return ResponseEntity
			.status(ErrorResponseDto.toCode(exception))
			.body(ErrorResponseDto.toDto(exception));
	}
}
