package com.example.study2.basic2.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.GeneratedColumn;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedules")
@Getter
@NoArgsConstructor
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String task;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	String password;

	@Column(updatable = false, name = "created_at")
	@CreatedDate
	LocalDateTime createdAt;

	@Column(name = "modified_at")
	@LastModifiedDate
	LocalDateTime modifiedAt;

	@Builder
	public Schedule(String task, String name, String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.task = task;
		this.name = name;
		this.password = password;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}
}
