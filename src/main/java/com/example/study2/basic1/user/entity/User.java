package com.example.study2.basic1.user.entity;

import java.util.UUID;

import com.example.study2.basic1.user.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
// @Data
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	private String uuid;

	@Builder
	public User(Long id, String email, String password, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.uuid = UUID.randomUUID().toString();
	}

	public void updateUser(UserDTO userdto) {
		this.email = userdto.getEmail();
		this.password = userdto.getPassword();
		this.nickname = userdto.getNickname();
	}

	public void creatUuid() {
		this.uuid = UUID.randomUUID().toString();
	}

}



