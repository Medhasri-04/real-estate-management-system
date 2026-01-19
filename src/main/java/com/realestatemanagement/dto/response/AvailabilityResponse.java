package com.realestatemanagement.dto.response;

import java.util.List;

public class AvailabilityResponse {
	private Long propertyId;
	private boolean available;
	private List<BlockedRangeResponse> blockedRanges;

	public AvailabilityResponse() {
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<BlockedRangeResponse> getBlockedRanges() {
		return blockedRanges;
	}

	public void setBlockedRanges(List<BlockedRangeResponse> blockedRanges) {
		this.blockedRanges = blockedRanges;
	}
}