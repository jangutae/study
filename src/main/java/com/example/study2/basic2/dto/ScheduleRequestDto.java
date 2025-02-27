package com.example.study2.basic2.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

	private final String task;

	private final String name;

	private final String password;

	private final LocalDateTime createdAt;

	private final LocalDateTime modifiedAt;

	public ScheduleRequestDto(String task, String name, String password, LocalDateTime createdAt,
		LocalDateTime modifiedAt) {
		this.task = task;
		this.name = name;
		this.password = password;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}
}
