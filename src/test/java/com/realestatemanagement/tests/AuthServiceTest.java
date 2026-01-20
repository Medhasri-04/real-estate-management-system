package com.realestatemanagement.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.realestatemanagement.dto.request.auth.LoginRequest;
import com.realestatemanagement.dto.response.AuthResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.UserMapper;
import com.realestatemanagement.repository.UserRepository;
import com.realestatemanagement.security.JwtService;
import com.realestatemanagement.service.AuthService;

class AuthServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtService jwtService;
	@Mock
	private UserMapper userMapper;
	private AuthService authService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		authService = new AuthService(userRepository, passwordEncoder, jwtService, userMapper);
	}

	@Test
	void login_success_returnsTokenAndRole() {
		LoginRequest req = new LoginRequest();
		req.setEmail("user@example.com");
		req.setPassword("pass123");
		User u = new User();
		u.setEmail("user@example.com");
		u.setPassword("$bcryptHash");
		u.setRole("CUSTOMER");
		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(u));
		when(passwordEncoder.matches("pass123", "$bcryptHash")).thenReturn(true);
		when(jwtService.generateToken("user@example.com")).thenReturn("TOKEN123");
		AuthResponse res = authService.login(req);
		assertEquals("TOKEN123", res.getToken());
		assertEquals("CUSTOMER", res.getRole());
		verify(jwtService).generateToken("user@example.com");
	}

	@Test
	void login_wrongPassword_throwsRuntimeException() {
		LoginRequest req = new LoginRequest();
		req.setEmail("user@example.com");
		req.setPassword("wrong");
		User u = new User();
		u.setEmail("user@example.com");
		u.setPassword("$bcryptHash");
		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(u));
		when(passwordEncoder.matches("wrong", "$bcryptHash")).thenReturn(false);
		RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(req));
		assertEquals("Invalid credentials", ex.getMessage());
		verify(jwtService, never()).generateToken(anyString());
	}

	@Test
	void logout_returnsMessageAndTimestamp() {
		MessageResponse res = authService.logout();
		assertEquals("Logged out successfully", res.getMessage());
		assertNotNull(res.getTimestamp());
		assertTrue(res.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
	}
}