package com.realestatemanagement.mapper;

import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public UserProfileResponse toProfile(User u) {
		UserProfileResponse r = new UserProfileResponse();
		r.setUserId(u.getId());
		r.setFirstName(u.getFirstName());
		r.setLastName(u.getLastName());
		r.setEmail(u.getEmail());
		r.setPhoneNumber(u.getPhoneNumber());
		r.setRole(u.getRole());
		r.setEnabled(u.isEnabled());
		r.setCreatedAt(u.getCreatedAt());
		r.setUpdatedAt(u.getUpdatedAt());
		return r;
	}
}