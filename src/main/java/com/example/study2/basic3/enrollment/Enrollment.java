package com.example.study2.basic3.enrollment;

import java.time.LocalDate;

import com.example.study2.basic3.course.Course;
import com.example.study2.basic3.student.Student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
// @Table(name = "enrollments")
@NoArgsConstructor
@Setter
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDate enrolledDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	Course course;

	public Enrollment(Long id, LocalDate enrolledDate, Student student, Course course) {
		this.id = id;
		this.enrolledDate = enrolledDate;
		this.student = student;
		this.course = course;
	}
}
