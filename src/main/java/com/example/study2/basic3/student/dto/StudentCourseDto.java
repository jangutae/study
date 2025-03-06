package com.example.study2.basic3.student.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class StudentCourseDto {

	private String studentName;
	private String courseTitle;
	private LocalDate enrollmentDate;

	public StudentCourseDto(String studentName, String courseTitle, LocalDate enrollmentDate) {
		this.studentName = studentName;
		this.courseTitle = courseTitle;
		this.enrollmentDate = enrollmentDate;
	}
}
