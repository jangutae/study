package com.example.study2.basic2.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study2.basic2.dto.ScheduleRequestDto;
import com.example.study2.basic2.dto.ScheduleResponseDto;
import com.example.study2.basic2.service.ScheduleService;

@RestController
@RequestMapping("/v1/schedules")
public class ScheduleController {

	private final ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

		ScheduleResponseDto responseDto = scheduleService.createdSchedule(requestDto);

		return ResponseEntity.ok().body(responseDto);
	}

	@GetMapping
	public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
		@RequestParam(required = false) String workName,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime modifiedAt
	) {
		return ResponseEntity.ok().body(scheduleService.getAllSchedules(workName, modifiedAt));
	}

}
