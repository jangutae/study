package com.example.study2.basic3.course;

import java.util.ArrayList;
import java.util.List;

import com.example.study2.basic3.enrollment.Enrollment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
// @Table(name = "courses")
@NoArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	String title;

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
		orphanRemoval = true
	)
	List<Enrollment> enrollmentList = new ArrayList<>();

	public Course(Long id, String title, List<Enrollment> enrollmentList) {
		this.id = id;
		this.title = title;
		this.enrollmentList = enrollmentList;
	}
}
