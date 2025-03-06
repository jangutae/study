package com.example.study2.basic3.student.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.study2.basic3.student.Student;
import com.example.study2.basic3.student.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/{studentId}")
	public void findAllStudents(@PathVariable Long studentId) {
		studentService.printStudentsCourse(studentId);
	}

	@DeleteMapping("/{studentId}")
	public void deleteStudent(@PathVariable Long studentId) {
		studentService.deleteFirstEnrollment(studentId);
	}
}
