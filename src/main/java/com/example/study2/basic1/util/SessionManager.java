package com.example.study2.basic1.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Getter
public class SessionManager {

	private static final Map<String, HttpSession> sessionMap = Collections.synchronizedMap(new HashMap<>());

	public static void addSession(HttpSession session) {
		sessionMap.put(session.getId(), session);
	}

	public static void removeSession(HttpSession session) {
		sessionMap.remove(session.getId());
	}

	public static Map<String, HttpSession> getSessionMap() {
		return sessionMap;
	}
}
