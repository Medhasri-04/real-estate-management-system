package com.realestatemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.auth.ForgotPasswordRequest;
import com.realestatemanagement.dto.request.auth.LoginRequest;
import com.realestatemanagement.dto.request.auth.RegisterRequest;
import com.realestatemanagement.dto.request.auth.ResetPasswordRequest;
import com.realestatemanagement.dto.response.AuthResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// 1. POST /auth/register
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse register(@Valid @RequestBody RegisterRequest req) {
		return authService.registerAgent(req);
	}

	// 2. POST /auth/login
	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest req) {
		return authService.login(req);
	}

	// 3. POST /auth/logout
	@PostMapping("/logout")
	public MessageResponse logout() {
		return authService.logout();
	}

	// 4. POST /auth/forgot-password
	@PostMapping("/forgot-password")
	public MessageResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
		return authService.forgotPassword(req);
	}

	// 5. PUT /auth/reset-password
	@PutMapping("/reset-password")
	public MessageResponse resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
		return authService.resetPassword(req);
	}

	// 6. GET /auth/profile
	@GetMapping("/profile")
	public UserProfileResponse profile() {
		return authService.profile();
	}

	// 7. POST /auth/register/agent (ADMIN only)
	@PostMapping("/register/agent")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse registerAgent(@Valid @RequestBody RegisterRequest req) {
		return authService.registerAgent(req);
	}
}