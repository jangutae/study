package com.example.study2.basic3.student.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study2.basic3.enrollment.Enrollment;
import com.example.study2.basic3.student.Student;
import com.example.study2.basic3.student.dto.StudentCourseDto;
import com.example.study2.basic3.student.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public void printStudentsCourse(Long studentId) {
		// Student student = studentRepository.findStudentWithCourse(studentId);
		Student student = studentRepository.findStudentWithCourseById(studentId);
		// List<StudentCourseDto> studentCourseDto = studentRepository.findStudentCoursesAsDto(studentId);

		log.info("학생이름 : {}", student.getName());
		// log.info("학생이름 : {}", studentCourseDto.get(0).getStudentName());

		// 지연 로딩으로 인해 루프마다 쿼리가 발생합니다 (N+1 문제)
		for (Enrollment enrollment : student.getEnrollmentList()) {
			log.info("수강한 코스 제목 {}  " , enrollment.getCourse().getTitle());
			log.info("수강 날짜 {} " , enrollment.getEnrolledDate());
		}

		// for (StudentCourseDto dto : studentCourseDto) {
		// 	// log.info("학생 이름 : {}", dto.getStudentName());
		// 	log.info("수강한 코스 제목 {} ", dto.getCourseTitle());
		// 	log.info("수강한 날짜 {} ", dto.getEnrollmentDate());
		// }
	}

	public void deleteFirstEnrollment(Long studentId) {
		Student student = studentRepository.findById(studentId).orElseThrow();

		student.getEnrollmentList().remove(0);

		// studentRepository.delete(student);
	}
}
