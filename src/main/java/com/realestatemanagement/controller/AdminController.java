package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.admin.UpdateRoleRequest;
import com.realestatemanagement.dto.request.admin.UpdateStatusRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.UserProfileResponse;
import com.realestatemanagement.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	// 12. GET /admin/users
	@GetMapping("/users")
	public List<UserProfileResponse> getAllUsers() {
		return adminService.getAllUsers();
	}

	// 13. GET /admin/users/{userId}
	@GetMapping("/users/{userId}")
	public UserProfileResponse getUser(@PathVariable Long userId) {
		return adminService.getUser(userId);
	}

	// 14. PUT /admin/users/{userId}/role
	@PutMapping("/users/{userId}/role")
	public MessageResponse updateRole(@PathVariable Long userId, @Valid @RequestBody UpdateRoleRequest req) {
		return adminService.updateRole(userId, req);
	}

	// 15. PUT /admin/users/{userId}/status
	@PutMapping("/users/{userId}/status")
	public MessageResponse updateStatus(@PathVariable Long userId, @Valid @RequestBody UpdateStatusRequest req) {
		return adminService.updateStatus(userId, req);
	}
}