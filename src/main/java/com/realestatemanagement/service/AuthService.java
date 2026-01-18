package com.realestatemanagement.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.LoginRequestDTO;
import com.realestatemanagement.dto.request.RegisterRequestDTO;
import com.realestatemanagement.dto.response.JwtResponseDTO;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.repository.UserRepository;
import com.realestatemanagement.security.JwtService;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authManager = authManager;
	}

	// ================= Register a regular user =================
	public String register(RegisterRequestDTO dto) {
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole("CUSTOMER"); // default role for normal registration
		userRepository.save(user);

		return "User registered successfully";
	}

	// ================= Register an agent (ADMIN only) =================
	public String registerAgent(RegisterRequestDTO dto) {
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole("AGENT"); // role for agent
		userRepository.save(user);

		return "Agent registered successfully";
	}

	// ================= Login =================

	public JwtResponseDTO login(LoginRequestDTO dto) {
		var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		String token = jwtService.generateToken(principal); // ✅ now compiles

		// Optionally fetch role (or any other data) from your entity:
		User user = userRepository.findByEmail(principal.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		return new JwtResponseDTO(token, user.getRole());
	}

	// ================= Send password reset link =================
	public void sendResetLink(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		// Simulate sending email (replace with actual email service)
		System.out.println("Sending password reset link to: " + email);
	}

	// ================= Reset password =================
	public void resetPassword(String email, String newPassword) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	// ================= Get profile of currently logged-in user =================
	public User getProfile() {
		Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof User user) {
			return userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
		} else {
			throw new RuntimeException("User not authenticated");
		}
	}
}
