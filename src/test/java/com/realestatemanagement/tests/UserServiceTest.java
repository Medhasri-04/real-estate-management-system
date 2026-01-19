package com.realestatemanagement.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.realestatemanagement.dto.request.user.UpdatePasswordRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.UserMapper;
import com.realestatemanagement.repository.UserRepository;
import com.realestatemanagement.service.UserService;

class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private UserMapper userMapper;
	private UserService userService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		userService = new UserService(userRepository, passwordEncoder, userMapper);
		// simulate logged-in user
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("user@example.com", null));
	}

	@Test
	void updatePassword_success() {
		// arrange
		UpdatePasswordRequest req = new UpdatePasswordRequest();
		req.setOldPassword("Old@123");
		req.setNewPassword("New@456");
		User user = new User();
		user.setEmail("user@example.com");
		user.setPassword("oldHash");
		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("Old@123", "oldHash")).thenReturn(true);
		when(passwordEncoder.encode("New@456")).thenReturn("newHash");
		// act
		MessageResponse response = userService.updatePassword(req);
		// assert
		assertEquals("Password updated", response.getMessage());
		verify(userRepository).save(user);
		assertEquals("newHash", user.getPassword());
	}
}