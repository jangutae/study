package com.example.study2.basic2.dto;

import java.time.LocalDateTime;

import com.example.study2.basic2.entity.Schedule;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {

	private final Long id;

	private final String task;

	private final String name;

	private final String password;

	private final LocalDateTime createdAt;

	private final LocalDateTime modifiedAt;

	public ScheduleResponseDto(Long id, String task, String name, String password, LocalDateTime createdAt,
		LocalDateTime modifiedAt) {
		this.id = id;
		this.task = task;
		this.name = name;
		this.password = password;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static ScheduleResponseDto toDto(Schedule schedule) {
		return new ScheduleResponseDto(
			schedule.getId(),
			schedule.getTask(),
			schedule.getName(),
			schedule.getPassword(),
			schedule.getCreatedAt(),
			schedule.getModifiedAt());
	}

	public static ScheduleResponseDto toDto(Long id, Schedule schedule) {
		return new ScheduleResponseDto(
			id,
			schedule.getTask(),
			schedule.getName(),
			schedule.getPassword(),
			schedule.getCreatedAt(),
			schedule.getModifiedAt());
	}
}
