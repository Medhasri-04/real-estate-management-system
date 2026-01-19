package com.realestatemanagement.dto.request.amenity;

import jakarta.validation.constraints.NotBlank;

public class AmenityRequest {
	@NotBlank
	private String name;

	public AmenityRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}