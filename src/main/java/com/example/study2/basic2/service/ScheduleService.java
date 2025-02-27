package com.example.study2.basic2.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.study2.basic2.dto.ScheduleRequestDto;
import com.example.study2.basic2.dto.ScheduleResponseDto;
import com.example.study2.basic2.entity.Schedule;
import com.example.study2.basic2.repository.ScheduleRepository;

@Service
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;

	public ScheduleService(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	public ScheduleResponseDto createdSchedule(ScheduleRequestDto requestDto) {

		Schedule schedule = Schedule.builder()
			.task(requestDto.getTask())
			.name(requestDto.getName())
			.password(requestDto.getPassword())
			.createdAt(requestDto.getCreatedAt())
			.modifiedAt(requestDto.getModifiedAt()).build();

		return scheduleRepository.savedSchedule(schedule);
	}

	public List<ScheduleResponseDto> getAllSchedules(String workName, LocalDateTime modifiedAt) {
		return scheduleRepository.getAllSchedules(workName, modifiedAt);
	}
}
