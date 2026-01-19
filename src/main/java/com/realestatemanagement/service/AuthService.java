package com.realestatemanagement.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.auth.ForgotPasswordRequest;
import com.realestatemanagement.dto.request.auth.LoginRequest;
import com.realestatemanagement.dto.request.auth.RegisterRequest;
import com.realestatemanagement.dto.request.auth.ResetPasswordRequest;
import com.realestatemanagement.dto.response.AuthResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.UserMapper;
import com.realestatemanagement.repository.UserRepository;
import com.realestatemanagement.security.JwtService;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final UserMapper userMapper;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			UserMapper userMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userMapper = userMapper;
	}

	public MessageResponse registerAgent(RegisterRequest req) {
		if (userRepository.existsByEmail(req.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		User u = new User();
		u.setFirstName(req.getFirstName());
		u.setLastName(req.getLastName());
		u.setEmail(req.getEmail());
		u.setPhoneNumber(req.getPhoneNumber());
		u.setPassword(passwordEncoder.encode(req.getPassword()));
		u.setRole(req.getRole());
		u.setEnabled(true);
		userRepository.save(u);
		return new MessageResponse("User registered successfully", LocalDateTime.now());
	}

	public AuthResponse login(LoginRequest req) {
		User u = userRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid credentials"));
		if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		String token = jwtService.generateToken(u.getEmail());
		return new AuthResponse(token, u.getRole());
	}

	public MessageResponse logout() {
		return new MessageResponse("Logged out successfully", LocalDateTime.now());
	}

	public MessageResponse forgotPassword(ForgotPasswordRequest req) {
		userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		return new MessageResponse("Password reset link sent", LocalDateTime.now());
	}

	public MessageResponse resetPassword(ResetPasswordRequest req) {
		User u = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		u.setPassword(passwordEncoder.encode(req.getNewPassword()));
		userRepository.save(u);
		return new MessageResponse("Password reset successful", LocalDateTime.now());
	}

	public UserProfileResponse profile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userRepository.findByEmail(email).orElseThrow();
		return userMapper.toProfile(u);
	}
}