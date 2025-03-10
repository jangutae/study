package com.example.study2.basic1.user.controller;

import static org.mockito.ArgumentMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.study2.basic1.user.dto.UserDTO;
import com.example.study2.basic1.user.entity.User;
import com.example.study2.basic1.user.repository.UserRepository;
import com.example.study2.basic1.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@MockitoBean
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	private UserRepository userRepository;

	@Test
	@DisplayName("유저 생성 시 유저 정보를 반환")
	void createUser_ShouldReturnUserInfo() throws Exception {
		// given
		UserDTO dto = Mockito.mock(UserDTO.class);
		User user = new User(1L, "test@example.com", "aaAA!!00", "test", UUID.randomUUID().toString());
		BDDMockito.given(userService.createUser(any(UserDTO.class))).willReturn(user);
		// when, then
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
				.sessionAttr("user", user)
			)
			.andExpectAll(MockMvcResultMatchers.status().isCreated(),
				MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()),
				MockMvcResultMatchers.jsonPath("$.id").value(user.getId()),
				MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
			.andDo(MockMvcResultHandlers.print());
		// them
	}


	@Test
	@DisplayName("유저 생성 시 유저 정보 및 세션 반환")
	void createUser_ShouldReturnUserInfoAndSession() throws Exception {
		// given
		UserDTO dto = new UserDTO(1L, "test@example.com", "aaAA!!00", "test");
		MockHttpSession mockSession = new MockHttpSession();
		BDDMockito.given(userService.login(any(UserDTO.class))).willReturn("UUID");

		mockSession.setAttribute("mySession", "UUID");
		// when, then
		mockMvc.perform(MockMvcRequestBuilders.post("/session-login/session")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))
				.session(mockSession))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andDo(MockMvcResultHandlers.print());
		// them
	}
}
