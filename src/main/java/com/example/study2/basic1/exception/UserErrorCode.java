package com.example.study2.basic1.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다."),

	NOT_EXIST_SESSION(HttpStatus.BAD_REQUEST, "세션이 존재하지 않습니다."),

	ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 유저입니다.");

	private final HttpStatus errorCode;
	private final String errorMessage;
}
