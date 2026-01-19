package com.realestatemanagement.dto.request.admin;

import jakarta.validation.constraints.NotBlank;

public class UpdateRoleRequest {
	@NotBlank
	private String role; // ADMIN / AGENT / CUSTOMER

	public UpdateRoleRequest() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}