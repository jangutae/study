package com.example.study2.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study2.user.entity.User;
import com.example.study2.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
public class UserController {

	final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	List<User> userList = new ArrayList<>();

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId) {
		// userId를 사용한 로직 처리
		return ResponseEntity.ok().body(userService.getUser(userId));
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {

		return ResponseEntity.ok().body(userService.readAllUser());
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User response = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{userId}")
	public User updateUser(@PathVariable Integer userId, @RequestBody User user) {
		// this.userList.set(userId, name);
		this.userList.remove(userId.intValue());
		this.userList.add(userId, user);
		return this.userList.get(userId);
	}
}


