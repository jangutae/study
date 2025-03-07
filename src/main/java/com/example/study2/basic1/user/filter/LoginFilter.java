package com.example.study2.basic1.user.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginFilter implements Filter {

	private static final String[] WHITE_LIST = {"/v1/users"};

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {

		log.info("로그인 필터 시작");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession(false);
		String requestURI = request.getRequestURI();

		if (session == null) {
			// if (requestURI.equals("/session-login/session")) {
				log.info("세션이 없습니다. 로그인해주세요");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			// }
		} else {
			log.info("세션이 있습니다.");
		}

		filterChain.doFilter(request, response);
		log.info("로그인 필터 종료");
	}

	private boolean needAuthentication(String requestURI) {
		return Arrays.stream(WHITE_LIST).anyMatch(requestURI::startsWith);
	}

}
