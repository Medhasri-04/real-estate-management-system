package com.realestatemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.user.UpdateEmailRequest;
import com.realestatemanagement.dto.request.user.UpdatePasswordRequest;
import com.realestatemanagement.dto.request.user.UpdatePhoneRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// 8. PUT /users/update-phone
	@PutMapping("/update-phone")
	public MessageResponse updatePhone(@Valid @RequestBody UpdatePhoneRequest req) {
		return userService.updatePhone(req);
	}

	// 9. PUT /users/update-email
	@PutMapping("/update-email")
	public MessageResponse updateEmail(@Valid @RequestBody UpdateEmailRequest req) {
		return userService.updateEmail(req);
	}

	// 10. PUT /users/update-password
	@PutMapping("/update-password")
	public MessageResponse updatePassword(@Valid @RequestBody UpdatePasswordRequest req) {
		return userService.updatePassword(req);
	}

	// 11. GET /users/{userId}
	@GetMapping("/{userId}")
	public UserProfileResponse getUser(@PathVariable Long userId) {
		return userService.getUser(userId);
	}
}