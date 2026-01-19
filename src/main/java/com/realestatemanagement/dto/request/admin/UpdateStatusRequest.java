package com.realestatemanagement.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateStatusRequest {
	@NotBlank
	@Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "status must be ACTIVE or INACTIVE")
	private String status;

	public UpdateStatusRequest() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}