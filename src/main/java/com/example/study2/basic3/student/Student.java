package com.example.study2.basic3.student;

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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
// @NamedEntityGraph(name = "Student.withEnrollmentsAndCourses",
// 	attributeNodes = @NamedAttributeNode(value = "enrollmentList", subgraph = "enrollmentsWithCourse"),
// 	subgraphs = @NamedSubgraph(name = "enrollmentsWithCourse", attributeNodes = @NamedAttributeNode("course")))

@NamedEntityGraph(name = "Student.test",
	attributeNodes = @NamedAttributeNode(value = "enrollmentList", subgraph = "enrollmentsWithCourse"),
	subgraphs = @NamedSubgraph(name = "enrollmentsWithCourse", attributeNodes = @NamedAttributeNode("course")
	))
@Getter
@NoArgsConstructor
// @Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	String name;

	@OneToMany(mappedBy = "student" , fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
		orphanRemoval = true
	)
	List<Enrollment> enrollmentList = new ArrayList<>();

	public Student(Long id, String name, List<Enrollment> enrollmentList) {
		this.id = id;
		this.name = name;
		this.enrollmentList = enrollmentList;
	}


}
