package com.realestatemanagement.mapper;

import com.realestatemanagement.entity.User;
import com.realestatemanagement.dto.request.UserRequestDTO;
import com.realestatemanagement.dto.response.UserResponseDTO;

public class UserMapper {

	public static UserResponseDTO toDto(User user) {
		if (user == null)
			return null;

		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setRole(user.getRole()); // String now
		dto.setEnabled(user.getEnabled());
		dto.setCreatedAt(user.getCreatedAt());
		dto.setUpdatedAt(user.getUpdatedAt());
		return dto;
	}

	public static User toEntity(UserRequestDTO dto) {
		if (dto == null)
			return null;

		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setPassword(dto.getPassword());
		user.setRole(dto.getRole());
		user.setEnabled(dto.getEnabled());
		return user;
	}
}
