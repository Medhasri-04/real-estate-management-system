package com.realestatemanagement.dto.request.booking;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BookingCreateRequest {
	@NotNull
	private Long propertyId;
	private LocalDateTime bookingDate; // rental booking
	private LocalDateTime visitDateTime; // site visit booking
	private String status; // PENDING / CONFIRMED / CANCELLED
	private String type; // RENTAL / SITE_VISIT

	public BookingCreateRequest() {
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDateTime getVisitDateTime() {
		return visitDateTime;
	}

	public void setVisitDateTime(LocalDateTime visitDateTime) {
		this.visitDateTime = visitDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}