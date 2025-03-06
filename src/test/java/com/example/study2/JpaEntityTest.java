package com.example.study2;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.study2.basic3.course.Course;
import com.example.study2.basic3.course.CourseRepository;
import com.example.study2.basic3.enrollment.Enrollment;
import com.example.study2.basic3.enrollment.EnrollmentRepository;
import com.example.study2.basic3.student.Student;
import com.example.study2.basic3.student.repository.StudentRepository;

@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaEntityTest {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Test
	@DisplayName("CascadeType.ALL 동작 테스트 - 부모 엔티티 저장 시 자식 엔티티도 함께 저장")
	void testCascadePersist() {
		Course course = new Course(null, "JAVA", new ArrayList<>());
		Student student = new Student(null, "박성원", new ArrayList<>());
		Enrollment enrollment = new Enrollment(null, LocalDate.now(), student, course);

		student.getEnrollmentList().add(enrollment);
		course.getEnrollmentList().add(enrollment);

		studentRepository.save(student);
		courseRepository.save(course);

		Optional<Student> persistedStudent = studentRepository.findById(student.getId());
		assertThat(persistedStudent).isPresent();
		assertThat(persistedStudent.get().getEnrollmentList()).hasSize(1);

		Optional<Course> persistedCourse = courseRepository.findById(course.getId());
		assertThat(persistedCourse).isPresent();
		assertThat(persistedCourse.get().getEnrollmentList()).hasSize(1);
	}

	@Test
	@DisplayName("orphanRemoval 동작 테스트 - 부모와 관계가 끊어진 자식 엔티티 자동 삭제")
	void testOrphanRemoval() {
		Course course = new Course(null, "JAVA", new ArrayList<>());
		Student student = new Student(null, "박성원", new ArrayList<>());
		Enrollment enrollment = new Enrollment(null, LocalDate.now(), student, course);

		student.getEnrollmentList().add(enrollment);
		course.getEnrollmentList().add(enrollment);

		courseRepository.save(course);
		studentRepository.save(student);

		student.getEnrollmentList().remove(enrollment);
		enrollment.setStudent(null);

		studentRepository.saveAndFlush(student);

		Optional<Enrollment> orphan = enrollmentRepository.findById(enrollment.getId());
		assertThat(orphan).isNotPresent();
	}

	@Test
	@DisplayName("CascadeType.REMOVE 동작 테스트 - 부모 삭제 시 자식도 함께 삭제")
	void testCascadeRemove() {
		Course course = new Course(null, "JAVA", new ArrayList<>());
		Student student = new Student(null, "박성원", new ArrayList<>());

		Enrollment enrollment = new Enrollment(null, LocalDate.now(), student, course);

		student.getEnrollmentList().add(enrollment);
		course.getEnrollmentList().add(enrollment);

		courseRepository.save(course);
		studentRepository.save(student);
		Long a = student.getId();
		Long b = enrollment.getId();
		System.out.println(a);
		System.out.println(b);
		studentRepository.delete(student);



		Optional<Student> deletedStudent = studentRepository.findById(a);
		Optional<Enrollment> deletedEnrollment = enrollmentRepository.findById(b);

		assertThat(deletedStudent).isNotPresent();
		assertThat(deletedEnrollment).isNotPresent();
	}

	@Test
	@DisplayName("orphanRemoval 동작 테스트 - 부모와 관계가 끊어진 자식 삭제")
	void testOrphanRemoval2() {
		Course course = new Course(null, "JAVA", new ArrayList<>());
		Student student = new Student(null, "박성원", new ArrayList<>());
		Enrollment enrollment = new Enrollment(null, LocalDate.now(), student, course);

		student.getEnrollmentList().add(enrollment);
		course.getEnrollmentList().add(enrollment);

		courseRepository.save(course);
		studentRepository.save(student);
		enrollmentRepository.save(enrollment);


		student.getEnrollmentList().remove(enrollment);
		enrollment.setStudent(null);
		studentRepository.saveAndFlush(student);

		Optional<Student> remainingStudent = studentRepository.findById(student.getId());
		Optional<Enrollment> orphanEnrollment = enrollmentRepository.findById(enrollment.getId());

		assertThat(remainingStudent).isPresent();
		assertThat(orphanEnrollment).isNotPresent();
	}
}
