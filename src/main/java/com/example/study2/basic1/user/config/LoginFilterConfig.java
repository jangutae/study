package com.example.study2.basic1.user.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.study2.basic1.user.filter.LoginFilter;

@Configuration
public class LoginFilterConfig {

	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		FilterRegistrationBean<LoginFilter> loginFilter =
			new FilterRegistrationBean<>();
		loginFilter.setFilter(new LoginFilter());
		loginFilter.setOrder(1);
		loginFilter.addUrlPatterns("/v1/users");

		return loginFilter;

	}
}
