package com.example.study2.basic1.user.filter;

import java.io.IOException;

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
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {

		log.info("로그인 필터 시작");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession(false);

		if (session == null) {
			// if (request.getRequestURI().equals("/session-login/session")) {
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
}
