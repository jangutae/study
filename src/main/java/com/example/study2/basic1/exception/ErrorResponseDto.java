package com.example.study2.basic1.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto {

	private final HttpStatus httpStatus;
	private final String message;

	public static ErrorResponseDto toDto(UserException exception) {
		return new ErrorResponseDto(exception.getUserErrorCode().getErrorCode(),
			exception.getUserErrorCode().getErrorMessage());
	}

	public static HttpStatus toCode(UserException exception) {
		return exception.getUserErrorCode().getErrorCode();
	}

}
