package com.example.study2.basic1.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study2.basic1.user.dto.UserDTO;
import com.example.study2.basic1.user.entity.User;
import com.example.study2.basic1.user.service.UserService;
import com.example.study2.basic1.util.SessionManager;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	public final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	List<User> userList = new ArrayList<>();

	// @GetMapping("/{userId}")
	// public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId) {
	// 	// userId를 사용한 로직 처리
	// 	return ResponseEntity.ok().body(userService.getUser(userId));
	// }
	//
	// @GetMapping
	// public ResponseEntity<List<User>> getAllUsers() {
	//
	// 	return ResponseEntity.ok().body(userService.readAllUser());
	// }
	//
	// @PostMapping
	// public ResponseEntity<User> createUser(@RequestBody User user) {
	// 	User response = userService.createUser(user);
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(response);
	// }

	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userdto) {
		User response = userService.updatedUser(userId, userdto);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody UserDTO userdto) {
		User response = userService.createUser(userdto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<User>> findAllUsers() {
		List<User> allUsers = userService.findAllUsers();
		return ResponseEntity.ok().body(allUsers);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> findUser(@PathVariable Long userId) {
		User user = userService.findUser(userId);
		return ResponseEntity.ok().body(user);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId, @RequestBody UserDTO userdto) {
		userService.deletedUser(userId, userdto.getEmail());
		return ResponseEntity.status(HttpStatus.OK).body("Deleted user");
	}

}


