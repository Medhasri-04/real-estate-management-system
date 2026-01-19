package com.realestatemanagement.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdatePhoneRequest {
	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$", message = "newPhoneNumber must be 10 digits")
	private String newPhoneNumber;

	public UpdatePhoneRequest() {
	}

	public String getNewPhoneNumber() {
		return newPhoneNumber;
	}

	public void setNewPhoneNumber(String newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}
}