package com.example.study2.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserException extends ParentException {

	private final UserErrorCode userErrorCode;

	public UserException(UserErrorCode errorCode) {
		this.userErrorCode = errorCode;
	}

	@Override
	public ErrorResponseDto getErrorResponse() {
		return ErrorResponseDto.builder()
			.httpStatus(userErrorCode.getErrorCode())
			.message(userErrorCode.getErrorMessage())
			.build();
	}
}
