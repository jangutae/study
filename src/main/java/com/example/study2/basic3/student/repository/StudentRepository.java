package com.example.study2.basic3.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.study2.basic3.student.Student;
import com.example.study2.basic3.student.dto.StudentCourseDto;

public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("""
		SELECT s
		FROM Student s
		JOIN FETCH s.enrollmentList e
		JOIN FETCH e.course
	 	WHERE s.id = :id """)
	Student findStudentWithCourse(@Param("id") Long id);

	@Query("""
		SELECT new com.example.study2.basic3.student.dto.StudentCourseDto(s.name, c.title, e.enrolledDate)
		FROM Student s
		JOIN s.enrollmentList e
		JOIN e.course c
		WHERE s.id = :studentId
	""")
	List<StudentCourseDto> findStudentCoursesAsDto(@Param("studentId") Long Id);


	// @EntityGraph("Student.withEnrollmentsAndCourses")
	// @Query("SELECT s FROM Student s WHERE s.id = :id")
	// @EntityGraph(attributePaths = "enrollmentList")
	@EntityGraph("Student.test")
	Student findStudentWithCourseById(Long id);

}
