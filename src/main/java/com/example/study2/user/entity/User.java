package com.example.study2.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

	Long id;

	String name;

	int age;

	public User(Long id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

