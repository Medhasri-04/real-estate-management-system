package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.UserRequestDTO;
import com.realestatemanagement.dto.response.UserResponseDTO;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.UserMapper;
import com.realestatemanagement.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	// Constructor injection
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Create a new user
	public UserResponseDTO createUser(UserRequestDTO dto) {
		User user = UserMapper.toEntity(dto); // Convert DTO to entity
		user = userRepository.save(user); // Save entity in DB
		return UserMapper.toDto(user); // Convert entity back to DTO for response
	}

	// Get a single user by ID
	public UserResponseDTO getUserById(Long id) {
		return userRepository.findById(id).map(UserMapper::toDto).orElse(null); // Return null if not found
	}

	// Get all users
	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	// Update user
	public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
		return userRepository.findById(id).map(existing -> {
			existing.setFirstName(dto.getFirstName());
			existing.setLastName(dto.getLastName());
			existing.setEmail(dto.getEmail());
			existing.setPhoneNumber(dto.getPhoneNumber());
			existing.setPassword(dto.getPassword());
			existing.setRole(dto.getRole());
			existing.setEnabled(dto.getEnabled());
			return UserMapper.toDto(userRepository.save(existing));
		}).orElse(null);
	}

	// Delete user
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
