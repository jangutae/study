package com.example.study2.basic1.common;

import lombok.Getter;

@Getter
public class CommonResponseBody<T> {

	private final String message;

	private final T response;


	public CommonResponseBody(String message, T response) {
		this.message = message;
		this.response = response;
	}


	public CommonResponseBody(String message) {
		this(message, null);
	}
}
