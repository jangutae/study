package com.example.study2.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study2.user.entity.User;
import com.example.study2.user.repository.UserRepository;

@Service
public class UserService {

	final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> readAllUser() {
		return userRepository.getAllUsers();
	}

	public User createUser(User savedUser) {
		return userRepository.createUser1(savedUser);
	}

	public User getUser(Integer userId) {
		return null;
	}
}
