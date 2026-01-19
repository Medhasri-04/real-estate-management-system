package com.realestatemanagement.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	@NotBlank
	@Size(min = 2, max = 80)
	private String firstName;
	@NotBlank
	@Size(min = 2, max = 80)
	private String lastName;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$", message = "phoneNumber must be 10 digits")
	private String phoneNumber;
	@NotBlank
	@Size(min = 6, max = 100)
	private String password;
	// "ADMIN" / "AGENT" / "CUSTOMER"
	@NotBlank
	private String role;

	public RegisterRequest() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}