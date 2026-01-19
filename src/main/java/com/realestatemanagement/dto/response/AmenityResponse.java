package com.realestatemanagement.dto.response;

public class AmenityResponse {
	private Long amenityId;
	private String name;

	public AmenityResponse() {
	}

	public AmenityResponse(Long amenityId, String name) {
		this.amenityId = amenityId;
		this.name = name;
	}

	public Long getAmenityId() {
		return amenityId;
	}

	public void setAmenityId(Long amenityId) {
		this.amenityId = amenityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}