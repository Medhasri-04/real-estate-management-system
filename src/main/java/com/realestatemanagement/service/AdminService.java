package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.admin.UpdateRoleRequest;
import com.realestatemanagement.dto.request.admin.UpdateStatusRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.UserMapper;
import com.realestatemanagement.repository.UserRepository;

@Service
public class AdminService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public AdminService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	// GET 
	public List<UserProfileResponse> getAllUsers() {
		List<UserProfileResponse> res = new ArrayList<>();
		for (User u : userRepository.findAll()) {
			res.add(userMapper.toProfile(u));
		}
		return res;
	}

	// GET 
	public UserProfileResponse getUser(Long userId) {
		User u = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		return userMapper.toProfile(u);
	}

	// PUT 
	public MessageResponse updateRole(Long userId, UpdateRoleRequest req) {
		User u = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		u.setRole(req.getRole());
		userRepository.save(u);
		return new MessageResponse("Role updated", LocalDateTime.now());
	}

	// PUT 
	public MessageResponse updateStatus(Long userId, UpdateStatusRequest req) {
		User u = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		u.setEnabled("ACTIVE".equalsIgnoreCase(req.getStatus()));
		userRepository.save(u);
		return new MessageResponse("Status updated", LocalDateTime.now());
	}
}