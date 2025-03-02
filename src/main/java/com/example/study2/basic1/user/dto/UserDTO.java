package com.example.study2.basic1.user.dto;

import com.example.study2.basic1.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

	private Long id;

	private String email;

	private String password;

	private String nickname;

	public static UserDTO toDto(User user) {
		return new UserDTO(
			user.getId(),
			user.getEmail(),
			user.getPassword(),
			user.getNickname());
	}
}
