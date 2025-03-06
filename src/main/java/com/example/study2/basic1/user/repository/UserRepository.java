package com.example.study2.basic1.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.study2.basic1.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
	Optional<User> findByUuid(String uuid);

}

