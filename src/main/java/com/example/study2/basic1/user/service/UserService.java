package com.example.study2.basic1.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.study2.basic1.exception.UserErrorCode;
import com.example.study2.basic1.exception.UserException;
import com.example.study2.basic1.user.dto.UserDTO;
import com.example.study2.basic1.user.entity.User;
import com.example.study2.basic1.user.repository.JdbcTemplateUserRepository;
import com.example.study2.basic1.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	final JdbcTemplateUserRepository jdbcTemplateUserRepository;
	private final UserRepository userRepository;

	public UserService(
		JdbcTemplateUserRepository jdbcTemplateUserRepository,
		UserRepository userRepository) {
		this.jdbcTemplateUserRepository = jdbcTemplateUserRepository;
		this.userRepository = userRepository;
	}

	// 회원가입
	public void join(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email is already in use");
		}
		if (userRepository.existsByNickname(user.getNickname())) {
			throw new IllegalArgumentException("Nickname is already in use");
		}
		userRepository.save(user);
	}

	// 로그인
	public User login(String email, String password) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("email or password is incorrect"));

		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("Wrong password");
		}
		return user;
	}

	// ID 로 사용자 조회
	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("user not found"));
	}

	public List<User> readAllUser() {
		return jdbcTemplateUserRepository.getAllUsers();
	}

	public User createUser(User savedUser) {
		return jdbcTemplateUserRepository.createUser1(savedUser);
	}

	public User getUser(Integer userId) {
		if (userId == 8) {
			throw new UserException(UserErrorCode.NOT_FOUND_USER);
		}
		return jdbcTemplateUserRepository.getUser(userId);
	}

	public UserDTO createdUser(UserDTO userdto) {
		User user = User.builder()
			.email(userdto.getEmail())
			.password(userdto.getPassword())
			.nickname(userdto.getNickname()).build();

		User saveUser = userRepository.save(user);

		return UserDTO.toDto(saveUser);
	}

	@Transactional
	public String login(UserDTO userDto) {
		User findUser = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_USER));

		if (!findUser.getPassword().equals(userDto.getPassword())) {
			throw new IllegalArgumentException("Wrong password");
		}
		findUser.creatUuid();

		return findUser.getUuid();
	}

	public User checkUserBySession(String Uuid) {
		return userRepository.findByUuid(Uuid).orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_USER));
	}

}
