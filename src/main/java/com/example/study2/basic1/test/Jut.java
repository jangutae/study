package com.example.study2.basic1.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
// @Table(name = "juts")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Jut {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column
	String name;

	public void updateByDTO(JutDTO dto) {
		this.name = dto.name;
	}
}
