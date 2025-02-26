package com.example.study2.basic1.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.study2.basic1.user.dto.UserDTO;
import com.example.study2.basic1.user.entity.User;
import com.example.study2.basic1.user.service.UserService;
import com.example.study2.basic1.util.SessionManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/session-login")
public class SessionLoginController {

	private static final String MY_SESSION = "mySession";

	private UserService userService;

	public SessionLoginController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/session")
	public User test(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			throw new IllegalArgumentException("session is null");
		}

		String sessionUuid = session.getAttribute(MY_SESSION).toString();

		return userService.checkUserBySession(sessionUuid);
	}

	@PostMapping("/session")
	public String createSession(@RequestBody UserDTO userdto, HttpServletRequest request) {
		HttpSession session = request.getSession(true);

		String uuid = userService.login(userdto);

		session.setAttribute(MY_SESSION, uuid);

		return "세션이 생성되었습니다.";
	}


	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userdto) {
		UserDTO response = userService.createdUser(userdto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody User user) {
		try {
			userService.join(user);
			return ResponseEntity.ok().body("회원 가입 성공");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(
		@RequestBody User loginRequest,
		HttpServletRequest request
	) {
		try {
			User loginUser = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

			// 세션 생성
			HttpSession session = request.getSession(true);
			session.setAttribute("userId", loginUser.getId());
			session.setMaxInactiveInterval(1800); // 30분
			return ResponseEntity.ok().body("로그인 성공");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// 로그인 상태
	@GetMapping
	public ResponseEntity<String> getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Long userId = (Long) session.getAttribute("userId");
			if (userId != null) {
				try {
					User user = userService.findById(userId);
					return ResponseEntity.ok().body("현재 로그인한 사용자 nickname: " + user.getNickname());
				} catch (IllegalArgumentException e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
				}
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되지 않았습니다.");
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ResponseEntity.ok("로그아웃 성공");
	}

	@GetMapping("/sessions")
	public ResponseEntity<Map<String, Object>> getSessions(HttpServletRequest request) {
		HttpSession session1 = request.getSession(false);
		if (session1 == null) {
			throw new IllegalArgumentException("session is null");
		}
		Map<String, Object> sessions = new HashMap<>();
		Object sessionUuid = session1.getAttribute(MY_SESSION);
		sessions.put(MY_SESSION, sessionUuid);
		// SessionManager.getSessionMap().forEach((key, session) ->
		// 	sessions.put(MY_SESSION, session.getAttribute(MY_SESSION)));

		return ResponseEntity.ok().body(sessions);
	}
}
