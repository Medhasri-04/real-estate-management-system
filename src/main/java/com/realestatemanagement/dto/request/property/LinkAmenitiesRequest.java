package com.realestatemanagement.dto.request.property;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class LinkAmenitiesRequest {
	@NotEmpty
	private List<Long> amenityIds;

	public LinkAmenitiesRequest() {
	}

	public List<Long> getAmenityIds() {
		return amenityIds;
	}

	public void setAmenityIds(List<Long> amenityIds) {
		this.amenityIds = amenityIds;
	}
}