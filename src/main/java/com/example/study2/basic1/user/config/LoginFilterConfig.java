package com.example.study2.basic1.user.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.study2.basic1.user.filter.LoginFilter;
import com.example.study2.basic1.user.filter.RequestLoggingFilter;

import jakarta.servlet.FilterRegistration;

@Configuration
public class LoginFilterConfig {

	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		FilterRegistrationBean<LoginFilter> loginFilter =
			new FilterRegistrationBean<>();
		loginFilter.setFilter(new LoginFilter());
		loginFilter.setOrder(1);
		loginFilter.addUrlPatterns("/v1/users");
		// loginFilter.addUrlPatterns("/session-login/*");// 해당 패턴에 URI 만 LoginFilter 를 거쳐야 함.

		return loginFilter;

	}

	@Bean
	public FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilter() {
		FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilter = new FilterRegistrationBean<>();

		requestLoggingFilter.setFilter(new RequestLoggingFilter());
		requestLoggingFilter.setOrder(2);
		requestLoggingFilter.addUrlPatterns("/*");

		return requestLoggingFilter;
	}
}
