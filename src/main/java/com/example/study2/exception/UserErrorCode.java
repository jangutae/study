package com.example.study2.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다.");

	private final HttpStatus errorCode;
	private final String errorMessage;
}
