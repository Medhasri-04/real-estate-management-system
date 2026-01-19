package com.realestatemanagement.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.user.UpdateEmailRequest;
import com.realestatemanagement.dto.request.user.UpdatePasswordRequest;
import com.realestatemanagement.dto.request.user.UpdatePhoneRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.UserMapper;
import com.realestatemanagement.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}

	private User currentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).orElseThrow();
	}

	public MessageResponse updatePhone(UpdatePhoneRequest req) {
		User u = currentUser();
		u.setPhoneNumber(req.getNewPhoneNumber());
		userRepository.save(u);
		return new MessageResponse("Phone updated", LocalDateTime.now());
	}

	public MessageResponse updateEmail(UpdateEmailRequest req) {
		User u = currentUser();
		if (userRepository.existsByEmail(req.getNewEmail())) {
			throw new RuntimeException("Email already exists");
		}
		u.setEmail(req.getNewEmail());
		userRepository.save(u);
		return new MessageResponse("Email updated", LocalDateTime.now());
	}

	public MessageResponse updatePassword(UpdatePasswordRequest req) {
		User u = currentUser();
		if (!passwordEncoder.matches(req.getOldPassword(), u.getPassword())) {
			throw new RuntimeException("Old password incorrect");
		}
		u.setPassword(passwordEncoder.encode(req.getNewPassword()));
		userRepository.save(u);
		return new MessageResponse("Password updated", LocalDateTime.now());
	}

	public UserProfileResponse getUser(Long id) {
		return userMapper.toProfile(userRepository.findById(id).orElseThrow());
	}
}