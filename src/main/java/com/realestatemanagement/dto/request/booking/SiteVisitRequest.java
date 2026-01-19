package com.realestatemanagement.dto.request.booking;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;

public class SiteVisitRequest {
	@NotNull
	private Long propertyId;
	@NotNull
	private OffsetDateTime visitDateTime;
	@NotNull
	private String status;

	public Long getPropertyId() {
		return propertyId;
	}

	public OffsetDateTime getVisitDateTime() {
		return visitDateTime;
	}

	public String getStatus() {
		return status;
	}

}