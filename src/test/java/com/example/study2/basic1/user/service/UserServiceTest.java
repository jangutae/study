package com.example.study2.basic1.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.study2.basic1.user.entity.User;
import com.example.study2.basic1.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;


	@Test
	@DisplayName("email, password 일치 시 유저를 반환")
	void sendEmailAndPassword_willReturnUser() {
		// given
		String email = "test@example.com";
		String password = "aaAA!!00";
		User user = new User(1L, "test@example.com", "aaAA!!00", "test", UUID.randomUUID().toString());
		BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
		// when
		User findUser = userService.login(email, password);

		// then
		Assertions.assertThat(findUser.getPassword()).isEqualTo(password);
		Assertions.assertThat(findUser.getEmail()).isEqualTo(email);
	}

	@Test
	@DisplayName("email 불일치 시 -> email or password is incorrect 예외를 반환")
	void sendEmailAndPassword_willReturnException1() {
		// given
		String email = "test@example.com";
		String password = "aaAA!!00";
		// User user = new User(1L, "exception@example.com", "aaAA!!00", "test", UUID.randomUUID().toString());
		// BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
		// when

		// then
		Assertions.assertThatThrownBy(() -> userService.login(email, password))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("email or password is incorrect");
	}

	@Test
	@DisplayName("password 불일치 시 -> email or wrong password 예외를 반환")
	void sendEmailAndPassword_willReturnException2() {
		// given
		String email = "test@example.com";
		String password = "aaAA!!001";
		User user = new User(1L, "exception@example.com", "aaAA!!00", "test", UUID.randomUUID().toString());
		BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
		// when

		// then
		Assertions.assertThatThrownBy(() -> userService.login(email, password))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("email or Wrong password");
	}
}
